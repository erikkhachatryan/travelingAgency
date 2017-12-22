package service.beans.SubForms;

import org.primefaces.context.RequestContext;
import service.beans.LoginForm;
import service.dao.UserDao;
import service.model.Classifier;
import service.model.ClassifierImpl;
import service.model.GeneralClassifierCache;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Erik on 03-Dec-17.
 */
public class RegistrationSubForm {

    private Classifier currentUser;

    private GeneralClassifierCache generalClassifierCache;
    private UserDao userDao;

    public GeneralClassifierCache getGeneralClassifierCache() {
        return generalClassifierCache;
    }

    public void setGeneralClassifierCache(GeneralClassifierCache generalClassifierCache) {
        this.generalClassifierCache = generalClassifierCache;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
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

    public void closeAction() {
        currentUser = null;
        RequestContext.getCurrentInstance().execute("PF('registrationDialog').hide();");
    }

    public void register() {
        getUserDao().save(currentUser);
        closeAction();
    }
}
