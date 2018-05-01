package service.beans;

import service.commons.SessionData;
import service.model.*;
import service.util.MetaCategoryProvider;
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
        return getGeneralClassifierCache().loadMainEntities(MetaCategoryProvider.getLocation());
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

    public String getPhotoUrl(EditableEntity editableEntity) {
        return Util.isPhotoUploaded(editableEntity) ? "images/uploads/" + editableEntity.getString("Photo") : "images/noImageUploaded.png";
    }

}
