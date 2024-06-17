/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.security;

import com.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends Usuario implements UserDetails {

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Usuario usuario) {
        super(usuario.getId(), usuario.getRol(), usuario.getEstado(), usuario.getUsername(), usuario.getCorreo(), usuario.getMiembroId(), usuario.getPassword());
        List<GrantedAuthority> auths = new ArrayList<>();
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
