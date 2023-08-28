package br.com.livrariapapaya.papaya.service;

import br.com.livrariapapaya.papaya.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    private String secret = "SEGREDO123";
    public String gerarToken(Usuario usuario){
        Algorithm algoritimo = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withIssuer("Livraria Papaya API")
                .withSubject(usuario.getLogin())
                .withExpiresAt(DataExpiracao())
                .sign(algoritimo);

        return token;

    }
    public String recuperarSubject(String tokenJWT){
        Algorithm algoritimo = Algorithm.HMAC256(secret);
        try {
            return JWT.require(algoritimo)
                    .withIssuer("Livraria Papaya API")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token invalido ou expirado");
        }
    }

    private Instant DataExpiracao() {
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }
}
