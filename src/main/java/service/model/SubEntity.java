package service.model;

import javax.annotation.Nonnull;

/**
 * Created by Levon on 11/12/2017.
 */
public interface SubEntity extends EditableEntity {
    @Nonnull
    EditableEntity getParent();

    SubEntity clone();
}
