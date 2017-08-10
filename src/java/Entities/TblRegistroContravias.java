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
 * @author clopez
 */
public class TblRegistroContravias implements Serializable {

    private String transaccion;
    private int id_empresa;
    private String nombre_comprador;
    private String cc_comprador;
    private String nombre_viajero;
    private String cc_viajero;
    private String origen;
    private String destino;
    private int valor;
    private int cant_tiquetes = 1;
    private int total;
    private String ida_regreso;
    private String cod_bus;
    private String observacion;
    private Date fecha_creacion;
    private String no_tiquete = "";
    private String taquilla_vende;
    private String userNodumVende;
    private String usuarioTaquilla_vende;
    private String servicio;
    private int piso;
    private String strPiso;
    private String estado;
    private String taquilla_entrega;
    private String userNodumEntrega;
    private String usuarioTaquillaEntrega;
    private String telefonoComprador;
    private String no_reserva;
    private int reimprimir;
    private int dineroEnCasa;
    private boolean dineroSiNo;
    private Date fecha_entrega;
    private String claveNodum;
    private boolean confirmRegistro;
    private String userMod;
    private boolean selected;
    private String fecha_viajeString;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public TblRegistroContravias() {
    }

    public TblRegistroContravias(String cc_comprador, String nombre_comprador,
            String cc_viajero, String nombre_viajero,
            String origen, String destino,
            String servicio, String strPiso,
            int cant_tiquetes, int total,
            String no_tiquete, String usuarioTaquilla_vende,
            String usuarioTaquillaEntrega, String taquilla_vende,
            String taquilla_entrega) {
        this.cc_comprador = cc_comprador;
        this.nombre_comprador = nombre_comprador;
        this.cc_viajero = cc_viajero;
        this.nombre_viajero = nombre_viajero;
        this.origen = origen;
        this.destino = destino;
        this.servicio = servicio;
        this.strPiso = strPiso;
        this.cant_tiquetes = cant_tiquetes;
        this.total = total;
        this.no_tiquete = no_tiquete;
        this.usuarioTaquilla_vende = usuarioTaquilla_vende;
        this.usuarioTaquillaEntrega = usuarioTaquillaEntrega;
        this.taquilla_vende = taquilla_vende;
        this.taquilla_entrega = taquilla_entrega;

    }

    //old contravias
    public TblRegistroContravias(String transaccion, int id_empresa, String nombre_comprador,
            String cc_comprador, String nombre_viajero, String cc_viajero,
            String origen, String destino, int valor,
            int total, String ida_regreso, String cod_bus,
            String observacion, Date fecha_creacion, String taquilla_vende,
            String userNodumVende, String usuarioTaquilla_vende, String servicio,
            String strPiso, String estado, String taquilla_entrega,
            String userNodumEntrega, String usuarioTaquillaEntrega, String telefonoComprador,
            String no_reserva, int reimprimir, int dineroEnCasa,
            boolean dineroSiNo, Date fecha_entrega, String claveNodum, String no_tiquete, int cant_tiquetes) {
        this.transaccion = transaccion;
        this.id_empresa = id_empresa;
        this.nombre_comprador = nombre_comprador;
        this.cc_comprador = cc_comprador;
        this.nombre_viajero = nombre_viajero;
        this.cc_viajero = cc_viajero;
        this.origen = origen;
        this.destino = destino;
        this.valor = valor;
        this.total = total;
        this.ida_regreso = ida_regreso;
        this.cod_bus = cod_bus;
        this.observacion = observacion;
        this.fecha_creacion = fecha_creacion;
        this.taquilla_vende = taquilla_vende;
        this.userNodumVende = userNodumVende;
        this.usuarioTaquilla_vende = usuarioTaquilla_vende;
        this.servicio = servicio;
        this.strPiso = strPiso;
        this.estado = estado;
        this.taquilla_entrega = taquilla_entrega;
        this.userNodumEntrega = userNodumEntrega;
        this.usuarioTaquillaEntrega = usuarioTaquillaEntrega;
        this.telefonoComprador = telefonoComprador;
        this.no_reserva = no_reserva;
        this.reimprimir = reimprimir;
        this.dineroEnCasa = dineroEnCasa;
        this.dineroSiNo = dineroSiNo;
        this.fecha_entrega = fecha_entrega;
        this.claveNodum = claveNodum;
        this.no_tiquete = no_tiquete;
        this.cant_tiquetes = cant_tiquetes;
    }

