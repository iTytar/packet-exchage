package net.tyt.packet.core.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.tyt.packet.core.domain.Packet;
import net.tyt.packet.core.domain.PacketState;
import net.tyt.packet.core.domain.PacketStateEnum;
import net.tyt.packet.core.dto.StateDTO;
import net.tyt.packet.core.impexp.Exporter;
import net.tyt.packet.core.repositories.PacketRepository;
import net.tyt.packet.core.repositories.PacketStateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tyt
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AdminPacketServiceImpl implements AdminPacketService {

    private final PacketRepository packetRepository;
    private final PacketStateRepository stateRepository;
    private final Exporter<StateDTO> stateExporter;
//    private final Exporter<PacketDTO> packetExporter;
    
    @Override
    public Packet createPacket(boolean incomming, String domain, byte[] payload, String refId) {
        Packet p = new Packet();
        p.initId();
        p.setRefId(refId);
        p.setIncomming(incomming);
        p.setDomain(domain);
        p.setPayload(payload);
        return save(p);
    }

    @Override
    public List<Packet> getAllPackets() {
        return getPackets(null, null);
    }

    @Override
    public List<Packet> getPacketsByState(PacketStateEnum state) {
        return getPackets(null, state);
    }

    @Override
    public List<Packet> getPacketsByDomain(String domain) {
        return getPackets(domain, null);
    }

    @Override
    public List<Packet> getPackets(String domain, PacketStateEnum state) {
        if (domain != null && state != null) {
            return packetRepository.findByDomainAndCurrentStateState(domain,state);
        }
        if (domain != null && state == null) {
            return packetRepository.findByDomain(domain);
        }
        if (domain == null && state != null) {
            return packetRepository.findByCurrentStateState(state);
        }
        List<Packet> l = new ArrayList();
        packetRepository.findAll().forEach(l::add);
        return l;
    }
    @Override
    public List<String> getPacketIdsByState(PacketStateEnum state) {
        return packetRepository.findIdsByCurrentStateState(state);
    }

    @Override
    public List<String> getPacketIds(String domain, PacketStateEnum state) {
        if (domain != null && state != null) {
            return packetRepository.findIdsByDomainAndCurrentStateState(domain,state);
        }
        if (domain != null && state == null) {
            return packetRepository.findIdsByDomain(domain);
        }
        if (domain == null && state != null) {
            return packetRepository.findIdsByCurrentStateState(state);
        }
        return packetRepository.findAllIds();
    }

    @Override
    public List<String> getPacketIdsByDomain(String domain) {
        return packetRepository.findIdsByDomain(domain);
    }

    @Override
    public Packet updateState(String packetId, PacketStateEnum state) {
        return setState(getPacket(packetId),state);
    }
    
    private Packet setState(Packet packet, PacketStateEnum state) {
        PacketState ps = new PacketState();
        ps.initId();
        ps.setDate(new Date());
        ps.setState(state);
        ps.setPacket(packet);
        ps = stateRepository.save(ps);
        PacketState cps = packet.getCurrentState();
        if (cps == null || !ps.getDate().before(cps.getDate())) {
            packet.setCurrentState(ps);
            packet = packetRepository.save(packet);
        }
//        if (state == PacketStateEnum.PLACED) {
//            packetExporter.send(PacketDTO.of(packet));
//            setState(packet, PacketStateEnum.SENT);
//        }
        if (state == PacketStateEnum.RECEIVED) {
            stateExporter.send(StateDTO.of(ps.getId(), packet.getId(), PacketStateEnum.DELIVERED));
        }
        if (state == PacketStateEnum.PROCED && packet.isIncomming()) {
            stateExporter.send(StateDTO.of(ps.getId(), packet.getId(), PacketStateEnum.PROCED));
        }
        return packet;
    }

    @Override
    public Packet getPacket(String id) {
        return packetRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("packet not found by id="+id));
    }

    @Override
    public List<PacketState> getPacketStates(String packetId) {
        return stateRepository.findByPacketId(packetId);
    }
    

    @Override
    public long deleteAllPackets() {
        stateRepository.deleteAll();
        long c = packetRepository.count();
        packetRepository.deleteAll();
        return c;
    }

    @Override
    public Packet save(Packet packet) {
        packet = packetRepository.save(packet);
        return setState(packet, packet.isIncomming() ? PacketStateEnum.RECEIVED : PacketStateEnum.PLACED);
    }


    @Override
    public void deletePacket(String id) {
        stateRepository.deleteByPacketId(id);
        packetRepository.deleteById(id);
    }

    @Override
    public long deleteProcedPackets() {
        List<String> ids = packetRepository.findIdsByCurrentStateState(PacketStateEnum.PROCED); 
        ids.forEach(this::deletePacket);
        return ids.size();
    }
}
