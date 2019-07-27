package net.tyt.packet.core.impexp.fs;

import net.tyt.packet.core.dto.PacketDTO;
import org.springframework.stereotype.Component;

/**
 *
 * @author Игорь Тытарь
 */
@Component
public class FsPacketExporter extends AbstractFsExporter<PacketDTO> {

    @Override
    protected String getDirName() {
        return "packets";
    }
}
