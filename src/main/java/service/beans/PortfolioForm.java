package service.beans;

import service.model.GeneralClassifierCache;
import service.model.MainEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 20-Dec-17.
 */
public class PortfolioForm {

    private GeneralClassifierCache generalClassifierCache;

    public GeneralClassifierCache getGeneralClassifierCache() {
        return generalClassifierCache;
    }

    public void setGeneralClassifierCache(GeneralClassifierCache generalClassifierCache) {
        this.generalClassifierCache = generalClassifierCache;
    }

    public List<MainEntity> getAllTravelingLocations() {
        return new ArrayList<>();
    }

}
