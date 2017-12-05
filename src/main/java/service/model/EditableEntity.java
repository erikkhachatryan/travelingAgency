package service.model;

import javax.annotation.Nonnull;

/**
 * Created by Erik on 11/12/2017.
 */
public interface EditableEntity extends Entity {

    void setId(@Nonnull Integer id);

    EditableEntity clone();

}
