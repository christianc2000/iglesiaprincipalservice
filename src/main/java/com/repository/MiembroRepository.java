/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.repository;

import com.model.Miembro;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author USER
 */
public interface MiembroRepository extends MongoRepository<Miembro, String>{
    
}
