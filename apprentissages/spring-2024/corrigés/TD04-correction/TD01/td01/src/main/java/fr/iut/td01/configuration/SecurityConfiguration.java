package fr.iut.td01.configuration;


import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import fr.iut.td01.repositories.UserRepository;
import fr.iut.td01.services.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorizeRequests) -> authorizeRequests                                
                .requestMatchers("/person/**").authenticated()                
                .requestMatchers("/whois").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/hello").permitAll()
                //.anyRequest().authenticated()                 
            )                     
            .httpBasic(Customizer.withDefaults()).csrf(csrf->csrf.disable()) ;  // disable csrf security to authorize post, patch & delete

        return http.build();
    }
    
 /*   @Bean
    public UserDetailsService userDetailsService(@Value("${password}") String pass) {         // password is injected from application.properties
        String password="{noop}"+ pass;
        return (username) -> new User(username, password, AuthorityUtils.createAuthorityList("ROLE_USER"));
    }  */
     @Bean
    public UserDetailsService userDetailsService(){
        return new UserService(userRepository);
    } 
    private UserRepository userRepository;
    public SecurityConfiguration(UserRepository userRepository){
        this.userRepository = userRepository;
    }
}
