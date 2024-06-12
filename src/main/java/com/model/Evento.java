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
@Document(collection = "eventos")
public class Evento {

    @Id
    private String id;
    private String nombre;
    private String fecha;
    private String lugar;

    public Evento() {
    }

    @Override
    public String toString() {
        return "Evento [id=" + id + ", nombre=" + nombre + ", fecha=" + fecha + ", lugar=" + lugar + "]";
    }
}
