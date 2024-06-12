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
@Document(collection = "ministerios")
public class Ministerio {

    @Id
    private String id;
    private String nombre;
    private String descripcion;

    public Ministerio() {
    }

    @Override
    public String toString() {
        return "Ministerio [id=" + id + ", monto=" + nombre + ", descripcion=" + descripcion + "]";
    }
}
