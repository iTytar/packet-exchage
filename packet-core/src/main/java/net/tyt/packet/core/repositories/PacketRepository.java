package net.tyt.packet.core.repositories;

import java.util.List;
import net.tyt.packet.core.domain.Packet;
import net.tyt.packet.core.domain.PacketStateEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author tyt
 */
public interface PacketRepository extends CrudRepository<Packet, String> {

    List<Packet> findByCurrentStateState(PacketStateEnum state);

    List<Packet> findByDomainAndCurrentStateState(String domain, PacketStateEnum state);

    List<Packet> findByDomain(String domain);

    @Query("select p.id from Packet p join p.currentState s where s.state = :state")
    List<String> findIdsByCurrentStateState(@Param("state") PacketStateEnum state);

    @Query("select p.id from Packet p join p.currentState s where p.domain = :domain and s.state = :state")
    List<String> findIdsByDomainAndCurrentStateState(@Param("domain") String domain, @Param("state") PacketStateEnum state);

    @Query("select p.id from Packet p where p.domain = :domain")
    List<String> findIdsByDomain(@Param("domain") String domain);

    @Query("select p.id from Packet p")
    List<String> findAllIds();
    
    long deleteByCurrentStateState(PacketStateEnum state);
}
