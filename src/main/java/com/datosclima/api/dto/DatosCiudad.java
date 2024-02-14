/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.datosclima.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Andres
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DatosCiudad {
    private String country;
    private double lat;
    @JsonProperty("local_names")
    @JsonIgnore
    private Map<String, String> localNames;
    private double lon;
    private String name;
    private String state;
}
