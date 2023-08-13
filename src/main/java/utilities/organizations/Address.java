package utilities.organizations;

import utilities.exceptions.WrongInputException;

public class Address {
    private String zipCode; //Длина строки должна быть не меньше 4, Поле не может быть null

    public Address(String zipcode) {
        if ((zipcode.equals(null)) || (zipcode.length() < 4)) {
            throw new WrongInputException("Wrong zipcode. Zipcode may not be null or shorter than 4 symbols");
        }
        this.zipCode = zipcode;
    }

    public String getZipCode() {return this.zipCode;}

    public String toString() {
        return this.zipCode;
    }
}
