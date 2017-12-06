package service.validators;

import service.model.Classifier;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.List;

/**
 * Created by Erik on 06-Dec-17.
 */
@FacesValidator("registrationValidator")
public class RegistrationValidator implements Validator {

    public static String PROCESS = "process";
    public static String MESSAGE = "message";
    public static String USER_NAME = "userName";
    public static String ALL_USERS = "allUsers";

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (processValidation(uiComponent)) {
            String userName = uiComponent.getAttributes().get(USER_NAME) == null ? "" : uiComponent.getAttributes().get(USER_NAME).toString();
            if (!"".equals(userName)) {
                List<Classifier> allUsers = ((List<Classifier>) uiComponent.getAttributes().get(ALL_USERS));
                for (Classifier user : allUsers) {
                    if (userName.equals(user.getName())) {
                        addMessage(uiComponent);
                        return;
                    }
                }
            }
        }
    }

    private boolean processValidation(UIComponent component) {
        return Boolean.valueOf(component.getAttributes().get(PROCESS).toString());
    }

    private void addMessage(UIComponent component) {
        FacesMessage message = new FacesMessage(component.getAttributes().get(MESSAGE).toString());
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(component.getClientId(facesContext), message);
    }
}
