package service.beans.subForms;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import service.commons.SessionData;
import service.model.GeneralClassifierCache;
import service.model.SubEntity;
import service.model.SubEntityImpl;
import service.util.Util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;

/**
 * Created by erik.khachatryan on 22-Apr-18.
 */
public class SightSeeingSubForm extends BaseSubForm {

    private TravelingLocationSubForm travelingLocationSubForm;

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
        super.prepareAdding();
    }

    @Override
    public void deleteAction() {
        getParentForm().getCurrentEntity().getSubEntities("locationSightSeeings").removeIf(subEntity -> subEntity.getId().equals(getCurrentEntity().getId()));
        super.deleteAction();
    }

    @Override
    public void saveAction() {
        if (!getCurrentEntity().getSubEntities("locationSightSeeingPhotos").isEmpty()) {
            getCurrentEntity().put("Photo", getCurrentEntity().getSubEntities("locationSightSeeingPhotos").get(0).getString("Photo"));
        }
        if (!isNewMode()) {
            getParentForm().getCurrentEntity().getSubEntities("locationSightSeeings").removeIf(subEntity -> subEntity.getId().equals(getCurrentEntity().getId()));
        }
        getParentForm().getCurrentEntity().getSubEntities("locationSightSeeings").add(((SubEntity) getCurrentEntity()));
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
    }
}
