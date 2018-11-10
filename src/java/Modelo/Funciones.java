package Modelo;

import Beans.LoginBean;
import Entities.DesprendiblePago;
import Entities.usersToConsultas;
import com.barcodelib.barcode.QRCode;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import org.apache.commons.codec.binary.Base64;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
public class Funciones {

    static ConexionPool pool = new ConexionPool();
    static Connection cn;
    static Connection con3 = null;
    static PreparedStatement pstm;
    static ResultSet rs;
    static CallableStatement cstmt;
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public usersToConsultas Login(String doc, String pass, ArrayList<String> log) throws SQLException, Exception {
        usersToConsultas user = null;
        try {
//            System.out.println("enciprtada = " + Encriptar(pass));
            System.out.println("desencriptada = " + Desencriptar("mzi0i2m19F8G61xzOCP2lA=="));
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("select * from usersToConsultas "
                    //+ "where (documento ='" + doc + "')");
                    + "where (documento ='" + doc + "' or nikname ='" + doc + "' or correo ='" + doc + "') and pass = '" + Encriptar(pass) + "'");
            rs = pstm.executeQuery();
            if (rs.next()) {
                user = new usersToConsultas(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7));
                String query = "insert into logConsultasEmpleados "
                        + "values((select case when max(id_log) is null then 1 else  max(id_log)+1 end id_log from logConsultasEmpleados),"
                        + "'" + log.get(0) + "', '" + log.get(1) + "', '" + log.get(2) + "', " + rs.getInt(1) + ", getdate())";
                System.out.println("query -> " + query);
                pstm = pool.con.prepareStatement(query);
                pstm.execute();
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
            pstm.close();
        }
        return user;
    }

    public static String getEmpresa(String doc) throws SQLException, Exception {
        String Empresa = "";
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("select cod_emp from v_RHTrabajador where cod_persona = '" + doc + "'");
            rs = pstm.executeQuery();
            if (rs.next()) {
                Empresa = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
            pstm.close();
        }
        return Empresa;

    }

