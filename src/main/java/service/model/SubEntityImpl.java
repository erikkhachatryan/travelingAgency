package service.model;

import java.util.HashMap;

/**
 * Created by Erik on 11/12/2017.
 */
public class SubEntityImpl extends HashMap<String, Object> implements SubEntity {

    public SubEntityImpl(EditableEntity parent) {
        this.put("parent", parent);
    }

    @Override
    public void setId(Integer id) {
        this.put("id", id);
    }

    @Override
    public Integer getId() {
        return (Integer) this.get("id");
    }

    @Override
    public EditableEntity getParent() {
        return (EditableEntity) this.get("parent");
    }
}
