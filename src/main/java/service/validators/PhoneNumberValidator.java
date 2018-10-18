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
@FacesValidator("phoneNumberValidator")
public class PhoneNumberValidator implements Validator {

    public static String PROCESS = "process";
    public static String MESSAGE = "message";
    public static String PHONE_NUMBER = "phoneNumber";

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (processValidation(uiComponent)) {
            String phoneNumber = (String) uiComponent.getAttributes().get(PHONE_NUMBER);
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                Pattern pattern = Pattern.compile("([+]){0,1}([0-9]){0,}");
                Matcher matcher = pattern.matcher(phoneNumber);
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
