package net.tyt.packet.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.logging.Level;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import net.tyt.packet.core.domain.Packet;
import net.tyt.packet.core.domain.PacketStateEnum;
import net.tyt.packet.core.services.AdminPacketService;
import net.tyt.packet.dto.PacketList;
import net.tyt.packet.dto.PacketStateList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/admin/packets")
@RequiredArgsConstructor
@Log
@Api(value="Admin Packet Exchage API")
public class AdminPacketController {

    private final AdminPacketService service;

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new packet", response = Packet.class)
    public @ResponseBody Packet createPacket(@RequestParam boolean incomming, @RequestParam String domain, @RequestParam(required = false) String refId, @RequestBody byte[] payload) {
        log.log(Level.INFO, "createPacketWithRefId({0},{1},{2})...", new Object[]{incomming,domain ,refId});
        return service.createPacket(incomming, domain, payload, refId);
    }

    @PutMapping(value = "/{id}", params = "state")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update packet state (PLACED,SENT,DELIVERED,PROCED,RECEIVED)", response = Void.class)
    public void updateState(@PathVariable String id, @RequestParam String state) {
        log.log(Level.INFO, "updateState({0})...", id);
        service.updateState(id, PacketStateEnum.valueOf(state)).getCurrentState();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get packet by id", response = Packet.class)
    public @ResponseBody Packet getPacket(@PathVariable String id) {
        log.log(Level.INFO, "getPacket({0})...", id);
        return service.getPacket(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete packet by id", response = Packet.class)
    public void deletePacket(@PathVariable String id) {
        log.log(Level.INFO, "deletePacket({0})...", id);
        service.deletePacket(id);
    }

    @GetMapping(value = "/{id}/states")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get packets states by packet id", response = PacketStateList.class)
    public @ResponseBody PacketStateList getPacketStates(@PathVariable String id) {
        log.log(Level.INFO, "getPacketStates({0})...", id);
        return new PacketStateList(service.getPacketStates(id));
    }

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get packets list by domaim and/or state (PLACED,SENT,DELIVERED,PROCED,RECEIVED)", response = PacketList.class)
    public @ResponseBody PacketList getPackets(@RequestParam(required = false) String domain, @RequestParam(required = false) String state) {
        log.log(Level.INFO, "getPacketsByState({0},{1})...", new Object[]{domain,state});
        PacketStateEnum stateEnum = (state == null) ? null : PacketStateEnum.valueOf(state); 
        return new PacketList(service.getPackets(domain, stateEnum));
    }
    
    @DeleteMapping(value = "/proced", params = {})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete all proced packets", response = Void.class)
    public void deleteProcedPackets() {
        log.log(Level.INFO, "deleteProcedPackets()...");
        service.deleteProcedPackets();
    }
    
    @DeleteMapping(value = "", params = {})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete all packets (NOT FOR PRODUCTION)", response = Void.class)
    public void deleteAllPackets() {
        log.log(Level.INFO, "deleteAllPackets()...");
        service.deleteAllPackets();
    }
}
