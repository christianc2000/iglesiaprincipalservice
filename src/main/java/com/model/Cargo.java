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
@Document(collection = "cargos")
public class Cargo {

    @Id
    private String id;

    @NotBlank(message = "El nombre de cargo no puede estar vacío")
    private String nombre;

    public Cargo() {
    }

    @Override
    public String toString() {
        return "Cargo [id=" + id + ", nombre=" + nombre + "]";
    }
}
