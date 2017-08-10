package Beans;

import Entities.*;
import Modelo.Conexion;
import Modelo.ConexionPool;
import Modelo.ConsultaGeneral;
import Modelo.CrudObject;
import Utils.CiudadesUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.component.growl.Growl;
import org.primefaces.event.FileUploadEvent;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
@Named(value = "empleadosBean")
@SessionScoped
public class EmpleadosBean implements Serializable {

    /**
     * Variable privada: listUsuarios. Contendra el listado de Empleados
     */
    private List<TblEmpleados> listUsuarios = new ArrayList();
    /**
     * Variable privada: listUsuariosAutoComplete. Contendra el listado de
     * Empleados para el acmpo autocomplete de primefaces
     */
    private List<TblEmpleados> listUsuariosAutoComplete = new ArrayList();
    /**
     * Variable privada: listTiquetesEmpleado. Contendra el listado de tiquetes
     * para entregar a los empledos
     */
    private List<TblViajesTiquetes> listTiquetesEmpleado = new ArrayList();
    /**
     * Variable privada: saldos. Contendra el listado de saldos pendientes para
     * entregar a los empleados
     */
    private List<TblViajesTiquetes> saldos = new ArrayList();
    /**
     * Variable privada: logUsers. Contendra el listado de empleados que
     * contienen errores al subir por archivo xls
     */
    private List<TblEmpleados> logUsers = new ArrayList();
    /**
     * Variable privada: empleadosInsert. Contendra el listado de empleados que
     * han de subirse a la base de datos cuando se cargan por archivo xls
     */
    private List<TblEmpleados> empleadosInsert = new ArrayList();

    private List<TblEmpleados> empleadosInsert2 = new ArrayList();
    /**
     * Variable privada: FacturaVenta. Contendra el listado de tiquetes y costos
     * que debe cobrarse a la empresa por la entrega de los tiquetes.
     */
    private List<TblEmpleados> FacturaVenta = new ArrayList();
    /**
     * Variable privada: FacturaVentaHistorico. Contendra el listado de tiquetes
     * y costos que debe cobrarse a la empresa por la entrega de los tiquetes,
     * esta información se obtiene de la base de datos del aplicativo anterior.
     */
    private List<FacturaHistorico> FacturaVentaHistorico = new ArrayList();
    /**
     * Variable privada: LogTiquetesEmpleado. Contendra el listado de tiquetes
     * que la empresa sube para sus empleados desde archivo xls.
     */
    private List<TblViajesTiquetes> LogTiquetesEmpleado = new ArrayList();
    /**
     * Variable: ListUsuario. Variable para la navegacion. vista
     * EmpleadosList.xhtml
     */
    String ListUsuario = "../Empleados/EmpleadosList.xhtml";
    String Cambiarlave = "../Empleados/CambiarClave.xhtml";

    /**
     * Variable: ListUsuariosTiquetes. Variable para la navegacion. vista
     * EmpleadosTiquetesList.xhtml
     */
    String ListUsuariosTiquetes = "../Tiquetes/EmpleadosTiquetesList.xhtml";
    /**
     * Variable: Crearusuario. Variable para la navegacion. vista
     * EmpleadosCrear.xhtml
     */
    String Crearusuario = "../Empleados/EmpleadosCrear.xhtml";

    private String Regeresar = "/Convenios/faces/Admin/Empresas/EmpresasList.xhtml";
    /**
     * Variable: CodigosRurasList. Variable para la navegacion. vista
     * CodigosRutas.xhtml
     */
    String CodigosRurasList = "../Tiquetes/CodigosRutas.xhtml";
    /**
     * Variable: CrearTiquetesEmpleados. Variable para la navegacion. vista
     * EmpleadosCrearTiquetes.xhtml
     */
    String CrearTiquetesEmpleados = "../Tiquetes/EmpleadosCrearTiquetes.xhtml";
    /**
     * Variable: EditarUsuario. Variable para la navegacion. vista
     * UsuarioEditar.xhtml
     */
    String EditarUsuario = "../Usuarios/UsuarioEditar.xhtml";
    /**
     * Variable: ListFacturaVenta. Variable para la navegacion. vista
     * EmpleadosViajesProgramados.xhtml
     */
    String ListFacturaVenta = "../Reportes/EmpleadosViajesProgramados.xhtml";
    /**
     * Variable: ListSaldosViajes. Variable para la navegacion. vista
     * EmpleadosSaldoActual.xhtml
     */
    String ListSaldosViajes = "../Reportes/EmpleadosSaldoActual.xhtml";
    /**
     * Variable: ListSaldosViajesAdmon. Variable para la navegacion. vista
     * EmpleadosSaldoActual.xhtml
     */
    private String ListSaldosViajesAdmon = "../../Admin/Reportes/EmpleadosSaldoActual.xhtml";
    /**
     * Variable: ListFacturaVentaAdmon. Variable para la navegacion. vista
     * EmpleadosViajesProgramados.xhtml
     */
    private String ListFacturaVentaAdmon = "../../Admin/Reportes/EmpleadosViajesProgramados.xhtml";
    /**
     * Variable: ListFacturaVentaAdmonHisrory. Variable para la navegacion.
     * vista EmpleadosViajesProgramadosHistorico.xhtml
     */
    private String ListFacturaVentaAdmonHisrory = "../../Admin/Reportes/EmpleadosViajesProgramadosHistorico.xhtml";

    private String ListFacturaVentaAdmonHisrory2 = "../Reportes/EmpleadosViajesProgramadosHistorico.xhtml";
    /**
     * Variable: fec. Variable auxiliar para nombrar el archivo subido al
     * servidor. que sera eliminado posterior a la insercion de datos en la bd
     */
    String fec = "";
    /**
     * Variable: usuario. Almacenara el objeto actual de un emplado
     */
    private TblEmpleados usuario;
    /**
     * Variable: usuario. Almacenara el objeto actual de los tiquetes a ingresar
     * para empleados
     */
    private TblViajesTiquetes empleadosTiquetes;
    private List<Estados> listEstados = new ArrayList();
    /**
     * Variable: listEmpresas. contendrá el listado de Empresas
     */
    private List<Empresas> listEmpresas = new ArrayList();
    /**
     * Variable: listDetalleConvenio. contendrá el listado de convenios con las
     * empresas
     */
    private List<DetalleConvenio> listDetalleConvenio = new ArrayList();
    /**
     * Variable: codigosRutas. contendra los codigos de las rutas asociadas al
     * convenio de la empresa para que puedan ser descargadas o visualizadas.
     */
    private List<DetalleConvenio> codigosRutas = new ArrayList();
    /**
     * Variable: listRoles. contendra el listado de roles.
     */
    private List<Roles> listRoles = new ArrayList();
    private Usuarios currentUser;
    /**
     * Variable: msn. Utilizada para almacenar un mensaje al finalizar la carga
     * de los tiquetes o los empleados por archivo xl.
     */
    private String msn = "";
    /**
     * Variable: format. Utilizada para formatear las fechas con hora
     */
    SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
    /**
     * Variable: format. Utilizada para formatear las fechas sin hora
     */
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd");
    /**
     * Variable: growl. Instancia el contenedor de mensajes en las vistas
     */
    Growl growl = new Growl();
    /**
     * Variable: validate. Utilizada para controlar errores al subir archivos
     * xls vistas
     */
    boolean validate = false;
    boolean idavuelta = false;

    private Empresas e;
    /**
     * Variable: selectRuta. Utilizada para establecer la ruta seleccionada
     * desde la vista y asi filtrar los datos apartir de su valor
     */
    int selectRuta;
    /**
     * Variable: selecFecha. Utilizada para establecer la fecha seleccionada
     * desde la vista y asi filtrar los datos apartir de su valor
     */
    Date selecFecha;
    /**
     * Variable: selectUser. Utilizada para establecer el usuario seleccionado
     * desde la vista y asi filtrar los datos apartir de su valor
     */
    String selectUser = "";
    /**
     * Variable: codRuta. Utilizada para establecer el codigo de ruta
     * seleccionado desde la vista y asi filtrar las rutas apartir de este
     */
    String codRuta = "";
    /**
     * Variable: origen. Utilizada para establecer el origen de ruta
     * seleccionado desde la vista y asi filtrar las rutas apartir de este
     */
    String origen = "";
    /**
     * Variable: destino. Utilizada para establecer el destino de ruta
     * seleccionado desde la vista y asi filtrar las rutas apartir de este
     */
    String destino = "";
    /**
     * Variable: servicio. Utilizada para establecer el servicio de ruta
     * seleccionado desde la vista y asi filtrar las rutas apartir de este
     */
    String servicio = "";
    /**
     * Variable: selecFechaIni. Utilizada para establecer la fecha inicial de un
     * rango de fechas seleccionado, esta es general para todo tipo de consultas
     * que requiere un rango de fechas
     */
    Date selecFechaIni;
    /**
     * Variable: selecFechaFin. Utilizada para establecer la fecha final de un
     * rango de fechas seleccionado, esta es general para todo tipo de consultas
     * que requiere un rango de fechas
     */
    Date selecFechaFin;
    /**
     * Variable: idEmpresa. Utilizada para establecer el id de una empresa
     * seleccionada desde la vista y asi los datos apartir de su valor
     */
    private int idEmpresa = 0;
    /**
     * Variable: emprsa. Utilizada para establecer el id de una empresa
     * seleccionada desde la vista y asi los datos apartir de su valor
     */
    private String emprsa = "";
    /**
     * Variable: countok. Utilizada para almacenar la cantidad de errores que se
     * presentan al momento de cargar los datos desde archivo excel
     */
    private int countok = 0;

