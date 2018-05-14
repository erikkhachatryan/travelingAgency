package service.beans.subForms;

import service.commons.SessionData;
import service.model.GeneralClassifierCache;
import service.model.MainEntity;
import service.util.MetaCategoryProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik.khachatryan on 01-May-18.
 */
public class ViewBookingsSubForm {

    private SessionData sessionData;
    private GeneralClassifierCache generalClassifierCache;
    private List<MainEntity> bookings;

    public ViewBookingsSubForm(SessionData sessionData, GeneralClassifierCache generalClassifierCache) {
        this.sessionData = sessionData;
        this.generalClassifierCache = generalClassifierCache;
    }

    public void prepareForm() {
        initCurrentUserBookings();
    }

    public List<MainEntity> initCurrentUserBookings() {
        bookings = new ArrayList<>();
        for (MainEntity booking : generalClassifierCache.loadMainEntities(MetaCategoryProvider.getBooking())) {
            if (booking.getInt("UserID").equals(sessionData.getApplicationUser().getId())) {
                booking.put("Trip", generalClassifierCache.loadSubEntityById(MetaCategoryProvider.getLocationTrip(),
                        booking.getInt("LocationTripID"), MetaCategoryProvider.getLocation(), booking.getInt("LocationID")));
                bookings.add(booking);
            }
        }
        return bookings;
    }

    public List<MainEntity> getBookings() {
        return bookings;
    }

    public void removeBooking(MainEntity booking) {
        generalClassifierCache.deleteMainEntity(MetaCategoryProvider.getBooking(), booking);
    }


}
