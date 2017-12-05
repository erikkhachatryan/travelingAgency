package service.model;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Erik on 05-Dec-17.
 */
public interface Entity {
    @Nonnull
    Integer getId();

    Integer getInt(String key);

    Long getLong(String key);

    Number getNumber(String key);

    Boolean getBoolean(String key);

    String getString(String key);

    Classifier getClassifier(String key);

    BigDecimal getBigDecimal(String key);

    LocalDate getDate(String key);

    LocalDateTime getDateTime(String key);

    Double getDouble(String key);

    List<SubEntity> getSubEntities(String key);

    Entity clone();
}
