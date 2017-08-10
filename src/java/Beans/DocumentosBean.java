package Beans;

import Entities.*;
import Modelo.ConsultaGeneral;
import Modelo.CrudObject;
import java.io.File;
import java.io.FileNotFoundException;
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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.component.growl.Growl;
import org.primefaces.event.FileUploadEvent;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
@Named(value = "documentosBean")
@SessionScoped
public class DocumentosBean implements Serializable {

    /**
     * Variable privada: a. para control de mensajes
     */
    long a = -1;

    /**
     * Variable privada: documentos. Contendra el listado de documentos
     */
    private List<Docs> documentos = new ArrayList();
    private List<Alertas> listAlertas = new ArrayList();

    private Alertas alert;
    private String now;

    /**
     * Variable: ListDocs. Variable para la navegacion. vista DocsList.xhtml
     */
    String ListDocs = "../Docs/DocsList.xhtml";
    String ListAlertasView = "../Taquilla/AlertList.xhtml";
    String ListAlertasAdmin = "../Docs/AlertList.xhtml";
    String CrearAlertas = "../Docs/AlertCrear.xhtml";

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Variable privada: doc. almacenara el objeto Docs actual seleccionado
     */
    private Docs doc;
    private int countAlert;

    Growl growl = new Growl();

    public DocumentosBean() {
    }

    @PostConstruct
    public void init() {
        growl.setLife(5000);
    }

    private void listarDocumentos(String vista, String param) throws SQLException {
        documentos.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CrudObject.getSelectSql("docs", 1, "param");
        for (ConsultaGeneral obj : l) {
            documentos.add(new Docs(obj.getNum1(), obj.getStr1(), obj.getStr2(), obj.getStr3(), obj.getFecha1(), obj.getNum1()));
        }
        if (a == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Error al eliminar, comuniquese con el administrador"));
            a = -1;
        }
    }

