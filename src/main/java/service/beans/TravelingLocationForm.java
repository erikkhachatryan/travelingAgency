package service.beans;

import service.model.Classifier;
import service.model.GeneralClassifierCache;
import org.primefaces.context.RequestContext;

import java.util.ArrayList;
import java.util.List;

public class TravelingLocationForm {
    private String systemName;
    private GeneralClassifierCache generalClassifierCache;
    private Classifier selectedLocation;
    private boolean logined = false;

    public boolean isLogined() {
        return logined;
    }

    public void setLogined(boolean logined) {
        this.logined = logined;
    }

    public TravelingLocationForm(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
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
}
