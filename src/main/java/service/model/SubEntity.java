package service.model;

/**
 * Created by Erik on 11/12/2017.
 */
public interface SubEntity extends EditableEntity {
    EditableEntity getParent();
}
