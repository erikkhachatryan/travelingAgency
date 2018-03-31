package service.beans.subForms;

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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Erik on 25-Dec-17.
 */
public class TravelingLocationSubForm {

    private PortfolioForm portfolioForm;
    private SessionData sessionData;
    private GeneralClassifierCache generalClassifierCache;
    private MainEntity currentEntity;
    private boolean editMode = false;

    public PortfolioForm getParentForm() {
        return portfolioForm;
    }

    public SessionData getSessionData() {
        return sessionData;
    }

    public GeneralClassifierCache getGeneralClassifierCache() {
        return generalClassifierCache;
    }

    public void setGeneralClassifierCache(GeneralClassifierCache generalClassifierCache) {
        this.generalClassifierCache = generalClassifierCache;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public TravelingLocationSubForm(PortfolioForm portfolioForm, SessionData sessionData) {
        this.portfolioForm = portfolioForm;
        this.sessionData = sessionData;
    }

    public MainEntity getCurrentEntity() {
        return this.currentEntity;
    }

    public void prepareEditing(MainEntity location) {
        currentEntity = location.clone();
        editMode = true;

    }

    public void prepareViewing(MainEntity location) {
        currentEntity = location.clone();
        editMode = false;
    }

    public void prepareAdding() {
        currentEntity = new MainEntityImpl(Util.getBean("idGenerator", IdGenerator.class).getNextId(MetaCategoryProvider.getLocation()), true);
        editMode = true;
    }

    public void closeAction() {
        currentEntity = null;
        RequestContext.getCurrentInstance().execute("PF('travelingLocationDialog').hide();");
    }

    public void save() {
        getGeneralClassifierCache().saveMainEntity(MetaCategoryProvider.getLocation(), currentEntity);
        closeAction();
    }

    public List<Classifier> loadCountries() {
        return getGeneralClassifierCache().loadClassifiers(MetaCategoryProvider.getCountry());
    }

    public List<Classifier> loadStates() {
        return getGeneralClassifierCache().loadClassifiers(MetaCategoryProvider.getState());
    }

    public void handleFileUpload(FileUploadEvent event) {
        String fileName = event.getFile().getFileName();
        try {
            byte[] buffer = new byte[event.getFile().getInputstream().available()];
            event.getFile().getInputstream().read(buffer);
            File image = new File(Util.getApplicationProperty("file.upload.folder") + "/" + fileName);
            OutputStream outStream = new FileOutputStream(image);
            outStream.write(buffer);
            ((MainEntityImpl) currentEntity).put("Photo", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FacesMessage message = new FacesMessage("Succesful", fileName + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
