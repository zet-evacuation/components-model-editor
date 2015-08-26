package zet.gui.main.tabs.editor.panel.viewmodels;

import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.AssignmentType;
import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.Project;
import java.util.List;

/**
 * The assignment area view model makes available all information necessary to display an assignment area.
 * @author Jan-Philipp Kappmeier
 */
public class AssignmentAreaViewModelImpl extends AbstractViewModel<AssignmentArea> implements AssignmentAreaViewModel {
    private final Project project;
    
    public AssignmentAreaViewModelImpl(AssignmentArea a, Project p) {
        super(a);
        this.project = p;
    }

    @Override
    public AssignmentType getAssignmentType() {
        return model.getAssignmentType();
    }

    @Override
    public int getMaxEvacuees() {
        return model.getMaxEvacuees();
    }

    @Override
    public double areaMeter() {
        return model.areaMeter();
    }

    @Override
    public int getEvacuees() {
        return model.getEvacuees();
    }

    @Override
    public EvacuationArea getExitArea() {
        return model.getExitArea();
    }

     @Override
    public List<AssignmentType> getAssignmentTypes() {
        return project.getCurrentAssignment().getAssignmentTypes();
    }

    @Override
    public boolean isRastered() {
        return project.getBuildingPlan().isRastered();
    }

    @Override
    public List<EvacuationArea> getEvacuationAreas() {
        return project.getBuildingPlan().getEvacuationAreas();
    }
}
