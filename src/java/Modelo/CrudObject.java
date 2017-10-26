package Modelo;

import Entities.*;
import Utils.CiudadesUtils;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
public class CrudObject {

    static ConexionPool pool = new ConexionPool();

//    static Connection cn;
    static PreparedStatement pstm;
    static ResultSet rs;
    static CallableStatement cstmt;
    static DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

    /**
     * Método estatico que permite realizar inserciones en la base de datos
     * apartir del objeto pasado como parametro, valida el tipo de instancia a
     * la que se refiere el objeto y posteriormente procede a enviar a la base
     * de datos la cadena de insercion
     *
     * @param x es el objeto que viene como parametro para validar e ingresarlo
     * a la bd
     * @return 0 : Error al crear SQL 1 : Exito al crear Query 2 : No se
     * reconoce Object
     * @throws java.sql.SQLException
     * @throws java.text.ParseException
     */
    public static long create(Object x) throws SQLException, ParseException {
        String tbl = "tablas";
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        boolean validacion = false;
        Estados estado = null;
        Empresas empresa = null;
        Estudiantes estudiante = null;
        Tiquete_Estudiante tiquete = null;
        Ciudad ciudad = null;
        Roles rol = null;
        Usuarios usuarios = null;
        TblusuarioRegistro registro = null;
        TblRegistroContravias contravias = null;
        TblServicios servicios = null;
        TblConvenio convenio = null;
        DetalleConvenio detalleconvenio = null;
        TblEmpleados empleado = null;
        TblViajesTiquetes viajesTiquetes = null;
        Docs documento = null;
        Alertas alertas = null;
        CmGenerado cmgen = null;
        TiquetesAutorizados tiquetesAutorizados = null;
        String preparet = "";
        long mns = 0;
        if (x instanceof Estados) {
            estado = (Estados) x;
            preparet = "insert into Estados values(" + estado.getDescripcion() + ")";
            validacion = true;
        } else if (x instanceof Empresas) {
            empresa = (Empresas) x;
            preparet = "insert into tbl_empresas values(\"" + empresa.getNit() + "\",\"" + empresa.getNombre() + "\",\"" + empresa.getDireccion() + "\",\"" + empresa.getTelefono() + "\",\"" + empresa.getCorreo() + "\",\""
                    + empresa.getUser_mod() + "\",\"" + empresa.getFecha_mod() + "\"," + empresa.getCod_ciudad() + ",\"" + empresa.getFecha_creacion() + "\"," + empresa.getCod_estado() + ")";
            validacion = true;
        } else if (x instanceof TblusuarioRegistro) {
            registro = (TblusuarioRegistro) x;
            tbl = "tablas";
            preparet = "insert into tbl_usuarioRegistro values(\"" + registro.getCliente() + "\",\"" + registro.getNombre() + "\",\"" + registro.getDocumento() + "\",\""
                    + registro.getOt() + "\"," + registro.getCodCiudadOrigen() + "," + registro.getCodCiudadDestino() + "," + registro.getValor() + "," + registro.getCantidad() + ",\""
                    + registro.getIdaRegreso() + "\"," + registro.getTotal() + ",\"" + registro.getCodBus() + "\",\"" + registro.getObservacion() + "\",\"" + registro.getTiquete() + "\","
                    + registro.getIdEmpresa() + ",\"" + registro.getUserMod() + "\",\"" + registro.getTaquilla() + "\"," + registro.getValorSin() + ",\"" + format2.format(registro.getFechaCreacion())
                    + "\"," + registro.getTotalsin() + ",\"" + format.format(registro.getFechaviaje()) + "\",\"" + registro.getUsuarioNodum() + "\",\"" + registro.getClaveNodum() + "\",\"" + registro.getStrConfirmRegistro()
                    + "\",\"" + registro.getUsuarioTaquilla() + "\",\"" + registro.getTransaccion() + "\"," + 0 + ")";
            if (registro.getTipocontratotemp().equals("Cerrado")) {
                preparet += ";UPDATE tbl_convenio set saldoValor_global = saldoValor_global +" + registro.getTotal() + "";
            }
            validacion = true;
        } else if (x instanceof Ciudad) {
            ciudad = (Ciudad) x;
            preparet = "insert into tbl_ciudades values(\"" + ciudad.getCiudad() + "\",\"" + ciudad.getUser_mod() + "\",\"" + ciudad.getFecha_mod() + "\",\"" + ciudad.getFecha_creacion() + "\")";
            validacion = true;
        } else if (x instanceof Roles) {
            rol = (Roles) x;
            preparet = "insert into roles values(\"" + rol.getRol() + "\",\"" + rol.getUserMod() + "\",\"" + rol.getFechaMod() + "\")";
            validacion = true;
        } else if (x instanceof Usuarios) {
            tbl = "usuarios";
            usuarios = (Usuarios) x;
            preparet = "insert into usuarios values(\"" + usuarios.getDocumento() + "\",\"" + usuarios.getNombres() + "\",\"" + usuarios.getApellidos()
                    + "\",\"" + usuarios.getClave() + "\",\"" + usuarios.getCambioClave() + "\",\"" + usuarios.getEstado() + "\",\"" + usuarios.getCambiaClaveAuto()
                    + "\",\"" + usuarios.getCodCiudad() + "\",\"" + usuarios.getIdEmpresa() + "\",\"" + usuarios.getUserMod() + "\",\"" + usuarios.getFechaMod() + "\",\""
                    + usuarios.getFechaCreacion() + "\");insert into usuarios_roles values(#1,\"" + usuarios.getUsuRol().getEstado() + "\",\""
                    + usuarios.getUsuRol().getFechaCreacion() + "\",\"" + usuarios.getUsuRol().getFechaMod() + "\"," + usuarios.getUsuRol().getRol() + ")";
            validacion = true;
        } else if (x instanceof TblServicios) {
            servicios = (TblServicios) x;
            preparet = "insert into tbl_servicios values(\"" + servicios.getServicio() + "\",\"" + servicios.getUserMod() + "\",\"" + servicios.getFechaMod() + "\",\"" + servicios.getFechaCreacion() + "\")";
            validacion = true;
        } else if (x instanceof Docs) {
            documento = (Docs) x;
            preparet = "insert into documentos values(\"" + documento.getNombre() + "\",\"" + documento.getRuta() + "\",\"" + documento.getReal_path() + "\",\"" + format.format(documento.getFecha_mod()) + "\",\"" + documento.getTipo() + "\")";
            validacion = true;
        } else if (x instanceof Alertas) {
            alertas = (Alertas) x;
            preparet = "insert into alertas values(\"" + alertas.getDescripcion() + "\",\"" + format.format(alertas.getFecha_creacion()) + "\",\"" + alertas.getRelevancia() + "\",\"" + alertas.getTitulo() + "\",\"" + format.format(alertas.getFecha_desde()) + " 00:00:00\",\"" + format.format(alertas.getFecha_hasta()) + " 23:59:59\")";
            validacion = true;
        } else if (x instanceof TblConvenio) {
            convenio = (TblConvenio) x;
            preparet = "insert into tbl_convenio values(\"" + convenio.getIdempresa() + "\",\"" + convenio.getUser_mod() + "\",\"" + convenio.getFechaMod() + "\",\"" + format.format(convenio.getDfechaInicial())
                    + "\",\"" + format.format(convenio.getDfechaFinal()) + "\",\"" + convenio.getDescripcion() + "\",\"" + convenio.getValorGlobal()
                    + "\",\"" + convenio.getEstado()
                    + "\",\"" + convenio.getSaldoValorGlobal()
                    + "\",\"" + convenio.getTipoContrato() + "\")";
            validacion = true;
        } else if (x instanceof DetalleConvenio) {
            detalleconvenio = (DetalleConvenio) x;
            preparet = "insert into detalle_convenio values(\"" + detalleconvenio.getId_convenio()
                    + "\",\"" + detalleconvenio.getId_empresa()
                    + "\",\"" + detalleconvenio.getValorCliente()
                    + "\",\"" + detalleconvenio.getValorExpal()
                    + "\",\"" + detalleconvenio.getServicio()
                    + "\",\"" + detalleconvenio.getCodCiudadOrigen().getCodCiudad()
                    + "\",\"" + detalleconvenio.getCodCiudadDestino().getCodCiudad()
                    + "\",\"" + format.format(detalleconvenio.getFechaInicial())
                    + "\",\"" + format.format(detalleconvenio.getFechaFinal())
                    + "\",\"" + detalleconvenio.getCod_estado()
                    + "\",\"" + detalleconvenio.getIdUsuario()
                    + "\",\"" + detalleconvenio.getFechaCreacion()
                    + "\",\"" + detalleconvenio.getEmpalme() + "\")";
            validacion = true;
        } else if (x instanceof TblEmpleados) {
            empleado = (TblEmpleados) x;
            preparet = "insert into tbl_empleados values(\"" + empleado.getDocumento() + "\",\"" + empleado.getNombre() + "\",\"" + empleado.getTelefono()
                    + "\",\"" + empleado.getCorreo() + "\",\"" + empleado.getOt() + "\",\"" + empleado.getTiquetesPendientes() + "\",\"" + empleado.getUserMod()
                    + "\",\"" + format.format(empleado.getFechaMod()) + "\",\"" + format.format(empleado.getFechaCreacion())
                    + "\");insert into empresa_empleado values(\"" + empleado.getEmpresaEmpleado().getDocumento() + "\",\""
                    + empleado.getEmpresaEmpleado().getIdEmpresa() + "\",\"" + format.format(empleado.getEmpresaEmpleado().getFechaCreacion()) + "\"," + empleado.getEmpresaEmpleado().getCodEstado() + ")";
            validacion = true;
        } else if (x instanceof TblViajesTiquetes) {
            viajesTiquetes = (TblViajesTiquetes) x;
            preparet = "insert into tbl_viajes_tiquetes values(\"" + viajesTiquetes.getDocumento() + "\",\"" + viajesTiquetes.getIdEmpresa() + "\",\"" + viajesTiquetes.getDetalleConvPk()
                    + "\",\"" + viajesTiquetes.getIdConvenio() + "\",\"" + format.format(viajesTiquetes.getFechaInicial())
                    + "\",\"" + format.format(viajesTiquetes.getFechaFinal()) + "\",\"" + viajesTiquetes.getIdaRegreso() + "\",\"" + Integer.parseInt(viajesTiquetes.getStrtTquetesAsignados())
                    + "\",\"" + viajesTiquetes.getTiquetesEntregados() + "\",\"" + viajesTiquetes.getUserMod() + "\",\"" + format2.format(viajesTiquetes.getFechaMod()) + "\",\"" + 1 + "\",\"" + viajesTiquetes.getOs() + "\")";
            validacion = true;
        } else if (x instanceof TblRegistroContravias) {
            contravias = (TblRegistroContravias) x;
            preparet = "insert into tbl_registroContravias values(\"" + contravias.getTransaccion() + "\",\"" + contravias.getId_empresa() + "\",\"" + contravias.getNombre_comprador()
                    + "\",\"" + contravias.getCc_comprador() + "\",\"" + contravias.getNombre_viajero()
                    + "\",\"" + contravias.getCc_viajero() + "\",\"" + contravias.getOrigen() + "\",\"" + contravias.getDestino()
                    + "\"," + contravias.getValor() + "," + contravias.getCant_tiquetes() + "," + contravias.getTotal()
                    + ",\"" + contravias.getIda_regreso() + "\",\"" + contravias.getCod_bus() + "\",\"" + contravias.getObservacion()
                    + "\",\"" + format2.format(contravias.getFecha_creacion()) + "\",\"" + contravias.getNo_tiquete() + "\",\"" + contravias.getTaquilla_vende()
                    + "\",\"" + contravias.getUserNodumVende() + "\",\"" + contravias.getUsuarioTaquilla_vende()
                    + "\",\"" + contravias.getServicio() + "\"," + contravias.getPiso()
                    + ",\"" + contravias.getEstado()
                    + "\",\"" + contravias.getTaquilla_entrega() + "\",\"" + contravias.getUserNodumEntrega() + "\",\"" + contravias.getUsuarioTaquillaEntrega()
                    + "\",\"" + contravias.getTelefonoComprador() + "\",\"" + contravias.getNo_reserva() + "\"," + contravias.getReimprimir()
                    + "," + contravias.getDineroEnCasa() + "," + null
                    + ",\"" + contravias.getUserMod() + "\")";
            validacion = true;
        } else if (x instanceof CmGenerado) {
            cmgen = (CmGenerado) x;
            preparet = "insert into relacion_recibos values(\"" + cmgen.getId_trans()
                    + "\",\"" + cmgen.getAgencia()
                    + "\",\"" + format2.format(cmgen.getFecha_creacion())
                    + "\"," + 0
                    + "," + "NULL"
                    + "," + 0 + "," + 0 + ")";
            if (cmgen.getListDetalleCm().size() > 0) {
                for (DetalleCm next : cmgen.getListDetalleCm()) {
                    if (next.getTabla().equals("Manual")) {
                        preparet += ";insert into recibos_manuales values(\"" + next.getPagado_a()
                                + "\",\"" + next.getCc_nit()
                                + "\",\"" + next.getConcepto()
                                + "\"," + next.getValor()
                                + ",\"" + format2.format(new Date())
                                + "\",\"" + next.getCm_asoc()
                                + "\",\"" + cmgen.getId_trans() + "\")";
                    } else {
                        preparet += ";insert into detalle_relacion values(" + next.getIdtrans()
                                + ",\"" + cmgen.getId_trans() + "\",\"" + next.getCm_asoc() + "\",\"" + next.getTabla() + "\",\"" + next.getDescripcion() + "\")";
                    }
                }
            }
            if (cmgen.getTransUpdate() != null && cmgen.getTransUpdate().size() > 0) {
                for (String nex : cmgen.getTransUpdate()) {
                    String[] parts = nex.split(",");
                    switch (parts[1]) {
                        case "transaccion":
                            preparet += ";update transaccion_viajero set cmgenerado = 1 where id_transaccion = " + parts[0] + "";
                            break;
                        case "tbl_usuarioRegistro":
                            preparet += ";update tbl_usuarioRegistro set cmgenerado = 1 where id_registro = " + parts[0] + "";
                            break;
                        default:
                            break;
                    }
                }
            }
            validacion = true;
        } else if (x instanceof Estudiantes) {
            estudiante = (Estudiantes) x;
            preparet = "insert into estudiante_convenios values(\"" + estudiante.getDocumento_estudiante()
                    + "\",\"" + estudiante.getCodigo_estudiante()
                    + "\",\"" + estudiante.getNombre_estudiante()
                    + "\",\"" + estudiante.getUniversidad() + "\"," + estudiante.getUsuario_mod() + ",\"" + estudiante.getEstado() + "\")";
            validacion = true;
        } else if (x instanceof Tiquete_Estudiante) {
            tiquete = (Tiquete_Estudiante) x;
            preparet = "insert into tiquete_estudiante values(\"" + tiquete.getDoc_estudiante()
                    + "\",\"" + tiquete.getNumero_tiquete()
                    + "\",\"" + format2.format(tiquete.getFecha_entrega())
                    + "\",\"" + tiquete.getTaquilla_entrega()
                    + "\",\"" + tiquete.getUsuario_taquilla() + "\")";
            System.out.println(preparet);
            validacion = true;
        } else if (x instanceof TiquetesAutorizados) {
            tiquetesAutorizados = (TiquetesAutorizados) x;
            preparet = "insert into tiquetes_autorizados values(\"" + tiquetesAutorizados.getDocumento().replace(".", "")
                    + "\",\"" + tiquetesAutorizados.getNombre_completo()
                    + "\",\"" + tiquetesAutorizados.getTelefono()
                    + "\",\"" + tiquetesAutorizados.getOrigen()
                    + "\",\"" + tiquetesAutorizados.getDestino()
                    + "\",\"" + tiquetesAutorizados.getMotivo_solicitud()
                    + "\",\"" + format2.format(tiquetesAutorizados.getFecha_solicitud())
                    + "\",\"" + tiquetesAutorizados.getEstado()
                    + "\"," + tiquetesAutorizados.getFecha_entrega()
                    + ",\"" + tiquetesAutorizados.getTipo_servicio()
                    + "\",\"" + tiquetesAutorizados.getTaquilla_entrega()
                    + "\",\"" + tiquetesAutorizados.getUsuario_taquilla()
                    + "\",\"" + tiquetesAutorizados.getUsaurio_solicita()
                    + "\",\"" + tiquetesAutorizados.getTiquete()
                    + "\",\"" + tiquetesAutorizados.getIdaRegreso() + "\")";
            validacion = true;
        }
        if (validacion) {
            try {
                System.out.println("preparet " + preparet);
                pool.con = pool.dataSource.getConnection();
                cstmt = pool.con.prepareCall("{call CrudObject (?,?,?)}");
                cstmt.setString(1, preparet);
                cstmt.setInt(2, 0);
                cstmt.setString(3, tbl);
                ResultSet rss = cstmt.executeQuery();
                long result = 0;
                while (rss.next()) {
                    result = rss.getInt(1);
                }
                mns = result;
                System.out.println("result " + mns);
            } catch (Exception ex) {
                System.out.println("error " + ex);
            } finally {
                pool.con.close();
            }
        } else {
            mns = 2;
        }
        return mns;
    }

