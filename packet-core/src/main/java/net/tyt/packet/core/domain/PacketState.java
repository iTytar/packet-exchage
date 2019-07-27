package net.tyt.packet.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author tyt
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PacketState implements Serializable {
    @Id
    private String id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "pkstate")
    @NotNull
    private PacketStateEnum state;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "pkdate")
    private Date date = new Date();
    
    @ManyToOne
    @JsonIgnore
    private Packet packet;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PacketState)) {
            return false;
        }
        PacketState ps = (PacketState) o;
        return Objects.equals(getId(), ps.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void initId() {
        if (getId() != null) {
            throw new IllegalStateException("state id initialized alredy "+this);
        }
        setId(createId());
    }

    private static String createId() {
        return UUID.randomUUID().toString();
    }

}
