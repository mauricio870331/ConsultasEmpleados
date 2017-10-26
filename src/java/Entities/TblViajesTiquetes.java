/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin
 */
public class TblViajesTiquetes implements Serializable {

    private int id_viaje_tiquete;

    private String opc = "add";

    private String documento;

    private int idEmpresa;

    private Date fechaInicial;

    private Date fechaFinal;

    private String strfechaInicial;

    private String strfechaFinal;

    private String idaRegreso;

    private float tiquetesAsignados;

    private String strtTquetesAsignados;

    private boolean selected;

    private float tiquetesEntregados;

    private String userMod;

    private DetalleConvenio detalleConvenio;

    private int total;

    private int idConvenio;

    private int detalleConvPk;

    private String estado_convenio;

    private EmpresaEmpleado empresaEmpleado;

    private Empresas empresa;

    private TblEmpleados empleado;

    private List<TransaccionViajero> transaccionViajeroList;

    private Date fechaMod;

    private String os;

    private float saldo;

    private boolean disabled;

    private float cantidadAEntregar;

    public TblViajesTiquetes() {
    }

    public TblViajesTiquetes(Date fechaInicial, Date fechaFinal, String idaRegreso, int tiquetesAsignados) {
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.idaRegreso = idaRegreso;
        this.tiquetesAsignados = tiquetesAsignados;
    }

    public TblViajesTiquetes(String servicio,
            String origen,
            String destino,
            Date fechaInicial,
            Date fechaFinal,
            String documento,
            String nombre,
            int tiquetesAsignados,
            String idaRegreso, int id_iaje_tiquete, String os, boolean disabled) {
        this.detalleConvenio = getDetalleConvenio();
        this.detalleConvenio.getTblservicio().setServicio(servicio);
        this.detalleConvenio.getCodCiudadOrigen().setCiudad(origen);
        this.detalleConvenio.getCodCiudadDestino().setCiudad(destino);
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.empleado = getEmpleado();
        this.empleado.setDocumento(documento);
        this.empleado.setNombre(nombre);
        this.tiquetesAsignados = tiquetesAsignados;
        this.idaRegreso = idaRegreso;
        this.id_viaje_tiquete = id_iaje_tiquete;
        this.os = os;
        this.disabled = disabled;

    }

    //saldos pendientes
    public TblViajesTiquetes(String servicio,
            String origen,
            String destino,
            Date fechaInicial,
            Date fechaFinal,
            String documento,
            String nombre,
            int tiquetesAsignados,
            String idaRegreso, int id_iaje_tiquete, String os, float tiquetesEntregados) {
        this.detalleConvenio = getDetalleConvenio();
        this.detalleConvenio.getTblservicio().setServicio(servicio);
        this.detalleConvenio.getCodCiudadOrigen().setCiudad(origen);
        this.detalleConvenio.getCodCiudadDestino().setCiudad(destino);
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.empleado = getEmpleado();
        this.empleado.setDocumento(documento);
        this.empleado.setNombre(nombre);
        this.tiquetesAsignados = tiquetesAsignados;
        this.idaRegreso = idaRegreso;
        this.id_viaje_tiquete = id_iaje_tiquete;
        this.os = os;
        this.tiquetesEntregados = tiquetesEntregados;

    }

