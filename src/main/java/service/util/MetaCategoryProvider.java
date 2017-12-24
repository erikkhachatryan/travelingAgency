package service.util;

import service.model.SubEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Erik on 20-Dec-17.
 */
public class MetaCategoryProvider {

    private static MetaCategoryId LOCATION;
    private static MetaCategoryId USER;
    private static MetaCategoryId COUNTRY;

    public static MetaCategoryId getLocation() {
        if (LOCATION == null) {
            Map<String, MetaCategoryType> columns = new HashMap<>();
            columns.put("LocationID", MetaCategoryType.IDENTITY);
            columns.put("LocationInstanceID", MetaCategoryType.INTEGER);
            columns.put("LocationName", MetaCategoryType.STRING);
            columns.put("CountryID", MetaCategoryType.CLASSIFIER);
            columns.put("StateID", MetaCategoryType.CLASSIFIER);
            LOCATION  = new MetaCategoryId("Location", columns);
        }
        return LOCATION;
    }

    public static MetaCategoryId getUser() {
        if (USER == null) {
            Map<String, MetaCategoryType> columns = new HashMap<>();
            columns.put("UserID", MetaCategoryType.IDENTITY);
            columns.put("FirstName", MetaCategoryType.STRING);
            columns.put("LastName", MetaCategoryType.STRING);
            columns.put("DateOfBirth", MetaCategoryType.DATE);
            columns.put("PhoneNumber", MetaCategoryType.STRING);
            columns.put("Address", MetaCategoryType.STRING);
            columns.put("Email", MetaCategoryType.STRING);
            columns.put("GenderID", MetaCategoryType.INTEGER);
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
}
