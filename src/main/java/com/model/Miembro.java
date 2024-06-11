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
 * @author USER
 */
@Getter
@Setter
@Document(collection = "miembros")
public class Miembro {
    
    @Id
    private String id;

    @NotBlank(message = "El ci del ministerio no puede estar vacío")
    private String ci;

    @NotBlank(message = "El nombre del ministerio no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El apellido del ministerio no puede estar vacío")
    private String apellido;

    @NotBlank(message = "El foto del ministerio no puede estar vacío")
    private String foto;
    
    @NotBlank(message = "El fechaNacimiento del ministerio no puede estar vacío")
    private String fechaNacimiento;

     @NotBlank(message = "El celular del ministerio no puede estar vacío")
    private String celular;
     
    @NotBlank(message = "El genero del ministerio no puede estar vacío")
    private String genero;

    @Override
    public String toString() {
        return "Miembro [id=" + id + ", ci=" + ci + ", nombre=" + nombre + ", apellido=" + apellido +", foto=" + foto +", fechaNacimiento=" + fechaNacimiento +",celular="+celular+", genero=" + genero +"]";
    }
}
