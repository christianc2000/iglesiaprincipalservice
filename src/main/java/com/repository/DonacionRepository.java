/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.repository;

import com.model.Donacion;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Christian
 */
public interface DonacionRepository extends MongoRepository<Donacion, String> {
    List<Donacion> findByEventoId(String eventoId);
    Donacion findByEventoIdAndMiembroIdAndTipoDonacionId(String eventoId, String miembroId, String tipoDonacionId);
}
