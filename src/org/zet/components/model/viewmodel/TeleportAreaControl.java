
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

    private TeleportArea model;

    public TeleportAreaControl(ZControl control) {
        super(control);
    }

    public void rename(String name) {
        model.setName(name);
        System.out.println("Renaming to " + name);
    }

    public void setTargetExit(EvacuationArea targetExit) {
        System.out.println("Setting target exit to " + targetExit);
        model.setExitArea(targetExit);
    }

    public void setTargetArea(TeleportArea targetArea) {
        System.out.println("Setting target area to " + targetArea);
        model.setTargetArea(targetArea);
    }
}
