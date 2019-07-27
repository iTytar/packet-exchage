package net.tyt.packet.core.impexp.fs;

import java.util.UUID;
import net.tyt.packet.core.domain.PacketStateEnum;
import net.tyt.packet.core.dto.PacketDTO;
import org.assertj.core.util.Files;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Игорь Тытарь
 */
public class FsPacketExporterTest {
    private final FsPacketExporter exporter;
    
    public FsPacketExporterTest() {
        exporter = new FsPacketExporter();
        exporter.setPath("./target/exchange/out");
    }
    
    @After
    public void tearDown() {
        Files.delete(exporter.getDir());
    }

    /**
     * Test of send method, of class FsPacketExporter.
     */
    @Test
    public void testSend() {
        System.out.println("testSend...");
        PacketDTO packet = createPacketDTO();
        exporter.send(packet);
        assertTrue(exporter.getFile(packet.getId()).exists());
    }
    public static PacketDTO createPacketDTO() {
        PacketDTO p = new PacketDTO();
        p.setId(UUID.randomUUID().toString());
        p.setState(PacketStateEnum.PLACED.toString());
        p.setDomain("domain");
        p.setPayload("payload".getBytes());
        p.setRefId("refId");
        return p;
    }
}
