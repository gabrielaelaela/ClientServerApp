package utilities.organizations;

import utilities.exceptions.WrongInputException;

public class Coordinates {
    private Float x; //Значение поля должно быть больше -893, Поле не может быть null
    private double y;

    public Coordinates(Float x, double y) {
        if (x == null || (x <= -893)) {
            throw new WrongInputException("Wrong x coordinate. The value may not be null and should be bigger then -893");
        }

        this.x = x;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public Float getX() {
        return x;
    }

    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }
}
