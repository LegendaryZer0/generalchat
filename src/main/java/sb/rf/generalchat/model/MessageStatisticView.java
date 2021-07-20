package sb.rf.generalchat.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Immutable
@Entity
@Synchronize({"id_from", "count"})
@Subselect(
    "select  id_from,count(message.message) from message group by id_from order by count(message.message)")
public class MessageStatisticView {
  @Id private Long id_from;
  private Long count;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    MessageStatisticView that = (MessageStatisticView) o;

    return id_from != null && id_from.equals(that.id_from);
  }

  @Override
  public int hashCode() {
    return 1099907004;
  }
}