    //entrega convenios
    public TblViajesTiquetes(int id_viaje_tiquete, int id_empresa,
            String nom_empresa,
            String origen,
            String destino,
            String servicio,
            String documento,
            String nombre,
            String idaRegreso,
            int valor_expal,
            float saldo,
            int detalle_convenio_pk,
            int id_convenio, int cod_ciudad_origen,
            int cod_ciudad_destino, int valor_cliente,
            float tiquetesEntregados, String estado_conv) {
        this.id_viaje_tiquete = id_viaje_tiquete;
        this.detalleConvenio = getDetalleConvenio();
        this.detalleConvenio.getTblservicio().setServicio(servicio);
        this.detalleConvenio.getCodCiudadOrigen().setCiudad(origen);
        this.detalleConvenio.getCodCiudadDestino().setCiudad(destino);
        this.detalleConvenio.setValorExpal(valor_expal);
        this.detalleConvenio.setValorCliente(valor_cliente);
        this.detalleConvenio.getCodCiudadOrigen().setCodCiudad(cod_ciudad_origen);
        this.detalleConvenio.getCodCiudadDestino().setCodCiudad(cod_ciudad_destino);
        this.detalleConvPk = detalle_convenio_pk;
        this.detalleConvenio.setId_convenio(id_convenio);
        this.empresa = getEmpresa();
        this.empresa.setId_empresa(id_empresa);
        this.empresa.setNombre(nom_empresa);
        this.empleado = getEmpleado();
        this.empleado.setDocumento(documento);
        this.empleado.setNombre(nombre);
        this.saldo = saldo;
        this.idaRegreso = idaRegreso;
        this.tiquetesEntregados = tiquetesEntregados;
        this.estado_convenio = estado_conv;

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

    public String getIdaRegreso() {
        return idaRegreso;
    }

    public void setIdaRegreso(String idaRegreso) {
        this.idaRegreso = idaRegreso;
    }

    public float getTiquetesAsignados() {
        return tiquetesAsignados;
    }

    public void setTiquetesAsignados(float tiquetesAsignados) {
        this.tiquetesAsignados = tiquetesAsignados;
    }

    public float getTiquetesEntregados() {
        return tiquetesEntregados;
    }

    public void setTiquetesEntregados(float tiquetesEntregados) {
        this.tiquetesEntregados = tiquetesEntregados;
    }

    public String getUserMod() {
        return userMod;
    }

    public void setUserMod(String userMod) {
        this.userMod = userMod;
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

    public EmpresaEmpleado getEmpresaEmpleado() {
        if (empresaEmpleado == null) {
            empresaEmpleado = new EmpresaEmpleado();
        }
        return empresaEmpleado;
    }

    public void setEmpresaEmpleado(EmpresaEmpleado empresaEmpleado) {
        this.empresaEmpleado = empresaEmpleado;
    }

    @XmlTransient
    public List<TransaccionViajero> getTransaccionViajeroList() {
        return transaccionViajeroList;
    }

    public void setTransaccionViajeroList(List<TransaccionViajero> transaccionViajeroList) {
        this.transaccionViajeroList = transaccionViajeroList;
    }

    public TblEmpleados getEmpleado() {
        if (empleado == null) {
            empleado = new TblEmpleados();
        }
        return empleado;
    }

    public void setEmpleado(TblEmpleados empleado) {
        this.empleado = empleado;
    }

    public String getStrtTquetesAsignados() {
        return strtTquetesAsignados;
    }

    public void setStrtTquetesAsignados(String strtTquetesAsignados) {
        this.strtTquetesAsignados = strtTquetesAsignados;
    }

    public int getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(int idConvenio) {
        this.idConvenio = idConvenio;
    }

    public int getDetalleConvPk() {
        return detalleConvPk;
    }

    public void setDetalleConvPk(int detalleConvPk) {
        this.detalleConvPk = detalleConvPk;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Date getFechaMod() {
        return fechaMod;
    }

    public void setFechaMod(Date fechaMod) {
        this.fechaMod = fechaMod;
    }

    public String getStrfechaInicial() {
        return strfechaInicial;
    }

    public void setStrfechaInicial(String strfechaInicial) {
        this.strfechaInicial = strfechaInicial;
    }

    public String getStrfechaFinal() {
        return strfechaFinal;
    }

    public void setStrfechaFinal(String strfechaFinal) {
        this.strfechaFinal = strfechaFinal;
    }

    public int getId_Viaje_tiquete() {
        return id_viaje_tiquete;
    }

    public void setId_Viaje_tiquete(int id_iaje_tiquete) {
        this.id_viaje_tiquete = id_iaje_tiquete;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
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

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public float getCantidadAEntregar() {
        return cantidadAEntregar;
    }

    @Override
    public String toString() {
        return "TblViajesTiquetes{" + "id_viaje_tiquete=" + id_viaje_tiquete + ", opc=" + opc + ", documento=" + documento + ", idEmpresa=" + idEmpresa + ", fechaInicial=" + fechaInicial + ", fechaFinal=" + fechaFinal + ", strfechaInicial=" + strfechaInicial + ", strfechaFinal=" + strfechaFinal + ", idaRegreso=" + idaRegreso + ", tiquetesAsignados=" + tiquetesAsignados + ", strtTquetesAsignados=" + strtTquetesAsignados + ", selected=" + selected + ", tiquetesEntregados=" + tiquetesEntregados + ", userMod=" + userMod + ", detalleConvenio=" + detalleConvenio + ", total=" + total + ", idConvenio=" + idConvenio + ", detalleConvPk=" + detalleConvPk + ", estado_convenio=" + estado_convenio + ", empresaEmpleado=" + empresaEmpleado + ", empresa=" + empresa + ", empleado=" + empleado + ", transaccionViajeroList=" + transaccionViajeroList + ", fechaMod=" + fechaMod + ", os=" + os + ", saldo=" + saldo + ", cantidadAEntregar=" + cantidadAEntregar + '}';
    }

    public void setCantidadAEntregar(float cantidadAEntregar) {
        this.cantidadAEntregar = cantidadAEntregar;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getEstado_convenio() {
        return estado_convenio;
    }

    public void setEstado_convenio(String estado_convenio) {
        this.estado_convenio = estado_convenio;
    }

    public String getOpc() {
        return opc;
    }

    public void setOpc(String opc) {
        this.opc = opc;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

}
