package service.model;

import service.dao.DataSource;
import service.util.MetaCategoryId;
import service.util.MetaCategoryProvider;
import service.util.MetaCategoryType;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by Erik on 10/22/2017.
 */
public class GeneralClassifierCache {

    private DataSource dataSource;

    private Map<MetaCategoryId, List<Classifier>> classifierCache;
    private Map<MetaCategoryId, List<MainEntity>> mainEntitiesCache;

    public GeneralClassifierCache(DataSource dataSource) {
        this.dataSource = dataSource;
        classifierCache = new HashMap<>();
        mainEntitiesCache = new HashMap<>();
    }

    public MainEntity loadMainEntityById(MetaCategoryId metaCategoryId, @Nonnull Integer instanceId) {
        ResultSet resultSet = null;
        MainEntity mainEntity = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(prepareLoadByIdQuery(metaCategoryId, false))) {
            preparedStatement.setInt(1, instanceId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                mainEntity = new MainEntityImpl(-1, false);
                setEntityFieldsFromResultSet(metaCategoryId, mainEntity, resultSet);
                loadAllSubEntities(metaCategoryId, mainEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
            }
        }
        return mainEntity;
    }

    public Classifier loadMainEntityAsClassifierById(MetaCategoryId metaCategoryId, @Nonnull Integer instanceId) {
        ResultSet resultSet = null;
        Classifier classifier = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(prepareLoadByIdQuery(metaCategoryId, false))) {
            preparedStatement.setInt(1, instanceId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                classifier = new ClassifierImpl(-1);
                setEntityFieldsFromResultSet(metaCategoryId, classifier, resultSet);
                loadAllSubEntities(metaCategoryId, classifier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
            }
        }
        return classifier;
    }

    public List<MainEntity> loadMainEntities(MetaCategoryId metaCategoryId) {
        if (mainEntitiesCache.get(metaCategoryId) == null) {
            List<MainEntity> mainEntities = new ArrayList<>();
            MainEntity mainEntity = null;
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(prepareLoadQuery(metaCategoryId, false));
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    mainEntity = new MainEntityImpl(-1, false);
                    setEntityFieldsFromResultSet(metaCategoryId, mainEntity, resultSet);
                    loadAllSubEntities(metaCategoryId, mainEntity);
                    mainEntities.add(mainEntity);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            mainEntitiesCache.put(metaCategoryId, mainEntities);
        }
        return mainEntitiesCache.get(metaCategoryId);
    }

    public void loadAllSubEntities(MetaCategoryId metaCategoryId, EditableEntity editableEntity) {
        for (Map.Entry<String, MetaCategoryId> subEntityEntry : metaCategoryId.getSubEntities().entrySet()) {
            editableEntity.put(subEntityEntry.getKey(), loadSubEntities(subEntityEntry.getValue(), getIdentityFieldKey(metaCategoryId), editableEntity));
        }
    }

