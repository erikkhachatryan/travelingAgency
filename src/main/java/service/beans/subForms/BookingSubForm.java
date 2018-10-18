package service.beans.subForms;

import service.beans.IdGenerator;
import service.commons.SessionData;
import service.model.*;
import service.util.MetaCategoryProvider;
import service.util.Util;


/**
 * Created by levon.khachatryan on 01-May-18.
 */
public class BookingSubForm extends BaseSubForm {

    private TripSubForm tripSubForm;
    private Integer oldBookedTicketsCount = null;

    public BookingSubForm(TripSubForm tripSubForm, SessionData sessionData, GeneralCache generalCache) {
        super(sessionData, generalCache, "bookingDialog");
        this.tripSubForm = tripSubForm;
    }

    @Override
    public void prepareAdding() {
        setCurrentEntity(new MainEntityImpl(Util.getBean("idGenerator", IdGenerator.class).getNextId(MetaCategoryProvider.getBooking()), true));
        getCurrentEntity().put("LocationID", getParentForm().getCurrentEntity().getInt("LocationID"));
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
        getGeneralCache().saveMainEntity(MetaCategoryProvider.getBooking(), ((MainEntity) getCurrentEntity()));
        getParentForm().getCurrentEntity().put("AvailableTickets", getParentForm().getCurrentEntity().getInt("AvailableTickets")
                - getCurrentEntity().getInt("TicketsCount") + (isNewMode() ? 0 : oldBookedTicketsCount));
        getParentForm().saveStayAction();
        getGeneralCache().saveMainEntity(MetaCategoryProvider.getLocation(), ((MainEntity) getParentForm().getParentForm().getCurrentEntity()));
        getParentForm().getParentForm().setCurrentEntity(getGeneralCache().loadMainEntityById(MetaCategoryProvider.getLocation(),
                getParentForm().getParentForm().getCurrentEntity().getId()));
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
