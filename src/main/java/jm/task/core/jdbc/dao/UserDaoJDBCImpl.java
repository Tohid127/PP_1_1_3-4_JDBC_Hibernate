package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age INT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE users")) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate("DROP TABLE users");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("INSERT INTO users (name, lastName, age) VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM users where id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate("DELETE FROM users where id=id");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "select * from users";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "TRUNCATE TABLE users")) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate("TRUNCATE TABLE users");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
