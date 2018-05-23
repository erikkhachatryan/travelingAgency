package service.beans.subForms;

import org.primefaces.context.RequestContext;
import service.commons.SessionData;
import service.model.EditableEntity;
import service.model.GeneralClassifierCache;

/**
 * Created by erik.khachatryan on 01-May-18.
 */
public class BaseSubForm {

    private SessionData sessionData;
    private GeneralClassifierCache generalClassifierCache;
    private EditableEntity currentEntity;
    private boolean editMode = false;
    private boolean newMode = false;
    private String subFormName;

    public BaseSubForm(SessionData sessionData, GeneralClassifierCache generalClassifierCache, String subFormName) {
        this.sessionData = sessionData;
        this.generalClassifierCache = generalClassifierCache;
        this.editMode = false;
        this.newMode = false;
        this.subFormName = subFormName;
    }

    public SessionData getSessionData() {
        return sessionData;
    }

    public GeneralClassifierCache getGeneralClassifierCache() {
        return generalClassifierCache;
    }

    public void setCurrentEntity(EditableEntity editableEntity) {
        currentEntity = editableEntity;
    }

    public EditableEntity getCurrentEntity() {
        return currentEntity;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public boolean isNewMode() {
        return newMode;
    }

    public void prepareEditing(EditableEntity editableEntity) {
        currentEntity = editableEntity.clone();
        editMode = true;
        newMode = false;
    }

    public void prepareViewing(EditableEntity editableEntity) {
        currentEntity = editableEntity;
        editMode = false;
        newMode = false;
    }

    public void prepareAdding() {
        editMode = true;
        newMode = true;
    }

    public void closeAction() {
        currentEntity = null;
        RequestContext.getCurrentInstance().execute("PF('" + subFormName + "').hide();");
    }

    public void deleteAction() {
        closeAction();
    }

    public void saveAction() {
        closeAction();
    }
}
