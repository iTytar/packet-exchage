package net.tyt.packet.core.services;

import java.util.List;
import net.tyt.packet.core.domain.Packet;

/**
 * @author tyt
 */
public interface InPacketService {
    List<String> getReceivedIds(String domain);
    Packet markAsReaded(String packetId);
    Packet getPacket(String packetId);
    byte[] getPayload(String packetId);
}
