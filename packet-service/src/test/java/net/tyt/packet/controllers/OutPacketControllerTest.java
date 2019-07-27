package net.tyt.packet.controllers;

import java.net.URI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import net.tyt.packet.core.domain.Packet;
import net.tyt.packet.core.domain.PacketState;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;

import static org.junit.Assert.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Игорь Тытарь
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OutPacketControllerTest extends AbstractPacketControllerTest {

    private final String url = "/out/packets";

    /**
     * Test of toSend method, of class OutPacketController.
     */
    @Test
    public void testToSend() {
        System.out.println("toSend");

        byte[] payload = "payload".getBytes();
        String domain = "domain";


        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("domain", domain);
        URI uri = builder.build().toUri();

        System.out.println("toSend URI: "+uri);

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(payload);

        Packet result = restTemplate.postForObject(uri, requestEntity, Packet.class);
        assertEquals(domain, result.getDomain());
        System.out.println("payload: " + new String(result.getPayload()));
        assertArrayEquals(payload, result.getPayload());

    }

    @Test
    public void testToSendWithRefId() {
        System.out.println("toSendWithRefId");

        byte[] payload = "payload".getBytes();
        String domain = "domain";
        String refId = "refId";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("domain", domain)
                .queryParam("refId", refId);
        URI uri = builder.build().toUri();

        System.out.println("toSendWithRefId URI: "+uri);

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(payload);

        Packet result = restTemplate.postForObject(uri, requestEntity, Packet.class);
        assertEquals(domain, result.getDomain());
        assertEquals(refId, result.getRefId());
        assertArrayEquals(payload, result.getPayload());
    }

    @Test
    public void testGetState() {
        System.out.println("getState");

        byte[] payload = "payload".getBytes();
        String domain = "domain";


        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("domain", domain);
        URI uri = builder.build().toUri();


        HttpEntity<byte[]> requestEntity = new HttpEntity<>(payload);

        Packet p = restTemplate.postForObject(uri, requestEntity, Packet.class);

        PacketState expected = p.getCurrentState();
        
        builder = UriComponentsBuilder.fromUriString(url).path("/{id}").path("/state");
        uri = builder.buildAndExpand(p.getId()).toUri();
        System.out.println("getState URI: "+uri);
        
        PacketState result = restTemplate.getForObject(uri, PacketState.class);
        System.out.println("getState result: "+result);
        assertEquals(expected.getState(), result.getState());
//        assertTrue(expected.getDate(), result.getDate());
//        assertTrue("Dates aren't close enough to each other!", (expected.getDate().getTime() - result.getDate().getTime()) < 1000);
    }

}
