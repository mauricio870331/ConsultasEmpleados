package Beans;

import Entities.*;
import Modelo.ConexionPool;
import Modelo.ConsultaGeneral;
import Modelo.CrudObject;
import Utils.CiudadesUtils;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.primefaces.component.growl.Growl;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
@Named(value = "transaccionesBean")
@SessionScoped
public class TransaccionesBean implements Serializable {

    /**
     * Variable privada: saldos. Contendra el listado de viajes y tiquetes que
     * se han de entregar a los epleados
     */
    private List<TiquetesAutorizados> TiquetesAutorizados = new ArrayList();
    private List<TiquetesAutorizados> tiquetesAutorizadosTolist = new ArrayList();
    private List<TblViajesTiquetes> saldos = new ArrayList();
    /**
     * Variable privada: tiquetesToList. auxiliar para almacenar los viajes y
     * tiquetes que se deben entregar para cada empleado
     */
    private List<TblViajesTiquetes> tiquetesToList = new ArrayList();
    /**
     * Variable privada: tiquetesCurrrent. almacenara el objeto
     * TblViajesTiquetes actualmente seleccionado
     */
    private TblViajesTiquetes tiquetesCurrrent;
    private TiquetesAutorizados autorizadosCurrent;

    private String ConvenioEstudiantes = "../Taquilla/ConvenioEstudiante.xhtml";
    /**
     * Variable privada: transaccion. almacenara el objeto Transaccion
     * actualmente seleccionado
     */
    private Transaccion transaccion;

    private String emprsa = "";

    private String[] selectedEmpresas;

    private String selectEmpresa;
    /**
     *
     * Variable privada: impresionesDia. auxiliar para almacenar los tiquetes
     * entregados al dia
     */
    private String idTransAnular;
    private ArrayList<Empresas> list_empresas = new ArrayList();

    private List<String> users = new ArrayList();

    private List<Transaccion> impresionesDia = new ArrayList();
    /**
     * Variable privada: impresionesCtra. auxiliar para almacenar las contravias
     * que se generan por dia
     */
    private List<TblRegistroContravias> impresionesCtra = new ArrayList();
    /**
     * Variable privada: fec. contendra una fecha seleccionada para consultas
     */
    String fec = "";
    /**
     * Variable privada: listUsuariosAutoComplete. auxiliar para almacenar los
     * empleados y cargarlos en el componente autocompletar de primefaces
     */
    private List<TblEmpleados> listUsuariosAutoComplete = new ArrayList();
    private String msn = "";
    /**
     * Variable: format. Variable para formatear las fechas con hora
     */
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    /**
     * Variable: format2. Variable para formatear las fechas sin hora
     */
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Variable: growl. Variable que instancia el contenedor de mensajes en las
     * vistas
     */
    Growl growl = new Growl();
    /**
     * Variable: validate. No usada
     */
    boolean validate = false;
    /**
     * Variable: idavuelta. No usada
     */
    boolean idavuelta = false;
    /**
     * Variable: selectRuta. contendra el id de la ruta seleccionada para
     * consultas
     */
    int selectRuta;
    /**
     * Variable: selecFecha. contendra la fecha seleccionada para consultas
     */
    Date selecFecha;
    /**
     * Variable: listaEntrega. Variable para la navegacion vista
     * ListaEntrega.xhtml
     */
    private String listaEntrega = "../Taquilla/ListaEntrega.xhtml";

    private String listAutorizados = "../Taquilla/ListAutorizados.xhtml";

    private String user;

    private String numeroCm = "";
    /**
     * Variable: impresiones. Variable para la navegacion vista
     * ImpresionesRealizadas.xhtml
     */
    private String impresiones = "../Taquilla/ImpresionesRealizadas.xhtml";
    /**
     * Variable: impresionesCtravias. Variable para la navegacion vista
     * ImpresionesRealizadasC.xhtml
     */
    private String impresionesCtravias = "../../Admin/Reportes/ImpresionesRealizadasC.xhtml";

    private String listRelacion = "../../Admin/Reportes/ListRelacion.xhtml";
    /**
     * Variable: reimpCompCtravia. Variable para la navegacion vista
     * ReimprimirComprobante.xhtml
     */
    private String reimpCompCtravia = "../Taquilla/ReimprimirComprobante.xhtml";
    /**
     * Variable: resumenContravias. Variable para la navegacion vista
     * ResumenContravias.xhtml
     */
    private String resumenContravias = "../Taquilla/ResumenContravias.xhtml";
    /**
     * Variable: selectUser. contendra el usuario seleccionado para consultas
     */
    String selectUser = "";
    /**
     * Variable: usuarioNodum. contendra el usuarioNodum, es decir el usuario
     * que ha iniciado sesion y va a realizar la entrega de los viajes o
     * tiquetes
     */
    private String usuarioNodum = "";
    /**
     * Variable: claveNodum. contendra el claveNodum, es decir la contraseña del
     * usuario que ha iniciado sesion y va a realizar la entrega de los viajes o
     * tiquetes
     */
    private String claveNodum = "";
    /**
     * Variable: usuarioTaquilla. contendra la taquilla que realizara la entrega
     *
     */
    private String usuarioTaquilla;
    /**
     * Variable: descripcionTiquetes. contendra una breve descripcion al momento
     * de entregar los viajes o tiquetes. No es obligatoria
     *
     */
    private String descripcionTiquetes;
    /**
     * Variable: cantTiquetesTrans. contendra la cantidad de tiquetes a entregar
     * por transaccion
     *
     */
    private int cantTiquetesTrans;
    /**
     * Variable: totalTrans. contendra contendra el costo todal de la
     * transaccion actual a entregar
     *
     */
    private int totalTrans;
    /**
     * Variable: disable. usada para habilitar y deshabilitar controles en la
     * vista
     *
     */
    private boolean disable = true;
    /**
     * Variable: disable2. usada para habilitar y deshabilitar controles en la
     * vista
     *
     */
    private boolean disable2 = true;
    /**
     * Variable: a. usada para mostrar mensajes en la vista
     *
     */
    private int a = 0;

    private String autorizados = "";
    /**
     * Variable: tempValorGlobal. temporal para almacenar el valor global del
     * convenio para posteriores calculos
     *
     */
    private int tempValorGlobal;
    /**
     * Variable: tempTipoCOntrato. temporal para almacenar el tipo de contrato
     * que maneja un convenio
     *
     */
    private String tempTipoCOntrato;
    /**
     * Variable: selecFechaIni. Para almacenar la fecha inicial seleccionada
     * para las busquedas
     *
     */
    private Date selecFechaIni;
    /**
     * Variable: selecFechaFin. Para almacenar la fecha final seleccionada para
     * las busquedas
     *
     */
    private Date selecFechaFin;

    private String CmList = "../Valores/ListRelacion.xhtml";

    private String CmListAdmin = "../../Admin/Docs/ListCm.xhtml";

    private String CmListAdminA = "../Auditoria/ListRelacion.xhtml";

    private String CmGenerar = "../Valores/GenerarRelacion.xhtml";

    private boolean printCm = true;

    private boolean printCm2 = true;

    private CmGenerado cmgenerado;

    private List<String> cars = new ArrayList();

    private List<CmGenerado> CmgeneradoList = new ArrayList();
    private List<CmGenerado> CmgeneradoListDescripcion = new ArrayList();

    private List<DetalleCm> detalleCmList = new ArrayList();

    private List<DetalleCm> detalleCmList2 = new ArrayList();

    private List<DetalleCm> currentCmList2 = new ArrayList();

    private List<String> empresaAnterior = new ArrayList();

    private List<String> trans = new ArrayList();

    private DetalleCm detalleCm;

    private String conseucutivo = "";

    private String tiqs = "";

    private List<Estudiantes> listEstudiantes = new ArrayList();

    // private List<TblViajesTiquetes> saldos = new ArrayList();
    public TransaccionesBean() {
    }

    @PostConstruct
    public void init() {
        growl.setLife(5000);
        saldos.clear();
        tiquetesAutorizadosTolist.clear();
        TiquetesAutorizados.clear();
        LoginBean log = new LoginBean();
        if (log.getRol().equals("EMPRESA")) {
            listarAutocomplete();
        }
        if (log.getRol().equals("VALORES")) {
            try {
                listarCms("cmGen");
            } catch (SQLException e) {
                System.out.println("error " + e);
            }
        }
        if (log.getRol().equals("REVISIONRELACION")) {
            try {
                cargarUsuarios();
                listarCms("cmGenAdmin");
            } catch (SQLException e) {
                System.out.println("error " + e);
            }
        }

    }

    public void listarCms(String vista) throws SQLException {
        CmgeneradoList.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        String param = "";
        if (vista.equals("cmGen")) {
            LoginBean log = new LoginBean();
            param = log.getNomUserLog();
        }

        System.out.println("param " + param);
        l = (ArrayList) CrudObject.getSelectSql(vista, 1, param);
        for (ConsultaGeneral obj : l) {
            CmgeneradoList.add(new CmGenerado(obj.getStr1(), obj.getStr2(), obj.getStr3(), obj.getFecha1(), obj.isBool1(), obj.getNum1(), obj.getStr4(), obj.getNum2(), obj.getNum3()));
        }
    }

