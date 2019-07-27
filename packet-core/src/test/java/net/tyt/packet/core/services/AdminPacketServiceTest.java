package net.tyt.packet.core.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;
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
        //(propagation = Propagation.NOT_SUPPORTED)
public class AdminPacketServiceTest {
    @Autowired
    private AdminPacketService packetService;
    
    private final byte[] payload = "payload".getBytes();

    /**
     * Test of getPacketsByState method, of class PacketServiceImpl.
     */
    @Test
    public void testGetPacketsByState() {
        System.out.println("getPacketsByState");
        List<Packet> expResult = new ArrayList();
        expResult.add(packetService.createPacket(false, "domain", payload, null));
        Iterable<Packet> result = packetService.getPacketsByState(PacketStateEnum.PLACED);
        assertEquals(expResult, result);
    }
    /**
     * Test of getPacketsByState method, of class PacketServiceImpl.
     */
    @Test
    public void testGetPacketsByStateRecieved() {
        System.out.println("testGetPacketsByStateRecieved...");
        final String domain = "domain";
        List<Packet> expResult = new ArrayList();
        expResult.add(packetService.createPacket(true, domain, payload, null));
        expResult.add(packetService.createPacket(true, domain, payload, null));
        expResult.add(packetService.createPacket(true, domain, payload, null));
        packetService.createPacket(false, "domain", payload, null);
        packetService.createPacket(true, "domain2", payload, null);

        Iterable<Packet> result = packetService.getPackets(domain, PacketStateEnum.RECEIVED);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllPackets method, of class PacketServiceImpl.
     */
    @Test
    public void testGetAllPackets() {
        System.out.println("getAllPackets");
        List<Packet> expResult = new ArrayList();
        expResult.add(packetService.createPacket(false, "domain1", payload, null));
        expResult.add(packetService.createPacket(true, "domain2", payload, null));
        Iterable<Packet> result = packetService.getAllPackets();
        assertEquals(expResult, result);
    }

    /**
     * Test of createIncommingPacket method, of class PacketServiceImpl.
     */
    @Test
    public void testCreateIncommingPacket() {
        System.out.println("createIncommingPacket");
        String domain = "domain";
        Packet result = packetService.createPacket(true, domain, payload, null);
        assertEquals(domain, result.getDomain());
        assertArrayEquals(payload, result.getPayload());
        assertEquals(true, result.isIncomming());
    }

    /**
     * Test of createOutgoingPacket method, of class PacketServiceImpl.
     */
    @Test
    public void testCreateOutgoingPacket() {
        System.out.println("createOutgoingPacket");
        String domain = "domain";
        Packet result = packetService.createPacket(false, domain, payload, null);
        assertEquals(domain, result.getDomain());
        assertArrayEquals(payload, result.getPayload());
        assertEquals(false, result.isIncomming());
    }

    /**
     * Test of process method, of class PacketServiceImpl.
     */
    @Test
    public void testUpdateState() {
        System.out.println("updateState");
        Packet packet = packetService.createPacket(true, "domain", payload, null);
        packet = packetService.updateState(packet.getId(), PacketStateEnum.DELIVERED);
        assertEquals(PacketStateEnum.DELIVERED, packet.getCurrentState().getState());
    }

    /**
     * Test of getPacket method, of class PacketServiceImpl.
     */
    @Test
    public void testGetPacket() {
        System.out.println("getPacket");
        Packet expResult = packetService.createPacket(true, "domain", payload, null);
        Packet result = packetService.getPacket(expResult.getId());
        assertEquals(expResult, result);
    }

    /**
     * Test of getPacketStates method, of class PacketServiceImpl.
     */
    @Test
    public void testGetPacketStates() {
        System.out.println("getPacketStates");
        List<PacketState> expResult = new ArrayList();
        Packet packet = packetService.createPacket(true, "domain", payload, null);
        expResult.add(packet.getCurrentState());
        packetService.updateState(packet.getId(), PacketStateEnum.DELIVERED);
        expResult.add(packet.getCurrentState());
        
        Iterable<PacketState> result = packetService.getPacketStates(packet.getId());

        assertEquals(2L,StreamSupport.stream(result.spliterator(),false).count());
        assertEquals(expResult, result);
    }
    
}
