package net.tyt.packet.core.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.tyt.packet.core.domain.Packet;
import net.tyt.packet.core.domain.PacketStateEnum;
import org.springframework.stereotype.Service;

/**
 *
 * @author tyt
 */
@Service
@RequiredArgsConstructor
public class InPacketServiceImpl implements InPacketService {
    private final AdminPacketService service;

    @Override
    public List<String> getReceivedIds(String domain) {
        return service.getPacketIds(domain, PacketStateEnum.RECEIVED);
    }

    @Override
    public byte[] getPayload(String packetId) {
        return service.getPacket(packetId).getPayload();
    }

    @Override
    public Packet markAsReaded(String packetId) {
        return service.updateState(packetId, PacketStateEnum.PROCED);
    }

    @Override
    public Packet getPacket(String packetId) {
        return service.getPacket(packetId);
    }

}
