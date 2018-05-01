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
public class TravelingLocationSubForm extends BaseSubForm {

    private PortfolioForm portfolioForm;

    public PortfolioForm getParentForm() {
        return portfolioForm;
    }

    public TravelingLocationSubForm(PortfolioForm portfolioForm, SessionData sessionData, GeneralClassifierCache generalClassifierCache) {
        super(sessionData, generalClassifierCache, "travelingLocationDialog");
        this.portfolioForm = portfolioForm;
    }

    @Override
    public void prepareAdding() {
        setCurrentEntity(new MainEntityImpl(Util.getBean("idGenerator", IdGenerator.class).getNextId(MetaCategoryProvider.getLocation()), true));
        super.prepareAdding();
    }

    @Override
    public void deleteAction() {
        getGeneralClassifierCache().deleteMainEntity(MetaCategoryProvider.getLocation(), ((MainEntity) getCurrentEntity()));
        super.deleteAction();
    }

    @Override
    public void saveAction() {
        if (!getCurrentEntity().getSubEntities("locationSightSeeings").isEmpty()) {
            ((MainEntityImpl) getCurrentEntity()).put("Photo", getCurrentEntity().getSubEntities("locationSightSeeings").get(0).getString("Photo"));
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
            ((MainEntityImpl) getCurrentEntity()).put("Photo", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FacesMessage message = new FacesMessage("Succesful", fileName + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void resetState() {
        if (getCurrentEntity() != null) {
            ((MainEntityImpl) getCurrentEntity()).put("State", null);
        }
    }

    public boolean isLocationPhotoUploaded() {
        return Util.isPhotoUploaded(getCurrentEntity());
    }

    public String getLocationPhotoUrl() {
        return portfolioForm.getPhotoUrl(getCurrentEntity());
    }

}
