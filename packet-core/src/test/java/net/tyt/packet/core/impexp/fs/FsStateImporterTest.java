package net.tyt.packet.core.impexp.fs;

import java.util.Collections;
import java.util.List;
import net.tyt.packet.core.domain.PacketStateEnum;
import net.tyt.packet.core.dto.PacketDTO;
import net.tyt.packet.core.dto.StateDTO;
import org.assertj.core.util.Files;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Игорь Тытарь
 */
public class FsStateImporterTest {
    private final FsStateExporter exporter;
    private final FsStateImporter importer;
    private final StateDTO packet;
    
    public FsStateImporterTest() {
        exporter = new FsStateExporter();
        exporter.setPath("./target/exchange/in");
        importer = new FsStateImporter();
        importer.setPath(exporter.getPath());
        packet = FsStateExporterTest.createDTO();
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
        StateDTO result = importer.get(packet.getId());
        assertEquals(packet.getId(), result.getId());
        assertEquals(packet.getPacketId(), result.getPacketId());
        assertEquals(packet.getState(), result.getState());
        assertEquals(PacketStateEnum.valueOf(packet.getState()),PacketStateEnum.DELIVERED);
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
