/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author admin
 */
public class DetalleConvenio implements Serializable {

    private int id_detalle_fk;
    private TblConvenio tblConvenio;
    private int id_convenio;
    private int id_empresa;
    private Empresas empresa;
    private int valorCliente;
    private int valorExpal;
    private int servicio;
    private TblServicios tblservicio;
    private TblCiudades codCiudadOrigen;
    private TblCiudades codCiudadDestino;
    private Date fechaInicial;
    private Date fechaFinal;
    private String sfechaInicial;
    private String sfechaFinal;
    private int cod_estado;
    private int idUsuario;
    private Usuarios Usuario;
    private String fechaCreacion;
    private String empalme;
    private List<TblViajesTiquetes> tblViajesTiquetesList;

    public DetalleConvenio() {
    }

    //origen y destinos
    public DetalleConvenio(int id_detalle_fk, String origen, String destino, String servicio) {
        setId_detalle_fk(id_detalle_fk);
        this.codCiudadOrigen = getCodCiudadOrigen();
        this.codCiudadOrigen.setCiudad(origen);
        this.codCiudadDestino = getCodCiudadDestino();    
        this.codCiudadDestino.setCiudad(destino);
        this.tblservicio = getTblservicio();
        this.tblservicio.setServicio(servicio);
    }
    
    
    
    

    public DetalleConvenio(int valorExpal, Date fechaInicial, Date fechaFinal, String fechaCreacion) {
        this.valorExpal = valorExpal;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.fechaCreacion = fechaCreacion;        
    }

    //constructor detalle
    public DetalleConvenio(int valorCliente, int valorExpal, int idServicio, int codEstado, int idConvenio,
            int cod_ciud_origen, int cod_ciud_destino, int idUsuario, int id_detalle_fk, Date fecha_inicial, Date fecha_final,
            String empalme, String origen, String destino, String documento, String servicio) {
        this.valorCliente = valorCliente;
        this.valorExpal = valorExpal;
        this.tblservicio = getTblservicio();
        this.tblservicio.setIdServicio(idServicio);
        this.tblservicio.setServicio(servicio);
        this.cod_estado = codEstado;
        this.id_convenio = idConvenio;
        this.codCiudadOrigen = getCodCiudadOrigen();
        this.codCiudadOrigen.setCodCiudad(cod_ciud_origen);
        this.codCiudadOrigen.setCiudad(origen);
        this.codCiudadDestino = getCodCiudadDestino();
        this.codCiudadDestino.setCodCiudad(cod_ciud_destino);
        this.codCiudadDestino.setCiudad(destino);
        this.Usuario = getUsuario();
        this.Usuario.setIdUsuario(idUsuario);
        this.Usuario.setDocumento(documento);
        this.fechaInicial = fecha_inicial;
        this.fechaFinal = fecha_final;
        this.empalme = empalme;
        this.id_detalle_fk = id_detalle_fk;    

    }

    public int getCod_estado() {
        return cod_estado;
    }

    public void setCod_estado(int cod_estado) {
        this.cod_estado = cod_estado;
    }

    public String getEmpalme() {
        return empalme;
    }

    public void setEmpalme(String empalme) {
        this.empalme = empalme;
    }

    public TblConvenio getTblConvenio() {
        if (tblConvenio == null) {
            tblConvenio = new TblConvenio();
        }
        return tblConvenio;
    }

    public void setTblConvenio(TblConvenio tblConvenio) {
        this.tblConvenio = tblConvenio;
    }

    public Empresas getEmpresa() {
        if (empresa == null) {
            empresa = new Empresas();
        }
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }

    public int getValorCliente() {
        return valorCliente;
    }

    public void setValorCliente(int valorCliente) {
        this.valorCliente = valorCliente;
    }

    public int getValorExpal() {
        return valorExpal;
    }

    public void setValorExpal(int valorExpal) {
        this.valorExpal = valorExpal;
    }

    public TblServicios getTblservicio() {
        if (tblservicio == null) {
            tblservicio = new TblServicios();
        }
        return tblservicio;
    }

    public void setTblservicio(TblServicios tblservicio) {
        this.tblservicio = tblservicio;
    }

    public TblCiudades getCodCiudadOrigen() {
        if (codCiudadOrigen == null) {
            codCiudadOrigen = new TblCiudades();
        }
        return codCiudadOrigen;
    }

    public void setCodCiudadOrigen(TblCiudades codCiudadOrigen) {
        this.codCiudadOrigen = codCiudadOrigen;
    }

    public TblCiudades getCodCiudadDestino() {
        if (codCiudadDestino == null) {
            codCiudadDestino = new TblCiudades();
        }
        return codCiudadDestino;
    }

    public void setCodCiudadDestino(TblCiudades codCiudadDestino) {
        this.codCiudadDestino = codCiudadDestino;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getSfechaInicial() {
        return sfechaInicial;
    }

    public void setSfechaInicial(String sfechaInicial) {
        this.sfechaInicial = sfechaInicial;
    }

    public String getSfechaFinal() {
        return sfechaFinal;
    }

    public void setSfechaFinal(String sfechaFinal) {
        this.sfechaFinal = sfechaFinal;
    }

    public Usuarios getUsuario() {
        if (Usuario == null) {
            Usuario = new Usuarios();
        }
        return Usuario;
    }

    public void Usuario(Usuarios idUsuario) {
        this.Usuario = idUsuario;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<TblViajesTiquetes> getTblViajesTiquetesList() {
        return tblViajesTiquetesList;
    }

    public void setTblViajesTiquetesList(List<TblViajesTiquetes> tblViajesTiquetesList) {
        this.tblViajesTiquetesList = tblViajesTiquetesList;
    }

    public int getId_convenio() {
        return id_convenio;
    }

    public void setId_convenio(int id_convenio) {
        this.id_convenio = id_convenio;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getServicio() {
        return servicio;
    }

    public void setServicio(int servicio) {
        this.servicio = servicio;
    }

    public int getId_detalle_fk() {
        return id_detalle_fk;
    }

    public void setId_detalle_fk(int id_detalle_fk) {
        this.id_detalle_fk = id_detalle_fk;
    }

    @Override
    public String toString() {
        return codCiudadOrigen.getCiudad()+" - "+codCiudadDestino.getCiudad()+" - (" + tblservicio.getServicio() + ")";
    }

}
