package net.tyt.packet.controllers;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import net.tyt.packet.core.domain.Packet;
import net.tyt.packet.core.domain.PacketStateEnum;
import net.tyt.packet.core.services.AdminPacketService;
import net.tyt.packet.dto.PacketList;
import org.junit.After;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.Assert.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Игорь Тытарь
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AbstractPacketControllerTest {

    @Autowired
    protected TestRestTemplate restTemplate;
    
    @Autowired
    protected AdminPacketService service;

    private final String url = "/admin/packets";
    
    @After
    public void after() {
        deleteAllPackets();
    }

    protected Packet createPacket(boolean incomming, String domain, byte[] payload) {
        return createPacket(incomming, domain, payload, null);
    }

    protected Packet createPacket(boolean incomming, String domain, byte[] payload, String refId) {
        PacketStateEnum state = incomming ? PacketStateEnum.RECEIVED : PacketStateEnum.PLACED;

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("incomming", incomming)
                .queryParam("domain", domain);
        if (refId != null) {
                builder = builder.queryParam("refId", refId);
        }
        URI uri = builder.build().toUri();

        System.out.println("createPacket URI: " + uri);

        Packet result = restTemplate.postForObject(uri, payload, Packet.class);
        assertNotNull("id must be not null", result.getId());
        assertEquals("invalid incomming", incomming, result.isIncomming());
        assertEquals("invalid domain", domain, result.getDomain());
        assertEquals("invalid refId", refId, result.getRefId());
        assertEquals("invalid state", state, result.getCurrentState().getState());
        assertArrayEquals("invalid payload", payload, result.getPayload());
        return result;
    }
    
    protected Packet getPacket(String id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .path("/{id}");
        URI uri = builder.buildAndExpand(Collections.singletonMap("id", id))
                .toUri();

        System.out.println("getPacket URI: " + uri);

        Packet result = restTemplate.getForObject(uri, Packet.class);
        assertEquals(id, result.getId());
        
        return result;
    }

    protected void updateState(String id, PacketStateEnum state) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .path("/{id}")
                .queryParam("state", state);
        URI uri = builder.buildAndExpand(Collections.singletonMap("id", id))
                .toUri();

        System.out.println("updateState URI: " + uri);

        restTemplate.put(uri, null);
    }

    protected List<Packet> getAllPackets() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        URI uri = builder.build()
                .toUri();

        System.out.println("getAllPackets URI: " + uri);

        PacketList result = restTemplate.getForObject(uri, PacketList.class);
        
        return result.getList();
    }

    protected void deleteAllPackets() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        URI uri = builder.build()
                .toUri();

        System.out.println("deleteAllPackets URI: " + uri);

        restTemplate.delete(uri);
    }
    
}
