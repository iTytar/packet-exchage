package net.tyt.packet.controllers;

import java.net.URI;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import net.tyt.packet.core.domain.Packet;
import net.tyt.packet.core.domain.PacketStateEnum;
import net.tyt.packet.dto.PacketList;
import net.tyt.packet.dto.StringList;

import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.Assert.*;

/**
 *
 * @author Игорь Тытарь
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InPacketControllerTest extends AbstractPacketControllerTest {

    private final String url = "/in/packets";

    /**
     * Test of toSend method, of class OutPacketController.
     */
    @Test
    public void testGetReceivedPacketIds() {
        System.out.println("testGetReceivedPacketIds...");

        byte[] payload = "payload".getBytes();
        String domain = "domain";
        
        createPacket(true, domain, payload);
        createPacket(true, domain, payload);
        createPacket(true, domain, payload);
        
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("domain", domain);
        URI uri = builder.build().toUri();

        System.out.println("getReceivedPackets URI: "+uri);

        StringList result = restTemplate.getForObject(uri, StringList.class);
        
        assertEquals(3,result.getList().size());
    }

    @Test
    public void testGetPayload() {
        System.out.println("testGetPayload...");

        byte[] payload = "payload".getBytes();
        String domain = "domain";

        Packet p = createPacket(true, domain, payload);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .path("/{id}")
                .path("/payload");
        URI uri = builder.buildAndExpand(Collections.singletonMap("id", p.getId())).toUri();

        System.out.println("testGetPayload URI: "+uri);

        byte[] result = restTemplate.getForObject(uri, byte[].class);
        
        assertArrayEquals(payload, result);
    }

    @Test
    public void testMarkAsReaded() {
        System.out.println("testMarkAsReaded...");

        PacketStateEnum expected = PacketStateEnum.PROCED;

        byte[] payload = "payload".getBytes();
        String domain = "domain";

        Packet p = createPacket(true, domain, payload);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .path("/{id}")
                .path("/proced");
        URI uri = builder.buildAndExpand(Collections.singletonMap("id", p.getId())).toUri();

        System.out.println("testGetPayload URI: "+uri);

        restTemplate.put(uri,null);

        p = getPacket(p.getId());
        
        assertEquals(expected, p.getCurrentState().getState());
        
    }

}
