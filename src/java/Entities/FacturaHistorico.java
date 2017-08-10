/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author clopez
 */
public class FacturaHistorico implements Serializable{
    private String cliente;
    private String empleado;
    private String documento;
    private String origen;
    private String destino;
    private String valor_unitario;
    private String cantidad;
    private Date fecha_venta;
    private String ot;
    private String ida_regreso;
    private String no_orden;
    private String total;

    public FacturaHistorico(String cliente, String empleado, String documento, String origen, String destino, String valor_unitario, String cantidad, String total, Date feha_venta, String ot, String ida_regreso, String no_orden) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.documento = documento;
        this.origen = origen;
        this.destino = destino;
        this.valor_unitario = valor_unitario;
        this.cantidad = cantidad;
        this.total = total;
        this.fecha_venta = feha_venta;
        this.ot = ot;
        this.ida_regreso = ida_regreso;
        this.no_orden = no_orden;
    }

       
    
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getValor_unitario() {
        return valor_unitario;
    }

    public void setValor_unitario(String valor_unitario) {
        this.valor_unitario = valor_unitario;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(Date feha_venta) {
        this.fecha_venta = feha_venta;
    }

    public String getOt() {
        return ot;
    }

    public void setOt(String ot) {
        this.ot = ot;
    }

    public String getIda_regreso() {
        return ida_regreso;
    }

    public void setIda_regreso(String ida_regreso) {
        this.ida_regreso = ida_regreso;
    }

    public String getNo_orden() {
        return no_orden;
    }

    public void setNo_orden(String no_orden) {
        this.no_orden = no_orden;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    
    
    
}
