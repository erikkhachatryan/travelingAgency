package service.beans.subForms;

import service.commons.SessionData;
import service.model.GeneralCache;
import service.model.MainEntity;
import service.model.SubEntity;
import service.util.MetaCategoryProvider;
import service.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik.khachatryan on 01-May-18.
 */
public class ViewBookingsSubForm extends BaseSubForm{

    private List<MainEntity> bookings;



    public ViewBookingsSubForm(SessionData sessionData, GeneralCache generalCache) {
        super(sessionData, generalCache, "viewBookingsDialog");
    }

    public void prepareEditing() {
        setEditMode(true);
        prepareForm();
    }

    public void prepareViewing() {
        setEditMode(false);
        prepareForm();
    }

    public void prepareForm() {
        initCurrentUserBookings();
    }

    private void initCurrentUserBookings() {
        bookings = new ArrayList<>();
        for (MainEntity booking : getGeneralCache().loadMainEntities(MetaCategoryProvider.getBooking())) {
            if (booking.getInt("UserID").equals(getSessionData().getApplicationUser().getId()) || getSessionData().getApplicationUser().getId().equals(Util.ADMINISTRATOR_USER_ID)) {
                booking.put("trip", getGeneralCache().loadSubEntityById(MetaCategoryProvider.getLocationTrip(),
                        booking.getInt("LocationTripID"), MetaCategoryProvider.getLocation(), booking.getInt("LocationID")));
                booking.put("User", getGeneralCache().loadClassifierById(MetaCategoryProvider.getUser(), booking.getInt("UserID")));
                bookings.add(booking);
            }
        }
    }

    public List<MainEntity> getBookings() {
        return bookings;
    }

    public void removeBooking(MainEntity booking) {
        SubEntity trip = (SubEntity) booking.get("trip");
        trip.getParent().getSubEntities("locationTrips").removeIf(subEntity -> subEntity.getId().equals(trip.getId()));
        trip.put("AvailableTickets", trip.getInt("AvailableTickets") + booking.getInt("TicketsCount"));
        trip.getParent().getSubEntities("locationTrips").add(trip);
        getGeneralCache().saveMainEntity(MetaCategoryProvider.getLocation(), ((MainEntity) trip.getParent()));
        getGeneralCache().deleteMainEntity(MetaCategoryProvider.getBooking(), booking);
        bookings.remove(booking);
    }


}
