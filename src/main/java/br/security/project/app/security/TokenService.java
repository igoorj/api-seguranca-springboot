package br.security.project.app.security;

import br.security.project.app.modelo.Usuario;
import io.jsonwebtoken.Claims;
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

    // o metodo utiliza a variavel secret para descriptografar o token
    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // método que recupera os dados do usuario no token
    public Long getUserAuthenticate(String token) {

        // retorna o corpo compactado do token
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject()); // id do usuario
    }
}
