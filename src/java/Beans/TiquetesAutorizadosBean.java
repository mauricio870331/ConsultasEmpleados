package Beans;

import Entities.*;
import Modelo.ConsultaGeneral;
import Modelo.CrudObject;
import Utils.CiudadesUtils;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.growl.Growl;
import org.primefaces.event.data.PageEvent;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
@Named(value = "tiquetesAutorizadosBean")
@SessionScoped
public class TiquetesAutorizadosBean implements Serializable {

    /**
     * Variable privada: saldos. Contendra el listado de viajes y tiquetes que
     * se han de entregar a los epleados
     */
    private List<TiquetesAutorizados> TiquetesAutorizados = new ArrayList();

    private TiquetesAutorizados currenTiquete;
    private Usuarios user;

    private ArrayList<String> servicio = new ArrayList();
    private ArrayList<Ciudad> list_origen = new ArrayList();
    private ArrayList<Ciudad> list_destino = new ArrayList();

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
    private int first = 0;

    /**
     * Variable: selecFecha. contendra la fecha seleccionada para consultas
     */
    Date selecFecha;
    /**
     * Variable: listaEntrega. Variable para la navegacion vista
     * ListaEntrega.xhtml
     */
    private String List = "../Tiquetes/ListTiquetesEntregados.xhtml";
    private String ListA = "../../Tiquetes/ListTiquetesEntregados.xhtml";
    private String Create = "../Tiquetes/RegistroTiquete.xhtml";
    private String CreateA = "../../Tiquetes/RegistroTiquete.xhtml";
    private String ChangePass = "../Tiquetes/CambiarClave.xhtml";
    private String ChangePassA = "../../Tiquetes/CambiarClave.xhtml";
    private String RegresarA = "../Admin/Empresas/EmpresasList.xhtml";

    /**
     * Variable: selectUser. contendra el usuario seleccionado para consultas
     */
    String selectUser = "";

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

    public TiquetesAutorizadosBean() {
    }

    @PostConstruct
    public void init() {
        try {
            growl.setLife(5000);
            ListarTiquetesEntregados();
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        }

    }

    public void ListarTiquetesEntregados() throws SQLException {
        TiquetesAutorizados.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        LoginBean log = new LoginBean();
        String param = log.getDocumentoUserLog();
        l = (ArrayList) CrudObject.getSelectSql("tiquAutorizados", 1, param);
        if (log.getRol().equals("VERAUTORIZADOS")) {
            l = (ArrayList) CrudObject.getSelectSql("tiquAutorizadosMon", 1, "");
        }
        for (ConsultaGeneral obj : l) {
            TiquetesAutorizados.add(new TiquetesAutorizados(obj.getNum1(), obj.getStr1(), obj.getStr2(), obj.getStr3(),
                    obj.getStr4(), obj.getStr5(), obj.getStr6(), obj.getFecha1(), obj.getStr7(), obj.getFecha2(),
                    obj.getStr8(), obj.getStr9(), obj.getStr10(), obj.getStr11(), obj.getStr12(), obj.getStr13(), obj.getStr14()));
        }
    }

    public void BuascarUser() throws SQLException {
        String user = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc");
        LoginBean log = new LoginBean();
        String param = log.getDocumentoUserLog() + "," + FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc");
        String vista = "tiquAutorizadosId";
        if (user.equals("")) {
            param = log.getDocumentoUserLog();
            vista = "tiquAutorizados";
        }
        TiquetesAutorizados.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CrudObject.getSelectSql(vista, 1, param);
        for (ConsultaGeneral obj : l) {
            TiquetesAutorizados.add(new TiquetesAutorizados(obj.getNum1(), obj.getStr1(), obj.getStr2(), obj.getStr3(),
                    obj.getStr4(), obj.getStr5(), obj.getStr6(), obj.getFecha1(), obj.getStr7(), obj.getFecha2(),
                    obj.getStr8(), obj.getStr9(), obj.getStr10(), obj.getStr11(), obj.getStr12(), obj.getStr13(), obj.getStr14()));
        }

    }

