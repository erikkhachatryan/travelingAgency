package service.beans;

import service.commons.SessionData;
import service.model.*;
import service.util.MetaCategoryProvider;
import service.util.Util;

import javax.faces.context.FacesContext;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by Erik on 20-Dec-17.
 */
public class PortfolioForm {

    private GeneralCache generalCache;
    private SessionData sessionData;

    public GeneralCache getGeneralCache() {
        return generalCache;
    }

    public void setGeneralCache(GeneralCache generalCache) {
        this.generalCache = generalCache;
    }

    public SessionData getSessionData() {
        return sessionData;
    }

    public void setSessionData(SessionData sessionData) {
        this.sessionData = sessionData;
    }

    public List<MainEntity> getTravelingLocations() {
        return getGeneralCache().loadMainEntities(MetaCategoryProvider.getLocation());
    }

    public List<MainEntity> getTopFiveLocations() {
        return getGeneralCache().loadTopFiveLocations();
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

    private Integer getIntRate(MainEntity location) {
        return location.getBigDecimal("Rate").setScale(0, RoundingMode.DOWN).intValue();
    }

    private boolean renderRateStart(Integer starRate, Integer actualRate) {
        return actualRate != null && starRate <= actualRate;
    }

    public String getStarImageUrl(Integer rate, MainEntity location) {
        return renderRateStart(rate, getIntRate(location)) ? getRatedStarImageUrl() : getNonRatedStarImageUrl();
    }

    private String getRatedStarImageUrl() {
        return "images/star.png";
    }

    private String getNonRatedStarImageUrl() {
        return "images/ratedStar.png";
    }


}
