package service.model;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Erik on 11/12/2017.
 */
public class MainEntityImpl extends EditableEntityImpl implements MainEntity {

    private static AtomicInteger ID_GENERATOR = new AtomicInteger(-1);

    private boolean isNew;

    public MainEntityImpl(@Nonnull Integer id) {
        super(id);
        isNew = id == -1;
    }

    public MainEntityImpl(@Nonnull Integer id, boolean isNew) {
        this(id);
        this.isNew = isNew;
    }


    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public void setOld() {
        isNew = false;
    }

    @Override
    public void setNew() {
        isNew = true;
    }

    @Override
    public void clearSubEntities() {

    }

    @Override
    public MainEntity clone() {
        return (MainEntity) super.clone();
    }
}
