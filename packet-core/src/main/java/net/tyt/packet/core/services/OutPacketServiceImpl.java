package net.tyt.packet.core.services;

import lombok.RequiredArgsConstructor;
import net.tyt.packet.core.domain.Packet;
import net.tyt.packet.core.domain.PacketState;
import org.springframework.stereotype.Service;

/**
 *
 * @author tyt
 */
@Service
@RequiredArgsConstructor
public class OutPacketServiceImpl implements OutPacketService {
    private final AdminPacketService service;

    @Override
    public Packet toSendPacket(String domain, byte[] payload, String refId) {
        return service.createPacket(false, domain, payload, refId);
    }

    @Override
    public PacketState getPacketState(String id) {
        return service.getPacket(id).getCurrentState();
    }
}
