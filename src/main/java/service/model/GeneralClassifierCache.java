package service.model;

import service.dao.DataSource;
import service.util.MetaCategoryId;
import service.util.MetaCategoryProvider;
import service.util.MetaCategoryType;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Classifier> loadCountries() {
        if (classifierCache.get(MetaCategoryProvider.getCountry()) == null) {
            classifierCache.put(MetaCategoryProvider.getCountry(), loadClassifiers(MetaCategoryProvider.getCountry()));
        }
        return classifierCache.get(MetaCategoryProvider.getCountry());
    }

    public List<Classifier> loadStates() {
        if (classifierCache.get(MetaCategoryProvider.getState()) == null) {
            classifierCache.put(MetaCategoryProvider.getState(), loadClassifiers(MetaCategoryProvider.getState()));
        }
        return classifierCache.get(MetaCategoryProvider.getState());
    }

    public List<Classifier> loadUsers() {
        if (classifierCache.get(MetaCategoryProvider.getUser()) == null) {
            classifierCache.put(MetaCategoryProvider.getUser(), loadClassifiers(MetaCategoryProvider.getUser()));
        }
        return classifierCache.get(MetaCategoryProvider.getUser());
    }

    public List<MainEntity> loadLocations() {
        if (mainEntitiesCache.get(MetaCategoryProvider.getLocation()) == null) {
            mainEntitiesCache.put(MetaCategoryProvider.getLocation(), loadMainEntities(MetaCategoryProvider.getLocation()));
        }
        return mainEntitiesCache.get(MetaCategoryProvider.getLocation());
    }

    public MainEntity loadMainEntityByInstanceId(MetaCategoryId metaCategoryId, @Nonnull Integer instanceId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        MainEntityImpl mainEntity = null;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM DE_" + metaCategoryId.getTableName() + " Where " + metaCategoryId.getTableName() + "InstanceID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, instanceId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                mainEntity = new MainEntityImpl(-1, false);
                for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
                    switch (entry.getValue()) {
                        case IDENTITY: {
                            mainEntity.setId(resultSet.getInt(entry.getKey()));
                            break;
                        }
                        case INTEGER: {
                            mainEntity.put(entry.getKey(), resultSet.getInt(entry.getKey()));
                            break;
                        }
                        case BOOLEAN: {
                            mainEntity.put(entry.getKey(), resultSet.getBoolean(entry.getKey()));
                            break;
                        }
                        case STRING: {
                            mainEntity.put(entry.getKey(), resultSet.getString(entry.getKey()));
                            break;
                        }
                        case LONG: {
                            mainEntity.put(entry.getKey(), resultSet.getLong(entry.getKey()));
                            break;
                        }
                        case BIG_DECIMAL: {
                            mainEntity.put(entry.getKey(), resultSet.getBigDecimal(entry.getKey()));
                            break;
                        }
                        case DATE: {
                            mainEntity.put(entry.getKey(), resultSet.getDate(entry.getKey()));
                            break;
                        }
                        case CLASSIFIER: {
                            mainEntity.put(entry.getKey(), loadClassifierById((MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null), resultSet.getInt(entry.getKey() + "ID")));
                            break;
                        }
                    }
                }
            }
        } catch (SQLException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
            }
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (Exception e) {
            }
        }
        return mainEntity;
    }

    public List<MainEntity> loadMainEntities(MetaCategoryId metaCategoryId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<MainEntity> mainEntities = new ArrayList<>();
        MainEntityImpl mainEntity = null;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM DE_" + metaCategoryId.getTableName();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                mainEntity = new MainEntityImpl(-1, false);
                for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
                    switch (entry.getValue()) {
                        case IDENTITY: {
                            mainEntity.setId(resultSet.getInt(entry.getKey()));
                            break;
                        }
                        case INTEGER: {
                            mainEntity.put(entry.getKey(), resultSet.getInt(entry.getKey()));
                            break;
                        }
                        case BOOLEAN: {
                            mainEntity.put(entry.getKey(), resultSet.getBoolean(entry.getKey()));
                            break;
                        }
                        case STRING: {
                            mainEntity.put(entry.getKey(), resultSet.getString(entry.getKey()));
                            break;
                        }
                        case LONG: {
                            mainEntity.put(entry.getKey(), resultSet.getLong(entry.getKey()));
                            break;
                        }
                        case BIG_DECIMAL: {
                            mainEntity.put(entry.getKey(), resultSet.getBigDecimal(entry.getKey()));
                            break;
                        }
                        case DATE: {
                            mainEntity.put(entry.getKey(), resultSet.getDate(entry.getKey()));
                            break;
                        }
                        case CLASSIFIER: {
                            mainEntity.put(entry.getKey(), loadClassifierById((MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null), resultSet.getInt(entry.getKey() + "ID")));
                            break;
                        }
                    }
                }
                mainEntities.add(mainEntity);
            }
        } catch (SQLException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
            }
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (Exception e) {
            }
        }
        return mainEntities;
    }

    public Classifier loadClassifierById(MetaCategoryId metaCategoryId, @Nonnull Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ClassifierImpl classifier = null;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM C_" + metaCategoryId.getTableName() + " Where " + metaCategoryId.getTableName() + "ID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                classifier = new ClassifierImpl(-1);
                for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
                    switch (entry.getValue()) {
                        case IDENTITY: {
                            classifier.setId(resultSet.getInt(entry.getKey()));
                            break;
                        }
                        case INTEGER: {
                            classifier.put(entry.getKey(), resultSet.getInt(entry.getKey()));
                            break;
                        }
                        case BOOLEAN: {
                            classifier.put(entry.getKey(), resultSet.getBoolean(entry.getKey()));
                            break;
                        }
                        case STRING: {
                            classifier.put(entry.getKey(), resultSet.getString(entry.getKey()));
                            break;
                        }
                        case LONG: {
                            classifier.put(entry.getKey(), resultSet.getLong(entry.getKey()));
                            break;
                        }
                        case BIG_DECIMAL: {
                            classifier.put(entry.getKey(), resultSet.getBigDecimal(entry.getKey()));
                            break;
                        }
                        case DATE: {
                            classifier.put(entry.getKey(), resultSet.getDate(entry.getKey()));
                            break;
                        }
                        case CLASSIFIER: {
                            classifier.put(entry.getKey(), loadClassifierById((MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null), resultSet.getInt(entry.getKey() + "ID")));
                            break;
                        }
                    }
                }
            }
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
            }
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (Exception e) {
            }
        }
        return classifier;
    }

    public List<Classifier> loadClassifiers(MetaCategoryId metaCategoryId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Classifier> classifiers = new ArrayList<>();
        ClassifierImpl classifier = null;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM C_" + metaCategoryId.getTableName();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                classifier = new ClassifierImpl(-1);
                for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
                    switch (entry.getValue()) {
                        case IDENTITY: {
                            classifier.setId(resultSet.getInt(entry.getKey()));
                            break;
                        }
                        case INTEGER: {
                            classifier.put(entry.getKey(), resultSet.getInt(entry.getKey()));
                            break;
                        }
                        case BOOLEAN: {
                            classifier.put(entry.getKey(), resultSet.getBoolean(entry.getKey()));
                            break;
                        }
                        case STRING: {
                            classifier.put(entry.getKey(), resultSet.getString(entry.getKey()));
                            break;
                        }
                        case LONG: {
                            classifier.put(entry.getKey(), resultSet.getLong(entry.getKey()));
                            break;
                        }
                        case BIG_DECIMAL: {
                            classifier.put(entry.getKey(), resultSet.getBigDecimal(entry.getKey()));
                            break;
                        }
                        case DATE: {
                            classifier.put(entry.getKey(), resultSet.getDate(entry.getKey()));
                            break;
                        }
                        case CLASSIFIER: {
                            classifier.put(entry.getKey(), loadClassifierById((MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null), resultSet.getInt(entry.getKey() + "ID")));
                            break;
                        }
                    }
                }
                classifiers.add(classifier);
            }
        } catch (SQLException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
            }
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (Exception e) {
            }
        }
        return classifiers;
    }

    public void saveClassifier(MetaCategoryId metaCategoryId, @Nonnull Classifier classifier) {
        if (classifier.getId() <= 0) {
            insertClassifier(metaCategoryId, classifier);
        } else {
            updateClassifier(metaCategoryId, classifier);
        }
        classifierCache.remove(metaCategoryId);
    }

    private void insertClassifier(MetaCategoryId metaCategoryId, Classifier classifier) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = dataSource.getConnection()) {
            StringBuilder insertIntoPart = new StringBuilder("INSERT INTO C_" + metaCategoryId.getTableName() + " (");
            StringBuilder valuesPart = new StringBuilder(" VALUES(");
            boolean isFirst = true;
            for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
                if (entry.getValue().equals(MetaCategoryType.IDENTITY)) {
                    continue;
                }
                if (isFirst) {
                    if (entry.getValue().equals(MetaCategoryType.CLASSIFIER)) {
                        insertIntoPart.append(entry.getKey()).append("ID");
                    } else {
                        insertIntoPart.append(entry.getKey());
                    }
                    valuesPart.append("?");
                    isFirst = false;
                } else {
                    if (entry.getValue().equals(MetaCategoryType.CLASSIFIER)) {
                        insertIntoPart.append(", ").append(entry.getKey()).append("ID");
                    } else {
                        insertIntoPart.append(", ").append(entry.getKey());
                    }
                    valuesPart.append(", ?");
                }
            }
            insertIntoPart.append(") ");
            valuesPart.append(")");
            preparedStatement = connection.prepareStatement(insertIntoPart.toString() + valuesPart.toString(), Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            String identityFieldKey = null;
            for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
                switch (entry.getValue()) {
                    case IDENTITY: {
                        identityFieldKey = entry.getKey();
                    }
                    case INTEGER: {
                        preparedStatement.setInt(index++, classifier.getInt(entry.getKey()));
                        break;
                    }
                    case BOOLEAN: {
                        preparedStatement.setBoolean(index++, classifier.getBoolean(entry.getKey()));
                        break;
                    }
                    case STRING: {
                        preparedStatement.setString(index++, classifier.getString(entry.getKey()));
                        break;
                    }
                    case LONG: {
                        preparedStatement.setLong(index++, classifier.getLong(entry.getKey()));
                        break;
                    }
                    case BIG_DECIMAL: {
                        preparedStatement.setBigDecimal(index++, classifier.getBigDecimal(entry.getKey()));
                        break;
                    }
                    case DATE: {
                        preparedStatement.setDate(index++, Date.valueOf(classifier.getDate(entry.getKey())));
                        break;
                    }
                    case CLASSIFIER: {
                        MetaCategoryId classifierMetaCategoryId = (MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null);
                        saveClassifier(classifierMetaCategoryId, classifier.getClassifier(entry.getKey()));
                        preparedStatement.setInt(index++, classifier.getClassifier(entry.getKey()).getId());
                        break;
                    }
                }
            }
            preparedStatement.execute();
            if (identityFieldKey != null) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                while (generatedKeys.next()) {
                    classifier.setId(generatedKeys.getInt(identityFieldKey));
                }
            }
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (Exception e) {
            }
        }
    }

    private void updateClassifier(MetaCategoryId metaCategoryId, Classifier classifier) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = dataSource.getConnection()) {
            StringBuilder stringBuilder = new StringBuilder("UPDATE C_" + metaCategoryId.getTableName() + " SET ");
            boolean isFirst = true;
            String identityColumnName = null;
            for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
                if (entry.getValue().equals(MetaCategoryType.IDENTITY)) {
                    identityColumnName = entry.getKey();
                    continue;
                }
                if (isFirst) {
                    if (entry.getValue().equals(MetaCategoryType.CLASSIFIER)) {
                        stringBuilder.append(entry.getKey()).append("ID").append(" = ?");
                    } else {
                        stringBuilder.append(entry.getKey()).append(" = ?");
                    }
                    isFirst = false;
                } else {
                    if (entry.getValue().equals(MetaCategoryType.CLASSIFIER)) {
                        stringBuilder.append(", ").append(entry.getKey()).append("ID").append(" = ?");
                    } else {
                        stringBuilder.append(", ").append(entry.getKey()).append(" = ?");
                    }
                }
            }
            if (identityColumnName == null) {
                return;
            }
            stringBuilder.append(" WHERE ").append(identityColumnName).append(" = ").append(classifier.getId());
            preparedStatement = connection.prepareStatement(stringBuilder.toString());
            int index = 1;
            for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
                switch (entry.getValue()) {
                    case INTEGER: {
                        preparedStatement.setInt(index++, classifier.getInt(entry.getKey()));
                        break;
                    }
                    case BOOLEAN: {
                        preparedStatement.setBoolean(index++, classifier.getBoolean(entry.getKey()));
                        break;
                    }
                    case STRING: {
                        preparedStatement.setString(index++, classifier.getString(entry.getKey()));
                        break;
                    }
                    case LONG: {
                        preparedStatement.setLong(index++, classifier.getLong(entry.getKey()));
                        break;
                    }
                    case BIG_DECIMAL: {
                        preparedStatement.setBigDecimal(index++, classifier.getBigDecimal(entry.getKey()));
                        break;
                    }
                    case DATE: {
                        preparedStatement.setDate(index++, Date.valueOf(classifier.getDate(entry.getKey())));
                        break;
                    }
                    case CLASSIFIER: {
                        MetaCategoryId classifierMetaCategoryId = (MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null);
                        saveClassifier(classifierMetaCategoryId, classifier.getClassifier(entry.getKey()));
                        preparedStatement.setInt(index++, classifier.getClassifier(entry.getKey()).getId());
                        break;
                    }
                }
            }
            preparedStatement.executeUpdate();
        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (Exception e) {
            }
        }
    }

    public void saveMainEntity(MetaCategoryId metaCategoryId, MainEntity mainEntity) {
        if (mainEntity.getId() <= 0) {
            insertMainEntity(metaCategoryId, mainEntity);
        } else {
            updateMainEntity(metaCategoryId, mainEntity);
        }
        mainEntitiesCache.remove(metaCategoryId);
    }

    private void insertMainEntity(MetaCategoryId metaCategoryId, MainEntity mainEntity) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = dataSource.getConnection()) {
            StringBuilder insertIntoPart = new StringBuilder("INSERT INTO DE_" + metaCategoryId.getTableName() + " (");
            StringBuilder valuesPart = new StringBuilder(" VALUES(");
            boolean isFirst = true;
            for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
                if (entry.getValue().equals(MetaCategoryType.IDENTITY)) {
                    continue;
                }
                if (isFirst) {
                    if (entry.getValue().equals(MetaCategoryType.CLASSIFIER)) {
                        insertIntoPart.append(entry.getKey()).append("ID");
                    } else {
                        insertIntoPart.append(entry.getKey());
                    }
                    valuesPart.append("?");
                    isFirst = false;
                } else {
                    if (entry.getValue().equals(MetaCategoryType.CLASSIFIER)) {
                        insertIntoPart.append(", ").append(entry.getKey()).append("ID");
                    } else {
                        insertIntoPart.append(", ").append(entry.getKey());
                    }
                    valuesPart.append(", ?");
                }
            }
            insertIntoPart.append(") ");
            valuesPart.append(")");
            preparedStatement = connection.prepareStatement(insertIntoPart.toString() + valuesPart.toString(), Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            String identityFieldKey = null;
            for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
                switch (entry.getValue()) {
                    case IDENTITY: {
                        identityFieldKey = entry.getKey();
                    }
                    case INTEGER: {
                        preparedStatement.setInt(index++, mainEntity.getInt(entry.getKey()));
                        break;
                    }
                    case BOOLEAN: {
                        preparedStatement.setBoolean(index++, mainEntity.getBoolean(entry.getKey()));
                        break;
                    }
                    case STRING: {
                        preparedStatement.setString(index++, mainEntity.getString(entry.getKey()));
                        break;
                    }
                    case LONG: {
                        preparedStatement.setLong(index++, mainEntity.getLong(entry.getKey()));
                        break;
                    }
                    case BIG_DECIMAL: {
                        preparedStatement.setBigDecimal(index++, mainEntity.getBigDecimal(entry.getKey()));
                        break;
                    }
                    case DATE: {
                        preparedStatement.setDate(index++, Date.valueOf(mainEntity.getDate(entry.getKey())));
                        break;
                    }
                    case CLASSIFIER: {
                        MetaCategoryId classifierMetaCategoryId = (MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null);
                        saveClassifier(classifierMetaCategoryId, mainEntity.getClassifier(entry.getKey()));
                        preparedStatement.setInt(index++, mainEntity.getClassifier(entry.getKey()).getId());
                        break;
                    }
                }
            }
            preparedStatement.execute();
            if (identityFieldKey != null) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                while (generatedKeys.next()) {
                    mainEntity.setId(generatedKeys.getInt(identityFieldKey));
                }
            }
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (Exception e) {
            }
        }
    }

    private void updateMainEntity(MetaCategoryId metaCategoryId, MainEntity mainEntity) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = dataSource.getConnection()) {
            StringBuilder stringBuilder = new StringBuilder("UPDATE DE_" + metaCategoryId.getTableName() + " SET ");
            boolean isFirst = true;
            String identityColumnName = null;
            for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
                if (entry.getValue().equals(MetaCategoryType.IDENTITY)) {
                    identityColumnName = entry.getKey();
                    continue;
                }
                if (isFirst) {
                    if (entry.getValue().equals(MetaCategoryType.CLASSIFIER)) {
                        stringBuilder.append(entry.getKey()).append("ID").append(" = ?");
                    } else {
                        stringBuilder.append(entry.getKey()).append(" = ?");
                    }
                    isFirst = false;
                } else {
                    if (entry.getValue().equals(MetaCategoryType.CLASSIFIER)) {
                        stringBuilder.append(", ").append(entry.getKey()).append("ID").append(" = ?");
                    } else {
                        stringBuilder.append(", ").append(entry.getKey()).append(" = ?");
                    }
                }
            }
            if (identityColumnName == null) {
                return;
            }
            stringBuilder.append(" WHERE ").append(identityColumnName).append(" = ").append(mainEntity.getId());
            preparedStatement = connection.prepareStatement(stringBuilder.toString());
            int index = 1;
            for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
                switch (entry.getValue()) {
                    case INTEGER: {
                        preparedStatement.setInt(index++, mainEntity.getInt(entry.getKey()));
                        break;
                    }
                    case BOOLEAN: {
                        preparedStatement.setBoolean(index++, mainEntity.getBoolean(entry.getKey()));
                        break;
                    }
                    case STRING: {
                        preparedStatement.setString(index++, mainEntity.getString(entry.getKey()));
                        break;
                    }
                    case LONG: {
                        preparedStatement.setLong(index++, mainEntity.getLong(entry.getKey()));
                        break;
                    }
                    case BIG_DECIMAL: {
                        preparedStatement.setBigDecimal(index++, mainEntity.getBigDecimal(entry.getKey()));
                        break;
                    }
                    case DATE: {
                        preparedStatement.setDate(index++, Date.valueOf(mainEntity.getDate(entry.getKey())));
                        break;
                    }
                    case CLASSIFIER: {
                        MetaCategoryId mainEntityMetaCategoryId = (MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null);
                        saveClassifier(mainEntityMetaCategoryId, mainEntity.getClassifier(entry.getKey()));
                        preparedStatement.setInt(index++, mainEntity.getClassifier(entry.getKey()).getId());
                        break;
                    }
                }
            }
            preparedStatement.executeUpdate();
        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (Exception e) {
            }
        }
    }

}
