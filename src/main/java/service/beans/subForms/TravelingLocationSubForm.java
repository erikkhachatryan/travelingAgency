package service.beans.subForms;

import service.beans.IdGenerator;
import service.beans.PortfolioForm;
import service.commons.SessionData;
import service.model.*;
import service.util.MetaCategoryId;
import service.util.MetaCategoryProvider;
import service.util.Util;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Levon on 25-Dec-17.
 */
public class TravelingLocationSubForm extends BaseSubForm {

    private PortfolioForm portfolioForm;
    private Map<MetaCategoryId, Set<Integer>> deletedSubEntities;
    private boolean addTripDisabled = false;

    public PortfolioForm getParentForm() {
        return portfolioForm;
    }

    public TravelingLocationSubForm(PortfolioForm portfolioForm, SessionData sessionData, GeneralCache generalCache) {
        super(sessionData, generalCache, "travelingLocationDialog");
        this.portfolioForm = portfolioForm;
        deletedSubEntities = new HashMap<>();
    }

    @Override
    public void prepareAdding() {
        setCurrentEntity(new MainEntityImpl(Util.getBean("idGenerator", IdGenerator.class).getNextId(MetaCategoryProvider.getLocation()), true));
        getCurrentEntity().put("Rate", BigDecimal.ZERO);
        prepareForm();
        super.prepareAdding();
    }

    @Override
    public void prepareEditing(EditableEntity editableEntity) {
        prepareForm();
        super.prepareEditing(editableEntity);
    }

    @Override
    public void deleteAction() {
        getGeneralCache().deleteMainEntity(MetaCategoryProvider.getLocation(), ((MainEntity) getCurrentEntity()));
        super.deleteAction();
    }

    public void saveStayAction() {
        if (!getCurrentEntity().getSubEntities("locationSightSeeings").isEmpty()) {
            getCurrentEntity().put("Photo", getCurrentEntity().getSubEntities("locationSightSeeings").get(0).getString("Photo"));
        } else {
            getCurrentEntity().put("Photo", null);
        }
        for (Map.Entry<MetaCategoryId, Set<Integer>> deletedEntityEntry : deletedSubEntities.entrySet()) {
            for (Integer subEntityId : deletedEntityEntry.getValue()) {
                getGeneralCache().deleteSubEntityById(deletedEntityEntry.getKey(), subEntityId);
            }
        }
        getGeneralCache().saveMainEntity(MetaCategoryProvider.getLocation(), ((MainEntity) getCurrentEntity()));
    }

    @Override
    public void saveAction() {
        saveStayAction();
        super.saveAction();
    }

    public List<Classifier> loadCountries() {
        return getGeneralCache().loadClassifiers(MetaCategoryProvider.getCountry());
    }

    public List<Classifier> loadStates() {
        List<Classifier> states = new ArrayList<>();
        if (getCurrentEntity() != null && getCurrentEntity().getClassifier("Country") != null) {
            for (Classifier state : getGeneralCache().loadClassifiers(MetaCategoryProvider.getState())) {
                if (Objects.equals(state.getClassifier("Country").getId(), getCurrentEntity().getClassifier("Country").getId())) {
                    states.add(state);
                }
            }
        }
        return states;
    }

    public void resetState() {
        if (getCurrentEntity() != null) {
            getCurrentEntity().put("State", null);
        }
    }

    public Map<MetaCategoryId, Set<Integer>> getDeletedSubEntities() {
        return deletedSubEntities;
    }

    private void prepareForm() {
        deletedSubEntities = new HashMap<>();
        addTripDisabled = false;
    }

    public boolean isAddTripDisabled() {
        return addTripDisabled;
    }

    public void setAddTripDisabled(boolean addTripDisabled) {
        this.addTripDisabled = addTripDisabled;
    }
}
