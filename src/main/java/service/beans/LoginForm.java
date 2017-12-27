package service.beans;

import org.primefaces.context.RequestContext;
import service.commons.SessionData;
import service.model.Classifier;
import service.model.GeneralClassifierCache;

import javax.faces.context.FacesContext;
import java.util.List;

/**
 * Created by Erik on 11/30/2017.
 */
public class LoginForm {

    private GeneralClassifierCache generalClassifierCache;
    private SessionData sessionData;
    private Classifier currentUser;

    public LoginForm(GeneralClassifierCache generalClassifierCache, SessionData sessionData) {
        this.generalClassifierCache = generalClassifierCache;
        this.sessionData = sessionData;
        this.currentUser = null;
    }

    public GeneralClassifierCache getGeneralClassifierCache() {
        return generalClassifierCache;
    }

    public SessionData getSessionData() {
        return sessionData;
    }

    public List<Classifier> getUsers() {
        return getGeneralClassifierCache().loadUsers();
    }

    public Classifier getCurrentUser() {
        if (currentUser == null) {
            currentUser = getSessionData().getApplicationUser().clone();
        }
        return currentUser;
    }

    public void prepare() {
        currentUser = null;
    }

    public void cancelAction() {
        currentUser = null;
        RequestContext.getCurrentInstance().execute("window.location.href = '" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/portfolio.xhtml'");
    }

    public void performLogin() {
        getSessionData().setApplicationUser(currentUser);
        cancelAction();
    }

}
