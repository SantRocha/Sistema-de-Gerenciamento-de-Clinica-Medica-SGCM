package br.ufac.sgcmapi.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;


@Configuration
@EnableWebSecurity
public class Seguranca {

    private final PerfilUsuarioService perfilUsuarioService;
    private final TokenFilter tokenFilter;

    public Seguranca(
            PerfilUsuarioService perfilUsuarioService,
            TokenFilter tokenFilter) {
        this.perfilUsuarioService = perfilUsuarioService;
        this.tokenFilter = tokenFilter;
    }

    @Bean
    AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    UserDetailsService udService() {
        return perfilUsuarioService;
    }

    @Bean
    BCryptPasswordEncoder passEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(udService());
        authProvider.setPasswordEncoder(passEncoder());
        return authProvider;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.httpBasic(withDefaults());
        http.cors(withDefaults());
        // http.csrf(csrf -> csrf.disable());
        http.authenticationProvider(authProvider());
        http.sessionManagement(
            session -> session.sessionCreationPolicy(
                SessionCreationPolicy.IF_REQUIRED
            )
        );

        XorCsrfTokenRequestAttributeHandler gerenciadorCsrf = new XorCsrfTokenRequestAttributeHandler();
        gerenciadorCsrf.setCsrfRequestAttributeName(null);
        http.csrf(
            csrf -> csrf
                .ignoringRequestMatchers("/login")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(gerenciadorCsrf::handle)
        );

        http.authorizeHttpRequests(
            authorize -> authorize
                // .anyRequest().permitAll()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers("/config/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        );

        http.addFilterBefore(
            tokenFilter,
            UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
    
}
