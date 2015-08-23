/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.menu.popup.events;

import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.PlanPoint;
import de.zet_evakuierung.model.RoomEdge;
import de.zet_evakuierung.model.ZControl;
import de.zet_evakuierung.template.ExitDoor;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class CreateTemplateExitEvent extends CreateTemplateEvent {
    private final PlanPoint clickPoint;
    private final PlanEdge myEdge;
    private final ExitDoor exit;
    
    public CreateTemplateExitEvent(Object source, PlanEdge edge, PlanPoint point, ExitDoor exit) {
        super(source, EdgeChange.CreateTemplateExit, edge,  point);
        this.clickPoint = point;
        this.myEdge = edge;
        this.exit = exit;
    }
    
    public void perform(ZControl control) {
        if( myEdge instanceof RoomEdge) {
            PlanPoint newPoint = myEdge.getPointOnEdge(clickPoint);
            final double rasterSnap = 400;
            boolean rasterizedPaintMode = true;
            if (rasterizedPaintMode) {
                newPoint.x = (int) Math.round((double) newPoint.x / rasterSnap) * (int) rasterSnap;
                newPoint.y = (int) Math.round((double) newPoint.y / rasterSnap) * (int) rasterSnap;
            }
            control.createExitDoor((RoomEdge) myEdge, newPoint, exit.getSize());
        }
    }
}
