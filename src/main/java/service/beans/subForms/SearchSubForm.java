package service.beans.subForms;

import service.beans.PortfolioForm;
import service.commons.SessionData;
import service.model.GeneralCache;
import service.model.MainEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Levon on 25-Dec-17.
 */
public class SearchSubForm extends BaseSubForm {

    private String locationNameContains;
    private Integer tripMinCost;
    private Integer tripMaxCost;
    private Date tripStartsAfter;
    private List<MainEntity> locationsList;
    private PortfolioForm portfolioForm;

    public SearchSubForm(PortfolioForm portfolioForm, SessionData sessionData, GeneralCache generalCache) {
        super(sessionData, generalCache, "searchDialog");
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

    public Date getTripStartsAfter() {
        return tripStartsAfter;
    }

    public void setTripStartsAfter(Date tripStartsAfter) {
        this.tripStartsAfter = tripStartsAfter;
    }

    public List<MainEntity> getLocationsList() {
        return locationsList;
    }

    public void prepare(boolean isAdmin) {
        locationNameContains = null;
        locationsList = new ArrayList<>();
        tripMinCost = null;
        tripMaxCost = null;
        tripStartsAfter = null;
        setEditMode(isAdmin);
    }

    public void search() {
        locationsList = getGeneralCache().searchLocations(locationNameContains, tripMinCost, tripMaxCost, tripStartsAfter);
    }

    public Date getCurrentDate() {
        return new Date();
    }

}
