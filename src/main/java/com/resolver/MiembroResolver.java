/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.resolver;

import com.model.Miembro;
import com.model.Usuario;
import com.repository.MiembroRepository;
import com.repository.UsuarioRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

/**
 *
 * @author USER
 */
@Controller
public class MiembroResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final MiembroRepository miembroRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public MiembroResolver(MiembroRepository miembroRepository, UsuarioRepository usuarioRepository) {
        this.miembroRepository = miembroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @QueryMapping
    public List<Miembro> findAllMiembros() {
        return miembroRepository.findAll();
    }

    @QueryMapping
    public Optional<Miembro> miembroById(@Argument String id) {
        return miembroRepository.findById(id);
    }

    @MutationMapping
    public Miembro createMiembro(@Argument String ci, @Argument String nombre, @Argument String apellido, @Argument String foto, @Argument String fechaNacimiento, @Argument String celular, @Argument String genero) {
        Miembro miembro = new Miembro();
        miembro.setCi(ci);
        miembro.setNombre(nombre);
        miembro.setApellido(apellido);
        miembro.setFoto(foto);
        miembro.setFechaNacimiento(fechaNacimiento);
        miembro.setCelular(celular);
        miembro.setGenero(genero);
        return miembroRepository.save(miembro);
    }

    @MutationMapping
    public Miembro updateMiembro(@Argument String id, @Argument String ci, @Argument String nombre, @Argument String apellido, @Argument String foto, @Argument String fechaNacimiento, @Argument String celular, @Argument String genero) {
        Optional<Miembro> optionalMiembro = miembroRepository.findById(id);
        if (optionalMiembro.isPresent()) {
            Miembro miembro = optionalMiembro.get();
            miembro.setCi(ci);
            miembro.setNombre(nombre);
            miembro.setApellido(apellido);
            miembro.setFoto(foto);
            miembro.setFechaNacimiento(fechaNacimiento);
            miembro.setCelular(celular);
            miembro.setGenero(genero);
            return miembroRepository.save(miembro);
        } else {
            throw new RuntimeException("No se encontró el Miembro con ID" + id);
        }
    }

    @MutationMapping
    public boolean deleteMiembro(@Argument String id) {
        if (!miembroRepository.existsById(id)) {
            return false;
        }
        try {
            miembroRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @MutationMapping
    public boolean deleteMiembroUsuario(@Argument String id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByMiembroId(id);
        if (usuarioOptional.isPresent()) {
            // Si se encuentra el usuario, eliminarlo
            Usuario usuario = usuarioOptional.get();
            try {
                usuarioRepository.deleteById(usuario.getId());
            } catch (Exception e) {
                // Manejar cualquier excepción al eliminar el usuario
                e.printStackTrace();
                return false;
            }
        }

        // Eliminar el miembro
        try {
            miembroRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            // Manejar cualquier excepción al eliminar el miembro
            e.printStackTrace();
            return false;
        }
    }
}
