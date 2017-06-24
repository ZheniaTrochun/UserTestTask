package evgenii.security.service;

import evgenii.exceptions.InvalidUserFieldsException;
import evgenii.model.User;
import evgenii.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;

/**
 * Created by zhenia on 23.06.17.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            User user = userService.getCustomerByName(s);

            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" +
                    (user.getUsername().charAt(0) == 'A' ? "ADMIN" : "USER"));

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    new HashSet<>(Collections.singletonList(grantedAuthority)));

        } catch (InvalidUserFieldsException e) {
            LOGGER.error(e.getMessage());
            throw new UsernameNotFoundException("Invalid username");
        }
    }
}
