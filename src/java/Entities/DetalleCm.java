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
public class DetalleCm implements Serializable {

    private int id;
    private String pagado_a;
    private String cc_nit;
    private String no_bus = "";
    private String concepto;
    private int valor;
    private String consecotivo;
    private int idtrans;
    private String tabla;
    private String cm_asoc;
    private String descripcion = "";
    private boolean selected;

    public DetalleCm() {

    }

    public DetalleCm(int id,
                    String pagado_a, String cc_nit, String no_bus, String concepto, int valor, int id_trans, String tabla) {
        this.id = id;
        this.pagado_a = pagado_a;
        this.cc_nit = cc_nit;
        this.no_bus = no_bus;
        this.concepto = concepto;
        this.valor = valor;
        this.idtrans = id_trans;
        this.tabla = tabla;
    }

    public String getPagado_a() {
        return pagado_a;
    }

    public void setPagado_a(String pagado_a) {
        this.pagado_a = pagado_a;
    }

    public String getCc_nit() {
        return cc_nit;
    }

    public void setCc_nit(String cc_nit) {
        this.cc_nit = cc_nit;
    }

    public String getNo_bus() {
        return no_bus;
    }

    public void setNo_bus(String no_bus) {
        this.no_bus = no_bus;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DetalleCm{" + "id=" + id + ", pagado_a=" + pagado_a + ", cc_nit=" + cc_nit + ", no_bus=" + no_bus + ", concepto=" + concepto + ", valor=" + valor + ", consecotivo=" + consecotivo + ", idtrans=" + idtrans + ", tabla=" + tabla + ", cm_asoc=" + cm_asoc + '}';
    }

    

    public String getConsecotivo() {
        return consecotivo;
    }

    public void setConsecotivo(String consecotivo) {
        this.consecotivo = consecotivo;
    }

    public int getIdtrans() {
        return idtrans;
    }

    public void setIdtrans(int idtrans) {
        this.idtrans = idtrans;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getCm_asoc() {
        return cm_asoc;
    }

    public void setCm_asoc(String cm_asoc) {
        this.cm_asoc = cm_asoc;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
