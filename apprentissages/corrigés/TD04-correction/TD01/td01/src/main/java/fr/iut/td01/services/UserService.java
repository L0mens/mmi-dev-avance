package fr.iut.td01.services;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import fr.iut.td01.models.UserData;
import fr.iut.td01.repositories.UserRepository;

/**
 * Service for users
 */
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private List<GrantedAuthority> userAuthorities;
    private List<GrantedAuthority> adminAuthorities;

    /**
     * Initialize the service
     * @param userRepository the repository
     */
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
        userAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");        
        adminAuthorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN","ROLE_USER");
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var data = userRepository.findById(username);
        if(!data.isPresent()) throw new UsernameNotFoundException(username);
        UserData user = data.get();
        List<GrantedAuthority> authorities = user.isAdmin()?adminAuthorities:userAuthorities;
        return new User(user.getLogin(), "{noop}"+user.getPassword(),authorities);
    }
    
}