    //contravias pendientes
    public TblRegistroContravias(String transaccion,
            String nombre_comprador,
            String cc_comprador,
            String nombre_viajero,
            String cc_viajero,
            String servicio,
            String cod_bus,
            String origen,
            String destino,
            int cant_tiquetes,
            int valor,
            int total,
            String observacion,
            String taquilla_vende,
            String usuarioTaquillaVende,
            String taquilla_entrega,
            String usuarioTaquillaEntrega,
            Date fecha_creacion,
            Date fecha_entrega, String estado, int dineroEnCasa) {
        this.transaccion = transaccion;
        this.nombre_comprador = nombre_comprador;
        this.cc_comprador = cc_comprador;
        this.nombre_viajero = nombre_viajero;
        this.cc_viajero = cc_viajero;
        this.servicio = servicio;
        this.cod_bus = cod_bus;
        this.origen = origen;
        this.destino = destino;
        this.cant_tiquetes = cant_tiquetes;
        this.valor = valor;
        this.total = total;
        this.observacion = observacion;
        this.taquilla_vende = taquilla_vende;
        this.taquilla_entrega = taquilla_entrega;
        this.usuarioTaquilla_vende = usuarioTaquillaVende;
        this.usuarioTaquillaEntrega = usuarioTaquillaEntrega;
        this.fecha_creacion = fecha_creacion;
        this.fecha_entrega = fecha_entrega;
        this.estado = estado;
        this.dineroEnCasa = dineroEnCasa;
    }

