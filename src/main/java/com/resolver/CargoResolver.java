/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.resolver;

import com.model.Cargo;
import com.repository.CargoRepository;
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
//Mutacion y query de cargo
@Controller
public class CargoResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final CargoRepository cargoRepository;

    @Autowired
    public CargoResolver(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @QueryMapping
    public List<Cargo> findAllCargos() {
        return cargoRepository.findAll();
    }

    @QueryMapping
    public Optional<Cargo> cargoById(@Argument String id) {
        return cargoRepository.findById(id);
    }

    @MutationMapping
    public Cargo createCargo(@Argument String nombre) {
        Cargo cargo = new Cargo();
        cargo.setNombre(nombre);
        cargoRepository.save(cargo);
        return cargo;
    }

    @MutationMapping
    public Cargo updateCargo(@Argument String id, @Argument String nombre) {
        Optional<Cargo> optionalCargo = cargoRepository.findById(id);
        if (optionalCargo.isPresent()) {
            Cargo cargo = optionalCargo.get();
            cargo.setNombre(nombre);

            return cargoRepository.save(cargo);
        } else {
            throw new RuntimeException("No se encontró el cargo con ID" + id);
        }
    }

    @MutationMapping
    public boolean deleteCargo(@Argument String id) {
        // Verificar si el evento existe
        if (!cargoRepository.existsById(id)) {
            // Si no existe, devolver false
            return false;
        }

        try {
            // Intentar eliminar el evento
            cargoRepository.deleteById(id);
            // Si se eliminó correctamente, devolver true
            return true;
        } catch (Exception e) {
            // Si no se puede eliminar (por ejemplo, por una restricción de integridad), devolver false
            return false;
        }
    }
}