    public void listarCmsAdmin(String vista) throws SQLException {
        System.out.println("vista" + vista + "selecFechaIni " + selecFechaIni + " selecFechaFin " + selecFechaFin);
        CmgeneradoList.clear();
        numeroCm = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("numeroCm");
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        String param = "";
        if (vista.equals("cmGenAdminF")) {
            if (!numeroCm.equals("")) {
                param = numeroCm;
                vista = "cmGenAdminByCm";
                CmgeneradoListDescripcion = Utils.CiudadesUtils.returnCmsAsocDescrip(numeroCm);
            } else {
                if (selecFechaIni != null && selecFechaFin != null) {
                    param += format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59";
                    if (!user.equals("")) {
                        param += "," + user;
                        vista = "cmGenAdminU";
                    }
                } else {
                    vista = "cmGenAdmin";
                }
            }
        }
        l = (ArrayList) CrudObject.getSelectSql(vista, 1, param);
        for (ConsultaGeneral obj : l) {
            CmgeneradoList.add(new CmGenerado(obj.getStr1(), obj.getStr2(), obj.getStr3(), obj.getFecha1(), obj.isBool1(), obj.getNum1(), obj.getStr4(), obj.getNum2(), obj.getNum3()));
        }

//        if (CmgeneradoListDescripcion.size() > 0) {
//            CmgeneradoListDescripcion.forEach((cm) -> {
//                System.out.println(cm.toString());
//            });
//        }
//        numeroCm = "";
//        selecFechaIni = null;
//        selecFechaFin = null;
        if (CmgeneradoList.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
        }
    }

    public void clearCmsAdmin() throws SQLException {
        CmgeneradoList.clear();
        numeroCm = "";
        selecFechaIni = null;
        selecFechaFin = null;
        user = "";
    }

    public void verificarCmAsoc(String consecutivo, boolean verificado) throws SQLException {
        System.out.println("hola mundo");
        int val = 0;
        if (verificado) {
            val = 1;
        }
        Utils.CiudadesUtils.editCm(consecutivo, val);
        listarCms("cmGenAdmin");
    }

    public void cargarDatos() throws SQLException {
        try {
            list_empresas.clear();
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            l = (ArrayList) CrudObject.getSelectSql("registroTiquete", 3, "nada");
            for (ConsultaGeneral consultaGeneral : l) {
                list_empresas.add(new Empresas(consultaGeneral.getNum1(), consultaGeneral.getStr1()));
            }
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        }
    }

    public void cargarUsuarios() throws SQLException {
        try {
            users.clear();
            setUsers(Utils.CiudadesUtils.cargarUsers());
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        }
    }

    /**
     * Método que Listara los viajes o tiquetes pendientes a entregar a los
     * empleados
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos de la tabla tbl_ciudades
     * @since incluido desde la version 1.0
     */
    public void saldoActual() throws SQLException {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc") != null) {
            setSelectUser(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc"));
        }
        setUsuarioNodum(null);
        setClaveNodum(null);
        setUsuarioTaquilla("");
        setDisable(true);
        if (!selectUser.equals("")) {
//            String[] porciones = selectUser.split(" ");
            saldos.clear();
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            l = (ArrayList) CrudObject.getSelectSql("entregaConvenio", 1, "" + selectUser + "");
            if (!l.isEmpty()) {
                if (l.get(0).getStr8().equals("A")) {
                    for (ConsultaGeneral obj : l) {
                        saldos.add(new TblViajesTiquetes(obj.getNum1(), obj.getNum2(), obj.getStr1(),
                                obj.getStr2(), obj.getStr3(),
                                obj.getStr4(), obj.getStr5(),
                                obj.getStr6(), obj.getStr7(),
                                obj.getNum3(), obj.getFloat1(),
                                obj.getNum5(), obj.getNum6(),
                                obj.getNum7(), obj.getNum8(), obj.getNum9(), obj.getNum10(), obj.getStr8()));
                    }
                    if (saldos.isEmpty()) {
                        setDisable2(true);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
                    } else {
                        setDisable2(false);
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso..!", "El convenio ha caducado \n1- Es posible que la fecha del convenio este vencida."
                            + "\n2- Es posible que el monto se haya agotado.\nPor favor comuniquese con la administración."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Ingrese Numero de documento"));
            saldos.clear();
        }
        setSelectUser("");
    }

    public void saldoActualAutorizados() throws SQLException {
        tiqs = "";
        tiquetesAutorizadosTolist.clear();
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc") != null) {
            setSelectUser(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc"));
        }
        setUsuarioNodum(null);
        setClaveNodum(null);
        setUsuarioTaquilla("");
        setDisable(true);
        if (!selectUser.equals("")) {
            TiquetesAutorizados.clear();
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            l = (ArrayList) CrudObject.getSelectSql("findtiqAutorizados", 1, "" + selectUser + "");
            if (!l.isEmpty()) {
                for (ConsultaGeneral obj : l) {
                    TiquetesAutorizados.add(new TiquetesAutorizados(obj.getNum1(), obj.getStr1(), obj.getStr2(), obj.getStr3(),
                            obj.getStr4(), obj.getStr5(), obj.getStr6(), obj.getFecha1(), obj.getStr7(), obj.getFecha2(),
                            obj.getStr8(), obj.getStr9(), obj.getStr10(), obj.getStr11(), obj.getStr12(), obj.getStr13(), obj.getStr14()));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Ingrese Numero de documento"));
            TiquetesAutorizados.clear();
        }
        setSelectUser("");
    }

    /**
     * Método que cargara los empleados para la funcion de autocompletar
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos de la tabla tbl_ciudades
     * @since incluido desde la version 1.0
     */
    private void listarAutocomplete() {
        try {
            LoginBean log = new LoginBean();
            listUsuariosAutoComplete.clear();
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            l = (ArrayList) CiudadesUtils.getSelectSql("autocompleteEmpl", 1, "" + log.getIdEmpresa());//quitar empresa en la consulta
            for (ConsultaGeneral obj : l) {
                listUsuariosAutoComplete.add(new TblEmpleados(obj.getStr1(), obj.getStr2()));
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        }

    }

    /**
     * Método que autocompleta a partir de las teclas tipeadas en la vista
     *
     * @param name
     * @return
     * @since incluido desde la version 1.0
     */
    public List<TblEmpleados> queryByName(String name) {
        List<TblEmpleados> queried = new ArrayList<>();
        this.listUsuariosAutoComplete.stream().forEach((empl) -> {
            if (empl.getNombre().startsWith(name)) {
                queried.add(empl);
            } else if (empl.getDocumento().startsWith(name)) {
                queried.add(empl);
            }
        });
        return queried;
    }

    /**
     * Método que agrega los viajes o tiquetes a entregar a elmpleado en el
     * array list
     *
     * @param tiquete
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void addTiqueteToList(TblViajesTiquetes tiquete) throws SQLException {
        if (tiquete.getCantidadAEntregar() > tiquete.getSaldo()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "La Cantidad de tiquetes a entregar no debe superar al saldo"));
            tiquete.setCantidadAEntregar(0);
            tiquete.setSelected(false);
            return;
        }

        if (tiquete.getCantidadAEntregar() == 0.1F || tiquete.getCantidadAEntregar() == 0.2F
                || tiquete.getCantidadAEntregar() == 0.3F || tiquete.getCantidadAEntregar() == 0.4F
                || tiquete.getCantidadAEntregar() == 0.6F || tiquete.getCantidadAEntregar() == 0.7F
                || tiquete.getCantidadAEntregar() == 0.8F || tiquete.getCantidadAEntregar() == 0.9F) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Los valores permitidos para entregar son: 0.5 y mayor o igual a 1"));
            tiquete.setCantidadAEntregar(0);
            tiquete.setSelected(false);
            return;
        }

        if (tiquete.getCantidadAEntregar() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Debes agregar la cantidad"));
            tiquete.setSelected(false);
            return;
        }

        setTiquetesCurrrent(tiquete);
        if (existEnArray(tiquetesToList, tiquete.getId_Viaje_tiquete())) {
//                    indiceDato(tiquetesToList, getTiquetesCurrrent().getTransaccion());
            eliminardelarray(tiquetesToList, tiquete.getId_Viaje_tiquete());
            getTiquetesCurrrent().setCantidadAEntregar(0);
            getTiquetesCurrrent().setSelected(false);
            setTempValorGlobal(0);
            setTempTipoCOntrato("");
        } else {
            tiquetesToList.add(tiquete);
            System.out.println(getTotalTrans());
            getTiquetesCurrrent().setSelected(true);
            setTempTipoCOntrato(CiudadesUtils.getValorGlobal(getTiquetesCurrrent().getIdEmpresa()));
        }
        System.out.println("cant " + tiquetesToList.size());

    }

    public void addTiqueteAutorizados(TiquetesAutorizados tiquete) throws SQLException {
        if (existEnArray3(tiquetesAutorizadosTolist, tiquete.getId_carga())) {
            eliminardelarray3(tiquetesAutorizadosTolist, tiquete.getId_carga());
        } else {
            tiquetesAutorizadosTolist.add(tiquete);
        }
    }

    /**
     * Método indica si el viaje ya esta en el array para entregar, si ya existe
     * se debe eliminar, sino se debe agregar
     *
     * @param l
     * @param id
     * @return
     * @since incluido desde la version 1.0
     */
    public boolean existEnArray(List<TblViajesTiquetes> l, int id) {
        boolean saber = false;
        Iterator<TblViajesTiquetes> lpt = l.iterator();
        while (lpt.hasNext()) {
            TblViajesTiquetes borrar = lpt.next();
            if (borrar.getId_Viaje_tiquete() == id) {
                saber = true;
                break;
            }
        }
        return saber;
    }

    public boolean existEnArray2(List<DetalleCm> l, int id) {
        boolean saber = false;
        Iterator<DetalleCm> lpt = l.iterator();
        while (lpt.hasNext()) {
            DetalleCm borrar = lpt.next();
            if (borrar.getId() == id) {
                saber = true;
                break;
            }
        }
        return saber;
    }

    public boolean existEnArray3(List<TiquetesAutorizados> l, int id) {
        boolean saber = false;
        Iterator<TiquetesAutorizados> lpt = l.iterator();
        while (lpt.hasNext()) {
            TiquetesAutorizados borrar = lpt.next();
            if (borrar.getId_carga() == id) {
                saber = true;
                break;
            }
        }
        return saber;
    }

    /**
     * Método no usado
     *
     * @param l
     * @param id
     * @return
     * @since incluido desde la version 1.0
     */
    public int indiceDato(List<TblViajesTiquetes> l, int id) {
        int j = 0;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getId_Viaje_tiquete() == id) {
                j = i;
                break;
            }
        }
        return j;
    }

    public int indiceDato2(List<DetalleCm> l, int id) {
        int j = 0;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getId() == id) {
                j = i;
                break;
            }
        }
        return j;
    }

    /**
     * Método que permite eliminar un viaje del array si este ya existe, para no
     * tener viajes repetidos
     *
     * @param l
     * @param id
     * @return
     * @since incluido desde la version 1.0
     */
    public boolean eliminardelarray(List<TblViajesTiquetes> l, int id) {
        boolean eliminado = false;
        int j = 0;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getId_Viaje_tiquete() == id) {
                l.remove(i);
                eliminado = true;
            }
        }
        return eliminado;
    }

