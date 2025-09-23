package br.com.tria.mobilebackend.controller;

import br.com.tria.mobilebackend.model.Usuario;
import br.com.tria.mobilebackend.repository.UsuarioRepository;
import br.com.tria.mobilebackend.security.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public AutenticacaoController(UsuarioRepository usuarioRepository,
                                  AuthenticationManager authenticationManager,
                                  PasswordEncoder passwordEncoder,
                                  JWTUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario salvo = usuarioRepository.save(usuario);
        return ResponseEntity.created(URI.create("/usuarios/" + salvo.getId())).body(salvo);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credenciais) {
        String username = credenciais.get("username");
        String senha = credenciais.get("senha") != null ? credenciais.get("senha") : credenciais.get("password");

        Authentication autenticado = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, senha)
        );

        if (autenticado.isAuthenticated()) {
            String token = jwtUtil.construirToken(username);
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).build();
    }
}


