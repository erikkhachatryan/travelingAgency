package service.beans.subForms;

import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import service.commons.SessionData;
import service.model.Classifier;
import service.model.EditableEntity;
import service.model.GeneralCache;
import service.model.SubEntity;
import service.model.SubEntityImpl;
import service.util.MetaCategoryProvider;

import javax.faces.event.ValueChangeEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by erik.khachatryan on 22-Apr-18.
 */
public class TripSubForm extends BaseSubForm {

    private TravelingLocationSubForm travelingLocationSubForm;
    private Integer selectedCheckpointId;
    private Integer visitOrder;
    private DefaultDiagramModel diagram;
    private boolean openFromLocationForm;
    private List<SubEntity> availableTripCheckpoints;
    private Set<Integer> deletedCheckpointIds;
    private List<Classifier> users;

    public TripSubForm(TravelingLocationSubForm travelingLocationSubForm, SessionData sessionData, GeneralCache generalCache) {
        super(sessionData, generalCache, "tripDialog");
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
        if (availableTripCheckpoints == null) {
            if (travelingLocationSubForm != null && travelingLocationSubForm.getCurrentEntity() != null) {
                availableTripCheckpoints = travelingLocationSubForm.getCurrentEntity().getSubEntities("locationSightSeeings");
            } else {
                availableTripCheckpoints = getGeneralCache().loadMainEntityById(MetaCategoryProvider.getLocation(),
                        getCurrentEntity().getInt("LocationID")).getSubEntities("locationSightSeeings");
            }
        }
        return availableTripCheckpoints;
    }

    public void addTripCheckpoint() {
        SubEntity checkpoint = new SubEntityImpl(getCurrentEntity());
        checkpoint.put("LocationSightSeeingID", selectedCheckpointId);
        checkpoint.put("VisitOrder", visitOrder++);
        getCurrentEntity().getSubEntities("locationTripCheckpoints").add(checkpoint);
        initDiagram();
    }

    public void resetCheckpoints() {
        getParentForm().getDeletedSubEntities().putIfAbsent(MetaCategoryProvider.getLocationTrip(), new HashSet<>());
        getCurrentEntity().getSubEntities("locationTripCheckpoints").forEach(checkpoint -> {
            if (checkpoint.getId() > 0) {
                deletedCheckpointIds.add(checkpoint.getId());
            }
        });
        getCurrentEntity().put("locationTripCheckpoints", new ArrayList<>());
        visitOrder = 0;
        initDiagram();
    }

    public void resetFields() {
        visitOrder = 0;
        diagram = null;
        selectedCheckpointId = null;
        availableTripCheckpoints = null;
        deletedCheckpointIds = new HashSet<>();
        resetCommentFields();
    }

    @Override
    public void prepareAdding() {
        setCurrentEntity(new SubEntityImpl(getParentForm().getCurrentEntity()));
        resetFields();
        super.prepareAdding();
    }

    public void prepareViewing(EditableEntity editableEntity, boolean isOpenFromLocationForm) {
        resetFields();
        openFromLocationForm = isOpenFromLocationForm;
        super.prepareViewing(editableEntity);
        initDiagram();
    }

    @Override
    public void prepareEditing(EditableEntity editableEntity) {
        super.prepareEditing(editableEntity);
        resetFields();
        getCurrentEntity().getSubEntities("locationTripCheckpoints").forEach(checkpoint -> visitOrder++);
        initDiagram();
    }

    @Override
    public void deleteAction() {
        getParentForm().getCurrentEntity().getSubEntities("locationTrips").removeIf(subEntity -> subEntity.getId().equals(getCurrentEntity().getId()));
        if (getCurrentEntity().getId() > 0) {
            getParentForm().getDeletedSubEntities().putIfAbsent(MetaCategoryProvider.getLocationTrip(), new HashSet<>());
            getParentForm().getDeletedSubEntities().get(MetaCategoryProvider.getLocationTrip()).add(getCurrentEntity().getId());
        }
        super.deleteAction();
    }

