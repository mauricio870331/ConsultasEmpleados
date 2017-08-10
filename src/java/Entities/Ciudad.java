/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;

/**
 *
 * @author admin
 */
public class Ciudad implements Serializable {

    private int cod_ciudad;
    private String ciudad;
    private String user_mod;
    private String fecha_mod;
    private String fecha_creacion;

    public Ciudad() {

    }

    public Ciudad(int cod_ciudad, String ciudad, String user_mod, String fecha_mod, String fecha_creacion) {
        this.cod_ciudad = cod_ciudad;
        this.ciudad = ciudad;
        this.user_mod = user_mod;
        this.fecha_mod = fecha_mod;
        this.fecha_creacion = fecha_creacion;
    }
    
    

    public Ciudad(int cod_ciudad, String ciudad) {
        this.cod_ciudad = cod_ciudad;
        this.ciudad = ciudad;
    }

    public int getCod_ciudad() {
        return cod_ciudad;
    }

    public void setCod_ciudad(int cod_ciudad) {
        this.cod_ciudad = cod_ciudad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Ciudad{" + "cod_ciudad=" + cod_ciudad + ", ciudad=" + ciudad + '}';
    }

    public String getUser_mod() {
        return user_mod;
    }

    public void setUser_mod(String user_mod) {
        this.user_mod = user_mod;
    }

    public String getFecha_mod() {
        return fecha_mod;
    }

    public void setFecha_mod(String fecha_mod) {
        this.fecha_mod = fecha_mod;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

}
