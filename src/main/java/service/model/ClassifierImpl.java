package service.model;

import java.util.HashMap;

/**
 * Created by Erik on 10/22/2017.
 */
public class ClassifierImpl extends HashMap<String, Object> implements Classifier {

    public ClassifierImpl() {
        super();
    }

    public ClassifierImpl(Integer id) {
        super();
        this.setId(id);
    }

    @Override
    public Integer getId() {
        return (Integer) this.get("id");
    }

    @Override
    public void setId(Integer id) {
        this.put("id", id);
    }

    @Override
    public String getName() {
        return (String) this.get("name");
    }

    @Override
    public void setName(String name) {
        this.put("name", name);
    }
}
