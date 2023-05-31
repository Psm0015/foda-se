package com.fodase.fodase.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fodase.fodase.Entity.Role;
import com.fodase.fodase.Entity.Usuario;
import com.fodase.fodase.Objects.AuthenticationRequest;
import com.fodase.fodase.Objects.AuthenticationResponse;
import com.fodase.fodase.Objects.RegisterRequest;
import com.fodase.fodase.Repository.UsuarioRepository;
import com.fodase.fodase.Security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Usuario user =  new Usuario();
        user.setNome(request.getNome());
        user.setLogin(request.getLogin());
        user.setSenha(passwordEncoder.encode(request.getSenha()));
        System.out.println(repository.countusers());
        if(repository.countusers() == 0){
            user.setRole(Role.ADMIN);
        }else{
            user.setRole(Role.USER);
        }
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getSenha())
        );
        var user  = repository.findByLogin(request.getLogin()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

}
