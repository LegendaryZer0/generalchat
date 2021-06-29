package sb.rf.generalchat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(
    name = "account",
    indexes = {@Index(name = "IDX_USER_email", columnList = "email")})
@DynamicUpdate
public class User implements Serializable {

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nickname")
  private String nickname;

  @Column(name = "phone")
  private String phone;

  @Fetch(FetchMode.SUBSELECT)
  @OneToMany(mappedBy = "userByIdFrom")
  @JsonIgnore
  private Set<Message> messagesById;

  @Fetch(FetchMode.SUBSELECT)
  @OneToMany(mappedBy = "userByIdFrom")
  private Set<Chats> chatsById;

  @Fetch(FetchMode.SUBSELECT)
  @OneToMany(mappedBy = "userByIdTo")
  private Set<Chats> chatsById_0;

  @OneToOne(
      mappedBy = "userId",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  @JsonIgnore
  private TechnicalInfo technicalInfo;

  @OneToOne(
      mappedBy = "account_user",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  @JsonIgnore
  private BasicOpenIdUser basicOpenIdUser;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Enumerated(EnumType.STRING)
  private State state;

  @Transient
  public boolean isActive() {
    return this.getState().toString().equals("ACTIVE");
  }

  // Так ли это делается?
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    User user = (User) o;

    return id != null && id.equals(user.id);
  }

  @Override
  public int
      hashCode() { // Todo разобраться,не стоит ли сюда так же дописывать id?(хеш мапы сейчас
                   // испорченные)
    return 562048007;
  }

  public enum Role {
    ADMIN,
    USER,
    MODERATOR
  }

  public enum State {
    ACTIVE,
    BANNED,
    FROZEN
  }
}
