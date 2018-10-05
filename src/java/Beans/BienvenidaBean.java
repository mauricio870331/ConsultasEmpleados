package Beans;

import java.io.IOException;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;

@ManagedBean(name = "bienvenidaBean")
@RequestScoped
public class BienvenidaBean {

    private DefaultDiagramModel model;

    @PostConstruct
    public void init() {
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);

        Element elementA = new Element("Tareas Que Puedes Realizar", "32em", "3em");
        elementA.addEndPoint(new DotEndPoint(EndPointAnchor.BOTTOM));

        Element elementB = new Element("Consultar Desprendibles de Pago", "10em", "18em");
        elementB.addEndPoint(new DotEndPoint(EndPointAnchor.TOP));

        Element elementC = new Element("Descargar Carta laboral", "58em", "18em");
        elementC.addEndPoint(new DotEndPoint(EndPointAnchor.TOP));

        model.addElement(elementA);
        model.addElement(elementB);
        model.addElement(elementC);

        model.connect(new Connection(elementA.getEndPoints().get(0), elementB.getEndPoints().get(0)));
        model.connect(new Connection(elementA.getEndPoints().get(0), elementC.getEndPoints().get(0)));
    }

    public DiagramModel getModel() {
        return model;
    }

    public void onSelection() throws IOException, SQLException, JRException {     
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("elementId");
        System.out.println(id);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/ConsultasEmpleados/faces/Consultas/ConsultarDesprendibles.xhtml");
    }
}
