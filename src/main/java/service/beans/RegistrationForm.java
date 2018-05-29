package service.beans;

import org.primefaces.context.RequestContext;
import service.model.Classifier;
import service.model.ClassifierImpl;
import service.model.GeneralCache;
import service.util.MetaCategoryProvider;

import javax.faces.context.FacesContext;
import java.util.List;

/**
 * Created by Erik on 03-Dec-17.
 */
public class RegistrationForm {

    private Classifier currentUser;

    private GeneralCache generalCache;

    public GeneralCache getGeneralCache() {
        return generalCache;
    }

    public void setGeneralCache(GeneralCache generalCache) {
        this.generalCache = generalCache;
    }

    public List<Classifier> getUsers() {
        return getGeneralCache().loadClassifiers(MetaCategoryProvider.getUser());
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
        getGeneralCache().saveClassifier(MetaCategoryProvider.getUser(), currentUser);
        closeAction();
    }

    public List<Classifier> loadGenders() {
        return getGeneralCache().loadClassifiers(MetaCategoryProvider.getGender());
    }
}
