
package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.DelayArea;
import de.zet_evakuierung.model.ZControl;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class DelayAreaControl extends AbstractControl<DelayArea, DelayAreaViewModel> {

    public DelayAreaControl(ZControl zcontrol) {
        super(zcontrol);
    }

    public void setType(DelayArea.DelayType type) {
        zcontrol.setDelayType(model, type);
    }

    public void setSpeedFactor(double speedFactor) {
        zcontrol.setDelaySpeedFactor(model, Math.max( 0, Math.min( 1, speedFactor)) );
    }

    public void setDefaultType() {
        double defaultSpeed = model.getDelayType().defaultSpeedFactor;
        zcontrol.setDelaySpeedFactor(model, defaultSpeed);
    }
}
