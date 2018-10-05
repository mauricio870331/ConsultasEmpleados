package Beans;

import Entities.*;
import Modelo.Conexion;
import Modelo.ConexionPool;
import Modelo.Funciones;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.primefaces.component.growl.Growl;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
@Named(value = "desprendiblesBean")
@SessionScoped
public class DesprendiblesBean implements Serializable {

    /**
     * Variable privada: id_em. Contendra el id del empleado
     */
    private int id_em;
    /**
     * Variable statica: conexion. para crear el objeto de la clase Conexion
     */
    static Conexion conexion;
    /**
     * Variable statica: cn para inicializar la conexion a la bd
     */
    static Connection cn;
    static PreparedStatement pstm;
    static ResultSet rs;
    private String mensaje;

    /**
     * Variable listEmpresas. Contendra el listado de las empresas
     */
    //nuevo    
    private List<DesprendiblePago> listDesprendibles = new ArrayList();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
    private Date selecFechaIni;
    private Date selecFechaFin;
    private Date selecFechaIni2;
    private Date selecFechaFin2;

    /**
     * Variable: ListEmp. Variable para la navegacion vista EmpresasList.xhtml
     */
    private String ListEmp = "../Empresas/EmpresasList.xhtml";
    /**
     * Variable: CrearEmp. Variable para la navegacion vista EmpresasCrear.xhtml
     */
    String CrearEmp = "../Empresas/EmpresasCrear.xhtml";

    /**
     * Variable: EditarEmp. Variable para la navegacion vista
     * EmpresasEditar.xhtml
     */
    String EditarEmp = "../Empresas/EmpresasEditar.xhtml";
    /**
     * Variable: format. Variable para formatear las fechas con hora
     */
    SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
    /**
     * Variable: format. Variable para formatear las fechas sin hora
     */
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd");
    /**
     * Variable: growl. Variable que instancia el contenedor de mensajes en las
     * vistas
     */
    Growl growl = new Growl();

    public DesprendiblesBean() {
    }

    @PostConstruct
    public void init() {
        growl.setLife(5000);
        listDesprendibles.clear();
//        try {
//            growl.setLife(5000);
////            listarEmpresas();
//        } catch (SQLException ex) {
//            System.out.println("error " + ex);
//        }
    }

    public void consultarDesprendibles() throws SQLException, IOException {
        listDesprendibles.clear();
        Funciones f = new Funciones();
        LoginBean l = new LoginBean();
//        Calendar c1 = GregorianCalendar.getInstance();
//        c1.setTime(selecFechaFin);
//        c1.add(Calendar.MONTH, -2);
//        selecFechaIni2 = c1.getTime();
//        selecFechaFin2 = selecFechaFin;
        int totmeses = Integer.parseInt(sdf.format(selecFechaFin)) - Integer.parseInt(sdf.format(selecFechaIni));
        if (totmeses > 3) {
            mensaje = "Solo puedes consultar hasta 3 meses..!";
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Solo puedes consultar hasta 3 meses..!"));
        } else {
            System.out.println("selecFechaFin " + Integer.parseInt(sdf.format(selecFechaIni)) + " " + Integer.parseInt(sdf.format(selecFechaFin)) + " " + l.getDatosUserToconsulta().getDocumento());
            listDesprendibles = f.returnListDesprendibles(Integer.parseInt(sdf.format(selecFechaIni)), Integer.parseInt(sdf.format(selecFechaFin)), l.getDatosUserToconsulta().getDocumento());
            if (listDesprendibles.isEmpty()) {
                mensaje = "No hay resultados para la consulta ...!";
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "No hay resultados para la consulta"));
            } else {
                mensaje = "";
            }
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("/ConsultasEmpleados/faces/Consultas/ConsultarDesprendibles.xhtml");
    }

    public void generarDesprendibles(ActionEvent evt, String documento, String proceso, int idregistro) throws IOException, JRException, SQLException, Exception {
        Funciones f = new Funciones();
        LoginBean l = new LoginBean();
        File jasper;
        Map parametros = new HashMap();
        String realPath = "";
        String rutaFolder = "/resources/";
        System.out.println(documento + " ddddf " + proceso.trim() + "  " + idregistro + " " + sdf.format(selecFechaIni) + " " + sdf.format(selecFechaFin));
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        realPath = (String) servletContext.getRealPath(rutaFolder); // Sustituye "/" por el directorio ej: "/upload"
        ArrayList<String> DatosHost = LoginBean.getDatauser();
        if (proceso.equals("ALL")) {
            jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/DesprendiblesAll.jasper"));
            parametros.put("periodoIni", sdf.format(selecFechaIni));
            parametros.put("periodoFin", sdf.format(selecFechaFin));
        } else {
            jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/DesprendiblesR.jasper"));
            parametros.put("idregistro", idregistro);
        }
        parametros.put("documento", l.getDatosUserToconsulta().getDocumento());
        parametros.put("ip", DatosHost.get(0));
        parametros.put("host", DatosHost.get(1));
        parametros.put("ruta", realPath);
        parametros.put("QR", f.generarQrValidation(2));
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

    public void generarCartaLaboral(ActionEvent evt, String documento) throws IOException, JRException, SQLException, Exception {
        Funciones f = new Funciones();
        File jasper;
        Map parametros = new HashMap();
        String realPath = "";
        String rutaFolder = "/resources/";
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        realPath = (String) servletContext.getRealPath(rutaFolder); // Sustituye "/" por el directorio ej: "/upload"
        System.out.println(" realPath " + realPath);
        jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/Cartalaboral.jasper"));
//        String Empresa = Funciones.getEmpresa(documento); //para cambiar los logos deacuerdo a la empresa
//        System.out.println("Empresa = " + Empresa);
        parametros.put("documento", documento);
        parametros.put("ruta", realPath);
        parametros.put("QR", f.generarQrValidation(1));
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

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public void consultDesprendible() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/ConsultasEmpleados/faces/Consultas/ConsultarDesprendibles.xhtml");
    }

    public List<DesprendiblePago> getListDesprendibles() {
        return listDesprendibles;
    }

    public void setListDesprendibles(List<DesprendiblePago> listDesprendibles) {
        this.listDesprendibles = listDesprendibles;
    }

    public Date getSelecFechaIni() {
        if (selecFechaIni == null) {
            selecFechaIni = new Date();
        }
        return selecFechaIni;
    }

    public void setSelecFechaIni(Date selecFechaIni) {
        this.selecFechaIni = selecFechaIni;
    }

    public Date getSelecFechaFin() {
        if (selecFechaFin == null) {
            selecFechaFin = new Date();
        }
        return selecFechaFin;
    }

    public void setSelecFechaFin(Date selecFechaFin) {
        this.selecFechaFin = selecFechaFin;
    }

    public String getSelecPeriodo() {
        return sdf.format(selecFechaIni) + "-" + sdf.format(selecFechaFin);
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
