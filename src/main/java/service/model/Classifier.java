package service.model;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

/**
 * Created by Erik on 10/22/2017.
 */
public interface Classifier extends EditableEntity {

    @Nonnull
    String getName();

    void setName(@Nonnull String name);

    Classifier clone();

}