    public List<DesprendiblePago> returnListDesprendibles(int fecha_ini, int fecha_fin, String doc_empleado) throws SQLException {
        List<DesprendiblePago> listdesprendibles = new ArrayList();
        System.out.println("por aqui, generando desprendibles");
        try {
            String query = "select * from tbl_consultasEmpleados  where documento = '" + doc_empleado + "' "
                    + "and aniomes between " + fecha_ini + " and " + fecha_fin;
            System.out.println("query " + query);
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                listdesprendibles.add(new DesprendiblePago(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
                        rs.getInt(11), rs.getInt(12), rs.getInt(13), rs.getInt(14), rs.getInt(15), rs.getInt(16), rs.getInt(17),
                        rs.getInt(18), rs.getInt(19), rs.getInt(20), rs.getInt(21), rs.getInt(22), rs.getInt(23), rs.getInt(24),
                        rs.getInt(25), rs.getInt(26), rs.getInt(27), rs.getInt(28), rs.getInt(29), rs.getInt(30), rs.getInt(31),
                        rs.getInt(32), rs.getInt(33), rs.getInt(34), rs.getInt(35), rs.getInt(36), rs.getInt(37),
                        rs.getInt(38), rs.getInt(39), rs.getInt(40), rs.getInt(41), rs.getInt(42), rs.getInt(43),
                        rs.getInt(44), rs.getInt(45), rs.getInt(46), rs.getInt(47), rs.getString(48), rs.getString(49),
                        rs.getString(50), rs.getInt(51), rs.getDate(52), rs.getInt(53), rs.getInt(1)));
            }
        } catch (SQLException e) {
            System.out.println("error linea 1250 " + e);
        } finally {
            pool.con.close();
            pstm.close();
        }
        return listdesprendibles;
    }

    public boolean validarUsuario(String documento, int opc) throws SQLException {
        boolean result = false;
        try {
            pool.con = pool.dataSource.getConnection();
            String query = "";
            if (opc == 1) {
                query = "select top 1 cod_persona from v_rhtrabajador where cod_persona = '" + documento + "'";
            } else {
                query = "select top 1 documento from usersToConsultas where documento = '" + documento + "'";
            }
            pstm = pool.con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public boolean registrarUsuario(usersToConsultas user) throws SQLException {
        boolean result = false;
        try {
            pool.con = pool.dataSource.getConnection();
            String query = "insert into usersToConsultas values ((select case when (max(id_trans)+1) is null then 1 else (max(id_trans)+1) end from usersToConsultas),"
                    + "'" + user.getNickName() + "', '" + user.getCorreo() + "','" + user.getRecibirCorreos() + "',"
                    + "'" + user.getDocumento() + "','" + Encriptar(user.getPass()) + "', '" + format.format(user.getFecha_exp_cc()) + "',"
                    + "(select convert(varchar(4),DATEPART(yyyy,GETDATE()))+''+case when len(DATEPART(m,GETDATE())) = 1 then '0'+convert(varchar(2),DATEPART(m,GETDATE())) else convert(varchar(2),DATEPART(m,GETDATE())) end),'N')";
            System.out.println("query " + query);
            pstm = pool.con.prepareStatement(query);
            pstm.execute();
            result = true;
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static String Encriptar(String texto) {
        String secretKey = "expalcali2018"; //llave para encriptar datos
        String base64EncryptedString = "";
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);

        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            System.out.println("error " + ex);
        }
        return base64EncryptedString;
    }

    public static String Desencriptar(String textoEncriptado) throws Exception {

        String secretKey = "expalcali2018"; //llave para desenciptar datos
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {

        }
        return base64EncryptedString;
    }

    public boolean reCordarClave(usersToConsultas user) throws SQLException, Exception {
        boolean result = false;
        int option_close = 0;
        try {
            pool.con = pool.dataSource.getConnection();
            String query = "select * from usersToConsultas where documento = '" + user.getDocumento() + "' and fec_exp_documento = '" + format.format(user.getFecha_exp_cc()) + "'";
            System.out.println("query " + query);
            pstm = pool.con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
//                String partCorreo[] = rs.getString(3).split("@");
//                if (partCorreo[1].contains("gmail")) {
                enviarConGMail(rs.getString(3).replace(" ", ""), "Recordatorio Clave", "Expreso Palmira le recuerda su Usuario y Clave de acceso a la plataforma de consultas.\n\n"
                        + "Usuario: " + rs.getString(5) + "\nClave: " + Desencriptar(rs.getString(6)));
//                } else {
//                    option_close = 1;
//                    System.out.println("clave " + Desencriptar(rs.getString(6)));
//                    cstmt = pool.con.prepareCall("{call EnviarEmail(?,?,?,?,?,?)}");
//                    cstmt.setString(1, "Consultas ExpresoP");
//                    cstmt.setString(2, rs.getString(3));//jose.manzi@expresopalmira.com.co;cristina.ocana@expresopalmira.com.co
//                    cstmt.setString(3, "Recordatorio Clave");
//                    cstmt.setString(4, "Expreso Palmira le recuerda su Usuario y Clave de acceso a la plataforma de consultas.<br><br>"
//                            + "Usuario: " + rs.getString(5) + "<br>Clave: " + Desencriptar(rs.getString(6)));
//                    cstmt.setString(5, "Prueba");
//                    cstmt.setInt(6, 2);
//                    cstmt.execute();
//                }
                result = true;
            }
        } catch (SQLException e) {
            System.out.println("error ---mauricio" + e);
        } finally {
            if (option_close == 1) {
                cstmt.close();
            }
            pstm.close();
            pool.con.close();
        }
        return result;
    }

    public boolean enviarConGMail(String destinatario, String asunto, String cuerpo) {
        // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
        String remitente = "desarrollo1expresopalmira@gmail.com";  //Para la dirección nomcuenta@gmail.com
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", "1113626301");    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   //Se podrían añadir varios de la misma manera
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, "1113626301");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            System.out.println("error mail = " + me);
        }
        return false;
    }

    public String generarQrValidation(int opc) throws SQLException, Exception {//opc 1 cartas 2 desprendibles
        String realPath = System.getProperty("java.io.tmpdir");
        String result = "";
        int idLogUpdate = 0;
        String queryU = "";
        int udm = 0, resol = 68, rot = 0;
        float mi = 0.000f, md = 0.000f, ms = 0.000f, min = 0.000f, tam = 3.00f;
        try {
            LoginBean l = new LoginBean();
            pool.con = pool.dataSource.getConnection();
            String query = "select max(id_log) from logConsultasEmpleados where id_user =" + l.getDatosUserToconsulta().getId_trans();
            System.out.println("query log " + query);
            pstm = pool.con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                idLogUpdate = rs.getInt(1);
                if (opc == 1) {
                    query = "select * from qrcertificados where id_qr = 'CRT'";
                } else {
                    query = "select * from qrcertificados where id_qr = 'DES'";
                }
                pstm = pool.con.prepareStatement(query);
                rs = pstm.executeQuery();
                if (rs.next()) {

                    result = rs.getString(1) + "" + rs.getString(2);
                    System.out.println(rs.getString(1) + "" + rs.getString(2));
                    QRCode c = new QRCode();
                    c.setData(rs.getString(1) + "" + rs.getString(2) + "\nNombre: " + l.getDatosUserToconsulta().getNickName() + "\nCC: " + l.getDatosUserToconsulta().getDocumento() + "");
                    c.setDataMode(QRCode.MODE_BYTE);
                    c.setUOM(udm);
                    c.setLeftMargin(mi);
                    c.setRightMargin(md);
                    c.setTopMargin(ms);
                    c.setBottomMargin(min);
                    c.setResolution(resol);
                    c.setRotate(rot);
                    c.setModuleSize(tam);
                    c.renderBarcode(realPath + "qr.png");
                    result = realPath + "qr.png";
                    query = "update qrcertificados set qrcod = (qrcod+1) where id_qr = '" + rs.getString(1) + "'";
                    pstm = pool.con.prepareStatement(query);
                    pstm.executeUpdate();
                    if (opc == 1) {
                        queryU = "insert into logTransaccion values (" + l.getDatosUserToconsulta().getId_trans() + ",'" + rs.getString(1) + "" + rs.getString(2) + "','Carta'," + idLogUpdate + ")";
                    } else {
                        queryU = "insert into logTransaccion values (" + l.getDatosUserToconsulta().getId_trans() + ",'" + rs.getString(1) + "" + rs.getString(2) + "','Desprendible'," + idLogUpdate + ")";
                    }
                    System.out.println("queryU = " + queryU);
                    pstm = pool.con.prepareStatement(queryU);
                    pstm.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pstm.close();
            pool.con.close();
        }
        return result;
    }

}
