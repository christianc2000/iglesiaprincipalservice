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
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;

    @NotBlank(message = "El rol del ministerio no puede estar vacío")
    private String rol;

    @NotBlank(message = "El estado del ministerio no puede estar vacío")
    private String estado;

    @NotBlank(message = "El correo del ministerio no puede estar vacío")
    private String correo;

    @NotBlank(message = "El password del ministerio no puede estar vacío")
    private String password;

    @NotBlank(message = "El miembroId del ministerio no puede estar vacío")
    private String miembroId;

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", rol=" + rol + ", estado=" + estado + ", correo=" + correo + ", password=" + password + "]";
    }
}
