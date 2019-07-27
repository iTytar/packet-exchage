package net.tyt.packet.core.impexp;

import java.util.List;

/**
 *
 * @author Игорь Тытарь
 */
public interface Importer<T> {
    List<String> receivedIds();
    T get(String id);
    void commit(String id);
}
