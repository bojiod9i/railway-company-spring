package ru.tsystems.railway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tsystems.railway.domain.accounts.User;
import ru.tsystems.railway.repository.UserRepository;

import java.util.Collections;

/**
 * Spring security class needed for authorization.
 */
@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    /**
     * This method override the base security method and sets the password and
     * the role of the user to Spring Security user.
     *
     * @param email the user's email;
     * @return org.springframework.security.core.userdetails.User
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email.toLowerCase());
        UserDetails userDetails = null;
        if (user != null) {
            userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true,
                    true, true, true,
                    Collections.singletonList(new SimpleGrantedAuthority(user
                            .getUserRole().name())));
        }
        return userDetails;
    }
}
