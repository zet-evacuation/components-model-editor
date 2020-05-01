
package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.TeleportArea;
import de.zet_evakuierung.model.ZControl;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class TeleportAreaControl extends AbstractControl<TeleportArea, TeleportAreaViewModel> {

    public TeleportAreaControl(ZControl control) {
        super(control);
    }

    public void rename(String name) {
        model.setName(name);
    }

    public void setTargetExit(EvacuationArea targetExit) {
        model.setExitArea(targetExit);
    }

    public void setTargetArea(TeleportArea targetArea) {
        model.setTargetArea(targetArea);
    }
}
