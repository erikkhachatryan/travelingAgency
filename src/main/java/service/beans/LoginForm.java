package service.beans;

import service.beans.dao.DataSource;
import service.model.Classifier;
import service.model.GeneralClassifierCache;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Erik on 11/30/2017.
 */
public class LoginForm {

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

    private TravelingLocationForm travelingLocationForm;

    private boolean loggedIn;

    private Classifier currentUser;

    public LoginForm(TravelingLocationForm travelingLocationForm, Classifier user) {
        loggedIn = false;
        currentUser = user;
        this.travelingLocationForm = travelingLocationForm;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean registerUser(Classifier user) {
        if (getUsers().stream().map(Classifier::getName).collect(Collectors.toList()).contains(user.getName())) {
            return false;
        }
        getUsers().add(user);
        getGeneralClassifierCache().insertUser(user);
        return true;
    }

    public Classifier getCurrentUser() {
        return currentUser;
    }

    public void performLogin() {
        getUsers();
        if(checkLogin()) {
            setLoggedIn(true);
            travelingLocationForm.setLogined(true);
        }
    }

    private boolean checkLogin() {
        boolean result = false;
        for (Classifier user : getUsers()) {
            if (user.getName().equals(currentUser.getName()) && user.getString("password").equals(currentUser.getString("password"))) {
                result = true;
            }
        }
        return result;
    }
}
