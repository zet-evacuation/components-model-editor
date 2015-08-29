package org.zet.components.model.editor.floor;

import de.zet_evakuierung.model.PlanEdge;
import java.util.EventObject;
import org.zet.components.model.editor.polygon.JPolygon;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class SelectionEvent extends EventObject {
    private final JPolygon selectedPolygon;
    private final PlanEdge edge;
    public SelectionEvent(Object source) {
        super(source);
        selectedPolygon = null;
        edge = null;
    }

    public SelectionEvent(Object source, JPolygon polygon) {
        super(source);
        this.selectedPolygon = polygon;
        edge = null;
    }

    public SelectionEvent(Object source, JPolygon polygon, PlanEdge edge) {
        super(source);
        this.selectedPolygon = polygon;
        this.edge = edge;
    }

    public JPolygon getSelectedPolygon() {
        return selectedPolygon;
    }

    public PlanEdge getEdge() {
        return edge;
    }
}
