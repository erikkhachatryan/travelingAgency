package service.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Erik on 11/12/2017.
 */
public class MainEntityImpl extends HashMap<String, Object> implements MainEntity {
    @Override
    public void setId(Integer id) {
        this.put("id", id);
    }

    @Override
    public Integer getId() {
        return ((Integer) this.get("id"));
    }

    @Override
    public List<SubEntity> getSubEntities(String subEntityKey) {
        return (List<SubEntity>) this.get(subEntityKey);
    }

    @Override
    public Classifier getClassifier(String classifierKey) {
        return (Classifier) this.get(classifierKey);
    }
}
