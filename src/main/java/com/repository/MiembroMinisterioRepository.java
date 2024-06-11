/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.repository;

import com.model.MiembroMinisterio;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Christian
 */
public interface MiembroMinisterioRepository extends MongoRepository<MiembroMinisterio, String> {
    List<MiembroMinisterio> findByMinisterioId(String ministerioId);
    MiembroMinisterio findByMinisterioIdAndMiembroIdAndCargoId(String ministerioId, String miembroId, String cargoId);
}
