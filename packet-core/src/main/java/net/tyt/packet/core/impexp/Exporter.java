package net.tyt.packet.core.impexp;

/**
 *
 * @author Игорь Тытарь
 */
public interface Exporter<T> {
    void send(T item);
}
