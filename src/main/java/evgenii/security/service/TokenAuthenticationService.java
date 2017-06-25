package evgenii.security.service;

import evgenii.exceptions.InvalidUserFieldsException;
import evgenii.model.User;
import evgenii.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by zhenia on 19.06.17.
 */

@Component
public class TokenAuthenticationService {
    private long expirationTime = 864_000_00;
    private String secret = "Secret";
    private String tokenPrefix = "Bearer";
    private String headerString = "Authorization";

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationService.class);

    @Autowired
    private UserService userService;

    public void addAutentication(HttpServletResponse res, String name) {
        String jwt = Jwts.builder()
                .setSubject(name)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        res.addHeader(headerString, tokenPrefix + " " + jwt);
    }

    public Authentication getAuthentication(HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader(headerString);

        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(tokenPrefix, ""))
                    .getBody()
                    .getSubject();

            User appUser = null;
            try {
                appUser = userService.getCustomerByName(user);
            } catch (InvalidUserFieldsException e) {
                LOGGER.error(e.getMessage());
            }

            System.out.println(appUser);

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user,
                            null,
                            new HashSet<>(Collections.singletonList(
                                    new SimpleGrantedAuthority("ROLE_" +
                                            (appUser.getUsername().toLowerCase().charAt(0) == 'a' ? "ADMIN" : "USER"))
                            ))) : null;
        }

        return null;
    }
}
