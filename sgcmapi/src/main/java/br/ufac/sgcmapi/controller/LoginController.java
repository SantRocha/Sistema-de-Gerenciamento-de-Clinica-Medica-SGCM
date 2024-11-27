package br.ufac.sgcmapi.controller;


import java.security.Principal;
import java.time.LocalDate;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.ufac.sgcmapi.config.PerfilUsuario;
import br.ufac.sgcmapi.config.TokenService;
import br.ufac.sgcmapi.controller.dto.UsuarioDto;
import br.ufac.sgcmapi.controller.mapper.UsuarioMapper;
import br.ufac.sgcmapi.model.Usuario;
import br.ufac.sgcmapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@Tag(
    name = "Login",
    description = "Endpoints para autenticação de usuários"
)
@RequestMapping(produces = MediaType.TEXT_PLAIN_VALUE)
public class LoginController {

    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper;

    public LoginController(
            AuthenticationManager authManager,
            TokenService tokenService,
            UsuarioService usuarioService,
            UsuarioMapper mapper) {
        this.authManager = authManager;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Autenticar usuário (HTTP Basic)",
        description = "Retorna as informações do usuário autenticado"
    )
    public ResponseEntity<UsuarioDto> login() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioService.getByNomeUsuario(principal.getName());
        UsuarioDto dto = mapper.toDto(usuario);
        return ResponseEntity.ok(dto);
    }
    

    @PostMapping("/login")
    @Operation(
        summary = "Autenticar usuário (JWT)",
        description = "Autentica um usuário e retorna um token de acesso"
    )
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {

        UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
            usuario.getNomeUsuario(), usuario.getSenha());
        Authentication auth = authManager.authenticate(loginToken);
        PerfilUsuario principal = (PerfilUsuario) auth.getPrincipal();

        Usuario usuarioAutenticado = usuarioService.getByNomeUsuario(principal.getUsername());
        String token = tokenService.generateToken(usuarioAutenticado);

        return ResponseEntity.ok(token);

    }

    @GetMapping("/refresh")
    @Operation(
        summary = "Renovar token de acesso",
        description = "Renova o token de acesso do usuário"
    )
    public ResponseEntity<String> refresh(
            @RequestHeader("Authorization") String authHeader) {
        
        String token = authHeader.replace("Bearer ", "");
        DecodedJWT tokenDecodificado = JWT.decode(token);
        Claim claimDataLimite = tokenDecodificado.getClaim("dataLimiteRenovacao");
        LocalDate dataLimite = LocalDate.parse(claimDataLimite.asString());
        LocalDate hoje = LocalDate.now();
        if (hoje.isAfter(dataLimite)) {
            return ResponseEntity.badRequest().build();
        }

        String nomeUsuario = tokenDecodificado.getSubject();
        Usuario usuario = usuarioService.getByNomeUsuario(nomeUsuario);
        String tokenNovo = tokenService.generateToken(usuario);

        return ResponseEntity.ok(tokenNovo);

    }

}
