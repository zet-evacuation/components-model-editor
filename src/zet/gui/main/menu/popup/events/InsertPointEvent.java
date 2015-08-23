/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.menu.popup.events;

import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.PlanPoint;
import de.zet_evakuierung.model.ZControl;
import zet.gui.main.tabs.editor.panel.EdgeChangeEvent;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class InsertPointEvent extends EdgeChangeEvent {
    private final PlanPoint clickPoint;
    private final PlanEdge myEdge;
    
    public InsertPointEvent(Object source, PlanEdge edge, PlanPoint point) {
        super(source, EdgeChange.InsertNewPoint);
        this.clickPoint = point;
        this.myEdge = edge;
    }
    
    public void perform(ZControl control) {
        // Compute a point that is ON the edge (the click is not neccessarily)
        PlanPoint newPoint = myEdge.getPointOnEdge(clickPoint);
        //final double rasterSnap = graphicsStyle.getRasterSizeSnap();
        double rasterSnap = 400;
        boolean rasterizedPaintMode = true;
        if (rasterizedPaintMode) {
            newPoint.x = (int) Math.round((double) newPoint.x / rasterSnap) * (int) rasterSnap;
            newPoint.y = (int) Math.round((double) newPoint.y / rasterSnap) * (int) rasterSnap;
        }
        control.insertPoint(myEdge, newPoint);
    }
}
