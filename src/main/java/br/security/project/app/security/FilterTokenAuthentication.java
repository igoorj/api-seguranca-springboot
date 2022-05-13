package br.security.project.app.security;

import antlr.Token;
import br.security.project.app.modelo.Usuario;
import br.security.project.app.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// filtro que intercepta a requisicao e valida o token
public class FilterTokenAuthentication extends OncePerRequestFilter {

    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;

    public FilterTokenAuthentication(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        boolean valid = this.tokenService.isValidToken(token);
        if(valid) {
            toAuthenticate(token);
        }
        filterChain.doFilter(request, response);
    }

    // autenticando o usuário
    private void toAuthenticate(String token) {

        // recuperando o usuario vindo com o token
        Long userId = this.tokenService.getUserAuthenticate(token);
        Usuario usuario = this.usuarioRepository.findById(userId).get();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
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
