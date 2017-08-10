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
@Named(value = "rolesBean")
@SessionScoped
public class RolesBean implements Serializable {

    /**
     * Variable privada: a para el conrol de mensajes
     */
    long a = -1;
    /**
     * Variable privada: listRoles. Contendra el listado de roles
     */
    private List<Roles> listRoles = new ArrayList();
    /**
     * Variable: ListRol. Variable para la navegacion. vista RolesList.xhtml
     */
    String ListRol = "../Roles/RolesList.xhtml";
    /**
     * Variable: CreaRol. Variable para la navegacion. vista RolesCrear.xhtml
     */
    String CreaRol = "../Roles/RolesCrear.xhtml";
    /**
     * Variable: EditarRol. Variable para la navegacion. vista RolesEditar.xhtml
     */
    String EditarRol = "../Roles/RolesEditar.xhtml";
    /**
     * Variable privada: rol. almacenara el objeto rol actual
     */
    private Roles rol;
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

    public RolesBean() {
    }

    @PostConstruct
    public void init() {
        try {
            growl.setLife(5000);
            listarRoles();
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        }
    }

    /**
     * Método que Listara los roles para renderizarlos en la vista
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos de la tabla tbl_ciudades
     * @since incluido desde la version 1.0
     */
    private void listarRoles() throws SQLException {
        listRoles.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CrudObject.getSelectSql("rol", 1, "param");
        for (ConsultaGeneral obj : l) {
            listRoles.add(new Roles(obj.getNum1(), obj.getStr1(), obj.getStr2(), obj.getStr3()));
        }
        if (a == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Error al eliminar, comuniquese con el administrador"));
            a = -1;
        }
    }

    /**
     * Método que creara los roles.
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al insertar los datos de en tabla roles
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     * @throws java.text.ParseException
     * @since incluido desde la version 1.0
     */
    public void createRoles() throws SQLException, InterruptedException, IOException, ParseException {
        LoginBean log = new LoginBean();
        rol.setUserMod(log.getDocumentoUserLog());
        rol.setFechaMod(format.format(new Date()));
        a = CrudObject.create(rol);
        if (a == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Rol Creado"));
            listRoles.clear();
            listarRoles();
        }
        rol = null;
        log = null;
    }

    /**
     * Método que setea el rol actual a editar
     *
     * @param r Parametro de tipo roles, que contiene el objeto de rol
     * @throws java.io.IOException la vista CiudadesEditar.xhtml
     * @since incluido desde la version 1.0
     */
    public void prepareEdit(Roles r) throws IOException {
        setRol(r);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Roles/RolesEditar.xhtml");
    }

    /**
     * Método que permite editar los roles
     *
     * @throws java.sql.SQLException Error de Sql, Ocurre cuando se presenta un
     * error al editar ciudades en la tabla tbl_ciudades
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException Error que ocurre al intentar redirigir a la
     * vista CiudadesList.xhtml
     * @since incluido desde la version 1.0
     */
    public void editRol() throws SQLException, InterruptedException, IOException {
        LoginBean log = new LoginBean();
        rol.setUserMod(log.getDocumentoUserLog());
        rol.setFechaMod(format.format(new Date()));
        a = CrudObject.edit(rol);
        if (a == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Roles/RolesList.xhtml");
        }
        rol = null;
        log = null;
    }

    /**
     * Método que setea el rol actual para su eliminación
     *
     * @param r Rol que se va a eliminar
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista RolConfirmDelete.xhtml
     * @since incluido desde la version 1.0
     */
    public void confirmDelete(Roles r) throws IOException {
        setRol(r);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Roles/RolConfirmDelete.xhtml");
    }

    /**
     * Método que Cancela la eliminacion de un rol
     *
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista RolesList.xhtml
     * @since incluido desde la version 1.0
     */
    public void cancelDelete() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Roles/RolesList.xhtml");
        rol = null;
    }

    /**
     * Método que elimina el rol seleccionado
     *
     * @throws java.sql.SQLException Error que ocurre al intentar eliminar una
     * rol en la tabla roles
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista CiudadesList.xhtml
     * @since incluido desde la version 1.0
     */
    public void delete() throws SQLException, InterruptedException, IOException {
        a = CrudObject.delete(rol);
        if (a == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Roles/RolesList.xhtml");
            listarRoles();
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Roles/RolesList.xhtml");
        }
        rol = null;
    }

    /**
     * Método devuelve el listado de roles para renderizar en la vista
     *
     * @return
     * @throws java.sql.SQLException Error que ocurre al intentar recuperar los
     * roles de la bd
     * @since incluido desde la version 1.0
     */
    public String getListRol() throws SQLException {
        listarRoles();
        return ListRol;
    }

    public void setListRol(String ListRol) {
        this.ListRol = ListRol;
    }

    public List<Roles> getListRoles() {
        return listRoles;
    }

    public String getCrearRol() {
        rol = null;
        return CreaRol;
    }

    public void setListRoles(List<Roles> listRoles) {
        this.listRoles = listRoles;
    }

    public Roles getRol() {
        if (rol == null) {
            rol = new Roles();
        }
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public String getEditarRol() {
        return EditarRol;
    }

    public void setEditarRol(String EditarRol) {
        this.EditarRol = EditarRol;
    }

}
