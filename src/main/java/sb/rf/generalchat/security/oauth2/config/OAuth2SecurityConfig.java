/*
package sb.rf.generalchat.security.oauth2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import sb.rf.generalchat.security.oauth2.CustomOAuth2User;
import sb.rf.generalchat.security.oauth2.CustomOidcUser;
import sb.rf.generalchat.security.oauth2.service.CustomOAuth2UserService;
import sb.rf.generalchat.security.oauth2.service.OidcService;
import sb.rf.generalchat.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity(debug = true)
@PropertySource("classpath:application-local.properties")
@Order(1)
@Slf4j
public class OAuth2SecurityConfig  extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserService userService;
    @Autowired
    private CustomOAuth2UserService oauthUserService;
    @Autowired
    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .oidcUserService(oidcUserService)
                */
/*.userService(oauthUserService)*//*

                .and()
                .successHandler((request, response, authentication) -> {

                    DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
                    log.info("oauthUser from config {}",oauthUser);
                    userService.processOAuthPostLogin(oauthUser);

                    response.sendRedirect("/user/selfProfile");
                }).and().csrf();
    }


}
*/
