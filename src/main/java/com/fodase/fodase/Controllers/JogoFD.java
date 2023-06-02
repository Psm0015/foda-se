package com.fodase.fodase.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fodase.fodase.Entity.Rodada;
import com.fodase.fodase.Entity.Usuario;

@RestController
@RequestMapping("/fodase")
public class JogoFD {

    @PostMapping
    public ResponseEntity<?> Cadastrar(Rodada rodada, Authentication authentication) {
        try {
            Usuario user = (Usuario) authentication.getPrincipal();
            rodada.setUsuario(user);
            return ResponseEntity.ok().body("Aposta Feita com Sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
