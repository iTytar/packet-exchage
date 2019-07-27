package net.tyt.packet.core.impexp.fs;

import net.tyt.packet.core.dto.StateDTO;
import org.springframework.stereotype.Component;

/**
 *
 * @author Игорь Тытарь
 */
@Component
public class FsStateImporter extends AbstractFsImporter<StateDTO> {

    @Override
    protected String getDirName() {
        return "states";
    }

    @Override
    protected Class<StateDTO> getItemClass() {
        return StateDTO.class;
    }
}
