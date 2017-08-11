package Beans;

import Entities.*;
import Modelo.ConsultaGeneral;
import Modelo.CrudObject;
import Utils.CiudadesUtils;
import static Utils.CiudadesUtils.validarTiqueteExist;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import net.sf.jasperreports.engine.JRException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.component.growl.Growl;
import org.primefaces.event.FileUploadEvent;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
@Named(value = "usuariosBean")
@SessionScoped
public class UsuariosBean implements Serializable {

    /**
     * Variable privada: listUsuarios. Contendra el listado usuarios
     */
    private List<Usuarios> listUsuarios = new ArrayList();
    /**
     * Variable: ListUsuario. Variable para la navegacion. vista
     * UsuarioList.xhtml
     */

    private List<Estudiantes> listEstudiantes = new ArrayList();

    private List<Estudiantes> estudiantesInsert = new ArrayList();
   

    String ListUsuario = "../Usuarios/UsuarioList.xhtml";
    /**
     * Variable: Crearusuario. Variable para la navegacion. vista
     * UsuarioCrear.xhtml
     */

    private String CrearEstudiante = "../universidades/EstudiantesCrear.xhtml";
    String Crearusuario = "../Usuarios/UsuarioCrear.xhtml";
    /**
     * Variable: EditarUsuario. Variable para la navegacion. vista
     * UsuarioEditar.xhtml
     */
    String EditarUsuario = "../Usuarios/UsuarioEditar.xhtml";
     private String selectUser = "";
    /**
     * Variable privada: selectUser. Se almacenara el usuario documento del
     * usuario seleccionado
     */

    private String ListaEstudiantes = "../universidades/EstudiantesList.xhtml";

   
    String fec = "";
    
     private Usuarios usuario;
    /**
     * Variable privada: usuario. almacenara el objeto Usuarios actualmente
     * seleccionado
     */
   
    private String msn = "";
    private boolean validate = false;
    /**
     * Variable privada: listCiudaes. Contendra el listado de las ciudades
     */

    private int countok = 0;
    private List<Estudiantes> logUsers = new ArrayList();
    private Estudiantes estudiante;
  
    
private List<Ciudad> listCiudaes = new ArrayList();    

    private List<Tiquete_Estudiante> listUniversidad = new ArrayList();
    /**
     * Variable privada: listEmpresas. Contendra el listado de las empresas
     */
    private List<Empresas> listEmpresas = new ArrayList();
    /**
     * Variable privada: listRoles. Contendra el listado de Roles
     */
    private List<Roles> listRoles = new ArrayList();
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

    public UsuariosBean() {
    }

    @PostConstruct
    public void init() {
        try {
            growl.setLife(5000);
            lisCiudades();
            listEmpresas();

        } catch (SQLException ex) {
            System.out.println("error " + ex);
        }
    }

