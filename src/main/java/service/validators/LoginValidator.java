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
@FacesValidator("loginValidator")
public class LoginValidator implements Validator {

    public static String PROCESS = "process";
    public static String MESSAGE = "message";
    public static String CURRENT_USER = "currentUser";
    public static String ALL_USERS = "allUsers";

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (processValidation(uiComponent)) {
            Classifier currentUser = ((Classifier) uiComponent.getAttributes().get(CURRENT_USER));
            if (currentUser != null && !"".equals(currentUser.getName()) && !"".equals(currentUser.getString("Password"))) {
                List<Classifier> allUsers = ((List<Classifier>) uiComponent.getAttributes().get(ALL_USERS));
                for (Classifier user : allUsers) {
                    if (currentUser.getName().equals(user.getName()) && currentUser.getString("Password").equals(user.getString("Password"))) {
                        currentUser.setId(user.getId());
                        return;
                    }
                }
                addMessage(uiComponent);
            }

        }
    }

    private boolean processValidation(UIComponent component) {
        return Boolean.valueOf(component.getAttributes().get(PROCESS).toString());
    }

    private void addMessage(UIComponent component) {
        FacesMessage message = new FacesMessage(component.getAttributes().get(MESSAGE).toString());
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }
}
