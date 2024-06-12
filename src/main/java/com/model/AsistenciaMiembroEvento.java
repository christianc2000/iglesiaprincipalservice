/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 *
 * @author USER
 */
@Getter
@Setter
public class AsistenciaMiembroEvento {
    @Id
    private String id;
    
    private String fecha;
    private Miembro miembro;
    private Evento evento;
    
     public AsistenciaMiembroEvento() {
    }
}
