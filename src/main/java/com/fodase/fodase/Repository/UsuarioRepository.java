package com.fodase.fodase.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fodase.fodase.Entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByLogin(String login);

    @Query("SELECT COUNT(u) FROM Usuario u") Long countusers();
}
