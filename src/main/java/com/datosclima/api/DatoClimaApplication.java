package com.datosclima.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@EnableCaching
@SpringBootApplication
public class DatoClimaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatoClimaApplication.class, args);
	}

}
