/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.resolver;

import com.model.Evento;
import com.repository.EventoRepository;
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
public class EventoResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final EventoRepository eventoRepository;

    @Autowired
    public EventoResolver(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @QueryMapping
    public List<Evento> findAllEventos() {
        return eventoRepository.findAll();
    }

    @QueryMapping
    public Optional<Evento> eventoById(@Argument String id) {
        return eventoRepository.findById(id);
    }

    @MutationMapping
    public Evento createEvento(@Argument String nombre, @Argument String fecha, @Argument String lugar) {
        Evento evento = new Evento();
        evento.setNombre(nombre);
        evento.setFecha(fecha);
        evento.setLugar(lugar);
        eventoRepository.save(evento);
        return evento;
    }

    @MutationMapping
    public Evento updateEvento(@Argument String id, @Argument String nombre, @Argument String fecha, @Argument String lugar) {
        Optional<Evento> optionalEvento = eventoRepository.findById(id);
        if (optionalEvento.isPresent()) {
            Evento evento = optionalEvento.get();
            evento.setNombre(nombre);
            evento.setFecha(fecha);
            evento.setLugar(lugar);
            return eventoRepository.save(evento);
        } else {
            throw new RuntimeException("No se encontró el evento con ID" + id);
        }
    }

    @MutationMapping
    public boolean deleteEvento(@Argument String id) {
        // Verificar si el evento existe
        if (!eventoRepository.existsById(id)) {
            // Si no existe, devolver false
            return false;
        }

        try {
            // Intentar eliminar el evento
            eventoRepository.deleteById(id);
            // Si se eliminó correctamente, devolver true
            return true;
        } catch (Exception e) {
            // Si no se puede eliminar (por ejemplo, por una restricción de integridad), devolver false
            return false;
        }
    }

    @QueryMapping
    public List<Evento> buscarEventos(@Argument String criterio) {
        return eventoRepository.findByNombreContainingOrLugarContainingOrFechaContaining(criterio, criterio, criterio);
    }
}
