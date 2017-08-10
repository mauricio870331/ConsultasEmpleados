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
 * @author admin
 */
public class Alertas implements Serializable {

    private int id;
    private String titulo;
    private String descripcion;
    private Date fecha_creacion;
    private String relevancia;
    private Date fecha_desde;
    private Date fecha_hasta;
    private String estilo;

    public Alertas() {

    }

    public Alertas(int id, String titulo, String descripcion, Date fecha_creacion, String relevancia, Date fecha_desde, Date fecha_hasta, String estilo) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha_creacion = fecha_creacion;
        this.relevancia = relevancia;
        this.fecha_desde = fecha_desde;
        this.fecha_hasta = fecha_hasta;
        this.estilo = estilo;
    }

    public Alertas(int id, String descripcion, Date fecha_creacion, String relevancia, String titulo, Date fecha_desde, Date fecha_hasta) {
        this.id = id;
        this.descripcion = descripcion;
        this.fecha_creacion = fecha_creacion;
        this.relevancia = relevancia;
        this.titulo = titulo;
        this.fecha_desde = fecha_desde;
        this.fecha_hasta = fecha_hasta;
        switch (this.relevancia) {
            case "Muy Importante":
                this.estilo = "danger";
                break;
            case "Importante":
                this.estilo = "success";
                break;
            case "Información":
                this.estilo = "info";
                break;
        }
    }

    public String getRelevancia() {
        return relevancia;
    }

    public void setRelevancia(String relevancia) {
        this.relevancia = relevancia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_desde() {
        return fecha_desde;
    }

    public void setFecha_desde(Date fecha_desde) {
        this.fecha_desde = fecha_desde;
    }

    public Date getFecha_hasta() {
        return fecha_hasta;
    }

    public void setFecha_hasta(Date fecha_hasta) {
        this.fecha_hasta = fecha_hasta;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
