package net.tyt.packet.core.impexp.fs;

import java.util.Collections;
import java.util.List;
import net.tyt.packet.core.domain.PacketStateEnum;
import net.tyt.packet.core.dto.PacketDTO;
import org.assertj.core.util.Files;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Игорь Тытарь
 */
public class FsPacketImporterTest {
    private final FsPacketExporter exporter;
    private final FsPacketImporter importer;
    private final PacketDTO packet;
    
    public FsPacketImporterTest() {
        exporter = new FsPacketExporter();
        exporter.setPath("./target/exchange/in");
        importer = new FsPacketImporter();
        importer.setPath(exporter.getPath());
        packet = FsPacketExporterTest.createPacketDTO();
    }
    
    @Before
    public void setUp() {
        exporter.send(packet);
    }
    
    @After
    public void tearDown() {
        Files.delete(importer.getDir());
    }

    /**
     * Test of receivedIds method, of class FsPacketImporter.
     */
    @Test
    public void testReceivedIds() {
        System.out.println("testReceivedIds...");
        List<String> expResult = Collections.singletonList(packet.getId());
        List<String> result = importer.receivedIds();
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class FsPacketImporter.
     */
    @Test
    public void testGet() {
        System.out.println("testGet...");
        PacketDTO result = importer.get(packet.getId());
        assertEquals(packet.getId(), result.getId());
        assertEquals(packet.getDomain(), result.getDomain());
        assertEquals(packet.getRefId(), result.getRefId());
        assertEquals(packet.getState(), result.getState());
        assertEquals(PacketStateEnum.valueOf(packet.getState()),PacketStateEnum.PLACED);
        assertArrayEquals(packet.getPayload(), result.getPayload());
    }

    /**
     * Test of commit method, of class FsPacketImporter.
     */
    @Test
    public void testCommit() {
        System.out.println("testCommit...");
        importer.commit(packet.getId());
        assertFalse(importer.getFile(packet.getId()).exists());
    }
    
}
