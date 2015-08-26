package zet.gui.main.tabs.editor.panel.viewmodels;

import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.Project;

/**
 * The assignment area view model makes available all information necessary to display an assignment area.
 * @author Jan-Philipp Kappmeier
 */
public class EvacuationAreaViewModelImpl extends AbstractViewModel<EvacuationArea> implements EvacuationAreaViewModel {
    private final Project project;
    
    public EvacuationAreaViewModelImpl(EvacuationArea a, Project p) {
        super(a);
        this.project = p;
    }

    @Override
    public int getAttractivity() {
        return model.getAttractivity();
    }

    @Override
    public String getName() {
        return model.getName();
    }
}
