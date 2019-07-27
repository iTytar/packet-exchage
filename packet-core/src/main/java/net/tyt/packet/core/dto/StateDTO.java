package net.tyt.packet.core.dto;

import lombok.Data;
import net.tyt.packet.core.domain.PacketState;
import net.tyt.packet.core.domain.PacketStateEnum;

/**
 *
 * @author Игорь Тытарь
 */
@Data
public class StateDTO implements Identifiable {
    private String id;
    private String packetId;
    private String state;
    
    public static StateDTO of(PacketState ps) {
        StateDTO dto = new StateDTO();
        dto.setId(ps.getId());
        dto.setState(ps.getState().toString());
        dto.setPacketId(ps.getPacket().getId());
        return dto;
    }
    public static StateDTO of(String id, String packetId, PacketStateEnum state) {
        StateDTO dto = new StateDTO();
        dto.setId(id);
        dto.setPacketId(packetId);
        dto.setState(state.toString());
        return dto;
    }
}
