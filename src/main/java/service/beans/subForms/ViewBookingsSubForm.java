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

    public ViewBookingsSubForm(SessionData sessionData, GeneralClassifierCache generalClassifierCache) {
        this.sessionData = sessionData;
        this.generalClassifierCache = generalClassifierCache;
    }

    public List<MainEntity> loadCurrentUserBookings() {
        List<MainEntity> result = new ArrayList<>();
        for (MainEntity booking : generalClassifierCache.loadMainEntities(MetaCategoryProvider.getBooking())) {
            if (booking.getInt("UserID").equals(sessionData.getApplicationUser().getId())) {
                result.add(booking);
            }
        }
        return result;
    }

    public void removeBooking(MainEntity booking) {
        generalClassifierCache.deleteMainEntity(MetaCategoryProvider.getBooking(), booking);
    }


}
