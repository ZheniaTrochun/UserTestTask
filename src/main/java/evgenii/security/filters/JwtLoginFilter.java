package evgenii.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import evgenii.model.dto.LoginDTO;
import evgenii.model.dto.UserDTO;
import evgenii.security.service.TokenAuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Created by zhenia on 19.06.17.
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenAuthenticationService tokenAuthenticationService;

    public JwtLoginFilter(String url, AuthenticationManager autManager, TokenAuthenticationService tokenAuthService) {
        super(new AntPathRequestMatcher(url));
        this.tokenAuthenticationService = tokenAuthService;
        setAuthenticationManager(autManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {

        LoginDTO loginDTO = new ObjectMapper().readValue(httpServletRequest.getInputStream(), LoginDTO.class);

        httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Access-Token, Authorization");

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.username, loginDTO.password, Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        tokenAuthenticationService.addAutentication(res, auth.getName());
    }
}
