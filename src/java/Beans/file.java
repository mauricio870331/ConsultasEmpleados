/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author admin
 */
@Named(value = "file")
@SessionScoped
public class file implements Serializable {

    /**
     * Creates a new instance of file
     */
    private UploadedFile  ruta;
    public file() {
    }
    
    public void prueba(){
        System.out.println("file : " + ruta.getFileName());
    }

    public UploadedFile getRuta() {
        return ruta;
    }

    public void setRuta(UploadedFile ruta) {
        this.ruta = ruta;
    }

   
    
}
