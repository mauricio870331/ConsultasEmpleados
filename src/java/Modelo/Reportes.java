/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Entities.TblusuarioRegistro;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.struts.action.Action;

/**
 *
 * @author admin
 */
public class Reportes extends Action {

    public static Document ReciboCajaRegistroTiquete(TblusuarioRegistro x, Document document) throws DocumentException, FileNotFoundException, IOException {
        System.out.println("- " + x.toString());
        SimpleDateFormat format2 = new SimpleDateFormat("dd/M/yyyy H:m:s");
        SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
        Date fecha = new Date();
        document.setMargins(10, 10, 10, 10);
        document.setMarginMirroring(true);
        // step 3
        document.open();
        // step 4
        Font titulo = new Font(Font.FontFamily.TIMES_ROMAN, 13.0f, Font.NORMAL);
        Font f2 = new Font(Font.FontFamily.TIMES_ROMAN, 9.0f, Font.NORMAL);
        document.add(new Paragraph("                  RECIBO DE CAJA", titulo));
        document.add(new Paragraph("                            Afiliacion Palmira S.A", f2));
        document.add(new Paragraph("\t"));
        document.add(new Paragraph("Fecha:            " + format.format(x.getFechaviaje()), f2));
        document.add(new Paragraph("Cliente:          " + x.getCliente(), f2));
        document.add(new Paragraph("Usuario:         " + x.getNombre(), f2));
        document.add(new Paragraph("Cedula:          " + x.getDocumento(), f2));
        document.add(new Paragraph("Nro. tiquete:  " + x.getTiquete(), f2));
        document.add(new Paragraph("Origen:          " + x.getNomCiudadOrigen(), f2));
        document.add(new Paragraph("Destino:         " + x.getNomCiudadDestino(), f2));
        document.add(new Paragraph("Valor:            " + x.getValor(), f2));
        document.add(new Paragraph("Ida/Reg:         " + x.getIdaRegreso(), f2));
        document.add(new Paragraph("Bus:               " + x.getCodBus(), f2));
        document.add(new Paragraph("Observacion: " + x.getObservacion(), f2));
        document.add(new Paragraph("Auxiliar:        " + x.getTaquilla(), f2));
        document.add(new Paragraph("Total:             " + x.getTotal(), f2));

        document.add(new Paragraph("\t"));
        document.add(new Paragraph("Firma Usuario:", f2));
        document.add(new Paragraph("\t"));
        document.add(new Paragraph("" + format2.format(fecha), f2));

        // step 5
        document.close();
        System.out.println("creo bien");
//        File path = new File("reportes/Reportetiq.pdf");
//        Desktop.getDesktop().open(path);
        return document;
    }



}
