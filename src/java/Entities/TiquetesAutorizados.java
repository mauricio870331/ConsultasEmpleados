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
public class TiquetesAutorizados implements Serializable {

    private int id_carga;
    private String documento;
    private String nombre_completo;
    private String telefono;
    private String origen;
    private String destino;
    private String motivo_solicitud;
    private Date fecha_solicitud;
    private String estado;
    private Date fecha_entrega;
    private String tipo_servicio;
    private String taquilla_entrega;
    private String usuario_taquilla;
    private String usaurio_solicita;
    private String usuarioSolicitaString;
    private String tiquete = "";
    private String idaRegreso;   
    private boolean selected = false;

    public TiquetesAutorizados() {

    }

    public TiquetesAutorizados(String documento, String nombre_completo, String telefono) {
        this.documento = documento;
        this.nombre_completo = nombre_completo;
        this.telefono = telefono;
    }

    
    public TiquetesAutorizados(int id_carga, String documento, String nombre_completo, String telefono, String origen, String destino, String motivo_solicitud, Date fecha_solicitud, String estado, Date fecha_entrega, String tipo_servicio, String taquilla_entrega, String usuario_taquilla, String usaurio_solicita, String tiquete, String idaRegreso, String usuarioSolicitaString) {
        this.id_carga = id_carga;
        this.documento = documento;
        this.nombre_completo = nombre_completo;
        this.telefono = telefono;
        this.origen = origen;
        this.destino = destino;
        this.motivo_solicitud = motivo_solicitud;
        this.fecha_solicitud = fecha_solicitud;
        this.estado = estado;
        this.fecha_entrega = fecha_entrega;
        this.tipo_servicio = tipo_servicio;
        this.taquilla_entrega = taquilla_entrega;
        this.usuario_taquilla = usuario_taquilla;
        this.usaurio_solicita = usaurio_solicita;
        this.tiquete = tiquete;
        this.idaRegreso = idaRegreso;
        this.usuarioSolicitaString = usuarioSolicitaString;
    }

    @Override
    public String toString() {
        return "TiquetesAutorizados{" + "id_carga=" + id_carga + ", documento=" + documento + ", nombre_completo=" + nombre_completo + ", telefono=" + telefono + ", origen=" + origen + ", destino=" + destino + ", motivo_solicitud=" + motivo_solicitud + ", fecha_solicitud=" + fecha_solicitud + ", estado=" + estado + ", fecha_entrega=" + fecha_entrega + ", tipo_servicio=" + tipo_servicio + ", taquilla_entrega=" + taquilla_entrega + ", usuario_taquilla=" + usuario_taquilla + ", usaurio_solicita=" + usaurio_solicita + ", tiquete=" + tiquete + ", idaRegreso=" + idaRegreso + '}';
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getMotivo_solicitud() {
        return motivo_solicitud;
    }

    public void setMotivo_solicitud(String motivo_solicitud) {
        this.motivo_solicitud = motivo_solicitud;
    }

    public Date getFecha_solicitud() {
        return fecha_solicitud;
    }

    public void setFecha_solicitud(Date fecha_solicitud) {
        this.fecha_solicitud = fecha_solicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(Date fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public String getTipo_servicio() {
        return tipo_servicio;
    }

    public void setTipo_servicio(String tipo_servicio) {
        this.tipo_servicio = tipo_servicio;
    }

    public String getTaquilla_entrega() {
        return taquilla_entrega;
    }

    public void setTaquilla_entrega(String taquilla_entrega) {
        this.taquilla_entrega = taquilla_entrega;
    }

    public String getUsuario_taquilla() {
        return usuario_taquilla;
    }

    public void setUsuario_taquilla(String usuario_taquilla) {
        this.usuario_taquilla = usuario_taquilla;
    }

    public String getUsaurio_solicita() {
        return usaurio_solicita;
    }

    public void setUsaurio_solicita(String usaurio_solicita) {
        this.usaurio_solicita = usaurio_solicita;
    }

    public String getTiquete() {
        return tiquete;
    }

    public void setTiquete(String tiquete) {
        this.tiquete = tiquete;
    }

    public int getId_carga() {
        return id_carga;
    }

    public void setId_carga(int id_carga) {
        this.id_carga = id_carga;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getIdaRegreso() {
        return idaRegreso;
    }

    public void setIdaRegreso(String idaRegreso) {
        this.idaRegreso = idaRegreso;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

  
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getUsuarioSolicitaString() {
        return usuarioSolicitaString;
    }

    public void setUsuarioSolicitaString(String usuarioSolicitaString) {
        this.usuarioSolicitaString = usuarioSolicitaString;
    }

}
