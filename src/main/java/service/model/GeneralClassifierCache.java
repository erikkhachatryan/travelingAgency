package service.model;

import org.primefaces.model.UploadedFile;
import service.dao.DataSource;
import service.util.MetaCategoryId;
import service.util.MetaCategoryProvider;
import service.util.MetaCategoryType;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
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
                            mainEntity.setId((Integer) resultSet.getObject(entry.getKey()));
                            break;
                        }
                        case INTEGER: {
                            mainEntity.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                            break;
                        }
                        case BOOLEAN: {
                            mainEntity.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                            break;
                        }
                        case STRING: {
                            mainEntity.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                            break;
                        }
                        case LONG: {
                            mainEntity.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                            break;
                        }
                        case BIG_DECIMAL: {
                            mainEntity.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                            break;
                        }
                        case DATE: {
                            mainEntity.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                            break;
                        }
                        case CLASSIFIER: {
                            if (resultSet.getObject(entry.getKey() + "ID") != null){
                                mainEntity.put(entry.getKey(), loadClassifierById((MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null), resultSet.getInt(entry.getKey() + "ID")));
                            }
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
        if (mainEntitiesCache.get(metaCategoryId) == null) {
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
                                mainEntity.setId((Integer) resultSet.getObject(entry.getKey()));
                                break;
                            }
                            case INTEGER: {
                                mainEntity.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                                break;
                            }
                            case BOOLEAN: {
                                mainEntity.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                                break;
                            }
                            case STRING: {
                                mainEntity.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                                break;
                            }
                            case LONG: {
                                mainEntity.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                                break;
                            }
                            case BIG_DECIMAL: {
                                mainEntity.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                                break;
                            }
                            case DATE: {
                                mainEntity.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                                break;
                            }
                            case CLASSIFIER: {
                                if (resultSet.getObject(entry.getKey() + "ID") != null) {
                                    mainEntity.put(entry.getKey(), loadClassifierById((MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null), resultSet.getInt(entry.getKey() + "ID")));
                                }
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
            mainEntitiesCache.put(metaCategoryId, mainEntities);
        }
        return mainEntitiesCache.get(metaCategoryId);
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
                            classifier.setId((Integer) resultSet.getObject(entry.getKey()));
                            break;
                        }
                        case INTEGER: {
                            classifier.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                            break;
                        }
                        case BOOLEAN: {
                            classifier.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                            break;
                        }
                        case STRING: {
                            classifier.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                            break;
                        }
                        case LONG: {
                            classifier.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                            break;
                        }
                        case BIG_DECIMAL: {
                            classifier.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                            break;
                        }
                        case DATE: {
                            classifier.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                            break;
                        }
                        case CLASSIFIER: {
                            if (resultSet.getObject(entry.getKey() + "ID") != null) {
                                classifier.put(entry.getKey(), loadClassifierById((MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null), resultSet.getInt(entry.getKey() + "ID")));
                            }
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
        if (classifierCache.get(metaCategoryId) == null) {
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
                                classifier.setId((Integer) resultSet.getObject(entry.getKey()));
                                break;
                            }
                            case INTEGER: {
                                classifier.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                                break;
                            }
                            case BOOLEAN: {
                                classifier.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                                break;
                            }
                            case STRING: {
                                classifier.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                                break;
                            }
                            case LONG: {
                                classifier.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                                break;
                            }
                            case BIG_DECIMAL: {
                                classifier.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                                break;
                            }
                            case DATE: {
                                classifier.put(entry.getKey(), resultSet.getObject(entry.getKey()));
                                break;
                            }
                            case CLASSIFIER: {
                                if (resultSet.getObject(entry.getKey() + "ID") != null) {
                                    classifier.put(entry.getKey(), loadClassifierById((MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null), resultSet.getInt(entry.getKey() + "ID")));
                                }
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
            for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
                switch (entry.getValue()) {
                    case INTEGER: {
                        preparedStatement.setObject(index++, classifier.getInt(entry.getKey()), JDBCType.INTEGER);
                        break;
                    }
                    case BOOLEAN: {
                        preparedStatement.setObject(index++, classifier.getBoolean(entry.getKey()), JDBCType.BOOLEAN);
                        break;
                    }
                    case STRING: {
                        preparedStatement.setObject(index++, classifier.getString(entry.getKey()), JDBCType.NVARCHAR);
                        break;
                    }
                    case LONG: {
                        preparedStatement.setObject(index++, classifier.getLong(entry.getKey()), JDBCType.BIGINT);
                        break;
                    }
                    case BIG_DECIMAL: {
                        preparedStatement.setObject(index++, classifier.getBigDecimal(entry.getKey()), JDBCType.DECIMAL);
                        break;
                    }
                    case DATE: {
                        if (classifier.getDate(entry.getKey()) == null) {
                            preparedStatement.setNull(index++, Types.DATE);
                        } else {
                            preparedStatement.setDate(index++, Date.valueOf(classifier.getDate(entry.getKey())));
                        }
                        break;
                    }
                    case CLASSIFIER: {
                        if (classifier.getClassifier(entry.getKey()) != null) {
                            MetaCategoryId classifierMetaCategoryId = (MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null);
                            saveClassifier(classifierMetaCategoryId, classifier.getClassifier(entry.getKey()));
                            preparedStatement.setInt(index++, classifier.getClassifier(entry.getKey()).getId());
                        } else {
                            preparedStatement.setNull(index++, Types.INTEGER);
                        }
                        break;
                    }
                }
            }
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                classifier.setId(generatedKeys.getInt(Statement.RETURN_GENERATED_KEYS));
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
                        preparedStatement.setObject(index++, classifier.getInt(entry.getKey()), JDBCType.INTEGER);
                        break;
                    }
                    case BOOLEAN: {
                        preparedStatement.setObject(index++, classifier.getBoolean(entry.getKey()), JDBCType.BOOLEAN);
                        break;
                    }
                    case STRING: {
                        preparedStatement.setObject(index++, classifier.getString(entry.getKey()), JDBCType.NVARCHAR);
                        break;
                    }
                    case LONG: {
                        preparedStatement.setObject(index++, classifier.getLong(entry.getKey()), JDBCType.BIGINT);
                        break;
                    }
                    case BIG_DECIMAL: {
                        preparedStatement.setObject(index++, classifier.getBigDecimal(entry.getKey()), JDBCType.DECIMAL);
                        break;
                    }
                    case DATE: {
                        if (classifier.getDate(entry.getKey()) == null) {
                            preparedStatement.setNull(index++, Types.DATE);
                        } else {
                            preparedStatement.setDate(index++, Date.valueOf(classifier.getDate(entry.getKey())));
                        }
                        break;
                    }
                    case CLASSIFIER: {
                        if (classifier.getClassifier(entry.getKey()) != null) {
                            MetaCategoryId classifierMetaCategoryId = (MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null);
                            saveClassifier(classifierMetaCategoryId, classifier.getClassifier(entry.getKey()));
                            preparedStatement.setInt(index++, classifier.getClassifier(entry.getKey()).getId());
                        } else {
                            preparedStatement.setNull(index++, Types.INTEGER);
                        }
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

    public void saveMainEntity(MetaCategoryId metaCategoryId,@Nonnull MainEntity mainEntity) {
        Objects.requireNonNull(mainEntity);
        if (mainEntity.isNew()) {
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
            for (Map.Entry<String, MetaCategoryType> entry : metaCategoryId.getColumns().entrySet()) {
                switch (entry.getValue()) {
                    case INTEGER: {
                        preparedStatement.setObject(index++, mainEntity.getInt(entry.getKey()), JDBCType.INTEGER);
                        break;
                    }
                    case BOOLEAN: {
                        preparedStatement.setObject(index++, mainEntity.getBoolean(entry.getKey()), JDBCType.BOOLEAN);
                        break;
                    }
                    case STRING: {
                        preparedStatement.setObject(index++, mainEntity.getString(entry.getKey()), JDBCType.NVARCHAR);
                        break;
                    }
                    case LONG: {
                        preparedStatement.setObject(index++, mainEntity.getLong(entry.getKey()), JDBCType.BIGINT);
                        break;
                    }
                    case BIG_DECIMAL: {
                        preparedStatement.setObject(index++, mainEntity.getBigDecimal(entry.getKey()), JDBCType.DECIMAL);
                        break;
                    }
                    case DATE: {
                        if (mainEntity.getDate(entry.getKey()) == null) {
                            preparedStatement.setNull(index++, Types.DATE);
                        } else {
                            preparedStatement.setDate(index++, Date.valueOf(mainEntity.getDate(entry.getKey())));
                        }
                        break;
                    }
                    case CLASSIFIER: {
                        if (mainEntity.getClassifier(entry.getKey()) != null) {
                            MetaCategoryId classifierMetaCategoryId = (MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null);
                            saveClassifier(classifierMetaCategoryId, mainEntity.getClassifier(entry.getKey()));
                            preparedStatement.setInt(index++, mainEntity.getClassifier(entry.getKey()).getId());
                        } else {
                            preparedStatement.setNull(index++, Types.INTEGER);
                        }
                        break;
                    }
                }
            }
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                mainEntity.setId(generatedKeys.getInt(Statement.RETURN_GENERATED_KEYS));
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
                        preparedStatement.setObject(index++, mainEntity.getInt(entry.getKey()), JDBCType.INTEGER);
                        break;
                    }
                    case BOOLEAN: {
                        preparedStatement.setObject(index++, mainEntity.getBoolean(entry.getKey()), JDBCType.BOOLEAN);
                        break;
                    }
                    case STRING: {
                        preparedStatement.setObject(index++, mainEntity.getString(entry.getKey()), JDBCType.NVARCHAR);
                        break;
                    }
                    case LONG: {
                        preparedStatement.setObject(index++, mainEntity.getLong(entry.getKey()), JDBCType.BIGINT);
                        break;
                    }
                    case BIG_DECIMAL: {
                        preparedStatement.setObject(index++, mainEntity.getBigDecimal(entry.getKey()), JDBCType.DECIMAL);
                        break;
                    }
                    case DATE: {
                        if (mainEntity.getDate(entry.getKey()) == null) {
                            preparedStatement.setNull(index++, Types.DATE);
                        } else {
                            preparedStatement.setDate(index++, Date.valueOf(mainEntity.getDate(entry.getKey())));
                        }
                        break;
                    }
                    case CLASSIFIER: {
                        if (mainEntity.getClassifier(entry.getKey()) != null) {
                            MetaCategoryId classifierMetaCategoryId = (MetaCategoryId) MetaCategoryProvider.class.getMethod("get" + entry.getKey()).invoke(null);
                            saveClassifier(classifierMetaCategoryId, mainEntity.getClassifier(entry.getKey()));
                            preparedStatement.setInt(index++, mainEntity.getClassifier(entry.getKey()).getId());
                        } else {
                            preparedStatement.setNull(index++, Types.INTEGER);
                        }
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
