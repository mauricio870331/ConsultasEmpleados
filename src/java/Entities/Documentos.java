/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;

/**
 *
 * @author clopez
 */
public class Documentos implements Serializable{
     private String empresa;
     private String ruta;

    public Documentos(String empresa, String ruta) {
        this.empresa = empresa;
        this.ruta = ruta;
    }     
     
    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
     
     
    
}
