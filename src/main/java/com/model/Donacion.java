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
@Document(collection = "donacions")
public class Donacion {

    @Id
    private String id;
    private String monto;
    private String fecha;
    private String miembroId;
    private String eventoId;
    private String tipoDonacionId;

    public Donacion() {
    }

    @Override
    public String toString() {
        return "Donacion [id=" + id + ", monto=" + monto + ", fecha=" + fecha + ", miembro=" + miembroId + ", evento=" + eventoId + ", tipoDonacion" + tipoDonacionId + "]";
    }
}
