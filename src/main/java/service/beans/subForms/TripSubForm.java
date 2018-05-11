package service.beans.subForms;

import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import service.commons.SessionData;
import service.model.EditableEntity;
import service.model.GeneralClassifierCache;
import service.model.SubEntity;
import service.model.SubEntityImpl;

import java.util.*;

/**
 * Created by erik.khachatryan on 22-Apr-18.
 */
public class TripSubForm extends BaseSubForm {

    private TravelingLocationSubForm travelingLocationSubForm;
    private Integer selectedCheckpointId;
    private Integer visitOrder;
    private DefaultDiagramModel diagram;

    public TripSubForm(TravelingLocationSubForm travelingLocationSubForm, SessionData sessionData, GeneralClassifierCache generalClassifierCache) {
        super(sessionData, generalClassifierCache, "tripDialog");
        this.travelingLocationSubForm = travelingLocationSubForm;
    }

    public TravelingLocationSubForm getParentForm() {
        return travelingLocationSubForm;
    }

    public DefaultDiagramModel getDiagram() {
        return diagram;
    }

    public Integer getSelectedCheckpointId() {
        return selectedCheckpointId;
    }

    public void setSelectedCheckpointId(Integer selectedCheckpointId) {
        this.selectedCheckpointId = selectedCheckpointId;
    }

    public List<SubEntity> getAvailableTripCheckpoints() {
        return travelingLocationSubForm.getCurrentEntity().getSubEntities("locationSightSeeings");
    }

    public void addTripCheckpoint() {
        SubEntity checkpoint = new SubEntityImpl(getCurrentEntity());
        checkpoint.put("LocationSightSeeingID", selectedCheckpointId);
        checkpoint.put("VisitOrder", visitOrder++);
        getCurrentEntity().getSubEntities("locationTripCheckpoints").add(checkpoint);
        initDiagram();
    }

    public void resetFields() {
        visitOrder = 0;
        diagram = null;
        selectedCheckpointId = null;
    }

    @Override
    public void prepareAdding() {
        setCurrentEntity(new SubEntityImpl(getParentForm().getCurrentEntity()));
        resetFields();
        super.prepareAdding();
    }

    @Override
    public void prepareEditing(EditableEntity editableEntity) {
        visitOrder = 0;
        getCurrentEntity().getSubEntities("locationTripCheckpoints").forEach(checkpoint -> visitOrder++);
        initDiagram();
        super.prepareEditing(editableEntity);
    }

    @Override
    public void deleteAction() {
        getParentForm().getCurrentEntity().getSubEntities("locationTrips").removeIf(subEntity -> subEntity.getId().equals(getCurrentEntity().getId()));
        super.deleteAction();
    }

    @Override
    public void saveAction() {
        if (!isNewMode()) {
            getParentForm().getCurrentEntity().getSubEntities("locationTrips").removeIf(subEntity -> subEntity.getId().equals(getCurrentEntity().getId()));
        }
        getParentForm().getCurrentEntity().getSubEntities("locationTrips").add(((SubEntity) getCurrentEntity()));
        super.saveAction();
    }

    public void initDiagram() {
        diagram = new DefaultDiagramModel();
        diagram.setMaxConnections(-1);

        Map<Integer, Element> elements = new HashMap<>();
        Element element;
        for (SubEntity locationTripCheckpoints : getCurrentEntity().getSubEntities("locationTripCheckpoints")) {
            element = new Element(getLocationSightseeingNameById(locationTripCheckpoints.getInt("LocationSightSeeingID")));
            element.addEndPoint(new DotEndPoint(EndPointAnchor.AUTO_DEFAULT));
            diagram.addElement(element);
            elements.put(locationTripCheckpoints.getInt("VisitOrder"), element);
        }

        for (int i = 0; i < elements.values().size() - 1; ++i) {
            diagram.connect(new Connection(elements.get(i).getEndPoints().get(0), elements.get(i + 1).getEndPoints().get(0)));
        }
    }

    private String getLocationSightseeingNameById(Integer sightseeingId) {
        for (SubEntity subEntity : getAvailableTripCheckpoints()) {
            if (Objects.equals(subEntity.getId(), sightseeingId)) {
                return subEntity.getString("SightSeeingName");
            }
        }
        return "";
    }
}
