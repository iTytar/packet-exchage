package net.tyt.packet.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 *
 * @author Игорь Тытарь
 * @param <T>
 */
public class ObjectList<T> {
    private final List<T> list;
    
    public ObjectList() {
       list = new ArrayList<>(); 
    }
    public ObjectList(Iterable<T> packets) {
        this.list = StreamSupport.stream(packets.spliterator(),false).collect(Collectors.toList());
    }
    public ObjectList(List<T> list) {
        this.list = new ArrayList<>(list);
    }
    public List<T> getList() {
        return list;
    }
}
