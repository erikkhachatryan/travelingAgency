package service.model;

import javax.annotation.Nonnull;

/**
 * Created by Levon on 05-Dec-17.
 */
public class EditableEntityImpl extends EntityImpl implements EditableEntity {

    public EditableEntityImpl(@Nonnull Integer id) {super(id);}

    @Override
    public void setId(@Nonnull Integer id) {
        this.put("id", id);
    }

    @Override
    public EditableEntity clone() {
        return (EditableEntityImpl) super.clone();
    }
}
