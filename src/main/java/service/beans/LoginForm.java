package service.beans;

import org.primefaces.context.RequestContext;
import service.commons.SessionData;
import service.model.Classifier;
import service.model.GeneralCache;
import service.util.MetaCategoryProvider;

import javax.faces.context.FacesContext;
import java.util.List;

/**
 * Created by Levon on 11/30/2017.
 */
public class LoginForm {

    private GeneralCache generalCache;
    private SessionData sessionData;
    private Classifier currentUser;

    public LoginForm(GeneralCache generalCache, SessionData sessionData) {
        this.generalCache = generalCache;
        this.sessionData = sessionData;
        this.currentUser = null;
    }

    public GeneralCache getGeneralCache() {
        return generalCache;
    }

    public SessionData getSessionData() {
        return sessionData;
    }

    public List<Classifier> getUsers() {
        return getGeneralCache().loadClassifiers(MetaCategoryProvider.getUser());
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
