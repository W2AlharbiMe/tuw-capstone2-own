package com.example.capstone2.Configuration;

import com.example.capstone2.Service.SecurityUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final SecurityUserDetailsService securityUserDetailsService;

    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(securityUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())

                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/create/customer").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/create/sales-person").permitAll()



                .requestMatchers("/api/v1/cars/**").hasAuthority("SALES_PERSON")
                .requestMatchers("/api/v1/dealer-services/**").hasAuthority("SALES_PERSON")
                .requestMatchers("/api/v1/inventories/**").hasAuthority("SALES_PERSON")
                .requestMatchers("/api/v1/inventory-items/**").hasAuthority("SALES_PERSON")
                .requestMatchers("/api/v1/manufacturers/**").hasAuthority("SALES_PERSON")
                .requestMatchers("/api/v1/parts/**").hasAuthority("SALES_PERSON")
                .requestMatchers("/api/v1/sales-invoices/**").hasAuthority("SALES_PERSON")
                .requestMatchers("/api/v1/sales-persons/**").hasAuthority("SALES_PERSON")

                .requestMatchers("/api/v1/customers/**").hasAuthority("CUSTOMER")


                .anyRequest().authenticated()

                .and()

                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)

                .and()

                .httpBasic()
        ;

        return http.build();
    }

}
