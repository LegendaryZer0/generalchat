package sb.rf.generalchat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@IdClass(ChatsPK.class)
public class Chats implements Serializable {

   @Id
   @Column(name = "id_from", nullable = false)
    private Long idFrom;

    @Id
    @Column(name = "id_to" )
    private Long idTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_from", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    @JsonIgnore
    private User userByIdFrom;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_to", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    @JsonIgnore
    private User userByIdTo;

    public  enum  State{
     BLOCKED,ACTIVE,BANNED
    }

}
