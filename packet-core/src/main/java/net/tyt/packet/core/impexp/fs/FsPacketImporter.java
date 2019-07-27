package net.tyt.packet.core.impexp.fs;

import net.tyt.packet.core.dto.PacketDTO;
import org.springframework.stereotype.Component;

/**
 *
 * @author Игорь Тытарь
 */
@Component
public class FsPacketImporter extends AbstractFsImporter<PacketDTO> {

    @Override
    protected String getDirName() {
        return "packets";
    }

    @Override
    protected Class<PacketDTO> getItemClass() {
        return PacketDTO.class;
    }
}
