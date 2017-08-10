package Beans;

import Entities.*;
import Modelo.Conexion;
import Modelo.ConsultaGeneral;
import Modelo.CrudObject;
import Utils.CiudadesUtils;
import java.io.IOException;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.component.growl.Growl;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
@Named(value = "tarifasBean")
@SessionScoped
public class TarifasBean implements Serializable {

    /**
     * Variable a. para controlar los mensajes en la vista
     */
    long a = -1;
    /**
     * Variable privada: listEmpConvenio. Contendra el listado de las empresas
     * que tiene convenio actualmente.
     */
    private List<Empresas> listEmpConvenio = new ArrayList();
    /**
     * Variable privada: detalleConvenioList. Contendra el listado de tarifas de
     * cada empresa en convenio.
     */
    private List<DetalleConvenio> detalleConvenioList = new ArrayList();
    /**
     * Variable privada: listEmpresas. Contendra el listado de empresas
     */
    private List<Empresas> listEmpresas = new ArrayList();
    /**
     * Variable privada: empresasCurrent. contendra el objeto de empresa
     * seleccionado actualmente
     */
    private Empresas empresasCurrent;
    /**
     * Variable privada: listCiudaes. Contendra el listado de las ciudades
     */
    private List<Ciudad> listCiudaes = new ArrayList();
    /**
     * Variable privada: listServicios. Contendra el listado de los servicios
     */
    private List<TblServicios> listServicios = new ArrayList();
    /**
     * Variable privada: listConvenios. Contendra el listado de convenios
     */
    private List<TblConvenio> listConvenios = new ArrayList();
    /**
     * Variable: ListTarifa. Variable para la navegacion vista TarifasList.xhtml
     */
    String ListTarifa = "../Tarifas/TarifasList.xhtml";
    /**
     * Variable: CreaTarifa. Variable para la navegacion vista
     * TarifasCrear.xhtml
     */
    String CreaTarifa = "../Tarifas/TarifasCrear.xhtml";
    /**
     * Variable: EditarTarifa. Variable para la navegacion vista
     * TarifasEditar.xhtml
     */
    String EditarTarifa = "../Tarifas/TarifasEditar.xhtml";
    /**
     * Variable: CreaTarifaDetalle. Variable para la navegacion vista
     * TarifasDetalleCrear.xhtml
     */
    String CreaTarifaDetalle = "../Tarifas/TarifasDetalleCrear.xhtml";
    /**
     * Variable: ListTarifaDetalle. Variable para la navegacion vista
     * TarifasDetalle.xhtml
     */
    String ListTarifaDetalle = "../Tarifas/TarifasDetalle.xhtml";
    /**
     * Variable: ListTarifaDetalleHistory. Variable para la navegacion vista
     * TarifasDetalleHistory.xhtml
     */
    String ListTarifaDetalleHistory = "../Tarifas/TarifasDetalleHistory.xhtml";
    /**
     * Variable privada: idconvenio. Es el Id del convenio seleccionado
     */
    private int idconvenio;
    /**
     * Variable privada: convenio. almacenara el objeto TblConvenio actualmente
     * seleccionado
     */
    private TblConvenio convenio;
    /**
     * Variable privada: detalleconvenio. almacenara el objeto DetalleConvenio
     * actualmente seleccionado
     */
    private DetalleConvenio detalleconvenio;
    /**
     * Variable: format. Variable para formatear las fechas con hora
     */
    SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
    /**
     * Variable: format2. Variable para formatear las fechas sin hora
     */
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd");
    /**
     * Variable: growl. Variable que instancia el contenedor de mensajes en las
     * vistas
     */
    Growl growl = new Growl();
    /**
     * Variable obj. Contendra los datos del convenoi para mostrar el detalle
     * del convenio
     */
    ArrayList<String> obj = new ArrayList<>();
    /**
     * Variable privada: origen. almacenara el origen seleccionado para usar en
     * los filtros
     */
    private int origen;
    /**
     * Variable privada: destino. almacenara el destino seleccionado para usar
     * en los filtros
     */
    private int destino;

    public TarifasBean() {
    }

    @PostConstruct
    public void init() {
        try {
            growl.setLife(5000);
            listarTarifas();
            listEmpresas();
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        }
    }

    /**
     * Método que Listara las empresas para renderizarlas en la vista
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos desde la bd
     * @since incluido desde la version 1.0
     */
    private void listEmpresas() throws SQLException {
        listEmpresas.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CiudadesUtils.getSelectSql("empresaUtil", 1, "param");
        for (ConsultaGeneral obj : l) {
            listEmpresas.add(new Empresas(obj.getNum1(), obj.getStr1()));
        }
    }

