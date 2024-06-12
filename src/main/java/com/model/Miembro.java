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
 * @author USER
 */
@Getter
@Setter
@Document(collection = "miembros")
public class Miembro {
    
    @Id
    private String id;
    private String ci;
    private String nombre;
    private String apellido;
    private String foto;
    private String fechaNacimiento;
    private String celular;
    private String genero;

    @Override
    public String toString() {
        return "Miembro [id=" + id + ", ci=" + ci + ", nombre=" + nombre + ", apellido=" + apellido +", foto=" + foto +", fechaNacimiento=" + fechaNacimiento +",celular="+celular+", genero=" + genero +"]";
    }
}
