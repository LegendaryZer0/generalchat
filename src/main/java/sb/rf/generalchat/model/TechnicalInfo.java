package sb.rf.generalchat.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString

@Entity

@Table(name = "technical_Info", schema = "public")
public class TechnicalInfo   implements Serializable {

    @Column(name = "uuid", nullable = true)
    private UUID uuid;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "isdeleted", nullable = false)
    private Boolean isDeleted;
    @Enumerated(EnumType.STRING)
    private ConfirmState confirmState;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private User userId;

    public enum ConfirmState{
        CONFIRMED,NONE_CONFIRMED
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TechnicalInfo that = (TechnicalInfo) o;

        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 728922588;
    }
}
