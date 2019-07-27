package net.tyt.packet.core.impexp.fs;

import net.tyt.packet.core.dto.StateDTO;
import org.springframework.stereotype.Component;

/**
 *
 * @author Игорь Тытарь
 */
@Component
public class FsStateExporter extends AbstractFsExporter<StateDTO> {

    @Override
    protected String getDirName() {
        return "states";
    }
}
