/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.repository;

import com.model.Usuario;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author USER
 */
public interface UsuarioRepository extends MongoRepository<Usuario, String>{
    Optional<Usuario> findByMiembroId(String miembroId);
}
