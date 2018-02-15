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
    private String no_factura;
    private boolean recibido;
    private boolean en_contabilidad;
    private String descripcionDetalle;

    public CmGenerado() {
    }

    public CmGenerado(String id_trans, String agencia, String cm_asoc, Date fecha_creacion, boolean verificado, int total, String no_factura, int en_contabilidad, int recibido) {
        this.id_trans = id_trans;
        this.agencia = agencia;
        this.fecha_creacion = fecha_creacion;
        this.cm_asoc = cm_asoc;
        this.verificado = verificado;
        this.total = total;
        this.no_factura = no_factura;
        this.recibido = recibido == 1;
        this.en_contabilidad = en_contabilidad == 1;
    }

    public CmGenerado(String id_trans, String agencia, String cm_asoc, Date fecha_creacion, boolean verificado, int total, String no_factura, int en_contabilidad, int recibido, String descripcionDetalle) {
        this.id_trans = id_trans;
        this.agencia = agencia;
        this.fecha_creacion = fecha_creacion;
        this.cm_asoc = cm_asoc;
        this.verificado = verificado;
        this.total = total;
        this.no_factura = no_factura;
        this.recibido = recibido == 1;
        this.en_contabilidad = en_contabilidad == 1;
        this.descripcionDetalle = descripcionDetalle;
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
        return "CmGenerado{" + "id_trans=" + id_trans + ", agencia=" + agencia + ", fecha_creacion=" + fecha_creacion + ", verificado=" + verificado + ", cm_asoc=" + cm_asoc + ", total=" + total + ", listDetalleCm=" + listDetalleCm + ", transUpdate=" + transUpdate + ", no_factura=" + no_factura + ", recibido=" + recibido + ", en_contabilidad=" + en_contabilidad + ", descripcionDetalle=" + descripcionDetalle + '}';
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

    public String getNo_factura() {
        return no_factura;
    }

    public void setNo_factura(String no_factura) {
        this.no_factura = no_factura;
    }

    public boolean isRecibido() {
        return recibido;
    }

    public void setRecibido(boolean recibido) {
        this.recibido = recibido;
    }

    public boolean isEn_contabilidad() {
        return en_contabilidad;
    }

    public void setEn_contabilidad(boolean en_contabilidad) {
        this.en_contabilidad = en_contabilidad;
    }

    public String getDescripcionDetalle() {
        return descripcionDetalle;
    }

    public void setDescripcionDetalle(String descripcionDetalle) {
        this.descripcionDetalle = descripcionDetalle;
    }

}
