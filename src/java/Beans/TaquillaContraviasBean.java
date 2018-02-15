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
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.Iterator;
import java.util.List;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
@Named(value = "taquillaContraviasBean")
@SessionScoped
public class TaquillaContraviasBean implements Serializable {

    /**
     * Variable privada: list_origen. Contendra el listado de las ciudades para
     * el combo origenes
     */
    private String servTemp = "Seleccione Servicio";
    private ArrayList<String> servicio = new ArrayList();
    private ArrayList<String> valorSer = new ArrayList();
    ArrayList<Ciudad> list_origen = new ArrayList();
    private boolean showmesagge = false;

    /**
     * Variable privada: list_destino. Contendra el listado de las ciudades para
     * el combo destinos
     */
    ArrayList<Ciudad> list_destino = new ArrayList();
    /**
     * Variable privada: list_taquillas. Contendra el listado de los usuarios
     * con rol de taquillas
     */
    private ArrayList<Usuarios> list_taquillas = new ArrayList();
    /**
     * Variable privada: servicios. Contendra el listado de servicios o rutas
     * que tiene en convenio cada empresa con rol de taquillas
     */
    ArrayList<String> servicios = new ArrayList();
    /**
     * Variable privada: listContraviasPendientes. Contendra el listado de
     * contravias pendientes para entregar
     */
    private List<TblRegistroContravias> listContraviasPendientes = new ArrayList();
    /**
     * Variable privada: tiquetesToList. Contendra el de tiquetes a entregar
     * para cada empleado
     */
    private List<TblRegistroContravias> tiquetesToList = new ArrayList();
    /**
     * Variable privada: tiquetesCurrrent. Contendra el objeto de contravias
     * seleccionado para agregar a la lista de entrega
     */
    private TblRegistroContravias tiquetesCurrrent;
    
    private TblRegistroContravias print;
    
    String idAgencia = "";
    String consecutivo = "";
    /**
     * Variable privada: temporal. Contendra el objeto de contravias
     * seleccionado para crear contravias
     */
    private TblRegistroContravias temporal;
    /**
     * Variable privada: disable. auxiliar para habilitar y deshabilitar
     * controles en las vistas
     */
    private boolean disable = true;
    /**
     * Variable privada: disable2. auxiliar para habilitar y deshabilitar
     * controles en la vistas
     */
    private boolean disable2 = true;
    /**
     * Variable privada: confirm. auxiliar para controlar que el usuario
     * confirma que el formulario es correcto antes de ser enviado
     */
    private final boolean confirm = false;
    private Date fecha;
    
    private boolean fics = false;
    private boolean pisoStr = false;
    private boolean pisoint = false;
    /**
     * Variable privada: currentTrans. auxiliar temporal para almacenar el id de
     * transaccion que se ha generado para la contravia que se va a generar
     */
    private String currentTrans = "";
    /**
     * Variable: format. Variable para formatear las fechas con hora
     */
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * Variable: format2. Variable para formatear las fechas sin hora
     */
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd");
    /**
     * Variable: registroContravias. Variable para la navegacion. vista
     * RegistroContravias.xhtml
     */
    private String registroContravias = "../Taquilla/RegistroContravias.xhtml";
    /**
     * Variable: ListEntrega. Variable para la navegacion. vista
     * ListaEntrega.xhtml
     */
    private String ListEntrega = "../Taquilla/ListaEntrega.xhtml";
    /**
     * Variable: ListEntregaContravias. Variable para la navegacion. vista
     * ListaEntregaContravias.xhtml
     */
    private String ListEntregaContravias = "../Taquilla/ListaEntregaContravias.xhtml";
    /**
     * Variable: ContraviasTesoreriaList. Variable para la navegacion. vista
     * ContraviasTesoreriaList.xhtml
     */
    private String ContraviasTesoreriaList = "../Tesoreria/ContraviasTesoreriaList.xhtml";
    
    private String ContraviasAuditoriaList = "../Auditoria/ContraviasAuditoriaList.xhtml";
    /**
     * Variable: ContraviasTesoreriaListHistory. Variable para la navegacion.
     * vista ContraviasTesoreriaHistorico.xhtml
     */
    private String ContraviasTesoreriaListHistory = "../Tesoreria/ContraviasTesoreriaHistorico.xhtml";
    
    private String ContraviasAuditoriaListHistory = "../Auditoria/ContraviasAuditoriaHistorico.xhtml";
    /**
     * Variable privada: selectUser auxliar, almacenara el documento del usuario
     * sleccionado actualmente
     */
    private String selectUser = "";
    
