package net.tyt.packet.core.services;

import net.tyt.packet.core.domain.Packet;
import net.tyt.packet.core.domain.PacketState;

/**
 * @author tyt
 */
public interface OutPacketService {
    Packet toSendPacket(String domain, byte[] payload, String refId);
    PacketState getPacketState(String id);
}
