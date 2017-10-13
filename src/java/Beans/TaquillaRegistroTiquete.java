package Beans;

import Entities.*;
import Modelo.Conexion;
import Modelo.ConexionPool;
import Modelo.ConsultaGeneral;
import Modelo.CrudObject;
import Utils.CiudadesUtils;
import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.List;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
@Named(value = "taquillaRegistroTiquete")
@SessionScoped
public class TaquillaRegistroTiquete implements Serializable {

    /**
     * Variable privada: list_origen. Contendra el listado de origenes
     *
     */
    ArrayList<Ciudad> list_origen = new ArrayList();
    /**
     * Variable privada: list_destino. Contendra el listado de destinos
     *
     */
    ArrayList<Ciudad> list_destino = new ArrayList();

    /**
     * Variable privada: list_empresas. Contendra el listado de empresas
     *
     */
    ArrayList<Empresas> list_empresas = new ArrayList();

    private ArrayList<String> servicio = new ArrayList();
    private ArrayList<String> valorSer = new ArrayList();
    /**
     * Variable privada: temporal. Almacenara el objeto de tiquetes convenio
     * seleccionado actualmente
     */
    private TblusuarioRegistro temporal;
    /**
     * Variable privada: print. Almacenara el objeto de tiquetes convenio
     * seleccionado actualmente. auxiliar para imprimir el ercibo de caja
     */
    private TblusuarioRegistro print;
    /**
     * Variable show_print. temporal para controlar el boton de imprimir el
     * recibo de caja
     */
    boolean show_print = true;
    /**
     * Variable disable. temporal para habilitar o deshabilitar cintroles en la
     * vista
     */
    private boolean disable = true;
    /**
     * Variable disable. disable2 para habilitar o deshabilitar cintroles en la
     * vista
     */
    private boolean disable2 = true;
    /**
     * Variable privada: confirm. auxiliar para controlar que el usuario
     * confirma que el formulario es correcto antes de ser enviado
     */
    private boolean confirm = false;
    private Date fecha;
    private String empresaById;
    private String nomciudadOrigen;
    private String nomciudadDestino;
    private ArrayList<String> datosImprimir = new ArrayList();
    private ArrayList<Estudiantes> list_entregasestudiantes = new ArrayList();
    private ArrayList<Estudiantes> list_universidades = new ArrayList();

    /**
     * Variable: format. Variable para formatear las fechas con hora
     */
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Variable: registroTiquete. Variable para la navegacion vista
     * RegistroTiquete.xhtml
     */
    private String registroTiquete = "../Taquilla/RegistroTiquete.xhtml";
    /**
     * Variable: ListEntrega. Variable para la navegacion vista
     * ListaEntrega.xhtml
     */
    private String ListEntrega = "../Taquilla/ListaEntrega.xhtml";
    /**
     * Variable: ListadoDocumentos. Variable para la navegacion vista
     * ListadoDocumentos.xhtml
     */
    private String ListadoDocumentos = "../Taquilla/ListadoDocumentos.xhtml";

    private String estudiante_universidades = "../Reportes/EstudiantesUniversidades.xhtml";

    private Date selecFechaIni;

    private Date selecFechaFin;

    private String universidad;

    private String selectUser;

    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd");
    /**
     * Variable privada: docs. Contendra el listado de documentos de ayuda para
     * las agencias
     *
     */
    private List<Docs> documentos = new ArrayList();

    private String servTemp = "";

    private int total = 0;

    private int idEmpresa = 0;

    String idAgencia = "";
    String consecutivo = "";

    public TaquillaRegistroTiquete() {

    }

