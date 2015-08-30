package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.InaccessibleArea;
import de.zet_evakuierung.model.ZControl;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class InaccessibleAreaControl extends AbstractControl<InaccessibleArea, Void> {

    public InaccessibleAreaControl(ZControl control) {
        super(control);
    }
}
