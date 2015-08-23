/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.ZControl;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class EdgeChangeEvent extends AbstractChangeEvent<EdgeChangeEvent.EdgeChange>  {

    public enum EdgeChange {
        InsertNewPoint, CreatePassage, CreatePassageRoom, CreateFloorPassage, CreateEvacuationPassage, ShowPassageTarget,
        RevertPassage, CreateTemplateDoor, CreateTemplateExit
    }

    public EdgeChangeEvent(Object source, EdgeChange change) {
        super(source, change);
    }
    
    public void perform(ZControl control) {
    }

    
}
