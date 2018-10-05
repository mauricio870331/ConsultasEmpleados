package Beans;

import Entities.usersToConsultas;
import Modelo.Funciones;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.component.growl.Growl;
import javax.servlet.http.HttpServletRequest;

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
    private usersToConsultas usersConsulta;
    private boolean recibircorreos;
    /**
     * Variable: growl. Variable que instancia el contenedor de mensajes en las
     * vistas
     */
    private String verdoc = "";
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
     * // * Método que verifica los datos del usuario para iniciar sesion, si
     * los // * datos son correctos almacena el usuario recuperado de la base de
     * datos y // * lo establece como variable de sesion
     *
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void login() throws IOException, SQLException {
        if (usersConsulta.getDocumento().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "El Campo Usuario no debe estar vacio..!"));
        } else if (usersConsulta.getPass().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "El Campo Clave no debe estar vacio..!"));
        } else {
            getDatauser();
            Funciones f = new Funciones();
            usersToConsultas u;
            try {
                u = f.Login(usersConsulta.getDocumento(), usersConsulta.getPass(), getDatauser());
                if (u != null) {
                    System.out.println(u.toString());
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", u);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/ConsultasEmpleados/faces/Consultas/ConsultarDesprendibles.xhtml");
//                   FacesContext.getCurrentInstance().getExternalContext().redirect("/ConsultasEmpleados/faces/Bienvenida.xhtml");

                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Usuario o clave invalida..!"));
                }
            } catch (Exception ex) {
                System.out.println("error " + ex);
            }

        }
    }

    public void RecordarClave() throws IOException, SQLException {
        if (usersConsulta.getDocumento().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "El Campo Usuario no debe estar vacio..!"));
        } else if (usersConsulta.getFecha_exp_cc() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "El Campo Clave Expedición del documento no debe estar vacio..!"));
        } else {
            try {
                Funciones f = new Funciones();
                if (f.reCordarClave(usersConsulta)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Se ha enviado la clave al correo registrado, revisa tu bandeja.!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "No se pudo recuperar la clave"));
                }
            } catch (Exception e) {
                System.out.println("error  " + e);
            }

        }
    }

    public void prepareRegister() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/ConsultasEmpleados/faces/Registro.xhtml");
    }

    public void prepareRememberClave() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/ConsultasEmpleados/faces/RecuperarClave.xhtml");
    }

    public void backtoLogin() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/ConsultasEmpleados/faces/login.xhtml");
    }

    public void Register() throws IOException {

        System.out.println("usersConsulta " + usersConsulta.toString());
        if (usersConsulta.getDocumento().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "El campo documento no debe estar vacio."));
        } else if (usersConsulta.getNickName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "El campo nombre no debe estar vacio."));
        } else if (usersConsulta.getPass().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "El campo clave no debe estar vacio."));
        } else if (!usersConsulta.getPass().equals(usersConsulta.getRep_pass())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Las contraseñas no coinciden."));
        } else if (usersConsulta.getFecha_exp_cc() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "La fecha de expedición del documento no debe ser vacio..!"));
        } else {
            try {
                Funciones f = new Funciones();
                if (f.validarUsuario(usersConsulta.getDocumento(), 1)) {
                    if (!usersConsulta.getCorreo().equals("")) {
                        if (isRecibircorreos()) {
                            usersConsulta.setRecibirCorreos("S");
                        } else {
                            usersConsulta.setRecibirCorreos("N");
                        }
                    } else {
                        usersConsulta.setRecibirCorreos("N");
                    }

                    if (!f.validarUsuario(usersConsulta.getDocumento(), 2)) {
                        if (f.registrarUsuario(usersConsulta)) {
                            usersConsulta = null;
                            f = null;
                            FacesContext.getCurrentInstance().getExternalContext().redirect("/ConsultasEmpleados/faces/login.xhtml");
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "No se pudo registrar el usuario..!"));
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "El usuario ya existe..!"));
                    }

                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "No has estado vinculado con la empresa ó lo estuviste pero tus datos estan inactivos, acercate directamente a gestión humana..!"));
                }
                f = null;
            } catch (SQLException ex) {
                System.out.println("error login bean metodo: Register()" + ex);
            }
        }
    }

    public static ArrayList<String> getDatauser() throws UnknownHostException {
        ArrayList datosUser = new ArrayList();
        String remoteAddr = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
        InetAddress addr = InetAddress.getByName(remoteAddr);
        String hostname = addr.getHostName();
        String browser = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getHeader("User-Agent");
        datosUser.add(remoteAddr);
        datosUser.add(hostname);
        datosUser.add(browser);
        return datosUser;
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
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ((HttpSession) ctx.getSession(false)).invalidate();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/ConsultasEmpleados/faces/login.xhtml");
        } catch (IOException e) {
            System.out.println("error " + e);
        }

    }

    /**
     * Método que recupera el documento del usuario de sesion.
     *
     * @return Documento del usuario logueado
     * @since incluido desde la version 1.0
     */
    public usersToConsultas getDatosUserToconsulta() {
        usersConsulta = (usersToConsultas) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return usersConsulta;
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
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

    public usersToConsultas getUsersConsulta() {
        if (usersConsulta == null) {
            usersConsulta = new usersToConsultas();
        }
        return usersConsulta;
    }

    public void setUsersConsulta(usersToConsultas usersConsulta) {
        this.usersConsulta = usersConsulta;
    }

    public boolean isRecibircorreos() {
        return recibircorreos;
    }

    public void setRecibircorreos(boolean recibircorreos) {
        this.recibircorreos = recibircorreos;
    }

}
