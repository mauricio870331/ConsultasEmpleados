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
 * @author clopez
 */
public class DesprendiblePago implements Serializable{
    
    private int idregistro;
    private int cod_trabajador;
    private String cod_empresa;
    private String cod_reloj;
    private String seccion;
    private String agencia;
    private String nom_empleado;
    private String cobra_banco;
    private String nom_empresa;
    private String proceso;
    private int sueldo;
    private int ordinario;
    private int fest;
    private int nlpe;
    private int inca;
    private int vaca;
    private int susp;
    private int lice;
    private int per;
    private int nolab;
    private int dia_descanso;
    private int hdom;
    private int hrn;
    private int hedn;
    private int henn;
    private int hedf;
    private int henf;
    private int base_sueldo;
    private int aux_transporte;
    private int domyfest;
    private int horasrecnoc;
    private int horasestras;
    private int horas_dominical;
    private int ing_descansos;
    private int otrosing;
    private int auxilio;
    private int ingreso;
    private int seguridad;
    private int val_incapacidad;
    private int otrosvalinca;
    private int cap_fond_solid;
    private int embargos;
    private int retefuente;
    private int camposanto;
    private int otrosdescuentos;
    private int neto;
    private int ajuste;
    private String documento;
    private String cod_sucursal;
    private String cod_seccion;
    private int incentivos;
    private Date fecha_ingreso;
    private int aniomes;

    public DesprendiblePago(int cod_trabajador, String cod_empresa, String cod_reloj, String seccion, String agencia, String nom_empleado, String cobra_banco, String nom_empresa, String proceso, int sueldo, int ordinario, int fest, int nlpe, int inca, int vaca, int susp, int lice, int per, int nolab, int dia_descanso, int hdom, int hrn, int hedn, int henn, int hedf,int henf, int base_sueldo, int aux_transporte, int domyfest, int horasrecnoc, int horasestras, int horas_dominical, int ing_descansos, int otrosing, int auxilio, int ingreso, int seguridad, int val_incapacidad, int otrosvalinca, int cap_fond_solid, int embargos, int retefuente, int camposanto, int otrosdescuentos, int neto, int ajuste, String documento, String cod_sucursal, String cod_seccion, int incentivos, Date fecha_ingreso, int aniomes,int idregistro) {
        this.cod_trabajador = cod_trabajador;
        this.cod_empresa = cod_empresa;
        this.cod_reloj = cod_reloj;
        this.seccion = seccion;
        this.agencia = agencia;
        this.nom_empleado = nom_empleado;
        this.cobra_banco = cobra_banco;
        this.nom_empresa = nom_empresa;
        this.proceso = proceso;
        this.sueldo = sueldo;
        this.ordinario = ordinario;
        this.fest = fest;
        this.nlpe = nlpe;
        this.inca = inca;
        this.vaca = vaca;
        this.susp = susp;
        this.lice = lice;
        this.per = per;
        this.nolab = nolab;
        this.dia_descanso = dia_descanso;
        this.hdom = hdom;
        this.hrn = hrn;
        this.hedn = hedn;
        this.henn = henn;
        this.hedf = hedf;
        this.henf = henf;
        this.base_sueldo = base_sueldo;
        this.aux_transporte = aux_transporte;
        this.domyfest = domyfest;
        this.horasrecnoc = horasrecnoc;
        this.horasestras = horasestras;
        this.horas_dominical = horas_dominical;
        this.ing_descansos = ing_descansos;
        this.otrosing = otrosing;
        this.auxilio = auxilio;
        this.ingreso = ingreso;
        this.seguridad = seguridad;
        this.val_incapacidad = val_incapacidad;
        this.otrosvalinca = otrosvalinca;
        this.cap_fond_solid = cap_fond_solid;
        this.embargos = embargos;
        this.retefuente = retefuente;
        this.camposanto = camposanto;
        this.otrosdescuentos = otrosdescuentos;
        this.neto = neto;
        this.ajuste = ajuste;
        this.documento = documento;
        this.cod_sucursal = cod_sucursal;
        this.cod_seccion = cod_seccion;
        this.incentivos = incentivos;
        this.fecha_ingreso = fecha_ingreso;
        this.aniomes = aniomes;
        this.idregistro = idregistro;
    }

    
    
    
   

  
    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public int getCod_trabajador() {
        return cod_trabajador;
    }

    public void setCod_trabajador(int cod_trabajador) {
        this.cod_trabajador = cod_trabajador;
    }

    public String getCod_empresa() {
        return cod_empresa;
    }

    public void setCod_empresa(String cod_empresa) {
        this.cod_empresa = cod_empresa;
    }

    public String getCod_reloj() {
        return cod_reloj;
    }

