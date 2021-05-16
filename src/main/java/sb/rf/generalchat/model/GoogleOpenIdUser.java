package sb.rf.generalchat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import javax.persistence.*;
import java.util.Collection;
import java.util.Map;
@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
public class GoogleOpenIdUser implements OidcUser {


    @Transient
    private OidcUser oidcUser;

    private String email;

    private String nickname;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
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



    public static GoogleOpenIdUser from(OidcUser oidcUser){
        GoogleOpenIdUser googleOpenIdUser = GoogleOpenIdUser.builder()
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
        return googleOpenIdUser;
    }
}
