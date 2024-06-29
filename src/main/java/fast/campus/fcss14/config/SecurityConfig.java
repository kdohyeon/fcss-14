package fast.campus.fcss14.config;

import fast.campus.fcss14.auth.CustomCsrfTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomCsrfTokenRepository customCsrfTokenRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(c -> {
            c.csrfTokenRepository(customCsrfTokenRepository);
            c.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler());
        });
        httpSecurity.authorizeHttpRequests(c -> c.anyRequest().permitAll());
        return httpSecurity.build();
    }
}