    @PostConstruct
    public void init() {
//        try {
////            HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
////            String url = origRequest.getRequestURL().toString();
////            if (url.contains("RegistroTiquete.xhtml")) {
//                cargarDatos();
//           
//        } catch (SQLException ex) {
//            Logger.getLogger(TaquillaRegistroTiquete.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    /**
     * Método que cargara las ciudades origen y destino para renderizarlos en la
     * vista
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos de la tabla tbl_ciudades
     * @since incluido desde la version 1.0
     */
    public void cargarDatos() throws SQLException {
        list_origen.clear();
        try {
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            l = (ArrayList) CrudObject.getSelectSql("registroTiquete", 1, "nada");
            for (ConsultaGeneral consultaGeneral : l) {
                list_origen.add(new Ciudad(consultaGeneral.getNum1(), consultaGeneral.getStr1()));
            }
            list_destino = (ArrayList<Ciudad>) list_origen.stream().collect(Collectors.toList());
            LoginBean log = new LoginBean();
            if (log.getNomUserLog().contains("Yolanda")) {
                l = (ArrayList) CrudObject.getSelectSql("registroTiquete", 3, "nada");
            } else {
                l = (ArrayList) CrudObject.getSelectSql("registroTiquete", 2, "nada");
            }
            for (ConsultaGeneral consultaGeneral : l) {
                list_empresas.add(new Empresas(consultaGeneral.getNum1(), consultaGeneral.getStr1()));
            }
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        }
    }

    /**
     * No utilizado
     *
     * @param condicion
     * @return
     */
    public double valorEmpresa(int condicion) {
        double valor = 0;
        if (condicion == 1) {
            //Valor del cliente
        } else if (condicion == 2) {
            //valor expal
        }
        return valor;

    }

    /**
     * Método que permite crear convenios para usuarios que no estan cargados
     * desde la empresa
     *
     * @param usermod
     * @param nomuserlog
     * @return
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al crear las los convenios
     * @throws java.text.ParseException
     * @throws java.io.IOException
     * @throws com.itextpdf.text.DocumentException
     * @since incluido desde la version 1.0
     */
    public String agregarTiquete(String usermod, String nomuserlog) throws SQLException, ParseException, DocumentException, IOException, Exception {
        if (!validarTquillaNodum()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No Tienes Permisos Para Entregar Convenios"));
            temporal.setUsuarioNodum(null);
            temporal.setClaveNodum(null);
            temporal.setUsuarioTaquilla("");
            return "RegistroTiquete";
        }
        Date fechaCreacion = new Date();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CiudadesUtils.getSelectSql("empresaById", 1, "" + temporal.getIdEmpresa());
        temporal.setTipocontratotemp(CiudadesUtils.getValorGlobal(temporal.getIdEmpresa()));
        temporal.setFechaCreacion(fechaCreacion);
        temporal.setOt("NA");
        temporal.setUserMod(usermod);
        temporal.setCliente(l.get(0).getStr1());
        temporal.setTaquilla(nomuserlog);
        temporal.setTotal(((temporal.getValor() * temporal.getCantidad())) * (temporal.getIdaRegreso().equalsIgnoreCase("S") ? 2 : 1));
        if (temporal.getTotal() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No Tienes Permisos Para Entregar Convenios"));
            l = null;
            return "RegistroTiquete";
        }
        String[] taquilla = nomuserlog.split(" ");
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
        consecutivo = CiudadesUtils.getConsecutivoAgencia(idAgencia, "consecutivo_convenios");
        temporal.setConfirmRegistro(true);
        temporal.setStrConfirmRegistro("Si");
        temporal.setTransaccion(idAgencia + "" + consecutivo);
        list_origen.stream().forEach((ciudad) -> {
            if (temporal.getCodCiudadOrigen() == ciudad.getCod_ciudad()) {
                temporal.setNomCiudadOrigen(ciudad.getCiudad());
            } else if (temporal.getCodCiudadDestino() == ciudad.getCod_ciudad()) {
                temporal.setNomCiudadDestino(ciudad.getCiudad());
            }
        });
        datosImprimir.add(temporal.getNomCiudadOrigen());//0
        datosImprimir.add(temporal.getNomCiudadDestino());//1
        datosImprimir.add(Integer.toString(temporal.getCodCiudadOrigen()));//2
        datosImprimir.add(Integer.toString(temporal.getCodCiudadDestino()));//3
        datosImprimir.add(temporal.getDocumento());//4
        datosImprimir.add(format.format(temporal.getFechaCreacion()));//5
        datosImprimir.add(Integer.toString(temporal.getIdEmpresa()));//6
        setPrint(temporal);
        return "previewTiquete";
    }

    public String guardatTiquete() throws SQLException, ParseException, IOException {
        long a = CrudObject.create(getPrint());
        if (a >= 1) {
            setTemporal(null);
            setPrint(null);
            servicio.clear();
            servTemp = "Seleccione Servicio";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Viaje Creado"));
        } else if (a == 0) {
            datosImprimir.clear();
            CiudadesUtils.rollBackConsecutivoAgencias(idAgencia, "consecutivo_convenios", consecutivo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error Al crear Tiquete"));
        }
        return "RegistroTiquete";
    }

    /**
     * Método que cargar el listado de documentos de ayuda para las agencias
     *
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void ListarDocumentos() throws SQLException {
        documentos.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CrudObject.getSelectSql("docsHelp", 1, "1");
        for (ConsultaGeneral obj : l) {
            documentos.add(new Docs(obj.getNum1(), obj.getStr1(), obj.getStr2(), obj.getStr3(), obj.getFecha1(), obj.getNum2()));
        }
    }

    /**
     * Método que permite ver el documento de ayuda en una pestaña nueva
     *
     * @param ruta
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void verDocumento(String ruta) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(ruta);
    }

    /**
     * Método que obtiene el valor del convenio a partir del origen y destino,
     * pues ya hay valores estableceidos para cada ruta, no obstante , el valor
     * puede ser modificado, el sistema solo arroja una sugerencia de precio por
     * ruta. Tambien establece los servicios que tiene disponible esa ruta.
     *
     * @param condicion
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al crear los convenios
     * @since incluido desde la version 1.0
     */
    public void listener(int condicion) throws SQLException {
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CiudadesUtils.getSelectSql("valueRuta", 1, "" + temporal.getCodCiudadOrigen() + "," + temporal.getCodCiudadDestino() + "," + temporal.getIdEmpresa() + "");
        if (l.size() > 0) {
            if (condicion == 1) {
                temporal.setValor(l.get(0).getNum2());
                temporal.setTotal(l.get(0).getNum2());
                temporal.setValorSin(l.get(0).getNum1());
                temporal.setTotalsin(l.get(0).getNum1());
            } else if (condicion == 2) {
                temporal.setTotal((temporal.getValor() * temporal.getCantidad()) * (temporal.getIdaRegreso().equalsIgnoreCase("S") ? 2 : 1));
                temporal.setTotalsin((temporal.getValorSin() * temporal.getCantidad()) * (temporal.getIdaRegreso().equalsIgnoreCase("S") ? 2 : 1));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso..!", "La empresa no tiene este convenio, verifique las rutas"));
            temporal.setValor(0);
            temporal.setTotal(0);
            temporal.setValorSin(0);
            temporal.setTotalsin(0);
        }

    }

    public void getRutasweb() throws SQLException {
//        System.out.println("orig " + temporal.getCodCiudadOrigen() + "-- dest" + temporal.getCodCiudadDestino());
        if (temporal.getCodCiudadOrigen() > 0) {
            ArrayList<String> l = new ArrayList<>();
            l = (ArrayList<String>) CiudadesUtils.getRutasWeb(getCiudadById(temporal.getCodCiudadOrigen()), getCiudadById(temporal.getCodCiudadDestino()));
            if (l.size() > 0) {
                getServicio(l);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso..!", "La empresa no tiene este convenio, verifique las rutas"));
                temporal.setValor(0);
                temporal.setTotal(0);
                temporal.setValorSin(0);
                temporal.setTotalsin(0);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso..!", "Seleccione Un Origen Por fvor.!"));
        }

    }

    public void getServicio(ArrayList<String> l) {
        servicio.clear();
        valorSer.clear();
        l.stream().forEach((string) -> {
            String[] prt = string.split(",");
            servicio.add(prt[4]);
            valorSer.add(prt[3]);
        });
    }

    public void getValorServicio(int cond) {
        System.out.println("***" + servTemp + " " + cond);
        int valor = 0;
        for (int i = 0; i < servicio.size(); i++) {
            if (servicio.get(i).equals(servTemp)) {
                valor = Integer.parseInt(valorSer.get(i));
                break;
            }
        }
        System.out.println("valor " + valor);
        temporal.setValor(valor);
        temporal.setTotal(valor);
        temporal.setValorSin(0);
        temporal.setTotalsin(0);

    }

    public void idavuelta() {
        temporal.setTotal(((temporal.getValor() * temporal.getCantidad())) * (temporal.getIdaRegreso().equalsIgnoreCase("S") ? 2 : 1));
        temporal.setTotalsin(0);
    }

    /**
     * Método que obtiene el nombre de una ciudad a partir de su id
     *
     * @param codCiudad
     * @return
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al crear los convenios
     * @since incluido desde la version 1.0
     */
    public String getCiudadById(int codCiudad) throws SQLException {
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        System.out.println("codCiudad " + codCiudad);
        l = (ArrayList) CrudObject.getSelectSql("ciudadById", 1, "" + codCiudad);
        return l.get(0).getStr1();
    }

    /**
     * Método que imprime el recibo de caja al entregar el convenio
     *
     * @param evt
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void imprimir(ActionEvent evt) throws IOException, JRException, SQLException {
//        System.out.println("getDatosImprimir().size() " + getDatosImprimir().size());
        if (getDatosImprimir().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No se ha creado ningun viaje"));
        } else if (getDatosImprimir().get(6) != null) {
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/reportTiquete.jasper"));
            // parametros de entrada
            Map parametros = new HashMap();
            ConexionPool pool = new ConexionPool();
            pool.con = pool.dataSource.getConnection();
            parametros.put("Origen", getDatosImprimir().get(0));
            parametros.put("Destino", getDatosImprimir().get(1));
            parametros.put("cod_origen", Integer.parseInt(getDatosImprimir().get(2)));
            parametros.put("cod_destino", Integer.parseInt(getDatosImprimir().get(3)));
            parametros.put("documento", getDatosImprimir().get(4));
            parametros.put("fecha_creacion", getDatosImprimir().get(5));
            parametros.put("empresa", Integer.parseInt(getDatosImprimir().get(6)));
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
            datosImprimir.clear();
            setPrint(null);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No se ha creado ningun viaje"));
        }

    }

    /**
     * Método que calcula el valor total del convenio a partir de la cantidad de
     * tiquetes
     *
     * @since incluido desde la version 1.0
     */
    public void teclaValor() {
        int mult = 1;
        if (temporal.getIdaRegreso() != null) {
            if (temporal.getIdaRegreso().equalsIgnoreCase("S")) {
                mult = 2;
            }
        }
        temporal.setTotal((temporal.getValor() * temporal.getCantidad()) * mult);
    }

    /**
     * Método que valida si el usuario y la contraseña suministrados por el
     * usuario que entrega el convenio es correcto
     *
     * @return
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public boolean validarTquillaNodum() throws SQLException {
        boolean validar = false;
        String valor = CiudadesUtils.getValidateNodum(temporal.getUsuarioNodum(), temporal.getClaveNodum());
        if (!valor.equals("")) {
            temporal.setUsuarioTaquilla(valor.trim());
            validar = true;
        }
        return validar;
    }

    /**
     * Método que habilita o deshabilita el boton guardar al entregar o
     * convenios
     *
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void confirmarRegistro() throws SQLException {
        if (temporal.isConfirmRegistro()) {
            setDisable(false);
            temporal.setStrConfirmRegistro("Si");
        } else {
            temporal.setStrConfirmRegistro("No");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Debes confirmar los datos del formulario"));
            setDisable(true);
        }
    }

    public TblusuarioRegistro getSelected() {
        if (temporal == null) {
            temporal = new TblusuarioRegistro();
        }
        return temporal;
    }

    public boolean isShow_print() {
        return show_print;
    }

    public TblusuarioRegistro getPrint() {
        if (print == null) {
            print = new TblusuarioRegistro();
        }
        return print;
    }

    /*
    *Método buscarEstudianteUniversidad busca un estudiante a partir 
    *de una fecha inicial, fecha final
    * ó también por documento de identidad
     */
    public void buscarEstudianteUniversidad() throws SQLException {
        if (selecFechaIni != null && selecFechaFin != null) {
            if (!universidad.equals("")) {
                if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc") != null) {
                    setSelectUser(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc"));
                }
                list_entregasestudiantes.clear();
                ArrayList<ConsultaGeneral> l = new ArrayList<>();
                if (getSelectUser().equals("")) {
                    l = (ArrayList) CrudObject.getSelectSql("estudiantefecha", 1, "" + universidad + "," + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59");
                } else {
                    l = (ArrayList) CrudObject.getSelectSql("estudiantefecha2", 1, "" + universidad + "," + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59," + getSelectUser() + "");
                }
                for (ConsultaGeneral obj : l) {
                    Estudiantes e = new Estudiantes();
                    e.getObjEstudiante().setId_entrega(obj.getNum1());
                    e.getObjEstudiante().setDoc_estudiante(obj.getStr1());
                    e.getObjEstudiante().setNumero_tiquete(obj.getStr2());
                    e.getObjEstudiante().setFechaentregaStr(obj.getStr7().substring(1, 19));
                    e.getObjEstudiante().setTaquilla_entrega(obj.getStr3());
                    e.getObjEstudiante().setUsuario_taquilla(obj.getStr4());
                    e.setUniversidad(obj.getStr5());
                    e.setNombre_estudiante(obj.getStr6());
                    list_entregasestudiantes.add(e);
                }
                if (list_entregasestudiantes.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Seleccione Universidad"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Ingrese un rango de fechas"));
        }
    }

    /*
    * Método reporteTiqueteEstudiantes genera un archivo .xls
    * con los datos filtrados por el método buscarEstudianteUniversidad()
     */
    public void reporteTiqueteEstudiantes() throws IOException, JRException, SQLException {
        if (list_entregasestudiantes.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para Exportar"));
        } else {
            Map parametros = new HashMap();
            parametros.put("universidad", getUniversidad());
            String report = "/Reports/tiqueteEstudiante.jasper";
            parametros.put("fechaIni", format2.format(selecFechaIni) + " 00:00:00");
            parametros.put("fechaFin", format2.format(selecFechaFin) + " 23:59:59");
            if (!selectUser.equals("")) {
                parametros.put("documento_estudiante", getSelectUser());
                report = "/Reports/tiqueteEstudianteDocumento.jasper";
            }

            System.out.println("datos report = " + format2.format(selecFechaIni) + "  " + format2.format(selecFechaFin) + "  " + getUniversidad() + "  " + getSelectUser());
            ConexionPool pool = new ConexionPool();
            pool.con = pool.dataSource.getConnection();
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(report));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, pool.con);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=Reporte_Tiquete_Estudiante.xls");
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

    public ArrayList<Ciudad> getList_origen() {
        return list_origen;
    }

    public void setList_origen(ArrayList<Ciudad> list_origen) {
        this.list_origen = list_origen;
    }

    public ArrayList<Ciudad> getList_destino() {
        return list_destino;
    }

    public void setList_destino(ArrayList<Ciudad> list_destino) {
        this.list_destino = list_destino;
    }

    public ArrayList<Empresas> getList_empresas() throws SQLException {
        cargarDatos();
        return list_empresas;
    }

    public void setList_empresas(ArrayList<Empresas> list_empresas) {
        this.list_empresas = list_empresas;
    }

    public void setTemporal(TblusuarioRegistro temporal) {
        this.temporal = temporal;
    }

    public void setPrint(TblusuarioRegistro print) {
        this.print = print;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public boolean isDisable2() {
        return disable2;
    }

    public void setDisable2(boolean disable2) {
        this.disable2 = disable2;
    }

    public String getRegistroTiquete() {
        try {
            cargarDatos();
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        }
        return registroTiquete;
    }

    public void setRegistroTiquete(String registroTiquete) {
        this.registroTiquete = registroTiquete;
    }

    public String getListEntrega() {
        setTemporal(null);
        setPrint(null);
        return ListEntrega;
    }

    public void setListEntrega(String ListEntrega) {
        this.ListEntrega = ListEntrega;
    }

    public void listDocumentos() throws IOException, SQLException {
        ListarDocumentos();
        FacesContext.getCurrentInstance().getExternalContext().redirect(ListadoDocumentos);
    }

    public String getListadoDocumentos() {
        return ListadoDocumentos;
    }

    public void setListadoDocumentos(String ListadoDocumentos) {
        this.ListadoDocumentos = ListadoDocumentos;
    }

    public List<Docs> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Docs> documentos) {
        this.documentos = documentos;
    }

    public ArrayList<String> getServicio() {
        return servicio;
    }

    public void setServicio(ArrayList<String> servicio) {
        this.servicio = servicio;
    }

    public ArrayList<String> getValorSer() {
        return valorSer;
    }

    public void setValorSer(ArrayList<String> valorSer) {
        this.valorSer = valorSer;
    }

    public String getServTemp() {
        return servTemp;
    }

    public void setServTemp(String servTemp) {
        this.servTemp = servTemp;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getEmpresaById(int idempresa) throws SQLException {
        return empresaById = Utils.CiudadesUtils.getEmpresaById(idempresa);
    }

    public String getNomciudadOrigen(int codCiudad) throws SQLException {
        return nomciudadOrigen = Utils.CiudadesUtils.getCiudadByCode(codCiudad);
    }

    public String getNomciudadDestino(int codCiudad) throws SQLException {
        return nomciudadDestino = Utils.CiudadesUtils.getCiudadByCode(codCiudad);
    }

    public String cancelRegistroTiquete() throws SQLException {
        CiudadesUtils.rollBackConsecutivoAgencias(idAgencia, "consecutivo_convenios", consecutivo);
        return "RegistroTiquete";
    }

    public ArrayList<String> getDatosImprimir() {
        return datosImprimir;
    }

    public void setDatosImprimir(ArrayList<String> datosImprimir) {
        this.datosImprimir = datosImprimir;
    }

    public ArrayList<Estudiantes> getList_universidades() {
        return list_universidades;
    }

    public void setList_universidades(ArrayList<Estudiantes> list_universidades) {
        this.list_universidades = list_universidades;
    }

    public String getEstudiante_universidades() throws SQLException {
        list_universidades.clear();
        setList_universidades(CiudadesUtils.listarUniversidades());
        return estudiante_universidades;
    }

    public void setEstudiante_universidades(String estudiante_universidades) {
        this.estudiante_universidades = estudiante_universidades;
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

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getSelectUser() {
        return selectUser;
    }

    public void setSelectUser(String selectUser) {
        this.selectUser = selectUser;
    }

    public ArrayList<Estudiantes> getList_entregasestudiantes() {
        return list_entregasestudiantes;
    }

    public void setList_entregasestudiantes(ArrayList<Estudiantes> list_entregasestudiantes) {
        this.list_entregasestudiantes = list_entregasestudiantes;
    }

}
