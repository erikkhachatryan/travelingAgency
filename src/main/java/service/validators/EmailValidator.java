package service.validators;

import service.model.Classifier;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Levon on 27-Dec-17.
 */
@FacesValidator("emailValidator")
public class EmailValidator implements Validator {

    public static String PROCESS = "process";
    public static String MESSAGE = "message";
    public static String EMAIL = "email";

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (processValidation(uiComponent)) {
            String email = (String) uiComponent.getAttributes().get(EMAIL);
            if (email != null && !email.isEmpty()) {
                Pattern pattern = Pattern.compile("[a-zA-Z.1-9]{1,}@[a-zA-Z]{1,}[.]{1}[a-zA-Z]{1,}");
                Matcher matcher = pattern.matcher(email);
                if (!matcher.matches()) {
                    addMessage(uiComponent);
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
        throw new ValidatorException(message);
    }
}
