package service.beans;

import org.primefaces.context.RequestContext;
import service.model.Classifier;
import service.model.ClassifierImpl;
import service.model.GeneralClassifierCache;
import service.util.MetaCategoryProvider;

import javax.faces.context.FacesContext;
import java.util.List;

/**
 * Created by Erik on 03-Dec-17.
 */
public class RegistrationForm {

    private Classifier currentUser;

    private GeneralClassifierCache generalClassifierCache;

    public GeneralClassifierCache getGeneralClassifierCache() {
        return generalClassifierCache;
    }

    public void setGeneralClassifierCache(GeneralClassifierCache generalClassifierCache) {
        this.generalClassifierCache = generalClassifierCache;
    }

    public List<Classifier> getUsers() {
        return getGeneralClassifierCache().loadUsers();
    }

    public Classifier getCurrentUser() {
        if (currentUser == null) {
            currentUser = new ClassifierImpl(-1);
        }
        return currentUser;
    }

    public void prepare() {
        currentUser = null;
    }

    public void closeAction() {
        currentUser = null;
        RequestContext.getCurrentInstance().execute("window.location.href='" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/portfolio.xhtml'");
    }

    public void register() {
        getGeneralClassifierCache().saveClassifier(MetaCategoryProvider.getUser(), currentUser);
        closeAction();
    }
}
