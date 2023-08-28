package br.com.livrariapapaya.papaya.controller;

import br.com.livrariapapaya.papaya.dto.DTOLogin;
import br.com.livrariapapaya.papaya.dto.DTOTokenJWT;
import br.com.livrariapapaya.papaya.model.Usuario;
import br.com.livrariapapaya.papaya.repository.UsuarioRepository;
import br.com.livrariapapaya.papaya.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody DTOLogin dados){

        var tokenAuth = new UsernamePasswordAuthenticationToken(dados.username(),dados.password());
        var auth = authenticationManager.authenticate(tokenAuth);
        var tokenJWT = tokenService.gerarToken((Usuario)auth.getPrincipal());


        return ResponseEntity.ok().body(new DTOTokenJWT(tokenJWT));
    }

}
