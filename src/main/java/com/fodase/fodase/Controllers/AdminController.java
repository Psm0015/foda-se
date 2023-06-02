package com.fodase.fodase.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fodase.fodase.Entity.Usuario;
import com.fodase.fodase.Objects.EditarRequest;
import com.fodase.fodase.Repository.UsuarioRepository;

@RestController
@RequestMapping("/adm")
public class AdminController {
    
    @Autowired
    private UsuarioRepository uRepository;

    @GetMapping("/usr")
    public ResponseEntity<?> BuscarUsuarios() {
        try {
            return ResponseEntity.ok().body(uRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
    @GetMapping("/usr/{id}")
    public ResponseEntity<?> buscarUsuario(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok().body(uRepository.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
    @PutMapping("/usr/{id}")
    public ResponseEntity<?> editarUsuario(@RequestBody EditarRequest usredit, @PathVariable Integer id) {
        try {
            Usuario user = uRepository.findById(id).get();
            user.setLogin(usredit.getLogin());
            user.setNome(usredit.getNome());
            uRepository.save(user);
            return ResponseEntity.ok().body("Usuário Editado com Sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
    @DeleteMapping("/usr/{id}")
    public ResponseEntity<?> apagarUsuario(@PathVariable Integer id) {
        try {
            uRepository.deleteById(id);
            return ResponseEntity.ok().body("Usuário Deletado com Sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

}
