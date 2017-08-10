


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin
 */

public class TblServicios implements Serializable {

  
     private int idServicio;
   
    private String servicio;
    
    private String userMod;
    
    private String fechaMod;
    
    private String fechaCreacion;
    
   

    public TblServicios() {
    }

    public TblServicios(int idServicio, String servicio, String userMod, String fechaMod, String fechaCreacion) {
        this.idServicio = idServicio;
        this.servicio = servicio;
        this.userMod = userMod;
        this.fechaMod = fechaMod;
        this.fechaCreacion = fechaCreacion;
    }
    
    

    public TblServicios(int idServicio) {
        this.idServicio = idServicio;
    }

    public TblServicios(int idServicio, String servicio) {
        this.idServicio = idServicio;
        this.servicio = servicio;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getUserMod() {
        return userMod;
    }

    public void setUserMod(String userMod) {
        this.userMod = userMod;
    }

    public String getFechaMod() {
        return fechaMod;
    }

    public void setFechaMod(String fechaMod) {
        this.fechaMod = fechaMod;
    }

    

    @Override
    public String toString() {
        return "entity.Roles[ idServicio=" + idServicio + " ]";
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
}