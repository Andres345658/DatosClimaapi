/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.datosclima.api.service;

import com.datosclima.api.entity.Auditoria;
import com.datosclima.api.repository.AuditoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaService {
    
    private final AuditoriaRepository auditoriarepository;
    
     public AuditoriaService(AuditoriaRepository auditoriarepository ) {
        this.auditoriarepository = auditoriarepository;
        
     }
    public void realizarAuditoria(Auditoria auditoria){
       
       this.auditoriarepository.save(auditoria);
       
       
       
       
       
   }
    
}