    /**
     * Método que Listara las tarifas o viajes que tiene autorizados cada
     * empresa
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos desde la bd
     * @since incluido desde la version 1.0
     */
    private void listarTarifas() throws SQLException {
        listEmpConvenio.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CrudObject.getSelectSql("empresaConvenio", 1, "param");
        for (ConsultaGeneral object : l) {
            listEmpConvenio.add(new Empresas(object.getNum1(), object.getStr1(), object.getNum2(), object.getStr2(), object.getFecha1(),
                    object.getFecha2(), object.getNum3(), object.getStr3()));
        }
        if (a == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Error al actualizar, comuniquese con el administrador"));
            a = -1;
        }
    }

    /**
     * Método que Listara las tarifas de los convenios apartir del id de
     * convenio
     *
     * @param idConvenio
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos desde la bd
     * @since incluido desde la version 1.0
     */
    public void listarTarifasDetalle(int idConvenio) throws SQLException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idConvenio", idConvenio);
        detalleConvenioList.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CrudObject.getSelectSql("detalleConvenio", 1, "" + idConvenio);
        for (ConsultaGeneral object : l) {
            System.out.println(object.getFecha1() + " " + object.getFecha2() + " " + object.getNum2() + " " + object.getStr2());
            detalleConvenioList.add(new DetalleConvenio(object.getNum1(), object.getNum2(), object.getNum3(), object.getNum4(), object.getNum5(), object.getNum6(),
                    object.getNum7(), object.getNum8(), object.getNum9(), object.getFecha1(), object.getFecha2(), object.getStr3(), object.getStr4(),
                    object.getStr5(), object.getStr6(), object.getStr7()));
        }
        if (a == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Error al eliminar, comuniquese con el administrador"));
            a = -1;
        }
    }

    /**
     * Método que Listara las tarifas de los convenios apartir del origen ,
     * destino y id_convenio
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos desde la bd
     * @since incluido desde la version 1.0
     */
    public void listarTarifasDetOrigenDest() throws SQLException {
        System.out.println("getOrigen() " + getOrigen());
        int id_conv = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idConvenio");
        detalleConvenioList.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        if (getOrigen() > 0 && getDestino() > 0) {
            l = (ArrayList) CrudObject.getSelectSql("detalleConvenioP", 1, "" + id_conv + "," + getOrigen() + "," + getDestino() + "");
        } else {
            l = (ArrayList) CrudObject.getSelectSql("detalleConvenio", 1, "" + id_conv);
        }
        for (ConsultaGeneral object : l) {
            detalleConvenioList.add(new DetalleConvenio(object.getNum1(), object.getNum2(), object.getNum3(), object.getNum4(), object.getNum5(), object.getNum6(),
                    object.getNum7(), object.getNum8(), object.getNum9(), object.getFecha1(), object.getFecha2(), object.getStr3(), object.getStr4(),
                    object.getStr5(), object.getStr6(), object.getStr7()));
        }
        if (a == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Error al eliminar, comuniquese con el administrador"));
            a = -1;
        }
    }

    /**
     * Método que Listara los conven ios que han tenido las empresas con expreso
     * palmrira, es para llevar un control historico
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos desde la bd
     * @since incluido desde la version 1.0
     */
    private void listarConveniosHistory(int idEmpresa) throws SQLException {
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CrudObject.getSelectSql("historyConvenio", 1, "" + idEmpresa);
        for (ConsultaGeneral object : l) {
            listConvenios.add(new TblConvenio(object.getNum1(), object.getStr1(), object.getFecha1(), object.getFecha2(), object.getStr2(), object.getNum3(),
                    object.getStr3(), object.getStr4(), object.getNum2()));
        }
        if (a == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Error al eliminar, comuniquese con el administrador"));
            a = -1;
        }
    }

    /**
     * Método que permite crear las tarifas o viajes para cada convenio
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos desde la bd
     * @throws java.lang.InterruptedException
     * @throws java.text.ParseException
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void createTarifa() throws SQLException, InterruptedException, IOException, ParseException {
        if (convenio.getTipoContrato().equals("Cerrado") && convenio.getValorGlobal() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Ingrese Un valor Global"));
        } else {
            LoginBean log = new LoginBean();
            convenio.setUser_mod(log.getDocumentoUserLog());
            convenio.setFechaMod(format.format(new Date()));
            convenio.setSaldoValorGlobal(0);
            convenio.setEstado("A");
            a = CrudObject.create(convenio);
            if (a == 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Tarifa Creada"));
                listEmpConvenio.clear();
                listarTarifas();
            }
            convenio = null;
            log = null;
        }

    }

    /**
     * Método que permite editar los convenios
     *
     * @throws java.sql.SQLException Error de Sql, Ocurre cuando se presenta un
     * error al editar el convenio en la bd
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     * @throws java.text.ParseException
     * @since incluido desde la version 1.0
     */
    public void editConvenio() throws SQLException, InterruptedException, IOException, ParseException {
        if (empresasCurrent.getTipo_contrato().equals("Cerrado") && empresasCurrent.getValor_global() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Ingrese Un valor Global"));
        } else {
            LoginBean log = new LoginBean();
            empresasCurrent.setUser_mod(log.getDocumentoUserLog());
            empresasCurrent.setFechaMod(new Date());
            empresasCurrent.setSaldoValorGlobal(0);
            empresasCurrent.setEstado("A");
            a = CrudObject.editConvenio(empresasCurrent);
            if (a == 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Convenio Actualizado"));
                listEmpConvenio.clear();
                listarTarifas();
            }
            empresasCurrent = null;
            log = null;
        }

    }

    /**
     * Método que Listara las ciudades para renderizarlas en la vista
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos de la tabla tbl_ciudades
     * @since incluido desde la version 1.0
     */
    private void lisCiudades() throws SQLException {
        listCiudaes.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CiudadesUtils.getSelectSql("ciudadUtil", 1, "param");
        for (ConsultaGeneral object : l) {
            listCiudaes.add(new Ciudad(object.getNum1(), object.getStr1()));
        }
    }

    /**
     * Método que Listara los servicios para renderizarlos en la vista
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos desde la bd
     * @since incluido desde la version 1.0
     */
    private void lisServicios() throws SQLException {
        listServicios.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CiudadesUtils.getSelectSql("servicioUtil", 1, "param");
        for (ConsultaGeneral object : l) {
            listServicios.add(new TblServicios(object.getNum1(), object.getStr1()));
        }
    }

    /**
     * Método que Listara el detalle del convenio, es decir mostrara las tarifas
     * o viajes en la vista
     *
     * @param idConvenio
     * @param idEmpresa
     * @param nomEmpresa
     * @param opc
     * @param convenio
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos desde la bd
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void viewDetalle(String idConvenio, String idEmpresa, String nomEmpresa, boolean opc, String convenio) throws IOException, SQLException {
        objectSession(idConvenio, idEmpresa, nomEmpresa, convenio);
        listarTarifasDetalle(Integer.parseInt(idConvenio));
        lisCiudades();
        lisServicios();
        if (opc) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Tarifas/TarifasDetalle.xhtml");
        } else if (convenio.equals("")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Tarifas/TarifasDetalleh.xhtml");
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Tarifas/TarifasDetalleh2.xhtml");
        }

    }

    /**
     * Método que Listara el historico de convenios
     *
     * @param idEmpresa
     * @param nomEmpresa
     * @param opc
     * @param convenio
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos desde la bd
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void viewDetalleHistory(String idEmpresa, String nomEmpresa, boolean opc, String convenio) throws IOException, SQLException {
        objectSession("", idEmpresa, nomEmpresa, convenio);
        listConvenios.clear();
        listarConveniosHistory(Integer.parseInt(idEmpresa));
        if (opc) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Tarifas/TarifasDetalleHistory.xhtml");
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Tarifas/TarifasDetalleHistoryh.xhtml");
        }
    }

    /**
     * Método que permitira crear las tarifas para cada convenio
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos desde la bd
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws java.text.ParseException
     * @since incluido desde la version 1.0
     */
    public void createTarifaDetalle() throws SQLException, InterruptedException, IOException, ParseException {
        obj = (ArrayList) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("obj");
        LoginBean log = new LoginBean();
        detalleconvenio.setIdUsuario(log.getIdUserLog());
        detalleconvenio.setId_convenio(Integer.parseInt(obj.get(0)));
        detalleconvenio.setId_empresa(Integer.parseInt(obj.get(1)));
        detalleconvenio.setFechaCreacion(format.format(new Date()));
        detalleconvenio.setCod_estado(38);
        a = CrudObject.create(detalleconvenio);
        if (a == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Detalle Creado"));
            detalleConvenioList.clear();
            listarTarifasDetalle(Integer.parseInt(obj.get(0)));
        }
        detalleconvenio = null;
        log = null;
    }

    /**
     * Método que permitira actualizar las tarifas para cada convenio
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos desde la bd
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws java.text.ParseException
     * @since incluido desde la version 1.0
     */
    public void createTarifaDetalleUpdate() throws SQLException, InterruptedException, IOException, ParseException {
        obj = (ArrayList) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("obj");
        LoginBean log = new LoginBean();
        detalleconvenio.setIdUsuario(log.getIdUserLog());
        detalleconvenio.setId_convenio(Integer.parseInt(obj.get(0)));
        detalleconvenio.setId_empresa(Integer.parseInt(obj.get(1)));
        detalleconvenio.setFechaCreacion(format.format(new Date()));
        detalleconvenio.setCod_estado(38);
        a = CrudObject.edit(detalleconvenio);
        if (a > 0) {
            detalleConvenioList.clear();
            listarTarifasDetalle(Integer.parseInt(obj.get(0)));
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Tarifas/TarifasDetalle.xhtml");
        }
        detalleconvenio = null;
        log = null;
    }

    /**
     * Método que obtiene la empresa establecida en el array de sesion
     *
     * @return
     * @since incluido desde la version 1.0
     */
    public String getEmpresaSession() {
        obj = (ArrayList) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("obj");
        return obj.get(2);
    }

    /**
     * Método que obtiene la descripcion del convenio establecido en el array de
     * sesion
     *
     * @return
     * @since incluido desde la version 1.0
     */
    public String getDescConvenioSession() {
        obj = (ArrayList) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("obj");
        return obj.get(3);
    }

    /**
     * Método que setea el detalle convenio actual a editar
     *
     * @param d
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void prepareEditDetalle(DetalleConvenio d) throws IOException, SQLException {
        setDetalleconvenio(d);
        lisServicios();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Tarifas/TarifasEditaDetalle.xhtml");
    }

    /**
     * Método que setea el detalle convenio actual a eliminar
     *
     * @param d
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void preparedeletetDetalle(DetalleConvenio d) throws IOException {
        setDetalleconvenio(d);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Tarifas/DeleteDetalle.xhtml");
    }

    /**
     * Método que setea el convenio actual a editar
     *
     * @param c
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void editarConvenio(Empresas c) throws IOException {
        setEmpresasCurrent(c);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Tarifas/TarifasEditar.xhtml");
    }

    /**
     * Método que setea la convenio actual para su eliminación
     *
     * @param c convenio a eliminar
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista CiudadConfirmDelete.xhtml
     * @since incluido desde la version 1.0
     */
    public void confirmDelete(TblConvenio c) throws IOException {
        setConvenio(c);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Tarifas/TarifasConfirmDelete.xhtml");
    }

    /**
     * Método que Cancela la eliminacion del convenio
     *
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista TarifasList.xhtml
     * @since incluido desde la version 1.0
     */
    public void cancelDelete() throws IOException {
        convenio = null;
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Tarifas/TarifasList.xhtml");
    }

    /**
     * Método que Cancela la eliminacion de tarifas para convenios
     *
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista TarifasDetalle.xhtml
     * @since incluido desde la version 1.0
     */
    public void cancelDeleteDetalle() throws IOException {
        detalleconvenio = null;
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Tarifas/TarifasDetalle.xhtml");
    }

    /**
     * Método que permite eliminar convenios
     *
     * @throws java.sql.SQLException
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista TarifasDetalle.xhtml
     * @since incluido desde la version 1.0
     */
    public void delete() throws SQLException, InterruptedException, IOException {
        convenio.setFechaMod(format.format(new Date()));
        a = CrudObject.delete(convenio);
        if (a == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Tarifas/TarifasList.xhtml");
            listarTarifas();
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Tarifas/TarifasList.xhtml");
        }
        convenio = null;
    }

    /**
     * Método que lista las tarifas o viajes de convenios y redirige a la vista
     * TarifasList.xhtml
     *
     * @return
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public String getListTarifa() throws SQLException {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idconvenio") != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idconvenio");
        }
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idEmpresa") != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idEmpresa");
        }
        listEmpresas();
        listarTarifas();
        return ListTarifa;
    }

    /**
     * Método que elimina una tarifa seleccionada
     *
     * @throws java.sql.SQLException Error que ocurre al intentar eliminar una
     * ciudad en la tabla tbl_ciudades
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista CiudadesList.xhtml
     * @since incluido desde la version 1.0
     */
    public void deleteDetalle() throws SQLException, InterruptedException, IOException {
        a = CrudObject.delete(detalleconvenio);
        if (a == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Tarifas/TarifasDetalle.xhtml");
            listServicios.clear();
            listarTarifasDetalle((int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idConvenio"));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Tarifas/TarifasDetalle.xhtml");
        }
        detalleconvenio = null;
    }

    /**
     * Método que establece en un el arraylist obj los datos de un convenio
     * seleccionado, para posteriormente usar sus datos en las vistas y realizar
     * consultas
     *
     * @throws java.sql.SQLException Error que ocurre al intentar eliminar una
     * ciudad en la tabla tbl_ciudades
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    private void objectSession(String idConvenio, String idEmpresa, String nomEmpresa, String convenio) {
        if (obj.size() > 0) {
            obj.set(0, idConvenio);
            obj.set(1, idEmpresa);
            obj.set(2, nomEmpresa);
            obj.set(3, convenio);
        } else {
            obj.add(idConvenio);
            obj.add(idEmpresa);
            obj.add(nomEmpresa);
            obj.add(convenio);
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("obj", obj);
    }

    public String getListTarifaDetalleHistory() {
        return ListTarifaDetalleHistory;
    }

    public void setListTarifa(String ListTarifa) {
        this.ListTarifa = ListTarifa;
    }

    public String getCrearTarifa() {
        convenio = null;
        return CreaTarifa;
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public String getEditarTarifa() {
        return EditarTarifa;
    }

    public void setEditarTarifa(String EditarTarifa) {
        this.EditarTarifa = EditarTarifa;
    }

    public List<Empresas> getListEmpConvenio() {
        return listEmpConvenio;
    }

    public void setListEmpConvenio(List<Empresas> listEmpConvenio) {
        this.listEmpConvenio = listEmpConvenio;
    }

    public TblConvenio getConvenio() {
        if (convenio == null) {
            convenio = new TblConvenio();
        }
        return convenio;
    }

    public void setConvenio(TblConvenio convenio) {
        this.convenio = convenio;
    }

    public List<Empresas> getListEmpresas() {
        return listEmpresas;
    }

    public void setListEmpresas(List<Empresas> listEmpresas) {
        this.listEmpresas = listEmpresas;
    }

    public int getIdconvenio() {
        return idconvenio;
    }

    public void setIdconvenio(int idconvenio) {
        this.idconvenio = idconvenio;
    }

    public List<DetalleConvenio> getDetalleConvenioList() {
        return detalleConvenioList;
    }

    public void setDetalleConvenioList(List<DetalleConvenio> detalleConvenioList) {
        this.detalleConvenioList = detalleConvenioList;
    }

    public String getCreaTarifaDetalle() {
        detalleconvenio = null;
        return CreaTarifaDetalle;
    }

    public void setCreaTarifaDetalle(String CreaTarifaDetalle) {
        this.CreaTarifaDetalle = CreaTarifaDetalle;
    }

    public DetalleConvenio getDetalleconvenio() {
        if (detalleconvenio == null) {
            detalleconvenio = new DetalleConvenio();
        }
        return detalleconvenio;
    }

    public void setDetalleconvenio(DetalleConvenio detalleconvenio) {
        this.detalleconvenio = detalleconvenio;
    }

    public List<Ciudad> getListCiudaes() {
        return listCiudaes;
    }

    public void setListCiudaes(List<Ciudad> listCiudaes) {
        this.listCiudaes = listCiudaes;
    }

    public List<TblServicios> getListServicios() {
        return listServicios;
    }

    public void setListServicios(List<TblServicios> listServicios) {
        this.listServicios = listServicios;
    }

    public String getCreaTarifa() {
        return CreaTarifa;
    }

    public void setCreaTarifa(String CreaTarifa) {
        this.CreaTarifa = CreaTarifa;
    }

    public String getListTarifaDetalle() throws SQLException {
        detalleConvenioList.clear();
        detalleconvenio = null;
        ArrayList<String> obj = new ArrayList<>();
        obj = (ArrayList) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("obj");
        listarTarifasDetalle(Integer.parseInt(obj.get(0)));
        return ListTarifaDetalle;
    }

    public void setListTarifaDetalle(String ListTarifaDetalle) {
        this.ListTarifaDetalle = ListTarifaDetalle;
    }

    public List<TblConvenio> getListConvenios() {
        return listConvenios;
    }

    public void setListConvenios(List<TblConvenio> listConvenios) {
        this.listConvenios = listConvenios;
    }

    public int getOrigen() {
        return origen;
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public Empresas getEmpresasCurrent() {
        if (empresasCurrent == null) {
            empresasCurrent = new Empresas();
        }
        return empresasCurrent;
    }

    public void setEmpresasCurrent(Empresas empresasCurrent) {
        this.empresasCurrent = empresasCurrent;
    }

}
