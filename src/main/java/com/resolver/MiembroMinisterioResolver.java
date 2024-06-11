/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.resolver;

import com.model.Cargo;
import com.model.Miembro;
import com.model.MiembroMinisterio;
import com.model.MiembroMinisterioCargo;
import com.model.Ministerio;
import com.repository.CargoRepository;
import com.repository.MiembroMinisterioRepository;
import com.repository.MiembroRepository;
import com.repository.MinisterioRepository;
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
public class MiembroMinisterioResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final MiembroMinisterioRepository miembroMinisterioRepository;
    private final MinisterioRepository ministerioRepository;
    private final MiembroRepository miembroRepository;
    private final CargoRepository cargoRepository;

    @Autowired
    public MiembroMinisterioResolver(MiembroMinisterioRepository miembroMinisterioRepository, MinisterioRepository ministerioRepository, MiembroRepository miembroRepository, CargoRepository cargoRepository) {
        this.miembroMinisterioRepository = miembroMinisterioRepository;
        this.ministerioRepository = ministerioRepository;
        this.miembroRepository = miembroRepository;
        this.cargoRepository = cargoRepository;
    }

    @QueryMapping
    public List<MiembroMinisterio> findAllMiembroMinisterios() {
        return miembroMinisterioRepository.findAll();
    }

    @QueryMapping
    public Optional<MiembroMinisterio> miembroMinisterioById(@Argument String id) {
        return miembroMinisterioRepository.findById(id);
    }

    @QueryMapping
    public List<MiembroMinisterio> miembroMinisteriosPorMinisterio(@Argument String ministerioId) {
        // Implementa la lógica para recuperar las asistencias por ID de evento
        return miembroMinisterioRepository.findByMinisterioId(ministerioId);
    }

    @QueryMapping
    public List<MiembroMinisterioCargo> miembroMinisteriosPorMinisterioCargo(@Argument String ministerioId) {
        List<MiembroMinisterioCargo> miembroMinisterioCargoList = new ArrayList<>();

        List<MiembroMinisterio> miembroMinisterios = miembroMinisterioRepository.findByMinisterioId(ministerioId);

        for (MiembroMinisterio miembroMinisterio : miembroMinisterios) {
            MiembroMinisterioCargo miembroMinisterioCargo = new MiembroMinisterioCargo();

            Optional<Miembro> optionalMiembro = miembroRepository.findById(miembroMinisterio.getMiembroId());
            Optional<Ministerio> optionalMinisterio = ministerioRepository.findById(miembroMinisterio.getMinisterioId());
            Optional<Cargo> optionalCargo = cargoRepository.findById(miembroMinisterio.getCargoId());

            if (optionalMiembro.isPresent() && optionalMinisterio.isPresent() && optionalCargo.isPresent()) {
                miembroMinisterioCargo.setId(miembroMinisterio.getId());
                miembroMinisterioCargo.setFechaInicio(miembroMinisterio.getFechaInicio());
                miembroMinisterioCargo.setFechaFin(miembroMinisterio.getFechaFin());
                miembroMinisterioCargo.setMiembro(optionalMiembro.get());
                miembroMinisterioCargo.setCargo(optionalCargo.get());
                miembroMinisterioCargo.setMinisterio(optionalMinisterio.get());
                miembroMinisterioCargoList.add(miembroMinisterioCargo);
            }
        }

        return miembroMinisterioCargoList;
    }

    @MutationMapping
    public MiembroMinisterio createMiembroMinisterio(@Argument String fechaInicio, @Argument String ministerioId, @Argument String miembroId, @Argument String cargoId) {
        MiembroMinisterio existingMiembroMinisterio = miembroMinisterioRepository.findByMinisterioIdAndMiembroIdAndCargoId(ministerioId, miembroId, cargoId);
        if (existingMiembroMinisterio != null) {
            //return existingMiembroMinisterio; // Devolver la donación existente si se encuentra
            if (existingMiembroMinisterio.getFechaFin() == "") {
                //Cuando la fecha Fin NO finalizó
                return existingMiembroMinisterio; // Devolver la donación existente si se encuentra
            }
        }
        MiembroMinisterio miembroMinisterio = new MiembroMinisterio();
        miembroMinisterio.setFechaInicio(fechaInicio);
        miembroMinisterio.setFechaFin("");
        miembroMinisterio.setMiembroId(miembroId);
        miembroMinisterio.setMinisterioId(ministerioId);
        miembroMinisterio.setCargoId(cargoId);
        miembroMinisterioRepository.save(miembroMinisterio);
        return miembroMinisterio;
    }

    @MutationMapping
    public Boolean finalizarMiembroMinisterio(@Argument String id) {
        Optional<MiembroMinisterio> optionalMiembroMinisterio = miembroMinisterioRepository.findById(id);
        if (optionalMiembroMinisterio.isPresent()) {
            MiembroMinisterio miembroMinisterio = optionalMiembroMinisterio.get();
            try {
                LocalDateTime fechaActual = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                String fechaFormateada = fechaActual.format(formatter);
                miembroMinisterio.setFechaFin(fechaFormateada);
                miembroMinisterioRepository.save(miembroMinisterio);
                return true;
            } catch (Exception e) {
                return false;
            }

        } else {
            return false;
        }
    }

    @MutationMapping
    public MiembroMinisterio updateMiembroMinisterio(@Argument String id, @Argument String fechaInicio, @Argument String fechaFin, @Argument String ministerioId, @Argument String miembroId, @Argument String cargoId) {
        Optional<MiembroMinisterio> optionalMiembroMinisterio = miembroMinisterioRepository.findById(id);
        if (optionalMiembroMinisterio.isPresent()) {
            MiembroMinisterio miembroMinisterio = optionalMiembroMinisterio.get();
            miembroMinisterio.setFechaInicio(fechaInicio);
            miembroMinisterio.setFechaFin(fechaFin);
            miembroMinisterio.setMiembroId(miembroId);
            miembroMinisterio.setMinisterioId(ministerioId);
            miembroMinisterio.setCargoId(cargoId);
            return miembroMinisterioRepository.save(miembroMinisterio);
        } else {
            throw new RuntimeException("No se encontró la Donación con ID" + id);
        }
    }

    @MutationMapping
    public boolean deleteMiembroMinisterio(@Argument String id) {
        if (!miembroMinisterioRepository.existsById(id)) {
            return false;
        }
        try {
            miembroMinisterioRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
