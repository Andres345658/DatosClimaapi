/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.datosclima.api.controller;

import com.datosclima.api.dto.DatosCiudad;
import com.datosclima.api.dto.Mensaje;
import com.datosclima.api.entity.Auditoria;
import com.datosclima.api.service.ApiService;
import com.datosclima.api.service.AuditoriaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.Bucket;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuditoriaService auditoriaService;
    private Authentication authentication;

    public DatosCiudadController(ApiService apiService, Bucket bucket, AuditoriaService auditoriaService) {
        this.apiService = apiService;
        this.bucket = bucket;
        this.auditoriaService = auditoriaService;

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
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Auditoria audi = new Auditoria();
        audi.setNombreUsuario(username);
        audi.setTipoConsulta("clima actual");
        audi.setFechaConsulta(new Date());
        audi.setRespuestaConsulta(ciudad);
        Mensaje mensaje = new Mensaje("");
        String respuesta = "";
        if (this.bucket.tryConsume(1)) {
            DatosCiudad dc = this.apiService.buscarCiudad(ciudad);
            if (dc == null) {
                mensaje.setMensaje("ciudad no encontrada");
                respuesta=objetoJson(mensaje);
                audi.setRespuestaConsulta(respuesta);
                auditoriaService.realizarAuditoria(audi);
                return new ResponseEntity<Mensaje>(mensaje, HttpStatus.NOT_FOUND);

            }

            Object object = this.apiService.buscarClimaActual(dc, ciudad);
            respuesta=objetoJson(object);
            System.out.println(respuesta);
            audi.setRespuestaConsulta(respuesta);
            this.auditoriaService.realizarAuditoria(audi);
            return new ResponseEntity<Object>(object, HttpStatus.OK);

        }
          mensaje.setMensaje("limite de consultas superado");
                respuesta=objetoJson(mensaje);
                audi.setRespuestaConsulta(respuesta);
                auditoriaService.realizarAuditoria(audi);
                return new ResponseEntity<Mensaje>(mensaje, HttpStatus.TOO_MANY_REQUESTS);

    }

    @GetMapping("/pronostico/{ciudad}")
    public ResponseEntity<?> buscarPronosticoTiempo(@PathVariable("ciudad") String ciudad) {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Auditoria audi = new Auditoria();
        audi.setNombreUsuario(username);
        audi.setTipoConsulta("pronostico tiempo");
        audi.setFechaConsulta(new Date());
        audi.setRespuestaConsulta(ciudad);
        Mensaje mensaje = new Mensaje("");
        String respuesta = "";
        
        if (this.bucket.tryConsume(1)) {
            DatosCiudad dc = this.apiService.buscarCiudad(ciudad);
            if (dc == null) {
                mensaje.setMensaje("ciudad no encontrada");
                respuesta=objetoJson(mensaje);
                audi.setRespuestaConsulta(respuesta);
                auditoriaService.realizarAuditoria(audi);
                return new ResponseEntity<Mensaje>(mensaje, HttpStatus.NOT_FOUND);
            }
            
            
            Object object = this.apiService.buscarPronosticoTiempo(dc, ciudad);
            respuesta=objetoJson(object);
            System.out.println(respuesta);
            audi.setRespuestaConsulta(respuesta);
            this.auditoriaService.realizarAuditoria(audi);
            return new ResponseEntity<Object>(object, HttpStatus.OK);
          
        }
         mensaje.setMensaje("limite de consultas superado");
                respuesta=objetoJson(mensaje);
                audi.setRespuestaConsulta(respuesta);
                auditoriaService.realizarAuditoria(audi);
                return new ResponseEntity<Mensaje>(mensaje, HttpStatus.TOO_MANY_REQUESTS);
    }

    @GetMapping("/contaminacion/{ciudad}")
    public ResponseEntity<?> buscarContaminacionAire(@PathVariable("ciudad") String ciudad) {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Auditoria audi = new Auditoria();
        audi.setNombreUsuario(username);
        audi.setTipoConsulta("contaminacion aire");
        audi.setFechaConsulta(new Date());
        audi.setRespuestaConsulta(ciudad);
        Mensaje mensaje = new Mensaje("");
        String respuesta = "";
        if (this.bucket.tryConsume(1)) {
            DatosCiudad dc = this.apiService.buscarCiudad(ciudad);
            if (dc == null) {
                mensaje.setMensaje("ciudad no encontrada");
                respuesta=objetoJson(mensaje);
                audi.setRespuestaConsulta(respuesta);
                auditoriaService.realizarAuditoria(audi);
                return new ResponseEntity<Mensaje>(mensaje, HttpStatus.NOT_FOUND);
            }
             Object object = this.apiService.buscarContaminacionAire(dc, ciudad);
            respuesta=objetoJson(object);
            System.out.println(respuesta);
            audi.setRespuestaConsulta(respuesta);
            this.auditoriaService.realizarAuditoria(audi);
            return new ResponseEntity<Object>(object, HttpStatus.OK);
        }
        mensaje.setMensaje("limite de consultas superado");
                respuesta=objetoJson(mensaje);
                audi.setRespuestaConsulta(respuesta);
                auditoriaService.realizarAuditoria(audi);
                return new ResponseEntity<Mensaje>(mensaje, HttpStatus.TOO_MANY_REQUESTS);
    }

    private String objetoJson(Object objson) {

        ObjectMapper obj = new ObjectMapper();
        String respuesta = "";
        try {
            respuesta = obj.writeValueAsString(objson);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(DatosCiudadController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuesta;

    }
}
