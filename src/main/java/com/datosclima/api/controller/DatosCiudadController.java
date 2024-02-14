/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.datosclima.api.controller;

import com.datosclima.api.dto.DatosCiudad;
import com.datosclima.api.dto.Mensaje;
import com.datosclima.api.service.ApiService;
import io.github.bucket4j.Bucket;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Andres
 */
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/datos")
public class DatosCiudadController {

    private final ApiService apiService;
    private final Bucket bucket;

    public DatosCiudadController(ApiService apiService, Bucket bucket) {
        this.apiService = apiService;
        this.bucket = bucket;
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<?> buscarCiudaPorNombre(@PathVariable("ciudad") String ciudad) {
        if (this.bucket.tryConsume(1)) {

            DatosCiudad dc = this.apiService.buscarCiudad(ciudad);
            if (dc == null) {
                return new ResponseEntity<Mensaje>(new Mensaje("ciudad no encontrada"), HttpStatus.NOT_FOUND);

            }
            return new ResponseEntity<DatosCiudad>(dc, HttpStatus.OK);

        }
        return new ResponseEntity<Mensaje>(new Mensaje("limite de consultas superado"), HttpStatus.TOO_MANY_REQUESTS);

    }

    @GetMapping("/clima/{ciudad}")
    public ResponseEntity<?> buscarClimaActual(@PathVariable("ciudad") String ciudad) {
        if (this.bucket.tryConsume(1)) {
            DatosCiudad dc = this.apiService.buscarCiudad(ciudad);
            if (dc == null) {
                return new ResponseEntity<Mensaje>(new Mensaje("ciudad no encontrada"), HttpStatus.NOT_FOUND);
            }
            Object object = this.apiService.buscarClimaActual(dc, ciudad);
            return new ResponseEntity<Object>(object, HttpStatus.OK);

        }
        return new ResponseEntity<Mensaje>(new Mensaje("limite de consultas superado"), HttpStatus.TOO_MANY_REQUESTS);
    }

    @GetMapping("/pronostico/{ciudad}")
    public ResponseEntity<?> buscarPronosticoTiempo(@PathVariable("ciudad") String ciudad) {
        if (this.bucket.tryConsume(1)) {
            DatosCiudad dc = this.apiService.buscarCiudad(ciudad);
            if (dc == null) {
                return new ResponseEntity<Mensaje>(new Mensaje("ciudad no encontrada"), HttpStatus.NOT_FOUND);
            }
            Object pronostico = this.apiService.buscarPronosticoTiempo(dc, ciudad);
            return new ResponseEntity<Object>(pronostico, HttpStatus.OK);
        }
        return new ResponseEntity<Mensaje>(new Mensaje("limite de consultas superado"), HttpStatus.TOO_MANY_REQUESTS);
    }

    @GetMapping("/contaminacion/{ciudad}")
    public ResponseEntity<?> buscarContaminacionAire(@PathVariable("ciudad") String ciudad) {
        if (this.bucket.tryConsume(1)) {
            DatosCiudad dc = this.apiService.buscarCiudad(ciudad);
            if (dc == null) {
                return new ResponseEntity<Mensaje>(new Mensaje("ciudad no encontrada"), HttpStatus.NOT_FOUND);
            }
            Object contaminacion = this.apiService.buscarContaminacionAire(dc, ciudad);
            return new ResponseEntity<Object>(contaminacion, HttpStatus.OK);
        }
        return new ResponseEntity<Mensaje>(new Mensaje("limite de consultas superado"), HttpStatus.TOO_MANY_REQUESTS);
    }
}
