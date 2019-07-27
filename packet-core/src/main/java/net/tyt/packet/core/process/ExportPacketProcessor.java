package net.tyt.packet.core.process;

import lombok.extern.slf4j.Slf4j;
import net.tyt.packet.core.domain.Packet;
import net.tyt.packet.core.domain.PacketStateEnum;
import net.tyt.packet.core.dto.PacketDTO;
import net.tyt.packet.core.impexp.Exporter;
import net.tyt.packet.core.services.AdminPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Игорь Тытарь
 */
@Component
@Slf4j
public class ExportPacketProcessor {

    @Autowired
    private AdminPacketService service;

    @Autowired
    private Exporter<PacketDTO> exporter;

    @Scheduled(fixedRateString = "${exchange.export.packet.rate:5000}")
    public void doExport() {
        service.getPacketsByState(PacketStateEnum.PLACED).forEach(this::send);
    }

    private void send(Packet p) {
        log.debug("send({})...",p.getId());
        exporter.send(PacketDTO.of(p));
        service.updateState(p.getId(), PacketStateEnum.SENT);
    }
}
