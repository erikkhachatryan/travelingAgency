package service.beans;

import service.model.Classifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Erik on 11/30/2017.
 */
public class LoginForm {

    private static List<Classifier> users = new ArrayList<>();

    public List<Classifier> getUsers() {
        return users;
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
        if (users.stream().map(Classifier::getName).collect(Collectors.toList()).contains(user.getName())) {
            return false;
        }
        users.add(user);
        return true;
    }

    public Classifier getCurrentUser() {
        return currentUser;
    }

    public void performLogin() {
        if(checkLogin()) {
            setLoggedIn(true);
            travelingLocationForm.setLogined(true);
        }
    }

    private boolean checkLogin() {
        boolean result = false;
        for (Classifier user : users) {
            if (user.getName().equals(currentUser.getName()) && user.getString("password").equals(currentUser.getString("password"))) {
                result = true;
            }
        }
        return result;
    }
}