    public EmpleadosBean() {
    }

    @PostConstruct
    public void init() {
        try {
            growl.setLife(5000);
            listUsuarios.clear();
            listEstados.clear();
            FacturaVenta.clear();
            listarUsuarios();
            lisEstados();
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        }
    }

    /**
     * Método que permite establecer el valor al arraylist listado de rutas.
     *
     * @throws java.sql.SQLException Error que se presenta cuando se intenta
     * recuperar las rutas desde la base de datos. Se puede filtrar por origen,
     * destino, codigo de ruta y servicio
     * @since incluido desde la version 1.0
     */
    public void listarCodigosRutas() throws SQLException {
        StringBuilder params = new StringBuilder();
        codigosRutas.clear();
        LoginBean log = new LoginBean();
        int id_empresa = 0;
        if (log.getIdEmpresa() == 60 && getE() != null) {
            id_empresa = getE().getId_empresa();
        } else {
            id_empresa = log.getIdEmpresa();
        }
        params.append(id_empresa);
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        if (!codRuta.equals("")) {
            params.append(',').append(codRuta);
            l = (ArrayList) CiudadesUtils.getSelectSql("oDestUtilCodRuta", 1, params.toString());
        } else if (!origen.equals("")) {
            params.append(',').append(origen);
            l = (ArrayList) CiudadesUtils.getSelectSql("oDestUtilOrigen", 1, params.toString());
        } else if (!destino.equals("")) {
            params.append(',').append(destino);
            l = (ArrayList) CiudadesUtils.getSelectSql("oDestUtilDestino", 1, params.toString());
        } else if (!servicio.equals("")) {
            params.append(',').append(servicio);
            l = (ArrayList) CiudadesUtils.getSelectSql("oDestUtilServicio", 1, params.toString());
        } else {
            l = (ArrayList) CiudadesUtils.getSelectSql("origenDestinoUtil", 1, params.toString());
        }
        for (ConsultaGeneral obj : l) {
            codigosRutas.add(new DetalleConvenio(obj.getNum1(), obj.getStr1(),
                    obj.getStr2(), obj.getStr3()));
        }
        setCodRuta("");
        setOrigen("");
        setDestino("");
        setServicio("");
    }

    /**
     * Método que permite establecer el valor al listado de usuarios.
     *
     * @throws java.sql.SQLException Error que se presenta cuando se intenta
     * recuperar los usuarios desde la base de datos.
     * @since incluido desde la version 1.0
     */
    public void listarUsuarios() throws SQLException {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectUser") != null) {
            setSelectUser(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectUser"));
        }
        LoginBean log = new LoginBean();
        int id_empresa = 0;
        if (log.getIdEmpresa() == 60 && getE() != null) {
            id_empresa = getE().getId_empresa();
        } else {
            id_empresa = log.getIdEmpresa();
        }
        listUsuarios.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        if (selectUser.equals("")) {
            l = (ArrayList) CrudObject.getSelectSql("empleados", 1, "" + id_empresa);
        } else {
            l = (ArrayList) CrudObject.getSelectSql("empleadosF", 1, "" + id_empresa + "," + selectUser + "");
        }
        for (ConsultaGeneral obj : l) {
            listUsuarios.add(new TblEmpleados(obj.getStr1(), obj.getStr2(),
                    obj.getStr3(), obj.getStr4(), obj.getStr5(), obj.getNum1(),
                    obj.getStr6(), obj.getFecha1(), obj.getFecha2(), obj.getNum2()));
        }
        setSelectUser("");
    }

