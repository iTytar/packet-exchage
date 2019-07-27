package net.tyt.packet.dto;

import java.util.List;
import net.tyt.packet.core.domain.PacketState;

/**
 *
 * @author Игорь Тытарь
 */
public class PacketStateList extends ObjectList<PacketState> {
    public PacketStateList() {
        super();
    }
    public PacketStateList(List<PacketState> list) {
        super(list);
    }
}
