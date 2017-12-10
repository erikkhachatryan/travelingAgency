package service.beans;

import service.commons.SessionData;
import service.model.Classifier;
import service.model.GeneralClassifierCache;
import org.primefaces.context.RequestContext;

import java.util.ArrayList;
import java.util.List;

public class TravelingLocationForm {
    private String systemName;
    private GeneralClassifierCache generalClassifierCache;
    private SessionData sessionData;
    private Classifier selectedLocation;

    public TravelingLocationForm(String systemName, SessionData sessionData) {
        this.systemName = systemName;
        this.sessionData = sessionData;
    }

    public String getSystemName() {
        return systemName;
    }

    public SessionData getSessionData() {
        return sessionData;
    }

    public GeneralClassifierCache getGeneralClassifierCache() {
        return this.generalClassifierCache;
    }

    public void setGeneralClassifierCache(GeneralClassifierCache generalClassifierCache) {
        this.generalClassifierCache = generalClassifierCache;
    }
    public Classifier getSelectedLocation() {
        return this.selectedLocation;
    }

    public void setSelectedLocation(Classifier selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    public List<Classifier> getLocationsList() {
        return new ArrayList<Classifier>(getGeneralClassifierCache().loadLocations().values());
    }

    public void save() {
        RequestContext.getCurrentInstance().execute("alert('selected location: " + selectedLocation.getId() + "')");
    }

    public boolean isLoggedIn() {
        return getSessionData().getApplicationUser().getId() != -1;
    }

}
