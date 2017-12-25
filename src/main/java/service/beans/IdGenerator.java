package service.beans;

import service.util.MetaCategoryId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Erik on 03-Dec-17.
 */
public class IdGenerator {
    private Map<MetaCategoryId, AtomicInteger> ID_GENERATORS;

    public IdGenerator() {
        ID_GENERATORS = new HashMap<>();
    }

    public AtomicInteger getIdGenerator(MetaCategoryId metaCategoryId) {
        ID_GENERATORS.putIfAbsent(metaCategoryId, new AtomicInteger(-1));
        return ID_GENERATORS.get(metaCategoryId);
    }

    public Integer getNextId(MetaCategoryId metaCategoryId) {
        return getIdGenerator(metaCategoryId).getAndDecrement();
    }
}
