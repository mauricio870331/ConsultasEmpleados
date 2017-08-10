/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author admin
 */
public class TblusuarioRegistro implements Serializable {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private String cliente;

    private String tipocontratotemp;

    private String nombre;

    private String documento;

    private String ot;

    private int codCiudadOrigen;

    private int codCiudadDestino;

    private String NomCiudadOrigen;

    private String NomCiudadDestino;

    private int valor;

    private int cantidad = 1;

    private int total;

    private String idaRegreso;

    private String codBus;

    private String observacion;

    private Date fecha;

    private Date fechaviaje;

    private String tiquete;

    private Integer idEmpresa;

    private String userMod;

    private String taquilla;

    private int valorSin;

    private Date fechaCreacion;

    private int totalsin;

    private String usuarioNodum;

    private String claveNodum;

    private boolean confirmRegistro;

    private String strConfirmRegistro;

    private String usuarioTaquilla;

    private String transaccion;
    
    
    private String fecha_viajeString;

    public TblusuarioRegistro() {
    }

    //impresionesRealizadas
    public TblusuarioRegistro(String documento, int id_empresa, String transaccion, String cliente, String nombre, Date fecha_creacion) {
        this.documento = documento;
        this.idEmpresa = id_empresa;
        this.transaccion = transaccion;
        this.cliente = cliente;
        this.nombre = nombre;
        this.fecha = fecha_creacion;
    }

    public TblusuarioRegistro(String idRegistro) {
        this.idaRegreso = idRegistro;
    }

    public String getIdRegistro() {
        return idaRegreso;
    }

    public void setIdRegistro(String idRegistro) {
        this.idaRegreso = idRegistro;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getOt() {
        return ot;
    }

    public void setOt(String ot) {
        this.ot = ot;
    }

    public int getCodCiudadOrigen() {
        return codCiudadOrigen;
    }

    public void setCodCiudadOrigen(int codCiudadOrigen) {
        this.codCiudadOrigen = codCiudadOrigen;
    }

    public int getCodCiudadDestino() {
        return codCiudadDestino;
    }

    public void setCodCiudadDestino(int codCiudadDestino) {
        this.codCiudadDestino = codCiudadDestino;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getIdaRegreso() {
        return idaRegreso;
    }

    public void setIdaRegreso(String idaRegreso) {
        this.idaRegreso = idaRegreso;
    }

    public String getCodBus() {
        return codBus;
    }

    public void setCodBus(String codBus) {
        this.codBus = codBus;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTiquete() {
        return tiquete;
    }

    public void setTiquete(String tiquete) {
        this.tiquete = tiquete;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getUserMod() {
        return userMod;
    }

    public void setUserMod(String userMod) {
        this.userMod = userMod;
    }

    public String getTaquilla() {
        return taquilla;
    }

    public void setTaquilla(String taquilla) {
        this.taquilla = taquilla;
    }

    public int getValorSin() {
        return valorSin;
    }

    public void setValorSin(int valorSin) {
        this.valorSin = valorSin;
    }

    public int getTotalsin() {
        return totalsin;
    }

    public void setTotalsin(int totalsin) {
        this.totalsin = totalsin;
    }

    public String getNomCiudadOrigen() {
        return NomCiudadOrigen;
    }

    public void setNomCiudadOrigen(String NomCiudadOrigen) {
        this.NomCiudadOrigen = NomCiudadOrigen;
    }

    public String getNomCiudadDestino() {
        return NomCiudadDestino;
    }

    public void setNomCiudadDestino(String NomCiudadDestino) {
        this.NomCiudadDestino = NomCiudadDestino;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaviaje() {
        return fechaviaje;
    }

    public void setFechaviaje(Date fechaviaje) {
        this.fechaviaje = fechaviaje;
    }

    @Override
    public String toString() {
        return "TblusuarioRegistro{" + "cliente=" + cliente + ", nombre=" + nombre + ", documento=" + documento + ", ot=" + ot + ", codCiudadOrigen=" + codCiudadOrigen + ", codCiudadDestino=" + codCiudadDestino + ", NomCiudadOrigen=" + NomCiudadOrigen + ", NomCiudadDestino=" + NomCiudadDestino + ", valor=" + valor + ", cantidad=" + cantidad + ", total=" + total + ", idaRegreso=" + idaRegreso + ", codBus=" + codBus + ", observacion=" + observacion + ", fecha=" + fecha + ", fechaviaje=" + fechaviaje + ", tiquete=" + tiquete + ", idEmpresa=" + idEmpresa + ", userMod=" + userMod + ", taquilla=" + taquilla + ", valorSin=" + valorSin + ", fechaCreacion=" + fechaCreacion + ", totalsin=" + totalsin + '}';
    }

    public String getClaveNodum() {
        return claveNodum;
    }

    public void setClaveNodum(String claveNodum) {
        this.claveNodum = claveNodum;
    }

    public String getUsuarioNodum() {
        return usuarioNodum;
    }

    public void setUsuarioNodum(String usuarioNodum) {
        this.usuarioNodum = usuarioNodum;
    }

    public boolean isConfirmRegistro() {
        return confirmRegistro;
    }

    public void setConfirmRegistro(boolean confirmRegistro) {
        this.confirmRegistro = confirmRegistro;
    }

    public String getStrConfirmRegistro() {
        return strConfirmRegistro;
    }

    public void setStrConfirmRegistro(String strConfirmRegistro) {
        this.strConfirmRegistro = strConfirmRegistro;
    }

    public String getUsuarioTaquilla() {
        return usuarioTaquilla;
    }

    public void setUsuarioTaquilla(String usuarioTaquilla) {
        this.usuarioTaquilla = usuarioTaquilla;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public String getTipocontratotemp() {
        return tipocontratotemp;
    }

    public void setTipocontratotemp(String tipocontratotemp) {
        this.tipocontratotemp = tipocontratotemp;
    }

    public String getFecha_viajeString() {
        return fecha_viajeString = format.format(fechaviaje);
    }

}
