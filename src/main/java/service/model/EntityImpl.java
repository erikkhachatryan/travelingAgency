package service.model;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Erik on 05-Dec-17.
 */
public class EntityImpl extends HashMap<String, Object> implements Entity {

    public EntityImpl(@Nonnull Integer id) {
        put("id", id);
    }

    @Nonnull
    @Override
    public Integer getId() {
        return (Integer) this.get("id");
    }

    @Override
    public BigDecimal getBigDecimal(String key) {
        return (BigDecimal) get(key);
    }

    @Override
    public Boolean getBoolean(String key) {
        return (Boolean) get(key);
    }

    @Override
    public Integer getInt(String key) {
        return (Integer) get(key);
    }

    @Override
    public Long getLong(String key) {
        return (Long) get(key);
    }

    @Override
    public Number getNumber(String key) {
        return (Number) get(key);
    }

    @Override
    public String getString(String key) {
        return (String) get(key);
    }

    @Override
    public Classifier getClassifier(String key) {
        return (Classifier) get(key);
    }

    @Override
    public LocalDate getDate(String key) {
        if (get(key) == null) {
            return null;
        }
        if (get(key) instanceof LocalDate) {
            return (LocalDate) get(key);
        } else if (get(key) instanceof java.util.Date) {
            return ((java.util.Date) get(key)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else {
            return ((java.sql.Date) get(key)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }

    @Override
    public LocalDateTime getDateTime(String key) {
        if (get(key) == null) {
            return null;
        }
        if (get(key) instanceof LocalDateTime) {
            return (LocalDateTime) get(key);
        } else if (get(key) instanceof java.util.Date) {
            return ((java.util.Date) get(key)).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } else {
            return ((java.sql.Date) get(key)).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
    }

    @Override
    public Double getDouble(String key) {
        return (Double) get(key);
    }

    @Override
    public List<SubEntity> getSubEntities(String key) {
        putIfAbsent(key, new ArrayList<>());
        return (List<SubEntity>) get(key);
    }

    @Override
    public Entity clone() {
        EntityImpl entity = (EntityImpl) super.clone();
        for (Entry<String, Object> entry : entity.entrySet()) {
            if (entry.getValue() instanceof Classifier) {
                entity.put(entry.getKey(), ((Classifier) entry.getValue()).clone());
            } else if (entry.getValue() instanceof List) {
                List<SubEntity> subEntities  = new ArrayList<>();
                for (Object value : ((List) entry.getValue())) {
                    subEntities.add(((SubEntity) value).clone());
                }
                entity.put(entry.getKey(),subEntities);
            }
        }
        return entity;
    }
}
