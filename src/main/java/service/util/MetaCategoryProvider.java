package service.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Erik on 20-Dec-17.
 */
public class MetaCategoryProvider {

    private static MetaCategoryId USER;
    private static MetaCategoryId COUNTRY;
    private static MetaCategoryId STATE;
    private static MetaCategoryId GENDER;
    private static MetaCategoryId ROLE;
    private static MetaCategoryId LOCATION;
    private static MetaCategoryId LOCATION_SIGHT_SEEING;
    private static MetaCategoryId LOCATION_SIGHT_SEEING_PHOTO;
    private static MetaCategoryId BOOKING;
    private static MetaCategoryId Location_Trip;
    private static MetaCategoryId Location_Trip_Checkpoint;

    public static MetaCategoryId getLocation() {
        if (LOCATION == null) {
            Map<String, MetaCategoryType> columns = new HashMap<>();
            columns.put("LocationID", MetaCategoryType.IDENTITY);
            columns.put("LocationName", MetaCategoryType.STRING);
            columns.put("Country", MetaCategoryType.CLASSIFIER);
            columns.put("State", MetaCategoryType.CLASSIFIER);
            columns.put("Photo", MetaCategoryType.STRING);
            Map<String, MetaCategoryId> subEntities = new HashMap<>();
            subEntities.put("locationTrips", getLocationTrip());
            subEntities.put("locationSightSeeings", getLocationSightSeeing());
            LOCATION  = new MetaCategoryId("Location", columns, subEntities);
        }
        return LOCATION;
    }

    public static MetaCategoryId getLocationSightSeeing() {
        if (LOCATION_SIGHT_SEEING == null) {
            Map<String, MetaCategoryType> columns = new HashMap<>();
            columns.put("LocationSightSeeingID", MetaCategoryType.IDENTITY);
            columns.put("LocationID", MetaCategoryType.INTEGER);
            columns.put("SightSeeingName", MetaCategoryType.STRING);
            columns.put("Details", MetaCategoryType.STRING);
            columns.put("Comment", MetaCategoryType.STRING);
            columns.put("Photo", MetaCategoryType.STRING);
            Map<String, MetaCategoryId> subEntities = new HashMap<>();
            subEntities.put("locationSightSeeingPhotos", getLocationSightSeeingPhoto());
            LOCATION_SIGHT_SEEING = new MetaCategoryId("LocationSightSeeing", columns, subEntities);
        }
        return LOCATION_SIGHT_SEEING;
    }

    public static MetaCategoryId getLocationSightSeeingPhoto() {
        if (LOCATION_SIGHT_SEEING_PHOTO == null) {
            Map<String, MetaCategoryType> columns = new HashMap<>();
            columns.put("LocationSightSeeingPhotoID", MetaCategoryType.IDENTITY);
            columns.put("LocationSightSeeingID", MetaCategoryType.INTEGER);
            columns.put("Photo", MetaCategoryType.STRING);
            LOCATION_SIGHT_SEEING_PHOTO = new MetaCategoryId("LocationSightSeeingPhoto", columns);
        }
        return LOCATION_SIGHT_SEEING_PHOTO;
    }

    public static MetaCategoryId getUser() {
        if (USER == null) {
            Map<String, MetaCategoryType> columns = new HashMap<>();
            columns.put("UserID", MetaCategoryType.IDENTITY);
            columns.put("Name", MetaCategoryType.STRING);
            columns.put("Password", MetaCategoryType.STRING);
            columns.put("FirstName", MetaCategoryType.STRING);
            columns.put("LastName", MetaCategoryType.STRING);
            columns.put("DateOfBirth", MetaCategoryType.DATE);
            columns.put("PhoneNumber", MetaCategoryType.STRING);
            columns.put("Address", MetaCategoryType.STRING);
            columns.put("Email", MetaCategoryType.STRING);
            columns.put("Gender", MetaCategoryType.CLASSIFIER);
            USER  = new MetaCategoryId("User", columns);
        }
        return USER;
    }

