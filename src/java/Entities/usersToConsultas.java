/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author clopez
 */
public class usersToConsultas implements Serializable {

    private int id_trans;
    private String nickName;
    private String correo;
    private String recibirCorreos;
    private String documento;
    private String pass;
    private String rep_pass;
    private Date fecha_exp_cc;

    public usersToConsultas(String documento, String pass) {
        this.documento = documento;
        this.pass = pass;
    }

    public usersToConsultas(int id_trans, String nickName, String correo, String recibirCorreos, String documento, String pass, Date fecha_exp_cc) {
        this.id_trans = id_trans;
        this.nickName = nickName;
        this.correo = correo;
        this.recibirCorreos = recibirCorreos;
        this.documento = documento;
        this.pass = pass;
        this.fecha_exp_cc = fecha_exp_cc;
    }

    public usersToConsultas() {

    }

    @Override
    public String toString() {
        return "usersToConsultas{" + "id_trans=" + id_trans + ", nickName=" + nickName + ", correo=" + correo + ", recibirCorreos=" + recibirCorreos + ", documento=" + documento + ", pass=" + pass + ", rep_pass=" + rep_pass + ", fecha_exp_cc=" + fecha_exp_cc + '}';
    }

   

    public int getId_trans() {
        return id_trans;
    }

    public void setId_trans(int id_trans) {
        this.id_trans = id_trans;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRecibirCorreos() {
        return recibirCorreos;
    }

    public void setRecibirCorreos(String recibirCorreos) {
        this.recibirCorreos = recibirCorreos;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Date getFecha_exp_cc() {
        return fecha_exp_cc;
    }

    public void setFecha_exp_cc(Date fecha_exp_cc) {
        this.fecha_exp_cc = fecha_exp_cc;
    }

    public String getRep_pass() {
        return rep_pass;
    }

    public void setRep_pass(String rep_pass) {
        this.rep_pass = rep_pass;
    }

}
