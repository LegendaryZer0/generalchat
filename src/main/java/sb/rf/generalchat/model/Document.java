package sb.rf.generalchat.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Document {
    @Id
    private UUID link;
    @Column(columnDefinition = "text")
    private String data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Document document = (Document) o;

        return link != null && link.equals(document.link);
    }

    @Override
    public int hashCode() {
        return 1422296640;
    }
}
