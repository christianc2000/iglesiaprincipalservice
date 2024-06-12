/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service;

import com.model.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author USER
 */
public class CustomUserDetails extends Usuario implements UserDetails {
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Usuario usuario) {
        this.setUsername(usuario.getUsername()); // Usa el setter heredado de Usuario
        this.setPassword(usuario.getPassword());

        List<GrantedAuthority> auths = new ArrayList<>();
        // Asigna el rol basado en el valor de 'rol'
        if ("A".equals(usuario.getRol())) {
            auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if ("G".equals(usuario.getRol())) {
           auths.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
