package service.beans.subForms;

import org.primefaces.context.RequestContext;
import service.beans.PortfolioForm;
import service.commons.SessionData;
import service.model.Classifier;
import service.model.GeneralClassifierCache;
import service.model.MainEntity;
import service.util.MetaCategoryProvider;

import java.util.List;

/**
 * Created by Erik on 25-Dec-17.
 */
public class TravelingLocationSubForm {

    private PortfolioForm portfolioForm;
    private SessionData sessionData;
    private GeneralClassifierCache generalClassifierCache;
    private MainEntity currentEntity;

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

    public MainEntity getCurrentEntity() {
        return this.currentEntity;
    }

    public void prepareEditing(MainEntity location) {
        currentEntity = location;
    }

    public void closeAction() {
        currentEntity = null;
        RequestContext.getCurrentInstance().execute("PF('travelingLocationDialog').hide();");
    }

    public void save() {
        getGeneralClassifierCache().saveMainEntity(MetaCategoryProvider.getLocation(), currentEntity);
        closeAction();
    }

}
