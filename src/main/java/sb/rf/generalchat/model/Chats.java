package sb.rf.generalchat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@IdClass(ChatsPK.class)
/*@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)*/
public class Chats implements Serializable {
   /* private static final long serialVersionUID = 34932398932845L;*/
   @Id
   @Column(name = "id_from", nullable = false)
    private Long idFrom;

    @Id
 /*   @Type( type = "list-array" )
    @Column(
            name = "id_to",
            columnDefinition = "bigint[]"
    )*/
    @Column(name = "id_to" )
    private Long idTo;

    @Basic
    @Column(name = "uuid", nullable = false)
    private UUID uuid = UUID.randomUUID();
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


}
