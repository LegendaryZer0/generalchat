package sb.rf.generalchat.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;
import sb.rf.generalchat.security.handler.SignInFailHandler;
import sb.rf.generalchat.security.oauth2.service.CustomOAuth2UserService;
import sb.rf.generalchat.service.GoogleOidcService;
import sb.rf.generalchat.service.UserService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:application.properties")
@Order(2)
@Slf4j
public class SecurityBasicConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("activeUserDetailsService")
    private UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SignInFailHandler signInFailHandler;

    @Autowired
    private CustomOAuth2UserService oauthUserService;
    @Autowired
    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;

 /*   @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
*/

    @Autowired
    private GoogleOidcService service;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        try {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        } catch (Exception e) {
            throw new IllegalStateException(e);

        }

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class)
                .authorizeRequests()
                //and something matchers here

                .antMatchers("/admin/**").hasAnyAuthority("ADMIN") //
                .antMatchers("/user/**").hasAnyAuthority("USER", "ADMIN", "MODER")/*.hasAnyRole("ADMIN", "MODER", "USER")*/
                .antMatchers("/authenticate/**", "/**", "css/**", "js/**").permitAll()
                .and()
                .formLogin().failureHandler(signInFailHandler)
                .loginPage("/login").loginProcessingUrl("/login").permitAll()
                .usernameParameter("email")
                .defaultSuccessUrl("/user/selfProfile")

                .failureUrl("/loginFail")
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and().rememberMe().rememberMeParameter("remember_me").tokenRepository(persistentTokenRepository())
                .and()

                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .oidcUserService(oidcUserService)
                .userService(oauthUserService)
                .and()
                .successHandler((request, response, authentication) -> {

                    DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
                    log.info("oauthUser from config {}",oidcUser);
                    authentication.setAuthenticated(true);
                    log.info("aouth principals before precess {}",authentication.getPrincipal());
                    service.processOAuthPostLogin(oidcUser,request);
                    log.info("aouth principals after precess {}",authentication.getPrincipal());

                    response.sendRedirect("/user/selfProfile");
                }).and()
                .csrf()
                .ignoringAntMatchers("/api/**","/authenticate/**")



        ;

    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }


}
