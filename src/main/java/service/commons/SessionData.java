package service.commons;

import service.model.Classifier;
import service.model.GeneralClassifierCache;
import service.util.MetaCategoryProvider;
import service.util.Util;

import javax.annotation.Nonnull;

/**
 * Created by Erik on 10-Dec-17.
 */
public class SessionData {

    private Classifier applicationUser;

    public Classifier getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(Classifier applicationUser) {
        this.applicationUser = applicationUser;
    }


}
