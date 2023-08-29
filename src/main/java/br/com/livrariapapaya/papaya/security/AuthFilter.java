package br.com.livrariapapaya.papaya.security;

import br.com.livrariapapaya.papaya.repository.UsuarioRepository;
import br.com.livrariapapaya.papaya.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = recuperarToken(request);
        if(token!= null){
            var subject = tokenService.recuperarSubject(token);
            var usuario = usuarioRepository.findByLogin(subject);
            var auth = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            System.out.println("DEBUG subject - " +subject );
        }
        System.out.println("DEBUG : header - " + token );
        filterChain.doFilter(request,response);
    }
    private String recuperarToken(HttpServletRequest request){
        if(request.getHeader("Authorization") != null) {
            var header = request.getHeader("Authorization").replace("Bearer ", "").trim();

            return header;
        }
        return null;
    }
}
