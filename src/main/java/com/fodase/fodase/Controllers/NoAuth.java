package com.fodase.fodase.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fodase.fodase.Entity.Rodada;
import com.fodase.fodase.Repository.RodadaRepository;

@RestController
@RequestMapping("/na")
public class NoAuth {
    @Autowired
    private RodadaRepository rRepository;

    @MessageMapping("/mostrarjogo")
    @SendTo("/topic/greetings")
    public Iterable<Rodada> mostrarjogo() {
        return rRepository.findAll();
    }
}
