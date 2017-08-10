/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin
 */
public class Usuarios implements Serializable {

    private int idUsuario;
    private String documento;
    private String nombres;
    private String apellidos;
    private String clave;
    private String cambioClave;
    private String estado;
    private String cambiaClaveAuto;
    private int codCiudad;
    private int idEmpresa;
    private String userMod;
    private String fechaMod;
    private String fechaCreacion;
    private String ciudad;
    private String empresa;
    private int id_rol;
    private UsuariosRoles usuRol;
    private String taquilla;
    

    public Usuarios() {
    }

    public Usuarios(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuarios(String documento, String nombres, String apellidos, String clave) {
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.clave = clave;
    }

    public Usuarios(int idUsuario, String documento, String nombres, String apellidos) {
        this.idUsuario = idUsuario;
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.taquilla = nombres+" "+apellidos;
    }
    
    // list construct
    public Usuarios(int idUsuario,
            String documento,
            String nombres,
            String apellidos,
            String clave,
            String cambioClave,
            String estado,
            String cambiaClaveAuto,
            String ciudad,
            String empresa,
            String userMod,
            String fechaMod,
            String fechaCreacion,
            int codCiudad,
            int idEmpresa,
            int id_rol) {
        this.idUsuario = idUsuario;
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.clave = clave;
        this.cambioClave = cambioClave;
        this.estado = estado;
        this.cambiaClaveAuto = cambiaClaveAuto;
        this.ciudad = ciudad;
        this.empresa = empresa;
        this.userMod = userMod;
        this.fechaMod = fechaMod;
        this.fechaCreacion = fechaCreacion;
        this.codCiudad = codCiudad;
        this.idEmpresa = idEmpresa;
        this.id_rol = id_rol;
        
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCambioClave() {
        return cambioClave;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public void setCambioClave(String cambioClave) {
        this.cambioClave = cambioClave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCambiaClaveAuto() {
        return cambiaClaveAuto;
    }

    public void setCambiaClaveAuto(String cambiaClaveAuto) {
        this.cambiaClaveAuto = cambiaClaveAuto;
    }

    public int getCodCiudad() {
        return codCiudad;
    }

    public void setCodCiudad(int codCiudad) {
        this.codCiudad = codCiudad;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getUserMod() {
        return userMod;
    }

    public void setUserMod(String userMod) {
        this.userMod = userMod;
    }

    public String getFechaMod() {
        return fechaMod;
    }

    public void setFechaMod(String fechaMod) {
        this.fechaMod = fechaMod;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
     public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public UsuariosRoles getUsuRol() {
        return usuRol;
    }

    public void setUsuRol(UsuariosRoles usuRol) {
        this.usuRol = usuRol;
    }

    public String getTaquilla() {
        return taquilla;
    }

    public void setTaquilla(String taquilla) {
        this.taquilla = taquilla;
    }
    
    
}