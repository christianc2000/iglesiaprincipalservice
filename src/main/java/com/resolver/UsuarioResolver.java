/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.resolver;

import com.model.Miembro;
import com.model.Usuario;
import com.model.UsuarioMiembro;
import com.repository.MiembroRepository;
import com.repository.UsuarioRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

/**
 *
 * @author USER
 */
@Controller
public class UsuarioResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final UsuarioRepository usuarioRepository;
    private final MiembroRepository miembroRepository;

    @Autowired
    public UsuarioResolver(UsuarioRepository usuarioRepository, MiembroRepository miembroRepository) {
        this.usuarioRepository = usuarioRepository;
        this.miembroRepository = miembroRepository;
    }

    @QueryMapping
    public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @QueryMapping
    public Optional<Usuario> usuarioById(@Argument String id) {
        return usuarioRepository.findById(id);
    }

    @QueryMapping
    public Optional<Usuario> usuarioByMiembroId(@Argument String miembroId) {
        return usuarioRepository.findByMiembroId(miembroId);
    }

    @QueryMapping
    public List<UsuarioMiembro> usuariosConMiembros() {
        List<UsuarioMiembro> usuariosConMiembros = new ArrayList<>();

        List<Usuario> usuarios = usuarioRepository.findAll();
        for (Usuario usuario : usuarios) {
            UsuarioMiembro usuarioMiembro = new UsuarioMiembro();
            usuarioMiembro.setUsuario(usuario);

            Miembro miembro = miembroRepository.findById(usuario.getMiembroId()).orElse(null);
            usuarioMiembro.setMiembro(miembro);

            usuariosConMiembros.add(usuarioMiembro);
        }

        return usuariosConMiembros;
    }

    @MutationMapping
    public Usuario createUsuario(@Argument String rol, @Argument String estado, @Argument String correo, @Argument String password, @Argument String miembroId) {
        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setRol(rol);
        usuario.setUsername(correo);
        usuario.setEstado(estado);
        usuario.setMiembroId(miembroId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = password;
        String encodedPassword = encoder.encode(rawPassword);
        usuario.setPassword(encodedPassword);
        return usuarioRepository.save(usuario);
    }

    @MutationMapping
    public Usuario createMiembroUsuario(@Argument String ci, @Argument String nombre, @Argument String apellido, @Argument String foto, @Argument String fechaNacimiento, @Argument String celular, @Argument String genero, @Argument String rol, @Argument String estado, @Argument String correo, @Argument String password) {
        //CREANDO MIEMBRO
        Miembro miembro = new Miembro();
        miembro.setCi(ci);
        miembro.setNombre(nombre);
        miembro.setApellido(apellido);
        miembro.setFoto(foto);
        miembro.setFechaNacimiento(fechaNacimiento);
        miembro.setCelular(celular);
        miembro.setGenero(genero);
        miembroRepository.save(miembro);
        //CREANDO USUARIO
        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setRol(rol);
        usuario.setUsername(correo);
        usuario.setEstado(estado);
        usuario.setMiembroId(miembro.getId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = password;
        String encodedPassword = encoder.encode(rawPassword);
        usuario.setPassword(encodedPassword);
        return usuarioRepository.save(usuario);
    }

    @MutationMapping
    public Usuario updateUsuario(@Argument String id, @Argument String rol, @Argument String estado, @Argument String correo, @Argument String password, @Argument String miembroId) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setRol(rol);
            usuario.setEstado(estado);
            usuario.setUsername(correo);
            usuario.setCorreo(correo);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String rawPassword = password;
            String encodedPassword = encoder.encode(rawPassword);
            usuario.setPassword(encodedPassword);
            usuario.setMiembroId(miembroId);
            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("No se encontró el Usuario con ID" + id);
        }
    }

    @MutationMapping
    public boolean deleteUsuario(@Argument String id) {
        if (!usuarioRepository.existsById(id)) {
            return false;
        }
        try {
            usuarioRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
