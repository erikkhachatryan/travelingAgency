package service.model;

import service.beans.dao.DataSource;

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
            allUsers = new ArrayList<>();
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try {
                connection = dataSource.getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM C_USER");
                resultSet = preparedStatement.executeQuery();
                ClassifierImpl user;
                while (resultSet.next()) {
                    user = new ClassifierImpl(resultSet.getInt("UserID"));
                    user.setName(resultSet.getString("FirstName"));
                    user.put("lastName", resultSet.getString("LastName"));
                    user.put("dateOfBirth", resultSet.getString("DateOfBirth"));
                    user.put("phoneNumber", resultSet.getString("PhoneNumber"));
                    user.put("address", resultSet.getString("Address"));
                    user.put("email", resultSet.getString("Email"));
                    user.put("genderId", resultSet.getString("GenderID"));
                    allUsers.add(user);
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
                if (connection != null) try {
                    connection.close();
                } catch (Exception e) {
                }
            }
        }
        return allUsers;
    }

    public void insertUser(Classifier user) {

    }
}
