package com.fodase.fodase.Controllers;

import java.io.Console;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fodase.fodase.Entity.Rodada;
import com.fodase.fodase.Entity.Usuario;
import com.fodase.fodase.Repository.RodadaRepository;

@RestController
@RequestMapping("/fodase")
public class JogoFD {

    @Autowired
    private RodadaRepository rRepository;

    @PostMapping
    public ResponseEntity<?> Cadastrar(@RequestBody Rodada rodada, Authentication authentication) {
        try {
            Usuario user = (Usuario) authentication.getPrincipal();
            rodada.setUsuario(user);
            Date dataAtual = new Date();
            rodada.setData(new Timestamp(dataAtual.getTime()));
            rRepository.save(rodada);
            return ResponseEntity.ok().body("Aposta Feita com Sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @MessageMapping("/allrd")
    @SendTo("/topic/greetings")
    public Iterable<Rodada> rodadasall() {
        return rRepository.findAll();
    }
}
