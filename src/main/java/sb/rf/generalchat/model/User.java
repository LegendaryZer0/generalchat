package sb.rf.generalchat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "account")
@DynamicUpdate
public class User implements Serializable {
 /*   private static final long serialVersionUID = 3493239893284L;*/

    @Column(name = "email", nullable = false,unique = true)
    private String email;


    @Basic
    @Column(name = "password", nullable = false)
    private String password;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Type(type = "long")
    private Long id;

    @Basic
    @Column(name = "nickname", nullable = true)
    private String nickname;
    @Basic
    @Column(name = "phone")
    private String phone;
    @ToString.Exclude
    @OneToMany(mappedBy = "userByIdFrom", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Message> messagesById;

    @OneToMany(mappedBy = "userByIdFrom", fetch = FetchType.EAGER)
    private Set<Chats> chatsById;

    @OneToMany(mappedBy = "userByIdTo", fetch = FetchType.EAGER)
    private Set<Chats> chatsById_0;
    @OneToOne(mappedBy = "userId", fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private TechnicalInfo technicalInfo;

    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private State state;

    public enum Role{
        ADMIN,USER,MODERATOR
    }
    public enum State{
        ACTIVE,BANNED,FROZEN
    }
    @Transient
    public boolean isActive(){
        return this.getState().toString().equals("ACTIVE");
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(id, user.id) && Objects.equals(nickname, user.nickname) && Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, id, nickname, phone);
    }


/*    public List<Chats> getChatsById() {
        return chatsById;
    }

    public void setChatsById(List<Chats> chatsById) {
        this.chatsById = chatsById;
    }*/

/*

    public List<Chats> getChatsById_0() {
        return chatsById_0;
    }

    public void setChatsById_0(List<Chats> chatsById_0) {
        this.chatsById_0 = chatsById_0;
    }
*/


}
