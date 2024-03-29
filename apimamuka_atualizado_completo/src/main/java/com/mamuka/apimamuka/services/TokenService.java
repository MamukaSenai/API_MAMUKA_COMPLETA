package com.mamuka.apimamuka.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.mamuka.apimamuka.models.UsuarioModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(UsuarioModel usuario) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("api-mamuka")
                    .withSubject(usuario.getEmail())
                    .withClaim("perfil", usuario.getTipo_usuario().getRole())
                    .withClaim("nomeUsuario", usuario.getNome()) // para enviar um Claim, procurar no material da materia, primeiro dia sobre o assunto dia 06 do 11
                    .withClaim("idUsuario", usuario.getId().toString())
                    .withExpiresAt(gerarValidadeToken())
                    .sign(algoritmo);

            return token;
        } catch(JWTCreationException exception) {
            throw new RuntimeException("Erro na criação do token: ", exception);
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            return JWT.require(algoritmo)
                    .withIssuer("api-mamuka")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch(JWTCreationException exception) {
            return "";
        }
    }

    public Instant gerarValidadeToken() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
