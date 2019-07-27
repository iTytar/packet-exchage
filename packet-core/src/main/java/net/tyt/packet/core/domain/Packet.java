package net.tyt.packet.core.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
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
public class Packet implements Serializable {
    @Id
    protected String id;

    private String refId;

    @OneToOne//(cascade = CascadeType.ALL)
    private PacketState currentState;

    private boolean incomming;

    @Column(name = "pkdomain")
    @NotNull
    private String domain;

    @Lob
//    @JsonIgnore
    private byte[] payload;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Packet)) {
            return false;
        }
        Packet packet = (Packet) o;
        return Objects.equals(getId(), packet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void initId() {
        if (getId() != null) {
            throw new IllegalStateException("packet id initialized alredy "+this);
        }
        setId(createId());
    }

    private static String createId() {
        return UUID.randomUUID().toString();
    }

}
