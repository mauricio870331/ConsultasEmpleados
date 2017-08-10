/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author clopez
 */
public class ReciboDeCajaDataSource implements JRDataSource {

    private List<ReciboDeCaja> listaRecibo = new ArrayList();
    private int indiceDetalle = -1;

    public ReciboDeCajaDataSource() {
    }

    @Override
    public boolean next() throws JRException {
        return ++indiceDetalle < listaRecibo.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        if (null != jrf.getName()) {
            switch (jrf.getName()) {
                case "id_transaccion":
                    valor = listaRecibo.get(indiceDetalle).getId_transaccion();
                    break;
                case "fechaEntrega":
                    valor = listaRecibo.get(indiceDetalle).getFechaEntrega();
                    break;
                case "cant_tiquetes":
                    valor = listaRecibo.get(indiceDetalle).getCant_tiquetes();
                    break;
                case "desc_tiquetes":
                    valor = listaRecibo.get(indiceDetalle).getDesc_tiquetes();
                    break;
                case "total":
                    valor = listaRecibo.get(indiceDetalle).getTotal();
                    break;
                case "empresa":
                    valor = listaRecibo.get(indiceDetalle).getEmpresa();
                    break;
                case "userNodum":
                    valor = listaRecibo.get(indiceDetalle).getUserNodum();
                    break;
                case "userTaquilla":
                    valor = listaRecibo.get(indiceDetalle).getUserTaquilla();
                    break;
                case "taquilla":
                    valor = listaRecibo.get(indiceDetalle).getTaquilla();
                    break;
                case "tiquetes_entregados":
                    valor = listaRecibo.get(indiceDetalle).getTiquetes_entregados();
                    break;
                case "documento":
                    valor = listaRecibo.get(indiceDetalle).getDocumento();
                    break;
                case "nombre":
                    valor = listaRecibo.get(indiceDetalle).getNombre();
                    break;
                case "origen":
                    valor = listaRecibo.get(indiceDetalle).getOrigen();
                    break;
                case "destino":
                    valor = listaRecibo.get(indiceDetalle).getDestino();
                    break;
                case "valor_expal":
                    valor = listaRecibo.get(indiceDetalle).getValor_expal();
                    break;
                case "unidad_tiquete":
                    valor = listaRecibo.get(indiceDetalle).getUnidad_tiquete();
                    break;
                case "ida_regreso":
                    valor = listaRecibo.get(indiceDetalle).getIda_regreso();
                    break;
                case "servicio":
                    valor = listaRecibo.get(indiceDetalle).getServicio();
                    break;
                case "total_unidad":
                    valor = listaRecibo.get(indiceDetalle).getTotal_unidad();
                    break;
            }
        }
        return valor;
    }

    public List<ReciboDeCaja> getListaRecibo() {
        return listaRecibo;
    }

    public void setListaRecibo(List<ReciboDeCaja> listaRecibo) {
        this.listaRecibo = listaRecibo;
    }

}
