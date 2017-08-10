package Modelo;

import java.util.Date;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
public class ConsultaGeneral {

    /**
     * Variables privadas: esta clase implementa diferentes tipos de datos ya
     * sean enteros, dobles, cadenas y fechas que seran usadas en general desde
     * cada clase que necesite realizar petciones a la base de datos, esta
     * contiene diferentes constructores adaptados para cada peticion.
     */
    private int num1;
    private int num2;
    private int num3;
    private int num4;
    private int num5;
    private int num6;
    private int num7;
    private int num8;
    private int num9;
    private int num10;
    private String str1;
    private String str2;
    private String str3;
    private String str4;
    private String str5;
    private String str6;
    private String str7;
    private String str8;
    private String str9;
    private String str10;
    private String str11;
    private String str12;
    private String str13;
    private String str14;
    private String str15;
    private boolean bool1;
    private boolean bool2;
    private boolean bool3;
    private boolean bool4;
    private boolean bool5;
    private boolean bool6;
    private boolean bool7;
    private boolean bool8;
    private boolean bool9;
    private boolean bool10;
    private Date fecha1;
    private Date fecha2;
    private Date fecha3;
    private Date fecha4;
    private Date fecha5;
    private Date fecha6;
    private Date fecha7;
    private Date fecha8;
    private Date fecha9;
    private Date fecha10;
    private double doble1;
    private double doble2;
    private double doble3;
    private double doble4;
    private double doble5;
    private double doble6;
    private double doble7;
    private double doble8;
    private double doble9;
    private double doble10;

    
    //consulta estudiantes
    public ConsultaGeneral(int num1,  String str1, String str2, String str3, String str4,int num2, String str5) {
        this.num1 = num1;
        this.num2 = num2;
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
        this.str5 = str5;
    }
    
    

    //Constructores
    //Constructor Login
    public ConsultaGeneral(String str1, String str2, String str3, Date fecha1, boolean bool1, int num1) {
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.bool1 = bool1;
        this.fecha1 = fecha1;
        this.num1 = num1;
    }

    public ConsultaGeneral(int num1, String str1, String str2, String str3, int num2, String str4, int num3) {
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
    }

    //Constructor alertas
    public ConsultaGeneral(int num1, String str1, Date fecha1, String str2, String str3, Date fecha2, Date fecha3) {
        this.num1 = num1;
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.fecha1 = fecha1;
        this.fecha2 = fecha2;
        this.fecha3 = fecha3;

    }

    // Construct Roles
    public ConsultaGeneral(int num1, String str1, String str2, String str3) {
        this.num1 = num1;
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;

    }

    // Construct docs
    public ConsultaGeneral(int num1, String str1, String str2, String str3, Date fecha1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.fecha1 = fecha1;

    }

    //agencias   
    //id_convenio by iddetalle
    public ConsultaGeneral(int num1) {
        this.num1 = num1;
    }

    public ConsultaGeneral(String str1) {
        this.str1 = str1;
    }

