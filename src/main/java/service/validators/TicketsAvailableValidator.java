package service.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Erik on 27-Dec-17.
 */
@FacesValidator("ticketsAvailableValidator")
public class TicketsAvailableValidator implements Validator {

    public static String PROCESS = "process";
    public static String MESSAGE = "message";
    public static String AVAILABLE_TICKETS_COUNT = "availableTicketsCount";
    public static String ORDERED_TICKETS_COUNT = "orderedTicketsCount";

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (processValidation(uiComponent)) {
            Integer availableTicketsCount = (Integer) uiComponent.getAttributes().get(AVAILABLE_TICKETS_COUNT);
            Integer orderedTicketsCount = (Integer) uiComponent.getAttributes().get(ORDERED_TICKETS_COUNT);
            if (orderedTicketsCount != null && availableTicketsCount != null && orderedTicketsCount > availableTicketsCount) {
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
