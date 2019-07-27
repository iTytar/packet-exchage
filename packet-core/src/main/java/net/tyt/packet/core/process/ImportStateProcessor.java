package net.tyt.packet.core.process;

import lombok.extern.slf4j.Slf4j;
import net.tyt.packet.core.domain.PacketStateEnum;
import net.tyt.packet.core.dto.StateDTO;
import net.tyt.packet.core.services.AdminPacketService;
import net.tyt.packet.core.impexp.Importer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Игорь Тытарь
 */
@Component
@Slf4j
public class ImportStateProcessor {
    @Autowired
    private AdminPacketService service;


    @Autowired
    private Importer<StateDTO> importer;
    
    @Scheduled(fixedRateString = "${exchange.import.state.rate:5000}")
    public void doImport() {
        importer.receivedIds().forEach(this::importState);
    }
    private void importState(String id) {
        log.debug("importState({})...",id);
        StateDTO dto = importer.get(id);
        service.updateState(dto.getPacketId(), PacketStateEnum.valueOf(dto.getState()));
        importer.commit(id);
    }
}