    public List<SubEntity> loadSubEntities(MetaCategoryId metaCategoryId, String parentIdentityFieldKey, EditableEntity parent) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<SubEntity> subEntities = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(prepareLoadQuery(metaCategoryId, false) + " WHERE " + parentIdentityFieldKey + " = ?");
            preparedStatement.setInt(1, parent.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SubEntity subEntity = new SubEntityImpl(parent);
                setEntityFieldsFromResultSet(metaCategoryId, subEntity, resultSet);
                loadAllSubEntities(metaCategoryId, subEntity);
                subEntities.add(subEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (Exception e) {
            }
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
            }
        }
        return subEntities;
    }

    public SubEntity loadSubEntityById(MetaCategoryId metaCategoryId, Integer id, MetaCategoryId parentMetaCategoryId, Integer parentId) {
        SubEntity subEntity = null;
        ResultSet resultSet = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(prepareLoadByIdQuery(metaCategoryId, false))) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                subEntity = new SubEntityImpl(loadMainEntityById(parentMetaCategoryId, parentId));
                setEntityFieldsFromResultSet(metaCategoryId, subEntity, resultSet);
            }
            loadAllSubEntities(metaCategoryId, subEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
            }
        }
        return subEntity;
    }

    public Classifier loadClassifierById(MetaCategoryId metaCategoryId, @Nonnull Integer id) {
        ResultSet resultSet = null;
        ClassifierImpl classifier = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(prepareLoadByIdQuery(metaCategoryId, true))) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                classifier = new ClassifierImpl(-1);
                setEntityFieldsFromResultSet(metaCategoryId, classifier, resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
            }
        }
        return classifier;
    }

    public List<Classifier> loadClassifiers(MetaCategoryId metaCategoryId) {
        if (classifierCache.get(metaCategoryId) == null) {
            List<Classifier> classifiers = new ArrayList<>();
            Classifier classifier = null;
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(prepareLoadQuery(metaCategoryId, true));
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    classifier = new ClassifierImpl(-1);
                    setEntityFieldsFromResultSet(metaCategoryId, classifier, resultSet);
                    classifiers.add(classifier);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            classifierCache.put(metaCategoryId, classifiers);
        }
        return classifierCache.get(metaCategoryId);
    }

    public void saveClassifier(MetaCategoryId metaCategoryId, @Nonnull Classifier classifier) {
        Objects.requireNonNull(classifier);
        if (classifier.getId() <= 0) {
            insertClassifier(metaCategoryId, classifier);
        } else {
            updateClassifier(metaCategoryId, classifier);
        }
        classifierCache.remove(metaCategoryId);
    }

    private void insertClassifier(MetaCategoryId metaCategoryId, Classifier classifier) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(prepareInsertQuery(metaCategoryId, true), Statement.RETURN_GENERATED_KEYS)) {
            setQueryParams(preparedStatement, metaCategoryId, classifier);
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                classifier.setId(generatedKeys.getInt(Statement.RETURN_GENERATED_KEYS));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateClassifier(MetaCategoryId metaCategoryId, Classifier classifier) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(prepareUpdateQuery(metaCategoryId, classifier.getId(), true))) {
            setQueryParams(preparedStatement, metaCategoryId, classifier);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveMainEntity(MetaCategoryId metaCategoryId, @Nonnull MainEntity mainEntity) {
        Objects.requireNonNull(mainEntity);
        if (mainEntity.isNew()) {
            insertMainEntity(metaCategoryId, mainEntity);
        } else {
            updateMainEntity(metaCategoryId, mainEntity);
        }
        mainEntitiesCache.remove(metaCategoryId);
    }

    private void insertMainEntity(MetaCategoryId metaCategoryId, MainEntity mainEntity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(prepareInsertQuery(metaCategoryId, false), Statement.RETURN_GENERATED_KEYS)) {
            setQueryParams(preparedStatement, metaCategoryId, mainEntity);
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                mainEntity.setId(generatedKeys.getInt(Statement.RETURN_GENERATED_KEYS));
            }
            for (Map.Entry<String, MetaCategoryId> subEntitiesEntry : metaCategoryId.getSubEntities().entrySet()) {
                saveSubEntities(mainEntity.getSubEntities(subEntitiesEntry.getKey()), subEntitiesEntry.getValue(), getIdentityFieldKey(metaCategoryId), mainEntity.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveSubEntities(List<SubEntity> subEntities, MetaCategoryId metaCategoryId, String parentIdentityFieldKey, Integer parentId) {
        for (SubEntity subEntity : subEntities) {
            saveSubEntity(subEntity, metaCategoryId, parentIdentityFieldKey, parentId);
        }
    }

    public void saveSubEntity(SubEntity subEntity, MetaCategoryId metaCategoryId, String parentIdentityFieldKey, Integer parentId) {
        Objects.requireNonNull(subEntity);
        if (subEntity.getId() < 0) {
            insertSubEntity(subEntity, metaCategoryId, parentIdentityFieldKey, parentId);
        } else {
            updateSubEntity(subEntity, metaCategoryId, parentIdentityFieldKey, parentId);
        }
    }

    private void insertSubEntity(SubEntity subEntity, MetaCategoryId metaCategoryId, String parentIdentityFieldKey, Integer parentId) {
        subEntity.put(parentIdentityFieldKey, parentId);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(prepareInsertQuery(metaCategoryId, false), Statement.RETURN_GENERATED_KEYS)) {
            String identityFieldKey = getIdentityFieldKey(metaCategoryId);
            setQueryParams(preparedStatement, metaCategoryId, subEntity);
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                subEntity.setId(generatedKeys.getInt(Statement.RETURN_GENERATED_KEYS));
            }
            for (Map.Entry<String, MetaCategoryId> subEntitiesEntry : metaCategoryId.getSubEntities().entrySet()) {
                saveSubEntities(subEntity.getSubEntities(subEntitiesEntry.getKey()), subEntitiesEntry.getValue(), identityFieldKey, subEntity.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateSubEntity(SubEntity subEntity, MetaCategoryId metaCategoryId, String parentIdentityFieldKey, Integer parentId) {
        subEntity.put(parentIdentityFieldKey, parentId);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(prepareUpdateQuery(metaCategoryId, subEntity.getId(), false))) {
            String identityFieldKey = getIdentityFieldKey(metaCategoryId);
            setQueryParams(preparedStatement, metaCategoryId, subEntity);
            preparedStatement.executeUpdate();
            for (Map.Entry<String, MetaCategoryId> subEntitiesEntry : metaCategoryId.getSubEntities().entrySet()) {
                saveSubEntities(subEntity.getSubEntities(subEntitiesEntry.getKey()), subEntitiesEntry.getValue(), identityFieldKey, subEntity.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSubEntityById(MetaCategoryId metaCategoryId, Integer id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM DE_" + metaCategoryId.getTableName() +
                     " WHERE " + getIdentityFieldKey(metaCategoryId) + " = " + id);
             PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM DE_" + metaCategoryId.getTableName() +
                     " WHERE " + getIdentityFieldKey(metaCategoryId) + " = " + id);
             ResultSet resultSet = selectStatement.executeQuery()) {
            if (resultSet.next()) {
                deleteStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateMainEntity(MetaCategoryId metaCategoryId, MainEntity mainEntity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(prepareUpdateQuery(metaCategoryId, mainEntity.getId(), false))) {
            setQueryParams(preparedStatement, metaCategoryId, mainEntity);
            preparedStatement.executeUpdate();
            for (Map.Entry<String, MetaCategoryId> subEntitiesEntry : metaCategoryId.getSubEntities().entrySet()) {
                saveSubEntities(mainEntity.getSubEntities(subEntitiesEntry.getKey()), subEntitiesEntry.getValue(), getIdentityFieldKey(metaCategoryId), mainEntity.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMainEntity(MetaCategoryId metaCategoryId, MainEntity mainEntity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM DE_" + metaCategoryId.getTableName() +
                     " WHERE " + getIdentityFieldKey(metaCategoryId) + " = " + mainEntity.getId())) {
            preparedStatement.execute();
            for (Map.Entry<String, MetaCategoryId> subEntitiesEntry : metaCategoryId.getSubEntities().entrySet()) {
                saveSubEntities(mainEntity.getSubEntities(subEntitiesEntry.getKey()), subEntitiesEntry.getValue(), getIdentityFieldKey(metaCategoryId), mainEntity.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mainEntitiesCache.remove(metaCategoryId);
    }

    private String prepareInsertQuery(MetaCategoryId metaCategoryId, boolean isClassifier) {
        StringBuilder insertIntoPart = new StringBuilder("INSERT INTO " + (isClassifier ? "C_" : "DE_") + metaCategoryId.getTableName() + " (");
        StringBuilder valuesPart = new StringBuilder(" VALUES(");
        boolean isFirst = true;
        for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
            if (entry.getValue().equals(MetaCategoryType.IDENTITY)) {
                continue;
            }
            if (isFirst) {
                if (entry.getValue().equals(MetaCategoryType.CLASSIFIER) || entry.getValue().equals(MetaCategoryType.MAIN_ENTITY)) {
                    insertIntoPart.append(entry.getKey()).append("ID");
                } else {
                    insertIntoPart.append(entry.getKey());
                }
                valuesPart.append("?");
                isFirst = false;
            } else {
                if (entry.getValue().equals(MetaCategoryType.CLASSIFIER) || entry.getValue().equals(MetaCategoryType.MAIN_ENTITY)) {
                    insertIntoPart.append(", ").append(entry.getKey()).append("ID");
                } else {
                    insertIntoPart.append(", ").append(entry.getKey());
                }
                valuesPart.append(", ?");
            }
        }
        insertIntoPart.append(") ");
        valuesPart.append(")");
        return insertIntoPart.toString() + valuesPart.toString();
    }

    private String prepareUpdateQuery(MetaCategoryId metaCategoryId, Integer entityId, boolean isClassifier) {
        StringBuilder stringBuilder = new StringBuilder("UPDATE " + (isClassifier ? "C_" : "DE_") + metaCategoryId.getTableName() + " SET ");
        boolean isFirst = true;
        for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
            if (entry.getValue().equals(MetaCategoryType.IDENTITY)) {
                continue;
            }
            if (isFirst) {
                if (entry.getValue().equals(MetaCategoryType.CLASSIFIER) || entry.getValue().equals(MetaCategoryType.MAIN_ENTITY)) {
                    stringBuilder.append(entry.getKey()).append("ID").append(" = ?");
                } else {
                    stringBuilder.append(entry.getKey()).append(" = ?");
                }
                isFirst = false;
            } else {
                if (entry.getValue().equals(MetaCategoryType.CLASSIFIER) || entry.getValue().equals(MetaCategoryType.MAIN_ENTITY)) {
                    stringBuilder.append(", ").append(entry.getKey()).append("ID").append(" = ?");
                } else {
                    stringBuilder.append(", ").append(entry.getKey()).append(" = ?");
                }
            }
        }
        return stringBuilder.append(" WHERE ").append(getIdentityFieldKey(metaCategoryId)).append(" = ").append(entityId).toString();
    }

    private void setQueryParams(PreparedStatement preparedStatement, MetaCategoryId metaCategoryId, Entity entity) {
        try {
            int index = 1;
            for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
                switch (entry.getValue()) {
                    case INTEGER: {
                        preparedStatement.setObject(index++, entity.getInt(entry.getKey()), JDBCType.INTEGER);
                        break;
                    }
                    case BOOLEAN: {
                        preparedStatement.setObject(index++, entity.getBoolean(entry.getKey()), JDBCType.BOOLEAN);
                        break;
                    }
                    case STRING: {
                        preparedStatement.setObject(index++, entity.getString(entry.getKey()), JDBCType.NVARCHAR);
                        break;
                    }
                    case LONG: {
                        preparedStatement.setObject(index++, entity.getLong(entry.getKey()), JDBCType.BIGINT);
                        break;
                    }
                    case BIG_DECIMAL: {
                        preparedStatement.setObject(index++, entity.getBigDecimal(entry.getKey()), JDBCType.DECIMAL);
                        break;
                    }
                    case DATE: {
                        if (entity.getDate(entry.getKey()) == null) {
                            preparedStatement.setNull(index++, Types.DATE);
                        } else {
                            preparedStatement.setDate(index++, Date.valueOf(entity.getDate(entry.getKey())));
                        }
                        break;
                    }
                    case CLASSIFIER: {
                        if (entity.getClassifier(entry.getKey()) != null) {
                            MetaCategoryId classifierMetaCategoryId = (MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null);
                            saveClassifier(classifierMetaCategoryId, entity.getClassifier(entry.getKey()));
                            preparedStatement.setInt(index++, entity.getClassifier(entry.getKey()).getId());
                        } else {
                            preparedStatement.setNull(index++, Types.INTEGER);
                        }
                        break;
                    }
                    case MAIN_ENTITY: {
                        if (entity.getClassifier(entry.getKey()) != null) {
                            preparedStatement.setInt(index++, entity.getClassifier(entry.getKey()).getId());
                        } else {
                            preparedStatement.setNull(index++, Types.INTEGER);
                        }
                        break;
                    }
                }
            }
        } catch (SQLException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public String getIdentityFieldKey(MetaCategoryId metaCategoryId) {
        for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
            if (entry.getValue().equals(MetaCategoryType.IDENTITY)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void setEntityFieldsFromResultSet(MetaCategoryId metaCategoryId, EditableEntity editableEntity, ResultSet resultSet) {
        try {
            for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
                switch (entry.getValue()) {
                    case IDENTITY: {
                        editableEntity.setId((Integer) resultSet.getObject(entry.getKey()));
                        break;
                    }
                    case CLASSIFIER: {
                        if (resultSet.getObject(entry.getKey() + "ID") != null) {
                            editableEntity.put(entry.getKey(), loadClassifierById((MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null), resultSet.getInt(entry.getKey() + "ID")));
                        }
                        break;
                    }
                    case MAIN_ENTITY: {
                        if (resultSet.getObject(entry.getKey() + "ID") != null) {
                            editableEntity.put(entry.getKey(), loadMainEntityAsClassifierById(MetaCategoryProvider.getLocation(), resultSet.getInt(entry.getKey() + "ID")));
                        }
                        break;
                    }
                    default: {
                        editableEntity.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                        break;
                    }
                }
            }
        } catch (SQLException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private String prepareLoadByIdQuery(MetaCategoryId metaCategoryId, boolean isClassifier) {
        return prepareLoadQuery(metaCategoryId, isClassifier) + " Where " + getIdentityFieldKey(metaCategoryId) + " = ?";
    }

    private String prepareLoadQuery(MetaCategoryId metaCategoryId, boolean isClassifier) {
        return "SELECT * FROM " + (isClassifier ? "C_" : "DE_") + metaCategoryId.getTableName();
    }
}
