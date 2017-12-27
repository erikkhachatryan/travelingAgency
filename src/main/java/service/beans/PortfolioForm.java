package service.beans;

import service.commons.SessionData;
import service.model.ClassifierImpl;
import service.model.GeneralClassifierCache;
import service.model.MainEntity;
import service.util.Util;

import java.util.List;

/**
 * Created by Erik on 20-Dec-17.
 */
public class PortfolioForm {

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

    public List<MainEntity> getTravelingLocations() {
        return getGeneralClassifierCache().loadLocations();
    }

    public boolean isLoggedIn() {
        return !(getSessionData().getApplicationUser().getId() < 0);
    }

    public boolean isLoggedInAsAdministrator() {
        return getSessionData().getApplicationUser().getId().equals(Util.ADMINISTRATOR_USER_ID);
    }

    public void logout() {
        getSessionData().setApplicationUser(new ClassifierImpl(-1));
    }
}
