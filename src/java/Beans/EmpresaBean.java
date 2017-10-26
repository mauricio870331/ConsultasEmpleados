package Beans;

import Entities.*;
import Modelo.Conexion;
import Modelo.CrudObject;
import java.io.IOException;
import java.io.Serializable;
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
@Named(value = "empresaBean")
@SessionScoped
public class EmpresaBean implements Serializable {

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

    /**
     * Variable listEmpresas. Contendra el listado de las empresas
     */
    List<Empresas> listEmpresas = new ArrayList();
    /**
     * Variable privada listCiudaes. Contendra el listado de las ciudades
     */
    private List<Ciudad> listCiudaes = new ArrayList();
    /**
     * Variable privada lisEstados. Contendra el listado de estados
     */
    private List<Estados> lisEstados = new ArrayList();
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
     * Variable privada: empresa. Contendra la empresa actual
     */
    private Empresas empresa;
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
    
    public EmpresaBean() {
    }
    
    @PostConstruct
    public void init() {
        try {
            growl.setLife(5000);
            listarEmpresas();
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        }
    }
    
    public List<Empresas> getListEmpresas() {
        return listEmpresas;
    }
    
    public void setListEmpresas(List<Empresas> listEmpresas) {
        this.listEmpresas = listEmpresas;
    }

    /**
     * Método que Listara las empresas, ciudades y estados para renderizarlos en
     * la vista
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos de la tabla tbl_ciudades
     * @since incluido desde la version 1.0
     */
    private void listarEmpresas() throws SQLException {
        System.out.println("estoy cargando empresas");
        listEmpresas.clear();
        listCiudaes.clear();
        lisEstados.clear();
        try {
            cn = Conexion.conectar();
            String sql = "select * from tbl_empresas where id_empresa <> 60 ";//AND cod_estado = 38
            pstm = cn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Empresas em = new Empresas(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getString(10),
                        rs.getInt(11));
                listEmpresas.add(em);
            }
            pstm = cn.prepareStatement("select * from tbl_ciudades");
            rs = pstm.executeQuery();
            while (rs.next()) {
                listCiudaes.add(new Ciudad(rs.getInt(1), rs.getString(2)));
            }
            pstm = cn.prepareStatement("select * from estados");
            rs = pstm.executeQuery();
            while (rs.next()) {
                lisEstados.add(new Estados(rs.getInt(1), rs.getString(2)));
            }
            
        } catch (SQLException ex) {
            System.out.println("error aqui" + ex);
        } finally {
            cn.close();
            rs.close();
        }
    }

    /**
     * Método que permitira crear las empresas
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al intentar ingreas los datos en la tabla tbl_empresas
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     * @throws java.text.ParseException
     * @since incluido desde la version 1.0
     */
    public void createEmpresas() throws SQLException, InterruptedException, IOException, ParseException {
        LoginBean log = new LoginBean();
        empresa.setFecha_creacion(format2.format(new Date()));
        empresa.setUser_mod(log.getDocumentoUserLog());
        empresa.setFecha_mod(format.format(new Date()));
        empresa.setCod_estado(38);
        long a = CrudObject.create(empresa);
        if (a == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Empresa Creada"));
            listEmpresas.clear();
            listCiudaes.clear();
            lisEstados.clear();
            listarEmpresas();
//            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/EmpresasList.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Error al Crear la empresa"));
        }
        empresa = null;
    }

    /**
     * Método que permitira editar las empresas
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al intentar editar los datos en la tabla tbl_empresas
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void editEmpresas() throws SQLException, InterruptedException, IOException {
        LoginBean log = new LoginBean();
        empresa.setUser_mod(log.getDocumentoUserLog());
        empresa.setFecha_mod(format.format(new Date()));
        int a = CrudObject.edit(empresa);
        if (a == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Empresas/EmpresasList.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se pudo actualizar la empresa"));
        }
        empresa = null;
    }

    /**
     * Método que setea la empresa actual a editar
     *
     * @param e Parametro de tipo Empresas, que contiene el objeto de ciudad *
     * seleccionada para la edicion
     * @throws java.io.IOException la vista EmpresasEditar.xhtml
     * @since incluido desde la version 1.0
     */
    public void prepareEdit(Empresas e) throws IOException {
        setEmpresa(e);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Empresas/EmpresasEditar.xhtml");
    }

    /**
     * Método que setea la empresa actual para su eliminación
     *
     * @param e Empresa que se va a eliminar
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista EmpresasConfirmDelete.xhtml
     * @since incluido desde la version 1.0
     */
    public void confirmDelete(Empresas e) throws IOException {
        setEmpresa(e);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Empresas/EmpresasConfirmDelete.xhtml");
    }
    
   
    /**
     * Método que Cancela la eliminacion de una Empresa
     * <PRE> Establece de nuevo a null la variable empresa</PRE>
     *
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista EmpresasList.xhtml
     * @since incluido desde la version 1.0
     */
    public void cancelDelete() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Empresas/EmpresasList.xhtml");
        empresa = null;
    }

    /**
     * Método que elimina la empresa seleccionada
     *
     * @throws java.sql.SQLException Error que ocurre al intentar eliminar una
     * ciudad en la tabla tbl_ciudades
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista EmpresasList.xhtml
     * @since incluido desde la version 1.0
     */
    public void delete() throws SQLException, InterruptedException, IOException {
        int a = CrudObject.delete(empresa);
        if (a == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Empresas/EmpresasList.xhtml");
            listarEmpresas();
        }
        empresa = null;
    }
    
    public void listarEmpresasLink() throws SQLException, IOException {
        listEmpresas.clear();
        listCiudaes.clear();
        lisEstados.clear();
        listarEmpresas();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Empresas/EmpresasList.xhtml");
    }
    
    public String getCrearEmp() {
        empresa = null;
        return CrearEmp;
    }
    
    public List<Ciudad> getListCiudaes() {
        return listCiudaes;
    }
    
    public void setListCiudaes(List<Ciudad> listCiudaes) {
        this.listCiudaes = listCiudaes;
    }
    
    public Empresas getEmpresa() {
        if (empresa == null) {
            empresa = new Empresas();
        }
        return empresa;
    }
    
    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }
    
    public Growl getGrowl() {
        return growl;
    }
    
    public void setGrowl(Growl growl) {
        this.growl = growl;
    }
    
    public String getEditarEmp() {
        return EditarEmp;
    }
    
    public void setEditarEmp(String EditarEmp) {
        this.EditarEmp = EditarEmp;
    }
    
    public int getId_Delete() {
        return id_em;
    }
    
    public void setId_Delete(int id_Delete) {
        this.id_em = id_Delete;
    }
    
    public List<Estados> getLisEstados() {
        return lisEstados;
    }
    
    public void setLisEstados(List<Estados> lisEstados) {
        this.lisEstados = lisEstados;
    }

    public String getListEmp() throws SQLException {
        listarEmpresas();
        return ListEmp;
    }

    public void setListEmp(String ListEmp) {
        this.ListEmp = ListEmp;
    }
    

    
}
