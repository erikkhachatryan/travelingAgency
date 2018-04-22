package service.beans.subForms;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import service.commons.SessionData;
import service.model.GeneralClassifierCache;
import service.model.MainEntityImpl;
import service.model.SubEntity;
import service.model.SubEntityImpl;
import service.util.Util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.ListIterator;

/**
 * Created by erikk on 22-Apr-18.
 */
public class SightSeeingSubForm {

    private TravelingLocationSubForm travelingLocationSubForm;
    private SubEntity currentEntity;
    private boolean editMode = false;
    private boolean newMode = false;

    public SightSeeingSubForm(TravelingLocationSubForm travelingLocationSubForm) {
        this.travelingLocationSubForm = travelingLocationSubForm;
    }

    public TravelingLocationSubForm getParentForm() {
        return travelingLocationSubForm;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public boolean isNewMode() {
        return newMode;
    }

    public SubEntity getCurrentEntity() {
        return this.currentEntity;
    }

    public void prepareEditing(SubEntity sightSeeing) {
        currentEntity = sightSeeing.clone();
        newMode = false;
        editMode = true;

    }

    public void prepareViewing(SubEntity sightSeeing) {
        currentEntity = sightSeeing;
        newMode = false;
        editMode = false;
    }

    public void prepareAdding() {
        currentEntity = new SubEntityImpl(getParentForm().getCurrentEntity());
        newMode= true;
        editMode = true;
    }

    public void closeAction() {
        currentEntity = null;
        RequestContext.getCurrentInstance().execute("PF('sightSeeingDialog').hide();");
    }

    public void deleteAction() {
        getParentForm().getCurrentEntity().getSubEntities("locationSightSeeings").removeIf(subEntity -> subEntity.getId().equals(currentEntity.getId()));
        closeAction();
    }

    public void saveAction() {
        if (!newMode) {
            getParentForm().getCurrentEntity().getSubEntities("locationSightSeeings").removeIf(subEntity -> subEntity.getId().equals(currentEntity.getId()));
        }
        getParentForm().getCurrentEntity().getSubEntities("locationSightSeeings").add(currentEntity);
        closeAction();
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
            ((SubEntityImpl) currentEntity).put("Photo", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FacesMessage message = new FacesMessage("Succesful", fileName + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
