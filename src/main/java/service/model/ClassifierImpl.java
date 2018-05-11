package service.model;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Erik on 10/22/2017.
 */
public class ClassifierImpl extends EditableEntityImpl implements Classifier {

    public ClassifierImpl(Integer id) {
        super(id);
    }

    public ClassifierImpl(@Nonnull Integer id, @Nonnull String name) {
        super(id);
        put("Name", Objects.requireNonNull(name));
    }

    @Nonnull
    @Override
    public String getName() {
        return this.get("Name") == null ? "" : this.get("Name").toString();
    }

    @Override
    public void setName(@Nonnull String name) {
        this.put("Name", Objects.requireNonNull(name));
    }

    @Override
    public boolean equals(Object classifier) {
        if (this == classifier) {
            return true;
        }
        if (classifier == null || !(classifier instanceof Classifier)) {
            return false;
        }
        return this.getName().equals(((Classifier) classifier).getName());
    }

    @Override
    public Classifier clone() {return (Classifier) super.clone();}
}
