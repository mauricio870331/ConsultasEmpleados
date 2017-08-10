package Beans;

import Entities.Docs;
import Entities.Usuarios;
import Modelo.ConsultaGeneral;
import Modelo.CrudObject;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.component.growl.Growl;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    /**
     * Variable privada: user. Contendra los datos del usuario que esta logueado
     * actualmente
     */
    private Usuarios user;
    /**
     * Variable: growl. Variable que instancia el contenedor de mensajes en las
     * vistas
     */
    private String verdoc = "";
    private List<Docs> listDocumentos = new ArrayList();
    private Date createAt = null;
    Growl growl = new Growl();
    private String logOUT;
 

    @PostConstruct
    public void init() {
        growl.setLife(5000);
        destroysession();
    }

    public LoginBean() {

    }

    /**
     * Método que eliminara el usuairo de la sesion al iniciar si existe una
     * sesion activa.
     *
     * @since incluido desde la version 1.0
     */
    public void destroysession() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario") != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        }
    }

    /**
     * Método que verifica si hay o no sesiones activas, si no hay una sesion
     * activa sera redirigido a la vista del login.xhtml
     *
     * @since incluido desde la version 1.0
     */
    public void validarsession() {
        try {
            if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario") == null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/login.xhtml");
            }
        } catch (IOException ex) {
            System.out.println("Error Redirect : " + ex.toString());
        }
    }

    /**
     * Método que verifica los datos del usuario para iniciar sesion, si los
     * datos son correctos almacena el usuario recuperado de la base de datos y
     * lo establece como variable de sesion
     *
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void login() throws IOException, SQLException {
        if (user.getDocumento().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "El Campo Usuario no debe estar vacio..!"));
        } else if (user.getClave().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "El Campo Clave no debe estar vacio..!"));
        } else {
            ArrayList<ConsultaGeneral> l = new ArrayList<>();
            l = (ArrayList) CrudObject.getSelectSql("login", 1, "" + user.getDocumento() + "," + user.getClave() + "");
            if (l.size() > 0) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", l);
                System.out.println(l.get(0).getNum2());
                if (l.get(0).getStr4().equals("TAQUILLA")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Taquilla/ListaEntrega.xhtml");
                    user = null;
                    cargarDocsAgency();
                }
                if (l.get(0).getStr4().equals("ADMIN")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Empresas/EmpresasList.xhtml");
                    user = null;
                }
                if (l.get(0).getStr4().equals("EMPRESA")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Empresa/Empleados/EmpleadosList.xhtml");
                    user = null;
                }
                if (l.get(0).getStr4().equals("TESORERIA")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Tesoreria/ContraviasTesoreriaList.xhtml");
                    user = null;
                }
                if (l.get(0).getStr4().equals("VALORES")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Valores/ListRelacion.xhtml");
                    user = null;
                }
                if (l.get(0).getStr4().equals("AUDITOR")) {
                    if (l.get(0).getStr1().equals("29533170")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Auditoria/ContraviasAuditoriaList.xhtml");
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Auditoria/ListRelacion.xhtml");
                    }
                    user = null;
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "El Usuario No Existe..!"));
            }
        }
    }

    public String getVerdoc() throws SQLException {
        return verdoc;
    }

    public void setVerdoc(String verdoc) {
        this.verdoc = verdoc;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * Método que remueve el suaurio almacenado en la variable de sesion y
     * redirige a la vista login.xhtml
     *
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/login.xhtml");
    }

    /**
     * Método que recupera el nombre del usuario de sesion.
     *
     * @return nombre del usuario logueado
     * @since incluido desde la version 1.0
     */
    public String getNomUserLog() {
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return l.get(0).getStr2() + " " + l.get(0).getStr3();
    }

    /**
     * Método que recupera el documento del usuario de sesion.
     *
     * @return Documento del usuario logueado
     * @since incluido desde la version 1.0
     */
    public String getDocumentoUserLog() {
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return l.get(0).getStr1();
    }

    /**
     * Método que recupera el rol del usuario de sesion.
     *
     * @return Rol del usuario logueado
     * @since incluido desde la version 1.0
     */
    public String getRol() {
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return l.get(0).getStr4();
    }

    /**
     * Método que recupera el id del usuario de sesion.
     *
     * @return Id del usuario logueado
     * @since incluido desde la version 1.0
     */
    public int getIdUserLog() {
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return l.get(0).getNum1();
    }

    /**
     * Método que recupera el id_empresa del usuario de sesion.
     *
     * @return id_empresa del usuario logueado
     * @since incluido desde la version 1.0
     */
    public int getIdEmpresa() {
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return l.get(0).getNum3();
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

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    private void cargarDocsAgency() throws SQLException {
//        StringBuilder strb = new StringBuilder();
        listDocumentos.clear();
        System.out.println("doc = " + getDocumentoUserLog());
        String[] parts = getDocumentoUserLog().split(" ");
        String docl = "";
        if (parts.length > 1) {
            docl = parts[0];
        } else {
            docl = getDocumentoUserLog();
        }
        ArrayList<ConsultaGeneral> d = new ArrayList<>();
        d = (ArrayList) CrudObject.getSelectSql("docsAgency", 1, docl + ",0");
        for (ConsultaGeneral doc : d) {
            listDocumentos.add(new Docs(doc.getNum1(), doc.getStr1(), doc.getStr2(), doc.getStr3(), doc.getFecha1(), doc.getNum2()));
        }
//        if (d.size() > 0) {
//            strb.append(d.get(0).getStr2());
//            strb.append(d.get(0).getStr1());
//            setVerdoc(strb.toString());
//            setCreateAt(d.get(0).getFecha1());
//        } else {
//            setVerdoc("javascript:void(0)");
//            setCreateAt(null);
//        }
    }

    public List<Docs> getListDocumentos() {
        return listDocumentos;
    }

    public void setListDocumentos(List<Docs> listDocumentos) {
        this.listDocumentos = listDocumentos;
    }

    public String getLogOUT() throws IOException {
        System.out.println("*****-----------**************");
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        return logOUT = "/Convenios/faces/login.xhtml";
    }

    public void setLogOUT(String logOUT) {
        this.logOUT = logOUT;
    }

    public void logout2() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
    }

}
