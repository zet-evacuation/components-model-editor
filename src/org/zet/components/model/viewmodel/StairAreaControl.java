package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.StairArea;
import de.zet_evakuierung.model.StairPreset;
import de.zet_evakuierung.model.ZControl;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class StairAreaControl extends AbstractControl<StairArea, StairAreaViewModel> {

    public StairAreaControl(ZControl control) {
        super(control);
    }

    public void setFactorUp(double factorUp) {
        model.setSpeedFactorUp(Math.max(0, Math.min(1, factorUp)));
    }

    public void setFactorDown(double factorDown) {
        model.setSpeedFactorDown(Math.max(0, Math.min(1, factorDown)));
    }

    public void setPreset(StairPreset preset) {
        model.setSpeedFactorDown(preset.getSpeedFactorDown());
        model.setSpeedFactorUp(preset.getSpeedFactorUp());
    }
}
