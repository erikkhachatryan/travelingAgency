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

    public GeneralClassifierCache getGeneralClassifierCache() {
        return generalClassifierCache;
    }

    public void setGeneralClassifierCache(GeneralClassifierCache generalClassifierCache) {
        this.generalClassifierCache = generalClassifierCache;
    }

    public SessionData getSessionData() {
        return sessionData;
    }

    public void setSessionData(SessionData sessionData) {
        this.sessionData = sessionData;
    }

    public List<Classifier> getUsers() {
        return getGeneralClassifierCache().loadUsers();
    }

    public Classifier getCurrentUser() {
        return getSessionData().getApplicationUser();
    }

    public void performLogin() {
        RequestContext.getCurrentInstance().execute("window.location.href = '" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/mainPage.xhtml'");
    }

}
