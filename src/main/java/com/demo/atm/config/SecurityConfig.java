package com.demo.atm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.demo.atm.utils.Constants.*;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( "/h2-console/**").permitAll()
                .antMatchers("/admin").hasAuthority(ROLE_SYSTEM_ADMIN)
                .antMatchers("/**").hasAuthority(ROLE_ATM_CLIENT)
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("diana_fagateanu")
                .password(encodePassword().encode("Parola123"))
                .roles(SYSTEM_ADMIN)
                .and()
                .withUser("ion_popescu")
                .password(encodePassword().encode("Parola456"))
                .roles(ATM_CLIENT)
                .and()
                .withUser("gigi_ionescu")
                .password(encodePassword().encode("Parola789"))
                .roles(ATM_CLIENT);
    }

    @Bean
    public PasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder(11);
    }
}