    private String trans = "";
    /**
     * Variable privada: noTquete auxliar, almacenara el numero de tiquete que
     * se le entregara al usuario al entregar la contravia
     */
    private String noTquete;
    /**
     * Variable privada: usuarioNodum auxliar, almacenara el usuario que va a
     * entrgar la contravia para luego validar si es correcto o no
     */
    private String usuarioNodum = "";
    private String usuarioTaquillaEntrega = "";
    /**
     * Variable privada: claveNodum auxliar, almacenara la contraseña del
     * usuario para la validacion de sus datos
     */
    private String claveNodum = "";
    /**
     * Variable privada: transContraviaEntrega auxliar, almacenara la
     * transaccion de la contravia a entregar para generar el comprobante
     * entrega de tiquete
     */
    private String transContraviaEntrega = "";
    /**
     * Variable privada: selecFechaIni auxiliar fecha inicial para filtrar datos
     * en un rango de fechas
     */
    private Date selecFechaIni;
    /**
     * Variable privada: selecFechaFin auxiliar fecha fihnal para filtrar datos
     * en un rango de fechas
     */
    private Date selecFechaFin;
    /**
     * Variable privada: agencia auxiliar para filtrar contravias por agencia
     * para tesoreria
     */
    private String agencia = "";
    
    public TaquillaContraviasBean() {
        
    }
    
    @PostConstruct
    public void init() {
//        try {
//            HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            String url = origRequest.getRequestURL().toString();
//            if (url.contains("RegistroContravias.xhtml")) {
//                cargarDatos();
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(TaquillaContraviasBean.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            l = (ArrayList) CrudObject.getSelectSql("registroTiquete", 1, "nada");
            for (ConsultaGeneral consultaGeneral : l) {
                list_origen.add(new Ciudad(consultaGeneral.getNum1(), consultaGeneral.getStr1()));
            }
            list_destino = (ArrayList<Ciudad>) list_origen.stream().collect(Collectors.toList());
            
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        }
    }

