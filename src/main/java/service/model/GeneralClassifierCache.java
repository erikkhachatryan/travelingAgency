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

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private List<Classifier> countries;

    public List<Classifier> loadCountries() {
        if (countries == null) {
            countries = loadClassifiers(MetaCategoryProvider.getCountry());
        }
        return countries;
    }

    private List<MainEntity> locations;

    public List<MainEntity> loadLocations() {
        if (locations == null) {
            locations = loadMainEntities(MetaCategoryProvider.getLocation());
        }
        return locations;
    }

    private List<Classifier> allUsers;

    public List<Classifier> loadUsers() {
        if (allUsers == null) {
            allUsers = loadClassifiers(MetaCategoryProvider.getUser());
        }
        return allUsers;
    }

    public MainEntity loadMainEntityByInstanceId(MetaCategoryId metaCategoryId, @Nonnull Integer instanceId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        MainEntityImpl mainEntity = null;
        try (Connection connection = getDataSource().getConnection()) {
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
        try (Connection connection = getDataSource().getConnection()) {
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
        try (Connection connection = getDataSource().getConnection()) {
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
        try (Connection connection = getDataSource().getConnection()) {
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
    }

    private void insertClassifier(MetaCategoryId metaCategoryId, Classifier classifier) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = getDataSource().getConnection()) {
            StringBuilder insertIntoPart = new StringBuilder("INSERT INTO C_" + metaCategoryId.getTableName() + " (");
            StringBuilder valuesPart = new StringBuilder(" VALUES(");
            boolean isFirst = true;
            for (String key : metaCategoryId.getColumns().keySet()) {
                if (isFirst) {
                    insertIntoPart.append(key);
                    valuesPart.append("?");
                    isFirst = false;
                } else {
                    insertIntoPart.append(", ").append(key);
                    valuesPart.append(", ?");
                }
            }
            insertIntoPart.append(") ");
            valuesPart.append(")");
            preparedStatement = connection.prepareStatement(insertIntoPart.toString() + valuesPart.toString());
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
                }
            }
            preparedStatement.execute();
        } catch (SQLException e) {
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
        try (Connection connection = getDataSource().getConnection()) {
            StringBuilder stringBuilder = new StringBuilder("UPDATE C_" + metaCategoryId.getTableName() + " SET ");
            boolean isFirst = true;
            for (String key : metaCategoryId.getColumns().keySet()) {
                if (isFirst) {
                    stringBuilder.append(key).append(" = ?");
                    isFirst = false;
                } else {
                    stringBuilder.append(", ").append(key).append(" = ?");
                }
            }
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
                }
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (Exception e) {
            }
        }
    }

    private void saveMainEntity() {

    }

}
