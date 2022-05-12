package br.security.project.app.security;

import antlr.Token;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterTokenAuthentication extends OncePerRequestFilter {

    private TokenService tokenService;
    public FilterTokenAuthentication(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        boolean valid = this.tokenService.isValidToken(token);
        System.out.println(valid);
        filterChain.doFilter(request, response);
    }

    // metodo responsável por extrair o token da requisicao
    private String getToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7, token.length());
    }
}
