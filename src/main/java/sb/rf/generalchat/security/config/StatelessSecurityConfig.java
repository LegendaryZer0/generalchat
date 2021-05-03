package sb.rf.generalchat.security.config;

import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sb.rf.generalchat.filter.JwtRequestFilter;
import sb.rf.generalchat.security.handler.SignInFailHandler;

import javax.persistence.criteria.Expression;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity(debug = true)
@Order(1)
public class StatelessSecurityConfig extends WebSecurityConfigurerAdapter {
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
    private JwtRequestFilter jwtRequestFilter;


    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        try {

            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        } catch (Exception e) {
            throw  new IllegalStateException(e);

        }

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {

        http.csrf().ignoringAntMatchers("/api/**","/authenticate/**").and()
                .authorizeRequests()
                .and().formLogin().disable().authorizeRequests()
                .antMatchers("/api/**").hasAuthority("ADMIN")
        ;
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).antMatcher("/api/**");
    }
}
