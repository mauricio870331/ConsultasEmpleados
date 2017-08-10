/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin
 */

public class Estados implements Serializable {

    
    private Integer codEstado;
    
    private String descripcion;
    
    private List<EmpresaEmpleado> empresaEmpleadoList;
    
    private List<DetalleConvenio> detalleConvenioList;
    
    

    public Estados() {
    }

    public Estados(Integer codEstado) {
        this.codEstado = codEstado;
    }

    public Estados(String descripcion) {
        this.descripcion = descripcion;
    }
        

    public Estados(Integer codEstado, String descripcion) {
        this.codEstado = codEstado;
        this.descripcion = descripcion;
    }

    public Integer getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(Integer codEstado) {
        this.codEstado = codEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<EmpresaEmpleado> getEmpresaEmpleadoList() {
        return empresaEmpleadoList;
    }

    public void setEmpresaEmpleadoList(List<EmpresaEmpleado> empresaEmpleadoList) {
        this.empresaEmpleadoList = empresaEmpleadoList;
    }

    @XmlTransient
    public List<DetalleConvenio> getDetalleConvenioList() {
        return detalleConvenioList;
    }

    public void setDetalleConvenioList(List<DetalleConvenio> detalleConvenioList) {
        this.detalleConvenioList = detalleConvenioList;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEstado != null ? codEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estados)) {
            return false;
        }
        Estados other = (Estados) object;
        if ((this.codEstado == null && other.codEstado != null) || (this.codEstado != null && !this.codEstado.equals(other.codEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Estados[ codEstado=" + codEstado + " ]";
    }
    
}
