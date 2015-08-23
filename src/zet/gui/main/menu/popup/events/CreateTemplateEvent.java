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
public class CreateTemplateEvent extends EdgeChangeEvent {
    private final PlanPoint clickPoint;
    private final PlanEdge myEdge;
    
    public CreateTemplateEvent(Object source, EdgeChange type, PlanEdge edge, PlanPoint point) {
        super(source, EdgeChange.InsertNewPoint);
        this.clickPoint = point;
        this.myEdge = edge;
    }
    
    public void perform(ZControl control) {

    }
}