    public void BuascarUserView() throws SQLException {
        String user = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("doc");
        if (!user.equals("")) {
            String vista = "tiquAutorizadosView";
            TiquetesAutorizados.clear();
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            l = (ArrayList) CrudObject.getSelectSql(vista, 1, user);
            for (ConsultaGeneral obj : l) {
                TiquetesAutorizados.add(new TiquetesAutorizados(obj.getNum1(), obj.getStr1(), obj.getStr2(), obj.getStr3(),
                        obj.getStr4(), obj.getStr5(), obj.getStr6(), obj.getFecha1(), obj.getStr7(), obj.getFecha2(),
                        obj.getStr8(), obj.getStr9(), obj.getStr10(), obj.getStr11(), obj.getStr12(), obj.getStr13(), obj.getStr14()));
            }
        } else {
            ListarTiquetesEntregados();
        }
    }

    public void prepareEdit(TiquetesAutorizados T) throws IOException, SQLException {
        setCurrenTiquete(T);
        getRutasweb();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Tiquetes/EditTiquete.xhtml");
    }

    public void setUserExist() throws SQLException {
//        System.out.println("-- " + currenTiquete.getDocumento());
        String doc = currenTiquete.getDocumento();
        currenTiquete = Utils.CiudadesUtils.getUserExist(doc);
        if (currenTiquete==null) {
            getCurrenTiquete().setDocumento(doc);
        }
    }

    public void edit() throws SQLException, IOException {
        LoginBean log = new LoginBean();
        currenTiquete.setFecha_solicitud(new Date());
        currenTiquete.setUsaurio_solicita(log.getDocumentoUserLog());
        int a = CrudObject.edit(currenTiquete);
        if (a == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Tiquetes/ListTiquetesEntregados.xhtml");
        }
        currenTiquete = null;
        log = null;
    }

    public void updateNumberTiquete(TiquetesAutorizados t) throws SQLException, IOException {
        boolean a = Utils.CiudadesUtils.updateNumberTiquete(t);
//        if (a) {
//            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Tiquetes/viewAutorizados.xhtml");
//        }        
    }

    public void confirmDelete(TiquetesAutorizados T) throws IOException {
        setCurrenTiquete(T);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Tiquetes/ConfirmDelete.xhtml");
    }

    public void delete() throws SQLException, IOException {
        int a = CrudObject.delete(currenTiquete);
        if (a == 1) {
            ListarTiquetesEntregados();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Tiquetes/ListTiquetesEntregados.xhtml");

        }
        currenTiquete = null;
    }

    public void cancelDelete() throws IOException {
        setCurrenTiquete(null);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Tiquetes/ListTiquetesEntregados.xhtml");
    }

    public void getRutasweb() throws SQLException {
        if (currenTiquete.getOrigen() == null || currenTiquete.getOrigen().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso..!", "Aviso..!\nSeleccione primero el origen"));
        } else {
            ArrayList<String> l = new ArrayList<>();
            l = (ArrayList<String>) CiudadesUtils.getRutasWeb(currenTiquete.getOrigen(), currenTiquete.getDestino());
            if (l.size() > 0) {
                getServicio(l);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso..!", "La empresa no tiene este convenio, verifique las rutas"));
            }
        }
    }

    public void getServicio(ArrayList<String> l) {
        servicio.clear();
        l.stream().forEach((string) -> {
//            System.out.println("string " + string);
            String[] prt = string.split(",");
            servicio.add(prt[5]);
        });
    }

    public String getCiudadById(int codCiudad) throws SQLException {
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
//        System.out.println("codCiudad " + codCiudad);
        l = (ArrayList) CrudObject.getSelectSql("ciudadById", 1, "" + codCiudad);
        return l.get(0).getStr1();
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
        LoginBean log = new LoginBean();
        currenTiquete.setFecha_solicitud(new Date());
        currenTiquete.setEstado("Pendiente");
        currenTiquete.setUsaurio_solicita(log.getDocumentoUserLog());
        if (CrudObject.create(currenTiquete) >= 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Transaccion realizada con exito"));
            currenTiquete = null;
            return "RegistroTiquete";
        } else {
            currenTiquete = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informacion", "Error al crear la transaccion"));
            return "RegistroTiquete";
        }
    }

