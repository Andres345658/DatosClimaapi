/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.datosclima.api.service;

import com.datosclima.api.dto.DatosCiudad;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Andres
 */
@Service
public class ApiService {

    @Value("${openweathermap.api.url}")
    private String URL_BASE;
    @Value("${openweathermap.api.url.geo}")
    private String URL_GEO;
    @Value("${openweathermap.api.id}")
    private String API_ID;
    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Cacheable(value="buscarCiudad",key="#ciudad")
    public DatosCiudad buscarCiudad(String ciudad) {
        // Construir la URL utilizando las propiedades del archivo application.properties
        String urlString = this.URL_GEO + "direct?q=" + ciudad + "&appid=" + this.API_ID + "&limit=1";
        DatosCiudad[] city = restTemplate.getForObject(urlString, DatosCiudad[].class);
        if (city == null || city.length == 0) {
            return null;
        }
        return city[0];
    }
     @Cacheable(value="buscarClimaActual",key="#ciudad")
    public Object buscarClimaActual(DatosCiudad dc,String ciudad) {

        String urlString = this.URL_BASE + "weather?lat=" + dc.getLat() + "&lon=" + dc.getLon() + "&appid=" + this.API_ID;
        return restTemplate.getForObject(urlString, Object.class);
    }
     @Cacheable(value="buscarPronosticoTiempo",key="#ciudad")
    public Object buscarPronosticoTiempo(DatosCiudad dc,String ciudad) {

        String urlString = this.URL_BASE + "forecast?lat=" + dc.getLat() + "&lon=" + dc.getLon() + "&appid=" + this.API_ID;
        return restTemplate.getForObject(urlString, Object.class);

    }
     @Cacheable(value="buscarContaminacionAire",key="#ciudad")
    public Object buscarContaminacionAire(DatosCiudad dc,String ciudad) {

        String urlString = this.URL_BASE + "air_pollution?lat=" + dc.getLat() + "&lon=" + dc.getLon() + "&appid=" + this.API_ID;
        return restTemplate.getForObject(urlString, Object.class);

    }

   
//- https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid=7fa1077c9476a59bbdda850ef4137d31
//- https://api.openweathermap.org/data/2.5/forecast?id=524901&appid=7fa1077c9476a59bbdda850ef4137d31
//- http://api.openweathermap.org/data/2.5/air_pollution?lat=50&lon=50&appid=7fa1077c9476a59bbdda850ef4137d31
//- http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid=7fa1077c9476a59bbdda850ef4137d31
}
