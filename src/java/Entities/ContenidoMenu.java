/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */

public class ContenidoMenu implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Integer idContentMenu;

    private String descripcion;
   
    private String link;
    
    private Menu idMenu;

    public ContenidoMenu() {
    }

    public ContenidoMenu(Integer idContentMenu) {
        this.idContentMenu = idContentMenu;
    }

    public ContenidoMenu(Integer idContentMenu, String descripcion, String link) {
        this.idContentMenu = idContentMenu;
        this.descripcion = descripcion;
        this.link = link;
    }

    public Integer getIdContentMenu() {
        return idContentMenu;
    }

    public void setIdContentMenu(Integer idContentMenu) {
        this.idContentMenu = idContentMenu;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Menu getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Menu idMenu) {
        this.idMenu = idMenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContentMenu != null ? idContentMenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContenidoMenu)) {
            return false;
        }
        ContenidoMenu other = (ContenidoMenu) object;
        if ((this.idContentMenu == null && other.idContentMenu != null) || (this.idContentMenu != null && !this.idContentMenu.equals(other.idContentMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ContenidoMenu[ idContentMenu=" + idContentMenu + " ]";
    }
    
}
