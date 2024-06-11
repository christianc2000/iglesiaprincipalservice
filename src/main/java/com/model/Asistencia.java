/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import javax.validation.constraints.NotBlank;
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
    
    @NotBlank(message = "La fecha de la asistencia no puede estar vacío")
    private String fecha;
    
    @NotBlank(message = "El miembro de la asistencia no puede estar vacío")
    private String miembroId;
    
    @NotBlank(message = "El evento de la asistencia no puede estar vacío")
    private String eventoId;
    
     public Asistencia() {
    }

    @Override
    public String toString() {
        return "Asistencia [id=" + id + ", fecha=" + fecha + ", miembro=" + miembroId + ", evento=" + eventoId + "]";
    }
    
}
