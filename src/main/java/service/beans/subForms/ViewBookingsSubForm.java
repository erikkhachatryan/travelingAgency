package service.beans.subForms;

import service.commons.SessionData;
import service.model.GeneralClassifierCache;
import service.model.MainEntity;
import service.model.SubEntity;
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
                booking.put("trip", generalClassifierCache.loadSubEntityById(MetaCategoryProvider.getLocationTrip(),
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
        SubEntity trip = (SubEntity) booking.get("trip");
        trip.getParent().getSubEntities("locationTrips").removeIf(subEntity -> subEntity.getId().equals(trip.getId()));
        trip.put("AvailableTickets", trip.getInt("AvailableTickets") + booking.getInt("TicketsCount"));
        trip.getParent().getSubEntities("locationTrips").add(trip);
        generalClassifierCache.saveMainEntity(MetaCategoryProvider.getLocation(), ((MainEntity) trip.getParent()));
        generalClassifierCache.deleteMainEntity(MetaCategoryProvider.getBooking(), booking);
        bookings.remove(booking);
    }


}