    public static long create2(Object x) throws SQLException, ParseException {
        String tbl = "tablas";
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd H:m:s");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        boolean validacion = false;
        TblEmpleados empleado = null;
        String preparet = "";
        long mns = 0;
        if (x instanceof TblEmpleados) {
            empleado = (TblEmpleados) x;
            if (!Utils.CiudadesUtils.existEmpresaEmpleado(empleado.getEmpresaEmpleado().getDocumento().trim(), empleado.getEmpresaEmpleado().getIdEmpresa())) {
                preparet = "insert into empresa_empleado values(\"" + empleado.getEmpresaEmpleado().getDocumento().trim() + "\",\""
                        + empleado.getEmpresaEmpleado().getIdEmpresa() + "\",\"" + format.format(empleado.getEmpresaEmpleado().getFechaCreacion()) + "\"," + empleado.getEmpresaEmpleado().getCodEstado() + ")";
                validacion = true;
            }//si ya existe verificar estado y cambiar por 38
        }
        if (validacion) {
            try {
                System.out.println("preparet " + preparet);
                pool.con = pool.dataSource.getConnection();
                cstmt = pool.con.prepareCall("{call CrudObject (?,?,?)}");
                cstmt.setString(1, preparet);
                cstmt.setInt(2, 0);
                cstmt.setString(3, tbl);
                ResultSet rss = cstmt.executeQuery();
                long result = 0;
                while (rss.next()) {
                    result = rss.getInt(1);
                }
                mns = result;
                System.out.println("result " + mns);
            } catch (SQLException ex) {
                System.out.println("error " + ex);
            } finally {
                pool.con.close();
            }
        } else {
            mns = 2;
        }
        return mns;
    }

