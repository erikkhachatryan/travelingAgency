package service.commons;

import service.model.Classifier;

import javax.annotation.Nonnull;

/**
 * Created by Erik on 10-Dec-17.
 */
public class SessionData {

    private Classifier applicationUser;

    public SessionData(Classifier applicationUser) {
        this.applicationUser = applicationUser;
    }

    public Classifier getApplicationUser() {
        return applicationUser;
    }


}