    public boolean eliminardelarray2(List<DetalleCm> l, int id) {
        boolean eliminado = false;
        int j = 0;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getId() == id) {
                l.remove(i);
                eliminado = true;
            }
        }
        return eliminado;
    }

    public boolean eliminardelarray3(List<TiquetesAutorizados> l, int id) {
        boolean eliminado = false;
        int j = 0;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getId_carga() == id) {
                l.remove(i);
                eliminado = true;
            }
        }
        return eliminado;
    }

    /**
     * Método que permite entregar los viajes o tiquetes a cada empleado
     *
     * @return
     * @throws java.sql.SQLException
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     * @throws java.text.ParseException
     * @since incluido desde la version 1.0
     */
    public String createTransaccion() throws SQLException, InterruptedException, IOException, ParseException {
        if (tiquetesToList.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No has seleccionado registros para entregar"));
            return "ListaEntrega";
        }
        tiqs = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tiqs");
        if (tiqs.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo Número de tiquetes no debe estar vacio..!"));
            return "ListaEntrega";
        }
        if (getUsuarioNodum() == null && getClaveNodum() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El usuario y la clave no debe estar vacio..!"));
            return "ListaEntrega";
        } else if (!validarTquillaNodum()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No Tienes Permisos Para Entregar Convenios"));
            setUsuarioNodum("");
            setClaveNodum("");
            setUsuarioTaquilla("");
            return "ListaEntrega";
        }
        if (getTiquetesToList().size() > 0) {
            if (!getUsuarioNodum().equals("") && !getClaveNodum().equals("")) {
                LoginBean log = new LoginBean();
                Transaccion tr = new Transaccion();
                tr.getIdUsuario().setIdUsuario(Utils.CiudadesUtils.getIdUserByEmpresa(getTiquetesCurrrent().getEmpresa().getId_empresa()));
                tr.setCantTiquetes(0); // * (getTiquetesCurrrent().getIdaRegreso().equalsIgnoreCase("Si") ? 2 : 1)
                tr.setDescripcionTiquetes(getDescripcionTiquetes());
                tr.setTotal(getTotalTrans());
                tr.setFechaMod(new Date());
                tr.setUsuarioNodum(getUsuarioNodum());
                tr.setClaveNodum(getClaveNodum());
                tr.setUsuarioTaquilla(getUsuarioTaquilla());
                tr.setTaquilla(log.getNomUserLog());
                tr.setTempTipoContrato(getTempTipoCOntrato());
                tr.setTiqtes_entregados(tiqs);
                setA((int) CrudObject.create(tr, getTiquetesToList()));
                if (getA() >= 1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Transaccion realizada con exito"));
//                    tiquetesToList.clear();
                    saldos.clear();
                    setUsuarioNodum(null);
                    setClaveNodum(null);
                    setUsuarioTaquilla("");
                    setDescripcionTiquetes("");
                    setDisable(true);
                    setDisable2(true);
                    tr = null;
                    log = null;
                    tiqs = "";
                    totalTrans = 0;
                    cantTiquetesTrans = 0;
                    setTiquetesCurrrent(null);
                    getTiquetesToList().clear();
                    setTiquetesToList();
                    return "ListaEntrega";
                } else if (getA() < 0) {
                    saldos.clear();
                    setUsuarioNodum(null);
                    setClaveNodum(null);
                    setUsuarioTaquilla("");
                    setDescripcionTiquetes("");
                    setDisable(true);
                    setDisable2(true);
                    tr = null;
                    log = null;
                    tiqs = "";
                    totalTrans = 0;
                    cantTiquetesTrans = 0;
                    setTiquetesCurrrent(null);
                    getTiquetesToList().clear();
                    setTiquetesToList();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informacion", "Error no se puede crear una transaccion con mas de un documento, por favor intente de nuevo"));
                    return "ListaEntrega";
                } else {
                    saldos.clear();
                    setUsuarioNodum(null);
                    setClaveNodum(null);
                    setUsuarioTaquilla("");
                    setDescripcionTiquetes("");
                    setDisable(true);
                    setDisable2(true);
                    tr = null;
                    log = null;
                    tiqs = "";
                    totalTrans = 0;
                    cantTiquetesTrans = 0;
                    setTiquetesCurrrent(null);
                    getTiquetesToList().clear();
                    setTiquetesToList();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informacion", "Error al crear la transaccion"));
                    return "ListaEntrega";
                }
            } else {
                setTiquetesToList();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El usuario y la clave no debe estar vacio..!"));
                return "ListaEntrega";
            }
        } else {
            setTiquetesToList();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "No has seleccionado nada para entregar..!"));
            return "ListaEntrega";
        }
    }

    public String entregarAutorizados() throws SQLException, InterruptedException, IOException, ParseException {
        if (tiquetesAutorizadosTolist.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No has seleccionado registros para entregar"));
            return "ListAutorizados";
        }
        if (getUsuarioNodum() == null && getClaveNodum() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El usuario y la clave no debe estar vacio..!"));
            return "ListAutorizados";
        } else if (!validarTquillaNodum()) {
            setUsuarioNodum("");
            setClaveNodum("");
            setUsuarioTaquilla("");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No Tienes Permisos Para Entregar Convenios"));
            return "ListAutorizados";
        }
        if (getTiquetesAutorizadosTolist().size() > 0) {
            if (!getUsuarioNodum().equals("") && !getClaveNodum().equals("")) {
                LoginBean log = new LoginBean();
                StringBuilder Object = new StringBuilder();
                StringBuilder sb = new StringBuilder();
                tiquetesAutorizadosTolist.forEach((next) -> {
                    sb.append(next.getId_carga()).append(",");
                });
                setAutorizados(sb.toString());
                Object.append(format.format(new Date())).append(",").append(log.getNomUserLog()).append(",").append(getUsuarioTaquilla());
                setA((int) CrudObject.createAutorizacion(Object.toString(), getTiquetesAutorizadosTolist()));
                if (getA() >= 1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Transaccion realizada con exito"));
                    tiquetesAutorizadosTolist.clear();
                    TiquetesAutorizados.clear();
                    setUsuarioNodum("");
                    setClaveNodum("");
                    setUsuarioTaquilla("");
                    log = null;
                    Object = null;
                    return "ListAutorizados";
                } else {
                    return "ListAutorizados";
                }
            } else {
                tiquetesAutorizadosTolist.clear();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El usuario y la clave no debe estar vacio..!"));
                return "ListAutorizados";
            }
        } else {
            tiquetesAutorizadosTolist.clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "No has seleccionado nada para entregar..!"));
            return "ListAutorizados";
        }
    }


    /*
    * Método que lista los estudiantes por número de documento
    * o por nombre
    * vista @estudiante - filtra por documento
    * vista @estudiantef -  filtra por nombre
     */
    public void listarEstudiantes() throws SQLException {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc") != null) {
            setSelectUser(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc"));
        }
        LoginBean log = new LoginBean();
        listEstudiantes.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        if (selectUser.equals("")) {
            l = (ArrayList) CrudObject.getSelectSql("estudiante", 1, "");
        } else {
            l = (ArrayList) CrudObject.getSelectSql("estudiantef", 1, "" + selectUser + "");
        }
        for (ConsultaGeneral obj : l) {
            listEstudiantes.add(new Estudiantes(obj.getNum1(), obj.getStr1(),
                    obj.getStr2(), obj.getStr3(), obj.getStr4(), obj.getNum2(), obj.getStr5()));
        }

    }

    /**
     * Método que permite guardar el número de tiquetes entregados al estudiante
     *
     * @throws java.sql.SQLException
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     * @throws java.text.ParseException
     */
    public String createConvenioEstudiante() throws SQLException, InterruptedException, IOException, ParseException {

        tiqs = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tiqs");
        selectUser = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc");

        System.out.println("tiqs + selectUser" + tiqs + " " + selectUser);

        if (validarTiquete(tiqs)) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El número de tiquete ya fue guardado"));
            return "ConvenioEstudiante";
        }

        if (tiqs.equals("") || selectUser.equals("")) {
            System.out.println("tiqs1 + selectUser1 " + tiqs + " " + selectUser);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo Número de tiquetes y usuario no debe estar vacio..!"));
            return "ConvenioEstudiante";
        }

        if (getUsuarioNodum() == null && getClaveNodum() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El usuario y la clave no debe estar vacio..!"));
            return "ConvenioEstudiante";
        } else if (!validarTquillaNodum()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No Tienes Permisos Para Entregar Convenios"));
            setUsuarioNodum("");
            setClaveNodum("");
            setUsuarioTaquilla("");
            return "ConvenioEstudiante";
        } else {

            LoginBean taquilla = new LoginBean();
            Tiquete_Estudiante tiquete = new Tiquete_Estudiante();
            tiquete.setDoc_estudiante(selectUser);
            tiquete.setNumero_tiquete(tiqs);
            tiquete.setFecha_entrega(new Date());
            tiquete.setTaquilla_entrega(taquilla.getNomUserLog());
            tiquete.setUsuario_taquilla(getUsuarioTaquilla());
            long resultado = CrudObject.create(tiquete);
            if (resultado > 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Información guardada"));
                setUsuarioNodum("");
                setClaveNodum("");
                setUsuarioTaquilla("");
                selectUser = "";
                tiqs = "";
                taquilla = null;
                tiquete = null;
                listEstudiantes.clear();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al guardar la información"));
                setUsuarioNodum("");
                setClaveNodum("");
                setUsuarioTaquilla("");
                selectUser = "";
                tiqs = "";
                taquilla = null;
                tiquete = null;
                listEstudiantes.clear();
            }

            return "ConvenioEstudiante";
        }

    }

    public void exportarListadoCms() throws IOException, JRException, SQLException {
//        if (FacturaVenta.isEmpty()) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para Exportar"));
//        } else {
//            Map parametros = new HashMap();
//            parametros.put("idEmpresa", getIdEmpresa());
        String report = "/Reports/listCmsExportNoParameters.jasper";
//            parametros.put("fechaIni", format2.format(selecFechaIni) + " 00:00:00");
//            parametros.put("fechaFin", format2.format(selecFechaFin) + " 23:59:59");
//            if (!selectUser.equals("")) {
//                parametros.put("doc", getSelectUser());
//                report = "/Reports/faturaventaAdmonDoc.jasper";
//            }
//            System.out.println("datos report = " + format2.format(selecFechaIni) + "  " + format2.format(selecFechaFin) + "  " + getIdEmpresa() + "  " + getSelectUser());
        ConexionPool pool = new ConexionPool();
        pool.con = pool.dataSource.getConnection();
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(report));
        JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), null, pool.con);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=Listado_Relacion.xls");
        try (ServletOutputStream stream = response.getOutputStream()) {
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporter.exportReport();
            stream.flush();
        }
        FacesContext.getCurrentInstance().responseComplete();