    public static long create2(List<TblEmpleados> l) throws SQLException, ParseException {
        String tbl = "tablas";
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd H:m:s");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String preparet = "";
        long mns = 0;
        for (TblEmpleados empleado : l) {
            if (!Utils.CiudadesUtils.existEmpresaEmpleado(empleado.getEmpresaEmpleado().getDocumento().trim(), empleado.getEmpresaEmpleado().getIdEmpresa())) {
                preparet = "insert into empresa_empleado values(\"" + empleado.getEmpresaEmpleado().getDocumento().trim() + "\",\""
                        + empleado.getEmpresaEmpleado().getIdEmpresa() + "\",\"" + format.format(empleado.getEmpresaEmpleado().getFechaCreacion()) + "\"," + empleado.getEmpresaEmpleado().getCodEstado() + ")";
                try {
                    System.out.println("preparet " + preparet);
                    pool.con = pool.dataSource.getConnection();
                    cstmt = pool.con.prepareCall("{call CrudObject (?,?,?)}");
                    cstmt.setString(1, preparet);
                    cstmt.setInt(2, 0);
                    cstmt.setString(3, tbl);
                    ResultSet rss = cstmt.executeQuery();
                    long result = 0;
                    while (rss.next()) {
                        result = rss.getInt(1);
                    }
                    mns = result;
                    System.out.println("result " + mns);
                } catch (SQLException ex) {
                    System.out.println("error " + ex);
                } finally {
                    pool.con.close();
                }
            }
        }
        return mns;
    }

    public static long create3(List<Estudiantes> l) throws SQLException, ParseException {
        String tbl = "tablas";

        String preparet = "";
        long mns = 0;
        for (Estudiantes estudiante : l) {

            preparet = "insert into estudiante_convenios values(\"" + estudiante.getDocumento_estudiante().replace(",", "")
                    + "\",\"" + estudiante.getCodigo_estudiante()
                    + "\",\"" + estudiante.getNombre_estudiante()
                    + "\",\"" + estudiante.getUniversidad() + "\"," + estudiante.getUsuario_mod() + ",\"" + estudiante.getEstado() + "\")";

            try {
                System.out.println("preparet " + preparet);
                pool.con = pool.dataSource.getConnection();
                cstmt = pool.con.prepareCall("{call CrudObject (?,?,?)}");
                cstmt.setString(1, preparet);
                cstmt.setInt(2, 0);
                cstmt.setString(3, tbl);
                ResultSet rss = cstmt.executeQuery();
                long result = 0;
                while (rss.next()) {
                    result = rss.getInt(1);
                }
                mns = result;
                System.out.println("result " + mns);
            } catch (SQLException ex) {
                System.out.println("error " + ex);
            } finally {
                pool.con.close();
            }
        }

        return mns;
    }

    /**
     * Método estatico que permite realizar inserciones en la base de datos
     * apartir del listado de empleados, solo para cargas masivas desde archivo
     * .xls compatibilidad office 97-2003
     *
     * @param l listado de elmpeados
     * @return 0 : Error al crear SQL 1 : Exito al crear Query
     * @throws java.sql.SQLException
     * @throws java.text.ParseException
     */
    public static long create(List<TblEmpleados> l) throws SQLException, ParseException {
        String tbl = "tablas";
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String preparet = "";
        long mns = 0;
        for (TblEmpleados empleado : l) {
            preparet = "insert into tbl_empleados values(\"" + empleado.getDocumento() + "\",\"" + empleado.getNombre() + "\",\"" + empleado.getTelefono()
                    + "\",\"" + empleado.getCorreo() + "\",\"" + empleado.getOt() + "\",\"" + empleado.getTiquetesPendientes() + "\",\"" + empleado.getUserMod()
                    + "\",\"" + format.format(empleado.getFechaMod()) + "\",\"" + format.format(empleado.getFechaCreacion())
                    + "\");insert into empresa_empleado values(\"" + empleado.getEmpresaEmpleado().getDocumento() + "\",\""
                    + empleado.getEmpresaEmpleado().getIdEmpresa() + "\",\"" + format.format(empleado.getEmpresaEmpleado().getFechaCreacion()) + "\"," + empleado.getEmpresaEmpleado().getCodEstado() + ")";
            try {
//                System.out.println("preparet " + preparet);
                pool.con = pool.dataSource.getConnection();
                cstmt = pool.con.prepareCall("{call CrudObject (?,?,?)}");
                cstmt.setString(1, preparet);
                cstmt.setInt(2, 0);
                cstmt.setString(3, tbl);
                ResultSet rs = cstmt.executeQuery();
                long result = 0;
                while (rs.next()) {
                    result = rs.getInt(1);
                }
                mns = result;
            } catch (SQLException ex) {
                System.out.println("errorr transaccion " + ex);
            } finally {
                pool.con.close();
            }
        }
        return mns;
    }

