package sb.rf.generalchat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Immutable
@Entity
@Synchronize({"id_from","count"})
@Subselect("select  id_from,count(message.message) from message group by id_from order by count(message.message)")
public class MessageStatisticView {
    @Id
    private Long id_from;
    private Long count;
}