    public void cancelDelete() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Docs/DocsList.xhtml");
        doc = null;
    }

    public void confirmDelete(Docs d) throws IOException {
        setDoc(d);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Docs/DocsConfirmDelete.xhtml");
    }

    public void verDocumento(String ruta) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(ruta);
    }

    public void deleteDoc() throws FileNotFoundException {
        File fichero2 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Documentos/" + doc.getNombre()));
        File fichero = new File(doc.getReal_path());
        FileReader fr = new FileReader(fichero);
        FileReader fr2 = new FileReader(fichero2);
        try {
            a = CrudObject.delete(doc);
            if (a == 1) {
                if (null != fr) {
                    fr.close();
                }
                if (null != fr2) {
                    fr2.close();
                }
                fichero.delete();
                fichero2.delete();
                listarDocumentos("docs", "param");
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Docs/DocsList.xhtml");
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Docs/DocsList.xhtml");
            }
            doc = null;
        } catch (SQLException | IOException e) {
            System.err.println("Error " + e);
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException, SQLException, ParseException {
        String ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Documentos");
        String nuevaRuta = ruta.replace("build\\", "");
        InputStream inputStream = null;
        OutputStream outputStream = null;
        OutputStream outputStream2 = null;
        String[] parts = event.getFile().getFileName().split("_");
        System.out.println("nomb = " + parts[1]);
        int tipo = Integer.parseInt(parts[1].substring(0, 1));
        try {
            getDoc();
            doc.setNombre(parts[0].replace("+", "") + ".pdf");
            doc.setRuta("../../Documentos/");
            doc.setReal_path(nuevaRuta + "\\" + parts[0].replace("+", "") + ".pdf");
            doc.setFecha_mod(new Date());
            doc.setTipo(tipo);
            a = CrudObject.create(doc);
            if (a == 1) {
                inputStream = event.getFile().getInputstream(); //leemos el fichero local
                // write the inputStream to a FileOutputStream          
                outputStream = new FileOutputStream(new File(nuevaRuta + "/" + parts[0].replace("+", "") + ".pdf"));
                outputStream2 = new FileOutputStream(new File(ruta + "/" + parts[0].replace("+", "") + ".pdf"));
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                    outputStream2.write(bytes, 0, read);
                }
                outputStream.close();
                outputStream2.close();
                inputStream.close();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Documento Subido"));
                listarDocumentos("docs", "param");
            }
            doc = null;
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

    public void createAlerts() throws SQLException, ParseException {
        alert.setFecha_creacion(new Date());
        a = CrudObject.create(alert);
        if (a == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Alerta creada"));
//            listarDocumentos();

        }
        alert = null;
    }

    public void listAlerts(String vista) throws SQLException {
        listAlertas.clear();
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        l = (ArrayList) CrudObject.getSelectSql(vista, 1, format.format(new Date()));
        for (ConsultaGeneral obj : l) {
            listAlertas.add(new Alertas(obj.getNum1(), obj.getStr1(), obj.getFecha1(), obj.getStr2(), obj.getStr3(), obj.getFecha2(), obj.getFecha3()));
        }
    }

    public void showalerts() throws SQLException {
        countAlerts();
        listAlerts("alertList");
    }

    public void countAlerts() throws SQLException {      
        setCountAlert(Utils.CiudadesUtils.countAlertas());
        setNow(format.format(new Date()));
    }

    public void prepareEdit(Alertas a) throws IOException {
        setAlert(a);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Docs/AlertEditar.xhtml");
    }

    public void editAlertas() throws SQLException, InterruptedException, IOException {
        a = CrudObject.edit(alert);
        if (a == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Docs/AlertList.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se pudo actualizar la alerta"));
        }
        alert = null;
    }

    public void confirmDeleteAlert(Alertas a) throws IOException {
        setAlert(a);
        System.out.println("a " + a.getId());
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Docs/AlertConfirmDelete.xhtml");
    }

    public void cancelDeleteAler() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Docs/AlertList.xhtml");
        alert = null;
    }

    public void deleteAlert() throws SQLException, InterruptedException, IOException {
        a = CrudObject.delete(alert);
        if (a == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Convenios/faces/Admin/Docs/AlertList.xhtml");
            listAlerts("alertListAdm");
        }
        alert = null;
    }

    public String getListDocs() throws SQLException {
        listarDocumentos("docs", "param");
        return ListDocs;
    }

    public void setListDocs(String ListDocs) {
        this.ListDocs = ListDocs;
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public List<Docs> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Docs> documentos) {
        this.documentos = documentos;
    }

    public Docs getDoc() {
        if (doc == null) {
            doc = new Docs();
        }
        return doc;
    }

    public void setDoc(Docs doc) {
        this.doc = doc;
    }

    public int getCountAlert() {
        return countAlert;
    }

    public String getListAlertasView() throws SQLException {
        listAlerts("alertList");
        return ListAlertasView;
    }

    public String getListAlertasAdmin() throws SQLException {
        listAlerts("alertListAdm");
        return ListAlertasAdmin;
    }

    public void setListAlertasAdmin(String ListAlertasAdmin) {
        this.ListAlertasAdmin = ListAlertasAdmin;
    }

    public void setListAlertasView(String ListAlertasView) {
        this.ListAlertasView = ListAlertasView;
    }

    public void setCountAlert(int countAlert) {
        this.countAlert = countAlert;
    }

    public List<Alertas> getListAlertas() {
        return listAlertas;
    }

    public void setListAlertas(List<Alertas> listAlertas) {
        this.listAlertas = listAlertas;
    }

    public Alertas getAlert() {
        if (alert == null) {
            alert = new Alertas();
        }
        return alert;
    }

    public void setAlert(Alertas alert) {
        this.alert = alert;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getCrearAlertas() {
        alert = null;
        return CrearAlertas;
    }

    public void setCrearAlertas(String CrearAlertas) {
        this.CrearAlertas = CrearAlertas;
    }
}
