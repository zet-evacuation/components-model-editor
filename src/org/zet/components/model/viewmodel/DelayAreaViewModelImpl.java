
package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.DelayArea;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
class DelayAreaViewModelImpl extends AbstractViewModel<DelayArea> implements DelayAreaViewModel {

    DelayAreaViewModelImpl(DelayArea model) {
        super(model);
    }

    @Override
    public DelayArea.DelayType getDelayType() {
        return model.getDelayType();
    }

    @Override
    public double getSpeedFactor() {
        return model.getSpeedFactor();
    }
    
    
}