    public void saveStayAction() {
        if (!isNewMode()) {
            getParentForm().getCurrentEntity().getSubEntities("locationTrips").removeIf(subEntity -> subEntity.getId().equals(getCurrentEntity().getId()));
        }
        getParentForm().getCurrentEntity().getSubEntities("locationTrips").add(((SubEntity) getCurrentEntity()));
        getParentForm().getDeletedSubEntities().putIfAbsent(MetaCategoryProvider.getLocationTripCheckpoint(), new HashSet<>());
        getParentForm().getDeletedSubEntities().get(MetaCategoryProvider.getLocationTripCheckpoint()).addAll(deletedCheckpointIds);
    }

    @Override
    public void saveAction() {
        saveStayAction();
        super.saveAction();
    }

    public void initDiagram() {
        diagram = new DefaultDiagramModel();
        diagram.setMaxConnections(-1);

        Map<Integer, Element> elements = new HashMap<>();
        Element element;
        for (SubEntity locationTripCheckpoints : getCurrentEntity().getSubEntities("locationTripCheckpoints")) {
            element = new Element(getLocationSightseeingNameById(locationTripCheckpoints.getInt("LocationSightSeeingID")),
                    (locationTripCheckpoints.getInt("VisitOrder") * 11 + 3) + "em", "2em");
            element.addEndPoint(new DotEndPoint(EndPointAnchor.CENTER));
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

    public Date getCurrentDate() {
        return new Date();
    }

    public boolean isOpenFromLocationForm() {
        return openFromLocationForm;
    }

    public void onTicketsCountChange(ValueChangeEvent event) {
        if (getCurrentEntity().getInt("AvailableTickets") == null) {
            getCurrentEntity().put("AvailableTickets", event.getNewValue());
            return;
        }
        getCurrentEntity().put("AvailableTickets", getCurrentEntity().getInt("AvailableTickets") + ((Integer) event.getNewValue() - (Integer) event.getOldValue()));
    }

    private void resetCommentFields() {
        rate = null;
        comment = null;
        this.users = getGeneralCache().loadClassifiers(MetaCategoryProvider.getUser());
    }

    private Integer rate;

    public void rate(Integer rate) {
        this.rate = rate;
    }

    public boolean renderRateStart(Integer starRate, Integer actualRate) {
        return actualRate != null && starRate <= actualRate;
    }

    public String getStarImageUrl(Integer rate) {
        return renderRateStart(rate, this.rate) ? getRatedStarImageUrl() : getNonRatedStarImageUrl();
    }

    public String getRatedStarImageUrl() {
        return "images/star.png";
    }

    public String getNonRatedStarImageUrl() {
        return "images/ratedStar.png";
    }

    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void addComment() {
        SubEntity comment = new SubEntityImpl(getCurrentEntity());
        comment.put("Comment", this.comment);
        comment.put("Rate", this.rate);
        comment.put("UserID", getSessionData().getApplicationUser().getId());
        getCurrentEntity().getSubEntities("locationTripComments").add(comment);
        int rateSum = 0;
        int ratesCount = 0;
        for (SubEntity locationSightSeeing : getParentForm().getCurrentEntity().getSubEntities("locationSightSeeings")) {
            for (SubEntity locationSightSeeingComment : locationSightSeeing.getSubEntities("locationSightSeeingComments")) {
                ratesCount++;
                rateSum += locationSightSeeingComment.getInt("Rate");
            }
        }
        for (SubEntity locationSightSeeing : getParentForm().getCurrentEntity().getSubEntities("locationTrips")) {
            for (SubEntity locationTripComment : locationSightSeeing.getSubEntities("locationTripComments")) {
                ratesCount++;
                rateSum += locationTripComment.getInt("Rate");
            }
        }
        getParentForm().getCurrentEntity().put("Rate", new BigDecimal(rateSum).setScale(10, RoundingMode.HALF_DOWN).divide(new BigDecimal(ratesCount), RoundingMode.HALF_DOWN));
        getParentForm().saveStayAction();
        resetCommentFields();
    }

    public String getUserName(Integer userId) {
        for (Classifier user : users) {
            if (Objects.equals(user.getId(), userId)) {
                return user.getName();
            }
        }
        return "";
    }

}
