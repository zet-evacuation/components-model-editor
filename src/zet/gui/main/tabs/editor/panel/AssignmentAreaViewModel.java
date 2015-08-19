/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.AssignmentType;
import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.ZControl;
import java.util.List;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class AssignmentAreaViewModel {
    private final AssignmentArea assignmentArea;
    private final ZControl zcontrol;
    
    AssignmentAreaViewModel(AssignmentArea a, ZControl zcontrol) {
        this.assignmentArea = a;
        this.zcontrol = zcontrol;
    }

    List<AssignmentType> getAssignmentTypes() {
        return zcontrol.getProject().getCurrentAssignment().getAssignmentTypes();
    }

    boolean isRastered() {
        return zcontrol.getProject().getBuildingPlan().isRastered();
    }

    List<EvacuationArea> getEvacuationAreas() {
        return zcontrol.getProject().getBuildingPlan().getEvacuationAreas();
    }
}
