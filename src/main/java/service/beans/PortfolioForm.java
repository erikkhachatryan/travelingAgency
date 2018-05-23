package service.beans;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import service.commons.SessionData;
import service.model.*;
import service.util.MetaCategoryProvider;
import service.util.Util;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 20-Dec-17.
 */
public class PortfolioForm {

    private GeneralClassifierCache generalClassifierCache;
    private SessionData sessionData;

    public GeneralClassifierCache getGeneralClassifierCache() {
        return generalClassifierCache;
    }

    public void setGeneralClassifierCache(GeneralClassifierCache generalClassifierCache) {
        this.generalClassifierCache = generalClassifierCache;
    }

    public SessionData getSessionData() {
        return sessionData;
    }

    public void setSessionData(SessionData sessionData) {
        this.sessionData = sessionData;
    }

    public List<MainEntity> getTravelingLocations() {
        return getGeneralClassifierCache().loadMainEntities(MetaCategoryProvider.getLocation());
    }

    public List<MainEntity> getTopFiveLocations() {
        return getGeneralClassifierCache().loadTopFiveLocations();
    }

    public boolean isLoggedIn() {
        return !(getSessionData().getApplicationUser().getId() < 0);
    }

    public boolean isLoggedInAsAdministrator() {
        return getSessionData().getApplicationUser().getId().equals(Util.ADMINISTRATOR_USER_ID);
    }

    public void logout() {
        getSessionData().setApplicationUser(new ClassifierImpl(-1));
    }

    public String getPhotoUrl(EditableEntity editableEntity) {
        return Util.isPhotoUploaded(editableEntity) ? "images/uploads/" + editableEntity.getString("Photo") : "images/noImageUploaded.png";
    }

}
