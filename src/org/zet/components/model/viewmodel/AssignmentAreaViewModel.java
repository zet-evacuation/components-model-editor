
package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.AssignmentType;
import de.zet_evakuierung.model.EvacuationArea;
import java.util.Collections;
import java.util.List;

/**
 * @author Jan-Philipp Kappmeier
 */
public interface AssignmentAreaViewModel {

    default public List<EvacuationArea> getEvacuationAreas() {
        return Collections.emptyList();
    }

    default public boolean isRastered() {
        return false;
    }

    default public List<AssignmentType> getAssignmentTypes() {
        return Collections.emptyList();
    }
    
    default AssignmentType getAssignmentType() {
        return new AssignmentType(null, null, null, null, null, null, null);
    }
    
    default EvacuationArea getExitArea() {
        return new EvacuationArea(null, 1, "");
    }

    default public int getEvacuees() {
        return 0;
    }

    default public double areaMeter() {
        return 0;
    }

    default public int getMaxEvacuees() {
        return 0;
    }
}