    public String crhangePassword() throws SQLException, InterruptedException, IOException, ParseException {
        LoginBean log = new LoginBean();
        user.setDocumento(log.getDocumentoUserLog());
        if (CrudObject.editPass(user) >= 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Clave actualzada, los cambios serán aplicados cuando inicie sesión nuevamente..!"));
            user = null;
            return "CambiarClave";
        } else {
            user = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informacion", "Error al actualizar la clave..!"));
            return "CambiarClave";
        }
    }

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
        } catch (SQLException ex) {
            System.out.println("error " + ex);
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
//        setIdTransAnular(id_trans);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Taquilla/ConfirmDeleteTrans.xhtml");
    }

//    public void exportarRelacion() throws IOException, JRException, SQLException {
//        if (selecFechaIni != null && selecFechaFin != null) {
//            if (CmgeneradoList.isEmpty()) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No hay resultados para Exportar"));
//            } else {
//                Map parametros = new HashMap();
//                String report = "/Reports/reporteCmAsociados.jasper";
//                parametros.put("fechaIni", format2.format(selecFechaIni) + " 00:00:00");
//                parametros.put("fechaFin", format2.format(selecFechaFin) + " 23:59:59");
//                if (!user.equals("")) {
//                    parametros.put("agencia", user);
//                    report = "/Reports/reporteCmAsociadosU.jasper";
//                }
//                ConexionPool pool = new ConexionPool();
//                pool.con = pool.dataSource.getConnection();
//                File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(report));
//                JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, pool.con);
//                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//                response.addHeader("Content-disposition", "attachment; filename=CmAsociados.xls");
//                try (ServletOutputStream stream = response.getOutputStream()) {
//                    JRXlsExporter exporter = new JRXlsExporter();
//                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
//                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
//                    exporter.exportReport();
//                    stream.flush();
//                }
//                FacesContext.getCurrentInstance().responseComplete();
//            }
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Debes seleccionar un rango de fechas"));
//        }
//    }
    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
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

    public String getList() throws SQLException {
        ListarTiquetesEntregados();
        return List;

    }

    public String getListA() throws SQLException {
        ListarTiquetesEntregados();
        return ListA;
    }

    public void setList(String listaEntrega) {
        this.List = listaEntrega;
    }

    public List<TiquetesAutorizados> getTiquetesAutorizados() {
        return TiquetesAutorizados;
    }

    public void setTiquetesAutorizados(List<TiquetesAutorizados> TiquetesAutorizados) {
        this.TiquetesAutorizados = TiquetesAutorizados;
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

    public String getCreate() {
        try {
            cargarDatos();
        } catch (SQLException ex) {
            System.out.println("error  " + ex);
        }
        return Create;
    }

    public String getCreateA() {
        return CreateA;
    }

    public void setCreate(String Create) {
        this.Create = Create;
    }

    public TiquetesAutorizados getCurrenTiquete() {        
        if (currenTiquete == null) {
            currenTiquete = new TiquetesAutorizados();
        }
        return currenTiquete;
    }

    public void setCurrenTiquete(TiquetesAutorizados currenTiquete) {       
        this.currenTiquete = currenTiquete;
    }

    public ArrayList<String> getServicio() {
        return servicio;
    }

    public void setServicio(ArrayList<String> servicio) {
        this.servicio = servicio;
    }

    public String getChangePass() {
        return ChangePass;
    }

    public String getChangePassA() {
        return ChangePassA;
    }

    public void setChangePass(String ChangePass) {
        this.ChangePass = ChangePass;
    }

    public Usuarios getUser() {
        if (user == null) {
            user = new Usuarios();
        }
        return user;
    }

    public void setUser(Usuarios user) {
        this.user = user;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public void onPageChange(PageEvent event) {
        this.setFirst(((DataTable) event.getSource()).getFirst());

    }

    public String getRegresarA() {
        return RegresarA;
    }

    public void setRegresarA(String RegresarA) {
        this.RegresarA = RegresarA;
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

}
