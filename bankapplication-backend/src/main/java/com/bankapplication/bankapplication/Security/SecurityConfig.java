package com.bankapplication.bankapplication.Security;

import com.bankapplication.bankapplication.Security.Filter.AuthenticationFilter;
import com.bankapplication.bankapplication.Security.Filter.AuthorizationFilter;
import com.bankapplication.bankapplication.Types.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl("/auth/api/login");

        String adminRole = "ROLE_"+RoleType.ADMIN.name();
        String userRole = "ROLE_"+RoleType.USER.name();

        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests().antMatchers("/auth/api/login", "/auth/api/registration").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET,"/api/ownBalance", "/api/ownInformation").hasAnyAuthority(userRole,adminRole)
                .and().authorizeRequests().antMatchers(HttpMethod.POST,"/api/transferMoney/**").hasAnyAuthority(userRole,adminRole)
                .and().authorizeRequests().antMatchers(HttpMethod.PUT,"/api/doTransaction/**").hasAnyAuthority(userRole,adminRole)
                .and().authorizeRequests().antMatchers("/api/**").hasAnyAuthority(adminRole);
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManager();
    }
}
