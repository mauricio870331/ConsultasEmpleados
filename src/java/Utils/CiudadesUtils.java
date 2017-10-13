package Utils;

import Entities.Estudiantes;
import Entities.FacturaHistorico;
import Entities.ReciboDeCaja;
import Entities.TblRegistroContravias;
import Entities.TiquetesAutorizados;
import Modelo.Conexion;
import Modelo.ConexionPool;
import Modelo.ConsultaGeneral;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
public class CiudadesUtils {

    static ConexionPool pool = new ConexionPool();
    static Connection cn;
    static Connection con3 = null;
    static PreparedStatement pstm;
    static ResultSet rs;
    static CallableStatement cstmt;
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static List<ConsultaGeneral> getSelectSql(String vista, int opc, String params) throws SQLException {
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        System.out.println("parametros : " + params);
        try {
            pool.con = pool.dataSource.getConnection();
            cstmt = pool.con.prepareCall("{call ConsultaGeneral (?,?,?)}");
            cstmt.setString(1, vista);
            cstmt.setInt(2, opc);
            cstmt.setString(3, params);
            ResultSet rs = cstmt.executeQuery();
            int result = 0;
            while (rs.next()) {
                if (vista.equalsIgnoreCase("ciudadUtil")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2)));
                    }
                } else if (vista.equalsIgnoreCase("empresaUtil")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2)));
                    }
                } else if (vista.equalsIgnoreCase("empresaById")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1)));
                    }
                } else if (vista.equalsIgnoreCase("servicioUtil")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2)));
                    }
                } else if (vista.equalsIgnoreCase("estadosUtil")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2)));
                    }
                } else if (vista.equalsIgnoreCase("origenDestinoUtil")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    }
                } else if (vista.equalsIgnoreCase("oDestUtilCodRuta")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    }
                } else if (vista.equalsIgnoreCase("oDestUtilOrigen")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    }
                } else if (vista.equalsIgnoreCase("oDestUtilDestino")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    }
                } else if (vista.equalsIgnoreCase("oDestUtilServicio")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    }
                } else if (vista.equalsIgnoreCase("empleadoAutocomplete")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1), rs.getString(2)));
                    }
                } else if (vista.equalsIgnoreCase("autocompleteEmpl")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1), rs.getString(2)));
                    }
                } else if (vista.equalsIgnoreCase("idConvenioBydetalle")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1)));
                    }
                } else if (vista.equalsIgnoreCase("valueRuta")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getInt(2)));
                    }
                } else if (vista.equalsIgnoreCase("taquillas")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    }
                } else if (vista.equalsIgnoreCase("listCiudades")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                    }
                }
                //result = rs.getInt(1);
                // System.out.println("valor " + result);
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }

        return l;
    }

    public static int getValorRuta(String origen, String destino) throws SQLException {
        int result = 0;
        try {
            cn = Conexion.conectar2();
            pstm = cn.prepareStatement("select case when b.Temporada='Baja' then cast(precio as numeric) "
                    + "when b.Temporada='Alta' then cast(precio2 as numeric) end precio "
                    + "from cpp_rutasweb a ,cpt_rutasweb b  where a.nro_trans=b.nro_trans and "
                    + "a.fecha_mod in (select MAX(fecha_mod) from cpp_rutasweb) "
                    + "AND origen = '" + origen + "' AND destino = '" + destino + "'");
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            cn.close();
            pstm.close();
        }
        return result;
    }

    public static List<String> getRutasWeb(String origen, String destino) throws SQLException {
        System.out.println(origen + "" + destino);
        ArrayList<String> result = new ArrayList();
        try {
            cn = Conexion.conectar2();
            String sql = "select a.fecha as fecha, origen,destino,case when b.Temporada='Baja' then cast(precio as numeric) "
                    + "when b.Temporada='Alta' then cast(precio2 as numeric) end precio,servicio,SUBSTRING(hora,0,3)+':'+ SUBSTRING(hora,3,5) horario, "
                    + "observaciones from cpp_rutasweb a ,cpt_rutasweb b  where a.nro_trans=b.nro_trans and "
                    + "a.fecha_mod in (select MAX(fecha_mod) from cpp_rutasweb) and origen = '" + origen + "' and destino = '" + destino + "'  order by precio desc";
            System.out.println("query = " + sql);
            pstm = cn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                String cadena = rs.getString(1).trim() + "," + rs.getString(2).trim() + "," + rs.getString(3).trim() + "," + rs.getInt(4) + "," + rs.getString(5).trim() + " " + rs.getString(7).trim().replace(",", " -") + "," + rs.getString(5).trim() + " -- " + rs.getString(6);
                if (!result.contains(cadena)) {
                    result.add(cadena);
                }
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            cn.close();
            pstm.close();
        }
        HashSet<String> hs = new HashSet();
        hs.addAll(result);
        result.clear();
        result.addAll(hs);
        return result;
    }

    public static String getValorGlobal(int id_empresa) throws SQLException {
        String result = "";
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("select tipo_contrato from tbl_convenio where id_empresa = " + id_empresa + " AND estado = 'A' ");
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    /*
    * Método que compara el número de documento del estudiante ingresado al sistema
    * con el número de documento que se encuentra registrado en la base de datos
    * @param numero_documento
     */
    public static boolean getExistEstudiante(String numero_documento) throws SQLException {
        boolean result = false;
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "select * from estudiante_convenios where documento_estudiante = '" + numero_documento + "' AND estado = 'A'";
            System.out.println("sql = " + sql);
            pstm = pool.con.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            System.out.println("error  camilo n" + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static ArrayList<Estudiantes> listarUniversidades() throws SQLException {
        ArrayList<Estudiantes> lista = new ArrayList();
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("select distinct universidad from estudiante_convenios");
            rs = pstm.executeQuery();
            while (rs.next()) {
                lista.add(new Estudiantes(rs.getString(1)));
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return lista;
    }

    public static int getDetalleConvPk(int id_viaje_tiquete) throws SQLException {
        int result = 0;
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("select detalle_conv_pk from tbl_viajes_tiquetes where id_viaje_tiquete = " + id_viaje_tiquete);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static List<String> getServicios(String origen, String destino) throws SQLException {
        List<String> result = new ArrayList<>();
        try {
            cn = Conexion.conectar2();
            pstm = cn.prepareStatement("select servicio "
                    + "from cpp_rutasweb a ,cpt_rutasweb b  where a.nro_trans=b.nro_trans and "
                    + "a.fecha_mod in (select MAX(fecha_mod) from cpp_rutasweb) "
                    + "AND origen = '" + origen + "' AND destino = '" + destino + "'");
            rs = pstm.executeQuery();
            if (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            cn.close();
            pstm.close();
        }
        return result;
    }

    //historico de facturas old app
    public static List<FacturaHistorico> getHistoricoFacturas(Date fecha_ini, Date fecha_fin, String empresa) throws SQLException {
        List<FacturaHistorico> result = new ArrayList<>();
        try {
            int emp = getCodSucurEmpOldApp(empresa);
            if (emp > 0) {
                con3 = Conexion.getConexionOldApp();
                String sql = "SELECT tbl_msucursales.nombre,"
                        + "nombres,"
                        + "cedula,origen,"
                        + "destino,valor,cantidad,total,"
                        + "DATE(fecha_venta),ot,ida_regreso,"
                        + "case when orden is null then 'N/A' else orden end "
                        + "FROM factura,"
                        + "tbl_msucursales "
                        + "WHERE  DATE(fecha_venta) BETWEEN '" + format.format(fecha_ini) + "' AND '" + format.format(fecha_fin) + "' and  factura.cod_sucuremp = tbl_msucursales.cod_sucuremp "
                        + "AND tbl_msucursales.cod_sucuremp IN (" + emp + ")";
                pstm = con3.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
//                    System.out.println(rs.getString(1)+" "+ rs.getString(2)+" "+ rs.getString(3)+" "+ 
//                                       rs.getString(4)+" "+  rs.getString(5)+" "+  rs.getString(6)+" "+ 
//                                       rs.getString(7)+" "+  rs.getString(8)+" "+  rs.getDate(9)+" "+  rs.getString(10)+" "+ 
//                                       rs.getString(11)+" "+  rs.getString(12));
                    result.add(new FacturaHistorico(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), rs.getString(10),
                            rs.getString(11), rs.getString(12)));
                }
                String sql2 = "SELECT cliente,nombre,cedula,origen,destino,"
                        + "CONCAT('$',cast(valor as char(15))),cantidad,CONCAT('$',cast(total as char(15))),"
                        + "DATE(fecha),'-',idRegreso,'-' FROM tbl_usuarioRegistro where "
                        + "DATE(fecha)  BETWEEN '" + format.format(fecha_ini) + "' AND '" + format.format(fecha_fin) + "' and cliente<>'prueba' and cod_cliente IN (" + emp + ")";
                pstm = con3.prepareStatement(sql2);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    result.add(new FacturaHistorico(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), rs.getString(10),
                            rs.getString(11), rs.getString(12)));
                }
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            con3.close();
            pstm.close();
            Conexion.cerrarConexion3();
        }
        return result;
    }

    public static int getCodSucurEmpOldApp(String emp) throws SQLException {
        int cod = 0;
        try {
            con3 = Conexion.getConexionOldApp();
            pstm = con3.prepareStatement("SELECT cod_sucuremp FROM tbl_msucursales WHERE nombre LIKE '%" + emp + "%'");
            rs = pstm.executeQuery();
            if (rs.next()) {
                cod = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            con3.close();
            pstm.close();
            Conexion.cerrarConexion3();
        }
        return cod;
    }

    public static String getValidateNodum(String user, String clave) throws SQLException {
        String result = "";
        try {
            String sql = "select top 1 Nombre from WF_PALM.dbo.Usuarios where Codigo='" + user + "' and Password ='" + clave + "' and Estado=0";
//            + "union "
//                    + "select Nombre from gl_usuarios where usuario='" + user + "' and estado_usuario='A' "
//                    + ") Y
            System.out.println("sql = " + sql);
            cn = Conexion.conectar2();
            pstm = cn.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getString(1);
            }
            System.out.println("result " + result);
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            cn.close();
            pstm.close();
        }
        return result;
    }

    public static boolean editCm(String consec, int val) throws SQLException {
        boolean resp = false;
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("update relacion_recibos set verificado = " + val + " where id_trans = '" + consec + "'");
            pstm.executeUpdate();
            resp = true;
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } finally {
            pool.con.close();
        }
        return resp;
    }

    /*
    * Método que valida el número de tiquete ingresado en el sistema
    * con el número de tiquete que esta en la base de datos
    * @param numtiquet
     */
    public static boolean validarTiqueteExist(String numtiquet) throws SQLException {

        boolean resp = false;
        try {
            String query = "select numero_tiquete from tiquete_estudiante where numero_tiquete = '" + numtiquet + "'";
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                resp = true;
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } finally {
            pool.con.close();
        }
        return resp;
    }

    /*
    * Método que valida el número de documento del estudiante ingresado en el sistema
    * con el número de documento que esta en la base de datos
     */
    public static boolean validarDocumentoExist(String documento) throws SQLException {

        boolean resp = false;
        try {
            String query = "select documento_estudiante from estudiante_convenios where documento_estudiante = '" + documento + "'";
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                resp = true;
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } finally {
            pool.con.close();
        }
        return resp;
    }

    public static boolean editValTrans(int id, int val) throws SQLException {
        boolean resp = false;
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("update tbl_usuarioRegistro set total = " + val + ", valor = " + val / 2 + " where id_registro = " + id + "");
            pstm.executeUpdate();
            resp = true;
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } finally {
            pool.con.close();
        }
        return resp;
    }

    public static boolean editEmpTrans(int id, String val) throws SQLException {
        boolean resp = false;
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("update tbl_usuarioRegistro set cliente = '" + val + "' where id_registro = " + id + "");
            pstm.executeUpdate();
            resp = true;
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } finally {
            pool.con.close();
        }
        return resp;
    }

    public static int getIdUserByEmpresa(int id_empresa) throws SQLException {
        int result = 0;
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("select id_usuario from usuarios where id_empresa = " + id_empresa + "");
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    // mas adelante cambiar estos 2 metodos y dejarlos en uno solo
    public static String getConsecutivoAgencia(String cod_agencia, String tabla) throws SQLException {
        String code = "";
        String newCode = "";
        int sum = 0;
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("select consecutivo from " + tabla + " where cod_agencia = '" + cod_agencia + "'");
            rs = pstm.executeQuery();
            if (rs.next()) {
                code = rs.getString(1);
            }
            if (code.equals("000000")) {
                newCode = "000001";
            } else {
                sum = Integer.parseInt(code) + 1;
                if (Integer.toString(sum).length() == 1) {
                    newCode = "00000" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 2) {
                    newCode = "0000" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 3) {
                    newCode = "000" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 4) {
                    newCode = "00" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 5) {
                    newCode = "0" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 6) {
                    newCode = Integer.toString(sum);
                }

            }
            pstm = pool.con.prepareStatement("update " + tabla
                    + " set consecutivo = '" + newCode + "' where cod_agencia = '" + cod_agencia + "'");
            pstm.executeUpdate();
        } catch (SQLException | NumberFormatException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return code;
    }

    public static void rollBackConsecutivoAgencias(String cod_agencia, String tabla, String newCode) throws SQLException {
        int res = Integer.parseInt(newCode);

        if (Integer.toString(res).length() == 1) {
            newCode = "00000" + Integer.toString(res);
        }
        if (Integer.toString(res).length() == 2) {
            newCode = "0000" + Integer.toString(res);
        }
        if (Integer.toString(res).length() == 3) {
            newCode = "000" + Integer.toString(res);
        }
        if (Integer.toString(res).length() == 4) {
            newCode = "00" + Integer.toString(res);
        }
        if (Integer.toString(res).length() == 5) {
            newCode = "0" + Integer.toString(res);
        }
        if (Integer.toString(res).length() == 6) {
            newCode = Integer.toString(res);
        }
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("update " + tabla
                    + " set consecutivo = '" + newCode + "' where cod_agencia = '" + cod_agencia + "'");
            pstm.executeUpdate();
        } catch (SQLException | NumberFormatException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }

    }
    //CONSECUTIVO CONTRAVIAS

    public static String getConsecutivoContravias(String cod_agencia) throws SQLException {
        String code = "";
        String newCode = "";
        int sum = 0;
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("select consecutivo from consecutivo_contravias where cod_agencia = '" + cod_agencia + "'");
            rs = pstm.executeQuery();
            if (rs.next()) {
                code = rs.getString(1);
            }

            if (code.equals("000000")) {
                newCode = "000001";
            } else {
                sum = Integer.parseInt(code) + 1;
                if (Integer.toString(sum).length() == 1) {
                    newCode = "00000" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 2) {
                    newCode = "0000" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 3) {
                    newCode = "000" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 4) {
                    newCode = "00" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 5) {
                    newCode = "0" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 6) {
                    newCode = Integer.toString(sum);
                }

            }

            String sqlu = "update consecutivo_contravias "
                    + "set consecutivo = '" + newCode + "' where cod_agencia = '" + cod_agencia + "'";
            pstm = pool.con.prepareStatement(sqlu);
            System.out.println(sqlu);
            pstm.executeUpdate();
        } catch (SQLException | NumberFormatException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return code;
    }

    //historico de contravias old app
    public static List<TblRegistroContravias> getHistoricoContravias(Date fecha_ini, Date fecha_fin, String agencia) throws SQLException {
        List<TblRegistroContravias> result = new ArrayList<>();
        try {
            System.out.println("agencia = " + agencia);
            cn = Conexion.getConexionOldApp();
            agencia = agencia.replace('-', ' ');
            System.out.println("");
            String and = "";
            if (!agencia.equals("")) {
                and = " AND usuario_mod LIKE '%" + agencia + "%'";
            }
            String sql = "SELECT * FROM tbl_registroContravias "
                    + "WHERE fecha BETWEEN '" + format.format(fecha_ini) + " 00:00:00' "
                    + "AND '" + format.format(fecha_fin) + " 23:59:59'" + and;
            pstm = cn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                result.add(new TblRegistroContravias(rs.getString("secuencia"), 60, rs.getString("nom_comprador"),
                        rs.getString("cc_comprador"), rs.getString("nom_viajero"), rs.getString("cc_viajero"),
                        rs.getString("origen"), rs.getString("destino"), rs.getInt("valor"),
                        rs.getInt("total"), rs.getString("idRegreso"), rs.getString("cod_bus"),
                        rs.getString("observacion"), rs.getDate("fecha"), rs.getString("usuario_mod"),
                        "No asignado", rs.getString("UsuarioTaquilla"), rs.getString("servicio"),
                        rs.getString("piso"), rs.getString("estado"), rs.getString("user_entrega"),
                        "No asignado", rs.getString("nomb_user_entrega"), rs.getString("telefono_comprador"),
                        rs.getString("no_reserva"), 0, rs.getInt("dinero_en_casa"),
                        rs.getInt("dinero_en_casa") == 1, rs.getDate("fecha_entrega"), "No asignado",
                        rs.getString("tiquete"), rs.getInt("cantidad")));
            }

        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            pstm.close();
            rs.close();
//            Conexion.cerrarConexion3();
        }
        return result;
    }

    //recaudarMoney contravias old app
    public static int recaudarMoneyold(TblRegistroContravias c) throws SQLException {
        int result = 0;
        try {
//            System.out.println("trns = " + c.getTransaccion());
            con3 = Conexion.getConexionOldApp();
            String sql = "UPDATE tbl_registroContravias set dinero_en_casa = " + (c.getDineroEnCasa() == 1 ? 0 : 1)
                    + " WHERE secuencia = '" + c.getTransaccion() + "'";
            pstm = con3.prepareStatement(sql);
            result = pstm.executeUpdate();

        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            Conexion.cerrarConexion3();
        }
        return result;
    }

    public static int countAlertas() throws SQLException {
        int result = 0;
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "select COUNT(*) from alertas where fecha_creacion = '" + format.format(new Date()) + "'";
            pstm = pool.con.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static String getEmpresaById(int idEmpresa) throws SQLException {
        String result = "";
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "select nombre from tbl_empresas where id_empresa = " + idEmpresa;
            pstm = pool.con.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static String getCiudadByCode(int codCiudad) throws SQLException {
        String result = "";
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "select ciudad from tbl_ciudades where cod_ciudad = " + codCiudad;
            pstm = pool.con.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static boolean updateTotalTransbyId(int idTrans) throws SQLException {
        boolean result = false;
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "select SUM(pagar) total from total_transaccion where trans = " + idTrans;
            pstm = pool.con.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                String query = "update transaccion set total = " + rs.getInt(1) + " "
                        + "where id_transaccion = " + idTrans;
//                System.out.println("query " +query);
                pstm = pool.con.prepareStatement(query);
                pstm.executeUpdate();
                result = true;
            }
            System.out.println("exito");
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static boolean updateTotalTransTiq(int idTrans) throws SQLException {
        boolean result = false;
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "select [dbo].[getTotalTiquetesTrans](" + idTrans + ")";
            pstm = pool.con.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                String query = "update transaccion set cant_tiquetes = " + rs.getInt(1) + " "
                        + "where id_transaccion = " + idTrans;
//                System.out.println("query " +query);
                pstm = pool.con.prepareStatement(query);
                pstm.executeUpdate();
                result = true;
            }
            System.out.println("exito");
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static List<String> cargarUsers() throws SQLException {
        List<String> users = new ArrayList();
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("Select nombres+' '+apellidos from usuarios where nombres like '%VALORES%'");
            rs = pstm.executeQuery();
            while (rs.next()) {
                users.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } finally {
            pool.con.close();
        }
        return users;
    }

    public static List<ReciboDeCaja> reciboDeCaja(int id_trans) throws SQLException {
        List<ReciboDeCaja> recibo = new ArrayList();
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "select  t.id_transaccion, "
                    + "CONVERT(datetime, t.fecha_mod) as fechaEntrega, "
                    + "t.cant_tiquetes, "
                    + "t.descripcion_tiquetes as desc_tiquetes, "
                    + "t.total, "
                    + "e.nombre empresa, "
                    + "t.userNodum, "
                    + "t.usuario_taquilla userTaquilla, "
                    + "t.taquilla, "
                    + "t.tiquetes_entregados, "
                    + "tv.documento, "
                    + "em.nombre, "
                    + "c1.ciudad as origen, "
                    + "c2.ciudad as destino, "
                    + "tv.valor_expal, "
                    + "tv.cant_tiquetes unidad_tiquete, "
                    + "vt.ida_regreso, "
                    + "s.servicio, "
                    + "case when vt.ida_regreso = 'Si' then  (tv.cant_tiquetes * tv.valor_expal)*2 "
                    + "else (tv.cant_tiquetes * tv.valor_expal) end as total_unidad "
                    + "from transaccion_viajero tv, transaccion t, tbl_viajes_tiquetes vt, "
                    + "tbl_ciudades c1, tbl_ciudades c2, empresa_empleado ee, tbl_empleados em, tbl_servicios s, "
                    + "detalle_convenio dc, tbl_empresas e, usuarios u "
                    + "where t.id_transaccion = tv.id_transaccion and tv.id_transaccion = " + id_trans + " "
                    + "and tv.id_viaje_tiquete = vt.id_viaje_tiquete "
                    + "and tv.cod_ciudad_origen = c1.cod_ciudad and tv.cod_ciudad_destino = c2.cod_ciudad "
                    + "and ee.documento = tv.documento and em.documento = tv.documento "
                    + "and tv.detalle_conv_pk = dc.detalle_conv_pk "
                    + "and dc.id_servicio = s.id_servicio "
                    + "and u.id_empresa = e.id_empresa and t.id_usuario = u.id_usuario";
            pstm = pool.con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                recibo.add(new ReciboDeCaja(rs.getInt(1), rs.getString(2), rs.getInt(3),
                        rs.getString(4), rs.getInt(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9),
                        rs.getString(10), rs.getString(11), rs.getString(12),
                        rs.getString(13), rs.getString(14), rs.getInt(15), rs.getInt(16),
                        rs.getString(17), rs.getString(18), rs.getInt(19)));
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return recibo;
    }

    public static List<TiquetesAutorizados> reciboDeCajaAutorizados(String arrayIdTrans) throws SQLException {
        List<TiquetesAutorizados> recibo = new ArrayList();
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "select t.*, u.nombres+''+u.apellidos from tiquetes_autorizados t , usuarios u where u.documento = t.usuario_solicita and t.id_carga in (" + arrayIdTrans.substring(0, arrayIdTrans.length() - 1) + ")";
            System.out.println("sql = " + sql);
            pstm = pool.con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                recibo.add(new TiquetesAutorizados(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getDate(8),
                        rs.getString(9), rs.getDate(10), rs.getString(11),
                        rs.getString(12), rs.getString(13), rs.getString(14),
                        rs.getString(15), rs.getString(16), rs.getString(17)));
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return recibo;
    }

    public static boolean existEmpresaEmpleado(String documento, int id_empresa) throws SQLException {
        boolean result = false;
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("select * from empresa_empleado where documento = '" + documento + "' and id_empresa = " + id_empresa);
            ResultSet rst = pstm.executeQuery();
            if (rst.next()) {
                result = true;
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static boolean anularTransaccion(String idTransAnular, String documentoUserLog) throws SQLException {
        boolean result = false;
        try {
            pool.con = pool.dataSource.getConnection();
            String query = "update tbl_usuarioRegistro set cliente = 'anulado', "
                    + "nombre = 'anulado', documento = 'anulado', valor = 0, cantidad = 0, total = 0, cod_bus = '', "
                    + "tiquete = '', taquilla = '', user_mod = '" + documentoUserLog + "' where transaccion = '" + idTransAnular + "'";
            pstm = pool.con.prepareStatement(query);
            pstm.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static boolean updateNumberTiquete(TiquetesAutorizados t) throws SQLException {
        boolean result = false;
        try {
            pool.con = pool.dataSource.getConnection();
            String query = "update tiquetes_autorizados set tiquete = '" + t.getTiquete() + "' where id_carga = " + t.getId_carga() + "";
            System.out.println("query " + query);
            pstm = pool.con.prepareStatement(query);
            pstm.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static TiquetesAutorizados getUserExist(String documento) throws SQLException {
        TiquetesAutorizados object = null;
        try {
            pool.con = pool.dataSource.getConnection();
            String query = "select top 1 documento, nombre_completo, telefono from tiquetes_autorizados\n"
                    + "where documento = '" + documento + "'";
//            System.out.println("query " + query);
            pstm = pool.con.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                object= new TiquetesAutorizados(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return object;
    }

}
