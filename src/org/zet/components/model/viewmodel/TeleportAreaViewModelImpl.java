package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.Floor;
import de.zet_evakuierung.model.Project;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.TeleportArea;
import java.util.LinkedList;
import java.util.List;

/**
 * The assignment area view model makes available all information necessary to display an assignment area.
 *
 * @author Jan-Philipp Kappmeier
 */
public class TeleportAreaViewModelImpl extends AbstractViewModel<TeleportArea> implements TeleportAreaViewModel {

    private final Project project;

    public TeleportAreaViewModelImpl(TeleportArea a, Project p) {
        super(a);
        this.project = p;
    }

    @Override
    public List<EvacuationArea> getEvacuationAreas() {
        return project.getBuildingPlan().getEvacuationAreas();
    }

    @Override
    public List<TeleportArea> getTeleportAreas() {
        LinkedList<TeleportArea> teleportAreas = new LinkedList<>();
        for (Floor f : project.getBuildingPlan()) {
            for (Room r : f) {
                teleportAreas.addAll(r.getTeleportAreas());
            }
        }
        return teleportAreas;
    }

    @Override
    public String getName() {
        return model.getName();
    }

    @Override
    public TeleportArea getTargetArea() {
        return model.getTargetArea();
    }

    @Override
    public EvacuationArea getExitArea() {
        return model.getExitArea();
    }
    
}
