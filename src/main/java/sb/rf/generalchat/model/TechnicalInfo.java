package sb.rf.generalchat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity

@Table(name = "technical_Info", schema = "public")
public class TechnicalInfo   implements Serializable {
  /*  private static final long serialVersionUID = 34932398932841L;*/
    private UUID uuid;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "isdeleted", nullable = false)
    private Boolean isDeleted;
    @Enumerated(EnumType.STRING)
    private ConfirmState confirmState;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    private User userId;

    public enum ConfirmState{
        CONFIRMED,NONE_CONFIRMED
    }

    @Basic
    @Column(name = "uuid", nullable = true)
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechnicalInfo that = (TechnicalInfo) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(id, that.id) && Objects.equals(isDeleted, that.isDeleted) && Objects.equals(confirmState, that.confirmState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, id, isDeleted, confirmState);
    }



}
