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

public class TblCiudades implements Serializable {

   
    private Integer codCiudad;
    
    private String ciudad;
    
    private String userMod;
    
    private byte[] fechaMod;
    
    private Date fechaCreacion;
   
    private List<DetalleConvenio> detalleConvenioList;
    
    private List<DetalleConvenio> detalleConvenioList1;
   


    public TblCiudades() {
    }

    public TblCiudades(Integer codCiudad) {
        this.codCiudad = codCiudad;
    }

    public TblCiudades(Integer codCiudad, byte[] fechaMod) {
        this.codCiudad = codCiudad;
        this.fechaMod = fechaMod;
    }

    public Integer getCodCiudad() {
        return codCiudad;
    }

    public void setCodCiudad(Integer codCiudad) {
        this.codCiudad = codCiudad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getUserMod() {
        return userMod;
    }

    public void setUserMod(String userMod) {
        this.userMod = userMod;
    }

    public byte[] getFechaMod() {
        return fechaMod;
    }

    public void setFechaMod(byte[] fechaMod) {
        this.fechaMod = fechaMod;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @XmlTransient
    public List<DetalleConvenio> getDetalleConvenioList() {
        return detalleConvenioList;
    }

    public void setDetalleConvenioList(List<DetalleConvenio> detalleConvenioList) {
        this.detalleConvenioList = detalleConvenioList;
    }

    @XmlTransient
    public List<DetalleConvenio> getDetalleConvenioList1() {
        return detalleConvenioList1;
    }

    public void setDetalleConvenioList1(List<DetalleConvenio> detalleConvenioList1) {
        this.detalleConvenioList1 = detalleConvenioList1;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCiudad != null ? codCiudad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCiudades)) {
            return false;
        }
        TblCiudades other = (TblCiudades) object;
        if ((this.codCiudad == null && other.codCiudad != null) || (this.codCiudad != null && !this.codCiudad.equals(other.codCiudad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TblCiudades[ codCiudad=" + codCiudad + " ]";
    }
    
}
