package br.security.project.app.controller;

import br.security.project.app.dto.UserDTO;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO.getEmail() + " " + userDTO.getSenha());
        return ResponseEntity.ok().build();
    }
}
