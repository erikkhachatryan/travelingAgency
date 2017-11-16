package service.model;

import java.util.List;

/**
 * Created by Erik on 11/12/2017.
 */
public interface MainEntity extends EditableEntity {
    List<SubEntity> getSubEntities(String key);
    Classifier getClassifier(String key);
}
