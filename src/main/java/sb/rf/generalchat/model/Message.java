package sb.rf.generalchat.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Message  implements Serializable {
    @Type(type = "long")
    private Long idTo;
    @Type(type = "long")

    @Basic
    @Column(name = "id_from", nullable = true)
    private Long idFrom;
    private String message;
    private Timestamp time;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pk", nullable = false)
    @Type(type = "long")
    private Long idPk;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_from", referencedColumnName = "id", insertable = false,updatable = false)
    private User userByIdFrom;
    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus; //Todo Stomp протокол
    public enum MessageStatus {
        RECEIVED, DELIVERED
    }
   /* @Enumerated(EnumType.STRING)
    private MessageType messageType; //Todo Stomp протокол
    public enum MessageType {
        RECEIVED, DELIVERED
    }*/


    @Column(name = "id_to", nullable = true)
    public Long getIdTo() {
        return idTo;
    }

    public void setIdTo(Long idTo) {
        this.idTo = idTo;
    }


    @Basic
    @Column(name = "Message", nullable = true)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "Time", nullable = true)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(idTo, message1.idTo) && Objects.equals(idFrom, message1.idFrom) && Objects.equals(message, message1.message) && Objects.equals(time, message1.time) && Objects.equals(idPk, message1.idPk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTo, idFrom, message, time, idPk);
    }


}
