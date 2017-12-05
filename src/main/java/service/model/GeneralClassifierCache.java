package service.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Erik on 10/22/2017.
 */
public class GeneralClassifierCache {

    private HashMap<String, Classifier> locations;

    public Map<String, Classifier> loadLocations() {
        if (locations == null) {
            locations = new HashMap<>();
            Classifier location1 = new ClassifierImpl(1);
            location1.setName("Yerevan");
            locations.put("Yerevan", location1);
            location1 = new ClassifierImpl(2);
            location1.setName("Spitak");
            locations.put("Spitak", location1);
        }
        return locations;
    }
}