//        }
    }

    /*
    * Método que valida el número de tiquete del sistema
    * @param numtiquet
     */
    public boolean validarTiquete(String numtiquet) throws SQLException {
        boolean r = CiudadesUtils.validarTiqueteExist(numtiquet);
        return r;
    }

    /**
     * Método que permite imprimir el recibo de caja cuando se entregan los
     * viajes o tiquetes
     *
     * @param evt
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void imprimir(ActionEvent evt) throws IOException, JRException, SQLException {
        if (getA() > 0) {
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/reciboDeCaja2.jasper"));
//            String subreport = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/reciboDeCajaDetalle.jasper");
//            Map parametros = new HashMap();
            ReciboDeCajaDataSource rdatasource = new ReciboDeCajaDataSource();
            rdatasource.setListaRecibo(CiudadesUtils.reciboDeCaja(getA()));
            byte[] jp = JasperRunManager.runReportToPdf(jasper.getPath(), null, rdatasource);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(jp.length);
            try (ServletOutputStream outStream = response.getOutputStream()) {
                outStream.write(jp, 0, jp.length);
                outStream.flush();
                outStream.close();
            }
            FacesContext.getCurrentInstance().responseComplete();
            setA(0);
            saldos.clear();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso..!", "No se han entregado tiquetes"));
        }

    }

    public void imprimirReciboAutorizados(ActionEvent evt) throws IOException, JRException, SQLException {
        if (getA() > 0) {
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/reciboAutorizacion.jasper"));

//            Map parametros = new HashMap();
            AutorizadosDataSource rdatasource = new AutorizadosDataSource();
            rdatasource.setListaRecibo(CiudadesUtils.reciboDeCajaAutorizados(getAutorizados()));
            byte[] jp = JasperRunManager.runReportToPdf(jasper.getPath(), null, rdatasource);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(jp.length);
            try (ServletOutputStream outStream = response.getOutputStream()) {
                outStream.write(jp, 0, jp.length);
                outStream.flush();
                outStream.close();
            }
            FacesContext.getCurrentInstance().responseComplete();
            setA(0);
            tiquetesAutorizadosTolist.clear();
            setAutorizados("");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso..!", "No se han entregado tiquetes"));
        }

    }

    /**
     * Método que permite re imprimir el recibo de caja cuando se entregan los
     * viajes o tiquetes si se cierra la ventana de impresion
     *
     * @param evt
     * @param id_trans
     * @param opc
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void imprimir2(ActionEvent evt, String id_trans, int opc) throws IOException, JRException, SQLException {
        File jasper;
        Map parametros = new HashMap();
        ReciboDeCajaDataSource rdatasource = new ReciboDeCajaDataSource();
        if (opc == 1) {
            jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/reciboDeCaja2.jasper"));
            rdatasource.setListaRecibo(CiudadesUtils.reciboDeCaja(Integer.parseInt(id_trans)));
        } else {
            jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/reportTiquete2.jasper"));
            parametros.put("idTransaccion", id_trans);
        }
        ConexionPool pool = new ConexionPool();
        pool.con = pool.dataSource.getConnection();
        byte[] jp;
        if (opc == 1) {
            jp = JasperRunManager.runReportToPdf(jasper.getPath(), null, rdatasource);
        } else {
            jp = JasperRunManager.runReportToPdf(jasper.getPath(), parametros, pool.con);
        }
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setContentType("application/pdf");
        response.setContentLength(jp.length);
        try (ServletOutputStream outStream = response.getOutputStream()) {
            outStream.write(jp, 0, jp.length);
            outStream.flush();
            outStream.close();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

//    public void crearCM() throws SQLException, ParseException {
//        if (cmgenerado.getElaborado_por().equals("") || cmgenerado.getElaborado_por() == null) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo elaborado por es obligatorio"));
//            return;
//        }
//        if (cmgenerado.getAutorizado_por().equals("") || cmgenerado.getAutorizado_por() == null) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo autorizado por es obligatorio"));
//            return;
//        }
//        if (cmgenerado.getPlanilla().equals("") || cmgenerado.getPlanilla() == null) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo No. Planilla es obligatorio"));
//            return;
//        }
//        if (detalleCmList.size() > 0) {
//            StringBuilder sb = new StringBuilder();
//            LoginBean log = new LoginBean();
//
//            cmgenerado.setFecha_creacion(new Date());
//            if (selecFecha == null) {
//                cmgenerado.setFecha_cm(new Date());
//            } else {
//                cmgenerado.setFecha_cm(selecFecha);
//            }
//            String[] taquilla = log.getNomUserLog().split(" ");
//            String idAgencia = "";
//            cmgenerado.setOficina(taquilla[1]);
//            switch (taquilla[1]) {
//                case "ARMENIA":
//                    idAgencia = "AR";
//                    break;
//                case "BUGA":
//                    idAgencia = "BU";
//                    break;
//                case "BUENAVENTURA":
//                    idAgencia = "BV";
//                    break;
//                case "CALI":
//                    idAgencia = "CL";
//                    break;
//                case "BOGOTA-NORTE":
//                    idAgencia = "DCN";
//                    break;
//                case "BOGOTA-SUR":
//                    idAgencia = "DCS";
//                    break;
//                case "IBAGUE":
//                    idAgencia = "IB";
//                    break;
//                case "CARTAGO":
//                    idAgencia = "KG";
//                    break;
//                case "MEDELLIN":
//                    idAgencia = "MD";
//                    break;
//                case "MANIZALES":
//                    idAgencia = "MZ";
//                    break;
//                case "PALMIRA-ESTACION":
//                    idAgencia = "PLE";
//                    break;
//                case "PALMIRA-VERSALLES":
//                    idAgencia = "PLV";
//                    break;
//                case "PEREIRA":
//                    idAgencia = "PR";
//                    break;
//                case "POPAYAN":
//                    idAgencia = "PY";
//                    break;
//                case "SEVILLA":
//                    idAgencia = "SV";
//                    break;
//                case "TULUA":
//                    idAgencia = "TU";
//                    break;
//            }
//            sb.append(idAgencia).append(Utils.CiudadesUtils.getConsecutivoAgencia(idAgencia, "consecutivo_cms"));
//            cmgenerado.setConsecutivo(sb.toString());
//            cmgenerado.setListDetalleCm(detalleCmList);
//            setConseucutivo(sb.toString());
//            long r = CrudObject.create(cmgenerado);
//            if (r >= 1) {
//                setPrintCm(false);
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cm Generado con exito..!"));
//            } else {
//                setPrintCm(true);
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al generar el Cm..!"));
//            }
//            detalleCmList.clear();
//            cmgenerado = null;
//            listarCms("cmGen");
//        } else {
//            setPrintCm(true);
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Aún no has ingresado un concepto..!"));
//        }
//
//    }
    public void crearCM2() throws SQLException, ParseException {
        getCmgenerado();
//        if (cmgenerado.getElaborado_por().equals("") || cmgenerado.getElaborado_por() == null) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo elaborado por es obligatorio"));
//            return;
//        }
//        if (cmgenerado.getAutorizado_por().equals("") || cmgenerado.getAutorizado_por() == null) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo autorizado por es obligatorio"));
//            return;
//        }
//        if (cmgenerado.getPlanilla().equals("") || cmgenerado.getPlanilla() == null) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo No. Planilla es obligatorio"));
//            return;
//        }
        if (empresaAnterior.size() > 1) {
            empresaAnterior.clear();
            currentCmList2.clear();
            detalleCmList2.clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Solo puedes asociar un CM a la vez..!"));
            return;
        }       
        
        if (howEmpresas(emprsa, (ArrayList<DetalleCm>) currentCmList2) > 1) {
            empresaAnterior.clear();
            currentCmList2.clear();
            detalleCmList2.clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Solo puedes asociar una Empresa a la vez..!"));
            return;
        }        

        if (Utils.CiudadesUtils.verificarCmExist(currentCmList2.get(0).getCm_asoc())) {
            empresaAnterior.clear();
            currentCmList2.clear();
            detalleCmList2.clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El número de CM ya fue relacionado en otra transacción..!"));
            return;
        }

        if (currentCmList2.size() > 0) {
            StringBuilder sb = new StringBuilder();
            LoginBean log = new LoginBean();
            String[] taquilla = log.getNomUserLog().split(" ");
            String idAgencia = "";
            switch (taquilla[1]) {
                case "ARMENIA":
                    idAgencia = "AR";
                    break;
                case "BUGA":
                    idAgencia = "BU";
                    break;
                case "BUENAVENTURA":
                    idAgencia = "BV";
                    break;
                case "CALI":
                    idAgencia = "CL";
                    break;
                case "BOGOTA-NORTE":
                    idAgencia = "DCN";
                    break;
                case "BOGOTA-SUR":
                    idAgencia = "DCS";
                    break;
                case "IBAGUE":
                    idAgencia = "IB";
                    break;
                case "CARTAGO":
                    idAgencia = "KG";
                    break;
                case "MEDELLIN":
                    idAgencia = "MD";
                    break;
                case "MANIZALES":
                    idAgencia = "MZ";
                    break;
                case "PALMIRA-ESTACION":
                    idAgencia = "PLE";
                    break;
                case "PALMIRA-VERSALLES":
                    idAgencia = "PLV";
                    break;
                case "PEREIRA":
                    idAgencia = "PR";
                    break;
                case "POPAYAN":
                    idAgencia = "PY";
                    break;
                case "SEVILLA":
                    idAgencia = "SV";
                    break;
                case "TULUA":
                    idAgencia = "TU";
                    break;
                case "SOACHA":
                    idAgencia = "SO";
                    break;
            }
            sb.append(idAgencia).append(Utils.CiudadesUtils.getConsecutivoAgencia(idAgencia, "consecutivo_cms"));
            cmgenerado.setId_trans(sb.toString());
            cmgenerado.setFecha_creacion(new Date());
            cmgenerado.setAgencia(log.getNomUserLog());
            cmgenerado.setListDetalleCm(currentCmList2);
            currentCmList2.forEach((next) -> {
                String str = next.getIdtrans() + "," + next.getTabla();
                if (!trans.contains(str)) {
                    trans.add(str);
                }
            });
            cmgenerado.setTransUpdate(trans);
            setConseucutivo(sb.toString());
            long r = CrudObject.create(cmgenerado);
            if (r >= 1) {
                setPrintCm2(false);
                detalleCmList2.clear();
                currentCmList2.clear();
                trans.clear();
                cmgenerado = null;
                listarCms("cmGen");
                selectEmpresa = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Relacion Generada con exito..!"));
            } else {
                setPrintCm2(true);
                detalleCmList2.clear();
                currentCmList2.clear();
                trans.clear();
                cmgenerado = null;
                selectEmpresa = "";
                listarCms("cmGen");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al generar el Relacion!"));
            }
        } else {
            setPrintCm2(true);
            detalleCmList2.clear();
            currentCmList2.clear();
            trans.clear();
            cmgenerado = null;
            selectEmpresa = "";
            listarCms("cmGen");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Aún no has ingresado un recibos a relacionar!"));
        }

    }

    private static int howEmpresas(String empresa, ArrayList<DetalleCm> ar) {
        StringBuilder sb = new StringBuilder();
        sb.append(empresa);
        ar.stream().filter((detalleCm) -> (!sb.toString().contains(detalleCm.getConcepto()))).forEachOrdered((detalleCm) -> {
            sb.append(detalleCm.getConcepto()).append(",");
        });
        String[] split = sb.toString().substring(0, sb.toString().length() - 1).split(",");
        return split.length;
    }

    //GenerrarCM   
    public void generarCm(ActionEvent evt, String consecutivo, boolean update) throws IOException, JRException, SQLException, ParseException {
        System.out.println("consecutivo " + consecutivo);
        if (!getConseucutivo().equals("") || !consecutivo.equals("")) {
            LoginBean log = new LoginBean();
            File jasper = null;
            String logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/logo2.png");
            System.out.println("logo  = " + logo);
            Map parametros = new HashMap();
            ConexionPool pool = new ConexionPool();
            pool.con = pool.dataSource.getConnection();
            jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/CmGenerado.jasper"));
            String subreport = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/DetalleCm.jasper");
            parametros.put("SUBREPORT_DIR", subreport);
            parametros.put("con", pool.con);
            parametros.put("imagen", logo);
            parametros.put("impresion", format.format(new Date()));
            if (consecutivo.equals("")) {
                parametros.put("idTransaccion", getConseucutivo());
            } else {
                parametros.put("idTransaccion", consecutivo);
                if (update) {
                    Utils.CiudadesUtils.editCm(consecutivo, 0);
                }
            }
            byte[] jp = JasperRunManager.runReportToPdf(jasper.getPath(), parametros, pool.con);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(jp.length);
            try (ServletOutputStream outStream = response.getOutputStream()) {
                outStream.write(jp, 0, jp.length);
                outStream.flush();
                outStream.close();
            }
            FacesContext.getCurrentInstance().responseComplete();
            selectedEmpresas = null;
            setConseucutivo("");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No has generado un CM"));
        }
    }

    public void enabledPrintCm(String consecutivo, String office) throws SQLException {
        Utils.CiudadesUtils.editCm(consecutivo, 1);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha habilitado la reimpresion para la oficina " + office + ""));
    }

    /**
     * Método que permite re imprimir el comprobante de entrega de contravias
     *
     * @param evt
     * @param id_trans
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @since incluido desde la version 1.0
     */
    public void imprimirCompContravia(ActionEvent evt, String id_trans) throws IOException, JRException, SQLException {
        File jasper;
        Map parametros = new HashMap();
        jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/compEntregaContravia.jasper"));
        parametros.put("transaccion", id_trans);
        ConexionPool pool = new ConexionPool();
        pool.con = pool.dataSource.getConnection();
        byte[] jp = JasperRunManager.runReportToPdf(jasper.getPath(), parametros, pool.con);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setContentType("application/pdf");
        response.setContentLength(jp.length);
        try (ServletOutputStream outStream = response.getOutputStream()) {
            outStream.write(jp, 0, jp.length);
            outStream.flush();
            outStream.close();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * Método que permite re imprimir el recibo de caja de las contravias
     * generadas desde el rol de administracion
     *
     * @param evt
     * @param id_trans
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void reimprimirContravias(ActionEvent evt, String id_trans) throws IOException, JRException, SQLException {
        File jasper;
        Map parametros = new HashMap();
        jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/reimpresionContravia.jasper"));
        parametros.put("transaccion", id_trans);
        ConexionPool pool = new ConexionPool();
        pool.con = pool.dataSource.getConnection();
        byte[] jp = JasperRunManager.runReportToPdf(jasper.getPath(), parametros, pool.con);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setContentType("application/pdf");
        response.setContentLength(jp.length);
        try (ServletOutputStream outStream = response.getOutputStream()) {
            outStream.write(jp, 0, jp.length);
            outStream.flush();
            outStream.close();
        }
        FacesContext.getCurrentInstance().responseComplete();

    }

    /**
     * Método valida si el ususario es correcto para entregar los convenios
     *
     * @return
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public boolean validarTquillaNodum() throws SQLException {
        boolean validar = false;
        String valor = CiudadesUtils.getValidateNodum(getUsuarioNodum(), getClaveNodum());
        if (!valor.equals("")) {
            if (getUsuarioNodum() != null && getClaveNodum() != null) {
                setUsuarioTaquilla(valor.trim());
                validar = true;
            }
        }
        return validar;
    }

//      public boolean validarTquillaNodumEntrega() throws SQLException {        
//        boolean validar = false;
//        String valor = CiudadesUtils.getValidateNodum(usuarioNodum, claveNodum);
//        if (!valor.equals("")) {
//            if (usuarioNodum != null && claveNodum != null) {
//                getTiquetesCurrrent().setUsuarioTaquillaEntrega(valor.trim());
//                getTiquetesCurrrent().setUserNodumEntrega(usuarioNodum);
//                validar = true;
//            }
//        }
//        return validar;
//    }
    //agregar concepto cm
    public void addConceptCm() throws SQLException {
        getDetalleCm();
        detalleCm.setPagado_a(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagadoa"));
        detalleCm.setCc_nit(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ccnit"));
        detalleCm.setConcepto(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("concepto"));
        detalleCm.setValor(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("valor")));
        if (detalleCm.getNo_bus() == null) {
            detalleCm.setNo_bus("NA");
        }
        if (detalleCm.getPagado_a().equals("") || detalleCm.getPagado_a() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo Pagado A es obligatorio"));
            return;
        }
        if (detalleCm.getCc_nit().equals("") || detalleCm.getCc_nit() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo Cedula o Nit es obligatorio"));
            return;
        }
        if (detalleCm.getConcepto().equals("") || detalleCm.getConcepto() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo Concepto es obligatorio"));
            return;
        }
        if (detalleCm.getValor() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo Valor es obligatorio"));
            return;
        }
        detalleCmList.add(detalleCm);
        setDetalleCm(null);
    }

    //agregar concepto cm
    public void addConceptCmManual() throws SQLException, ParseException {
        getDetalleCm();
        detalleCm.setPagado_a(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pp"));
        detalleCm.setCc_nit(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cc"));
        detalleCm.setConcepto(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("conc"));
        try {
            Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("val"));
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo Valor debe ser Numerico"));
            return;
        }
        detalleCm.setValor(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("val")));
        if (detalleCm.getNo_bus() == null) {
            detalleCm.setNo_bus("--");
        }
        if (detalleCm.getPagado_a().equals("") || detalleCm.getPagado_a() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo Pagado A es obligatorio"));
            return;
        }
        if (detalleCm.getCc_nit().equals("") || detalleCm.getCc_nit() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo Cedula o Nit es obligatorio"));
            return;
        }
        if (detalleCm.getConcepto().equals("") || detalleCm.getConcepto() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo Concepto es obligatorio"));
            return;
        }
        if (detalleCm.getValor() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El campo Valor es obligatorio"));
            return;
        }
        detalleCm.setTabla("Manual");
        detalleCmList2.add(detalleCm);
        setDetalleCm(null);
    }
//
//    //quitar 
//    public void quitarConceptCm(DetalleCm d, int opc) throws SQLException {
//        if (opc == 1) {
//            detalleCmList2.remove(d);
//        } else {
//            detalleCmList.remove(d);
//        }
//
//    }

    /**
     * Método que lista las impreisones diarias realizadas
     *
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void impresionesRealizadas() throws SQLException {
        if (selecFechaIni != null && selecFechaFin != null) {
            if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc") != null) {
                setSelectUser(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc"));
            }
            impresionesDia.clear();
            LoginBean log = new LoginBean();
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            if (getSelectUser().equals("")) {
                l = (ArrayList) CrudObject.getSelectSql("transEntregadas", 1, "" + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59");//+ log.getNomUserLog() +
            } else {
                l = (ArrayList) CrudObject.getSelectSql("transEntregadas2", 1, "" + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59" + "," + getSelectUser());
                setSelectUser("");
            }
            for (ConsultaGeneral obj : l) {
                impresionesDia.add(new Transaccion(obj.getStr1(), obj.getNum1(), obj.getStr2(),
                        obj.getStr3(), obj.getStr4(), obj.getFecha1(), obj.getStr5(), obj.getNum2()));
            }
            if (impresionesDia.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Ingrese un rango de fechas"));
        }
    }

    public boolean validateIsnroTrans(String id_trans) {
//        Comprobar si el String cadena no empieza por un dígito
        LoginBean log = new LoginBean();
        boolean r = false;
        Pattern pat = Pattern.compile("^[^\\d].*");
        Matcher mat = pat.matcher(id_trans);
        if (mat.matches() && log.getIdUserLog() == 70) {
            r = true;
        }
        return r;
    }

    public void confirmAnularTrans(String id_trans) throws IOException {
        setIdTransAnular(id_trans);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Taquilla/ConfirmDeleteTrans.xhtml");
    }

    public void anularTransaccion() throws SQLException, InterruptedException, IOException {
        LoginBean log = new LoginBean();
        log.getDocumentoUserLog();
        boolean result = Utils.CiudadesUtils.anularTransaccion(getIdTransAnular(), log.getDocumentoUserLog());
        if (result) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Taquilla/ImpresionesRealizadas.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo anular la transaccion..!"));
        }
        setIdTransAnular(null);
    }

    public void cancelAnularTrans() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Taquilla/ImpresionesRealizadas.xhtml");
        setIdTransAnular(null);
    }

    public void generarCmConvenios() throws SQLException {
        empresaAnterior.clear();
        currentCmList2.clear();
        if (selecFechaIni != null && selecFechaFin != null) {
            detalleCmList2.clear();
            trans.clear();
            LoginBean log = new LoginBean();
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            String[] parts = log.getNomUserLog().split(" ");
            String empresa = selectEmpresa;
            System.out.println("empresa selec = " + selectEmpresa);
            if (selectEmpresa != null && !selectEmpresa.equals("")) {
                //            int contain = 0;
//            String list = "";
//            if (selectedEmpresas == null) {
//                contain = 1;
//            } else {
//                for (String selectedEmpresa : selectedEmpresas) {
//                    if (selectedEmpresa.equals("Todas")) {
//                        contain += 1;
//                    } else {
//                        list += selectedEmpresa + "*";
//                    }
//                }
//            }
////            System.out.println(list.length());
////            System.out.println(list.substring(0, list.length() - 1));
////            System.out.println("list " + list);
//            if (contain > 0) {
//                System.out.println("23");
//                l = (ArrayList) CrudObject.getSelectSql("cmgenConvenios", 1, "" + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59" + "," + parts[1] + "");
//            } else {
//                System.out.println("4");
                l = (ArrayList) CrudObject.getSelectSql("cmgenConvenios2", 1, "" + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59" + "," + parts[1] + "," + empresa + "");
//            }
                for (ConsultaGeneral obj : l) {
                    detalleCmList2.add(new DetalleCm(0,
                            obj.getStr4(),
                            obj.getStr1(),
                            obj.getStr5(),
                            obj.getStr3(),
                            obj.getNum2(),
                            obj.getNum3(),
                            obj.getStr6()));
                }
                if (detalleCmList2.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Seleccione una empresa"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Ingrese un rango de fechas"));
        }
    }

    public void editValue(DetalleCm d) throws SQLException {
        System.out.println("d.getIdtrans() " + d.getIdtrans());
        if (d.getIdtrans() > 0 && d.getValor() > 0) {
            CiudadesUtils.editValTrans(d.getIdtrans(), d.getValor());
        }
    }

    public void editValue2(DetalleCm d) throws SQLException {
        System.out.println("d.getIdtrans() " + d.getIdtrans());
        if (!d.getConcepto().equals("")) {
            CiudadesUtils.editEmpTrans(d.getIdtrans(), d.getConcepto());
        }
    }

    public void adddetalleasoccm(DetalleCm d) {
        if (!empresaAnterior.contains(d.getCm_asoc())) {
            if (!d.getCm_asoc().equals("")) {
                empresaAnterior.add(d.getCm_asoc());
            }
        }
        if (!currentCmList2.contains(d)) {
            if (!d.getCm_asoc().equals("")) {
                currentCmList2.add(d);
            }
        } else {
            currentCmList2.remove(d);
            if (!d.getCm_asoc().equals("")) {
                currentCmList2.add(d);
            }
        }

    }

    public void setFacuraCMS(CmGenerado cms) throws SQLException {
        Utils.CiudadesUtils.updateNumeroFacturaCms(cms);
    }

    public void chekRecibido(CmGenerado cms, int opc) throws SQLException {
        Utils.CiudadesUtils.chekRecibido(cms, opc);
    }

    /**
     * Método que lista las contravias diarias realizadas para reimprimir el
     * comprobante de entrega de tiquetes, recibe un rango de fechas
     *
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void reimprimirComprobante() throws SQLException {
        if (selecFechaIni != null && selecFechaFin != null) {
            LoginBean log = new LoginBean();
            String[] parts = log.getDocumentoUserLog().split("-");
            String[] parts2 = parts[0].split(" ");
            String filtroOrigen = "";
            if (parts.length > 1) {
                filtroOrigen = parts[0];
            } else if (parts2.length > 1) {
                filtroOrigen = parts2[0];
            } else {
                filtroOrigen = log.getDocumentoUserLog();
            }

            impresionesCtra.clear();
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            if (!filtroOrigen.equals("Mercadeo")) {
                l = (ArrayList) CrudObject.getSelectSql("reimprimirContrav", 1, "" + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59" + "," + filtroOrigen + "");
            } else {
                l = (ArrayList) CrudObject.getSelectSql("reimprimirContravM", 1, "" + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59" + "");
            }

            for (ConsultaGeneral obj : l) {//TblRegistroContravias
                impresionesCtra.add(new TblRegistroContravias(obj.getStr1(),
                        obj.getStr2(),
                        obj.getStr3(),
                        obj.getStr4(),
                        obj.getStr5(),
                        obj.getStr6(),
                        obj.getStr7(),
                        obj.getStr8(),
                        obj.getStr9(),
                        obj.getNum1(),
                        obj.getNum2(),
                        obj.getNum3(),
                        obj.getStr10(),
                        obj.getStr11(),
                        obj.getStr12(),
                        obj.getStr13(),
                        obj.getStr14(),
                        obj.getFecha1(),
                        obj.getFecha2(), obj.getStr15(), obj.getNum4()));
            }
            if (impresionesCtra.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Seleccione un rango de fechas"));
        }

    }

    /**
     * Método que lista las contravias diarias realizadas para exportar a
     * archivo .xls compatibilidad 97-2003 .Este metodo es para control de los
     * encargados de valores
     *
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void resumenContraviasDiarias() throws SQLException {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectUser") != null) {
            setSelectUser(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectUser"));
        }
        if (selecFechaIni != null && selecFechaFin != null) {
            LoginBean log = new LoginBean();
            impresionesCtra.clear();
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
//            String[] parts = log.getDocumentoUserLog().split("-");
            String filtroOrigen = log.getNomUserLog();
//            if (parts.length > 1) {
//                filtroOrigen = parts[0];
//            } else {
//                filtroOrigen = log.getDocumentoUserLog();
//            }
            if (selectUser.equals("")) {
                l = (ArrayList) CrudObject.getSelectSql("resumenContrav", 1, "" + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59" + "," + filtroOrigen + "");
            } else {
                l = (ArrayList) CrudObject.getSelectSql("resumenContravUser", 1, "" + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59" + "," + filtroOrigen + "," + selectUser + "");
            }
            for (ConsultaGeneral obj : l) {//TblRegistroContravias
                impresionesCtra.add(new TblRegistroContravias(obj.getStr1(),
                        obj.getStr2(),
                        obj.getStr3(),
                        obj.getStr4(),
                        obj.getStr5(),
                        obj.getStr6(),
                        obj.getStr7(),
                        obj.getStr8(),
                        obj.getStr9(),
                        obj.getNum1(),
                        obj.getNum2(),
                        obj.getNum3(),
                        obj.getStr10(),
                        obj.getStr11(),
                        obj.getStr12(),
                        obj.getStr13(),
                        obj.getStr14(),
                        obj.getFecha1(),
                        obj.getFecha2(), obj.getStr15(), obj.getNum4()));
            }
            if (impresionesCtra.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Seleccione un rango de fechas"));
        }
    }

    /**
     * Método que lista las contravias diarias realizadas para renderizarlas en
     * la vista
     *
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void impresionesRealizadasContravia() throws SQLException {
        if (selecFechaIni != null && selecFechaFin != null) {
            impresionesCtra.clear();
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            l = (ArrayList) CrudObject.getSelectSql("contraviasImpr", 1, "" + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59");
            for (ConsultaGeneral obj : l) {//TblRegistroContravias
                impresionesCtra.add(new TblRegistroContravias(obj.getStr1(),
                        obj.getStr2(),
                        obj.getStr3(),
                        obj.getStr4(),
                        obj.getStr5(),
                        obj.getStr6(),
                        obj.getStr7(),
                        obj.getStr8(),
                        obj.getStr9(),
                        obj.getNum1(),
                        obj.getNum2(),
                        obj.getNum3(),
                        obj.getStr10(),
                        obj.getStr11(),
                        obj.getStr12(),
                        obj.getStr13(),
                        obj.getStr14(),
                        obj.getFecha1(),
                        obj.getFecha2(), obj.getStr15(), obj.getNum4()));
            }
            if (impresionesCtra.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Seleccione un rango de fechas"));
        }
    }

    /**
     * Método que permite exportar las contravias realizadas a archivo .xls
     * compatibilidad office 97-2003
     *
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void exportarXLS() throws IOException, JRException, SQLException {
        if (selecFechaIni != null && selecFechaFin != null) {
            LoginBean log = new LoginBean();
            impresionesCtra.clear();
//            ArrayList<ConsultaGeneral> l = new ArrayList<>();
//            String[] parts = log.getDocumentoUserLog().split("-");
//            String filtroOrigen = "";
//            if (parts.length > 1) {
//                filtroOrigen = parts[0];
//            } else {
//                filtroOrigen = log.getDocumentoUserLog();
//            }
            File jasper;
            Map parametros = new HashMap();
            parametros.put("fechaIni", format2.format(selecFechaIni) + " 00:00:00");
            parametros.put("fechaFin", format2.format(selecFechaFin) + " 23:59:59");
            parametros.put("agencia", log.getNomUserLog());
            ConexionPool pool = new ConexionPool();
            pool.con = pool.dataSource.getConnection();
            System.out.println("filtroOrigen " + log.getNomUserLog());
            System.out.println("ini " + format2.format(selecFechaIni) + " 00:00:00");
            System.out.println("fin " + format2.format(selecFechaFin) + " 23:59:59");
            if (selectUser.equals("")) {
                jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/ResumenContravias.jasper"));
            } else {
                jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/ResumenContraviasUser.jasper"));
                parametros.put("usuario", selectUser);
            }
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, pool.con);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=ResumenContravias.xls");
            try (ServletOutputStream stream = response.getOutputStream()) {
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
                exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                exporter.exportReport();
                stream.flush();
            } catch (JRException e) {
                System.out.println("error " + e);
            }
            FacesContext.getCurrentInstance().responseComplete();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Seleccione un rango de fechas"));
        }
    }

    public void exportarRelacion() throws IOException, JRException, SQLException {
        if (selecFechaIni != null && selecFechaFin != null) {
            if (CmgeneradoList.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No hay resultados para Exportar"));
            } else {
                Map parametros = new HashMap();
                String report = "/Reports/reporteCmAsociados.jasper";
                parametros.put("fechaIni", format2.format(selecFechaIni) + " 00:00:00");
                parametros.put("fechaFin", format2.format(selecFechaFin) + " 23:59:59");
                if (!user.equals("")) {
                    parametros.put("agencia", user);
                    report = "/Reports/reporteCmAsociadosU.jasper";
                }
                ConexionPool pool = new ConexionPool();
                pool.con = pool.dataSource.getConnection();
                File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(report));
                JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, pool.con);
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment; filename=CmAsociados.xls");
                try (ServletOutputStream stream = response.getOutputStream()) {
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
                    exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                    exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                    exporter.exportReport();
                    stream.flush();
                }
                FacesContext.getCurrentInstance().responseComplete();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Debes seleccionar un rango de fechas"));
        }
    }

    public void exportarXLSRelacionCMS() throws IOException, JRException, SQLException {
        LoginBean log = new LoginBean();
        ConexionPool pool = new ConexionPool();
        pool.con = pool.dataSource.getConnection();
        File jasper = null;
        Map parametros = new HashMap();
        if (!numeroCm.equals("")) {
            System.out.println("filter 1");
            parametros.put("numeroCm", numeroCm);
            jasper = jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/ExportCmsByNumCm.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, pool.con);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=ListadoRelacionXCM.xls");
            try (ServletOutputStream stream = response.getOutputStream()) {
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
                exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                exporter.exportReport();
                stream.flush();
            } catch (JRException e) {
                System.out.println("error " + e);
            }
            FacesContext.getCurrentInstance().responseComplete();
        } else if (selecFechaIni != null && selecFechaFin != null) {
            System.out.println("fechas 2");
            parametros.put("fechaIni", format2.format(selecFechaIni) + " 00:00:00");
            parametros.put("fechaFin", format2.format(selecFechaFin) + " 23:59:59");
            if (user.equals("")) {
                jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/ExportCmsAllFechas.jasper"));
            } else {
                jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/ExportCmsAllFechasU.jasper"));
                parametros.put("agencia", user);
            }
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, pool.con);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=ListadoRelacionXFechas.xls");
            try (ServletOutputStream stream = response.getOutputStream()) {
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
                exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                exporter.exportReport();
                stream.flush();
                numeroCm = "";
                selecFechaIni = null;
                selecFechaFin = null;
                CmgeneradoList.clear();
                user = "";
            } catch (JRException e) {
                System.out.println("error " + e);
            }
            FacesContext.getCurrentInstance().responseComplete();
        } else {
            System.out.println("3");
            jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/ExportCmsAll.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, pool.con);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=ListadoRelacion.xls");
            try (ServletOutputStream stream = response.getOutputStream()) {
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
                exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                exporter.exportReport();
                stream.flush();
                selecFechaIni = null;
                selecFechaFin = null;
                CmgeneradoList.clear();
                user = "";
                numeroCm = "";
            } catch (JRException e) {
                System.out.println("error " + e);
            }
            FacesContext.getCurrentInstance().responseComplete();
        }
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public String getMsn() {
        return msn;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public boolean isIdavuelta() {
        return idavuelta;
    }

    public void setIdavuelta(boolean idavuelta) {
        this.idavuelta = idavuelta;
    }

    public int getSelectRuta() {
        return selectRuta;
    }

    public void setSelectRuta(int selectRuta) {
        this.selectRuta = selectRuta;
    }

    public Date getSelecFecha() {
        return selecFecha;
    }

    public void setSelecFecha(Date selecFecha) {
        this.selecFecha = selecFecha;
    }

    public String getSelectUser() {
        return selectUser;
    }

    public void setSelectUser(String selectUser) {
        this.selectUser = selectUser;
    }

    public String getListaEntrega() throws SQLException {
//        listarAutocomplete();
        saldos.clear();
        return listaEntrega;
    }

    public void setListaEntrega(String listaEntrega) {
        this.listaEntrega = listaEntrega;
    }

    public List<TblViajesTiquetes> getSaldos() {
        return saldos;
    }

    public void setSaldos(List<TblViajesTiquetes> saldos) {
        this.saldos = saldos;
    }

    public TblViajesTiquetes getTiquetesCurrrent() {
        if (tiquetesCurrrent == null) {
            tiquetesCurrrent = new TblViajesTiquetes();
        }
        return tiquetesCurrrent;
    }

    public void setTiquetesCurrrent(TblViajesTiquetes tiquetesCurrrent) {
        this.tiquetesCurrrent = tiquetesCurrrent;
    }

    public List<TblViajesTiquetes> getTiquetesToList() {
        return tiquetesToList;
    }

    public void setTiquetesToList() {
        this.tiquetesToList.clear();
    }

    public String getUsuarioNodum() {
        return usuarioNodum;
    }

    public void setUsuarioNodum(String usuarioNodum) {
        this.usuarioNodum = usuarioNodum;
    }

    public String getClaveNodum() {
        return claveNodum;
    }

    public void setClaveNodum(String claveNodum) {
        this.claveNodum = claveNodum;
    }

    public String getUsuarioTaquilla() {
        return usuarioTaquilla;
    }

    public void setUsuarioTaquilla(String usuarioTaquilla) {
        this.usuarioTaquilla = usuarioTaquilla;
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

    public String getDescripcionTiquetes() {
        return descripcionTiquetes;
    }

    public void setDescripcionTiquetes(String descripcionTiquetes) {
        this.descripcionTiquetes = descripcionTiquetes;
    }

    public int getTotalTrans() {
        return totalTrans;
    }

    public void setTotalTrans(int totalTrans, String signo) {
        if (signo.equals("+")) {
            this.totalTrans += totalTrans;
        } else {
            this.totalTrans -= totalTrans;
        }
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public boolean isDisable2() {
        return disable2;
    }

    public void setDisable2(boolean disable2) {
        this.disable2 = disable2;
    }

    public int getTempValorGlobal() {
        return tempValorGlobal;
    }

    public void setTempValorGlobal(int tempValorGlobal) {
        this.tempValorGlobal = tempValorGlobal;
    }

    public String getTempTipoCOntrato() {
        return tempTipoCOntrato;
    }

    public void setTempTipoCOntrato(String tempTipoCOntrato) {
        this.tempTipoCOntrato = tempTipoCOntrato;
    }

    public Date getSelecFechaIni() {
        return selecFechaIni;
    }

    public void setSelecFechaIni(Date selecFechaIni) {
        this.selecFechaIni = selecFechaIni;
    }

    public Date getSelecFechaFin() {
        return selecFechaFin;
    }

    public void setSelecFechaFin(Date selecFechaFin) {
        this.selecFechaFin = selecFechaFin;
    }

    public String getImpresiones() {
        return impresiones;
    }

    public void setImpresiones(String impresiones) {
        this.impresiones = impresiones;
    }

    public List<Transaccion> getImpresionesDia() {
        return impresionesDia;
    }

    public void setImpresionesDia(List<Transaccion> impresionesDia) {
        this.impresionesDia = impresionesDia;
    }

    public List<TblRegistroContravias> getImpresionesCtra() {
        return impresionesCtra;
    }

    public void setImpresionesCtra(List<TblRegistroContravias> impresionesCtra) {
        this.impresionesCtra = impresionesCtra;
    }

    public String getImpresionesCtravias() throws IOException {
        setSelecFechaIni(null);
        setSelecFechaFin(null);
        saldos.clear();
        setSelectUser("");
        return impresionesCtravias;
    }

    public void urlmpresionesCtravias() throws IOException {
        setSelecFechaIni(null);
        setSelecFechaFin(null);
        saldos.clear();
        setSelectUser("");
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Reportes/ImpresionesRealizadasC.xhtml");

    }

    public void setImpresionesCtravias(String impresionesCtravias) {
        this.impresionesCtravias = impresionesCtravias;
    }

    public String getReimpCompCtravia() {
        impresionesCtra.clear();
        return reimpCompCtravia;
    }

    public void setReimpCompCtravia(String reimpCompCtravia) {
        this.reimpCompCtravia = reimpCompCtravia;
    }

    public String getResumenContravias() {
        impresionesCtra.clear();
        return resumenContravias;
    }

    public void setResumenContravias(String resumenContravias) {
        this.resumenContravias = resumenContravias;
    }

    public List<DetalleCm> getDetalleCmList() {
        return detalleCmList;
    }

    public void setDetalleCmList(List<DetalleCm> detalleCmList) {
        this.detalleCmList = detalleCmList;
    }

    public DetalleCm getDetalleCm() {
        if (detalleCm == null) {
            detalleCm = new DetalleCm();
        }
        return detalleCm;
    }

    public void setDetalleCm(DetalleCm detalleCm) {
        this.detalleCm = detalleCm;
    }

    public List<DetalleCm> getDetalleCmList2() {
        return detalleCmList2;
    }

    public void setDetalleCmList2(List<DetalleCm> detalleCmList2) {
        this.detalleCmList2 = detalleCmList2;
    }

    public CmGenerado getCmgenerado() {
        if (cmgenerado == null) {
            cmgenerado = new CmGenerado();
        }
        return cmgenerado;
    }

    public void setCmgenerado(CmGenerado cmgenerado) {
        this.cmgenerado = cmgenerado;
    }

    public String getConseucutivo() {
        return conseucutivo;
    }

    public void setConseucutivo(String conseucutivo) {
        this.conseucutivo = conseucutivo;
    }

    public boolean isPrintCm() {
        return printCm;
    }

    public void setPrintCm(boolean printCm) {
        this.printCm = printCm;
    }

    public boolean isPrintCm2() {
        return printCm2;
    }

    public void setPrintCm2(boolean printCm2) {
        this.printCm2 = printCm2;
    }

    public List<String> getTrans() {
        return trans;
    }

    public void setTrans(List<String> trans) {
        this.trans = trans;
    }

    public String getCmList() throws SQLException {
        listarCms("cmGen");
        cmgenerado = null;
        detalleCm = null;
        detalleCmList.clear();
        detalleCmList2.clear();
        selectedEmpresas = null;
        return CmList;
    }

    public void setCmList(String CmList) {
        this.CmList = CmList;
    }

    public List<CmGenerado> getCmgeneradoList() {
        return CmgeneradoList;
    }

    public void setCmgeneradoList(List<CmGenerado> CmgeneradoList) {
        this.CmgeneradoList = CmgeneradoList;
    }

    public String getCmGenerar() throws SQLException {
        cmgenerado = null;
        detalleCm = null;
        detalleCmList.clear();
        detalleCmList2.clear();
        cargarDatos();
        selectedEmpresas = null;
        return CmGenerar;
    }

    public void setCmGenerar(String CmGenerar) {
        this.CmGenerar = CmGenerar;
    }

    public String getCmListAdmin() throws SQLException {
        listarCms("cmGenAdmin");
        return CmListAdmin;
    }

    public void setCmListAdmin(String CmListAdmin) {
        this.CmListAdmin = CmListAdmin;
    }

    public String getCmListAdminA() throws SQLException {
        CmgeneradoList.clear();
        cargarUsuarios();
        return CmListAdminA;
    }

    public void setCmListAdminA(String CmListAdminA) {
        this.CmListAdminA = CmListAdminA;
    }

    public String getTiqs() {
        return tiqs;
    }

    public void setTiqs(String tiqs) {
        this.tiqs = tiqs;
    }

    public ArrayList<Empresas> getList_empresas() {
        return list_empresas;
    }

    public void setList_empresas(ArrayList<Empresas> list_empresas) {
        this.list_empresas = list_empresas;
    }

    public String getEmprsa() {
        return emprsa;
    }

    public void setEmprsa(String emprsa) {
        this.emprsa = emprsa;
    }

    public List<DetalleCm> getCurrentCmList2() {
        return currentCmList2;
    }

    public void setCurrentCmList2(List<DetalleCm> currentCmList2) {
        this.currentCmList2 = currentCmList2;
    }

    public String[] getSelectedEmpresas() {
        return selectedEmpresas;
    }

    public void setSelectedEmpresas(String[] selectedEmpresas) {
        this.selectedEmpresas = selectedEmpresas;
    }

    public String getListRelacion() throws SQLException {
        cmgenerado = null;
        detalleCm = null;
        detalleCmList.clear();
        detalleCmList2.clear();
        selectedEmpresas = null;
        cargarUsuarios();
        listarCms("cmGenAdmin");
        return listRelacion;
    }

    public void urlListRelacion() throws SQLException, IOException {
        cmgenerado = null;
        detalleCm = null;
        detalleCmList.clear();
        detalleCmList2.clear();
        selectedEmpresas = null;
        cargarUsuarios();
        selectEmpresa = "";
        listarCms("cmGenAdmin");
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Reportes/ListRelacion.xhtml");

    }

    public void urlRevisarRelacion() throws SQLException, IOException {
        cmgenerado = null;
        detalleCm = null;
        detalleCmList.clear();
        detalleCmList2.clear();
        selectedEmpresas = null;
        selectEmpresa = "";
        cargarUsuarios();
        listarCms("cmGenAdmin");
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Reportes/RevisionRelacion.xhtml");
    }

    public void setListRelacion(String listRelacion) {
        this.listRelacion = listRelacion;
    }

    public List<String> getEmpresaAnterior() {
        return empresaAnterior;
    }

    public void setEmpresaAnterior(List<String> empresaAnterior) {
        this.empresaAnterior = empresaAnterior;
    }

    public String getUser() {
        if (user == null) {
            user = "";
        }
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public String getIdTransAnular() {
        return idTransAnular;
    }

    public void setIdTransAnular(String idTransAnular) {
        this.idTransAnular = idTransAnular;
    }

    public String getConvenioEstudiantes() {
        return ConvenioEstudiantes;
    }

    public void setConvenioEstudiantes(String ConvenioEstudiantes) {
        this.ConvenioEstudiantes = ConvenioEstudiantes;
    }

    public List<Estudiantes> getListEstudiantes() {
        return listEstudiantes;
    }

    public void setListEstudiantes(List<Estudiantes> listEstudiantes) {
        this.listEstudiantes = listEstudiantes;
    }

    public String getListAutorizados() {
        tiquetesAutorizadosTolist.clear();
        TiquetesAutorizados.clear();
        return listAutorizados;
    }

    public void setListAutorizados(String listAutorizados) {
        this.listAutorizados = listAutorizados;
    }

    public List<TiquetesAutorizados> getTiquetesAutorizados() {
        return TiquetesAutorizados;
    }

    public void setTiquetesAutorizados(List<TiquetesAutorizados> TiquetesAutorizados) {
        this.TiquetesAutorizados = TiquetesAutorizados;
    }

    public TiquetesAutorizados getAutorizadosCurrent() {
        return autorizadosCurrent;
    }

    public void setAutorizadosCurrent(TiquetesAutorizados autorizadosCurrent) {
        this.autorizadosCurrent = autorizadosCurrent;
    }

    public List<TiquetesAutorizados> getTiquetesAutorizadosTolist() {
        return tiquetesAutorizadosTolist;
    }

    public void setTiquetesAutorizadosTolist(List<TiquetesAutorizados> tiquetesAutorizadosTolist) {
        this.tiquetesAutorizadosTolist = tiquetesAutorizadosTolist;
    }

    public String getAutorizados() {
        return autorizados;
    }

    public void setAutorizados(String autorizados) {
        this.autorizados = autorizados;
    }

    public String getSelectEmpresa() {
        return selectEmpresa;
    }

    public void setSelectEmpresa(String selectEmpresa) {
        this.selectEmpresa = selectEmpresa;
    }

    public String getNumeroCm() {
        return numeroCm;
    }

    public void setNumeroCm(String numeroCm) {
        this.numeroCm = numeroCm;
    }

    public List<String> getCars() {
        return cars;
    }

    public void setCars(List<String> cars) {
        this.cars = cars;
    }

    public List<CmGenerado> getCmgeneradoListDescripcion() {
        return CmgeneradoListDescripcion;
    }

    public void setCmgeneradoListDescripcion(List<CmGenerado> CmgeneradoListDescripcion) {
        this.CmgeneradoListDescripcion = CmgeneradoListDescripcion;
    }

}
