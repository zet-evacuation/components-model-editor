/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.StairArea;
import de.zet_evakuierung.model.StairPreset;
import de.zet_evakuierung.model.ZControl;

/**
 * A control class for evacuation areas.
 * @author Jan-Philipp Kappmeier
 */
public class StairAreaControl extends AbstractInformationPanelControl<JStairAreaInformationPanel, StairArea, StairAreaChangeEvent> {
    private final ZControl control;

    public StairAreaControl(ZControl control) {
        super(new JStairAreaInformationPanel());
        this.control = control;
    }

    @Override
    public void changed(StairAreaChangeEvent c) {
        switch (c.getChangeType()) {
            case DownFactor:
                double downFactor = getView().getFactorDown();
                System.out.println("Setting down factor to " + downFactor);
                model.setSpeedFactorDown(downFactor);
                break;
            case UpFactor:
                double upFactor = getView().getFactorUp();
                System.out.println("Setting up factor to " + upFactor);
                model.setSpeedFactorUp(upFactor);
                break;
            case Preset:
                StairPreset preset = getView().getPreset();
                System.out.println("Loading stair preset " + preset);
                model.setSpeedFactorDown(preset.getSpeedFactorDown());
                model.setSpeedFactorUp(preset.getSpeedFactorUp());
                break;
            default:
                throw new AssertionError(String.format("Event type %s not supported.", c.getChangeType()));
        }
    }
}
