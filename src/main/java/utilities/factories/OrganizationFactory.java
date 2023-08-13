package utilities.factories;

import client.Client;
import server.Server;
import utilities.iowork.ConsolePrint;
import utilities.iowork.Printable;
import utilities.iowork.Scannable;
import utilities.organizations.Address;
import utilities.organizations.Coordinates;
import utilities.organizations.Organization;
import utilities.sql.SQLCollection;
import utilities.users.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;

public class OrganizationFactory implements ObjectsFactory {
    Printable printable;
    Scannable scannable;

    public OrganizationFactory(Printable printable, Scannable scannable) {
        this.printable = printable;
        this.scannable = scannable;
    }

    public static Object buildFromDatabase(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        Float x = resultSet.getFloat("x");
        double y = resultSet.getDouble("y");
        Coordinates coordinates = new Coordinates(x, y);
        long annualTurnover = resultSet.getLong("annual_turnover");
        String type = resultSet.getString("type").toUpperCase(Locale.ROOT);
        Organization.OrganizationType organizationType;
        switch (type) {
            case "PUBLIC":
                organizationType = Organization.OrganizationType.PUBLIC;
                break;
            case "PRIVATE_LIMITED_COMPANY":
                organizationType = Organization.OrganizationType.PRIVATE_LIMITED_COMPANY;
                break;
            case "OPEN JOINT STOCK COMPANY":
                organizationType = Organization.OrganizationType.OPEN_JOINT_STOCK_COMPANY;
                break;
            default:
                new ConsolePrint().println("Wrong organization type in the SQL database. Please, check it");
                return null;
        }

        Address postalAddress = new Address(resultSet.getString("zipcode"));
        String username = SQLCollection.getUsernameById(resultSet.getInt("username_id"));

        Organization organization = new Organization(name, coordinates, annualTurnover, organizationType, postalAddress, username);
        java.time.LocalDate creationDate = resultSet.getDate("creation_date").toLocalDate();
        organization.setCreationDate(creationDate);
        organization.setId(resultSet.getInt("id"));
        return organization;
    }

    public Object buildWithQuestions() throws IOException {
        String name = setName();
        Coordinates coordinates = setCoordinates();
        long annualTurnover = setAnnualTurnover();
        Organization.OrganizationType organizationType = setOrganizationType();
        Address address = setAddress();
        return new Organization(name, coordinates, annualTurnover, organizationType, address, Client.user.getUsername());
    }

    public Object buildWithoutQuestions(User user) throws IOException {
        String name = scannable.scanLine();
        float x = scannable.scanFloat();
        int y = scannable.scanInt();
        Coordinates coordinates = new Coordinates(x, y);
        long annualTurnover = scannable.scanLong();
        Organization.OrganizationType organizationType = scanOrganizationType();
        Address address = new Address(scannable.scanLine());
        return new Organization(name, coordinates, annualTurnover, organizationType, address, user.getUsername());
    }

    private String setName() throws IOException {
        String name = null;
        while(name == null) {
            printable.print("Enter the name of organization: ");
            name = scannable.scanLine();
        }
        return name;
    }

    private Coordinates setCoordinates() throws IOException {
        float xCoordinate = -893;
        while (xCoordinate <= -893) {
            printable.print("Enter the x coordinate (above -893): ");
            xCoordinate = scannable.scanFloat();
        }
        printable.print("Enter the y coordinate: ");
        double yCoordinate = scannable.scanDouble();
        return new Coordinates(xCoordinate, yCoordinate);
    }

    private long setAnnualTurnover() throws IOException {
        long annualTurnover = 0;
        while (annualTurnover <= 0) {
            printable.print("Enter the annual turnover (above 0): ");
            annualTurnover = scannable.scanLong();
        }
        return annualTurnover;
    }

    private Organization.OrganizationType setOrganizationType() throws IOException {
        printable.print("Enter the organization type (PUBLIC/PRIVATE LIMITED COMPANY/OPEN JOINT STOCK COMPANY): ");
        String inputType = scannable.scanLine().toUpperCase();
        while (inputType.equals(""))
            inputType = scannable.scanLine().toUpperCase();

        switch (inputType) {
            case "PUBLIC":
                return Organization.OrganizationType.PUBLIC;
            case "PRIVATE LIMITED COMPANY" :
                return Organization.OrganizationType.PRIVATE_LIMITED_COMPANY;
            case "OPEN JOINT STOCK COMPANY":
                return Organization.OrganizationType.OPEN_JOINT_STOCK_COMPANY;
            default:
                return setOrganizationType();
        }
    }

    private Organization.OrganizationType scanOrganizationType() throws IOException {
        String inputType = scannable.scanLine().toUpperCase();
        while (inputType.equals(""))
            inputType = scannable.scanLine().toUpperCase();

        switch (inputType) {
            case "PUBLIC":
                return Organization.OrganizationType.PUBLIC;
            case "PRIVATE LIMITED COMPANY" :
                return Organization.OrganizationType.PRIVATE_LIMITED_COMPANY;
            case "OPEN JOINT STOCK COMPANY":
                return Organization.OrganizationType.OPEN_JOINT_STOCK_COMPANY;
            default:
                return scanOrganizationType();
        }
    }

    private Address setAddress() throws IOException {
        String zipcode = null;
        while (zipcode == null || zipcode.length() < 4) {
            printable.print("Enter the zipcode (the length should at least 4): ");
            zipcode = scannable.scanLine();
        }
        return new Address(zipcode);
    }
}
