package br.security.project.app.security;

import br.security.project.app.modelo.Usuario;
import br.security.project.app.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = this.usuarioRepository.findByEmail(username);
        if(usuario.isPresent()) {
            return usuario.get();
        }
        throw new UsernameNotFoundException("Dados invalidos");
    }
}