    //valor cliente-expal
    public ConsultaGeneral(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    //entregaConvenio
    public ConsultaGeneral(int num1, int num2, String str1,
            String str2, String str3,
            String str4, String str5,
            String str6, String str7,
            int num3, int num4,
            int num5, int num6,
            int num7, int num8, int num9, int num10, String str8) {
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.num4 = num4;
        this.num5 = num5;
        this.num6 = num6;
        this.num7 = num7;
        this.num8 = num8;
        this.num9 = num9;
        this.num10 = num10;
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
        this.str5 = str5;
        this.str6 = str6;
        this.str7 = str7;
        this.str8 = str8;
    }

    // Construct Usuarios
    public ConsultaGeneral(int num1, String str1, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, int num2, int num3, int num4) {
        this.num1 = num1;
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
        this.str5 = str5;
        this.str6 = str6;
        this.str7 = str7;
        this.str8 = str8;
        this.str9 = str9;
        this.str10 = str10;
        this.str11 = str11;
        this.str12 = str12;
        this.num2 = num2;
        this.num3 = num3;
        this.num4 = num4;
    }

    // Construct Servicios
    public ConsultaGeneral(int num1, String str1, String str2, String str3, String str4) {
        this.num1 = num1;
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
    }

    // Construct EmpresaConvenio
    public ConsultaGeneral(int num1, String str1, int num2, String str2, Date fecha1, Date fecha2, int num3, String str3) {
        this.num1 = num1;
        this.str1 = str1;
        this.num2 = num2;
        this.num3 = num3;
        this.str2 = str2;
        this.str3 = str3;
        this.fecha1 = fecha1;
        this.fecha2 = fecha2;
    }

    // Construct impresiones realizadas 
    public ConsultaGeneral(String str1, int num1, String str2, String str3, String str4, Date fecha1, String str5, int num2) {
        this.num1 = num1;
        this.num2 = num2;
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
        this.str5 = str5;
        this.fecha1 = fecha1;
    }

    // Construct impresiones cmgenerado 
    public ConsultaGeneral(String str1, int num1, String str2, String str3, String str4, Date fecha1, int num2, String str5, int num3, String str6) {
        this.num1 = num1;
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
        this.str5 = str5;
        this.str6 = str6;
        this.num2 = num2;
        this.fecha1 = fecha1;
        this.num3 = num3;
    }

    // Construct impresiones realizadas usuarios
    public ConsultaGeneral(String str1, int num1, String str2, String str3, String str4, Date fecha1) {
        this.num1 = num1;
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
        this.fecha1 = fecha1;
    }

    public ConsultaGeneral(String str1, String str2,
            String str3, String str4,
            String str5, String str6,
            String str7, String str8,
            int num1, int num2,
            String str9, String str10,
            String str11, String str12,
            String str13) {
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
        this.str5 = str5;
        this.str6 = str6;
        this.str7 = str7;
        this.str8 = str8;
        this.str9 = str9;
        this.str10 = str10;
        this.str11 = str11;
        this.str12 = str12;
        this.str13 = str13;
        this.num1 = num1;
        this.num2 = num2;
    }

    //entregar contravias
    public ConsultaGeneral(String str1,
            String str2,
            String str3,
            String str4,
            String str5,
            String str6,
            String str7,
            String str8,
            String str9,
            int num1,
            int num2,
            int num3,
            String str10,
            String str11,
            String str12,
            String str13,
            String str14,
            Date fecha1,
            Date fecha2,
            String str15,
            int num4) {
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
        this.str5 = str5;
        this.str6 = str6;
        this.str7 = str7;
        this.str8 = str8;
        this.str9 = str9;
        this.str10 = str10;
        this.str11 = str11;
        this.str12 = str12;
        this.str13 = str13;
        this.str14 = str14;
        this.str15 = str15;
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.num4 = num4;
        this.fecha1 = fecha1;
        this.fecha2 = fecha2;
    }

    // Construct DetalleCOnvenio
    public ConsultaGeneral(int num1, int num2, int num3, int num4, int num5, int num6,
            int num7,
            int num8,
            int num9,
            Date date1,
            Date date2, String str3, String str4, String str5,
            String str6, String str7) {
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.num4 = num4;
        this.num5 = num5;
        this.num6 = num6;
        this.num7 = num7;
        this.num8 = num8;
        this.num9 = num9;
        this.fecha1 = date1;
        this.fecha2 = date2;
        this.str3 = str3;
        this.str4 = str4;
        this.str5 = str5;
        this.str6 = str6;
        this.str7 = str7;
    }

    public ConsultaGeneral(int num1, String str1) {
        this.num1 = num1;
        this.str1 = str1;
    }

    //cmgen
    public ConsultaGeneral(String str1, Date fecha1, Date fecha2, String str2, String str3, String str4, String str5, int num1) {
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
        this.str5 = str5;
        this.fecha1 = fecha1;
        this.fecha2 = fecha2;
        this.num1 = num1;

    }

    //autocomplete
    public ConsultaGeneral(String str1, String str2) {
        this.str1 = str1;
        this.str2 = str2;
    }

    //facturaventa
    public ConsultaGeneral(String str1, String str2,
            String str3,
            String str4, String str5,
            int num1, int num2, Date fecha1,
            String str6, int num3, String str7, String str8, String str9) {
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
        this.str5 = str5;
        this.str6 = str6;
        this.str7 = str7;
        this.str8 = str8;
        this.str9 = str9;
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.fecha1 = fecha1;
    }

    ConsultaGeneral(int num1, String str1, Date date1, Date date2, String str2, int doble1, String str3, String str4, int num2) {
        this.num1 = num1;
        this.str1 = str1;
        this.fecha1 = date1;
        this.fecha2 = date2;
        this.str2 = str2;
        this.num3 = doble1;
        this.str3 = str3;
        this.str4 = str4;
        this.num2 = num2;

    }

    //empleados
    ConsultaGeneral(String str1,
            String str2,
            String str3,
            String str4,
            String str5,
            int num1,
            String str6,
            Date date1,
            Date date2,
            int num2) {
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
        this.str5 = str5;
        this.num1 = num1;
        this.str6 = str6;
        this.fecha1 = date1;
        this.fecha2 = date2;
        this.num2 = num2;
    }

    //empleados tiquetes
    ConsultaGeneral(String str6,
            String str1,
            String str2,
            Date date1,
            Date date2,
            String str3,
            String str4,
            int num2,
            String str5, int num1, String str7, boolean bool1) {
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
        this.str5 = str5;
        this.str6 = str6;
        this.str7 = str7;
        this.fecha1 = date1;
        this.fecha2 = date2;
        this.num2 = num2;
        this.num1 = num1;
        this.bool1 = bool1;
    }

    //saldos pend
    ConsultaGeneral(String str6,
            String str1,
            String str2,
            Date date1,
            Date date2,
            String str3,
            String str4,
            int num2,
            String str5, int num1, String str7, int num3) {
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
        this.str5 = str5;
        this.str6 = str6;
        this.str7 = str7;
        this.fecha1 = date1;
        this.fecha2 = date2;
        this.num2 = num2;
        this.num1 = num1;
        this.num3 = num3;
    }

    ConsultaGeneral(int num1, String str1, String str2, String str7, String str3, String str4, String str5, String str6) {
        this.num1 = num1;
        this.str1 = str1;
        this.str2 = str2;
        this.str7 = str7;
        this.str3 = str3;
        this.str4 = str4;
        this.str5 = str5;
        this.str6 = str6;
        
    }

   
    
    

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public int getNum3() {
        return num3;
    }

    public void setNum3(int num3) {
        this.num3 = num3;
    }

    public int getNum4() {
        return num4;
    }

    public void setNum4(int num4) {
        this.num4 = num4;
    }

    public int getNum5() {
        return num5;
    }

    public void setNum5(int num5) {
        this.num5 = num5;
    }

    public int getNum6() {
        return num6;
    }

    public void setNum6(int num6) {
        this.num6 = num6;
    }

    public int getNum7() {
        return num7;
    }

    public void setNum7(int num7) {
        this.num7 = num7;
    }

    public int getNum8() {
        return num8;
    }

    public void setNum8(int num8) {
        this.num8 = num8;
    }

    public int getNum9() {
        return num9;
    }

    public void setNum9(int num9) {
        this.num9 = num9;
    }

    public int getNum10() {
        return num10;
    }

    public void setNum10(int num10) {
        this.num10 = num10;
    }

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public String getStr3() {
        return str3;
    }

    public void setStr3(String str3) {
        this.str3 = str3;
    }

    public String getStr4() {
        return str4;
    }

    public void setStr4(String str4) {
        this.str4 = str4;
    }

    public String getStr5() {
        return str5;
    }

    public void setStr5(String str5) {
        this.str5 = str5;
    }

    public String getStr6() {
        return str6;
    }

    public void setStr6(String str6) {
        this.str6 = str6;
    }

    public String getStr7() {
        return str7;
    }

    public void setStr7(String str7) {
        this.str7 = str7;
    }

    public String getStr8() {
        return str8;
    }

    public void setStr8(String str8) {
        this.str8 = str8;
    }

    public String getStr9() {
        return str9;
    }

    public void setStr9(String str9) {
        this.str9 = str9;
    }

    public String getStr10() {
        return str10;
    }

    public void setStr10(String str10) {
        this.str10 = str10;
    }

    public String getStr11() {
        return str11;
    }

    public void setStr11(String str11) {
        this.str11 = str11;
    }

    public String getStr12() {
        return str12;
    }

    public void setStr12(String str12) {
        this.str12 = str12;
    }

    public boolean isBool1() {
        return bool1;
    }

    public void setBool1(boolean bool1) {
        this.bool1 = bool1;
    }

    public boolean isBool2() {
        return bool2;
    }

    public void setBool2(boolean bool2) {
        this.bool2 = bool2;
    }

    public boolean isBool3() {
        return bool3;
    }

    public void setBool3(boolean bool3) {
        this.bool3 = bool3;
    }

    public boolean isBool4() {
        return bool4;
    }

    public void setBool4(boolean bool4) {
        this.bool4 = bool4;
    }

    public boolean isBool5() {
        return bool5;
    }

    public void setBool5(boolean bool5) {
        this.bool5 = bool5;
    }

    public boolean isBool6() {
        return bool6;
    }

    public void setBool6(boolean bool6) {
        this.bool6 = bool6;
    }

    public boolean isBool7() {
        return bool7;
    }

    public void setBool7(boolean bool7) {
        this.bool7 = bool7;
    }

    public boolean isBool8() {
        return bool8;
    }

    public void setBool8(boolean bool8) {
        this.bool8 = bool8;
    }

    public boolean isBool9() {
        return bool9;
    }

    public void setBool9(boolean bool9) {
        this.bool9 = bool9;
    }

    public boolean isBool10() {
        return bool10;
    }

    public void setBool10(boolean bool10) {
        this.bool10 = bool10;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public Date getFecha2() {
        return fecha2;
    }

    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
    }

    public Date getFecha3() {
        return fecha3;
    }

    public void setFecha3(Date fecha3) {
        this.fecha3 = fecha3;
    }

    public Date getFecha4() {
        return fecha4;
    }

    public void setFecha4(Date fecha4) {
        this.fecha4 = fecha4;
    }

    public Date getFecha5() {
        return fecha5;
    }

    public void setFecha5(Date fecha5) {
        this.fecha5 = fecha5;
    }

    public Date getFecha6() {
        return fecha6;
    }

    public void setFecha6(Date fecha6) {
        this.fecha6 = fecha6;
    }

    public Date getFecha7() {
        return fecha7;
    }

    public void setFecha7(Date fecha7) {
        this.fecha7 = fecha7;
    }

    public Date getFecha8() {
        return fecha8;
    }

    public void setFecha8(Date fecha8) {
        this.fecha8 = fecha8;
    }

    public Date getFecha9() {
        return fecha9;
    }

    public void setFecha9(Date fecha9) {
        this.fecha9 = fecha9;
    }

    public Date getFecha10() {
        return fecha10;
    }

    public void setFecha10(Date fecha10) {
        this.fecha10 = fecha10;
    }

    public double getDoble1() {
        return doble1;
    }

    public void setDoble1(double doble1) {
        this.doble1 = doble1;
    }

    public double getDoble2() {
        return doble2;
    }

    public void setDoble2(double doble2) {
        this.doble2 = doble2;
    }

    public double getDoble3() {
        return doble3;
    }

    public void setDoble3(double doble3) {
        this.doble3 = doble3;
    }

    public double getDoble4() {
        return doble4;
    }

    public void setDoble4(double doble4) {
        this.doble4 = doble4;
    }

    public double getDoble5() {
        return doble5;
    }

    public void setDoble5(double doble5) {
        this.doble5 = doble5;
    }

    public double getDoble6() {
        return doble6;
    }

    public void setDoble6(double doble6) {
        this.doble6 = doble6;
    }

    public double getDoble7() {
        return doble7;
    }

    public void setDoble7(double doble7) {
        this.doble7 = doble7;
    }

    public double getDoble8() {
        return doble8;
    }

    public void setDoble8(double doble8) {
        this.doble8 = doble8;
    }

    public double getDoble9() {
        return doble9;
    }

    public void setDoble9(double doble9) {
        this.doble9 = doble9;
    }

    public double getDoble10() {
        return doble10;
    }

    public void setDoble10(double doble10) {
        this.doble10 = doble10;
    }

    public String getStr13() {
        return str13;
    }

    public void setStr13(String str13) {
        this.str13 = str13;
    }

    public String getStr14() {
        return str14;
    }

    public void setStr14(String str14) {
        this.str14 = str14;
    }

    public String getStr15() {
        return str15;
    }

    public void setStr15(String str15) {
        this.str15 = str15;
    }

}
