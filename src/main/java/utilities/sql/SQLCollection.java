package utilities.sql;

import utilities.factories.OrganizationFactory;
import utilities.iowork.ConsolePrint;
import utilities.organizations.Organization;
import utilities.upgradedcollections.UpgradedPriorityQueue;

import java.sql.*;
import java.util.ArrayList;

public class SQLCollection {

    private static Connection connection;
    private static ConsolePrint consolePrint = new ConsolePrint();

    public static void setup() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/lab7";
        try {
            connection = DriverManager.getConnection(jdbcURL, "postgres", "2390");
            consolePrint.println("Connected to PostgreSQL server");
        } catch (SQLException e) {
            consolePrint.println("Error connecting to PostgreSQL server");
            e.printStackTrace();
        }
    }

    public static synchronized boolean checkPassword(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String user = resultSet.getString("username");
            if (user.equals(username)) {
                String pass = resultSet.getString("password");
                if (pass.equals(password)) return true;
                else return false;
            }
        }
        return false;
    }

    public static synchronized boolean containsUser(String username) throws SQLException {
        String sql = "SELECT * FROM users";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String user = resultSet.getString("username");
            if (user.equals(username)) return true;
        }

        return false;
    }

    public static synchronized void addUser(String username, String password) throws SQLException {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.executeUpdate();
    }

    public static synchronized UpgradedPriorityQueue<Organization> getCollection() throws SQLException {
        UpgradedPriorityQueue upgradedPriorityQueue = new UpgradedPriorityQueue();

        String sql = "SELECT * FROM organization";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            upgradedPriorityQueue.add(OrganizationFactory.buildFromDatabase(resultSet));
        }

        return upgradedPriorityQueue;
    }

    public static synchronized void addToCollection(Organization organization) throws SQLException {
        String sql = "INSERT INTO organization (name, x, y, creation_date, annual_turnover, type, zipcode, username_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, organization.getName());
        statement.setFloat(2, organization.getCoordinates().getX());
        statement.setDouble(3, organization.getCoordinates().getY());
        statement.setDate(4, Date.valueOf(organization.getCreationDate()));
        statement.setLong(5, organization.getAnnualTurnover());
        statement.setString(6, organization.getType());
        statement.setString(7, organization.getPostalAddress().getZipCode());
        statement.setInt(8, getUserId(organization.getUsername()));

        statement.executeUpdate();
    }

    public static synchronized void clearCollection() throws SQLException {
        String sql = "DELETE FROM organization";
        Statement statement = connection.createStatement();
        statement.executeQuery(sql);
    }

    public static synchronized void clearUsersCollection(String username) throws SQLException {
        String sql = "DELETE FROM organization WHERE username_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        String sql_users = "SELECT * FROM users WHERE user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql_users);
        preparedStatement.setInt(1, getUserId(username));
        statement.executeUpdate();
    }

    public static synchronized ArrayList<Long> getAnnualTurnovers() throws SQLException {
        ArrayList<Long> listOfAnnualTurnovers = new ArrayList<>();

        String sql = "SELECT * FROM organization";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            listOfAnnualTurnovers.add(resultSet.getLong("annual_turnover"));
        }

        return listOfAnnualTurnovers;
    }

    public static synchronized int getNumberOfElements() throws SQLException {
        String sql = "SELECT COUNT(*) FROM organization";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    public static synchronized void removeById(int id) throws SQLException {
        String sql = "DELETE FROM TABLE WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
    }

    public static synchronized String getUsername(int id) throws SQLException {
        String sql = "SELECT username_id FROM organization WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int user_id = resultSet.getInt(1);
        String username = getUsernameById(user_id);
        return username;
    }

    public static synchronized void changeObject(Organization organization) throws SQLException {
        String sql = "UPDATE organization SET (name, x, y, creation_date, annual_turnover, type, zipcode, user_id) = (?, ?, ?, ?, ?, ?, ?, ?)  WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, organization.getName());
        statement.setFloat(2, organization.getCoordinates().getX());
        statement.setDouble(3, organization.getCoordinates().getY());
        statement.setDate(4, Date.valueOf(organization.getCreationDate()));
        statement.setLong(5, organization.getAnnualTurnover());
        statement.setString(6, organization.getType());
        statement.setString(7, organization.getPostalAddress().getZipCode());
        statement.setString(8, organization.getUsername());
        statement.setInt(9, getUserId(organization.getUsername()));
        statement.executeQuery();
    }

    public static int getUserId(String username) throws SQLException {
        String sql_users = "SELECT * FROM users WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql_users);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }

    public static String getUsernameById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getString("username");
    }
}
