/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.resolver;

import com.model.TipoDonacion;
import com.repository.TipoDonacionRepository;
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
public class TipoDonacionResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    private final TipoDonacionRepository tipoDonacionRepository;

    @Autowired
    public TipoDonacionResolver(TipoDonacionRepository tipoDonacionRepository) {
        this.tipoDonacionRepository = tipoDonacionRepository;
    }
    
    @QueryMapping
    public List<TipoDonacion> findAllTipoDonacions() {
        return tipoDonacionRepository.findAll();
    }

    @QueryMapping
    public Optional<TipoDonacion> tipoDonacionById(@Argument String id) {
        return tipoDonacionRepository.findById(id);
    }

    @MutationMapping
    public TipoDonacion createTipoDonacion(@Argument String nombre) {
        TipoDonacion tipoDonacion = new TipoDonacion();
        tipoDonacion.setNombre(nombre);
        tipoDonacionRepository.save(tipoDonacion);
        return tipoDonacion;
    }

    @MutationMapping
    public TipoDonacion updateTipoDonacion(@Argument String id, @Argument String nombre) {
        Optional<TipoDonacion> optionalTipoDonacion = tipoDonacionRepository.findById(id);
        if (optionalTipoDonacion.isPresent()) {
            TipoDonacion tipoDonacion = optionalTipoDonacion.get();
            tipoDonacion.setNombre(nombre);
            return tipoDonacionRepository.save(tipoDonacion);
        } else {
            throw new RuntimeException("No se encontró el tipoDonacion con ID" + id);
        }
    }

    @MutationMapping
    public boolean deleteTipoDonacion(@Argument String id) {
        if (!tipoDonacionRepository.existsById(id)) {
            return false;
        }
        try {
            tipoDonacionRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
