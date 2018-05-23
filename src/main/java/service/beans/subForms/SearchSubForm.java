package service.beans.subForms;

import service.beans.PortfolioForm;
import service.commons.SessionData;
import service.model.GeneralClassifierCache;
import service.model.MainEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 25-Dec-17.
 */
public class SearchSubForm extends BaseSubForm {

    private String locationNameContains;
    private Integer tripMinCost;
    private Integer tripMaxCost;
    private List<MainEntity> locationsList;
    private PortfolioForm portfolioForm;

    public SearchSubForm(PortfolioForm portfolioForm, SessionData sessionData, GeneralClassifierCache generalClassifierCache) {
        super(sessionData, generalClassifierCache, "searchDialog");
        this.portfolioForm = portfolioForm;
        locationsList = new ArrayList<>();
    }

    public PortfolioForm getParentForm() {
        return portfolioForm;
    }

    public String getLocationNameContains() {
        return locationNameContains;
    }

    public void setLocationNameContains(String locationNameContains) {
        this.locationNameContains = locationNameContains;
    }

    public Integer getTripMinCost() {
        return tripMinCost;
    }

    public void setTripMinCost(Integer tripMinCost) {
        this.tripMinCost = tripMinCost;
    }

    public Integer getTripMaxCost() {
        return tripMaxCost;
    }

    public void setTripMaxCost(Integer tripMaxCost) {
        this.tripMaxCost = tripMaxCost;
    }

    public List<MainEntity> getLocationsList() {
        return locationsList;
    }

    public void prepare(boolean isAdmin) {
        locationNameContains = null;
        locationsList = new ArrayList<>();
        tripMinCost = null;
        tripMaxCost = null;
        setEditMode(isAdmin);
    }

    public void search() {
        locationsList = getGeneralClassifierCache().searchLocations(locationNameContains, tripMinCost, tripMaxCost);
    }

}