    public static MetaCategoryId getCountry() {
        if (COUNTRY == null) {
            Map<String, MetaCategoryType> columns = new HashMap<>();
            columns.put("CountryID", MetaCategoryType.IDENTITY);
            columns.put("Name", MetaCategoryType.STRING);
            COUNTRY = new MetaCategoryId("Country", columns);
        }
        return COUNTRY;
    }

    public static MetaCategoryId getState() {
        if (STATE == null) {
            Map<String, MetaCategoryType> columns = new HashMap<>();
            columns.put("StateID", MetaCategoryType.IDENTITY);
            columns.put("Name", MetaCategoryType.STRING);
            columns.put("Country", MetaCategoryType.CLASSIFIER);
            STATE = new MetaCategoryId("State", columns);
        }
        return STATE;
    }

    public static MetaCategoryId getGender() {
        if (GENDER == null) {
            Map<String, MetaCategoryType> columns = new HashMap<>();
            columns.put("GenderID", MetaCategoryType.IDENTITY);
            columns.put("Name", MetaCategoryType.STRING);
            GENDER = new MetaCategoryId("Gender", columns);
        }
        return GENDER;
    }

    public static MetaCategoryId getRole() {
        if (ROLE == null) {
            Map<String, MetaCategoryType> columns = new HashMap<>();
            columns.put("RoleID", MetaCategoryType.IDENTITY);
            columns.put("Name", MetaCategoryType.STRING);
            ROLE = new MetaCategoryId("Role", columns);
        }
        return ROLE;
    }

    public static MetaCategoryId getBooking() {
        if (BOOKING == null) {
            Map<String, MetaCategoryType> columns = new HashMap<>();
            columns.put("BookingID", MetaCategoryType.IDENTITY);
            columns.put("LocationID", MetaCategoryType.INTEGER);
            columns.put("LocationTripID", MetaCategoryType.INTEGER);
            columns.put("TicketsCount", MetaCategoryType.INTEGER);
            columns.put("TotalCost", MetaCategoryType.INTEGER);
            columns.put("UserID", MetaCategoryType.INTEGER);
            BOOKING = new MetaCategoryId("Booking", columns);
        }
        return BOOKING;
    }

    public static MetaCategoryId getLocationTrip() {
        if (Location_Trip == null) {
            Map<String, MetaCategoryType> columns = new HashMap<>();
            columns.put("LocationTripID", MetaCategoryType.IDENTITY);
            columns.put("LocationID", MetaCategoryType.INTEGER);
            columns.put("Title", MetaCategoryType.STRING);
            columns.put("Details", MetaCategoryType.STRING);
            columns.put("StartDate", MetaCategoryType.DATE);
            columns.put("TicketsCount", MetaCategoryType.INTEGER);
            columns.put("AvailableTickets", MetaCategoryType.INTEGER);
            columns.put("TicketCost", MetaCategoryType.INTEGER);
            Map<String, MetaCategoryId> subEntities = new HashMap<>();
            subEntities.put("locationTripCheckpoints", getLocationTripCheckpoint());
            Location_Trip = new MetaCategoryId("LocationTrip", columns, subEntities);
        }
        return Location_Trip;
    }

    public static MetaCategoryId getLocationTripCheckpoint() {
        if (Location_Trip_Checkpoint == null) {
            Map<String, MetaCategoryType> columns = new HashMap<>();
            columns.put("LocationTripCheckPointID", MetaCategoryType.IDENTITY);
            columns.put("LocationTripID", MetaCategoryType.INTEGER);
            columns.put("LocationSightSeeingID", MetaCategoryType.INTEGER);
            columns.put("VisitOrder", MetaCategoryType.INTEGER);
            Location_Trip_Checkpoint = new MetaCategoryId("LocationTripCheckpoint", columns);
        }
        return Location_Trip_Checkpoint;
    }


}
