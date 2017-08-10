/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author admin
 */
public class CmGenerado implements Serializable {

    private String id_trans;
    private String agencia;
    private Date fecha_creacion;
    private boolean verificado;
    private String cm_asoc;
    private int total;
    private List<DetalleCm> listDetalleCm;
    private List<String> transUpdate;

    public CmGenerado() {
    }

    public CmGenerado(String id_trans, String agencia, String cm_asoc, Date fecha_creacion, boolean verificado, int total) {
        this.id_trans = id_trans;
        this.agencia = agencia;
        this.fecha_creacion = fecha_creacion;
        this.cm_asoc=cm_asoc;
        this.verificado = verificado;
        this.total = total;
    }

    public String getId_trans() {
        return id_trans;
    }

    public void setId_trans(String id_trans) {
        this.id_trans = id_trans;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public List<String> getTransUpdate() {
        return transUpdate;
    }

    public void setTransUpdate(List<String> transUpdate) {
        this.transUpdate = transUpdate;
    }

    public List<DetalleCm> getListDetalleCm() {
        return listDetalleCm;
    }

    public void setListDetalleCm(List<DetalleCm> listDetalleCm) {
        this.listDetalleCm = listDetalleCm;
    }

    @Override
    public String toString() {
        return "CmGenerado{" + "id_trans=" + id_trans + ", agencia=" + agencia + ", fecha_creacion=" + fecha_creacion + ", listDetalleCm=" + listDetalleCm + ", transUpdate=" + transUpdate + '}';
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public String getCm_asoc() {
        return cm_asoc;
    }

    public void setCm_asoc(String cm_asoc) {
        this.cm_asoc = cm_asoc;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    

}
