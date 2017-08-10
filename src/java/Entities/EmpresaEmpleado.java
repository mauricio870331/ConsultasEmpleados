/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin
 */

public class EmpresaEmpleado implements Serializable {


    private Date fechaCreacion;
    private String documento;
    private int idEmpresa;
    private int codEstado;
   
    
    
   
    private List<TblViajesTiquetes> tblViajesTiquetesList;

    public EmpresaEmpleado() {
    }

    

   
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(int codEstado) {
        this.codEstado = codEstado;
    }

    

    public void setTblViajesTiquetesList(List<TblViajesTiquetes> tblViajesTiquetesList) {
        this.tblViajesTiquetesList = tblViajesTiquetesList;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }


}
