package utilities.organizations;

import utilities.users.User;
import utilities.exceptions.WrongInputException;
import utilities.idobjects.IdObject;

import java.time.LocalDate;

public class Organization implements IdObject, Comparable<IdObject> {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long annualTurnover; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле может быть null
    private Address postalAddress; //Поле не может быть null
    private String username;

    public Organization(String name, Coordinates coordinates, long annualTurnover, OrganizationType type, Address postalAddress, String username) {
        this.id = (int) (Math.random()*2000000000);
        setName(name);
        setCoordinates(coordinates);
        creationDate = LocalDate.now();
        setAnnualTurnover(annualTurnover);
        setOrganizationType(type);
        setPostalAddress(postalAddress);
        setUsername(username);
    }

    public void setCreationDate(java.time.LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new WrongInputException("Wrong name of organization");
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) throw new WrongInputException("Null coordinates");
        this.coordinates = coordinates;
    }

    public void setAnnualTurnover(long annualTurnover) {
        if (annualTurnover <= 0) throw new WrongInputException("Annual turnover should be bigger than 0");
        this.annualTurnover = annualTurnover;
    }

    public void setOrganizationType(OrganizationType type) {
        if (type == null) throw new WrongInputException("Organization type may not be null");
        this.type = type;
    }

    public void setPostalAddress(Address postalAddress) {
        if (postalAddress == null) throw new WrongInputException("Postal address may not be null");
        this.postalAddress = postalAddress;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return this.id;
    }

    public long getAnnualTurnover() {
        return annualTurnover;
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {return this.name;};

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public java.time.LocalDate getCreationDate() {return this.creationDate;}

    public String getType() {return this.type.toString();}

    public Address getPostalAddress() {
        return postalAddress;
    }

    public String toString() {
        return "===\nid: " + this.id + "\nOrganization name: " + this.name + "\nPostal address: " + this.postalAddress + "\nCoordinates: " + coordinates.toString() + "\nAnnual turnover: " + annualTurnover + "\ncreation date: " + creationDate + "\n===";
    }

    @Override
    public int compareTo(IdObject o) {
        if (this == o) {
            return 0;
        }
        if (o == null) {
            return 1;
        }
        return this.getId()-o.getId();
    }

    public enum OrganizationType {
        PUBLIC,
        PRIVATE_LIMITED_COMPANY,
        OPEN_JOINT_STOCK_COMPANY
    }
}