    /**
     * Método estatico que permite realizar inserciones en la base de datos para
     * la entrega de viajes a empleados
     *
     * @param x es ol objeto transaccion que viene como parametro
     * @param l listado de elmpeados que viene como parametro
     * @return 0 : Error al crear SQL 1 : Exito al crear Query
     * @throws java.sql.SQLException
     * @throws java.text.ParseException
     */
    public static long create(Object x, List<TblViajesTiquetes> l) throws SQLException, ParseException {
        String tbl = "tablas";
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        boolean validacion = false;
        Transaccion trans = null;
        String preparet = "";
        long mns = 0;
        if (x instanceof Transaccion) {
            tbl = "transaccion";
            trans = (Transaccion) x;
            preparet = "insert into transaccion values(\"" + trans.getCantTiquetes() + "\",\"" + trans.getDescripcionTiquetes() + "\",\"" + trans.getTotal()
                    + "\",\"" + trans.getIdUsuario().getIdUsuario() + "\",\"" + format2.format(trans.getFechaMod()) + "\",\"" + trans.getUsuarioNodum() + "\",\"" + trans.getClaveNodum()
                    + "\",\"" + trans.getUsuarioTaquilla() + "\",\"" + trans.getTaquilla() + "\",\"" + trans.getTiqtes_entregados() + "\")";
            String doc = l.get(0).getEmpleado().getDocumento();
            for (TblViajesTiquetes next : l) {
                if (next.getEmpleado().getDocumento().equals(doc)) {
                    preparet += ";insert into transaccion_viajero values(" + next.getId_Viaje_tiquete() + ",'"
                            + next.getEmpleado().getDocumento() + "'," + next.getEmpresa().getId_empresa()
                            + "," + next.getDetalleConvPk() + "," + next.getDetalleConvenio().getId_convenio()
                            + "," + next.getDetalleConvenio().getCodCiudadDestino().getCodCiudad()
                            + "," + next.getDetalleConvenio().getCodCiudadOrigen().getCodCiudad()
                            + "," + next.getDetalleConvenio().getValorCliente()
                            + "," + next.getDetalleConvenio().getValorExpal() + ",\"#1"
                            + "\"," + next.getCantidadAEntregar() + "," + 0 + ")";
//                trans.getTempTipoContrato()
                    preparet += ";UPDATE tbl_viajes_tiquetes SET tiquetes_entregados = tiquetes_entregados +\"" + next.getCantidadAEntregar() + "\", fecha_mod = \"" + format2.format(trans.getFechaMod()) + "\" WHERE id_viaje_tiquete = " + next.getId_Viaje_tiquete();
                    validacion = true;
                } else {
                    validacion = false;
                    break;
                }
            }

        }
        if (validacion) {
            try {
                System.out.println("preparet " + preparet);
                pool.con = pool.dataSource.getConnection();
                cstmt = pool.con.prepareCall("{call CrudObject (?,?,?)}");
                cstmt.setString(1, preparet);
                cstmt.setInt(2, 0);
                cstmt.setString(3, tbl);
                ResultSet rst = cstmt.executeQuery();
                int result = 0;
                while (rst.next()) {
                    result = rst.getInt(1);
                }
                if (result > 0) {
                    if (CiudadesUtils.updateTotalTransbyId(result)) {
                        CiudadesUtils.updateTotalTransTiq(result);
                    }
                }
                mns = result;
                System.out.println(" mns " + mns);
            } catch (SQLException ex) {
                System.out.println("transaccion " + ex);
            } finally {
                pool.con.close();
            }
        } else {
            mns = -1;
        }
        return mns;
    }

    public static long createAutorizacion(String x, List<TiquetesAutorizados> l) throws SQLException, ParseException {
        String tbl = "tablas";
        boolean validacion = false;
        String[] trans = x.split(",");
        String preparet = "";
        long mns = 0;
        preparet = "";
        for (TiquetesAutorizados next : l) {

            preparet += "UPDATE tiquetes_autorizados SET fecha_entrega = \"" + trans[0]
                    + "\", taquilla_entrega = \"" + trans[1]
                    + "\", usuario_taquilla = \"" + trans[2]
                    + "\", estado = 'Entregado' WHERE id_carga = " + next.getId_carga();
            validacion = true;

        }

        if (validacion) {
            try {
                System.out.println("preparet " + preparet);
                pool.con = pool.dataSource.getConnection();
                cstmt = pool.con.prepareCall("{call CrudObject (?,?,?)}");
                cstmt.setString(1, preparet);
                cstmt.setInt(2, 0);
                cstmt.setString(3, tbl);
                ResultSet rst = cstmt.executeQuery();
                int result = 0;
                while (rst.next()) {
                    result = rst.getInt(1);
                }
                mns = result;
                System.out.println(" mns " + mns);
            } catch (SQLException ex) {
                System.out.println("transaccion " + ex);
            } finally {
                pool.con.close();
            }
        } else {
            mns = -1;
        }
        return mns;
    }

