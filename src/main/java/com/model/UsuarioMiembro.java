/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author USER
 */
@Getter
@Setter

public class UsuarioMiembro {
 @Id
    private String id;
    private String rol;
    private String estado;
    private String username;
    private String correo;
    private Miembro miembro;
}
