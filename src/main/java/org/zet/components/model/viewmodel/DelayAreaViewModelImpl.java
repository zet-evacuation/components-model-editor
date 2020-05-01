
package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.DelayArea;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class DelayAreaViewModelImpl extends AbstractViewModel<DelayArea> implements DelayAreaViewModel {

    public DelayAreaViewModelImpl(DelayArea model) {
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
