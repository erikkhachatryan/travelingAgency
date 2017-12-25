package service.beans.subForms;

import service.beans.PortfolioForm;
import service.commons.SessionData;
import service.model.Classifier;
import service.model.GeneralClassifierCache;
import service.model.MainEntity;

import java.util.List;

/**
 * Created by Erik on 25-Dec-17.
 */
public class TravelingLocationSubForm {

    private PortfolioForm portfolioForm;
    private SessionData sessionData;
    private GeneralClassifierCache generalClassifierCache;
    private MainEntity currentEntity;
    private MainEntity currentEntityBackup;

    public PortfolioForm getParentForm() {
        return portfolioForm;
    }

    public SessionData getSessionData() {
        return sessionData;
    }

    public GeneralClassifierCache getGeneralClassifierCache() {
        return generalClassifierCache;
    }

    public void setGeneralClassifierCache(GeneralClassifierCache generalClassifierCache) {
        this.generalClassifierCache = generalClassifierCache;
    }

    public TravelingLocationSubForm(PortfolioForm portfolioForm, SessionData sessionData) {
        this.portfolioForm = portfolioForm;
        this.sessionData = sessionData;
    }

    public void prepareEditing(MainEntity location) {
        currentEntityBackup = location;
        currentEntity = location.clone();
    }

    public void update() {
        List<MainEntity> travelingLocations = getParentForm().getTravelingLocations();
        travelingLocations.remove(currentEntityBackup);
        travelingLocations.add(currentEntity);
    }

}
