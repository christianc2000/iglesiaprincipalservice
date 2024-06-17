/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model.Dto;


/**
 *
 * @author USER
 */

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AuthResponseDTO {
    private String id;
    private String token;
    private String username;
    private String email;
    private List<String> roles;
}
