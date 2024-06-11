/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.repository;

import com.model.Evento;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Christian
 */
public interface EventoRepository extends MongoRepository<Evento, String> {
     // Métodos personalizados si los necesitas
    List<Evento> findByNombreContainingOrLugarContainingOrFechaContaining(String nombre, String lugar, String fecha);
}
