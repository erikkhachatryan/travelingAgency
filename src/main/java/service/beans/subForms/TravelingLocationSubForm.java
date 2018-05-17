package service.beans.subForms;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.primefaces.event.FileUploadEvent;
import service.beans.IdGenerator;
import service.beans.PortfolioForm;
import service.commons.SessionData;
import service.model.*;
import service.util.MetaCategoryId;
import service.util.MetaCategoryProvider;
import service.util.Util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.*;
import java.nio.file.StandardCopyOption;
import java.util.*;

/**
 * Created by Erik on 25-Dec-17.
 */
public class TravelingLocationSubForm extends BaseSubForm {

    private PortfolioForm portfolioForm;
    private Map<MetaCategoryId, Set<Integer>> deletedSubEntities;
    private boolean addTripDisabled = false;

    public PortfolioForm getParentForm() {
        return portfolioForm;
    }

    public TravelingLocationSubForm(PortfolioForm portfolioForm, SessionData sessionData, GeneralClassifierCache generalClassifierCache) {
        super(sessionData, generalClassifierCache, "travelingLocationDialog");
        this.portfolioForm = portfolioForm;
        deletedSubEntities = new HashMap<>();
    }

    @Override
    public void prepareAdding() {
        setCurrentEntity(new MainEntityImpl(Util.getBean("idGenerator", IdGenerator.class).getNextId(MetaCategoryProvider.getLocation()), true));
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
        getGeneralClassifierCache().deleteMainEntity(MetaCategoryProvider.getLocation(), ((MainEntity) getCurrentEntity()));
        super.deleteAction();
    }

    @Override
    public void saveAction() {
        if (!getCurrentEntity().getSubEntities("locationSightSeeings").isEmpty()) {
            getCurrentEntity().put("Photo", getCurrentEntity().getSubEntities("locationSightSeeings").get(0).getString("Photo"));
        } else {
            getCurrentEntity().put("Photo", null);
        }
        for (Map.Entry<MetaCategoryId, Set<Integer>> deletedEntityEntry : deletedSubEntities.entrySet()) {
            for (Integer subEntityId : deletedEntityEntry.getValue()) {
                getGeneralClassifierCache().deleteSubEntityById(deletedEntityEntry.getKey(), subEntityId);
            }
        }
        getGeneralClassifierCache().saveMainEntity(MetaCategoryProvider.getLocation(), ((MainEntity) getCurrentEntity()));
        super.saveAction();
    }

    public List<Classifier> loadCountries() {
        return getGeneralClassifierCache().loadClassifiers(MetaCategoryProvider.getCountry());
    }

    public List<Classifier> loadStates() {
        List<Classifier> states = new ArrayList<>();
        if (getCurrentEntity() != null && getCurrentEntity().getClassifier("Country") != null) {
            for (Classifier state : getGeneralClassifierCache().loadClassifiers(MetaCategoryProvider.getState())) {
                if (Objects.equals(state.getClassifier("Country").getId(), getCurrentEntity().getClassifier("Country").getId())) {
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
            getCurrentEntity().put("Photo", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FacesMessage message = new FacesMessage("Succesful", fileName + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void resetState() {
        if (getCurrentEntity() != null) {
            getCurrentEntity().put("State", null);
        }
    }

    public boolean isLocationPhotoUploaded() {
        return Util.isPhotoUploaded(getCurrentEntity());
    }

    public String getLocationPhotoUrl() {
        return portfolioForm.getPhotoUrl(getCurrentEntity());
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
