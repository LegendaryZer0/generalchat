package sb.rf.generalchat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
/*@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)*/
public class ChatsPK implements Serializable {

    @Column(name = "id_from", nullable = false)
    @Id
    private Long idFrom;
    @Id
/*   *//* @Type( type = "list-array" *//*)*/
   /* @Column(
            name = "id_to",
            columnDefinition = "bigint[]"
    )*/
    @Column(name = "id_to" )
    private Long idTo;




}
