package zet.gui.main.tabs.editor.panel.viewmodels;

import de.zet_evakuierung.model.Project;
import de.zet_evakuierung.model.StairArea;

/**
 * The assignment area view model makes available all information necessary to display an assignment area.
 * @author Jan-Philipp Kappmeier
 */
public class StairAreaViewModelImpl extends AbstractViewModel<StairArea> implements StairAreaViewModel {
    private final Project project;
    
    public StairAreaViewModelImpl(StairArea a, Project p) {
        super(a);
        this.project = p;
    }

    @Override
    public double getSpeedFactorDown() {
        return model.getSpeedFactorDown();
    }

    @Override
    public double getSpeedFactorUp() {
        return model.getSpeedFactorUp();
    }
}
