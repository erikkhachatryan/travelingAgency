package service.model;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by Erik on 11/12/2017.
 */
public interface MainEntity extends EditableEntity {

    boolean isNew();

    void setOld();

    void setNew();

    void clearSubEntities();

    MainEntity clone();

}
