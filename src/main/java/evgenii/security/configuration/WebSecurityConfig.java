package evgenii.security.configuration;

import com.google.common.collect.ImmutableList;
import evgenii.security.filters.JwtAuthenticationFilter;
import evgenii.security.filters.JwtLoginFilter;
import evgenii.security.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Created by zhenia on 19.06.17.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/getAll", "/getCustomerByName", "/getCustomerById")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/updateUser").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/createUser").permitAll()
                .and().addFilterBefore(
                        new JwtLoginFilter("/login", authenticationManager(), tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class);
//                .antMatchers()
//                .anyRequest().authenticated();

        http.csrf().disable();
        http.cors();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
