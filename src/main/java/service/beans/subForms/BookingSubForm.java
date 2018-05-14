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

    private TripSubForm tripSubForm;
    private Integer oldBookedTicketsCount = null;

    public BookingSubForm(TripSubForm tripSubForm, SessionData sessionData, GeneralClassifierCache generalClassifierCache) {
        super(sessionData, generalClassifierCache, "bookingDialog");
        this.tripSubForm = tripSubForm;
    }

    @Override
    public void prepareAdding() {
        setCurrentEntity(new MainEntityImpl(Util.getBean("idGenerator", IdGenerator.class).getNextId(MetaCategoryProvider.getBooking()), true));
        getCurrentEntity().put("LocationTripID", getParentForm().getCurrentEntity().getId());
        getCurrentEntity().put("UserID", getSessionData().getApplicationUser().getId());
        super.prepareAdding();
    }

    @Override
    public void prepareEditing(EditableEntity editableEntity) {
        super.prepareEditing(editableEntity);
        oldBookedTicketsCount = getCurrentEntity().getInt("TicketsCount");
    }

    @Override
    public void saveAction() {
        getCurrentEntity().put("TotalCost", getTotalCost());
        getParentForm().getParentForm().getCurrentEntity().put("AvailableTickets", getParentForm().getParentForm().getCurrentEntity()
                .getInt("AvailableTickets") - getCurrentEntity().getInt("TicketsCount") + (isNewMode() ? 0 : oldBookedTicketsCount));
        getGeneralClassifierCache().saveMainEntity(MetaCategoryProvider.getLocation(), ((MainEntity) getParentForm().getParentForm().getCurrentEntity()));
        getParentForm().getParentForm().setCurrentEntity(getGeneralClassifierCache().loadMainEntityById(MetaCategoryProvider.getLocation(),
                getParentForm().getParentForm().getCurrentEntity().getId()));
        getGeneralClassifierCache().saveMainEntity(MetaCategoryProvider.getBooking(), ((MainEntity) getCurrentEntity()));
        super.saveAction();
    }

    public Integer getTotalCost() {
        if (getCurrentEntity() == null || getCurrentEntity().getInt("TicketsCount") == null) {
            return null;
        }
        return getCurrentEntity().getInt("TicketsCount") * getParentForm().getCurrentEntity().getInt("TicketCost");

    }
    public TripSubForm getParentForm() {
        return tripSubForm;
    }

}
