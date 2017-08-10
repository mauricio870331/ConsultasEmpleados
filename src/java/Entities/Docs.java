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
public class Docs implements Serializable {

    private int id;
    private String nombre;
    private String ruta;
    private String real_path;
    private Date fecha_mod;
    private int tipo;

    public Docs() {

    }

    public Docs(int id, String nombre, String ruta, String real_path, Date fecha_mod, int tipo) {
        this.id = id;
        this.nombre = nombre;
        this.ruta = ruta;
        this.real_path = real_path;
        this.fecha_mod = fecha_mod;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public String toString() {
        return ruta + nombre;
    }

    public String getReal_path() {
        return real_path;
    }

    public void setReal_path(String real_path) {
        this.real_path = real_path;
    }

    public Date getFecha_mod() {
        return fecha_mod;
    }

    public void setFecha_mod(Date fecha_mod) {
        this.fecha_mod = fecha_mod;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String Icono(String nomArchivo) {
        String icono = "menu-icon fa fa-book bg-green";
        if (nomArchivo.toLowerCase().contains("tarifas")) {
            icono = "menu-icon fa fa-money bg-green";
        }
        return icono;
    }

}
