/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.floor;

import de.zet_evakuierung.model.PlanEdge;
import java.util.EventObject;
import zet.gui.main.tabs.base.JPolygon;

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
