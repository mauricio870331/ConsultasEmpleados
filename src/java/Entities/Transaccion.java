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
public class Transaccion implements Serializable {

    private Integer idTransaccion;
    
    private String strIdetrans;
    
    private int opc;

    private int cantTiquetes;

    private String descripcionTiquetes;

    private int total;

    private Date fechaMod;

    private List<TransaccionViajero> transaccionViajeroList;
    
    private TransaccionViajero trnasaccionViaje;

    private Usuarios idUsuario;
    
    private TblEmpleados empleados;

    private String usuarioNodum;
    private String claveNodum;
    
    private String usuarioTaquilla;
    
    private String taquilla;

    private int tempValorGlobal;
    
    private String tempTipoContrato;
    
    private String tiqtes_entregados;
    
    public Transaccion() {
    }

    public Transaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }
    
    
    //transacciones realizadas
    public Transaccion(String documento, int id_empresa, String strIdetrans,  String empresa, String empleado, Date fecha_entrega, String usuarioTaquilla, int opc) {
        this.strIdetrans = strIdetrans;
        this.trnasaccionViaje = getTrnasaccionViaje();        
        this.trnasaccionViaje.getEmpresas().setId_empresa(id_empresa);
        this.trnasaccionViaje.getEmpresas().setNombre(empresa);
        this.empleados = getEmpleados();
        this.empleados.setNombre(empleado);
        this.empleados.setDocumento(documento);
        this.fechaMod = fecha_entrega;
        this.usuarioTaquilla = usuarioTaquilla;
        this.opc = opc;
    }

    public Transaccion(Integer idTransaccion, int cantTiquetes, String descripcionTiquetes, short total, Date fechaMod) {
        this.idTransaccion = idTransaccion;
        this.cantTiquetes = cantTiquetes;
        this.descripcionTiquetes = descripcionTiquetes;
        this.total = total;
        this.fechaMod = fechaMod;
    }

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public int getCantTiquetes() {
        return cantTiquetes;
    }

    public void setCantTiquetes(int cantTiquetes) {
        this.cantTiquetes = cantTiquetes;
    }

    public String getDescripcionTiquetes() {
        return descripcionTiquetes;
    }

    public void setDescripcionTiquetes(String descripcionTiquetes) {
        this.descripcionTiquetes = descripcionTiquetes;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getFechaMod() {
        return fechaMod;
    }

    public void setFechaMod(Date fechaMod) {
        this.fechaMod = fechaMod;
    }

    @XmlTransient
    public List<TransaccionViajero> getTransaccionViajeroList() {
        return transaccionViajeroList;
    }

    public void setTransaccionViajeroList(List<TransaccionViajero> transaccionViajeroList) {
        this.transaccionViajeroList = transaccionViajeroList;
    }

    public Usuarios getIdUsuario() {
        if (idUsuario == null) {
            idUsuario = new Usuarios();
        }
        return idUsuario;
    }

    public void setIdUsuario(Usuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransaccion != null ? idTransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaccion)) {
            return false;
        }
        Transaccion other = (Transaccion) object;
        if ((this.idTransaccion == null && other.idTransaccion != null) || (this.idTransaccion != null && !this.idTransaccion.equals(other.idTransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Transaccion[ idTransaccion=" + idTransaccion + " ]";
    }

    public String getUsuarioNodum() {
        return usuarioNodum;
    }

    public void setUsuarioNodum(String usuarioNodum) {
        this.usuarioNodum = usuarioNodum;
    }

    public String getClaveNodum() {
        return claveNodum;
    }

    public void setClaveNodum(String claveNodum) {
        this.claveNodum = claveNodum;
    }

    public String getUsuarioTaquilla() {
        return usuarioTaquilla;
    }

    public void setUsuarioTaquilla(String usuarioTaquilla) {
        this.usuarioTaquilla = usuarioTaquilla;
    }

    public String getTaquilla() {
        return taquilla;
    }

    public void setTaquilla(String taquilla) {
        this.taquilla = taquilla;
    }

    public int getTempValorGlobal() {
        return tempValorGlobal;
    }

    public void setTempValorGlobal(int tempValorGlobal) {
        this.tempValorGlobal = tempValorGlobal;
    }

    public String getTempTipoContrato() {
        return tempTipoContrato;
    }

    public void setTempTipoContrato(String tempTipoContrato) {
        this.tempTipoContrato = tempTipoContrato;
    }

    public TransaccionViajero getTrnasaccionViaje() {
        if (trnasaccionViaje == null) {
            trnasaccionViaje = new TransaccionViajero();
        }
        return trnasaccionViaje;
    }

    public void setTrnasaccionViaje(TransaccionViajero trnasaccionViaje) {
        this.trnasaccionViaje = trnasaccionViaje;
    }

    public TblEmpleados getEmpleados() {
        if (empleados == null) {
            empleados = new TblEmpleados();
        }
        return empleados;
    }

    public void setEmpleados(TblEmpleados empleados) {
        this.empleados = empleados;
    }

    public int getOpc() {
        return opc;
    }

    public void setOpc(int opc) {
        this.opc = opc;
    }

    public String getStrIdetrans() {
        return strIdetrans;
    }

    public void setStrIdetrans(String strIdetrans) {
        this.strIdetrans = strIdetrans;
    }

    public String getTiqtes_entregados() {
        return tiqtes_entregados;
    }

    public void setTiqtes_entregados(String tiqtes_entregados) {
        this.tiqtes_entregados = tiqtes_entregados;
    }

}
