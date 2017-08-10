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
public class ReciboDeCaja implements Serializable{
    private int id_transaccion;
    private String fechaEntrega;
    private int cant_tiquetes;
    private String desc_tiquetes;
    private int total;
    private String empresa;
    private String userNodum;
    private String userTaquilla;
    private String taquilla;
    private String tiquetes_entregados;
    private String documento;
    private String nombre;
    private String origen;
    private String destino;
    private int valor_expal;
    private int unidad_tiquete;
    private String ida_regreso;
    private String servicio;
    private int total_unidad;

    public ReciboDeCaja() {
    }

    public ReciboDeCaja(int id_transaccion, String fechaEntrega, int cant_tiquetes, String desc_tiquetes, int total, String empresa, String userNodum, String userTaquilla, String taquilla, String tiquetes_entregados, String documento, String nombre, String origen, String destino, int valor_expal, int unidad_tiquete, String ida_regreso, String servicio, int total_unidad) {
        this.id_transaccion = id_transaccion;
        this.fechaEntrega = fechaEntrega;
        this.cant_tiquetes = cant_tiquetes;
        this.desc_tiquetes = desc_tiquetes;
        this.total = total;
        this.empresa = empresa;
        this.userNodum = userNodum;
        this.userTaquilla = userTaquilla;
        this.taquilla = taquilla;
        this.tiquetes_entregados = tiquetes_entregados;
        this.documento = documento;
        this.nombre = nombre;
        this.origen = origen;
        this.destino = destino;
        this.valor_expal = valor_expal;
        this.unidad_tiquete = unidad_tiquete;
        this.ida_regreso = ida_regreso;
        this.servicio = servicio;
        this.total_unidad = total_unidad;
    }

    
    public int getId_transaccion() {
        return id_transaccion;
    }

    public void setId_transaccion(int id_transaccion) {
        this.id_transaccion = id_transaccion;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public int getCant_tiquetes() {
        return cant_tiquetes;
    }

    public void setCant_tiquetes(int cant_tiquetes) {
        this.cant_tiquetes = cant_tiquetes;
    }

    public String getDesc_tiquetes() {
        return desc_tiquetes;
    }

    public void setDesc_tiquetes(String desc_tiquetes) {
        this.desc_tiquetes = desc_tiquetes;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getUserNodum() {
        return userNodum;
    }

    public void setUserNodum(String userNodum) {
        this.userNodum = userNodum;
    }

    public String getUserTaquilla() {
        return userTaquilla;
    }

    public void setUserTaquilla(String userTaquilla) {
        this.userTaquilla = userTaquilla;
    }

    public String getTaquilla() {
        return taquilla;
    }

    public void setTaquilla(String taquilla) {
        this.taquilla = taquilla;
    }

    public String getTiquetes_entregados() {
        return tiquetes_entregados;
    }

    public void setTiquetes_entregados(String tiquetes_entregados) {
        this.tiquetes_entregados = tiquetes_entregados;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public int getValor_expal() {
        return valor_expal;
    }

    public void setValor_expal(int valor_expal) {
        this.valor_expal = valor_expal;
    }

    public int getUnidad_tiquete() {
        return unidad_tiquete;
    }

    public void setUnidad_tiquete(int unidad_tiquete) {
        this.unidad_tiquete = unidad_tiquete;
    }

    public String getIda_regreso() {
        return ida_regreso;
    }

    public void setIda_regreso(String ida_regreso) {
        this.ida_regreso = ida_regreso;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public int getTotal_unidad() {
        return total_unidad;
    }

    public void setTotal_unidad(int total_unidad) {
        this.total_unidad = total_unidad;
    }
    
    
    
    
}
