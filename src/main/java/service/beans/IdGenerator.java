package service.beans;

/**
 * Created by Erik on 03-Dec-17.
 */
public class IdGenerator {
    private int id;

    public IdGenerator() {
        id = -1;
    }

    public int getId() {
        return id--;
    }
}