    public TblRegistroContravias(String transaccion,
            String nombre_comprador,
            String cc_comprador,
            String nombre_viajero,
            String cc_viajero,
            String servicio,
            String cod_bus,
            String origen,
            String destino,
            int cant_tiquetes,
            int valor,
            int total,
            String observacion,
            String taquilla_vende,
            String usuarioTaquillaVende,
            String taquilla_entrega,
            String usuarioTaquillaEntrega,
            Date fecha_creacion,
            Date fecha_entrega, String estado, int dineroEnCasa, boolean dineroSiNo) {
        this.transaccion = transaccion;
        this.nombre_comprador = nombre_comprador;
        this.cc_comprador = cc_comprador;
        this.nombre_viajero = nombre_viajero;
        this.cc_viajero = cc_viajero;
        this.servicio = servicio;
        this.cod_bus = cod_bus;
        this.origen = origen;
        this.destino = destino;
        this.cant_tiquetes = cant_tiquetes;
        this.valor = valor;
        this.total = total;
        this.observacion = observacion;
        this.taquilla_vende = taquilla_vende;
        this.taquilla_entrega = taquilla_entrega;
        this.usuarioTaquilla_vende = usuarioTaquillaVende;
        this.usuarioTaquillaEntrega = usuarioTaquillaEntrega;
        this.fecha_creacion = fecha_creacion;
        this.fecha_entrega = fecha_entrega;
        this.estado = estado;
        this.dineroEnCasa = dineroEnCasa;
        this.dineroSiNo = dineroSiNo;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getNombre_comprador() {
        return nombre_comprador;
    }

    public void setNombre_comprador(String nombre_comprador) {
        this.nombre_comprador = nombre_comprador;
    }

    public String getCc_comprador() {
        return cc_comprador;
    }

    public void setCc_comprador(String cc_comprador) {
        this.cc_comprador = cc_comprador;
    }

    public String getNombre_viajero() {
        return nombre_viajero;
    }

    public void setNombre_viajero(String nombre_viajero) {
        this.nombre_viajero = nombre_viajero;
    }

    public String getCc_viajero() {
        return cc_viajero;
    }

    public void setCc_viajero(String cc_viajero) {
        this.cc_viajero = cc_viajero;
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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getCant_tiquetes() {
        return cant_tiquetes;
    }

    public void setCant_tiquetes(int cant_tiquetes) {
        this.cant_tiquetes = cant_tiquetes;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getIda_regreso() {
        return ida_regreso;
    }

    public void setIda_regreso(String ida_regreso) {
        this.ida_regreso = ida_regreso;
    }

    public String getCod_bus() {
        return cod_bus;
    }

    public void setCod_bus(String cod_bus) {
        this.cod_bus = cod_bus;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getNo_tiquete() {
        return no_tiquete;
    }

    public void setNo_tiquete(String no_tiquete) {
        this.no_tiquete = no_tiquete;
    }

    public String getTaquilla_vende() {
        return taquilla_vende;
    }

    public void setTaquilla_vende(String taquilla_vende) {
        this.taquilla_vende = taquilla_vende;
    }

    public String getUserNodumVende() {
        return userNodumVende;
    }

    public void setUserNodumVende(String userNodumVende) {
        this.userNodumVende = userNodumVende;
    }

    public String getUsuarioTaquilla_vende() {
        return usuarioTaquilla_vende;
    }

    public void setUsuarioTaquilla_vende(String usuarioTaquilla_vende) {
        this.usuarioTaquilla_vende = usuarioTaquilla_vende;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTaquilla_entrega() {
        return taquilla_entrega;
    }

    public void setTaquilla_entrega(String taquilla_entrega) {
        this.taquilla_entrega = taquilla_entrega;
    }

    public String getUserNodumEntrega() {
        return userNodumEntrega;
    }

    public void setUserNodumEntrega(String userNodumEntrega) {
        this.userNodumEntrega = userNodumEntrega;
    }

    public String getUsuarioTaquillaEntrega() {
        return usuarioTaquillaEntrega;
    }

    public void setUsuarioTaquillaEntrega(String usuarioTaquillaEntrega) {
        this.usuarioTaquillaEntrega = usuarioTaquillaEntrega;
    }

    public String getTelefonoComprador() {
        return telefonoComprador;
    }

    public void setTelefonoComprador(String telefonoComprador) {
        this.telefonoComprador = telefonoComprador;
    }

    public String getNo_reserva() {
        return no_reserva;
    }

    public void setNo_reserva(String no_reserva) {
        this.no_reserva = no_reserva;
    }

    public int getReimprimir() {
        return reimprimir;
    }

    public void setReimprimir(int reimprimir) {
        this.reimprimir = reimprimir;
    }

    public int getDineroEnCasa() {
        return dineroEnCasa;
    }

    public void setDineroEnCasa(int dineroEnCasa) {
        this.dineroEnCasa = dineroEnCasa;
    }

    public Date getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(Date fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    @Override
    public String toString() {
        return "TblRegistroContravias{" + "transaccion="
                + transaccion
                + ", id_empresa="
                + id_empresa
                + ", nombre_comprador=" + nombre_comprador
                + ", cc_comprador=" + cc_comprador + ", nombre_viajero="
                + nombre_viajero + ", cc_viajero=" + cc_viajero
                + ", origen=" + origen + ", destino="
                + destino + ", valor=" + valor + ", cant_tiquetes="
                + cant_tiquetes + ", total=" + total + ", ida_regreso="
                + ida_regreso + ", cod_bus=" + cod_bus + ", observacion="
                + observacion + ", fecha_creacion=" + fecha_creacion + ", no_tiquete="
                + no_tiquete + ", taquilla_vende=" + taquilla_vende + ", userNodumVende="
                + userNodumVende + ", usuarioTaquilla_vende=" + usuarioTaquilla_vende
                + ", servicio=" + servicio + ", piso=" + piso + ", estado="
                + estado + ", taquilla_entrega=" + taquilla_entrega + ", userNodumEntrega="
                + userNodumEntrega + ", usuarioTaquillaEntrega=" + usuarioTaquillaEntrega
                + ", telefonoComprador=" + telefonoComprador + ", no_reserva="
                + no_reserva + ", reimprimir=" + reimprimir + ", dineroEnCasa="
                + dineroEnCasa + ", fecha_entrega=" + fecha_entrega + '}';
    }

    public String getClaveNodum() {
        return claveNodum;
    }

    public void setClaveNodum(String claveNodum) {
        this.claveNodum = claveNodum;
    }

    public boolean isConfirmRegistro() {
        return confirmRegistro;
    }

    public void setConfirmRegistro(boolean confirmRegistro) {
        this.confirmRegistro = confirmRegistro;
    }

    public String getUserMod() {
        return userMod;
    }

    public void setUserMod(String userMod) {
        this.userMod = userMod;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isDineroSiNo() {
        return dineroSiNo;
    }

    public void setDineroSiNo(boolean dineroSiNo) {
        this.dineroSiNo = dineroSiNo;
    }

    public String getStrPiso() {
        return strPiso;
    }

    public void setStrPiso(String strPiso) {
        this.strPiso = strPiso;
    }

    public String getFecha_viajeString() {
        return fecha_viajeString = format.format(fecha_creacion);
    }

}
