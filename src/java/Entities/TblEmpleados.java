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
public class TblEmpleados implements Serializable {
    
    private String oldoc;
    private String documento;
    private String nomDoc;
    
    private String nombre;
    
    private String telefono;
    
    private String correo;
    
    private String ot;
    
    private String cm_asoc;
    

    
    private int tiquetesPendientes;
    
    
    
    private String userMod;
    
    private Date fechaMod;
    
    private Date fechaCreacion;
    
    private boolean estado;
    
    private TblCiudades ciudadesOrigen;
    
    private TblCiudades ciudadesDestino;
    
    private DetalleConvenio detalleConvenio;
    
    private EmpresaEmpleado empresaEmpleado;
    
    private TblViajesTiquetes viajesTiquetes;
    
    private Transaccion transaccion;
    
    private int total;
    
    public TblEmpleados() {
    }
    
    public TblEmpleados(String documento) {
        this.documento = documento;
    }
    
    public TblEmpleados(String documento, String nombre) {
        this.documento = documento;
        this.nombre = nombre;
    }
    
    public TblEmpleados(String documento, String nombre, String userMod, Date fechaMod, Date fechaCreacion) {
        this.documento = documento;
        this.nombre = nombre;
        this.userMod = userMod;
        this.fechaMod = fechaMod;
        this.fechaCreacion = fechaCreacion;
    }
    
    //empleados
    public TblEmpleados(String documento, String nombre, String telefono, String correo, String ot, int tiquetesPendientes,
            String userMod, Date fechaMod, Date fechaCreacion, int cod_estado) {
        this.documento = documento;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.ot = ot;
        this.tiquetesPendientes = tiquetesPendientes;
        this.userMod = userMod;
        this.fechaMod = fechaMod;
        this.fechaCreacion = fechaCreacion;
        this.empresaEmpleado = getEmpresaEmpleado();
        this.empresaEmpleado.setCodEstado(cod_estado);
//        if (this.empresaEmpleado.getCodEstado() == 1) {
//            this.setEstado(true);
//        } else {
//            this.setEstado(false);
//        }

    }

    //factura de venta
    public TblEmpleados(String documento,
            String nombre,
            String ot,
            String origen,
            String destino,
            int valor,
            int tiquetesAsignados,
            Date fecha_venta,
            String idaRegreso,
            int total, String orden_servicio, String tiquetesEntregados, String cm_asoc) {
        this.documento = documento;
        this.nombre = nombre;
        this.ot = ot;
        this.ciudadesOrigen = getCiudadesOrigen();
        this.ciudadesOrigen.setCiudad(origen);
        this.ciudadesDestino = getCiudadesDestino();
        this.ciudadesDestino.setCiudad(destino);
        this.detalleConvenio = getDetalleConvenio();
        this.detalleConvenio.setValorCliente(valor);
        this.viajesTiquetes = getViajesTiquetes();
        this.viajesTiquetes.setTiquetesAsignados(tiquetesAsignados);
        this.transaccion = getTransaccion();
        this.transaccion.setFechaMod(fecha_venta);
        this.transaccion.setTiqtes_entregados(tiquetesEntregados);
        this.viajesTiquetes.setIdaRegreso(idaRegreso);
        this.viajesTiquetes.setOs(orden_servicio);
        this.total = total;     
        this.cm_asoc = cm_asoc;
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
    
    public String getOt() {
        return ot;
    }
    
    public void setOt(String ot) {
        this.ot = ot;
    }
    
    public int getTiquetesPendientes() {
        return tiquetesPendientes;
    }
    
    public void setTiquetesPendientes(int tiquetesPendientes) {
        this.tiquetesPendientes = tiquetesPendientes;
    }
    
    public String getUserMod() {
        return userMod;
    }
    
    public void setUserMod(String userMod) {
        this.userMod = userMod;
    }
    
    public Date getFechaMod() {
        return fechaMod;
    }
    
    public void setFechaMod(Date fechaMod) {
        this.fechaMod = fechaMod;
    }
    
    public Date getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public EmpresaEmpleado getEmpresaEmpleado() {
        if (empresaEmpleado == null) {
            empresaEmpleado = new EmpresaEmpleado();
        }
        return empresaEmpleado;
    }
    
    public void setEmpresaEmpleado(EmpresaEmpleado empresaEmpleado) {
        this.empresaEmpleado = empresaEmpleado;
    }
    
    public boolean isEstado() {
        return estado;
    }
    
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    public String getOldoc() {
        return oldoc;
    }
    
    public void setOldoc(String oldoc) {
        this.oldoc = oldoc;
    }
    
    @Override
    public String toString() {
        return documento + " " + nombre;
    }
    
    public String getNomDoc() {
        return nomDoc;
    }
    
    public void setNomDoc(String nomDoc) {
        this.nomDoc = nomDoc;
    }
    
    
    
    
    
    public TblCiudades getCiudadesOrigen() {
        if (ciudadesOrigen == null) {
            ciudadesOrigen = new TblCiudades();
        }
        return ciudadesOrigen;
    }
    
    public void setCiudadesOrigen(TblCiudades ciudadesOrigen) {
        this.ciudadesOrigen = ciudadesOrigen;
    }
    
    public TblCiudades getCiudadesDestino() {
        if (ciudadesDestino == null) {
            ciudadesDestino = new TblCiudades();
        }
        return ciudadesDestino;
    }
    
    public void setCiudadesDestino(TblCiudades ciudadesDestino) {
        this.ciudadesDestino = ciudadesDestino;
    }

    public DetalleConvenio getDetalleConvenio() {
        if (detalleConvenio == null) {
            detalleConvenio = new DetalleConvenio();
        }
        return detalleConvenio;
    }

    public void setDetalleConvenio(DetalleConvenio detalleConvenio) {
        this.detalleConvenio = detalleConvenio;
    }

    public TblViajesTiquetes getViajesTiquetes() {
        if (viajesTiquetes == null) {
            viajesTiquetes = new TblViajesTiquetes();
        }
        return viajesTiquetes;
    }

    public void setViajesTiquetes(TblViajesTiquetes viajesTiquetes) {
        this.viajesTiquetes = viajesTiquetes;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Transaccion getTransaccion() {
        if (transaccion == null) {
            transaccion = new Transaccion();
        }
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public String getCm_asoc() {
        return cm_asoc;
    }

    public void setCm_asoc(String cm_asoc) {
        this.cm_asoc = cm_asoc;
    }
    
}
