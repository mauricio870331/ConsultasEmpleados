/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author clopez
 */
public class Empresas {
    private int id_empresa;
    private String nit;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;
    private String user_mod;
    private String fecha_mod;
    private Date fechaMod;
    private int cod_ciudad;
    private String fecha_creacion;
    private int cod_estado;
    private TblConvenio convenio;
    private Date fecha_inicial;
    private Date fecha_final;
    private int valor_global;
    private int saldoValorGlobal;
    private String tipo_contrato;
    private String estado;
    

    public Empresas() {
    }

    public Empresas(int id_empresa, String nombre) {
        this.id_empresa = id_empresa;
        this.nombre = nombre;
    }
    
    
    public Empresas(int id_empresa, String nombre, int idconvenio, String descConvenio,
                    Date fecha_inicial, Date fecha_final, int valor_global, String tipo_contrato) {
        this.id_empresa = id_empresa;
        this.nombre = nombre;
        this.convenio = getConvenio();
        this.convenio.setIdconvenio(idconvenio);
        this.convenio.setDescripcion(descConvenio);
        this.fecha_inicial = fecha_inicial;
        this.fecha_final = fecha_final;
        this.valor_global = valor_global;
        this.tipo_contrato = tipo_contrato;
    }
    
    

    public Empresas(int id_empresa, String nit, String nombre, String direccion, String telefono, String correo, String user_mod, String fecha_mod, int cod_ciudad, String fecha_creacion, int cod_estado) {
        this.id_empresa = id_empresa;
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.user_mod = user_mod;
        this.fecha_mod = fecha_mod;
        this.cod_ciudad = cod_ciudad;
        this.fecha_creacion = fecha_creacion;
        this.cod_estado = cod_estado;
    }
    
    


    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUser_mod() {
        return user_mod;
    }

    public void setUser_mod(String user_mod) {
        this.user_mod = user_mod;
    }

    public String getFecha_mod() {
        return fecha_mod;
    }

    public void setFecha_mod(String fecha_mod) {
        this.fecha_mod = fecha_mod;
    }

    public int getCod_ciudad() {
        return cod_ciudad;
    }

    public void setCod_ciudad(int cod_ciudad) {
        this.cod_ciudad = cod_ciudad;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public int getCod_estado() {
        return cod_estado;
    }

    public void setCod_estado(int cod_estado) {
        this.cod_estado = cod_estado;
    }

    @Override
    public String toString() {
        return "Empresas{" + "id_empresa=" + id_empresa + ", nit=" + nit + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono + ", correo=" + correo + ", user_mod=" + user_mod + ", fecha_mod=" + fecha_mod + ", cod_ciudad=" + cod_ciudad + ", fecha_creacion=" + fecha_creacion + ", cod_estado=" + cod_estado + '}';
    }

    
   
    public TblConvenio getConvenio() {
        if (convenio==null) {
            convenio = new TblConvenio();
        }
        return convenio;
    }

    public void setConvenio(TblConvenio convenio) {
        this.convenio = convenio;
    }

    public Date getFecha_inicial() {
        return fecha_inicial;
    }

    public void setFecha_inicial(Date fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
    }

    public Date getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(Date fecha_final) {
        this.fecha_final = fecha_final;
    }

    public int getValor_global() {
        return valor_global;
    }

    public void setValor_global(int valor_global) {
        this.valor_global = valor_global;
    }

    public String getTipo_contrato() {
        return tipo_contrato;
    }

    public void setTipo_contrato(String tipo_contrato) {
        this.tipo_contrato = tipo_contrato;
    }

    public Date getFechaMod() {
        return fechaMod;
    }

    public void setFechaMod(Date fechaMod) {
        this.fechaMod = fechaMod;
    }

    public int getSaldoValorGlobal() {
        return saldoValorGlobal;
    }

    public void setSaldoValorGlobal(int saldoValorGlobal) {
        this.saldoValorGlobal = saldoValorGlobal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
     
    
}
