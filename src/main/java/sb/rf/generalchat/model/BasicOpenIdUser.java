package sb.rf.generalchat.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import javax.persistence.*;
import java.util.Collection;
import java.util.Map;
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
public class BasicOpenIdUser implements OidcUser {


    @Transient
    private OidcUser oidcUser;

    private String email;

    private String nickname;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private User account_user;
    @Id
    private String name;


    @Transient
    @Override
    public Map<String, Object> getClaims() {
        return oidcUser.getClaims();
    }
    @Transient
    @Override
    public OidcUserInfo getUserInfo() {
        return oidcUser.getUserInfo();
    }
    @Transient
    @Override
    public OidcIdToken getIdToken() {
        return oidcUser.getIdToken();
    }
    @Transient
    @Override
    public Map<String, Object> getAttributes() {
        return oidcUser.getAttributes();
    }
    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oidcUser.getAuthorities();
    }

    @Transient
    @Override
    public String getName() {
        return oidcUser.getName();
    }



    public static BasicOpenIdUser from(OidcUser oidcUser){
        BasicOpenIdUser basicOpenIdUser = BasicOpenIdUser.builder()
                .oidcUser(oidcUser)
                .email(oidcUser.getEmail())
                .name(oidcUser.getName())
                .nickname(oidcUser.getFullName())
                .account_user(User.builder()
                        .email(oidcUser.getEmail())
                        .nickname(oidcUser.getFullName())
                        .password("")
                        .role(User.Role.USER)
                        .state(User.State.ACTIVE)
                        .technicalInfo(TechnicalInfo.builder()
                                .isDeleted(false)
                                .confirmState(TechnicalInfo.ConfirmState.CONFIRMED)
                                .build())
                        .build()).build();
        return basicOpenIdUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BasicOpenIdUser that = (BasicOpenIdUser) o;

        return name != null && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return 2068085731;
    }
}
