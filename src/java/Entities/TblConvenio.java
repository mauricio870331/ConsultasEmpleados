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
 * @author admin
 */
public class TblConvenio implements Serializable {

    private int idconvenio;
    private int idempresa;
    private String user_mod;
    private String fechaMod;
    private String fechaInicial;
    private String fechaFinal;
    private String descripcion;
    private int valorGlobal;
    private String estado;
    private Date dfechaInicial;
    private Date dfechaFinal;
    private String empresa;
    private String tipoContrato;
    private int saldoValorGlobal;

    public TblConvenio() {
    }

    public TblConvenio(int idconvenio, String descripcion) {
        this.idconvenio = idconvenio;
        this.descripcion = descripcion;
    }

    public TblConvenio(int idconvenio, String user_mod, Date dfechaInicial, Date dfechaFinal, String descripcion, int valorGlobal, String estado, String empresa, int id_empresa) {
       this.idconvenio = idconvenio;
       this.dfechaInicial = dfechaInicial;
       this.dfechaFinal = dfechaFinal;
       this.descripcion = descripcion;
       this.valorGlobal = valorGlobal;
       this.estado = estado;
       this.user_mod = user_mod;
       this.empresa = empresa;
       this.idempresa = id_empresa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdconvenio() {
        return idconvenio;
    }

    public void setIdconvenio(int idconvenio) {
        this.idconvenio = idconvenio;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public String getUser_mod() {
        return user_mod;
    }

    public void setUser_mod(String user_mod) {
        this.user_mod = user_mod;
    }

    public String getFechaMod() {
        return fechaMod;
    }

    public void setFechaMod(String fechaMod) {
        this.fechaMod = fechaMod;
    }

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public int getValorGlobal() {
        return valorGlobal;
    }

    public void setValorGlobal(int valorGlobal) {
        this.valorGlobal = valorGlobal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getDfechaInicial() {
        return dfechaInicial;
    }

    public void setDfechaInicial(Date dfechaInicial) {
        this.dfechaInicial = dfechaInicial;
    }

    public Date getDfechaFinal() {
        return dfechaFinal;
    }

    public void setDfechaFinal(Date dfechaFinal) {
        this.dfechaFinal = dfechaFinal;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public int getSaldoValorGlobal() {
        return saldoValorGlobal;
    }

    public void setSaldoValorGlobal(int saldoValorGlobal) {
        this.saldoValorGlobal = saldoValorGlobal;
    }

}
