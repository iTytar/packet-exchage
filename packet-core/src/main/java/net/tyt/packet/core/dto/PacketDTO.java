package net.tyt.packet.core.dto;

import lombok.Data;
import net.tyt.packet.core.domain.Packet;

/**
 *
 * @author Игорь Тытарь
 */
@Data
public class PacketDTO implements Identifiable {
    private String id;
    private String refId;
    private String domain;
    private String state;
    private byte[] payload;
    
    public static PacketDTO of(Packet p) {
        PacketDTO dto = new PacketDTO();
        dto.setId(p.getId());
        dto.setDomain(p.getDomain());
        dto.setPayload(p.getPayload());
        dto.setRefId(p.getRefId());
        dto.setState(p.getCurrentState().getState().toString());
        return dto;
    }
    
    public Packet buildPacket() {
        Packet p = new Packet();
        p.setId(getId());
        p.setCurrentState(null);
        p.setDomain(getDomain());
        p.setRefId(getRefId());
        p.setIncomming(true);
        p.setPayload(getPayload());
        return p;
    }
}
