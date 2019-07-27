package net.tyt.packet.core.services;

import java.util.List;
import net.tyt.packet.core.domain.Packet;
import net.tyt.packet.core.domain.PacketState;
import net.tyt.packet.core.domain.PacketStateEnum;

/**
 * @author tyt
 */
public interface AdminPacketService {
    Packet createPacket(boolean incomming, String domain, byte[] payload, String refId);

    Packet save(Packet packet);

    Packet updateState(String packetId, PacketStateEnum state);

    Packet getPacket(String id);
    List<PacketState> getPacketStates(String packetId);
    List<Packet> getPacketsByState(PacketStateEnum state);
    List<String> getPacketIdsByState(PacketStateEnum state);
    List<Packet> getPacketsByDomain(String domain);
    List<String> getPacketIdsByDomain(String domain);
    List<Packet> getPackets(String domain, PacketStateEnum state);
    List<String> getPacketIds(String domain, PacketStateEnum state);

    void deletePacket(String id);
    long deleteProcedPackets();

    List<Packet> getAllPackets();
    long deleteAllPackets();
}
