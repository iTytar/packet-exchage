package net.tyt.packet.core.impexp.fs;

import java.util.UUID;
import net.tyt.packet.core.domain.PacketStateEnum;
import net.tyt.packet.core.dto.StateDTO;
import org.assertj.core.util.Files;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Игорь Тытарь
 */
public class FsStateExporterTest {
    private final FsStateExporter exporter;
    
    public FsStateExporterTest() {
        exporter = new FsStateExporter();
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
        StateDTO packet = createDTO();
        exporter.send(packet);
        assertTrue(exporter.getFile(packet.getId()).exists());
    }
    public static StateDTO createDTO() {
        StateDTO p = new StateDTO();
        p.setId(UUID.randomUUID().toString());
        p.setState(PacketStateEnum.DELIVERED.toString());
        p.setPacketId(UUID.randomUUID().toString());
        return p;
    }
}
