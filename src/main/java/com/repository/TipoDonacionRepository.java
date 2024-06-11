/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.repository;

import com.model.TipoDonacion;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Christian
 */
public interface TipoDonacionRepository extends MongoRepository<TipoDonacion, String>{
    
}
