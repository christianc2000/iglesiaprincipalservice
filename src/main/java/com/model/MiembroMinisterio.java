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
@Document(collection = "miembro_ministerios")
public class MiembroMinisterio {

    @Id
    private String id;
    private String fechaInicio;
    private String fechaFin;
    private String cargoId;
    private String ministerioId;
    private String miembroId;

}
