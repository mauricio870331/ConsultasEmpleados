package Beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
@Named(value = "listaentregaBean")
@SessionScoped
public class ListaentregaBean implements Serializable {

    /**
     * Variable privada: Cedula. Es el documento del empleado al que se le va a
     * entregar viajes
     */
    private String Cedula;

    public ListaentregaBean() {
    }

    public void buscarTiquete() {
        System.out.println("- palabra :  " + Cedula);
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }

}
