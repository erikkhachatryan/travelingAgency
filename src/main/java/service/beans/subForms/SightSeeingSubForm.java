package service.beans.subForms;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.primefaces.event.FileUploadEvent;
import service.commons.SessionData;
import service.model.Classifier;
import service.model.EditableEntity;
import service.model.GeneralClassifierCache;
import service.model.SubEntity;
import service.model.SubEntityImpl;
import service.util.MetaCategoryProvider;
import service.util.Util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by erik.khachatryan on 22-Apr-18.
 */
public class SightSeeingSubForm extends BaseSubForm {

    private TravelingLocationSubForm travelingLocationSubForm;
    private Set<Integer> deletedPhotosIds;
    private List<Classifier> users;

    public SightSeeingSubForm(TravelingLocationSubForm travelingLocationSubForm, SessionData sessionData, GeneralClassifierCache generalClassifierCache) {
        super(sessionData, generalClassifierCache, "sightSeeingDialog");
        this.travelingLocationSubForm = travelingLocationSubForm;
    }

    public TravelingLocationSubForm getParentForm() {
        return travelingLocationSubForm;
    }

    @Override
    public void prepareAdding() {
        setCurrentEntity(new SubEntityImpl(getParentForm().getCurrentEntity()));
        resetIdsSet();
        resetFields();
        super.prepareAdding();
    }

    @Override
    public void prepareEditing(EditableEntity editableEntity) {
        resetIdsSet();
        resetFields();
        super.prepareEditing(editableEntity);
    }

    @Override
    public void prepareViewing(EditableEntity editableEntity) {
        resetFields();
        super.prepareViewing(editableEntity);
    }

    @Override
    public void deleteAction() {
        getParentForm().getCurrentEntity().getSubEntities("locationSightSeeings").removeIf(subEntity -> subEntity.getId().equals(getCurrentEntity().getId()));
        if (getCurrentEntity().getId() > 0) {
            getParentForm().getDeletedSubEntities().putIfAbsent(MetaCategoryProvider.getLocationSightSeeing(), new HashSet<>());
            getParentForm().getDeletedSubEntities().get(MetaCategoryProvider.getLocationSightSeeing()).add(getCurrentEntity().getId());
        }
        super.deleteAction();
    }

    @Override
    public void saveAction() {
        if (!getCurrentEntity().getSubEntities("locationSightSeeingPhotos").isEmpty()) {
            getCurrentEntity().put("Photo", getCurrentEntity().getSubEntities("locationSightSeeingPhotos").get(0).getString("Photo"));
        } else {
            getCurrentEntity().put("Photo", null);
        }
        if (!isNewMode()) {
            getParentForm().getCurrentEntity().getSubEntities("locationSightSeeings").removeIf(subEntity -> subEntity.getId().equals(getCurrentEntity().getId()));
        }
        getParentForm().getCurrentEntity().getSubEntities("locationSightSeeings").add(((SubEntity) getCurrentEntity()));
        getParentForm().getDeletedSubEntities().putIfAbsent(MetaCategoryProvider.getLocationSightSeeingPhoto(), new HashSet<>());
        getParentForm().getDeletedSubEntities().get(MetaCategoryProvider.getLocationSightSeeingPhoto()).addAll(deletedPhotosIds);
        if (isNewMode()) {
            getParentForm().setAddTripDisabled(true);
        }
        super.saveAction();
    }

    public void handleFileUpload(FileUploadEvent event) {
        String fileName = event.getFile().getFileName();
        try {
            InputStream initialStream = event.getFile().getInputstream();
            File targetFile = new File(Util.getApplicationProperty("file.upload.folder") + "/" + fileName);
            java.nio.file.Files.copy(
                    initialStream,
                    targetFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            IOUtils.closeQuietly(initialStream);
            SubEntity locationSightSeeingPhoto = new SubEntityImpl(getCurrentEntity());
            locationSightSeeingPhoto.put("Photo", fileName);
            getCurrentEntity().getSubEntities("locationSightSeeingPhotos").add(locationSightSeeingPhoto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FacesMessage message = new FacesMessage("Succesful", fileName + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void removeSightSeeingPhoto(SubEntity sightSeeingPhoto) {
        getCurrentEntity().getSubEntities("locationSightSeeingPhotos").removeIf(subEntity -> subEntity.getId().equals(sightSeeingPhoto.getId()));
        if (sightSeeingPhoto.getId() > 0) {
            deletedPhotosIds.add(sightSeeingPhoto.getId());
        }
    }

    private void resetIdsSet() {
        deletedPhotosIds = new HashSet<>();
    }

    private void resetFields() {
        rate = null;
        comment = null;
        this.users = getGeneralClassifierCache().loadClassifiers(MetaCategoryProvider.getUser());
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
        getCurrentEntity().getSubEntities("locationSightSeeingComments").add(comment);
        int rateSum = 0;
        int ratesCount = 0;
        for (SubEntity locationSightSeeing : getParentForm().getCurrentEntity().getSubEntities("locationSightSeeings")) {
            for (SubEntity locationSightSeeingComment : locationSightSeeing.getSubEntities("locationSightSeeingComments")) {
                ratesCount++;
                rateSum += locationSightSeeingComment.getInt("Rate");
            }
        }
        getParentForm().getCurrentEntity().put("Rate", new BigDecimal(rateSum).setScale(10, RoundingMode.HALF_DOWN).divide(new BigDecimal(ratesCount), RoundingMode.HALF_DOWN));
        getParentForm().saveStayAction();
        resetFields();
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
