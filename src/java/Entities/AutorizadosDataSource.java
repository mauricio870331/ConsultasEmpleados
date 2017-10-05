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
public class AutorizadosDataSource implements JRDataSource {

    private List<TiquetesAutorizados> listaRecibo = new ArrayList();
    private int indiceDetalle = -1;

    public AutorizadosDataSource() {
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
                case "id_carga":
                    valor = listaRecibo.get(indiceDetalle).getId_carga();
                    break;
                case "documento":
                    valor = listaRecibo.get(indiceDetalle).getDocumento();
                    break;
                case "nombre_completo":
                    valor = listaRecibo.get(indiceDetalle).getNombre_completo();
                    break;
                case "telefono":
                    valor = listaRecibo.get(indiceDetalle).getTelefono();
                    break;
                case "origen":
                    valor = listaRecibo.get(indiceDetalle).getOrigen();
                    break;
                case "destino":
                    valor = listaRecibo.get(indiceDetalle).getDestino();
                    break;
                case "motivo_solicitud":
                    valor = listaRecibo.get(indiceDetalle).getMotivo_solicitud();
                    break;
                case "fecha_solicitud":
                    valor = listaRecibo.get(indiceDetalle).getFecha_solicitud();
                    break;
                case "estado":
                    valor = listaRecibo.get(indiceDetalle).getEstado();
                    break;
                case "fecha_entrega":
                    valor = listaRecibo.get(indiceDetalle).getFecha_entrega();
                    break;
                case "tipo_servicio":
                    valor = listaRecibo.get(indiceDetalle).getTipo_servicio();
                    break;
                case "taquilla_entrega":
                    valor = listaRecibo.get(indiceDetalle).getTaquilla_entrega();
                    break;
                case "usuario_taquilla":
                    valor = listaRecibo.get(indiceDetalle).getUsuario_taquilla();
                    break;
                case "idaRegreso":
                    valor = listaRecibo.get(indiceDetalle).getIdaRegreso();
                    break;
                case "usaurio_solicita":
                    valor = listaRecibo.get(indiceDetalle).getUsaurio_solicita();
                    break;
                case "tiquete":
                    valor = listaRecibo.get(indiceDetalle).getTiquete();
                    break;
            }
        }
        return valor;
    }

    public List<TiquetesAutorizados> getListaRecibo() {
        return listaRecibo;
    }

    public void setListaRecibo(List<TiquetesAutorizados> listaRecibo) {
        this.listaRecibo = listaRecibo;
    }

}
