package service.model;

import service.dao.DataSource;
import service.dao.UserDao;
import service.util.MetaCategoryId;
import service.util.MetaCategoryType;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    private HashMap<String, Classifier> locations;

    public Map<String, Classifier> loadLocations() {
        if (locations == null) {
            locations = new HashMap<>();
            Classifier location1 = new ClassifierImpl(1);
            location1.setName("Yerevan");
            locations.put("Yerevan", location1);
            location1 = new ClassifierImpl(2);
            location1.setName("Spitak");
            locations.put("Spitak", location1);
        }
        return locations;
    }

    private List<Classifier> allUsers;

    public List<Classifier> loadUsers() {
        if (allUsers == null) {
            allUsers = UserDao.loadUsers();
        }
        return allUsers;
    }

    public void saveUser(Classifier user) {
        if (user.getId() == -1) {
            UserDao.insertUser(user);
        } else {
            UserDao.updateUser(user);
        }
        allUsers = null;
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
                        default: { //DATE
                            mainEntity.put(entry.getKey(), resultSet.getDate(entry.getKey()));
                            break;
                        }
                    }
                }
            }
        } catch (SQLException e) {
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
                        default: { //DATE
                            mainEntity.put(entry.getKey(), resultSet.getDate(entry.getKey()));
                            break;
                        }
                    }
                }
                mainEntities.add(mainEntity);
            }
        } catch (SQLException e) {
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
                        default: { //DATE
                            classifier.put(entry.getKey(), resultSet.getDate(entry.getKey()));
                            break;
                        }
                    }
                }
            }
        } catch (SQLException e) {
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

    public List<Classifier> loadClassifier(MetaCategoryId metaCategoryId, @Nonnull Integer id) {
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
                        default: { //DATE
                            classifier.put(entry.getKey(), resultSet.getDate(entry.getKey()));
                            break;
                        }
                    }
                }
                classifiers.add(classifier);
            }
        } catch (SQLException e) {
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

}
