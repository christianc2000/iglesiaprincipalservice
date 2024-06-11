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
@Document(collection = "donacions")
public class Donacion {

    @Id
    private String id;

    @NotBlank(message = "El monto de la donación no puede estar vacío")
    private String monto;

    @NotBlank(message = "El monto de la donación no puede estar vacío")
    private String fecha;

    @NotBlank(message = "El miembro de la donación no puede estar vacío")
    private String miembroId;

    @NotBlank(message = "El evento de la donación no puede estar vacío")
    private String eventoId;

    @NotBlank(message = "El tipo donacion de la donación no puede estar vacío")
    private String tipoDonacionId;

    public Donacion() {
    }

    @Override
    public String toString() {
        return "Donacion [id=" + id + ", monto=" + monto + ", fecha=" + fecha + ", miembro=" + miembroId + ", evento=" + eventoId + ", tipoDonacion" + tipoDonacionId + "]";
    }
}
