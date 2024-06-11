/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.repository;

import com.model.Asistencia;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Christian
 */
public interface AsistenciaRepository extends MongoRepository<Asistencia, String>{
    List<Asistencia> findByEventoId(String eventoId);
    Asistencia findByEventoIdAndMiembroId(String eventoId,String miembroId);
}
