package service.beans;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Erik on 03-Dec-17.
 */
public class IdGenerator {
    private AtomicInteger ID_GENERATOR;

    public IdGenerator() {
        ID_GENERATOR = new AtomicInteger(-1);
    }

    public int getId() {
        return ID_GENERATOR.getAndDecrement();
    }
}
