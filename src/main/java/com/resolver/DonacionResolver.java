/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.resolver;

import com.model.Donacion;
import com.model.DonacionMiembroEvento;
import com.model.Evento;
import com.model.Miembro;
import com.model.TipoDonacion;
import com.repository.DonacionRepository;
import com.repository.EventoRepository;
import com.repository.MiembroRepository;
import com.repository.TipoDonacionRepository;
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
public class DonacionResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final DonacionRepository donacionRepository;
    private final EventoRepository eventoRepository;
    private final MiembroRepository miembroRepository;
    private final TipoDonacionRepository tipoDonacionRepository;

    @Autowired
    public DonacionResolver(DonacionRepository donacionRepository, EventoRepository eventoRepository, MiembroRepository miembroRepository, TipoDonacionRepository tipoDonacionRepository) {
        this.donacionRepository = donacionRepository;
        this.eventoRepository = eventoRepository;
        this.miembroRepository = miembroRepository;
        this.tipoDonacionRepository = tipoDonacionRepository;
    }

    @QueryMapping
    public List<Donacion> findAllDonacions() {
        return donacionRepository.findAll();
    }

    @QueryMapping
    public Optional<Donacion> donacionById(@Argument String id) {
        return donacionRepository.findById(id);
    }

    @QueryMapping
    public List<Donacion> donacionesPorEvento(@Argument String eventoId) {
        // Implementa la lógica para recuperar las asistencias por ID de evento
        return donacionRepository.findByEventoId(eventoId);
    }

    @QueryMapping
    public List<DonacionMiembroEvento> donacionesPorEventoMiembro(@Argument String eventoId) {
        List<DonacionMiembroEvento> donacionEventoMiembroList = new ArrayList<>();

        List<Donacion> donaciones = donacionRepository.findByEventoId(eventoId);

        for (Donacion donacion : donaciones) {
            DonacionMiembroEvento donacionEventoMiembro = new DonacionMiembroEvento();

            Optional<Miembro> optionalMiembro = miembroRepository.findById(donacion.getMiembroId());
            Optional<Evento> optionalEvento = eventoRepository.findById(donacion.getEventoId());
            Optional<TipoDonacion> optionalTipoDonacion = tipoDonacionRepository.findById(donacion.getTipoDonacionId());

            if (optionalMiembro.isPresent() && optionalEvento.isPresent() && optionalTipoDonacion.isPresent()) {
                donacionEventoMiembro.setEvento(optionalEvento.get());
                donacionEventoMiembro.setMiembro(optionalMiembro.get());
                donacionEventoMiembro.setTipoDonacion(optionalTipoDonacion.get());
                donacionEventoMiembro.setMonto(donacion.getMonto());
                donacionEventoMiembro.setFecha(donacion.getFecha());
                donacionEventoMiembro.setId(donacion.getId());
                donacionEventoMiembroList.add(donacionEventoMiembro);
            }
        }

        return donacionEventoMiembroList;
    }

    @MutationMapping
    public Donacion createDonacion(@Argument String monto, @Argument String miembroId, @Argument String eventoId, @Argument String tipoDonacionId) {
        Donacion existingDonacion = donacionRepository.findByEventoIdAndMiembroIdAndTipoDonacionId(eventoId, miembroId, tipoDonacionId);
        if (existingDonacion != null) {
            return existingDonacion; // Devolver la donación existente si se encuentra
        }
        Donacion donacion = new Donacion();
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String fechaFormateada = fechaActual.format(formatter);
        donacion.setMonto(monto);
        donacion.setFecha(fechaFormateada);
        donacion.setMiembroId(miembroId);
        donacion.setEventoId(eventoId);
        donacion.setTipoDonacionId(tipoDonacionId);
        donacionRepository.save(donacion);
        return donacion;
    }

    @MutationMapping
    public Donacion updateDonacion(@Argument String id, @Argument String monto, @Argument String miembroId, @Argument String eventoId, @Argument String tipoDonacionId) {
        Optional<Donacion> optionalDonacion = donacionRepository.findById(id);
        if (optionalDonacion.isPresent()) {
            Donacion donacion = optionalDonacion.get();
            LocalDateTime fechaActual = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String fechaFormateada = fechaActual.format(formatter);
            donacion.setMonto(monto);
            donacion.setFecha(fechaFormateada);
            donacion.setMiembroId(miembroId);
            donacion.setEventoId(eventoId);
            donacion.setTipoDonacionId(tipoDonacionId);
            return donacionRepository.save(donacion);
        } else {
            throw new RuntimeException("No se encontró la Donación con ID" + id);
        }
    }

    @MutationMapping
    public boolean deleteDonacion(@Argument String id) {
        if (!donacionRepository.existsById(id)) {
            return false;
        }
        try {
            donacionRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
