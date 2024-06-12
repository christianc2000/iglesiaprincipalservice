/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Christian
 */
@Getter
@Setter
@Document(collection = "asistencias")
public class Asistencia {
    @Id
    private String id;
    private String fecha;
    private String miembroId;
    private String eventoId;
    
     public Asistencia() {
    }

    @Override
    public String toString() {
        return "Asistencia [id=" + id + ", fecha=" + fecha + ", miembro=" + miembroId + ", evento=" + eventoId + "]";
    }
    
}
