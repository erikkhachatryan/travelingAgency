package service.beans;

import service.commons.SessionData;
import service.model.Classifier;
import service.model.GeneralClassifierCache;

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

    public boolean isLoggedIn() {
        return getCurrentUser().getId() != -1;
    }

    public Classifier getCurrentUser() {
        return getSessionData().getApplicationUser();
    }

}
