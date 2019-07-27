package net.tyt.packet.controllers;

import java.net.URI;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import net.tyt.packet.core.domain.Packet;
import net.tyt.packet.core.domain.PacketStateEnum;
import net.tyt.packet.dto.PacketList;
import net.tyt.packet.dto.PacketStateList;

import static org.junit.Assert.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Игорь Тытарь
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminPacketControllerTest extends AbstractPacketControllerTest{

    private final String url = "/admin/packets";
    
    /**
     * Test of toSend method, of class OutPacketController.
     */
    @Test
    public void testCreatePacket() {
        System.out.println("testCreatePacket...");

        byte[] payload = "payload".getBytes();
        boolean incomming = true;
        String domain = "domain";

        Packet result = createPacket(incomming, domain, payload);
    }

    /**
     * Test of toSend method, of class OutPacketController.
     */
    @Test
    public void testCreatePacketWithRefId() {
        System.out.println("testCreatePacketWithRefId...");

        byte[] payload = "payload".getBytes();
        boolean incomming = false;
        String domain = "domain";
        String refId = "refId";

        Packet result = createPacket(incomming, domain, payload, refId);
    }

    @Test
    public void testUpdateState() {
        System.out.println("testUpdateState...");

        byte[] payload = "payload".getBytes();
        boolean incomming = true;
        String domain = "domain";
        PacketStateEnum state = PacketStateEnum.PROCED;

        Packet p = createPacket(incomming, domain, payload);

        updateState(p.getId(), state);

        Packet result = getPacket(p.getId());

        assertEquals(state, result.getCurrentState().getState());
    }

    @Test
    public void testGetPacket() {
        System.out.println("testGetPacket...");

        byte[] payload = "payload".getBytes();
        boolean incomming = true;
        String domain = "domain";

        Packet p = createPacket(incomming, domain, payload);

        Packet result = getPacket(p.getId());

        assertEquals(p.isIncomming(), result.isIncomming());
        assertEquals(p.getDomain(), result.getDomain());
        assertEquals(p.getRefId(), result.getRefId());
        assertEquals(p.getCurrentState().getState(), result.getCurrentState().getState());
        assertArrayEquals(p.getPayload(), result.getPayload());
    }
    
    @Test
    public void testGetPacketStates() {
        System.out.println("testGetPacketStates...");

        byte[] payload = "payload".getBytes();
        boolean incomming = false;
        String domain = "domain";

        Packet p = createPacket(incomming, domain, payload);

        updateState(p.getId(), PacketStateEnum.SENT);
        updateState(p.getId(), PacketStateEnum.DELIVERED);
        updateState(p.getId(), PacketStateEnum.PROCED);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .path("/{id}/states");
        URI uri = builder.buildAndExpand(Collections.singletonMap("id", p.getId()))
                .toUri();

        System.out.println("updateState URI: " + uri);

        PacketStateList result = restTemplate.getForObject(uri, PacketStateList.class);

        assertEquals(4, result.getList().size());
    }

    @Test
    public void testGetPacketsByState() {
        System.out.println("testGetPacketsByState...");

        byte[] payload = "payload".getBytes();
        boolean incomming = false;
        String domain = "domain";

        createPacket(incomming, domain, payload);
        createPacket(incomming, domain, payload);
        createPacket(incomming, domain, payload);
        createPacket(incomming, domain, payload);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("state", PacketStateEnum.PLACED);
        URI uri = builder.build()
                .toUri();

        System.out.println("getPacketsByState URI: " + uri);

        PacketList result = restTemplate.getForObject(uri, PacketList.class);

        assertEquals(4, result.getList().size());
    }
}
