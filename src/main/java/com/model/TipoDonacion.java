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
@Document(collection = "tipo_donacions")
public class TipoDonacion {

    @Id
    private String id;
    private String nombre;

    public TipoDonacion() {
    }

    @Override
    public String toString() {
        return "TipoDonacion [id=" + id + ", nombre=" + nombre + "]";
    }
}
