
package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.DelayArea;

/**
 * @author Jan-Philipp Kappmeier
 */
public interface DelayAreaViewModel {

    default public double getSpeedFactor() {
        return 1;
    }

    default public DelayArea.DelayType getDelayType() {
        return DelayArea.DelayType.OTHER;
    }

}
