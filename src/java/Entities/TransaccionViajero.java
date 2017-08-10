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

public class TransaccionViajero implements Serializable {

   
   private int id_trans_viaje;
   private int id_viaje_tiquete;
   private String documento;     
   private Empresas empresas;
    
    private int codCiudadDestino;
    
    private int codCiudadOrigen;
   
    private Integer valorCliente;
   
    
    private int valorExpal;
   
    private TblViajesTiquetes tblViajesTiquetes;
    
    private Transaccion idTransaccion;

    public TransaccionViajero() {
    }

    public TransaccionViajero( int codCiudadDestino, int codCiudadOrigen, int valorExpal) {

        this.codCiudadDestino = codCiudadDestino;
        this.codCiudadOrigen = codCiudadOrigen;
        this.valorExpal = valorExpal;
    }

  
    public int getCodCiudadDestino() {
        return codCiudadDestino;
    }

    public void setCodCiudadDestino(int codCiudadDestino) {
        this.codCiudadDestino = codCiudadDestino;
    }

    public int getCodCiudadOrigen() {
        return codCiudadOrigen;
    }

    public void setCodCiudadOrigen(int codCiudadOrigen) {
        this.codCiudadOrigen = codCiudadOrigen;
    }

    public Integer getValorCliente() {
        return valorCliente;
    }

    public void setValorCliente(Integer valorCliente) {
        this.valorCliente = valorCliente;
    }

    public int getValorExpal() {
        return valorExpal;
    }

    public void setValorExpal(int valorExpal) {
        this.valorExpal = valorExpal;
    }

    public TblViajesTiquetes getTblViajesTiquetes() {
        if (tblViajesTiquetes == null) {
            tblViajesTiquetes = new TblViajesTiquetes();
        }
        return tblViajesTiquetes;
    }

    public void setTblViajesTiquetes(TblViajesTiquetes tblViajesTiquetes) {
        this.tblViajesTiquetes = tblViajesTiquetes;
    }

    public Transaccion getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Transaccion idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public int getId_trans_viaje() {
        return id_trans_viaje;
    }

    public void setId_trans_viaje(int id_trans_viaje) {
        this.id_trans_viaje = id_trans_viaje;
    }

    public int getId_viaje_tiquete() {
        return id_viaje_tiquete;
    }

    public void setId_viaje_tiquete(int id_viaje_tiquete) {
        this.id_viaje_tiquete = id_viaje_tiquete;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Empresas getEmpresas() {
        if (empresas == null) {
            empresas = new Empresas();
        }
        return empresas;
    }

    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
    }

   
    
}
