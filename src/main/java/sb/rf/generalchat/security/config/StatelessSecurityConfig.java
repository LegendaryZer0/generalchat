package sb.rf.generalchat.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.mobile.device.DeviceResolverRequestFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sb.rf.generalchat.filter.AccesTokenFilter;
import sb.rf.generalchat.filter.RefreshTokenFilter;
import sb.rf.generalchat.security.handler.SignInFailHandler;
import sb.rf.generalchat.security.provider.JWTAuthProvider;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity(debug = true)
@Order(1)
public class StatelessSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired
  @Qualifier("activeUserDetailsService")
  private UserDetailsService userDetailsService;

  @Autowired private DataSource dataSource;
  @Autowired private SignInFailHandler signInFailHandler;
  @Autowired private AccesTokenFilter accesTokenFilter;
  @Autowired private RefreshTokenFilter refreshTokenFilter;

  //    @Autowired
  //    private TokenAuthenticateFilter tokenAuthenticateFilter;

  @Autowired private JWTAuthProvider provider;

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    try {
      auth.userDetailsService(userDetailsService)
          .passwordEncoder(passwordEncoder)
          .and()
          .authenticationProvider(provider);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf()
        .ignoringAntMatchers("/api/**", "/authenticate/**", "/api/generate/**")
        .and()
        .authorizeRequests()
        .and()
        .formLogin()
        .disable()
        .authorizeRequests()
        .antMatchers("/api/**")
        .hasAuthority("ADMIN");
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(
            new DeviceResolverRequestFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(refreshTokenFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(accesTokenFilter, refreshTokenFilter.getClass())
        .antMatcher("/api/**");
  }
}
