package net.tyt.packet.dto;

import java.util.List;
import net.tyt.packet.core.domain.Packet;

/**
 *
 * @author Игорь Тытарь
 */
public class PacketList extends ObjectList<Packet> {
    public PacketList() {
        super();
    }
    public PacketList(List<Packet> packets) {
        super(packets);
    }
    public PacketList(Iterable<Packet> packets) {
        super(packets);
    }
}
