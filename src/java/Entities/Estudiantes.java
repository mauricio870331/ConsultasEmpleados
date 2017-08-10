/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;

/**
 *
 * @author cnoguera
 */
public class Estudiantes implements Serializable {

    private int id_estudiante;
    private String documento_estudiante;
    private String codigo_estudiante;
    private String nombre_estudiante;
    private String universidad;
    private int usuario_mod;
    private String usuariomod_str;
    private String estado;
    private Tiquete_Estudiante objEstudiante;

    public Estudiantes() {
    }

    public Estudiantes(String universidad) {

        this.universidad = universidad;
    }

    @Override
    public String toString() {
        return "Estudiantes{" + "id_estudiante=" + id_estudiante + ", documento_estudiante=" + documento_estudiante + ", codigo_estudiante=" + codigo_estudiante + ", nombre_estudiante=" + nombre_estudiante + ", universidad=" + universidad + ", usuario_mod=" + usuario_mod + '}';
    }

    public Estudiantes(int id_estudiante, String documento_estudiante, String codigo_estudiante, String nombre_estudiante, String universidad, int usuario_mod, String usuariomod_str) {
        this.id_estudiante = id_estudiante;
        this.documento_estudiante = documento_estudiante;
        this.codigo_estudiante = codigo_estudiante;
        this.nombre_estudiante = nombre_estudiante;
        this.universidad = universidad;
        this.usuario_mod = usuario_mod;
        this.usuariomod_str = usuariomod_str;
    }

    public Estudiantes(String documento_estudiante, String codigo_estudiante, String nombre_estudiante, String universidad, int usuario_mod) {

        this.documento_estudiante = documento_estudiante;
        this.codigo_estudiante = codigo_estudiante;
        this.nombre_estudiante = nombre_estudiante;
        this.universidad = universidad;
        this.usuario_mod = usuario_mod;
    }

    public int getId_estudiante() {
        return id_estudiante;
    }

    public void setId_estudiante(int id_estudiante) {
        this.id_estudiante = id_estudiante;
    }

    public String getDocumento_estudiante() {
        return documento_estudiante;
    }

    public void setDocumento_estudiante(String documento_estudiante) {
        this.documento_estudiante = documento_estudiante;
    }

    public String getCodigo_estudiante() {
        return codigo_estudiante;
    }

    public void setCodigo_estudiante(String codigo_estudiante) {
        this.codigo_estudiante = codigo_estudiante;
    }

    public String getNombre_estudiante() {
        return nombre_estudiante;
    }

    public void setNombre_estudiante(String nombre_estudiante) {
        this.nombre_estudiante = nombre_estudiante;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public int getUsuario_mod() {
        return usuario_mod;
    }

    public void setUsuario_mod(int usuario_mod) {
        this.usuario_mod = usuario_mod;
    }

    public String getUsuariomod_str() {
        return usuariomod_str;
    }

    public void setUsuariomod_str(String usuariomod_str) {
        this.usuariomod_str = usuariomod_str;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Tiquete_Estudiante getObjEstudiante() {
        if (objEstudiante == null) {
            objEstudiante = new Tiquete_Estudiante();
        }
        return objEstudiante;
    }

    public void setObjEstudiante(Tiquete_Estudiante objEstudiante) {
        this.objEstudiante = objEstudiante;
    }

}
