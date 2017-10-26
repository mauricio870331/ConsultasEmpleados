package Beans;

import Entities.*;
import Modelo.ConsultaGeneral;
import Modelo.CrudObject;
import java.io.IOException;
import java.io.Serializable;
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
@Named(value = "serviciosBean")
@SessionScoped
public class ServiciosBean implements Serializable {

    /**
     * Variable privada: a. para control de mensajes
     */
    long a = -1;

    /**
     * Variable privada: listServicios. Contendra el listado de servicios
     */
    private List<TblServicios> listServicios = new ArrayList();
    /**
     * Variable: ListServicio. Variable para la navegacion. vista
     * ServiciosList.xhtml
     */
    String ListServicio = "../Servicios/ServiciosList.xhtml";
    /**
     * Variable: CreaServicios. Variable para la navegacion. vista
     * ServiciosCrear.xhtml
     */
    String CreaServicios = "../Servicios/ServiciosCrear.xhtml";
    /**
     * Variable: EditarServicios. Variable para la navegacion. vista
     * ServiciosEditar.xhtml
     */
    String EditarServicios = "../Servicios/ServiciosEditar.xhtml";
    /**
     * Variable privada: servicio. almacenara el objeto TblServicios actual
     * seleccionado
     */
    private TblServicios servicio;
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

    public ServiciosBean() {
    }

    @PostConstruct
    public void init() {
        growl.setLife(5000);
    }

    /**
     * Método que Listara los servicios para renderizarlos en la vista
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos de la tabla tbl_servicios
     * @since incluido desde la version 1.0
     */
    private void listarServicios() throws SQLException {
        listServicios.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CrudObject.getSelectSql("servicio", 1, "param");
        for (ConsultaGeneral obj : l) {
            listServicios.add(new TblServicios(obj.getNum1(), obj.getStr1(), obj.getStr2(), obj.getStr3(), obj.getStr4()));
        }
        if (a == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Error al eliminar, comuniquese con el administrador"));
            a = -1;
        }
    }

    /**
     * Método que permite crear Servicios
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al insertar nuevos servicios en la tabla tbl_servicios
     * @throws java.text.ParseException Error de parseo, ocurre al intentar
     * convertir un string a entero en el metodo Create de la clase CrudObject.
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void createServicio() throws SQLException, InterruptedException, IOException, ParseException {
        System.out.println("crear servicio");
        LoginBean log = new LoginBean();
        servicio.setUserMod(log.getDocumentoUserLog());
        servicio.setFechaMod(format.format(new Date()));
        servicio.setFechaCreacion(format.format(new Date()));
        a = CrudObject.create(servicio);
        if (a == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Servicio Creado"));
            listarServicios();
        }
        servicio = null;
        log = null;
    }

    /**
     * Método que setea el servicio actual a editar
     *
     * @param s parametro de tipo servicio que contiene el objeto del servicio
     * actual
     * @throws java.io.IOException la vista ServiciosEditar.xhtml
     * @since incluido desde la version 1.0
     */
    public void prepareEdit(TblServicios s) throws IOException {
        setServicios(s);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Servicios/ServiciosEditar.xhtml");
    }

    /**
     * Método que permite editar los Servicios
     *
     * @throws java.sql.SQLException Error de Sql, Ocurre cuando se presenta un
     * error al editar servicios en la tabla tbl_servicios
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException Error que ocurre al intentar redirigir a la
     * vista ServiciosList.xhtml
     * @since incluido desde la version 1.0
     */
    public void editServicio() throws SQLException, InterruptedException, IOException {
        LoginBean log = new LoginBean();
        servicio.setUserMod(log.getDocumentoUserLog());
        servicio.setFechaMod(format.format(new Date()));
        a = CrudObject.edit(servicio);
        if (a == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Servicios/ServiciosList.xhtml");
        }
        servicio = null;
        log = null;
        a = -1;
    }

    /**
     * Método que setea el Servicio actual para su eliminación
     *
     * @param s objeto actual servicio
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista ServiciosConfirmDelete.xhtml
     * @since incluido desde la version 1.0
     */
    public void confirmDelete(TblServicios s) throws IOException {
        setServicios(s);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Servicios/ServiciosConfirmDelete.xhtml");
    }

    /**
     * Método que Cancela la eliminacion de un servicio
     *
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista ServiciosList.xhtml
     * @since incluido desde la version 1.0
     */
    public void cancelDelete() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Servicios/ServiciosList.xhtml");
        servicio = null;
    }

    /**
     * Método que elimina el servicio seleccionado
     *
     * @throws java.sql.SQLException Error que ocurre al intentar eliminar un
     * servicio en la tabla tnl_servicios
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista ServiciosList.xhtml
     * @since incluido desde la version 1.0
     */
    public void delete() throws SQLException, InterruptedException, IOException {
        a = CrudObject.delete(servicio);
        if (a == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Servicios/ServiciosList.xhtml");
            listarServicios();
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Servicios/ServiciosList.xhtml");
        }
        servicio = null;
    }

    public String getListServicio() throws SQLException {
        listarServicios();
        return ListServicio;
    }

    public void urlListServicios() throws IOException, SQLException {
        listarServicios();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Servicios/ServiciosList.xhtml");
    }

    public void setListServicio(String ListServicio) {
        this.ListServicio = ListServicio;
    }

    public List<TblServicios> getListServicios() {
        return listServicios;
    }

    public String getCrearServicios() {
        servicio = null;
        return CreaServicios;
    }

    public void setListRoles(List<TblServicios> listServicios) {
        this.listServicios = listServicios;
    }

    public TblServicios getServicios() {
        if (servicio == null) {
            servicio = new TblServicios();
        }
        return servicio;
    }

    public void setServicios(TblServicios servicio) {
        this.servicio = servicio;
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public String getEditarServicio() {
        return EditarServicios;
    }

    public void setServicio(String EditarServicios) {
        this.EditarServicios = EditarServicios;
    }

}
