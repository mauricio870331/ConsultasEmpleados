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
@Named(value = "ciudadesBean")
@SessionScoped
public class CiudadesBean implements Serializable {

    /**
     * Variable privada: id_ciu. Es el Id de la ciudad seleccionada
     */
    private int id_ciu;
    /**
     * Variable privada: listCiudaes. Contendra el listado de las ciudades
     */
    private List<Ciudad> listCiudaes = new ArrayList();
    /**
     * Variable: ListCiu. Variable para la navegacion. vista CiudadesList.xhtml
     */
    String ListCiu = "../Ciudades/CiudadesList.xhtml";
    /**
     * Variable: CreaCiu. Variable para la navegacion vista CiudadesCrear.xhtml
     */
    String CreaCiu = "../Ciudades/CiudadesCrear.xhtml";
    /**
     * Variable: EditarCiu. Variable para la navegacion vista
     * CiudadesEditar.xhtml
     */
    String EditarCiu = "../Ciudades/CiudadesEditar.xhtml";
    /**
     * Variable privada: ciudad. almacenara el objeto Ciudad actualmente
     * seleccionado
     */
    private Ciudad ciudad;
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
     * Constructor de clase
     */
    public CiudadesBean() {
    }

    @PostConstruct
    public void init() {
        try {
            growl.setLife(5000);
            listCiudaes.clear();
            listarCiudades();
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        }
    }

    /**
     * Método que Listara las ciudades para renderizarlas en la vista
     * <PRE> List<Ciudad> listCiudaes = new ArrayList();
     *  listCiudaes.add(new Ciudad(obj.getNum1(), obj.getStr1(), obj.getStr2(), obj.getStr3(), obj.getStr4()))</PRE>
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos de la tabla tbl_ciudades
     * @since incluido desde la version 1.0
     */
    private void listarCiudades() throws SQLException {
        try {
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            l = (ArrayList) CiudadesUtils.getSelectSql("listCiudades", 1, "nada");
            for (ConsultaGeneral obj : l) {
                listCiudaes.add(new Ciudad(obj.getNum1(), obj.getStr1(), obj.getStr2(), obj.getStr3(), obj.getStr4()));
            }
        } catch (SQLException ex) {
            System.out.println("error aqui" + ex);
        }
    }

    /**
     * Método que permite crear ciudades
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al insertar nuevas ciudades en la tabla tbl_ciudades
     * @throws java.text.ParseException Error de parseo, ocurre al intentar
     * convertir un string a entero en el metodo Create de la clase CrudObject.
     * @since incluido desde la version 1.0
     */
    public void createCiudades() throws SQLException, ParseException {
        LoginBean log = new LoginBean();
        ciudad.setFecha_creacion(format2.format(new Date()));
        ciudad.setUser_mod(log.getDocumentoUserLog());
        ciudad.setFecha_mod(format.format(new Date()));
        long a = CrudObject.create(ciudad);
        if (a == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Ciudad Creada"));
            listCiudaes.clear();
            listarCiudades();
        }
        ciudad = null;
        log = null;
    }

    /**
     * Método que setea la ciudad actual a editar
     *
     * @param c Parametro de tipo Ciudad, que contiene el objeto de ciudad
     * seleccionada para la edicion
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * CiudadesEditar
     * @since incluido desde la version 1.0
     */
    public void prepareEdit(Ciudad c) throws IOException {
        setCiudad(c);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Ciudades/CiudadesEditar.xhtml");
    }

    /**
     * Método que permite editar las ciudades
     *
     * @throws java.sql.SQLException Error de Sql, Ocurre cuando se presenta un
     * error al editar ciudades en la tabla tbl_ciudades
     * @throws java.io.IOException Error que ocurre al intentar redirigir a la
     * vista CiudadesList.xhtml
     * @since incluido desde la version 1.0
     */
    public void editCiudades() throws SQLException, IOException {
        LoginBean log = new LoginBean();
        ciudad.setUser_mod(log.getDocumentoUserLog());
        ciudad.setFecha_mod(format.format(new Date()));
        int a = CrudObject.edit(ciudad);
        if (a == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Ciudades/CiudadesList.xhtml");
        }
        ciudad = null;
        log = null;
    }

    /**
     * Método que setea la ciudad actual para su eliminación
     *
     * @param c Cuiudad que se va a eliminar
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista CiudadConfirmDelete.xhtml
     * @since incluido desde la version 1.0
     */
    public void confirmDelete(Ciudad c) throws IOException {
        setCiudad(c);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Ciudades/CiudadConfirmDelete.xhtml");
    }

    /**
     * Método que Cancela la eliminacion de una ciudad
     * <PRE> Establece denuevo a null la variable ciudad</PRE>
     *
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista CiudadesList.xhtml
     * @since incluido desde la version 1.0
     */
    public void cancelDelete() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Ciudades/CiudadesList.xhtml");
        ciudad = null;
    }

    /**
     * Método que elimina la ciudad seleccionada
     *
     * @throws java.sql.SQLException Error que ocurre al intentar eliminar una
     * ciudad en la tabla tbl_ciudades
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista CiudadesList.xhtml
     * @since incluido desde la version 1.0
     */
    public void delete() throws SQLException, IOException {
        int a = CrudObject.delete(ciudad);
        if (a == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Ciudades/CiudadesList.xhtml");
            listCiudaes.clear();
            listarCiudades();
        }
        ciudad = null;
    }

    /**
     * Método que permite obtener la ruta de navegacion para las vistas en este
     * caso: CiudadesList.xhtml
     *
     * @return ListCiu String con la ruta establecida para la navegacion
     * @since incluido desde la version 1.0
     */
    public String getListCiu() {
        return ListCiu;
    }

    /**
     * Método que permite obtener el listado de ciudades
     *
     * @return listCiudaes Ciudad almacena cada una de las ciudades obtenidas.
     * @since incluido desde la version 1.0
     */
    public List<Ciudad> getListCiudaes() {
        return listCiudaes;
    }

    /**
     * Método que permite obtener la ruta de navegacion para las vistas en este
     * caso: CiudadesCrear.xhtml, limpia el objeto ciudad para su posterior
     * instancia.
     *
     * @return CreaCiu String con la ruta establecida para la navegacion
     * @since incluido desde la version 1.0
     */
    public String getCrearCiu() {
        ciudad = null;
        return CreaCiu;
    }

    /**
     * Método que permite establecer el valor al listado de ciudades.
     *
     * @param listCiudaes Ciudad, Array list contenedor de las ciudades
     * @since incluido desde la version 1.0
     */
    public void setListCiudaes(List<Ciudad> listCiudaes) {
        this.listCiudaes = listCiudaes;
    }

    /**
     * Método que permite obtener el objeto de la ciudad actual, si no esta
     * instanciado, este lo hará.
     *
     * @return retornara el actual objeto.
     * @since incluido desde la version 1.0
     */
    public Ciudad getCiudad() {
        if (ciudad == null) {
            ciudad = new Ciudad();
        }
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public String getEditarCiu() {
        return EditarCiu;
    }

    public void setEditarCiu(String EditarEmp) {
        this.EditarCiu = EditarEmp;
    }

    public int getId_ciu() {
        return id_ciu;
    }

    public void setId_ciu(int id_ciu) {
        this.id_ciu = id_ciu;
    }

}
