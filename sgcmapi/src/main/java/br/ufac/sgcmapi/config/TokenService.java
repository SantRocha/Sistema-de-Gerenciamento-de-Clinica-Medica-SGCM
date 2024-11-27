package br.ufac.sgcmapi.config;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.ufac.sgcmapi.model.Usuario;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    private Instant generateExpirationDate() {
        // LocalDateTime dateTime = LocalDateTime.now().plusMinutes(10);
        LocalDateTime dateTime = LocalDateTime.now().plusSeconds(45);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zoneDateTime = dateTime.atZone(zoneId);
        return zoneDateTime.toInstant();
    }

    public String generateToken(Usuario usuario) {
        Algorithm alg = Algorithm.HMAC256(secret);
        String token = JWT.create()
                          .withIssuer("SGCM")
                          .withSubject(usuario.getNomeUsuario())
                          .withClaim("nomeCompleto", usuario.getNomeCompleto())
                          .withClaim("papel", usuario.getPapel().name())
                          .withClaim("dataLimiteRenovacao", LocalDate.now().toString())
                          .withExpiresAt(generateExpirationDate())
                          .sign(alg);
        return token;
    }

    public String validateToken(String token) {
        Algorithm alg = Algorithm.HMAC256(secret);
        DecodedJWT tokenValidado = JWT.require(alg)
                                      .withIssuer("SGCM")
                                      .build()
                                      .verify(token);
        return tokenValidado.getSubject();
    }
    
}
