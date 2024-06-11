/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.resolver;

import com.model.Asistencia;
import com.model.AsistenciaMiembroEvento;
import com.model.Evento;
import com.model.Miembro;
import com.repository.AsistenciaRepository;
import com.repository.EventoRepository;
import com.repository.MiembroRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
public class AsistenciaResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final AsistenciaRepository asistenciaRepository;
    private final EventoRepository eventoRepository;
    private final MiembroRepository miembroRepository;

    @Autowired
    public AsistenciaResolver(AsistenciaRepository asistenciaRepository, EventoRepository eventoRepository, MiembroRepository miembroRepository) {
        this.asistenciaRepository = asistenciaRepository;
        this.eventoRepository = eventoRepository;
        this.miembroRepository = miembroRepository;
    }

    @QueryMapping
    public List<Asistencia> findAllAsistencias() {
        return asistenciaRepository.findAll();
    }

    @QueryMapping
    public Optional<Asistencia> asistenciaById(@Argument String id) {
        return asistenciaRepository.findById(id);
    }

    @QueryMapping
    public List<Asistencia> asistenciasPorEvento(@Argument String eventoId) {
        // Implementa la lógica para recuperar las asistencias por ID de evento
        return asistenciaRepository.findByEventoId(eventoId);
    }

    @QueryMapping
    public List<AsistenciaMiembroEvento> asistenciasPorEventoMiembro(@Argument String eventoId) {
        List<AsistenciaMiembroEvento> asistenciaEventoMiembroList = new ArrayList<>();

        List<Asistencia> asistencias = asistenciaRepository.findByEventoId(eventoId);

        for (Asistencia asistencia : asistencias) {
            AsistenciaMiembroEvento asistenciaEventoMiembro = new AsistenciaMiembroEvento();

            Optional<Miembro> optionalMiembro = miembroRepository.findById(asistencia.getMiembroId());
            Optional<Evento> optionalEvento = eventoRepository.findById(asistencia.getEventoId());

            if (optionalMiembro.isPresent() && optionalEvento.isPresent()) {
                asistenciaEventoMiembro.setEvento(optionalEvento.get());
                asistenciaEventoMiembro.setMiembro(optionalMiembro.get());
                asistenciaEventoMiembro.setFecha(asistencia.getFecha());
                asistenciaEventoMiembro.setId(asistencia.getId());
                asistenciaEventoMiembroList.add(asistenciaEventoMiembro);
            }
        }

        return asistenciaEventoMiembroList;
    }

    @MutationMapping
    public Asistencia createAsistencia(@Argument String eventoId, @Argument String miembroId) {
        Asistencia existingAsistencia = asistenciaRepository.findByEventoIdAndMiembroId(eventoId, miembroId);
        if (existingAsistencia != null) {
            return existingAsistencia; // Devolver la asistencia existente si se encuentra
        }
        // Si no existe, crear una nueva asistencia
        Asistencia asistencia = new Asistencia();
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String fechaFormateada = fechaActual.format(formatter);
        asistencia.setFecha(fechaFormateada);
        asistencia.setEventoId(eventoId);
        asistencia.setMiembroId(miembroId);
        asistenciaRepository.save(asistencia);
        return asistencia;
    }

    @MutationMapping
    public Asistencia updateAsistencia(@Argument String id, @Argument String eventoId, @Argument String miembroId) {
        Optional<Asistencia> optionalAsistencia = asistenciaRepository.findById(id);
        if (optionalAsistencia.isPresent()) {
            Asistencia asistencia = optionalAsistencia.get();
            LocalDateTime fechaActual = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String fechaFormateada = fechaActual.format(formatter);
            asistencia.setFecha(fechaFormateada);
            asistencia.setEventoId(eventoId);
            asistencia.setMiembroId(miembroId);
            return asistenciaRepository.save(asistencia);
        } else {
            throw new RuntimeException("No se encontró el Asistencia con ID" + id);
        }
    }

    @MutationMapping
    public boolean deleteAsistencia(@Argument String id) {
        if (!asistenciaRepository.existsById(id)) {
            return false;
        }
        try {
            asistenciaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