    /**
     * Método que Listara los usuarios para renderizarlos en la vista
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos
     * @since incluido desde la version 1.0
     */
    public void listarUsuarios() throws SQLException {
        listUsuarios.clear();
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sel") != null) {
            selectUser = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sel");
        }
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        if (selectUser.equals("")) {
            l = (ArrayList) CrudObject.getSelectSql("usuario", 1, "param");
        } else {
            l = (ArrayList) CrudObject.getSelectSql("usuarioParam", 1, selectUser);
        }
        for (ConsultaGeneral obj : l) {
            listUsuarios.add(new Usuarios(obj.getNum1(), obj.getStr1(), obj.getStr2(),
                    obj.getStr3(), obj.getStr4(), obj.getStr5(), obj.getStr6(),
                    obj.getStr7(), obj.getStr8(), obj.getStr9(), obj.getStr10(),
                    obj.getStr11(), obj.getStr12(), obj.getNum2(), obj.getNum3(), obj.getNum4()));

        }
        setSelectUser("");
    }

   /*
    * Método que listara los estudiantes para redirigirlos a la vista
    * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
    * al recuperar los datos del estudiante
    */
    public void listarEstudiantes() throws SQLException {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectUser") != null) {
            setSelectUser(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectUser"));
        }
        LoginBean log = new LoginBean();      
        listEstudiantes.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        if (selectUser.equals("")) {
            l = (ArrayList) CrudObject.getSelectSql("estudiante", 1, "");
        } else {
            l = (ArrayList) CrudObject.getSelectSql("estudiantef", 1, "" + selectUser + "");
        }
        for (ConsultaGeneral obj : l) {
            listEstudiantes.add(new Estudiantes(obj.getNum1(), obj.getStr1(),
                    obj.getStr2(), obj.getStr3(), obj.getStr4(), obj.getNum2(), obj.getStr5()));
        }
        setSelectUser("");
    }

    /**
     * Método que Listara las ciudades para renderizarlas en la vista
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos
     * @since incluido desde la version 1.0
     */
    private void lisCiudades() throws SQLException {
        listCiudaes.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CiudadesUtils.getSelectSql("ciudadUtil", 1, "param");
        for (ConsultaGeneral obj : l) {
            listCiudaes.add(new Ciudad(obj.getNum1(), obj.getStr1()));
        }
    }

    /**
     * Método que Listara las empresas para renderizarlas en la vista
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al recuperar los datos
     * @since incluido desde la version 1.0
     */
    private void listEmpresas() throws SQLException {
        listEmpresas.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CiudadesUtils.getSelectSql("empresaUtil", 1, "param");
        for (ConsultaGeneral obj : l) {
            listEmpresas.add(new Empresas(obj.getNum1(), obj.getStr1()));
        }
    }

    /**
     * Método que permitira crear ususarios en el sistema
     *
     * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
     * al intentar insertar los datos
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     * @throws java.text.ParseException
     * @since incluido desde la version 1.0
     */
    public void createUsers() throws SQLException, InterruptedException, IOException, ParseException {
        LoginBean log = new LoginBean();
        UsuariosRoles usuRol = new UsuariosRoles();
        usuario.setUserMod(log.getDocumentoUserLog());
        usuario.setFechaMod(format.format(new Date()));
        usuario.setFechaCreacion(format.format(new Date()));
        usuario.setCambiaClaveAuto("N");
        usuario.setCambioClave("N");
        usuario.setEstado("A");
        usuRol.setEstado("A");
        usuRol.setFechaCreacion(format.format(new Date()));
        usuRol.setFechaMod(format.format(new Date()));
        usuRol.setRol(usuario.getId_rol());
        usuario.setUsuRol(usuRol);
        long a = CrudObject.create(usuario);
        if (a >= 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Usuario Creado"));
            listUsuarios.clear();
            listarUsuarios();
        }
        usuario = null;
        log = null;
    }
    
   /*
    * Método que crea un Estudiante
    * @exception SQLException Error de Sql, Ocurre cuando se presenta un error
    * al intentar insertar los datos del estudiante
    */

    public void createEstudiantes() throws SQLException, InterruptedException, IOException, ParseException {
      
        System.out.println("doc " + estudiante.getDocumento_estudiante());
   
        LoginBean log = new LoginBean();
        
        estudiante.setUsuario_mod(log.getIdUserLog());
        estudiante.setEstado("A");
        long a = CrudObject.create(estudiante);
        if (validarDocumento(estudiante.getDocumento_estudiante())) {
           
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "El número de documento ya fue guardado"));
          
        }
        else if (a >= 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Estudiante Creado"));
            listarEstudiantes();
        } 
     
        
        estudiante = null;
        log = null;
      
    }
    
   /*
    * Método que valida el número de de documento del estudiante ingresado en el sistema
    * @param documento
    */
    
     public boolean validarDocumento(String documento) throws SQLException {
        boolean r = CiudadesUtils.validarDocumentoExist(documento);
        return r;
    }

    
    
   /*
    * Método que edita a un estudiante
    *@throws java.sql.SQLException Error de Sql, Ocurre cuando se presenta un
    * error al editar
    * @throws java.lang.InterruptedException
    * @throws java.io.IOException Error que ocurre al intentar redirigir a la
    * vista EstudiantesList.xhtml
    */
    public void editEstudiantes() throws SQLException, InterruptedException, IOException, ParseException {
        LoginBean log = new LoginBean();
        estudiante.setUsuario_mod(log.getIdUserLog());

        System.out.println("estudiante " + estudiante.toString());

        long a = CrudObject.edit(estudiante);
        if (a >= 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/universidades/EstudiantesList.xhtml");
            listarEstudiantes();

        }
        estudiante = null;
        log = null;

    }

    /**
     * Método que setea el usuario actual a editar
     *
     * @param u
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void prepareEdit(Usuarios u) throws IOException {
        setUsuarios(u);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Usuarios/UsuarioEditar.xhtml");
    }

   /*
    * Método que setea al estudiante actual a editar
    * @param e
    */
    public void prepareEditEstudiante(Estudiantes e) throws IOException {
        System.out.println("e" + e.toString());
        setEstudiante(e);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/universidades/EstudiantesEditar.xhtml");
    }
    
   /*
    * Método que elimina al estudiante seleccionado
    * @throws java.sql.SQLException Error que ocurre al intentar eliminar una
    * un estudiante
    * @throws java.lang.InterruptedException
    * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
    * la vista EstudiantesList.xhtml
    */

    public void deleteEstudiante() throws SQLException, InterruptedException, IOException {
        LoginBean log = new LoginBean();
        estudiante.setUsuario_mod(log.getIdUserLog());

        int a = CrudObject.delete(estudiante);
        System.out.println("estudiante" + estudiante);
        if (a == 1) {

            listarEstudiantes();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/universidades/EstudiantesList.xhtml");
        }
        estudiante = null;
        log = null;
    }

    /**
     * Método que permite editar los Usuarios
     *
     * @throws java.sql.SQLException Error de Sql, Ocurre cuando se presenta un
     * error al editar
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException Error que ocurre al intentar redirigir a la
     * vista CiudadesList.xhtml
     * @since incluido desde la version 1.0
     */
    public void editUser() throws SQLException, InterruptedException, IOException {
        LoginBean log = new LoginBean();
        UsuariosRoles usuRol = new UsuariosRoles();
        usuario.setUserMod(log.getDocumentoUserLog());
        usuario.setFechaMod(format.format(new Date()));
        usuRol.setFechaMod(format.format(new Date()));
        usuRol.setRol(usuario.getId_rol());
        usuario.setUsuRol(usuRol);
        long a = CrudObject.edit(usuario);
        if (a >= 1) {
            listUsuarios.clear();
            listarUsuarios();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Usuarios/UsuarioList.xhtml");
        }
        usuario = null;
        log = null;

    }

    /**
     * Método que setea el Usuario actual para su eliminación
     *
     * @param u
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista CiudadConfirmDelete.xhtml
     * @since incluido desde la version 1.0
     */
    public void confirmDelete(Usuarios u) throws IOException {
        setUsuarios(u);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Usuarios/UsuarioConfirmDelete.xhtml");
    }

   /*
    * Método que setea al estudiante para ser eliminado
    * @param e
    */
    public void confirmDeleteEstudiantes(Estudiantes e) throws IOException {
        System.out.println("estudiantes" + e.toString());
        setEstudiante(e);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/universidades/EstudiantesConfirmDelete.xhtml");
    }

    /**
     * Método que Cancela la eliminacion de un usuario
     *
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista UsuarioList.xhtml
     * @since incluido desde la version 1.0
     */
    public void cancelDelete() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Usuarios/UsuarioList.xhtml");
        usuario = null;
    }
    
    
   /*
    * Método que cancela la eliminación del Estudiante
    *@throws java.io.IOException Error que ocurre cuando intenta redirigir a
    * la vista EstudiantesList.xhtml
    */

    public void cancelDeleteEstudiantes() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/universidades/EstudiantesList.xhtml");
        usuario = null;
    }

    /**
     * Método que elimina el usuario seleccionado
     *
     * @throws java.sql.SQLException Error que ocurre al intentar eliminar una
     * un usuario
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException Error que ocurre cuando intenta redirigir a
     * la vista CiudadesList.xhtml
     * @since incluido desde la version 1.0
     */
    public void delete() throws SQLException, InterruptedException, IOException {
        usuario.setFechaMod(format.format(new Date()));
        if (usuario.getEstado().equals("A")) {
            usuario.setEstado("I");
        } else {
            usuario.setEstado("A");
        }
        int a = CrudObject.delete(usuario);
        if (a == 1) {
            listUsuarios.clear();
            listarUsuarios();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Usuarios/UsuarioList.xhtml");
        }
        usuario = null;
    }
    
    /**
     * Método que Importa los estudiantes desde archivo .xls compatibilidad office
     * 97-2003. Se encarga de leer el archivo local que contiene los registros,
     * una vez lo lee, crea un archivo en el servidor y lo guarda temporalmente,
     * luego lee el archivo temporal y apartir de este realiza las inserciones
     * en la base de datos, al finalizar las validaciones y la insercion de
     * datos el archivo temporal es eliminado.
     *
     * @param event
     * @throws java.io.IOException
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     * @throws java.sql.SQLException
     * @throws java.text.ParseException
     * @throws net.sf.jasperreports.engine.JRException
     * 
     */

    public void handleFileUploadEstudiantes(FileUploadEvent event) throws IOException, InvalidFormatException, SQLException, ParseException, JRException {
        String ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reports/");
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = event.getFile().getInputstream(); //leemos el fichero local
            // write the inputStream to a FileOutputStream
            fec = fmt.format(new Date());
            outputStream = new FileOutputStream(new File(ruta + "/" + fec + event.getFile().getFileName()));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();
            inputStream.close();
            InputStream input = new FileInputStream(new File(ruta + "/" + fec + event.getFile().getFileName()));
            HSSFWorkbook workbook = new HSSFWorkbook(input);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            int indiceFila = -1;
            ArrayList<String> im = new ArrayList<>();
            while (rowIterator.hasNext()) {
                indiceFila++;
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.iterator();
                String obj = "";
                int indiceColumna = -1;
                String s = ",";
                while (cellIterator.hasNext()) {
                    indiceColumna++;
                    Cell cell = cellIterator.next();
                    if (indiceFila == 0) {
                        System.out.println("columna =" + cell.getStringCellValue());
                    } else if (cell != null) {
                        if (indiceColumna == 3) {
                            s = "";
                        }
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC:
                                obj += Integer.toString((int) cell.getNumericCellValue()) + s;
                                break;
                            case Cell.CELL_TYPE_STRING:
                                obj += cell.getStringCellValue() + s;
                                break;
                        }
                    }
                }
                if (!obj.equals("")) {
                    String[] valid = obj.split(",");

                    if (valid.length == 3) {
                        obj += ",Vacio,Vacio";
                    }
                    if (valid.length == 2) {
                        obj += ",Vacio,Vacio,Vacio";
                    }
                    if (valid.length == 1) {
                        obj += ",Vacio,Vacio,Vacio,Vacio";
                    }
                    im.add(obj);
                }
            }
            System.out.println("1**");

            if (im.size() > 0) {
                if (im.size() > 1) {
                    im.stream().forEach((string) -> {
                        String[] parts = string.split(",");
                        LoginBean log = new LoginBean();
                        try {
                            if (parts[0].equals("Vacio") || parts[1].equals("Vacio") || parts[2].equals("Vacio") || parts[3].equals("Vacio") || CiudadesUtils.getExistEstudiante(parts[0])) {
                                countok++;
                                getEstudiante();
                                estudiante.setDocumento_estudiante(parts[0]);
                                estudiante.setCodigo_estudiante(parts[1]);
                                estudiante.setNombre_estudiante(parts[2]);
                                estudiante.setUniversidad(parts[3]);

                                logUsers.add(estudiante);
                                setEstudiante(null);
                            } else {
                                  System.out.println("2**");
                                getEstudiante();
                                usuario = getUsuarios();
                                estudiante.setDocumento_estudiante(parts[0]);
                                estudiante.setCodigo_estudiante(parts[1]);
                                estudiante.setNombre_estudiante(parts[2]);
                                estudiante.setUniversidad(parts[3]);
                                estudiante.setUsuario_mod(log.getIdUserLog());
                                estudiante.setEstado("A");

                                estudiantesInsert.add(estudiante);

                                setEstudiante(null);

                                log = null;
                            }
                        } catch (SQLException ex) {
                            System.out.println("error " + ex);
                        }
                    });
                } else {
                      System.out.println("3**");
                    String[] parts = im.get(0).split(",");
                    usuario = getUsuarios();
                    LoginBean log = new LoginBean();
                    getEstudiante();
                    usuario = getUsuarios();
                    estudiante.setDocumento_estudiante(parts[0]);
                    estudiante.setCodigo_estudiante(parts[1]);
                    estudiante.setNombre_estudiante(parts[2]);
                    estudiante.setUniversidad(parts[3]);
                    estudiante.setUsuario_mod(log.getIdUserLog());
                    estudiante.setEstado("A");

                    estudiantesInsert.add(estudiante);

                    setEstudiante(null);

                    log = null;
                }
                  System.out.println("4**");
                input.close();
                File fichero = new File(ruta + "/" + fec + event.getFile().getFileName());
                FileReader fr = new FileReader(fichero);
                try {
                    if (null != fr) {
                        fr.close();
                    }
                    fichero.delete();
                    System.err.println("Eliminado");
                } catch (IOException ex) {
                    System.err.println("Error en el cierre del archivo");
                }
                im.clear();
                try {
                     System.out.println("5**");
                     System.out.println("estudiantesInsert.size() "+estudiantesInsert.size());
                    if (estudiantesInsert.size() > 0) {
                          System.out.println("6**");
                        long a = CrudObject.create3(estudiantesInsert);
                        setEstudiante(null);
                        estudiantesInsert.clear();
                    }
                } catch (SQLException | ParseException e) {
                    System.out.println("error " + e);
                }
                System.out.println("7**");
                if (countok == 0) {//falta
                    setMsn("Archivo Importado");
                    FacesMessage message = new FacesMessage("Información", event.getFile().getFileName() + " " + getMsn());
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    listEstudiantes.clear();
                    listarEstudiantes();
                } else {
                    setMsn("Archivo Importado con " + countok + " errores, por favor compare el log con el archivo original\n es posible que los usuarios ya se enncuentren en el sistema, o hay datos vacios..!");
                    setValidate(true);
                    listEstudiantes.clear();
                    listarEstudiantes();;
                    countok = 0;
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/universidades/EstudiantesList.xhtml");
                }

            } else {
                FacesMessage message = new FacesMessage("Información", event.getFile().getFileName() + "Archivo vacio");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } catch (IOException e) {
            System.out.println("error " + e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }

    }
    
  

    public String getEditarUsuario() {
        return EditarUsuario;
    }

    public void setEditarUsuario(String EditarUsuario) {
        this.EditarUsuario = EditarUsuario;
    }

    public List<Usuarios> getListUsuarios() {
        return listUsuarios;
    }

    public String getCrearUsuario() {
        usuario = null;
        return Crearusuario;
    }

    public String getListUsuario() throws SQLException {
        listUsuarios.clear();
        listarUsuarios();
        listEmpresas();
        return ListUsuario;
    }

    public void setListUsuario(String ListUsuario) {
        this.ListUsuario = ListUsuario;
    }

    public void setCrearusuario(String Crearusuario) {
        this.Crearusuario = Crearusuario;
    }

    public void setListUsuarios(List<Usuarios> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    public Usuarios getUsuarios() {
        if (usuario == null) {
            usuario = new Usuarios();
        }
        return usuario;
    }

    public void setUsuarios(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public List<Ciudad> getListCiudaes() {
        return listCiudaes;
    }

    public void setListCiudaes(List<Ciudad> listCiudaes) {
        this.listCiudaes = listCiudaes;
    }

    public List<Empresas> getListEmpresas() {
        return listEmpresas;
    }

    public void setListEmpresas(List<Empresas> listEmpresas) {
        this.listEmpresas = listEmpresas;
    }

    public List<Roles> getListRoles() {
        return listRoles;
    }

    public void setListRoles(List<Roles> listRoles) {
        this.listRoles = listRoles;
    }

    public String getSelectUser() {
        return selectUser;
    }

    public void setSelectUser(String selectUser) {
        this.selectUser = selectUser;
    }

    public List<Estudiantes> getListEstudiantes() {
        return listEstudiantes;
    }

    public void setListEstudiantes(List<Estudiantes> listEstudiantes) {

        this.listEstudiantes = listEstudiantes;
    }

    public String getListaEstudiantes() throws SQLException {
        listarEstudiantes();
        setEstudiante(null);
        return ListaEstudiantes;
    }

    public void setListaEstudiantes(String ListaEstudiantes) {
        this.ListaEstudiantes = ListaEstudiantes;
    }

    public Estudiantes getEstudiante() {
        if (estudiante == null) {
            estudiante = new Estudiantes();
        }
        return estudiante;
    }

    public void setEstudiante(Estudiantes estudiante) {
        this.estudiante = estudiante;
    }

    public String getCrearEstudiante() {
        return CrearEstudiante;
    }

    public void setCrearEstudiante(String CrearEstudiante) {
        this.CrearEstudiante = CrearEstudiante;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public List<Tiquete_Estudiante> getListUniversidad() {
        return listUniversidad;
    }

    public void setListUniversidad(List<Tiquete_Estudiante> listUniversidad) {
        this.listUniversidad = listUniversidad;
    }

   

    

}
