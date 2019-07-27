package net.tyt.packet.core.process;

import lombok.extern.slf4j.Slf4j;
import net.tyt.packet.core.dto.PacketDTO;
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
public class ImportPacketProcessor {
    @Autowired
    private AdminPacketService service;


    @Autowired
    private Importer<PacketDTO> importer;
    
    @Scheduled(fixedRateString = "${exchange.import.packet.rate:5000}")
    public void doImport() {
        importer.receivedIds().forEach(this::importPacket);
    }
    private void importPacket(String id) {
        log.debug("importPacket({})...",id);
        PacketDTO dto = importer.get(id);
//        service.createPacket(true, dto.getDomain(), dto.getPayload(), dto.getRefId());
        service.save(dto.buildPacket());
        importer.commit(id);
    }
}
