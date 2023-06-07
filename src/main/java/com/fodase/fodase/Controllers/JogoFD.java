package com.fodase.fodase.Controllers;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
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
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private RodadaRepository rRepository;

    @PostMapping("/aposta/{aposta}")
    public ResponseEntity<?> Cadastrar(@PathVariable Integer aposta, Authentication authentication) {
        try {
            Usuario user = (Usuario) authentication.getPrincipal();
            Rodada rodada = new Rodada();
            rodada.setUsuario(user);
            Date dataAtual = new Date();
            rodada.setData(new Timestamp(dataAtual.getTime()));
            rodada.setAposta(aposta);
            Rodada rd = rRepository.save(rodada);
            messagingTemplate.convertAndSend("/topic/greetings", rodada);
            return ResponseEntity.ok().body(rd);
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
