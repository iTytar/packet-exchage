package net.tyt.packet.core.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.tyt.packet.core.domain.Packet;
import net.tyt.packet.core.domain.PacketState;
import net.tyt.packet.core.domain.PacketStateEnum;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tyt
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
//@DataJpaTest
public class PacketStateRepositoryTest {
    @Autowired
    private PacketStateRepository packetRepository;

    /**
     * Test of getPacketsByState method, of class PacketServiceImpl.
     */
    @Test
    public void testfindByPacketId() {
        System.out.println("findByPacketId");
        Packet packet = new Packet();
        packet.initId();
        List<PacketState> expResult = new ArrayList();
//        expResult.add(packetService.createOutgoingPacket("domain", "payload"));
        Iterable<PacketState> result = packetRepository.findByPacketId(packet.getId());
        assertEquals(expResult, result);
    }
}
