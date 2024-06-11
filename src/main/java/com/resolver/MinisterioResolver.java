/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.resolver;

import com.model.Ministerio;
import com.repository.MinisterioRepository;
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
 * @author Christian
 */
@Controller
public class MinisterioResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final MinisterioRepository ministerioRepository;

    @Autowired
    public MinisterioResolver(MinisterioRepository ministerioRepository) {
        this.ministerioRepository = ministerioRepository;
    }

    @QueryMapping
    public List<Ministerio> findAllMinisterios() {
        return ministerioRepository.findAll();
    }

    @QueryMapping
    public Optional<Ministerio> ministerioById(@Argument String id) {
        return ministerioRepository.findById(id);
    }

    @MutationMapping
    public Ministerio createMinisterio(@Argument String nombre, @Argument String descripcion) {
        Ministerio ministerio = new Ministerio();
        ministerio.setNombre(nombre);
        ministerio.setDescripcion(descripcion);
        ministerioRepository.save(ministerio);
        return ministerio;
    }

    @MutationMapping
    public Ministerio updateMinisterio(@Argument String id, @Argument String nombre, @Argument String descripcion) {
        Optional<Ministerio> optionalMinisterio = ministerioRepository.findById(id);
        if (optionalMinisterio.isPresent()) {
            Ministerio ministerio = optionalMinisterio.get();
            ministerio.setNombre(nombre);
            ministerio.setDescripcion(descripcion);
            return ministerioRepository.save(ministerio);
        } else {
            throw new RuntimeException("No se encontró el Ministerio con ID" + id);
        }
    }

    @MutationMapping
    public boolean deleteMinisterio(@Argument String id) {
        if (!ministerioRepository.existsById(id)) {
            return false;
        }
        try {
            ministerioRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
