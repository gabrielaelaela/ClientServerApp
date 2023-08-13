package utilities.upgradedcollections;

import utilities.exceptions.WrongClassException;
import utilities.idobjects.IdObject;

import java.time.LocalDate;
import java.util.PriorityQueue;
import java.util.Queue;

public class UpgradedPriorityQueue<T> extends PriorityQueue<T> implements Queue<T> {
    private java.time.LocalDate creationDate;

    public UpgradedPriorityQueue() {
        super();
        this.creationDate = LocalDate.now();
    }

    public java.time.LocalDate getCreationDate() {return this.creationDate;}

    public IdObject getElementById(int id) throws WrongClassException {
        if (!(element() instanceof IdObject)) throw new WrongClassException("Collection's objects do not have id");
        for (T priorityQueueObject: this) {
            IdObject idObject = (IdObject) priorityQueueObject;
            if (idObject.getId() == id) return idObject;
        }
        return null;
    }
}
