package service.dao;

import service.model.Classifier;
import service.model.ClassifierImpl;
import service.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 10-Dec-17.
 */
public class UserDao {

    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = Util.getBean("dataSource", DataSource.class);
        }
        return dataSource;
    }

    public static List<Classifier> loadUsers() {
        List<Classifier> result = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getDataSource().getConnection()) {
            preparedStatement = connection.prepareStatement("SELECT * FROM C_User");
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
                user.put("password", "erik"); //todo remove after adding password column in db and uncomment the line bellow.
//                user.put("password", resultSet.getString("password"));
                result.add(user);
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
        return result;
    }

    public Classifier loadUserById(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ClassifierImpl user = null;
        try (Connection connection = getDataSource().getConnection()) {
            preparedStatement = connection.prepareStatement("SELECT * FROM C_User WHERE UserID = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = new ClassifierImpl(resultSet.getInt("UserID"));
                user.setName(resultSet.getString("FirstName"));
                user.put("lastName", resultSet.getString("LastName"));
                user.put("dateOfBirth", resultSet.getString("DateOfBirth"));
                user.put("phoneNumber", resultSet.getString("PhoneNumber"));
                user.put("address", resultSet.getString("Address"));
                user.put("email", resultSet.getString("Email"));
                user.put("genderId", resultSet.getString("GenderID"));
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
        return user;
    }

    public static void insertUser(Classifier user) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = getDataSource().getConnection()) {
            preparedStatement = connection.prepareStatement("INSERT INTO C_User VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, user.getString("FirstName"));
            preparedStatement.setString(2, user.getString("LastName"));
            preparedStatement.setString(3, user.getString("DateOfBirth"));
            preparedStatement.setString(4, user.getString("PhoneNumber"));
            preparedStatement.setString(5, user.getString("Address"));
            preparedStatement.setString(6, user.getString("Email"));
            preparedStatement.setString(7, user.getString("GenderID"));
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

    public static void updateUser(Classifier user) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = getDataSource().getConnection()) {
            preparedStatement = connection.prepareStatement("UPDATE C_User SET FirstName=?, LastName=?, DateOfBirth=?, PhoneNumber=?, Address=?, Email=?, GenderID=? WHERE UserID=?");
            preparedStatement.setString(1, user.getString("FirstName"));
            preparedStatement.setString(2, user.getString("LastName"));
            preparedStatement.setString(3, user.getString("DateOfBirth"));
            preparedStatement.setString(4, user.getString("PhoneNumber"));
            preparedStatement.setString(5, user.getString("Address"));
            preparedStatement.setString(6, user.getString("Email"));
            preparedStatement.setString(7, user.getString("GenderID"));
            preparedStatement.setInt(8, user.getId());
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
}