    /**
     * Método que permite establecer el valor al listado de facturas de venta.
     *
     * @throws java.sql.SQLException Error que se presenta cuando se intenta
     * recuperar las facturas de venta desde la base de datos.
     * @since incluido desde la version 1.0
     */
    public void facturaVentaList() throws SQLException {
        FacturaVenta.clear();
        if (selecFechaIni != null && selecFechaFin != null) {
            LoginBean log = new LoginBean();
            int id_empresa = 0;
            if (log.getIdEmpresa() == 60 && getE() != null) {
                id_empresa = getE().getId_empresa();
            } else {
                id_empresa = log.getIdEmpresa();
            }
            String[] porciones = selectUser.split(" ");
            System.out.println("porciones " + porciones[0]);
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            if (!porciones[0].equals("")) {
                l = (ArrayList) CrudObject.getSelectSql("facturaVentaDoc", 1, "" + id_empresa + "," + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59," + porciones[0] + "");
            } else {
                l = (ArrayList) CrudObject.getSelectSql("facturaVenta", 1, "" + id_empresa + "," + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59");
            }
            for (ConsultaGeneral obj : l) {
                FacturaVenta.add(new TblEmpleados(obj.getStr1(),
                        obj.getStr2(), obj.getStr3(),
                        obj.getStr4(), obj.getStr5(), obj.getNum1(),
                        obj.getNum2(), obj.getFecha1(), obj.getStr6(), obj.getNum3(), obj.getStr7(), obj.getStr8(), obj.getStr9()));

            }
            if (FacturaVenta.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Ingrese un rango de fechas"));
        }
    }

    /**
     * Método que permite establecer el valor al listado de facturas de venta
     * para el administrador del sistema, son vistas diferentes.
     *
     * @throws java.sql.SQLException Error que se presenta cuando se intenta
     * recuperar las facturas de venta desde la base de datos.
     * @since incluido desde la version 1.0
     */
    public void facturaVentaListAdmon() throws SQLException {
        if (selecFechaIni != null && selecFechaFin != null) {
            if (idEmpresa > 0) {
                if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc") != null) {
                    setSelectUser(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc"));
                }
                FacturaVenta.clear();
                ArrayList<ConsultaGeneral> l = new ArrayList<>();
                if (getSelectUser().equals("")) {
                    l = (ArrayList) CrudObject.getSelectSql("facturaVenta", 1, "" + idEmpresa + "," + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59");
                } else {
                    l = (ArrayList) CrudObject.getSelectSql("facturaVentaDoc", 1, "" + idEmpresa + "," + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59," + getSelectUser() + "");
                }

                for (ConsultaGeneral obj : l) {
                    FacturaVenta.add(new TblEmpleados(obj.getStr1(),
                            obj.getStr2(), obj.getStr3(),
                            obj.getStr4(), obj.getStr5(), obj.getNum1(),
                            obj.getNum2(), obj.getFecha1(), obj.getStr6(), obj.getNum3(), obj.getStr7(), obj.getStr8(), obj.getStr9()));

                }
                if (FacturaVenta.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Seleccione empresa"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Ingrese un rango de fechas"));
        }
    }

    /**
     * Método que permite establecer el valor al listado de facturas de venta
     * desde el sistema viejo para tener un historial de estos registros. Se
     * obtienen las facturas a partir de una conexion remota al servidor de base
     * de datos que esta en la web
     *
     * @throws java.sql.SQLException Error que se presenta cuando se intenta
     * recuperar las facturas de venta desde la base de datos remota.
     * @since incluido desde la version 1.0
     */
    public void historicoFacturaVentaListAdmon() throws SQLException {
        if (selecFechaIni != null && selecFechaFin != null) {
            if (!emprsa.equals("")) {
                FacturaVentaHistorico.clear();
                FacturaVentaHistorico = CiudadesUtils.getHistoricoFacturas(selecFechaIni, selecFechaFin, emprsa);
                if (FacturaVentaHistorico.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Seleccione empresa"));
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Ingrese un rango de fechas"));
        }
    }

    public void historicoFacturaVentaListEmps() throws SQLException {
        if (selecFechaIni != null && selecFechaFin != null) {
            LoginBean log = new LoginBean();
            FacturaVentaHistorico.clear();
            FacturaVentaHistorico = CiudadesUtils.getHistoricoFacturas(selecFechaIni, selecFechaFin, log.getNomUserLog());
            if (FacturaVentaHistorico.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Ingrese un rango de fechas"));
        }
    }

    /**
     * Método que permite establecer el valor al listado saldos
     *
     * @throws java.sql.SQLException Error que se presenta cuando se intenta
     * recuperar los saldos actuales desde la base de datos.
     * @since incluido desde la version 1.0
     */
    public void saldoActual() throws SQLException {
        if (selecFechaIni != null && selecFechaFin != null) {
            saldos.clear();
            LoginBean log = new LoginBean();
            int id_empresa = 0;
            if (log.getIdEmpresa() == 60 && getE() != null) {
                id_empresa = getE().getId_empresa();
            } else {
                id_empresa = log.getIdEmpresa();
            }
            String[] porciones = selectUser.split(" ");
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            if (!porciones[0].equals("")) {
                l = (ArrayList) CrudObject.getSelectSql("saldosPendByDoc", 1, "" + id_empresa + "," + format2.format(selecFechaIni) + "," + format2.format(selecFechaFin) + "," + porciones[0] + "");
            } else {
                l = (ArrayList) CrudObject.getSelectSql("saldosPend", 1, "" + id_empresa + "," + format2.format(selecFechaIni) + "," + format2.format(selecFechaFin) + "");
            }
            for (ConsultaGeneral obj : l) {
                saldos.add(new TblViajesTiquetes(obj.getStr6(), obj.getStr1(),
                        obj.getStr2(), obj.getFecha1(),
                        obj.getFecha2(), obj.getStr3(),
                        obj.getStr4(), obj.getNum2(), obj.getStr5(), obj.getNum1(), obj.getStr7(), obj.getNum3()));

            }
            if (saldos.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Ingrese un rango de fechas"));
        }
    }
    //fin saldo actual

    /**
     * Método que permite establecer el valor al listado saldos para la vista de
     * los usuarios con rol EMPRESA
     *
     * @throws java.sql.SQLException Error que se presenta cuando se intenta
     * recuperar los saldos actuales para cada empresa desde la base de datos.
     * @since incluido desde la version 1.0
     */
    public void saldoActualAdmon() throws SQLException {
        if (selecFechaIni != null && selecFechaFin != null) {
            saldos.clear();
            if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc") != null) {
                setSelectUser(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc"));
            }
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            if (!selectUser.equals("")) {
                l = (ArrayList) CrudObject.getSelectSql("saldosPendByAdm", 1, "" + format2.format(selecFechaIni) + "," + format2.format(selecFechaFin) + "," + selectUser + "");
            } else {
                l = (ArrayList) CrudObject.getSelectSql("saldosPendAdm", 1, "" + format2.format(selecFechaIni) + "," + format2.format(selecFechaFin) + "");
            }
            for (ConsultaGeneral obj : l) {
                saldos.add(new TblViajesTiquetes(obj.getStr6(), obj.getStr1(),
                        obj.getStr2(), obj.getFecha1(),
                        obj.getFecha2(), obj.getStr3(),
                        obj.getStr4(), obj.getNum2(), obj.getStr5(), obj.getNum1(), obj.getStr7(), obj.getNum3()));

            }
            if (saldos.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Ingrese un rango de fechas"));
        }
    }
    //fin saldo actual admon

    /**
     * Método que permite establecer el valor al listado de tiquetes de
     * empleados para la vista de los usuarios con rol EMPRESA.
     *
     * @throws java.sql.SQLException Error que se presenta cuando se intenta
     * recuperar los tiquetes actuales para cada empleado desde la base de
     * datos.
     * @since incluido desde la version 1.0
     */
    public void listarUsuariosTiquetes() throws SQLException {
        listTiquetesEmpleado.clear();
        LoginBean log = new LoginBean();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        int id_empresa = 0;
        if (log.getIdEmpresa() == 60 && getE() != null) {
            id_empresa = getE().getId_empresa();
        } else {
            id_empresa = log.getIdEmpresa();
        }
        l = (ArrayList) CrudObject.getSelectSql("empleadosTiquetes", 1, "" + id_empresa + "");
        for (ConsultaGeneral obj : l) {
            listTiquetesEmpleado.add(new TblViajesTiquetes(obj.getStr6(), obj.getStr1(),
                    obj.getStr2(), obj.getFecha1(),
                    obj.getFecha2(), obj.getStr3(),
                    obj.getStr4(), obj.getNum2(), obj.getStr5(), obj.getNum1(), obj.getStr7(), obj.isBool1()));

        }
//        if (listTiquetesEmpleado.isEmpty()) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso..!", "No hay resultados"));
//        }
    }

    /**
     * Método que permite establecer el valor al listado de tiquetes de
     * empleados para la vista de los usuarios con rol EMPRESA a partir de la
     * ruta seleccionada.
     *
     * @throws java.sql.SQLException Error que se presenta cuando se intenta
     * recuperar los tiquetes actuales para cada empleado desde la base de
     * datos.
     * @since incluido desde la version 1.0
     */
    public void listarUsuariosByRuta() throws SQLException {
        listTiquetesEmpleado.clear();
        LoginBean log = new LoginBean();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        int id_empresa = 0;
        if (log.getIdEmpresa() == 60 && getE() != null) {
            id_empresa = getE().getId_empresa();
        } else {
            id_empresa = log.getIdEmpresa();
        }
        l = (ArrayList) CrudObject.getSelectSql("emptiqueteByRuta", 1, "" + id_empresa + "," + selectRuta + "");
        for (ConsultaGeneral obj : l) {
            listTiquetesEmpleado.add(new TblViajesTiquetes(obj.getStr6(), obj.getStr1(),
                    obj.getStr2(), obj.getFecha1(),
                    obj.getFecha2(), obj.getStr3(),
                    obj.getStr4(), obj.getNum2(), obj.getStr5(), obj.getNum1(), obj.getStr7(), obj.isBool1()));

        }
        setSelectRuta(0);
    }

    /**
     * Método que permite establecer el valor al listado de tiquetes de
     * empleados para la vista de los usuarios con rol EMPRESA a partir de la
     * fecha seleccionada.
     *
     * @throws java.sql.SQLException Error que se presenta cuando se intenta
     * recuperar los tiquetes actuales para cada empleado desde la base de
     * datos.
     * @since incluido desde la version 1.0
     */
    public void listarUsuariosByFecha() throws SQLException {
        listTiquetesEmpleado.clear();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        LoginBean log = new LoginBean();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        int id_empresa = 0;
        if (log.getIdEmpresa() == 60 && getE() != null) {
            id_empresa = getE().getId_empresa();
        } else {
            id_empresa = log.getIdEmpresa();
        }
        l = (ArrayList) CrudObject.getSelectSql("emptiqueteByFecha", 1, "" + id_empresa + "," + formato.format(selecFecha) + "");
        for (ConsultaGeneral obj : l) {
            listTiquetesEmpleado.add(new TblViajesTiquetes(obj.getStr6(), obj.getStr1(),
                    obj.getStr2(), obj.getFecha1(),
                    obj.getFecha2(), obj.getStr3(),
                    obj.getStr4(), obj.getNum2(), obj.getStr5(), obj.getNum1(), obj.getStr7(), obj.isBool1()));

        }
        setSelecFecha(null);
    }

    /**
     * Método que permite establecer el valor al listado de tiquetes de
     * empleados para la vista de los usuarios con rol EMPRESA a partir del
     * numero de documento del empleado.
     *
     * @throws java.sql.SQLException Error que se presenta cuando se intenta
     * recuperar los tiquetes actuales para cada empleado desde la base de
     * datos.
     * @since incluido desde la version 1.0
     */
    public void listarUsuariosByDocumento() throws SQLException {
        listTiquetesEmpleado.clear();
        String[] porciones = selectUser.split(" ");
        LoginBean log = new LoginBean();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        int id_empresa = 0;
        if (log.getIdEmpresa() == 60 && getE() != null) {
            id_empresa = getE().getId_empresa();
        } else {
            id_empresa = log.getIdEmpresa();
        }
        l = (ArrayList) CrudObject.getSelectSql("emptiqueteByUser", 1, "" + id_empresa + "," + porciones[0] + "");
        for (ConsultaGeneral obj : l) {
            listTiquetesEmpleado.add(new TblViajesTiquetes(obj.getStr6(), obj.getStr1(),
                    obj.getStr2(), obj.getFecha1(),
                    obj.getFecha2(), obj.getStr3(),
                    obj.getStr4(), obj.getNum2(), obj.getStr5(), obj.getNum1(), obj.getStr7(), obj.isBool1()));

        }
        setSelectUser("");
    }

    /**
     * Método que permite establecer el valor al listado de viajes creados por
     * la empresa para cada empleado.
     *
     * @throws java.sql.SQLException Error que se presenta cuando se intenta
     * recuperar los tiquetes actuales para cada empleado desde la base de
     * datos.
     * @since incluido desde la version 1.0
     */
    private void listarOrigenDestino() throws SQLException {
        listDetalleConvenio.clear();
        LoginBean log = new LoginBean();
        int id_empresa = 0;
        if (log.getIdEmpresa() == 60 && getE() != null) {
            id_empresa = getE().getId_empresa();
        } else {
            id_empresa = log.getIdEmpresa();
        }
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CiudadesUtils.getSelectSql("origenDestinoUtil", 1, "" + id_empresa);
        for (ConsultaGeneral obj : l) {
            listDetalleConvenio.add(new DetalleConvenio(obj.getNum1(), obj.getStr1(),
                    obj.getStr2(), obj.getStr3()));

        }
    }

    /**
     * Método que permite obtener el id_detalle_convenio_pk de la tabla
     * detalle_convenio para crear viajes a empleados apartir del id_convenio y
     * el id_empresa
     *
     * @param id_convenio id de convenio
     * @param id_empresa id de empresa
     * @throws java.sql.SQLException Error que se presenta cuando se intenta
     * recuperar el id del detalle convenio.
     * @since incluido desde la version 1.0
     */
    private int getIdConvenioByDetalle(int id_convenio, int id_empresa) throws SQLException {
        int id_conve = 0;
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CiudadesUtils.getSelectSql("idConvenioBydetalle", 1, "" + id_convenio + "," + id_empresa + "");
        if (l.size() > 0) {
            id_conve = l.get(0).getNum1();
        }
        return id_conve;
    }

    /**
     * Método que permite establecer el valor al listado de emplados para la
     * funcion autcompletar.
     *
     * @throws java.sql.SQLException Error que se presenta cuando se intenta
     * recuperar los empleados de la bd.
     * @since incluido desde la version 1.0
     */
    private void listarAutocomplete() throws SQLException {
        LoginBean log = new LoginBean();
        listUsuariosAutoComplete.clear();
        int id_empresa = 0;
        if (log.getIdEmpresa() == 60 && getE() != null) {
            id_empresa = getE().getId_empresa();
        } else {
            id_empresa = log.getIdEmpresa();
        }
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CiudadesUtils.getSelectSql("empleadoAutocomplete", 1, "" + id_empresa);
        for (ConsultaGeneral obj : l) {
            listUsuariosAutoComplete.add(new TblEmpleados(obj.getStr1(), obj.getStr2()));
        }
    }

    /**
     * Método que sugiere los empleados que coincidan con lo que se digita en la
     * vista y retorna dichas coincidencias en el campo autocompletar de
     * primefaces.
     *
     * @param name es el valor que se digita en la vista
     * @return queried es el valor que coincide con lo que el usuario digita
     * @since incluido desde la version 1.0
     */
    public List<TblEmpleados> queryByName(String name) {
        List<TblEmpleados> queried = new ArrayList<>();
        this.listUsuariosAutoComplete.stream().forEach((empl) -> {
            if (empl.getNombre().startsWith(name.toLowerCase()) || empl.getNombre().startsWith(name.toUpperCase())) {
                queried.add(empl);
            } else if (empl.getDocumento().startsWith(name)) {
                queried.add(empl);
            }
        });
        return queried;
    }

    /**
     * Método que permite establecer el valor al listado de estados
     *
     * @throws java.sql.SQLException Error que se presenta cuando se intenta
     * recuperar los estados de la bd.
     * @since incluido desde la version 1.0
     */
    private void lisEstados() throws SQLException {
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CiudadesUtils.getSelectSql("estadosUtil", 1, "param");
        for (ConsultaGeneral obj : l) {
            listEstados.add(new Estados(obj.getNum1(), obj.getStr1()));
        }
    }

    /**
     * Método que permite crear los empleados
     *
     * @throws java.sql.SQLException Error que se presenta cuando se intenta
     * crear los empleados en la bd.
     * @throws java.lang.InterruptedException error en tiempo de ejecucion
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir
     * @throws java.text.ParseException error que ocurre cuando intenta
     * convertir cadenas a enteros en el metodo en la calse CrudObject
     * @since incluido desde la version 1.0
     */
    public void createEmpleados() throws SQLException, InterruptedException, IOException, ParseException {
        LoginBean log = new LoginBean();
        int id_empresa = 0;
        if (log.getIdEmpresa() == 60 && getE() != null) {
            id_empresa = getE().getId_empresa();
        } else {
            id_empresa = log.getIdEmpresa();
        }
        EmpresaEmpleado empempleado = new EmpresaEmpleado();
        usuario.setUserMod(log.getDocumentoUserLog());
        usuario.setFechaMod(new Date());
        usuario.setFechaCreacion(new Date());
        usuario.setTiquetesPendientes(0);
        empempleado.setFechaCreacion(new Date());
        empempleado.setCodEstado(38);
        empempleado.setDocumento(usuario.getDocumento());
        empempleado.setIdEmpresa(id_empresa);
        usuario.setEmpresaEmpleado(empempleado);
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CrudObject.getSelectSql("empleadosHomonimo", 1, usuario.getDocumento() + "");
        if (l.size() > 0) {
            /* usuario = null;
            setUsuarios(new TblEmpleados(l.get(0).getStr1(), l.get(0).getStr2(),
                    l.get(0).getStr3(), l.get(0).getStr4(), l.get(0).getStr5(), l.get(0).getNum1(),
                    l.get(0).getStr6(), l.get(0).getFecha1(), l.get(0).getFecha2(), l.get(0).getNum2()));
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Empleados/EmpleadosHomonimo.xhtml");
             */
            System.out.println("******voy por aqui-------");
            long a = CrudObject.create2(usuario);
            if (a >= 1) {
                System.out.println("a =" + a);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Empleado Creado"));
                listUsuarios.clear();
                listarUsuarios();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "El empleado ya estaba asociado a esta emopresa"));
                listUsuarios.clear();
                listarUsuarios();
            }
            l.clear();
            usuario = null;
            log = null;
        } else {
            long a = CrudObject.create(usuario);
            if (a >= 1) {
                System.out.println("a =" + a);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Empleado Creado"));
                listUsuarios.clear();
                listarUsuarios();
            }
            l.clear();
            usuario = null;
            log = null;
        }

    }

    public void cambiarClaveUsuario() throws SQLException, InterruptedException, IOException, ParseException {
        LoginBean log = new LoginBean();
        currentUser.setIdUsuario(log.getIdUserLog());
        int a = CrudObject.editClaveUser(currentUser);
        if (a >= 1) {
            System.out.println("a =" + a);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Clave Actualizada"));
            listUsuarios.clear();
            listarUsuarios();
        }
        currentUser = null;
        log = null;

    }

    /**
     * Método que establece el empleado seleccionado para la edición y redirige
     * a la vista de edición de empleados.
     *
     * @param u Objeto actual de empleado seleccionado para la edición
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void prepareEdit(TblEmpleados u) throws IOException {
        setUsuarios(u);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Empleados/EmpleadosEditar.xhtml");
    }

    public void prepareEditViajes(TblViajesTiquetes e) throws IOException, SQLException {
        setUsuariosTiquetes(e);
        getUsuariosTiquetes().getEmpleado().setNomDoc(getUsuariosTiquetes().getEmpleado().getDocumento() + " " + getUsuariosTiquetes().getEmpleado().getNombre());

        getUsuariosTiquetes().setStrtTquetesAsignados(Integer.toString(getUsuariosTiquetes().getTiquetesAsignados()));
        getUsuariosTiquetes().getDetalleConvenio().setId_detalle_fk(Utils.CiudadesUtils.getDetalleConvPk(getUsuariosTiquetes().getId_Viaje_tiquete()));
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Tiquetes/EmpleadosEditarTiquetes.xhtml");
    }

    /**
     * Método que permite crear los viajes y asignar tiquete para los empleados.
     *
     * @throws java.sql.SQLException
     * @throws java.text.ParseException
     * @since incluido desde la version 1.0
     */
    public void createRutaseEmpleados() throws SQLException, ParseException {
        String[] porciones = empleadosTiquetes.getEmpleado().getNomDoc().split(" ");
        int tiq = 0;
        boolean convert = false;
        try {
            tiq = Integer.parseInt(empleadosTiquetes.getStrtTquetesAsignados());
        } catch (NumberFormatException ex) {
            convert = true;
        }
        if (empleadosTiquetes.getEmpleado().getNomDoc().length() < 5) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Empleado no valido"));
        } else if (convert) {// si hay error al convertit la cantidad de tiquetes debe mostrar este mensaje
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Cantidad de tiquetes Invalida"));
        } else {
            LoginBean log = new LoginBean();
            int id_empresa = 0;
            if (log.getIdEmpresa() == 60 && getE() != null) {
                id_empresa = getE().getId_empresa();
            } else {
                id_empresa = log.getIdEmpresa();
            }
            empleadosTiquetes.setDocumento(porciones[0]);
            empleadosTiquetes.setIdEmpresa(id_empresa);
            empleadosTiquetes.setDetalleConvPk(empleadosTiquetes.getDetalleConvenio().getId_detalle_fk());
            empleadosTiquetes.setIdConvenio(getIdConvenioByDetalle(empleadosTiquetes.getDetalleConvenio().getId_detalle_fk(), id_empresa));
            empleadosTiquetes.setUserMod(log.getDocumentoUserLog());
            empleadosTiquetes.setFechaMod(new Date());
            empleadosTiquetes.setTiquetesEntregados(0);
            long a = CrudObject.create(empleadosTiquetes);
            if (a >= 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Viaje actualizado con exito"));
                listarAutocomplete();
                listarOrigenDestino();
                empleadosTiquetes = null;
                log = null;
            }

        }

    }

    public void editRutaseEmpleados() throws SQLException, ParseException, IOException {
        String[] porciones = empleadosTiquetes.getEmpleado().getNomDoc().split(" ");
        int tiq = 0;
        boolean convert = false;
        try {
            tiq = Integer.parseInt(empleadosTiquetes.getStrtTquetesAsignados());
        } catch (NumberFormatException ex) {
            convert = true;
        }
        if (empleadosTiquetes.getEmpleado().getNomDoc().length() < 5) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Empleado no valido"));
        } else if (convert) {// si hay error al convertit la cantidad de tiquetes debe mostrar este mensaje
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Cantidad de tiquetes Invalida"));
        } else {
            LoginBean log = new LoginBean();
            int id_empresa = 0;
            if (log.getIdEmpresa() == 60 && getE() != null) {
                id_empresa = getE().getId_empresa();
            } else {
                id_empresa = log.getIdEmpresa();
            }
            empleadosTiquetes.setDocumento(porciones[0]);
            empleadosTiquetes.setIdEmpresa(id_empresa);
            empleadosTiquetes.setDetalleConvPk(empleadosTiquetes.getDetalleConvenio().getId_detalle_fk());
            empleadosTiquetes.setIdConvenio(getIdConvenioByDetalle(empleadosTiquetes.getDetalleConvenio().getId_detalle_fk(), id_empresa));
            empleadosTiquetes.setUserMod(log.getDocumentoUserLog());
            empleadosTiquetes.setFechaMod(new Date());
            long a = CrudObject.edit(empleadosTiquetes);
            if (a >= 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Viaje creado con exito"));
                listarAutocomplete();
                listarOrigenDestino();
                empleadosTiquetes = null;
                log = null;
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Tiquetes/EmpleadosTiquetesList.xhtml");
            }
        }

    }

    /**
     * Método que permite editar los empleados.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.InterruptedException ocurre cuando una transaccion se
     * cae en tiempo de ejecucion
     * @throws java.io.IOException
     * @throws java.text.ParseException
     * @since incluido desde la version 1.0
     */
    public void editEmpleados() throws SQLException, InterruptedException, IOException, ParseException {
        LoginBean log = new LoginBean();
        usuario.setUserMod(log.getDocumentoUserLog());
        usuario.setFechaMod(new Date());
        long a = CrudObject.edit(usuario);
        if (a >= 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Empleados/EmpleadosList.xhtml");
            listUsuarios.clear();
            listarUsuarios();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("oldDco");
        }
        usuario = null;
        log = null;

    }

    /**
     * Método que permite detectar si un empleado ya esta en la base de datos. y
     * lo actualiza
     *
     * @throws java.sql.SQLException
     * @throws java.lang.InterruptedException ocurre cuando una transaccion se
     * cae en tiempo de ejecucion
     * @throws java.io.IOException
     * @throws java.text.ParseException
     * @since incluido desde la version 1.0
     */
    public void hominimo() throws SQLException, InterruptedException, IOException, ParseException {
        LoginBean log = new LoginBean();
        usuario.setUserMod(log.getDocumentoUserLog());
        usuario.setFechaMod(new Date());
        long a = CrudObject.edit(usuario);
        if (a >= 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Empleados/EmpleadosList.xhtml");
            listUsuarios.clear();
            listarUsuarios();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("oldDco");
        }
        usuario = null;
        log = null;

    }

    /**
     * Método establece el empleado seleccionado para confirmar su eliminación,
     * y redirige a la vista de confirmación para la eliminación.
     *
     * @param e objeto empleado actualmente seleccionado
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void confirmDelete(TblEmpleados e) throws IOException {
        setUsuarios(e);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Empleados/EmpleadosConfirmDelete.xhtml");
    }
    
    

    /**
     * Método que establece el viaje seleccionado para la eliminación y redirige
     * a la vista de confirmación
     *
     * @param e objeto viajes tiquetes actualmente seleccionado
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void confirmDeleteViaje(TblViajesTiquetes e) throws IOException {
        setUsuariosTiquetes(e);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Tiquetes/EmpleadosViajeConfirmDelete.xhtml");
    }

    /**
     * Método que cancela la eliminación del empleado actualmente seleccionado.
     *
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void cancelDelete() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Empleados/EmpleadosList.xhtml");
        usuario = null;
    }
    
     
    
    //método que cancela la eliminación de un estudiante
     public void cancelDeleteEstudiantes() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Empleados/EstudiantesList.xhtml");
        usuario = null;
    }

    /**
     * Método que cancela la eliminación del viaje actualmente seleccionado.
     *
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void cancelDeleteViaje() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Tiquetes/EmpleadosTiquetesList.xhtml");
        empleadosTiquetes = null;
    }

    /**
     * Método que elimina el empleado actualmente seleccionado.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void delete() throws SQLException, InterruptedException, IOException {
        LoginBean log = new LoginBean();
        usuario.setUserMod(log.getDocumentoUserLog());
        usuario.setFechaMod(new Date());
        int a = CrudObject.delete(usuario);
        if (a == 1) {
            listUsuarios.clear();
            listarUsuarios();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Empleados/EmpleadosList.xhtml");
        }
        usuario = null;
        log = null;
    }

    /**
     * Método que elimina el viaje tiquete actualmente seleccionado.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void deleteViaje() throws SQLException, InterruptedException, IOException {
        LoginBean log = new LoginBean();
        empleadosTiquetes.setUserMod(log.getDocumentoUserLog());
        empleadosTiquetes.setFechaMod(new Date());
        int a = CrudObject.delete(empleadosTiquetes);
        if (a == 1) {
            listarUsuariosTiquetes();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Tiquetes/EmpleadosTiquetesList.xhtml");
        }
        empleadosTiquetes = null;
        log = null;
    }

    /**
     * Método que exporta los empleados de la empresa a archivo .xls
     *
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @since incluido desde la version 1.0
     */
    public void exportarXLS() throws IOException, JRException, SQLException {
        if (getListUsuarios().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para Exportar"));
        } else {
            LoginBean log = new LoginBean();
            Map parametros = new HashMap();
            int id_empresa = 0;
            if (log.getIdEmpresa() == 60 && getE() != null) {
                id_empresa = getE().getId_empresa();
            } else {
                id_empresa = log.getIdEmpresa();
            }
            parametros.put("idEmpresa", id_empresa);
            ConexionPool pool = new ConexionPool();
            pool.con = pool.dataSource.getConnection();
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/report1.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, pool.con);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=empleados.xls");
            try (ServletOutputStream stream = response.getOutputStream()) {
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
                exporter.exportReport();
                stream.flush();
            } catch (JRException e) {
                System.out.println("error " + e);
            }
            FacesContext.getCurrentInstance().responseComplete();
        }

    }

    /**
     * Método que exporta las facturas a archivo .xls
     *
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void exportarFacturaXLS() throws IOException, JRException, SQLException {
        if (FacturaVenta.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para Exportar"));
        } else {
            String[] porciones = selectUser.split(" ");
            LoginBean log = new LoginBean();
            Map parametros = new HashMap();
            int id_empresa = 0;
            if (log.getIdEmpresa() == 60 && getE() != null) {
                id_empresa = getE().getId_empresa();
            } else {
                id_empresa = log.getIdEmpresa();
            }
            parametros.put("idEmpresa", id_empresa);
            String report = "/Reports/report4.jasper";
            parametros.put("fechaIni", format2.format(selecFechaIni) + " 00:00:00");
            parametros.put("fechaFin", format2.format(selecFechaFin) + " 23:59:59");
            System.out.println(format.format(selecFechaIni) + "   " + format.format(selecFechaFin));
            if (!porciones[0].equals("")) {
                parametros.put("documento", Integer.parseInt(porciones[0]));
                report = "/Reports/report5.jasper";
            }
            setSelectUser("");
            ConexionPool pool = new ConexionPool();
            pool.con = pool.dataSource.getConnection();
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(report));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, pool.con);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=Factura_de_venta.xls");
            try (ServletOutputStream stream = response.getOutputStream()) {
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
                exporter.exportReport();
                stream.flush();
            }
            FacesContext.getCurrentInstance().responseComplete();
        }
    }

    /**
     * Método que exporta las facturas a archivo .xls desde la vista de
     * administrador
     *
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void exportarFacturaXLSAdmon() throws IOException, JRException, SQLException {
        if (FacturaVenta.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para Exportar"));
        } else {
            Map parametros = new HashMap();
            parametros.put("idEmpresa", getIdEmpresa());
            String report = "/Reports/faturaventaAdmon.jasper";
            parametros.put("fechaIni", format2.format(selecFechaIni) + " 00:00:00");
            parametros.put("fechaFin", format2.format(selecFechaFin) + " 23:59:59");
            if (!selectUser.equals("")) {
                parametros.put("doc", getSelectUser());
                report = "/Reports/faturaventaAdmonDoc.jasper";
            }

            System.out.println("datos report = " + format2.format(selecFechaIni) + "  " + format2.format(selecFechaFin) + "  " + getIdEmpresa() + "  " + getSelectUser());
            ConexionPool pool = new ConexionPool();
            pool.con = pool.dataSource.getConnection();
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(report));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, pool.con);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=Factura_de_venta.xls");
            try (ServletOutputStream stream = response.getOutputStream()) {
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
                exporter.exportReport();
                stream.flush();
            }
            FacesContext.getCurrentInstance().responseComplete();
        }
    }

    /**
     * Método que exporta las facturas a archivo .xls desde la base de datos del
     * sistema anterior.
     *
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @since incluido desde la version 1.0
     */
    public void exportarFcturaOldApp() throws IOException, JRException {
        if (FacturaVentaHistorico.size() > 0) {
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/ReportHistoryOldApp.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), null, new JRBeanCollectionDataSource(FacturaVentaHistorico));
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=FacturaHistorico.xls");
            try (ServletOutputStream stream = response.getOutputStream()) {
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
                exporter.exportReport();
                stream.flush();
            }
            FacesContext.getCurrentInstance().responseComplete();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No hay registros para exportar..!"));
        }
    }

    /**
     * Método que exporta los saldos actuales a archivo .xls
     *
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @since incluido desde la version 1.0
     */
    public void exportarSaldoXLS() throws IOException, JRException {
        if (saldos.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para Exportar"));
        } else {
            String[] porciones = selectUser.split(" ");
            LoginBean log = new LoginBean();
            Map parametros = new HashMap();
            int id_empresa = 0;
            if (log.getIdEmpresa() == 60 && getE() != null) {
                id_empresa = getE().getId_empresa();
            } else {
                id_empresa = log.getIdEmpresa();
            }
            parametros.put("idEmpresa", id_empresa);
            String report = "/Reports/report6.jasper";
            parametros.put("fechaIni", format2.format(selecFechaIni) + " 00:00:00");
            parametros.put("fechaFin", format2.format(selecFechaFin) + " 23:59:59");
            if (!porciones[0].equals("")) {
                parametros.put("documento", Integer.parseInt(porciones[0]));
                report = "/Reports/report7.jasper";
            }
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(report));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, Conexion.conectar());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=Saldos.xls");
            try (ServletOutputStream stream = response.getOutputStream()) {
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
                exporter.exportReport();
                stream.flush();
            }
            FacesContext.getCurrentInstance().responseComplete();
        }
    }

    /**
     * Método que exporta los saldos actuales a archivo .xls desde la vista del
     * administrador.
     *
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @since incluido desde la version 1.0
     */
    public void exportarSaldoXLSAdmon() throws IOException, JRException {
        if (saldos.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para Exportar"));
        } else {
            String[] porciones = selectUser.split(" ");
            Map parametros = new HashMap();
            String report = "/Reports/saldoActualAdmon.jasper";
            parametros.put("fechaIni", format2.format(selecFechaIni));
            parametros.put("fechaFin", format2.format(selecFechaFin));
            if (!porciones[0].equals("")) {
                parametros.put("documento", Integer.parseInt(porciones[0]));
                report = "/Reports/saldoActualId.jasper";
            }
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(report));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, Conexion.conectar());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=Saldos.xls");
            try (ServletOutputStream stream = response.getOutputStream()) {
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
                exporter.exportReport();
                stream.flush();
            }
            FacesContext.getCurrentInstance().responseComplete();
        }
    }

    /**
     * Método que exporta las rutas actuales a partir de la empresa logueada a
     * archivo .xls compatibilidad office 97-2003
     *
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @since incluido desde la version 1.0
     */
    public void exportarRutasXLS() throws IOException, JRException, SQLException {
        LoginBean log = new LoginBean();
        Map parametros = new HashMap();
        int id_empresa = 0;
        if (log.getIdEmpresa() == 60 && getE() != null) {
            id_empresa = getE().getId_empresa();
        } else {
            id_empresa = log.getIdEmpresa();
        }
        ConexionPool pool = new ConexionPool();
        pool.con = pool.dataSource.getConnection();
        parametros.put("idEmpresa", id_empresa);
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/report3.jasper"));
        JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, pool.con);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=codigosderutas.xls");
        try (ServletOutputStream stream = response.getOutputStream()) {
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
            exporter.exportReport();
            stream.flush();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * Método que exporta los empleados que tienen errores al momento de ser
     * cargados desde archivo .xls
     *
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @since incluido desde la version 1.0
     */
    public void exportarLogXLS() throws IOException, JRException {
        if (this.getLogUsers().size() > 0) {
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/LogUsers.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), null, new JRBeanCollectionDataSource(this.getLogUsers()));
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=LogUsuarios.xls");
            try (ServletOutputStream stream = response.getOutputStream()) {
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
                exporter.exportReport();
                stream.flush();
            }
            FacesContext.getCurrentInstance().responseComplete();
            logUsers.clear();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No has Importado un archivo  ó no se generaron errores..!"));
        }
    }

    /**
     * Método que exporta los viajes que tienen errores al momento de ser
     * cargados desde archivo .xls
     *
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @since incluido desde la version 1.0
     */
    public void exportarLogXLSViajes() throws IOException, JRException {
        if (this.getLogTiquetesEmpleado().size() > 0) {
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/LogViajes.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), null, new JRBeanCollectionDataSource(this.getLogTiquetesEmpleado()));
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=LogViajes.xls");
            try (ServletOutputStream stream = response.getOutputStream()) {
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
                exporter.exportReport();
                stream.flush();
            }
            FacesContext.getCurrentInstance().responseComplete();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No has Importado un archivo  ó no se generaron errores..!"));
        }
    }

    /**
     * Método que Importa los empleado desde archivo .xls compatibilidad office
     * 97-2003. Se encarga de leer el archivo local que contiene los registros,
     * una vez lo lee, crea un archivo en el servidor y lo guarda temporalmente,
     * luego lee el archivo temporal y apartir de este realiza las inserciones
     * en la base de datos, al finalizar las validaciones y la insercion de
     * datos el archivo temporal es eliminado.
     *
     * @param event
     * @throws java.io.IOException
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     * @throws java.sql.SQLException
     * @throws java.text.ParseException
     * @throws net.sf.jasperreports.engine.JRException
     * @since incluido desde la version 1.0
     */
    public void handleFileUpload(FileUploadEvent event) throws IOException, InvalidFormatException, SQLException, ParseException, JRException {
        String ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/");
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = event.getFile().getInputstream(); //leemos el fichero local
            // write the inputStream to a FileOutputStream
            fec = fmt.format(new Date());
            outputStream = new FileOutputStream(new File(ruta + "/" + fec + event.getFile().getFileName()));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();
            inputStream.close();
            InputStream input = new FileInputStream(new File(ruta + "/" + fec + event.getFile().getFileName()));
            HSSFWorkbook workbook = new HSSFWorkbook(input);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            int indiceFila = -1;
            ArrayList<String> im = new ArrayList<>();
            while (rowIterator.hasNext()) {
                indiceFila++;
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.iterator();
                String obj = "";
                int indiceColumna = -1;
                String s = ",";
                while (cellIterator.hasNext()) {
                    indiceColumna++;
                    Cell cell = cellIterator.next();
                    if (indiceFila == 0) {
                        System.out.println("columna =" + cell.getStringCellValue());
                    } else if (cell != null) {
                        if (indiceColumna == 4) {
                            s = "";
                        }
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC:
                                obj += Integer.toString((int) cell.getNumericCellValue()) + s;
                                break;
                            case Cell.CELL_TYPE_STRING:
                                obj += cell.getStringCellValue() + s;
                                break;
                        }
                    }
                }
                if (!obj.equals("")) {
                    String[] valid = obj.split(",");
                    if (valid.length == 4) {
                        obj += ",Vacio";
                    }
                    if (valid.length == 3) {
                        obj += ",Vacio,Vacio";
                    }
                    if (valid.length == 2) {
                        obj += ",Vacio,Vacio,Vacio";
                    }
                    if (valid.length == 1) {
                        obj += ",Vacio,Vacio,Vacio,Vacio";
                    }
                    im.add(obj);
                }
            }
            if (im.size() > 0) {
                if (im.size() > 1) {
                    im.stream().forEach((string) -> {
                        String[] parts = string.split(",");
                        ArrayList<ConsultaGeneral> l = new ArrayList<>();
                        LoginBean log = new LoginBean();
                        int id_empresa = 0;
                        if (log.getIdEmpresa() == 60 && getE() != null) {
                            id_empresa = getE().getId_empresa();
                        } else {
                            id_empresa = log.getIdEmpresa();
                        }
                        try {
                            l = (ArrayList) CrudObject.getSelectSql("empleadosHomonimo", 1, parts[0] + "");
                            if (l.size() > 0) {
                                System.out.println("********88888888************");
                                usuario = getUsuarios();
                                usuario.getEmpresaEmpleado().setDocumento(parts[0]);
                                usuario.getEmpresaEmpleado().setIdEmpresa(id_empresa);
                                usuario.getEmpresaEmpleado().setFechaCreacion(new Date());
                                usuario.getEmpresaEmpleado().setCodEstado(38);
                                empleadosInsert2.add(usuario);
                                setUsuarios(null);
                            } else if (parts[0].equals("Vacio") || parts[1].equals("Vacio") || parts[2].equals("Vacio") || parts[3].equals("Vacio") || parts[4].equals("Vacio")) {
                                countok++;
                                usuario = getUsuarios();
                                usuario.setDocumento(parts[0]);
                                usuario.setNombre(parts[1]);
                                usuario.setTelefono(parts[2]);
                                usuario.setCorreo(parts[3]);
                                usuario.setOt(parts[4]);
                                logUsers.add(usuario);
                                setUsuarios(null);
                            } else {
                                usuario = getUsuarios();
                                EmpresaEmpleado empempleado = new EmpresaEmpleado();
                                usuario.setDocumento(parts[0]);
                                usuario.setNombre(parts[1]);
                                usuario.setTelefono(parts[2]);
                                usuario.setCorreo(parts[3]);
                                usuario.setOt(parts[4]);
                                usuario.setUserMod(log.getDocumentoUserLog());
                                usuario.setFechaMod(new Date());
                                usuario.setFechaCreacion(new Date());
                                usuario.setTiquetesPendientes(0);
                                empempleado.setFechaCreacion(new Date());
                                empempleado.setCodEstado(38);
                                empempleado.setDocumento(usuario.getDocumento());
                                empempleado.setIdEmpresa(id_empresa);
                                usuario.setEmpresaEmpleado(empempleado);
                                empleadosInsert.add(usuario);
                                empempleado = null;
                                setUsuarios(null);
                                log = null;
                            }
                        } catch (SQLException ex) {
                            System.out.println("error " + ex);
                        }
                    });
                } else {
                    String[] parts = im.get(0).split(",");
                    usuario = getUsuarios();
                    LoginBean log = new LoginBean();
                    int id_empresa = 0;
                    if (log.getIdEmpresa() == 60) {
                        id_empresa = getE().getId_empresa();
                    } else {
                        id_empresa = log.getIdEmpresa();
                    }
                    EmpresaEmpleado empempleado = new EmpresaEmpleado();
                    usuario.setDocumento(parts[0]);
                    usuario.setNombre(parts[1]);
                    usuario.setTelefono(parts[2]);
                    usuario.setCorreo(parts[3]);
                    usuario.setOt(parts[4]);
                    usuario.setUserMod(log.getDocumentoUserLog());
                    usuario.setFechaMod(new Date());
                    usuario.setFechaCreacion(new Date());
                    usuario.setTiquetesPendientes(0);
                    empempleado.setFechaCreacion(new Date());
                    empempleado.setCodEstado(38);
                    empempleado.setDocumento(usuario.getDocumento());
                    empempleado.setIdEmpresa(id_empresa);
                    usuario.setEmpresaEmpleado(empempleado);
                    empleadosInsert.add(usuario);
                    empempleado = null;
                    setUsuarios(null);
                    log = null;
                }
                input.close();
                File fichero = new File(ruta + "/" + fec + event.getFile().getFileName());
                FileReader fr = new FileReader(fichero);
                try {
                    if (null != fr) {
                        fr.close();
                    }
                    fichero.delete();
                    System.err.println("Eliminado");
                } catch (IOException ex) {
                    System.err.println("Error en el cierre del archivo");
                }
                im.clear();
                try {
                    if (empleadosInsert2.size() > 0) {
                        long a = CrudObject.create2(empleadosInsert2);
                        if (a == 0) {
                            countok++;
                        }
                        empleadosInsert2.clear();
                    }
                    if (empleadosInsert.size() > 0) {
                        long a = CrudObject.create(empleadosInsert);
                        usuario = null;
                        empleadosInsert.clear();
                    }
                } catch (SQLException | ParseException e) {
                    System.out.println("error " + e);
                }
                if (countok == 0) {
                    setMsn("Archivo Importado");
                    FacesMessage message = new FacesMessage("Información", event.getFile().getFileName() + " " + getMsn());
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    listUsuarios.clear();
                    listarUsuarios();
                } else {
                    setMsn("Archivo Importado con " + countok + " errores, por favor compare el log con el archivo original\nes posible que los usuarios ya se enncuentren en el sistema, o hay datos vacios..!");
                    setValidate(true);
                    listUsuarios.clear();
                    listarUsuarios();
                    countok = 0;
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Empleados/EmpleadosList.xhtml");
                }

            } else {
                FacesMessage message = new FacesMessage("Información", event.getFile().getFileName() + "Archivo vacio");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } catch (IOException e) {
            System.out.println("error " + e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }

    }

    
        

    /**
     * Método que obtiene la ruta real de la imagen que sirve como ejemplo para
     * el archivo de carga de empleados
     *
     * @return ruta
     * @since incluido desde la version 1.0
     */
    public String img() {
        String ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Img/Captura.PNG");
        return ruta;
    }

    /**
     * Método que Importa los viajes de empleados desde archivo .xls
     * compatibilidad office 97-2003. Se encarga de leer el archivo local que
     * contiene los registros de tiquetes a subir de empleados, una vez lo lee,
     * crea un archivo en el servidor y lo guarda temporalmente, luego lee el
     * archivo temporal y apartir de este realiza las inserciones en la base de
     * datos, al finalizar las validaciones y la insercion de datos el archivo
     * temporal es eliminado.
     *
     * @param event
     * @throws java.io.IOException
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     * @throws java.sql.SQLException
     * @throws java.text.ParseException
     * @throws net.sf.jasperreports.engine.JRException
     * @since incluido desde la version 1.0
     */
    public void handleFileUploadTiquetes(FileUploadEvent event) throws IOException, InvalidFormatException, SQLException, ParseException, JRException {
        String ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/");
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = event.getFile().getInputstream(); //leemos el fichero local
            // write the inputStream to a FileOutputStream
            fec = fmt.format(new Date());
            outputStream = new FileOutputStream(new File(ruta + "/" + fec + event.getFile().getFileName()));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();
            inputStream.close();
            InputStream input = new FileInputStream(new File(ruta + "/" + fec + event.getFile().getFileName()));
            HSSFWorkbook workbook = new HSSFWorkbook(input);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            int indiceFila = -1;
            ArrayList<String> im = new ArrayList<>();
            while (rowIterator.hasNext()) {
                indiceFila++;
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.iterator();
                String obj = "";
                int indiceColumna = -1;
                String s = ",";
                while (cellIterator.hasNext()) {
                    indiceColumna++;
                    Cell cell = cellIterator.next();
                    if (indiceFila == 0) {
                        System.out.println("columna =" + cell.getStringCellValue());
                    } else if (cell != null) {
                        if (indiceColumna == 6) {
                            s = "";
                        }
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC:
                                obj += Integer.toString((int) cell.getNumericCellValue()) + s;
                                break;
                            case Cell.CELL_TYPE_STRING:
                                obj += cell.getStringCellValue() + s;
                                break;

                        }
                    }
                }
                if (!obj.equals("")) {
                    System.out.println("obj " + obj);
                    String[] valid = obj.split(",");
                    if (valid.length == 6) {
                        obj += ",Vacio";
                    }
                    if (valid.length == 5) {
                        obj += ",Vacio,Vacio";
                    }
                    if (valid.length == 4) {
                        obj += ",Vacio,Vacio,Vacio";
                    }
                    if (valid.length == 3) {
                        obj += ",Vacio,Vacio,Vacio,Vacio";
                    }
                    if (valid.length == 2) {
                        obj += ",Vacio,Vacio,Vacio,Vacio,Vacio";
                    }
                    if (valid.length == 1) {
                        obj += ",Vacio,Vacio,Vacio,Vacio,Vacio,Vacio";
                    }
                    im.add(obj);
                }
            }
            if (im.size() > 0) {
                Iterator<String> it = im.iterator();
                int contadorOk = 0;
                while (it.hasNext()) {
                    LoginBean log = new LoginBean();
                    String temp = it.next();
                    String[] parts = temp.split(",");
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date fechaini = null;
                    Date fechafin = null;
                    boolean fechas = false;
                    try {
                        fechaini = format.parse(parts[2]);
                        fechafin = format.parse(parts[3]);
                    } catch (ParseException e) {
                        System.out.println("error =" + e);
                        fechas = true;
                    }
                    int id_empresa = 0;
                    if (log.getIdEmpresa() == 60 && getE() != null) {
                        id_empresa = getE().getId_empresa();
                    } else {
                        id_empresa = log.getIdEmpresa();
                    }
                    int id_convenio = getIdConvenioByDetalle(Integer.parseInt(parts[1]), id_empresa);
                    ArrayList<ConsultaGeneral> l = new ArrayList<>();
                    l = (ArrayList) CrudObject.getSelectSql("empleadoExistEmpresa", 1, parts[0] + "," + id_empresa);
                    if (l.isEmpty() || parts[6].equals("Vacio") || fechas || id_convenio == 0) {
                        contadorOk++;
                        setValidate(true);
                        getUsuariosTiquetes();
                        empleadosTiquetes.setDocumento(parts[0]);
                        empleadosTiquetes.setDetalleConvPk(Integer.parseInt(parts[1]));
                        empleadosTiquetes.setStrtTquetesAsignados(parts[5]);
                        empleadosTiquetes.setStrfechaInicial(parts[2]);
                        empleadosTiquetes.setStrfechaFinal(parts[3]);
                        empleadosTiquetes.setIdaRegreso(parts[4]);
                        empleadosTiquetes.setStrtTquetesAsignados(parts[5]);
                        empleadosTiquetes.setOs(parts[6]);
                        LogTiquetesEmpleado.add(empleadosTiquetes);
                        l.clear();
                        empleadosTiquetes = null;
                        String addm = "";
                        if (fechas) {
                            addm = " ó que las fechas no tengan el formato texto";
                        }
                        if (id_convenio == 0) {
                            addm += " ó que el codigo de ruta no exista";
                        }
                        setMsn("Archivo Importado con " + contadorOk + " errores, Es posible que el usuario no exista ó este inactivo" + addm);
                        log = null;
                    } else {
                        System.out.println("fechaini " + fechaini);
                        l = (ArrayList) CrudObject.getSelectSql("empleadoTiqPendiente", 1, parts[0] + "," + id_convenio + "");
                        if (l.size() > 0) {
                            getUsuariosTiquetes();
                            empleadosTiquetes.setDocumento(parts[0]);
                            empleadosTiquetes.setDetalleConvPk(Integer.parseInt(parts[1]));
                            long a = CrudObject.edit(empleadosTiquetes);
                            if (a >= 1) {
                                System.out.println("a =" + a);
                            }
                            empleadosTiquetes = null;
                        }
                        getUsuariosTiquetes();
                        empleadosTiquetes.setDocumento(parts[0]);
                        empleadosTiquetes.setIdEmpresa(id_empresa);
                        empleadosTiquetes.setDetalleConvPk(Integer.parseInt(parts[1]));
                        empleadosTiquetes.setIdConvenio(id_convenio);
                        empleadosTiquetes.setFechaInicial(fechaini);
                        empleadosTiquetes.setFechaFinal(fechafin);
                        empleadosTiquetes.setIdaRegreso(parts[4]);
                        empleadosTiquetes.setStrtTquetesAsignados(parts[5]);
                        empleadosTiquetes.setTiquetesEntregados(0);
                        empleadosTiquetes.setUserMod(log.getDocumentoUserLog());
                        empleadosTiquetes.setFechaMod(new Date());
                        empleadosTiquetes.setOs(parts[6]);
                        long a = CrudObject.create(empleadosTiquetes);
                        if (a >= 1) {
                            System.out.println("a =" + a);
                        }
                        empleadosTiquetes = null;
                        log = null;
                        setMsn("Archivo Importado");
                    }

                }
                input.close();
                File fichero = new File(ruta + "/" + fec + event.getFile().getFileName());
                FileReader fr = new FileReader(fichero);
                try {
                    if (null != fr) {
                        fr.close();
                    }
                    fichero.delete();
                    System.err.println("Eliminado");
                } catch (Exception e) {
                    System.err.println("Error en el cierre del archivo");
                }
                im.clear();
//                if (!isValidate()) {
//                    FacesMessage message = new FacesMessage("Información", event.getFile().getFileName() + getMsn());
//                    FacesContext.getCurrentInstance().addMessage(null, message);
//                } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Tiquetes/EmpleadosTiquetesList.xhtml");
//                }
                listUsuarios.clear();
                listarUsuarios();
            } else {
                FacesMessage message = new FacesMessage("Información", event.getFile().getFileName() + "Archivo vacio");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }

        } catch (IOException e) {
            System.out.println("error " + e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }

    }
    //fin subir masivo tiquetes

    public String getEditarUsuario() {
        return EditarUsuario;
    }

    public void setEditarUsuario(String EditarUsuario) {
        this.EditarUsuario = EditarUsuario;
    }

    public List<TblEmpleados> getListUsuarios() {
        return listUsuarios;
    }

    public String getCrearUsuario() {
        usuario = null;
        return Crearusuario;
    }

    public String getCrearTiquetesEmpleados() throws SQLException {
        empleadosTiquetes = null;
        listDetalleConvenio.clear();
        listarAutocomplete();
        listarOrigenDestino();
        return CrearTiquetesEmpleados;
    }

    public String getListUsuario() throws SQLException {
        listUsuarios.clear();
        listarUsuarios();
        return ListUsuario;
    }

    public void setListUsuario(String ListUsuario) {
        this.ListUsuario = ListUsuario;
    }

    public void setCrearusuario(String Crearusuario) {
        this.Crearusuario = Crearusuario;
    }

    public void setListUsuarios(List<TblEmpleados> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    public TblEmpleados getUsuarios() {
        if (usuario == null) {
            usuario = new TblEmpleados();
        }
        return usuario;
    }

    public TblViajesTiquetes getUsuariosTiquetes() {
        if (empleadosTiquetes == null) {
            empleadosTiquetes = new TblViajesTiquetes();
        }
        return empleadosTiquetes;
    }

    public void setUsuariosTiquetes(TblViajesTiquetes empleadosTiquetes) {
        this.empleadosTiquetes = empleadosTiquetes;
    }

    public void setUsuarios(TblEmpleados usuario) {
        this.usuario = usuario;
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public List<Estados> getListEstados() {
        return listEstados;
    }

    public void setListEstados(List<Estados> listEstados) {
        this.listEstados = listEstados;
    }

    public List<Empresas> getListEmpresas() {
        return listEmpresas;
    }

    public void setListEmpresas(List<Empresas> listEmpresas) {
        this.listEmpresas = listEmpresas;
    }

    public List<Roles> getListRoles() {
        return listRoles;
    }

    public void setListRoles(List<Roles> listRoles) {
        this.listRoles = listRoles;
    }

    public List<TblEmpleados> getLogUsers() {
        return logUsers;
    }

    public void setLogUsers(List<TblEmpleados> logUsers) {
        this.logUsers = logUsers;
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

    public String getListUsuariosTiquetes() throws SQLException {
        listarUsuariosTiquetes();
        listarOrigenDestino();
        listarAutocomplete();
        return ListUsuariosTiquetes;
    }

    public List<TblViajesTiquetes> getListTiquetesEmpleado() {
        return listTiquetesEmpleado;
    }

    public void setListTiquetesEmpleado(List<TblViajesTiquetes> listTiquetesEmpleado) {
        this.listTiquetesEmpleado = listTiquetesEmpleado;
    }

    public List<DetalleConvenio> getListDetalleConvenio() {
        return listDetalleConvenio;
    }

    public void setListDetalleConvenio(List<DetalleConvenio> listDetalleConvenio) {
        this.listDetalleConvenio = listDetalleConvenio;
    }

    public boolean isIdavuelta() {
        return idavuelta;
    }

    public void setIdavuelta(boolean idavuelta) {
        this.idavuelta = idavuelta;
    }

    public List<TblViajesTiquetes> getLogTiquetesEmpleado() {
        return LogTiquetesEmpleado;
    }

    public void setLogTiquetesEmpleado(List<TblViajesTiquetes> LogTiquetesEmpleado) {
        this.LogTiquetesEmpleado = LogTiquetesEmpleado;
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
//        System.out.println("selectUser " + selectUser);
        return selectUser;
    }

    public void setSelectUser(String selectUser) {
        this.selectUser = selectUser;
    }

    public List<TblEmpleados> getListUsuariosAutoComplete() {
        return listUsuariosAutoComplete;
    }

    public void setListUsuariosAutoComplete(List<TblEmpleados> listUsuariosAutoComplete) {
        this.listUsuariosAutoComplete = listUsuariosAutoComplete;
    }

    public String getCodigosRurasList() throws SQLException {
        listarCodigosRutas();
        return CodigosRurasList;
    }

    public void setCodigosRurasList(String CodigosRurasList) {
        this.CodigosRurasList = CodigosRurasList;
    }

    public List<DetalleConvenio> getCodigosRutas() {
        return codigosRutas;
    }

    public void setCodigosRutas(List<DetalleConvenio> codigosRutas) {
        this.codigosRutas = codigosRutas;
    }

    public String getCodRuta() {
        return codRuta;
    }

    public void setCodRuta(String codRuta) {
        this.codRuta = codRuta;
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

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public List<TblEmpleados> getFacturaVenta() {
        return FacturaVenta;
    }

    public void setFacturaVenta(List<TblEmpleados> FacturaVenta) {
        this.FacturaVenta = FacturaVenta;
    }

    public String getListFacturaVenta() throws SQLException {
//          facturaVentaList();
        setSelecFechaIni(null);
        setSelecFechaFin(null);
        listarAutocomplete();
        return ListFacturaVenta;
    }

    public String getListSaldosViajes() throws SQLException {
        listarAutocomplete();
        setSelecFechaIni(null);
        setSelecFechaFin(null);
        return ListSaldosViajes;
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

    public List<TblViajesTiquetes> getSaldos() {
        return saldos;
    }

    public void setSaldos(List<TblViajesTiquetes> saldos) {
        this.saldos = saldos;
    }

    public String getListSaldosViajesAdmon() throws SQLException {
        setSelecFechaIni(null);
        setSelecFechaFin(null);
        saldos.clear();
        setSelectUser("");
        return ListSaldosViajesAdmon;
    }

    public void setListSaldosViajesAdmon(String ListSaldosViajesAdmon) {
        this.ListSaldosViajesAdmon = ListSaldosViajesAdmon;
    }

    public String getListFacturaVentaAdmon() throws SQLException {
        setSelecFechaIni(null);
        setSelecFechaFin(null);
        FacturaVenta.clear();
        idEmpresa = 0;
        return ListFacturaVentaAdmon;
    }

    public void setListFacturaVentaAdmon(String ListFacturaVentaAdmon) {
        this.ListFacturaVentaAdmon = ListFacturaVentaAdmon;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getListFacturaVentaAdmonHisrory() throws SQLException {
        setSelecFechaIni(null);
        setSelecFechaFin(null);
        saldos.clear();
        setSelectUser("");
        FacturaVentaHistorico.clear();
        return ListFacturaVentaAdmonHisrory;
    }

    public void setListFacturaVentaAdmonHisrory(String ListFacturaVentaAdmonHisrory) {
        this.ListFacturaVentaAdmonHisrory = ListFacturaVentaAdmonHisrory;
    }

    public List<FacturaHistorico> getFacturaVentaHistorico() {
        return FacturaVentaHistorico;

    }

    public void setFacturaVentaHistorico(List<FacturaHistorico> FacturaVentaHistorico) {
        this.FacturaVentaHistorico = FacturaVentaHistorico;
    }

    public String getEmprsa() {
        return emprsa;
    }

    public void setEmprsa(String emprsa) {
        this.emprsa = emprsa;
    }

    public String getCambiarlave() {
        return Cambiarlave;
    }

    public void setCambiarlave(String Cambiarlave) {
        this.Cambiarlave = Cambiarlave;
    }

    public Usuarios getCurrentUser() {
        if (currentUser == null) {
            currentUser = new Usuarios();
        }
        return currentUser;
    }

    public void setCurrentUser(Usuarios currentUser) {
        this.currentUser = currentUser;
    }

    public String getListFacturaVentaAdmonHisrory2() {
        setSelecFechaIni(null);
        setSelecFechaFin(null);
        saldos.clear();
        setSelectUser("");
        FacturaVentaHistorico.clear();
        return ListFacturaVentaAdmonHisrory2;
    }

    public void setListFacturaVentaAdmonHisrory2(String ListFacturaVentaAdmonHisrory2) {
        this.ListFacturaVentaAdmonHisrory2 = ListFacturaVentaAdmonHisrory2;
    }

    public Empresas getE() {
        return e;
    }

    public void setE(Empresas e) {
        this.e = e;
    }

    public void subirViajes(Empresas e) throws IOException, SQLException {
        setE(e);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Tiquetes/EmpleadosTiquetesList.xhtml");
        empleadosTiquetes = null;
        listDetalleConvenio.clear();
        listarUsuariosTiquetes();
        listarAutocomplete();
        listarOrigenDestino();
    }

    public String getRegeresar() {
        return Regeresar;
    }

    public void setRegeresar(String Regeresar) {
        this.Regeresar = Regeresar;
    }
}
