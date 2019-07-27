package net.tyt.packet.controllers;

import java.util.logging.Level;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import net.tyt.packet.core.domain.Packet;
import net.tyt.packet.core.domain.PacketState;
import net.tyt.packet.core.services.OutPacketService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Игорь Тытарь
 */
@RestController
@RequestMapping("/out/packets")
@RequiredArgsConstructor
@Log
public class OutPacketController {

    private final OutPacketService service;

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Packet toSendWithRefId(@RequestParam String domain, @RequestParam(required = false) String refId, @RequestBody byte[] payload) {
        log.log(Level.INFO, "toSendWithRefId({0},{1})...", new Object[]{domain ,refId});
        return service.toSendPacket(domain, payload, refId);
    }

    @GetMapping(value = "{id}/state")
    public @ResponseBody PacketState getState(@PathVariable String id) {
        log.log(Level.INFO, "getState({0})...", id);
        return service.getPacketState(id);
    }
}
