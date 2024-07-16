package sber.TODO.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import sber.TODO.Project.services.ClientService;

@EnableWebSecurity
@Configuration
public class SecurConfig {

    @Autowired
    private ClientService clientService;

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
            registry.requestMatchers("/").permitAll();
            registry.requestMatchers("/reg").permitAll();
            registry.requestMatchers("/sign_in").permitAll();
            registry.requestMatchers("clients/sign_in").permitAll();
            registry.requestMatchers("clients/add").permitAll();
            registry.requestMatchers("toDo/**").hasRole("USER");
            registry.requestMatchers("tasks/**").hasRole("USER");
            registry.anyRequest().authenticated();
        }).formLogin(httpSecurityFormLoginConfigurer ->{
                        httpSecurityFormLoginConfigurer.loginPage("/sign_in").defaultSuccessUrl("/tasks/main", true).permitAll();
        }).build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return clientService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(getPasswordEncoder());
        return provider;
    }

}
