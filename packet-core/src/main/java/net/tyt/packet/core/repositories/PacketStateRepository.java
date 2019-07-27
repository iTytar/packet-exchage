package net.tyt.packet.core.repositories;

import java.util.List;
import net.tyt.packet.core.domain.PacketState;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author tyt
 */
public interface PacketStateRepository extends CrudRepository<PacketState, String> {
    List<PacketState> findByPacketId(String packetId);
    long deleteByPacketId(String packetId);
}
