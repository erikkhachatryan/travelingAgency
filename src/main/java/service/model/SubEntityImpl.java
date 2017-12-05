package service.model;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Erik on 11/12/2017.
 */
public class SubEntityImpl extends EditableEntityImpl implements SubEntity {

    private static AtomicInteger ID_GENERATOR = new AtomicInteger(-1);

    public SubEntityImpl(@Nonnull EditableEntity parent) {
        super(ID_GENERATOR.decrementAndGet());
        this.put("parent", Objects.requireNonNull(parent));
    }

    @Override
    public EditableEntity getParent() {
        return (EditableEntity) this.get("parent");
    }

    public void updateParent(@Nonnull Entity parent) {
        this.put("parent", Objects.requireNonNull(parent));
    }

    @Override
    public SubEntity clone() {
        return (SubEntity) super.clone();
    }
}
