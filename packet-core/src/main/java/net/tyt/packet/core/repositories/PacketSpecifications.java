package net.tyt.packet.core.repositories;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import net.tyt.packet.core.domain.Packet;
import net.tyt.packet.core.domain.PacketStateEnum;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author Игорь Тытарь
 */
public class PacketSpecifications {
    public static Specification<Packet> idsByState(PacketStateEnum state) {
        return (Root<Packet> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
            CriteriaQuery<String> idcq = cb.createQuery(String.class);
            root.get("id");
            return cb.conjunction();
        };
    }
}