    public void setCod_reloj(String cod_reloj) {
        this.cod_reloj = cod_reloj;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNom_empleado() {
        return nom_empleado;
    }

    public void setNom_empleado(String nom_empleado) {
        this.nom_empleado = nom_empleado;
    }

    public String getCobra_banco() {
        return cobra_banco;
    }

    public void setCobra_banco(String cobra_banco) {
        this.cobra_banco = cobra_banco;
    }

    public String getNom_empresa() {
        return nom_empresa;
    }

    public void setNom_empresa(String nom_empresa) {
        this.nom_empresa = nom_empresa;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public int getSueldo() {
        return sueldo;
    }

    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
    }

    public int getOrdinario() {
        return ordinario;
    }

    public void setOrdinario(int ordinario) {
        this.ordinario = ordinario;
    }

    public int getFest() {
        return fest;
    }

    public void setFest(int fest) {
        this.fest = fest;
    }

    public int getNlpe() {
        return nlpe;
    }

    public void setNlpe(int nlpe) {
        this.nlpe = nlpe;
    }

    public int getInca() {
        return inca;
    }

    public void setInca(int inca) {
        this.inca = inca;
    }

    public int getVaca() {
        return vaca;
    }

    public void setVaca(int vaca) {
        this.vaca = vaca;
    }

    public int getSusp() {
        return susp;
    }

    public void setSusp(int susp) {
        this.susp = susp;
    }

    public int getLice() {
        return lice;
    }

    public void setLice(int lice) {
        this.lice = lice;
    }

    public int getPer() {
        return per;
    }

    public void setPer(int per) {
        this.per = per;
    }

    public int getNolab() {
        return nolab;
    }

    public void setNolab(int nolab) {
        this.nolab = nolab;
    }

    public int getDia_descanso() {
        return dia_descanso;
    }

    public void setDia_descanso(int dia_descanso) {
        this.dia_descanso = dia_descanso;
    }

    public int getHdom() {
        return hdom;
    }

    public void setHdom(int hdom) {
        this.hdom = hdom;
    }

    public int getHrn() {
        return hrn;
    }

    public void setHrn(int hrn) {
        this.hrn = hrn;
    }

    public int getHedn() {
        return hedn;
    }

    public void setHedn(int hedn) {
        this.hedn = hedn;
    }

    public int getHenn() {
        return henn;
    }

    public void setHenn(int henn) {
        this.henn = henn;
    }

    public int getHenf() {
        return henf;
    }

    public void setHenf(int henf) {
        this.henf = henf;
    }

    public int getBase_sueldo() {
        return base_sueldo;
    }

    public void setBase_sueldo(int base_sueldo) {
        this.base_sueldo = base_sueldo;
    }

    public int getAux_transporte() {
        return aux_transporte;
    }

    public void setAux_transporte(int aux_transporte) {
        this.aux_transporte = aux_transporte;
    }

    public int getDomyfest() {
        return domyfest;
    }

    public void setDomyfest(int domyfest) {
        this.domyfest = domyfest;
    }

    public int getHorasrecnoc() {
        return horasrecnoc;
    }

    public void setHorasrecnoc(int horasrecnoc) {
        this.horasrecnoc = horasrecnoc;
    }

    public int getHorasestras() {
        return horasestras;
    }

    public void setHorasestras(int horasestras) {
        this.horasestras = horasestras;
    }

    public int getHoras_dominical() {
        return horas_dominical;
    }

    public void setHoras_dominical(int horas_dominical) {
        this.horas_dominical = horas_dominical;
    }

    public int getIng_descansos() {
        return ing_descansos;
    }

    public void setIng_descansos(int ing_descansos) {
        this.ing_descansos = ing_descansos;
    }

    public int getOtrosing() {
        return otrosing;
    }

    public void setOtrosing(int otrosing) {
        this.otrosing = otrosing;
    }

    public int getAuxilio() {
        return auxilio;
    }

    public void setAuxilio(int auxilio) {
        this.auxilio = auxilio;
    }

    public int getIngreso() {
        return ingreso;
    }

    public void setIngreso(int ingreso) {
        this.ingreso = ingreso;
    }

    public int getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(int seguridad) {
        this.seguridad = seguridad;
    }

    public int getVal_incapacidad() {
        return val_incapacidad;
    }

    public void setVal_incapacidad(int val_incapacidad) {
        this.val_incapacidad = val_incapacidad;
    }

    public int getOtrosvalinca() {
        return otrosvalinca;
    }

    public void setOtrosvalinca(int otrosvalinca) {
        this.otrosvalinca = otrosvalinca;
    }

    public int getCap_fond_solid() {
        return cap_fond_solid;
    }

    public void setCap_fond_solid(int cap_fond_solid) {
        this.cap_fond_solid = cap_fond_solid;
    }

    public int getEmbargos() {
        return embargos;
    }

    public void setEmbargos(int embargos) {
        this.embargos = embargos;
    }

    public int getRetefuente() {
        return retefuente;
    }

    public void setRetefuente(int retefuente) {
        this.retefuente = retefuente;
    }

    public int getCamposanto() {
        return camposanto;
    }

    public void setCamposanto(int camposanto) {
        this.camposanto = camposanto;
    }

    public int getOtrosdescuentos() {
        return otrosdescuentos;
    }

    public void setOtrosdescuentos(int otrosdescuentos) {
        this.otrosdescuentos = otrosdescuentos;
    }

    public int getNeto() {
        return neto;
    }

    public void setNeto(int neto) {
        this.neto = neto;
    }

    public int getAjuste() {
        return ajuste;
    }

    public void setAjuste(int ajuste) {
        this.ajuste = ajuste;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCod_sucursal() {
        return cod_sucursal;
    }

    public void setCod_sucursal(String cod_sucursal) {
        this.cod_sucursal = cod_sucursal;
    }

    public String getCod_seccion() {
        return cod_seccion;
    }

    public void setCod_seccion(String cod_seccion) {
        this.cod_seccion = cod_seccion;
    }

    public int getIncentivos() {
        return incentivos;
    }

    public void setIncentivos(int incentivos) {
        this.incentivos = incentivos;
    }

    public int getAniomes() {
        return aniomes;
    }

    public void setAniomes(int aniomes) {
        this.aniomes = aniomes;
    }

    public int getHedf() {
        return hedf;
    }

    public void setHedf(int hedf) {
        this.hedf = hedf;
    }

    public int getIdregistro() {
        return idregistro;
    }

    public void setIdregistro(int idregistro) {
        this.idregistro = idregistro;
    }
    
    
    
}
