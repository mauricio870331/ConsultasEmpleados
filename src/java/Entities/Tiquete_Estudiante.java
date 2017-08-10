/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;



/**
 *
 * @author cnoguera
 */
public class Tiquete_Estudiante implements Serializable {
    
    private int id_entrega;
    private String doc_estudiante;
    private String numero_tiquete;
    private Date fecha_entrega;
    private String fechaentregaStr;
    private String taquilla_entrega;
    private String usuario_taquilla;

    public Tiquete_Estudiante() {
    }

    public Tiquete_Estudiante(int id_entrega, String doc_estudiante, String numero_tiquete, Date fecha_entrega, String taquilla_entrega, String usuario_taquilla) {
        this.id_entrega = id_entrega;
        this.doc_estudiante = doc_estudiante;
        this.numero_tiquete = numero_tiquete;
        this.fecha_entrega = fecha_entrega;
        this.taquilla_entrega = taquilla_entrega;
        this.usuario_taquilla = usuario_taquilla;
    }

    public Tiquete_Estudiante(String doc_estudiante, String numero_tiquete, Date fecha_entrega, String taquilla_entrega, String usuario_taquilla) {
        this.doc_estudiante = doc_estudiante;
        this.numero_tiquete = numero_tiquete;
        this.fecha_entrega = fecha_entrega;
        this.taquilla_entrega = taquilla_entrega;
        this.usuario_taquilla = usuario_taquilla;
    }
    
    

   
    

     
    public int getId_entrega() {
        return id_entrega;
    }
       

    public void setId_entrega(int id_entrega) {
        this.id_entrega = id_entrega;
    }
  
    public String getDoc_estudiante() {
        return doc_estudiante;
    }

    public void setDoc_estudiante(String doc_estudiante) {
        this.doc_estudiante = doc_estudiante;
    }

    public String getNumero_tiquete() {
        return numero_tiquete;
    }

    public void setNumero_tiquete(String numero_tiquete) {
        this.numero_tiquete = numero_tiquete;
    }

    public Date getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(Date fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public String getTaquilla_entrega() {
        return taquilla_entrega;
    }

    public void setTaquilla_entrega(String taquilla_entrega) {
        this.taquilla_entrega = taquilla_entrega;
    }

    public String getUsuario_taquilla() {
        return usuario_taquilla;
    }

    public void setUsuario_taquilla(String usuario_taquilla) {
        this.usuario_taquilla = usuario_taquilla;
    }

    public String getFechaentregaStr() {
        return fechaentregaStr;
    }

    public void setFechaentregaStr(String fechaentregaStr) {
        this.fechaentregaStr = fechaentregaStr;
    }

  
    
    
    
}
