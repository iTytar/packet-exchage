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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tyt
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PacketRepositoryTest {
    @Autowired
    private PacketRepository packetRepository;

    /**
     * Test of getPacketsByState method, of class PacketServiceImpl.
     */
    @Test
    public void testFindByCurrentStateState() {
        System.out.println("findByCurrentStateState");
        List<Packet> expResult = new ArrayList();
//        expResult.add(packetService.createOutgoingPacket("domain", "payload"));
        Iterable<Packet> result = packetRepository.findByCurrentStateState(PacketStateEnum.PLACED);
        assertEquals(expResult, result);
    }
}
