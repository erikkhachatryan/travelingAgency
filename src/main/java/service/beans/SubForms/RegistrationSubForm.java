package service.beans.SubForms;

import org.primefaces.context.RequestContext;
import service.beans.LoginForm;
import service.model.Classifier;

/**
 * Created by Erik on 03-Dec-17.
 */
public class RegistrationSubForm {

    private Classifier currentUser;

    private LoginForm loginForm;

    public RegistrationSubForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }

    public Classifier getCurrentUser() {
        return currentUser;
    }

    public void prepare(Classifier user) {
        currentUser = user;
    }

    public void register() {
        loginForm.registerUser(currentUser);
        RequestContext.getCurrentInstance().execute("PF('registrationDialog').hide();");
    }
}
