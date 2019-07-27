package net.tyt.packet.dto;

import java.util.List;

/**
 *
 * @author Игорь Тытарь
 */
public class StringList extends ObjectList<String> {
    public StringList() {
        super();
    }
    public StringList(List<String> list) {
        super(list);
    }
}
