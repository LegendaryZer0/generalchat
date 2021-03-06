package sb.rf.generalchat.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import sb.rf.generalchat.encryption.LongAttributeEncryptor;
import sb.rf.generalchat.encryption.StringAttributeEncryptor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Message implements Serializable {
 /* @Type(type = "long")*/
  @Convert(converter = LongAttributeEncryptor.class)
  @Column(name = "id_to", nullable = true)
  private Long idTo;

  @Type(type = "long")
  @Basic
  @Column(name = "id_from", nullable = true)
  private Long idFrom;
  @Convert(converter = StringAttributeEncryptor.class)
  @Column(columnDefinition = "text",name = "Message")
  private String message;


  private Timestamp time;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_pk", nullable = false)
  @Type(type = "long")
  private Long idPk;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "id_from", referencedColumnName = "id", insertable = false, updatable = false)
  private User userByIdFrom;

  @Enumerated(EnumType.STRING)
  private MessageStatus messageStatus; // Todo Stomp протокол
  public enum MessageStatus {
    RECEIVED,
    DELIVERED
  }

   @Enumerated(EnumType.STRING)
  private MessageType messageType; //Todo Stomp протокол
  public enum MessageType {
      DOCUMENT,STRING
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
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Message message = (Message) o;

    return idPk != null && idPk.equals(message.idPk);
  }

  @Override
  public int hashCode() {
    return 1996936819;
  }


}
