package service.beans.subForms;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import service.beans.IdGenerator;
import service.beans.PortfolioForm;
import service.commons.SessionData;
import service.model.Classifier;
import service.model.GeneralClassifierCache;
import service.model.MainEntity;
import service.model.MainEntityImpl;
import service.util.MetaCategoryProvider;
import service.util.Util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.*;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Erik on 25-Dec-17.
 */
public class TravelingLocationSubForm {

    private PortfolioForm portfolioForm;
    private SessionData sessionData;
    private GeneralClassifierCache generalClassifierCache;
    private MainEntity currentEntity;
    private boolean editMode = false;
    private boolean newMode = false;

    public PortfolioForm getParentForm() {
        return portfolioForm;
    }

    public SessionData getSessionData() {
        return sessionData;
    }

    public GeneralClassifierCache getGeneralClassifierCache() {
        return generalClassifierCache;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public boolean isNewMode() {
        return newMode;
    }

    public TravelingLocationSubForm(PortfolioForm portfolioForm, SessionData sessionData, GeneralClassifierCache generalClassifierCache) {
        this.portfolioForm = portfolioForm;
        this.sessionData = sessionData;
        this.generalClassifierCache = generalClassifierCache;
    }

    public MainEntity getCurrentEntity() {
        return this.currentEntity;
    }

    public void prepareEditing(MainEntity location) {
        currentEntity = location.clone();
        editMode = true;
        newMode = false;
    }

    public void prepareViewing(MainEntity location) {
        currentEntity = location;
        editMode = false;
        newMode = false;
    }

    public void prepareAdding() {
        currentEntity = new MainEntityImpl(Util.getBean("idGenerator", IdGenerator.class).getNextId(MetaCategoryProvider.getLocation()), true);
        editMode = true;
        newMode = true;
    }

    public void closeAction() {
        currentEntity = null;
        RequestContext.getCurrentInstance().execute("PF('travelingLocationDialog').hide();");
    }

    public void deleteAction() {
        generalClassifierCache.deleteMainEntity(MetaCategoryProvider.getLocation(), currentEntity);
        closeAction();
    }

    public void saveAction() {
        generalClassifierCache.saveMainEntity(MetaCategoryProvider.getLocation(), currentEntity);
        closeAction();
    }

    public List<Classifier> loadCountries() {
        return generalClassifierCache.loadClassifiers(MetaCategoryProvider.getCountry());
    }

    public List<Classifier> loadStates() {
        List<Classifier> states = new ArrayList<>();
        if (currentEntity != null && currentEntity.getClassifier("Country") != null) {
            for (Classifier state : generalClassifierCache.loadClassifiers(MetaCategoryProvider.getState())) {
                if (Objects.equals(state.getClassifier("Country").getId(), currentEntity.getClassifier("Country").getId())) {
                    states.add(state);
                }
            }
        }
        return states;
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
            ((MainEntityImpl) currentEntity).put("Photo", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FacesMessage message = new FacesMessage("Succesful", fileName + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void resetState() {
        if (currentEntity != null) {
            ((MainEntityImpl) currentEntity).put("State", null);
        }
    }

    public boolean isLocationPhotoUploaded() {
        return Util.isPhotoUploaded(currentEntity);
    }

    public String getLocationPhotoUrl() {
        return portfolioForm.getPhotoUrl(currentEntity);
    }

}
