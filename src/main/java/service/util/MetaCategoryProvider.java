package service.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Erik on 20-Dec-17.
 */
public class MetaCategoryProvider {

    private static MetaCategoryId LOCATION;
    private static MetaCategoryId USER;
    private static MetaCategoryId COUNTRY;
    private static MetaCategoryId STATE;
    private static MetaCategoryId GENDER;
    private static MetaCategoryId ROLE;
    private static MetaCategoryId BOOKING;
    private static MetaCategoryId LOCATION_SIGHT_SEEING;

    public static MetaCategoryId getLocation() {
        if (LOCATION == null) {
            Map<String, MetaCategoryType> columns = new HashMap<>();
            columns.put("LocationID", MetaCategoryType.IDENTITY);
            columns.put("LocationName", MetaCategoryType.STRING);
            columns.put("Country", MetaCategoryType.CLASSIFIER);
            columns.put("State", MetaCategoryType.CLASSIFIER);
            columns.put("Photo", MetaCategoryType.STRING);
            Map<String, MetaCategoryId> subEntities = new HashMap<>();
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
            LOCATION_SIGHT_SEEING = new MetaCategoryId("LocationSightSeeing", columns);
        }
        return LOCATION_SIGHT_SEEING;
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
            BOOKING = new MetaCategoryId("Booking", columns);
        }
        return BOOKING;
    }

}
