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

    public static MetaCategoryId getLocation() {
        if (LOCATION == null) {
            Map<String, MetaCategoryType> columns = new HashMap<>();
            columns.put("LocationID", MetaCategoryType.IDENTITY);
            columns.put("LocationInstanceID", MetaCategoryType.INTEGER);
            columns.put("LocationName", MetaCategoryType.STRING);
            columns.put("CountryID", MetaCategoryType.INTEGER);
            columns.put("StateID", MetaCategoryType.INTEGER);
            LOCATION  = new MetaCategoryId("Location", columns);
        }
        return LOCATION;
    }
}
