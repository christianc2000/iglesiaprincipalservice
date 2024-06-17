/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Dto.AuthRequestDTO;
import com.model.Dto.AuthResponseDTO;
import com.model.Dto.JwtResponseDTO;
import com.security.CustomUserDetails;
import com.security.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author USER
 */
@RestController
@RequestMapping("/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/")
    public HttpStatus healthCheck() {
        // Puedes agregar lógica adicional aquí si es necesario
        return HttpStatus.OK;
    }

    /*@PostMapping("/auth/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            System.out.println("EN TRUE IS AUTHENTICATED");
            return JwtResponseDTO.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername())).build();
        } else {
            System.out.println("Acceso INCORRECTO DEL " + authRequestDTO.getUsername());
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }*/
 /*@PostMapping("/auth/login")
    public AuthResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
        );

        if (authentication.isAuthenticated()) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtService.GenerateToken(userDetails.getUsername());

            // Construir el objeto de respuesta
            AuthResponseDTO response = AuthResponseDTO.builder()
                    .id(userDetails.getId())
                    .accessToken(token)
                    .username(userDetails.getUsername())
                    .email(userDetails.getCorreo())
                    .roles(userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
                    .build();

            return response;
        } else {
            System.out.println("Acceso INCORRECTO DEL " + authRequestDTO.getUsername());
            throw new UsernameNotFoundException("Invalid user request..!!");
        }
    }*/
    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public void authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
        );

        if (authentication.isAuthenticated()) {
            try {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                String token = jwtService.GenerateToken(userDetails.getUsername());
                
                // Crear la cookie con el token
                Cookie cookie = new Cookie("accessToken", token);
                cookie.setHttpOnly(true); // Asegura que la cookie no sea accesible a través de scripts del lado del cliente
                cookie.setSecure(false); // Asegura que la cookie solo se envíe a través de conexiones HTTPS
                cookie.setPath("/"); // Define el camino donde la cookie es válida
                cookie.setMaxAge(24 * 60 * 60); // Configura el tiempo de vida de la cookie (1 día en este caso)
                
                // Añadir la cookie a la respuesta
                response.addCookie(cookie);
                
                // Construir el objeto de respuesta
                AuthResponseDTO responseBody = AuthResponseDTO.builder()
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .email(userDetails.getCorreo())
                        .token(token)
                        .roles(userDetails.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                        .build();
                
                // Escribir el objeto de respuesta en el cuerpo de la respuesta
                // Escribir el objeto de respuesta en el cuerpo de la respuesta
                response.setContentType("application/json");
                response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
            } catch (IOException ex) {
                Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Acceso INCORRECTO DEL " + authRequestDTO.getUsername());
            throw new UsernameNotFoundException("Invalid user request..!!");
        }
    }
}