    /**
     * Método que cargara las Agencias para renderizarlos en la vista
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos de la tabla usuarios con rol TAQUILLA
     * @since incluido desde la version 1.0
     */
    public void cargarAgencias() throws SQLException {
        try {
            list_taquillas.clear();
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            l = (ArrayList) CiudadesUtils.getSelectSql("taquillas", 1, "nada");
            for (ConsultaGeneral obj : l) {
                list_taquillas.add(new Usuarios(obj.getNum1(), obj.getStr1(), obj.getStr2(), obj.getStr3()));
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
    
    public void getRutasweb() throws SQLException {
        if (!temporal.getOrigen().equals("") && !temporal.getDestino().equals("")) {
            LoginBean log = new LoginBean();
            if (!log.getNomUserLog().contains(temporal.getOrigen().toUpperCase())) {
                if (!temporal.getOrigen().equals(temporal.getDestino())) {
                    ArrayList<String> l = new ArrayList<>();
                    l = (ArrayList<String>) CiudadesUtils.getRutasWeb(temporal.getOrigen(), temporal.getDestino());
//                    System.out.println("**********" + l.size());
                    if (l.size() > 0) {
                        getServicio(l);
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso..!", "No hay servicios para esta ruta"));
                        temporal.setValor(0);
                        temporal.setTotal(0);
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso..!", "El origen y destino no deben ser iguales"));
                    temporal.setOrigen("");
                    temporal.setDestino("");
                    temporal.setCant_tiquetes(1);
                    temporal.setValor(0);
                    temporal.setTotal(0);
                    servicios.clear();
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso..!", "Lo que intentas vender no es una contravia, revisa el origen"));
                temporal.setOrigen("");
                temporal.setDestino("");
                temporal.setCant_tiquetes(1);
                temporal.setValor(0);
                temporal.setTotal(0);
                servicios.clear();
            }
            
        } else {
            temporal.setOrigen("");
            temporal.setDestino("");
            temporal.setCant_tiquetes(1);
            temporal.setValor(0);
            temporal.setTotal(0);
            servicios.clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso..!", "Seleccione Un Origen Por fvor.!"));
        }
        
    }
    
    public void getServicio(ArrayList<String> l) {
        servicio.clear();
        valorSer.clear();
        l.stream().forEach((string) -> {
            String[] prt = string.split(",");
            if (!servicio.contains(prt[4].trim())) {
                servicio.add(prt[4]);
                valorSer.add(prt[3]);
                System.out.println(prt[4] + " " + prt[3]);
            }
        });
    }
    
    public void getValorServicio(int cond) {
        System.out.println("***" + servTemp.trim() + " " + cond);
        int valor = 0;
        if (servicio.contains(servTemp)) {
            valor = Integer.parseInt(valorSer.get(servicio.indexOf(servTemp)));
        }
        if (cond == 1) {
            if (servTemp.contains("piso")) {
                String sSubCadena = servTemp.substring(servTemp.length() - 1);
                temporal.setPiso(Integer.parseInt(sSubCadena));
            } else {
                temporal.setPiso(1);
            }
            if (servTemp.contains("Mettro Salida desde")) {
                servTemp = "Mettro";
            }
            System.out.println("servTemp " + servTemp);
            temporal.setValor(valor);
            temporal.setTotal(valor);
            temporal.setServicio(servTemp);
        }
    }
    
    public String getCiudadById(int codCiudad) throws SQLException {
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        System.out.println("codCiudad " + codCiudad);
        l = (ArrayList) CrudObject.getSelectSql("ciudadById", 1, "" + codCiudad);
        return l.get(0).getStr1();
    }

    /**
     * Método que permite crear contravias
     *
     * @return
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al crear las contravias
     * @throws java.text.ParseException
     * @throws java.io.IOException
     * @throws com.itextpdf.text.DocumentException
     * @since incluido desde la version 1.0
     */
    public String agregarTiquete() throws SQLException, ParseException, DocumentException, IOException, Exception {
        if (!validarTquillaNodum()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No Tienes Permisos Para Entregar Convenios"));
            temporal.setUserNodumVende(null);
            temporal.setClaveNodum(null);
            temporal.setUsuarioTaquilla_vende("");
            return "RegistroContravias";
        }
        if ((temporal.getValor() * temporal.getCant_tiquetes()) == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "El valor no puede ser $0"));
            return "RegistroContravias";
        }
        LoginBean log = new LoginBean();
        Date fechaCreacion = new Date();
        temporal.setId_empresa(log.getIdEmpresa());
        temporal.setFecha_creacion(fechaCreacion);
        temporal.setUserMod(log.getDocumentoUserLog());
        temporal.setTaquilla_vende(log.getNomUserLog());
        temporal.setIda_regreso("No");
        temporal.setNo_tiquete(null);
        temporal.setEstado("Pendiente");
        temporal.setTotal(temporal.getValor() * temporal.getCant_tiquetes());
        temporal.setReimprimir(0);
        temporal.setDineroEnCasa(0);
        String[] taquilla = log.getNomUserLog().split(" ");
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
            case "CALL":
                idAgencia = "COL";
                break;
            case "SOACHA":
                idAgencia = "SO";
                break;
        }
        consecutivo = CiudadesUtils.getConsecutivoContravias(idAgencia);
        temporal.setTransaccion(idAgencia + "" + consecutivo);
        setPrint(temporal);
        setTrans(idAgencia + "" + consecutivo);
        return "previewTiqueteContravia";
    }
    
    public void guardatTiquete() throws SQLException, ParseException, IOException {
        long a = CrudObject.create(getPrint());
        if (a >= 1) {
            setTemporal(null);
            setDisable2(true);
            setPrint(null);
            servicios.clear();
            servicio.clear();
            servTemp = "Seleccione Servicio";
            System.out.println("trans = " + getTrans());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Contravia Creada"));
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Taquilla/RegistroContravias.xhtml");
            setShowmesagge(true);
        } else if (a == 0) {
            CiudadesUtils.rollBackConsecutivoAgencias(idAgencia, "consecutivo_contravias", consecutivo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso..!", "Error Creando Contravia"));
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Taquilla/previewTiqueteContravia.xhtml");
            setShowmesagge(false);
        }        
    }
    
    public void ChangeValMessage() {
        setShowmesagge(false);
    }

    /**
     * Método que obtiene el valor de la contravia a partir del origen y
     * destino, pues ya hay valores estableceidos para cada ruta, no obstante ,
     * el valor puede ser modificado, el sistema solo arroja una sugerencia de
     * precio por ruta. Tambien establece los servicios que tiene disponible esa
     * ruta.
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al crear las contravias
     * @since incluido desde la version 1.0
     */
    public void listener() throws SQLException {
        if (!temporal.getOrigen().equals("") && !temporal.getDestino().equals("")) {
            LoginBean log = new LoginBean();
            if (!log.getNomUserLog().contains(temporal.getOrigen().toUpperCase())) {
                if (!temporal.getOrigen().equals(temporal.getDestino())) {
                    int valor = CiudadesUtils.getValorRuta(temporal.getOrigen(), temporal.getDestino());
                    temporal.setValor(valor);
                    temporal.setTotal((temporal.getValor() * temporal.getCant_tiquetes()));
                    servicios.clear();
                    Iterator<String> itr = CiudadesUtils.getServicios(temporal.getOrigen(), temporal.getDestino()).iterator();
                    while (itr.hasNext()) {
                        String next = itr.next();
                        servicios.add(next);
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso..!", "El origen y destino no deben ser iguales"));
                    temporal.setOrigen("");
                    temporal.setDestino("");
                    temporal.setCant_tiquetes(1);
                    temporal.setValor(0);
                    temporal.setTotal(0);
                    servicios.clear();
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso..!", "Lo que intentas vender no es una contravia, revisa el origen"));
                temporal.setOrigen("");
                temporal.setDestino("");
                temporal.setCant_tiquetes(1);
                temporal.setValor(0);
                temporal.setTotal(0);
                servicios.clear();
            }
            
        } else {
            temporal.setOrigen("");
            temporal.setDestino("");
            temporal.setCant_tiquetes(1);
            temporal.setValor(0);
            temporal.setTotal(0);
            servicios.clear();
        }
        
    }

    /**
     * Método que permite imprimir el recibo de caja al vender la contravia
     *
     * @param evt
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void imprimir(ActionEvent evt) throws IOException, JRException, SQLException {
//        System.out.println("trans = " + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("transContravia"));
//        setTrans("MD000012");
        System.out.println("hola imprimir");
        if (getTrans().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No se ha creado una contravia"));
        } else if (!getTrans().equals("")) {
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/reciboCajaContravia.jasper"));
            //parametros de entrada
            Map parametros = new HashMap();
            ConexionPool pool = new ConexionPool();
            pool.con = pool.dataSource.getConnection();
            Connection c = pool.con;
            parametros.put("transaccion", getTrans());
            byte[] jp = JasperRunManager.runReportToPdf(jasper.getPath(), parametros, c);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//            response.setHeader("Content-Disposition", "attachment; filename=reciboCajaContravia.pdf");
            response.setContentType("application/pdf");
            response.setContentLength(jp.length);
            try (ServletOutputStream outStream = response.getOutputStream()) {
                outStream.write(jp, 0, jp.length);
                outStream.flush();
                outStream.close();
            }
            FacesContext.getCurrentInstance().responseComplete();
            setTrans("");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No se ha creado una contravia"));
            setTrans("");
        }
        
    }

    /**
     * Método que permite imprimir el comprobante de entrega al descargar una
     * contravia
     *
     * @param evt
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void imprimirEntrega(ActionEvent evt) throws IOException, JRException, SQLException {
        Map parametros = new HashMap();
        ConexionPool pool = new ConexionPool();
        pool.con = pool.dataSource.getConnection();
        File jasper = null;
        String transaccion = "";
        String msn = "";
        if (isFics()) {
            if (!listContraviasPendientes.isEmpty()) {
                transaccion = listContraviasPendientes.get(0).getNo_tiquete();
                jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/compEntregaContraviaFics.jasper"));
            }
        }
        if (!getTransContraviaEntrega().equals("")) {
            transaccion = getTransContraviaEntrega();
            jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/compEntregaContravia.jasper"));
        }
        if (!transaccion.equals("")) {
            parametros.put("transaccion", transaccion);
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
            setTransContraviaEntrega("");
            transaccion = "";
            setFics(false);
            listContraviasPendientes.clear();
        } else {
            if (isFics()) {
                msn = "No hay registros de contravias fics.";
            } else {
                msn = "No has entregado una contravia.";
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", msn));
        }
        
    }

    /**
     * Método que calcula el valor total de la contravia a partir de la cantidad
     * de tiquetes
     *
     * @since incluido desde la version 1.0
     */
    public void teclaValor() {
        temporal.setTotal(temporal.getValor() * temporal.getCant_tiquetes());
    }

    /**
     * Método que valida si el usuario y la contraseña suministrados por el
     * usuario que vende la contravia es correcto
     *
     * @return
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public boolean validarTquillaNodum() throws SQLException {
        boolean validar = false;
        String valor = CiudadesUtils.getValidateNodum(temporal.getUserNodumVende(), temporal.getClaveNodum());
        if (!valor.equals("")) {
            temporal.setUsuarioTaquilla_vende(valor.trim());
            validar = true;
        }
        return validar;
    }

    /**
     * Método que valida si el usuario y la contraseña suministrados por el
     * usuario que entrega la contravia es correcto
     *
     * @return
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public boolean validarTquillaNodumEntrega() throws SQLException {
        boolean validar = false;
        String valor = CiudadesUtils.getValidateNodum(usuarioNodum, claveNodum);
        if (!valor.equals("")) {
            if (usuarioNodum != null && claveNodum != null) {
                setUsuarioTaquillaEntrega(valor.trim());
                validar = true;
            }
        }
        return validar;
    }

    /**
     * Método que permite entregar las contravias
     *
     * @param usermod
     * @param nomuserlog
     * @return
     * @throws java.sql.SQLException
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     * @throws java.text.ParseException
     * @since incluido desde la version 1.0
     */
    public String entregarContravia(String usermod, String nomuserlog) throws SQLException, InterruptedException, IOException, ParseException {
//            System.out.println("user "+usuarioNodum+" "+claveNodum);
        if (!validarTquillaNodumEntrega()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso..!", "No Tienes Permisos Para Entregar Convenios"));
            getTiquetesCurrrent().setUsuarioTaquillaEntrega("");
            getTiquetesCurrrent().setUserNodumEntrega("");
            setUsuarioNodum("");
            setClaveNodum("");
            return "ListaEntregaContravias";
        }
        if (getTiquetesToList().size() > 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso..!", "Solo puedes entregar una contravia a la vez, busca de nuevo la contravia...!"));
            getTiquetesToList().clear();
            listContraviasPendientes.clear();
            return "ListaEntregaContravias";
        }
        if (getTiquetesToList().size() > 0) {
            if ((!getUsuarioNodum().equals("") || getUsuarioNodum() != null) || (!getClaveNodum().equals("") || getClaveNodum() != null)) {
                Iterator<TblRegistroContravias> it = getTiquetesToList().iterator();
                int counterror = 0;
                while (it.hasNext()) {
                    TblRegistroContravias next = it.next();
                    if (next.getNo_tiquete().equals("")) {
                        counterror += 1;
                    }
//                    System.out.println("tiq = "+next.toString());
                }
                if (counterror == 0) {
                    getTiquetesToList().get(0).setUserNodumEntrega(usuarioNodum);
                    getTiquetesToList().get(0).setUsuarioTaquillaEntrega(getUsuarioTaquillaEntrega());
                    getTiquetesToList().get(0).setFecha_entrega(new Date());
                    getTiquetesToList().get(0).setTaquilla_entrega(nomuserlog);
                    setTransContraviaEntrega(getTiquetesToList().get(0).getTransaccion());
                    long a = CrudObject.editEntregaContravia(getTiquetesToList());
                    if (a >= 1) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Transaccion exitosa"));
                        tiquetesToList.clear();
                        listContraviasPendientes.clear();
                        setSelectUser("");
                        setUsuarioNodum("");
                        setClaveNodum("");
                        setDisable(true);
                        setDisable2(true);
                        setTiquetesCurrrent(null);
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "El Número de tiquete no debe estar vacio..!"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El usuario y la clave no debe estar vacios..!"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "No has seleccionado nada para entregar..!"));
        }
        return "ListaEntregaContravias";
    }

    /**
     * Método que habilita o deshabilita el boton guardar al entregar o vender
     * las contravias
     *
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void confirmarRegistro() throws SQLException {
        if (temporal.isConfirmRegistro()) {
            setDisable(false);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Debes confirmar los datos del formulario"));
            setDisable(true);
        }
    }

    /**
     * Método que cargara las contravias pendientes para entregar a un usuario
     * especifico
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos de la bd
     * @since incluido desde la version 1.0
     */
    public void listContraviasEntrega() throws SQLException {
        listContraviasPendientes.clear();
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectUser") != null) {
            setSelectUser(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectUser"));
        }
        
        LoginBean log = new LoginBean();
        String[] parts = log.getDocumentoUserLog().split(" ");
        String filtroOrigen = "";
        if (parts.length > 1) {
            filtroOrigen = parts[0];
        } else {
            filtroOrigen = log.getDocumentoUserLog();
        }
        if (!selectUser.equals("")) {
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            if (isFics()) {
                l = (ArrayList) CrudObject.getSelectSql("contraviaPendFics", 1, "" + selectUser + "");
                for (ConsultaGeneral obj : l) {//TblRegistroContravias
                    listContraviasPendientes.add(new TblRegistroContravias(obj.getStr1(),
                            obj.getStr2(),
                            obj.getStr3(),
                            obj.getStr4(),
                            obj.getStr5(),
                            obj.getStr6(),
                            obj.getStr7(),
                            obj.getStr8(),
                            obj.getNum1(),
                            obj.getNum2(),
                            obj.getStr9(),
                            obj.getStr10(),
                            obj.getStr11(),
                            obj.getStr12(),
                            obj.getStr13()));
                    setPisoStr(true);
                    setPisoint(false);
                }
            } else {
                l = (ArrayList) CrudObject.getSelectSql("contraviaPendiente", 1, "" + selectUser + "," + filtroOrigen + "");
                for (ConsultaGeneral obj : l) {//TblRegistroContravias
                    listContraviasPendientes.add(new TblRegistroContravias(obj.getStr1(),
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
                    setPisoStr(false);
                    setPisoint(true);
                }
            }
            if (listContraviasPendientes.isEmpty()) {
                setDisable2(true);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
            }
            setUsuarioNodum("");
            setClaveNodum("");
            setSelectUser("");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Ingrese Numero de documento o Número tiquete Fics"));
        }
    }
    //fin contravias pendientes

    /**
     * Método que cargara las contravias para el control de tesoreria
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos de la tabla de contravias
     * @since incluido desde la version 1.0
     */
    public void listContraviasTesoria() throws SQLException {
        if (selecFechaIni != null && selecFechaFin != null) {
            listContraviasPendientes.clear();
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            if (!agencia.equals("")) {
                l = (ArrayList) CrudObject.getSelectSql("contraviaTesoreriaId", 1, "" + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59" + "," + agencia + "");
            } else {
                l = (ArrayList) CrudObject.getSelectSql("contraviaTesoreria", 1, "" + format2.format(selecFechaIni) + " 00:00:00," + format2.format(selecFechaFin) + " 23:59:59" + "");
            }
            for (ConsultaGeneral obj : l) {//TblRegistroContravias
                listContraviasPendientes.add(new TblRegistroContravias(obj.getStr1(),
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
                        obj.getFecha2(), obj.getStr15(), obj.getNum4(), (obj.getNum4() > 0)));
            }
            
            if (listContraviasPendientes.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Seleccione un rango de fechas"));
        }
        
    }

    /**
     * Método que cargara las contravias desde el aplicativo anterior para el
     * control de tesoreria
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos de la tabla de contravias
     * @since incluido desde la version 1.0
     */
    public void listContraviasTesoriaHistoric() throws SQLException {
        if (selecFechaIni != null && selecFechaFin != null) {
            listContraviasPendientes.clear();
            listContraviasPendientes = CiudadesUtils.getHistoricoContravias(selecFechaIni, selecFechaFin, agencia);
            if (listContraviasPendientes.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Seleccione un rango de fechas"));
        }
        
    }

    /**
     * Método que permite agregar contravias a entregar o quitar contravias a
     * entregar
     *
     * @param tiquete
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos de la tabla de contravias
     * @since incluido desde la version 1.0
     */
    public void addTiqueteToList(TblRegistroContravias tiquete) throws SQLException {
        setTiquetesCurrrent(tiquete);
//        getTiquetesCurrrent().setNo_tiquete(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tiquete"));
        if (!tiquete.getNo_tiquete().equals("")) {
            if (!tiquetesToList.contains(tiquete)) {
                tiquetesToList.add(tiquete);
                getTiquetesCurrrent().setSelected(true);
                setDisable2(false);
                setUsuarioNodum("");
                setClaveNodum("");
                getTiquetesCurrrent().setUsuarioTaquillaEntrega("");
            } else {
                tiquetesToList.remove(tiquete);
                if (!tiquete.getNo_tiquete().equals("")) {
                    tiquetesToList.add(tiquete);
                    getTiquetesCurrrent().setSelected(true);
                    setDisable2(false);
                    setUsuarioNodum("");
                    setClaveNodum("");
                    getTiquetesCurrrent().setUsuarioTaquillaEntrega("");
                }
            }
            
            tiquetesToList.forEach((next) -> {
                System.out.println("next " + next.getNo_tiquete());
            });
            
        } else if (getTiquetesCurrrent().isSelected()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "El numero de tiquete no debe estar vacio"));
        }
    }

    /**
     * Método que permite establecer si el dinero de las contravias ya fue
     * recaudado
     *
     * @param tiquete
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos de la tabla de contravias
     * @since incluido desde la version 1.0
     */
    public void recaudarMoney(TblRegistroContravias tiquete) throws SQLException {
        setTiquetesCurrrent(tiquete);
        try {
            long a = CrudObject.edit(getTiquetesCurrrent());
            System.out.println("a" + a);
            listContraviasTesoria();
            if (a > 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Se ha actualizado el registro"));
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        }
        
    }
    
    public void setDetalle(TblRegistroContravias c) {
        setTiquetesCurrrent(c);
        //System.out.println("*********** "+tiquetesCurrrent.getTransaccion());
    }

    /**
     * Método que permite establecer si el dinero de las contravias ya fue
     * recaudado desde el aplicativo viejo
     *
     * @param tiquete
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos de la tabla de contravias
     * @since incluido desde la version 1.0
     */
    public void recaudarMoneyOld(TblRegistroContravias tiquete) throws SQLException {
        try {
            long a = CiudadesUtils.recaudarMoneyold(tiquete);
            listContraviasTesoriaHistoric();
            if (a > 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Se ha actualizado el registro"));
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        }
        
    }

    /**
     * Método que permite saber si una contravia a entregar ya esta agregada o
     * no.
     *
     * @param l
     * @param id
     * @return
     * @since incluido desde la version 1.0
     */
    public boolean existEnArray(List<TblRegistroContravias> l, String id) {
        boolean saber = false;
        Iterator<TblRegistroContravias> lpt = l.iterator();
        while (lpt.hasNext()) {
            TblRegistroContravias borrar = lpt.next();
            if (borrar.getTransaccion().equals(id)) {
                saber = true;
                break;
            }
        }
        return saber;
    }

    /**
     * Método no usado.
     *
     * @param l
     * @param id
     * @return
     * @since incluido desde la version 1.0
     */
    public int indiceDato(List<TblRegistroContravias> l, String id) {
        int j = 0;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getTransaccion().equals(id)) {
                j = i;
                break;
            }
        }
        return j;
    }

    /**
     * Método que permite eliminar contravia a entregar si ya esta agregada.
     *
     * @param l
     * @param id
     * @return
     * @since incluido desde la version 1.0
     */
    public boolean eliminardelarray(List<TblRegistroContravias> l, String id) {
        boolean eliminado = false;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getTransaccion().equals(id)) {
                l.remove(i);
                eliminado = true;
            }
        }
        return eliminado;
    }

    /**
     * Método que exportar las contravias para el control de tesoreria en
     * archivo .xls compatibilidad office 97-2003.
     *
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @since incluido desde la version 1.0
     */
    public void exportarTesoreria() throws IOException, JRException {
        if (listContraviasPendientes.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para Exportar"));
        } else {
            String report = "";
            Map parametros = new HashMap();
            if (!agencia.equals("")) {
                report = "/Reports/ContraviasTesoreriaId.jasper";
                parametros.put("agencia", agencia);
            } else {
                report = "/Reports/ContraviasTesoreria.jasper";
            }
            parametros.put("fechaIni", format2.format(selecFechaIni) + " 00:00:00");
            parametros.put("fechaFin", format2.format(selecFechaFin) + " 23:59:59");
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(report));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, Conexion.conectar());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=ContraviasTesoreria.xls");
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
    }

    /**
     * Método que exportar las contravias para el control de tesoreria en
     * archivo .xls compatibilidad office 97-2003. desde el aplicativo viejo.
     *
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     * @since incluido desde la version 1.0
     */
    public void exportarContraviasOldApp() throws IOException, JRException {
        if (listContraviasPendientes.size() > 0) {
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/ReportHistoryContra.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), null, new JRBeanCollectionDataSource(listContraviasPendientes));
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=ContraviasHistorico.xls");
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
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No hay registros para exportar..!"));
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
    
    public String getRegistroContravias() {
        servicio.clear();
        try {
            cargarDatos();
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        }
        return registroContravias;
    }
    
    public void setRegistroContravias(String registroContravias) {
        this.registroContravias = registroContravias;
    }
    
    public TblRegistroContravias getTemporal() {
        if (temporal == null) {
            temporal = new TblRegistroContravias();
        }
        return temporal;
    }
    
    public void setTemporal(TblRegistroContravias temporal) {
        this.temporal = temporal;
    }
    
    public ArrayList<String> getServicios() {
        return servicios;
    }
    
    public void setServicios(ArrayList<String> servicios) {
        this.servicios = servicios;
    }
    
    public String getCurrentTrans() {
        return currentTrans;
    }
    
    public void setCurrentTrans(String currentTrans) {
        this.currentTrans = currentTrans;
    }
    
    public String getListEntrega() {
        setTemporal(null);
        setCurrentTrans("");
        servicio.clear();
        return ListEntrega;
    }
    
    public void setListEntrega(String ListEntrega) {
        this.ListEntrega = ListEntrega;
    }
    
    public String getSelectUser() {
        return selectUser;
    }
    
    public void setSelectUser(String selectUser) {
        this.selectUser = selectUser;
    }
    
    public List<TblRegistroContravias> getListContraviasPendientes() {
        return listContraviasPendientes;
    }
    
    public void setListContraviasPendientes(List<TblRegistroContravias> listContraviasPendientes) {
        this.listContraviasPendientes = listContraviasPendientes;
    }
    
    public String getNoTquete() {
        return noTquete;
    }
    
    public void setNoTquete(String noTquete) {
        this.noTquete = noTquete;
    }
    
    public TblRegistroContravias getTiquetesCurrrent() {
        if (tiquetesCurrrent == null) {
            tiquetesCurrrent = new TblRegistroContravias();
        }
        return tiquetesCurrrent;
    }
    
    public void setTiquetesCurrrent(TblRegistroContravias tiquetesCurrrent) {
        this.tiquetesCurrrent = tiquetesCurrrent;
    }
    
    public List<TblRegistroContravias> getTiquetesToList() {
        return tiquetesToList;
    }
    
    public void setTiquetesToList(List<TblRegistroContravias> tiquetesToList) {
        this.tiquetesToList = tiquetesToList;
    }
    
    public String getListEntregaContravias() {
        setTiquetesCurrrent(null);
        getListContraviasPendientes().clear();
        setFics(false);
        return ListEntregaContravias;
    }
    
    public void setListEntregaContravias(String ListEntregaContravias) {
        this.ListEntregaContravias = ListEntregaContravias;
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
    
    public String getTransContraviaEntrega() {
        return transContraviaEntrega;
    }
    
    public void setTransContraviaEntrega(String transContraviaEntrega) {
        this.transContraviaEntrega = transContraviaEntrega;
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
    
    public String getContraviasTesoreriaList() throws SQLException {
        setSelecFechaFin(null);
        setSelecFechaIni(null);
        setAgencia("");
        cargarAgencias();
        listContraviasPendientes.clear();
        return ContraviasTesoreriaList;
    }
    
    public void setContraviasTesoreriaList(String ContraviasTesoreriaList) {
        this.ContraviasTesoreriaList = ContraviasTesoreriaList;
    }
    
    public ArrayList<Usuarios> getList_taquillas() {
        return list_taquillas;
    }
    
    public void setList_taquillas(ArrayList<Usuarios> list_taquillas) {
        this.list_taquillas = list_taquillas;
    }
    
    public String getAgencia() {
        return agencia;
    }
    
    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }
    
    public String getContraviasTesoreriaListHistory() throws SQLException {
        setSelecFechaFin(null);
        setSelecFechaIni(null);
        setAgencia("");
        cargarAgencias();
        listContraviasPendientes.clear();
        return ContraviasTesoreriaListHistory;
    }
    
    public void setContraviasTesoreriaListHistory(String ContraviasTesoreriaListHistory) {
        this.ContraviasTesoreriaListHistory = ContraviasTesoreriaListHistory;
    }
    
    public String getContraviasAuditoriaList() throws SQLException {
        setSelecFechaFin(null);
        setSelecFechaIni(null);
        setAgencia("");
        cargarAgencias();
        listContraviasPendientes.clear();
        return ContraviasAuditoriaList;
    }
    
    public void setContraviasAuditoriaList(String ContraviasAuditoriaList) {
        this.ContraviasAuditoriaList = ContraviasAuditoriaList;
    }
    
    public String getContraviasAuditoriaListHistory() throws SQLException {
        setSelecFechaFin(null);
        setSelecFechaIni(null);
        setAgencia("");
        cargarAgencias();
        listContraviasPendientes.clear();
        return ContraviasAuditoriaListHistory;
    }
    
    public void setContraviasAuditoriaListHistory(String ContraviasAuditoriaListHistory) {
        this.ContraviasAuditoriaListHistory = ContraviasAuditoriaListHistory;
    }
    
    public String getServTemp() {
        return servTemp;
    }
    
    public void setServTemp(String servTemp) {
        this.servTemp = servTemp;
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
    
    public TblRegistroContravias getPrint() {
        return print;
    }
    
    public void setPrint(TblRegistroContravias print) {
        this.print = print;
    }
    
    public String cancelRegistroTiquete() throws SQLException {
        CiudadesUtils.rollBackConsecutivoAgencias(idAgencia, "consecutivo_contravias", consecutivo);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("contravia");
        return "RegistroContravias";
    }
    
    public String existeVarSession() {
        String v = "_self";
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("contravia") != null) {
            v = "_blank";
        }
        return v;
    }
    
    public String getTrans() {
        return trans;
    }
    
    public void setTrans(String trans) {
        this.trans = trans;
    }
    
    public boolean isFics() {
        return fics;
    }
    
    public void setFics(boolean fics) {
        this.fics = fics;
    }
    
    public boolean isPisoStr() {
        return pisoStr;
    }
    
    public void setPisoStr(boolean pisoStr) {
        this.pisoStr = pisoStr;
    }
    
    public boolean isPisoint() {
        return pisoint;
    }
    
    public void setPisoint(boolean pisoint) {
        this.pisoint = pisoint;
    }
    
    public String getUsuarioTaquillaEntrega() {
        return usuarioTaquillaEntrega;
    }
    
    public void setUsuarioTaquillaEntrega(String usuarioTaquillaEntrega) {
        this.usuarioTaquillaEntrega = usuarioTaquillaEntrega;
    }
    
    public boolean isShowmesagge() {
        return showmesagge;
    }
    
    public void setShowmesagge(boolean showmesagge) {
        this.showmesagge = showmesagge;
    }
    
}
