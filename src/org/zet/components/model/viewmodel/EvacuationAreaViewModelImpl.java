package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.Project;

/**
 * The assignment area view model makes available all information necessary to display an assignment area.
 * @author Jan-Philipp Kappmeier
 */
class EvacuationAreaViewModelImpl extends AbstractViewModel<EvacuationArea> implements EvacuationAreaViewModel {
    EvacuationAreaViewModelImpl(EvacuationArea a, Project p) {
        super(a);
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
