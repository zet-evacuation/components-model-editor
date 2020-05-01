package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.Project;
import de.zet_evakuierung.model.StairArea;

/**
 * The assignment area view model makes available all information necessary to display an assignment area.
 * @author Jan-Philipp Kappmeier
 */
public class StairAreaViewModelImpl extends AbstractViewModel<StairArea> implements StairAreaViewModel {
    
    public StairAreaViewModelImpl(StairArea a, Project p) {
        super(a);
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
