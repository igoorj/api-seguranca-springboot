package br.security.project.app.security;

import br.security.project.app.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${app.jwt.expiration}")
    private String expiration;

    @Value("${app.jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication) {

        Usuario logado = (Usuario) authentication.getPrincipal();
        Date today = new Date();
        Date exp = new Date(today.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("Api de Teste")
                .setSubject(logado.getId().toString())
                .setIssuedAt(today)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
