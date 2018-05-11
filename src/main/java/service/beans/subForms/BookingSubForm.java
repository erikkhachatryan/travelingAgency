package service.beans.subForms;

import service.beans.IdGenerator;
import service.commons.SessionData;
import service.model.*;
import service.util.MetaCategoryProvider;
import service.util.Util;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by erik.khachatryan on 01-May-18.
 */
public class BookingSubForm extends BaseSubForm {

    private MainEntity location;

    public MainEntity getLocation() {
        return location;
    }

    public BookingSubForm(SessionData sessionData, GeneralClassifierCache generalClassifierCache) {
        super(sessionData, generalClassifierCache, "bookingDialog");
    }

    public void prepareAdding(Integer locationToId) {
        setCurrentEntity(new MainEntityImpl(Util.getBean("idGenerator", IdGenerator.class).getNextId(MetaCategoryProvider.getBooking()), true));
        location = getGeneralClassifierCache().loadMainEntityById(MetaCategoryProvider.getLocation(), locationToId);
        getCurrentEntity().put("UserID", getSessionData().getApplicationUser().getId());
        super.prepareAdding();
    }

    @Override
    public void saveAction() {
        SubEntity subEntity;
        for (SubEntity sightseeing : getCurrentEntity().getClassifier("LocationTo").getSubEntities("locationSightSeeings")) {
            if (sightseeing.getBoolean("visit")) {
                sightseeing.remove("visit");
                subEntity = new SubEntityImpl(getCurrentEntity());
                subEntity.put("LocationSightSeeingID", sightseeing.getId());
                getCurrentEntity().getSubEntities("bookingSightSeeings").add(subEntity);
            }
        }
        getGeneralClassifierCache().saveMainEntity(MetaCategoryProvider.getBooking(), ((MainEntity) getCurrentEntity()));
        super.saveAction();
    }

    public List<Classifier> loadTravelingLocation() {
        List<Classifier> result = new ArrayList<>();
        ClassifierImpl classifier;
        List<MainEntity> mainEntities = getGeneralClassifierCache().loadMainEntities(MetaCategoryProvider.getLocation());
        for (MainEntity location : mainEntities) {
            classifier = new ClassifierImpl(location.getId());
            classifier.setName(location.getString("LocationName"));
            classifier.put("Photo", location.getString("Photo"));
            classifier.put("locationSightSeeings", location.getSubEntities("locationSightSeeings"));
            result.add(classifier);
        }
        return result;
    }

    public String getPhotoUrl(EditableEntity editableEntity) {
        return Util.isPhotoUploaded(editableEntity) ? "images/uploads/" + editableEntity.getString("Photo") : "images/noImageUploaded.png";
    }

}
