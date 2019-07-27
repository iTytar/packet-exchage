package net.tyt.packet.controllers;

import java.util.logging.Level;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import net.tyt.packet.core.services.InPacketService;
import net.tyt.packet.dto.StringList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/in/packets")
@RequiredArgsConstructor
@Log
public class InPacketController {

    private final InPacketService service;

    @GetMapping("")
    public @ResponseBody StringList getReceivedPacketIds(@RequestParam String domain) {
        log.log(Level.INFO, "getReceivedPacketIds({0})...", domain);
        return new StringList(service.getReceivedIds(domain));
    }

    @GetMapping("/{id}/payload")
    public @ResponseBody byte[] getPayload(@PathVariable String id) {
        log.log(Level.INFO, "getPayload({0})...", id);
        return service.getPayload(id);
    }

    @PutMapping("/{id}/proced")
    @ResponseStatus(HttpStatus.OK)
    public void markAsReaded(@PathVariable String id) {
        log.log(Level.INFO, "markAsReaded({0})...", new Object[]{id});
        service.markAsReaded(id);
    }
}