    /**
     * Método estatico que permite editar en la base de datos apartir del objeto
     * pasado como parametro, valida el tipo de instancia a la que se refiere el
     * objeto y posteriormente procede a enviar a la base de datos la cadena de
     * insercion
     *
     * @param x es ol objeto que viene como parametro
     * @return 0 : Error al crear SQL 1 : Exito al crear Query
     * @throws java.sql.SQLException
     */
    public static int edit(TblViajesTiquetes x) throws SQLException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String tbl = "tablas";
        String preparet = "";
        int mns = 0;
        preparet = "UPDATE tbl_viajes_tiquetes SET documento  = \"" + x.getDocumento() + "\" , id_empresa = \"" + x.getIdEmpresa()
                + "\", detalle_conv_pk = \"" + x.getDetalleConvPk() + "\", id_convenio = \"" + x.getIdConvenio()
                + "\", fecha_inicial = \"" + format.format(x.getFechaInicial())
                + "\", fecha_final = \"" + format.format(x.getFechaFinal())
                + "\", ida_regreso = \"" + x.getIdaRegreso()
                + "\", tiquetes_asignados = \"" + Integer.parseInt(x.getStrtTquetesAsignados())
                + "\", user_mod = \"" + x.getUserMod()
                + "\", fecha_mod = \"" + format.format(new Date())
                + "\", orden_servicio = \"" + x.getOs()
                + "\" WHERE id_viaje_tiquete = " + x.getId_Viaje_tiquete();
        System.out.println("preparet " + preparet);
        try {
            pool.con = pool.dataSource.getConnection();
            cstmt = pool.con.prepareCall("{call CrudObject (?,?,?)}");
            cstmt.setString(1, preparet);
            cstmt.setInt(2, 0);
            cstmt.setString(3, tbl);
            ResultSet rs = cstmt.executeQuery();
            int result = 0;
            while (rs.next()) {
                result = rs.getInt(1);
            }
            mns = result;
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        } finally {
            pool.con.close();
        }
        System.out.println("msn = " + mns);
        return mns;
    }

    public static int editPass(Usuarios x) throws SQLException {
        String tbl = "tablas";
        String preparet = "";
        int mns = 0;
        preparet = "UPDATE usuarios SET clave  = \"" + x.getClave() + "\" WHERE documento = '" + x.getDocumento() + "'";
        System.out.println("preparet " + preparet);
        try {
            pool.con = pool.dataSource.getConnection();
            cstmt = pool.con.prepareCall("{call CrudObject (?,?,?)}");
            cstmt.setString(1, preparet);
            cstmt.setInt(2, 0);
            cstmt.setString(3, tbl);
            ResultSet rs = cstmt.executeQuery();
            int result = 0;
            while (rs.next()) {
                result = rs.getInt(1);
            }
            mns = result;
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        } finally {
            pool.con.close();
        }
        System.out.println("msn = " + mns);
        return mns;
    }

    public static int edit(Object x) throws SQLException {
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd H:m:s");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String tbl = "tablas";
        boolean validacion = false;
        Estados estado = null;
        Empresas empresa = null;
        Estudiantes estudiantes = null;
        Ciudad ciudad = null;
        Roles rol = null;
        Usuarios usuarios = null;
        TblServicios servicios = null;
        DetalleConvenio detalleconvenio = null;
        TblEmpleados empleado = null;
        TblViajesTiquetes viajesTiquetes = null;
        TblRegistroContravias contravias = null;
        Alertas alerta = null;
        String preparet = "";
        TiquetesAutorizados tiqueteAutorizado = null;
        int mns = 0;
        if (x instanceof Estados) {
            estado = (Estados) x;
            preparet = "UPDATE Estados  set descripcion= \"" + estado.getDescripcion() + "\" WHERE cod_estado = " + estado.getCodEstado();
            validacion = true;
        } else if (x instanceof Empresas) {
            empresa = (Empresas) x;
            preparet = "UPDATE tbl_empresas SET nit = \"" + empresa.getNit() + "\", nombre = \"" + empresa.getNombre() + "\", "
                    + "direccion = \"" + empresa.getDireccion() + "\", telefono = \"" + empresa.getTelefono() + "\", "
                    + "correo = \"" + empresa.getCorreo() + "\", user_mod = \"" + empresa.getUser_mod() + "\", fecha_mod = \"" + empresa.getFecha_mod() + "\", "
                    + "cod_ciudad = " + empresa.getCod_ciudad() + ", cod_estado = " + empresa.getCod_estado() + " WHERE id_empresa = " + empresa.getId_empresa();
            validacion = true;
        } else if (x instanceof Ciudad) {
            ciudad = (Ciudad) x;
            preparet = "UPDATE tbl_ciudades SET ciudad = \"" + ciudad.getCiudad() + "\", user_mod = \"" + ciudad.getUser_mod() + "\", "
                    + "fecha_mod = \"" + ciudad.getFecha_mod() + "\" WHERE cod_ciudad = " + ciudad.getCod_ciudad();
            validacion = true;
        } else if (x instanceof Roles) {
            rol = (Roles) x;
            preparet = "UPDATE roles SET rol = \"" + rol.getRol() + "\", user_mod = \"" + rol.getUserMod() + "\", "
                    + "fecha_mod = \"" + rol.getFechaMod() + "\" WHERE id_rol = " + rol.getIdRol();
            validacion = true;
        } else if (x instanceof Usuarios) {
            tbl = "usuarios";
            usuarios = (Usuarios) x;
            preparet = "UPDATE usuarios SET  documento = \"" + usuarios.getDocumento() + "\", nombres = \"" + usuarios.getNombres() + "\", apellidos =\"" + usuarios.getApellidos()
                    + "\", clave = \"" + usuarios.getClave() + "\", cod_ciudad = \"" + usuarios.getCodCiudad() + "\", id_empresa = \"" + usuarios.getIdEmpresa() + "\", user_mod = \"" + usuarios.getUserMod() + "\", fecha_mod = \"" + usuarios.getFechaMod()
                    + "\" WHERE id_usuario = \"" + usuarios.getIdUsuario() + "\" ;UPDATE usuarios_roles SET id_rol = \"" + usuarios.getUsuRol().getRol() + "\", fecha_mod = \"" + usuarios.getUsuRol().getFechaMod() + "\" WHERE id_usuario = \"" + usuarios.getIdUsuario() + "\"";
            validacion = true;
        } else if (x instanceof TblServicios) {
            servicios = (TblServicios) x;
            preparet = "UPDATE tbl_servicios SET servicio = \"" + servicios.getServicio() + "\", user_mod = \"" + servicios.getUserMod() + "\", "
                    + "fecha_mod = \"" + servicios.getFechaMod() + "\" WHERE id_servicio = " + servicios.getIdServicio();
            validacion = true;
        } else if (x instanceof DetalleConvenio) {
            detalleconvenio = (DetalleConvenio) x;
            preparet = "UPDATE detalle_convenio SET valor_cliente = " + detalleconvenio.getValorCliente() + ","
                    + " valor_expal = " + detalleconvenio.getValorExpal() + ", id_servicio = " + detalleconvenio.getTblservicio().getIdServicio() + ""
                    + ", cod_ciudad_origen = " + detalleconvenio.getCodCiudadOrigen().getCodCiudad() + ""
                    + ", cod_ciudad_destino = " + detalleconvenio.getCodCiudadDestino().getCodCiudad() + ""
                    + ", fecha_inicial = \"" + format.format(detalleconvenio.getFechaInicial()) + "\""
                    + ", fecha_final = \"" + format.format(detalleconvenio.getFechaFinal()) + "\""
                    + ", empalme = \"" + detalleconvenio.getEmpalme() + "\""
                    + " WHERE detalle_conv_pk = " + detalleconvenio.getId_detalle_fk();

//            "insert into detalle_convenio values(\"" + detalleconvenio.getId_convenio()
//                    + "\",\"" + detalleconvenio.getId_empresa()
//                    + "\",\"" + detalleconvenio.getValorCliente()
//                    + "\",\"" + detalleconvenio.getValorExpal()
//                    + "\",\"" + detalleconvenio.getTblservicio().getIdServicio()
//                    + "\",\"" + detalleconvenio.getCodCiudadOrigen().getCodCiudad()
//                    + "\",\"" + detalleconvenio.getCodCiudadDestino().getCodCiudad()
//                    + "\",\"" + format.format(detalleconvenio.getFechaInicial())
//                    + "\",\"" + format.format(detalleconvenio.getFechaFinal())
//                    + "\",\"" + detalleconvenio.getCod_estado()
//                    + "\",\"" + detalleconvenio.getIdUsuario()
//                    + "\",\"" + detalleconvenio.getFechaCreacion()
//                    + "\",\"" + detalleconvenio.getEmpalme() + "\");"
            validacion = true;
        } else if (x instanceof TblEmpleados) {
            empleado = (TblEmpleados) x;
            preparet = "Update tbl_empleados SET nombre = \"" + empleado.getNombre() + "\", telefono = \"" + empleado.getTelefono()
                    + "\", correo = \"" + empleado.getCorreo() + "\", ot = \"" + empleado.getOt() + "\", user_mod = \"" + empleado.getUserMod()
                    + "\", fecha_mod = \"" + format2.format(empleado.getFechaMod()) + "\" WHERE documento = \"" + empleado.getDocumento() + "\";UPDATE  empresa_empleado set cod_estado = " + 38 + " WHERE documento = \"" + empleado.getDocumento() + "\"";
            validacion = true;
        } else if (x instanceof TblViajesTiquetes) {
            viajesTiquetes = (TblViajesTiquetes) x;
            preparet = "UPDATE tbl_viajes_tiquetes SET estado = \"" + 0 + "\" WHERE documento = \"" + viajesTiquetes.getDocumento()
                    + "\" AND fecha_final < \"" + format.format(new Date())
                    + "\" AND detalle_convenio_pk = " + viajesTiquetes.getDetalleConvPk();
            validacion = true;
        } else if (x instanceof TblRegistroContravias) {
            contravias = (TblRegistroContravias) x;
            preparet = "UPDATE tbl_registroContravias SET dinero_en_casa = \"" + (contravias.getDineroEnCasa() == 1 ? 0 : 1)
                    + "\" WHERE transaccion = \"" + contravias.getTransaccion() + "\"";
            validacion = true;
        } else if (x instanceof Alertas) {
            alerta = (Alertas) x;
            preparet = "UPDATE alertas set descripcion = \"" + alerta.getDescripcion() + "\", relevancia = \""
                    + alerta.getRelevancia() + "\", titulo = \""
                    + alerta.getTitulo() + "\", ver_desde = \""
                    + format.format(alerta.getFecha_desde()) + " 00:00:00\", ver_hasta = \"" + format.format(alerta.getFecha_hasta())
                    + " 23:59:59\" WHERE id = " + alerta.getId();
            validacion = true;
        } else if (x instanceof Estudiantes) {

            estudiantes = (Estudiantes) x;
            preparet = "UPDATE estudiante_convenios  set documento_estudiante = \"" + estudiantes.getDocumento_estudiante() + "\","
                    + "codigo_estudiante = \"" + estudiantes.getCodigo_estudiante() + "\","
                    + "nombre_estudiante = \"" + estudiantes.getNombre_estudiante() + "\","
                    + "universidad = \"" + estudiantes.getUniversidad() + "\","
                    + "usuario_mod = " + estudiantes.getUsuario_mod() + " WHERE id_estudiante = " + estudiantes.getId_estudiante();
            validacion = true;
        } else if (x instanceof TiquetesAutorizados) {
            tiqueteAutorizado = (TiquetesAutorizados) x;
            preparet = "UPDATE tiquetes_autorizados  set documento = \"" + tiqueteAutorizado.getDocumento().replace(".", "") + "\","
                    + "nombre_completo = \"" + tiqueteAutorizado.getNombre_completo() + "\","
                    + "telefono = \"" + tiqueteAutorizado.getTelefono() + "\","
                    + "origen = \"" + tiqueteAutorizado.getOrigen() + "\","
                    + "destino = \"" + tiqueteAutorizado.getDestino() + "\","
                    + "motivo_solicitud = \"" + tiqueteAutorizado.getMotivo_solicitud() + "\","
                    + "idaRegreso = \"" + tiqueteAutorizado.getIdaRegreso() + "\""
                    + " WHERE id_carga = " + tiqueteAutorizado.getId_carga();
            validacion = true;
        }
        System.out.println("preparet " + preparet);
        if (validacion) {
            try {
                pool.con = pool.dataSource.getConnection();
                cstmt = pool.con.prepareCall("{call CrudObject (?,?,?)}");
                cstmt.setString(1, preparet);
                cstmt.setInt(2, 0);
                cstmt.setString(3, tbl);
                ResultSet rs = cstmt.executeQuery();
                int result = 0;
                while (rs.next()) {
                    result = rs.getInt(1);
                }
                mns = result;
            } catch (Exception ex) {
                System.out.println("error " + ex);
            } finally {
                pool.con.close();
            }
        } else {
            mns = 2;
        }
        return mns;
    }

    /**
     * Método estatico que permite editar un convenio en la base de datos
     * apartir del objeto pasado como parametro, posteriormente procede a enviar
     * a la base de datos la cadena de edicion
     *
     * @param e objeto de tipo empersas que contiene el convenio.
     * @return 0 : Error al crear SQL 1 : Exito al crear Query
     * @throws java.sql.SQLException
     */
    public static int editConvenio(Empresas e) throws SQLException {
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd H:m:s");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String tbl = "tablas";
        boolean validacion = true;
        String preparet = "";
        int mns = 0;
        preparet = "UPDATE tbl_convenio SET user_mod = \"" + e.getUser_mod()
                + "\", fecha_mod = \"" + format2.format(e.getFechaMod())
                + "\", fecha_inicial = \"" + format.format(e.getFecha_inicial())
                + "\", fecha_final = \"" + format.format(e.getFecha_final())
                + "\", descripcion = \"" + e.getConvenio().getDescripcion()
                + "\", valor_global = " + e.getValor_global()
                + ", estado = \"" + e.getEstado()
                + "\", tipo_contrato = \"" + e.getTipo_contrato() + "\" WHERE id_convenio = " + e.getConvenio().getIdconvenio();

//        System.out.println("preparet " + preparet);
        if (validacion) {
            try {
                pool.con = pool.dataSource.getConnection();
                cstmt = pool.con.prepareCall("{call CrudObject (?,?,?)}");
                cstmt.setString(1, preparet);
                cstmt.setInt(2, 0);
                cstmt.setString(3, tbl);
                ResultSet rs = cstmt.executeQuery();
                int result = 0;
                while (rs.next()) {
                    result = rs.getInt(1);
                }
                mns = result;
//                System.out.println("r " + mns);
            } catch (Exception ex) {
                System.out.println("error " + ex);

            } finally {
                pool.con.close();
            }
        } else {
            mns = 2;
        }
        return mns;
    }

    public static int editClaveUser(Usuarios u) throws SQLException {
        String tbl = "tablas";
        boolean validacion = true;
        String preparet = "";
        int mns = 0;
        //update  usuarios set clave = '123456' where id_usuario = 30
        preparet = "UPDATE usuarios SET clave = \"" + u.getClave().trim()
                + "\" WHERE id_usuario = " + u.getIdUsuario();
        System.out.println("preparet " + preparet);
        if (validacion) {
            try {
                pool.con = pool.dataSource.getConnection();
                cstmt = pool.con.prepareCall("{call CrudObject (?,?,?)}");
                cstmt.setString(1, preparet);
                cstmt.setInt(2, 0);
                cstmt.setString(3, tbl);
                ResultSet rs = cstmt.executeQuery();
                int result = 0;
                while (rs.next()) {
                    result = rs.getInt(1);
                }
                mns = result;
//                System.out.println("r " + mns);
            } catch (Exception ex) {
                System.out.println("error " + ex);

            } finally {
                pool.con.close();
            }
        } else {
            mns = 2;
        }
        return mns;
    }

    /**
     * Método estatico que permite cambiar el estado de las contravias
     * entregadas apartir del listado pasado como parametro, posteriormente
     * procede a enviar a la base de datos la cadena de edicion
     *
     * @param x listado de contravias
     * @return 0 : Error al crear SQL 1 : Exito al crear Query
     * @throws java.sql.SQLException
     */
    public static int editEntregaContravia(List<TblRegistroContravias> x) throws SQLException {
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd H:m:s");
        String tbl = "tablas";
        boolean validacion = false;
        String preparet = "";
        int mns = 0;
        for (TblRegistroContravias contravias : x) {
            preparet += "UPDATE tbl_registroContravias SET estado = \"" + "Entregado" + "\", taquilla_entrega  = \"" + contravias.getTaquilla_entrega()
                    + "\", usuarioTaquilla_entrega  = \"" + contravias.getUsuarioTaquillaEntrega()
                    + "\", userNodum_entrega = \"" + contravias.getUserNodumEntrega()
                    + "\", fecha_entrega = \"" + format2.format(contravias.getFecha_entrega())
                    + "\", no_tiquete = \"" + contravias.getNo_tiquete()
                    + "\" WHERE transaccion = \"" + contravias.getTransaccion() + "\"";
            if (x.size() > 1) {
                preparet += ";";
            }
            validacion = true;
        }
//        System.out.println("preparet " + preparet);
        if (validacion) {
            try {
                pool.con = pool.dataSource.getConnection();
                cstmt = pool.con.prepareCall("{call CrudObject (?,?,?)}");
                cstmt.setString(1, preparet);
                cstmt.setInt(2, 0);
                cstmt.setString(3, tbl);
                ResultSet rst = cstmt.executeQuery();
                int result = 0;
                while (rst.next()) {
                    result = rst.getInt(1);
                }
                mns = result;
            } catch (Exception ex) {
                System.out.println("preparet " + ex);
            } finally {
                pool.con.close();
            }
        } else {
            mns = 2;
        }
        return mns;
    }

    /**
     * Método estatico que permite cambiar el estado del objeto pasado como
     * parametro y tambien permite eliminar en otras tablas, se valida el objeto
     * que viene como parametro para conocer su instancia y proceder a enviarl
     * as querys a la bd
     *
     * @param x
     * @return 0 : Error al crear SQL 1 : Exito al crear Query
     * @throws java.sql.SQLException
     */
    public static int delete(Object x) throws SQLException {
        String tbl = "tablas";
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd H:m:s");
        boolean validacion = false;
        Estados estado = null;
        Empresas empresa = null;
        Estudiantes estudiantes = null;
        Ciudad ciudad = null;
        Roles rol = null;
        Usuarios usuarios = null;
        TblServicios servicios = null;
        TblEmpleados empleado = null;
        TblConvenio convenio = null;
        DetalleConvenio detalleconvenio = null;
        TblViajesTiquetes viajesTiquetes = null;
        Docs documento = null;
        Alertas alertas = null;
        TiquetesAutorizados tiquetes = null;
        String preparet = "";
        int mns = 0;
        if (x instanceof Estados) {//pendiente por crud estados
            estado = (Estados) x;
            preparet = "insert into Estados values(" + estado.getDescripcion() + ")";
            validacion = true;
        } else if (x instanceof Empresas) {
            empresa = (Empresas) x;
            preparet = "DELETE FROM tbl_empresas WHERE id_empresa = " + empresa.getId_empresa();
            validacion = true;
        } else if (x instanceof Docs) {
            documento = (Docs) x;
            preparet = "DELETE FROM documentos WHERE id = " + documento.getId();
            validacion = true;
        } else if (x instanceof Ciudad) {
            ciudad = (Ciudad) x;
            preparet = "DELETE FROM tbl_ciudades WHERE cod_ciudad = " + ciudad.getCod_ciudad();
            validacion = true;
        } else if (x instanceof Roles) {
            rol = (Roles) x;
            preparet = "DELETE FROM roles WHERE id_rol = " + rol.getIdRol();
            validacion = true;
        } else if (x instanceof Usuarios) {
            usuarios = (Usuarios) x;
            preparet = "UPDATE usuarios SET estado = \"" + usuarios.getEstado() + "\", fecha_mod = \"" + usuarios.getFechaMod() + "\" WHERE id_usuario = \"" + usuarios.getIdUsuario() + "\"";
            validacion = true;
        } else if (x instanceof TblServicios) {
            servicios = (TblServicios) x;
            preparet = "DELETE FROM tbl_servicios WHERE id_servicio = " + servicios.getIdServicio();
            validacion = true;
        } else if (x instanceof TblConvenio) {
            convenio = (TblConvenio) x;
            preparet = "UPDATE tbl_convenio SET estado = 'I', fecha_mod = \"" + convenio.getFechaMod() + "\" WHERE id_convenio = \"" + convenio.getIdconvenio() + "\" AND id_empresa = \"" + convenio.getIdempresa() + "\"";
            validacion = true;
        } else if (x instanceof TblEmpleados) {
            empleado = (TblEmpleados) x;
            preparet = "Update tbl_empleados SET user_mod = \"" + empleado.getUserMod()
                    + "\", fecha_mod = \"" + format2.format(empleado.getFechaMod()) + "\", tiquetes_pendientes = " + 0 + " WHERE documento = \"" + empleado.getDocumento()
                    + "\";UPDATE  empresa_empleado set cod_estado = " + 39 + " WHERE documento = \"" + empleado.getDocumento() + "\"";
            validacion = true;
        } else if (x instanceof TblViajesTiquetes) {
            viajesTiquetes = (TblViajesTiquetes) x;
            preparet = "UPDATE tbl_viajes_tiquetes SET estado = \"" + 0 + "\", fecha_mod = \"" + format2.format(viajesTiquetes.getFechaMod()) + "\", user_mod = \"" + viajesTiquetes.getUserMod() + "\" WHERE id_viaje_tiquete = \"" + viajesTiquetes.getId_Viaje_tiquete() + "\"";
            validacion = true;
        } else if (x instanceof DetalleConvenio) {
            detalleconvenio = (DetalleConvenio) x;
            preparet = "UPDATE detalle_convenio SET cod_estado = " + 39 + " WHERE detalle_conv_pk = " + detalleconvenio.getId_detalle_fk() + "";
            validacion = true;
        } else if (x instanceof Alertas) {
            alertas = (Alertas) x;
            preparet = "DELETE FROM alertas  WHERE id = " + alertas.getId() + "";
            validacion = true;
        } else if (x instanceof Estudiantes) {//delete estudiantes
            estudiantes = (Estudiantes) x;
            preparet = "UPDATE estudiante_convenios set estado = 'I'  WHERE id_estudiante = " + estudiantes.getId_estudiante() + "";
            validacion = true;
        } else if (x instanceof TiquetesAutorizados) {//delete estudiantes
            tiquetes = (TiquetesAutorizados) x;
            preparet = "delete from tiquetes_autorizados  WHERE id_carga = " + tiquetes.getId_carga() + "";
            validacion = true;
        }//delete TiquetesAutorizados

//        System.out.println("preparet " + preparet);
        if (validacion) {
            try {
                System.out.println("preparet " + preparet);
                pool.con = pool.dataSource.getConnection();
                cstmt = pool.con.prepareCall("{call CrudObject (?,?,?)}");
                cstmt.setString(1, preparet);
                cstmt.setInt(2, 0);
                cstmt.setString(3, tbl);
                ResultSet rst = cstmt.executeQuery();
                int result = 0;
                while (rst.next()) {
                    result = rst.getInt(1);
                }
                mns = result;
            } catch (Exception ex) {
                System.out.println("preparet " + ex);
            } finally {
                pool.con.close();
            }
        } else {
            mns = 2;
        }
        return mns;
    }

    /**
     * Método estatico que permite listar en general de todas las tablas de la
     * base de datos a partir de los parametros recibidos, este hace uso de la
     * clase ConsultaGeneral que contiene los constructores establecidos para
     * cada tipo de consulta.
     *
     * @param vista
     * @param opc
     * @param params
     * @return 0 : Error al crear SQL 1 : Exito al crear Query
     * @throws java.sql.SQLException
     */
    public static List<ConsultaGeneral> getSelectSql(String vista, int opc, String params) throws SQLException {
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        System.out.println("parametros : " + params + " vista " + vista);
        try {
            pool.con = pool.dataSource.getConnection();
            cstmt = pool.con.prepareCall("{call ConsultaGeneral (?,?,?)}");
            cstmt.setString(1, vista);
            cstmt.setInt(2, opc);
            cstmt.setString(3, params);
            ResultSet rs = cstmt.executeQuery();
            int result = 0;
            while (rs.next()) {
                if (vista.equalsIgnoreCase("login")) {
                    if (opc == 1) {               // id_usuario,  doeumento,    nombres,       apellidos,        id_rol,     rol     
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7)));
                    }
                } else if (vista.equalsIgnoreCase("rol")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    }
                } else if (vista.equalsIgnoreCase("docs")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getInt(6)));
                    }
                } else if (vista.equalsIgnoreCase("docsAgency") || vista.equalsIgnoreCase("docsHelp")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getInt(6)));
                    }
                } else if (vista.equalsIgnoreCase("usuario") || vista.equalsIgnoreCase("usuarioParam")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), //id_user
                                rs.getString(2), //doc
                                rs.getString(3), //nombre
                                rs.getString(4), //apellid                                   
                                rs.getString(5), //clave
                                rs.getString(6), //cambiocla
                                rs.getString(7),//cambiocla_auto
                                rs.getString(8), //ciudad
                                rs.getString(9), //empresa
                                rs.getString(10), //usermod
                                rs.getString(11),//usermod
                                rs.getString(12),
                                rs.getString(13),
                                rs.getInt(14),
                                rs.getInt(15),
                                rs.getInt(16)));
                    }
                } else if (vista.equalsIgnoreCase("servicio")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                    }
                } else if (vista.equalsIgnoreCase("alertList") || vista.equalsIgnoreCase("alertListAdm")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getDate(7)));
                    }
                } else if (vista.equalsIgnoreCase("empresaConvenio")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
                                rs.getDate(5), rs.getDate(6), rs.getInt(7), rs.getString(8)));
                    }
                } else if (vista.equalsIgnoreCase("detalleConvenio")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1),
                                rs.getInt(2),
                                rs.getInt(3),
                                rs.getInt(4),
                                rs.getInt(5),
                                rs.getInt(6),
                                rs.getInt(7),
                                rs.getInt(8),
                                rs.getInt(16),
                                rs.getDate(9), rs.getDate(10), rs.getString(11), rs.getString(12),
                                rs.getString(13), rs.getString(14), rs.getString(15)));
                    }
                } else if (vista.equalsIgnoreCase("detalleConvenioP")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1),
                                rs.getInt(2),
                                rs.getInt(3),
                                rs.getInt(4),
                                rs.getInt(5),
                                rs.getInt(6),
                                rs.getInt(7),
                                rs.getInt(8),
                                rs.getInt(16),
                                rs.getDate(9), rs.getDate(10), rs.getString(11), rs.getString(12),
                                rs.getString(13), rs.getString(14), rs.getString(15)));
                    }
                } else if (vista.equalsIgnoreCase("historyConvenio")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
                    }
                } else if (vista.equalsIgnoreCase("empleados") || vista.equalsIgnoreCase("empleadosF")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getInt(6),
                                rs.getString(7), rs.getDate(8), rs.getDate(9), rs.getInt(10)));

                    }
                } else if (vista.equalsIgnoreCase("empleadosHomonimo")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getInt(6),
                                rs.getString(7), rs.getDate(8), rs.getDate(9), rs.getInt(10)));

                    }
                } else if (vista.equalsIgnoreCase("empleadosTiquetes")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getDate(4),
                                rs.getDate(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getInt(8),
                                rs.getString(9), rs.getInt(10), rs.getString(11), rs.getBoolean(12)));

                    }
                } else if (vista.equalsIgnoreCase("saldosPendByDoc")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getDate(4),
                                rs.getDate(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getInt(8),
                                rs.getString(9), rs.getInt(10), rs.getString(11), rs.getInt(12)));

                    }
                } else if (vista.equalsIgnoreCase("saldosPend")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getDate(4),
                                rs.getDate(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getInt(8),
                                rs.getString(9), rs.getInt(10), rs.getString(11), rs.getInt(12)));

                    }
                } else if (vista.equalsIgnoreCase("saldosPendAdm")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getDate(4),
                                rs.getDate(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getInt(8),
                                rs.getString(9), rs.getInt(10), rs.getString(11), rs.getInt(12)));

                    }
                } else if (vista.equalsIgnoreCase("saldosPendByAdm")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getDate(4),
                                rs.getDate(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getInt(8),
                                rs.getString(9), rs.getInt(10), rs.getString(11), rs.getInt(12)));

                    }
                } else if (vista.equalsIgnoreCase("emptiqueteByRuta")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getDate(4),
                                rs.getDate(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getInt(8),
                                rs.getString(9), rs.getInt(10), rs.getString(11), rs.getBoolean(12)));

                    }
                } else if (vista.equalsIgnoreCase("emptiqueteByFecha")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getDate(4),
                                rs.getDate(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getInt(8),
                                rs.getString(9), rs.getInt(10), rs.getString(11), rs.getBoolean(12)));

                    }
                } else if (vista.equalsIgnoreCase("emptiqueteByUser")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getDate(4),
                                rs.getDate(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getInt(8),
                                rs.getString(9), rs.getInt(10), rs.getString(11), rs.getBoolean(12)));

                    }
                } else if (vista.equalsIgnoreCase("empleadoTiqPendiente")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1)));
                    }
                } else if (vista.equalsIgnoreCase("empleadoExistEmpresa")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1)));
                    }
                } else if (vista.equalsIgnoreCase("registroTiquete")) {
                    if (opc == 1 || opc == 2 || opc == 3) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2)));
                    }
                } else if (vista.equalsIgnoreCase("facturaVenta") || vista.equalsIgnoreCase("facturaVentaDoc")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1), rs.getString(2),
                                rs.getString(3),
                                rs.getString(4), rs.getString(5),
                                rs.getInt(6), rs.getInt(7), rs.getDate(8),
                                rs.getString(9), rs.getInt(10), rs.getString(11), rs.getString(12), rs.getString(13)));
                    }
                } else if (vista.equalsIgnoreCase("ciudadById")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1)));
                    }
                } else if (vista.equalsIgnoreCase("entregaConvenio")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getInt(2), rs.getString(3),
                                rs.getString(4), rs.getString(5),
                                rs.getString(6), rs.getString(7),
                                rs.getString(8), rs.getString(9),
                                rs.getInt(10), rs.getFloat(11),
                                rs.getInt(12), rs.getInt(13),
                                rs.getInt(14), rs.getInt(15), rs.getInt(16), rs.getInt(17), rs.getString(18)));
                    }
                } else if (vista.equalsIgnoreCase("contraviaPendiente") || vista.equalsIgnoreCase("contraviaTesoreria") || vista.equalsIgnoreCase("contraviaTesoreriaId")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getString(9),
                                rs.getInt(10),
                                rs.getInt(11),
                                rs.getInt(12),
                                rs.getString(13),
                                rs.getString(14),
                                rs.getString(15),
                                rs.getString(16),
                                rs.getString(17),
                                rs.getDate(18),
                                rs.getDate(19),
                                rs.getString(20), rs.getInt(21)));
                    }
                } else if (vista.equalsIgnoreCase("contraviaPendFics")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getInt(9),
                                rs.getInt(10),
                                rs.getString(11),
                                rs.getString(12),
                                rs.getString(13),
                                rs.getString(14),
                                rs.getString(15)));
                    }
                } else if (vista.equalsIgnoreCase("contraviasImpr") || vista.equalsIgnoreCase("reimprimirContrav")
                        || vista.equalsIgnoreCase("resumenContrav")
                        || vista.equalsIgnoreCase("resumenContravUser") || vista.equalsIgnoreCase("reimprimirContravM")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getString(9),
                                rs.getInt(10),
                                rs.getInt(11),
                                rs.getInt(12),
                                rs.getString(13),
                                rs.getString(14),
                                rs.getString(15),
                                rs.getString(16),
                                rs.getString(17),
                                rs.getDate(18),
                                rs.getDate(19), rs.getString(20), rs.getInt(21)));
                    }
                } else if (vista.equalsIgnoreCase("transEntregadas") || vista.equalsIgnoreCase("transEntregadas2")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1),
                                rs.getInt(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getDate(6),
                                rs.getString(7), rs.getInt(8)));
                    }
                } else if (vista.equalsIgnoreCase("cmgenConvenios") || vista.equalsIgnoreCase("cmgenConvenios2")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1),
                                rs.getInt(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getDate(6),
                                rs.getInt(7), rs.getString(8), rs.getInt(9), rs.getString(10)));
                    }
                } else if (vista.equalsIgnoreCase("cmGen") || vista.equalsIgnoreCase("cmGenAdmin")
                        || vista.equalsIgnoreCase("cmGenAdminF") || vista.equalsIgnoreCase("cmGenAdminU")
                        || vista.equalsIgnoreCase("cmGenAdminByCm")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getBoolean(5), rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getInt(9)));
                    }
                } else if (vista.equalsIgnoreCase("estudiante") || vista.equalsIgnoreCase("estudiantef")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(8)));
                    }
                } else if (vista.equalsIgnoreCase("estudiantefecha") || vista.equalsIgnoreCase("estudiantefecha2")) {
                    if (opc == 1) {
                        // id_entrega,  documento,         nombres,       fecha,       taquilla_entrega, usuario_taquilla, universidad,   nombre_estudiante    

                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
                    }
                } else if (vista.equalsIgnoreCase("tiquAutorizados")
                        || vista.equalsIgnoreCase("tiquAutorizadosId") || vista.equalsIgnoreCase("findtiqAutorizados")
                        || vista.equalsIgnoreCase("tiquAutorizadosMon") || vista.equalsIgnoreCase("tiquAutorizadosView")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getDate(8),
                                rs.getString(9),
                                rs.getDate(10),
                                rs.getString(11),
                                rs.getString(12),
                                rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17)));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("error " + e + " " + vista);
        } finally {
            pool.con.close();
        }
        return l;
    }

}
