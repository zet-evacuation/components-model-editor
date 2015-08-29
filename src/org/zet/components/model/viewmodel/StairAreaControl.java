package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.StairArea;
import de.zet_evakuierung.model.StairPreset;
import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.editor.panel.JStairAreaInformationPanel;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class StairAreaControl extends AbstractInformationControl<JStairAreaInformationPanel, StairAreaViewModel> {

    private StairArea model;

    public StairAreaControl(JStairAreaInformationPanel view, ZControl control) {
        super(view, control);
    }

    public static StairAreaControl create(ZControl control) {
        JStairAreaInformationPanel panel = generateView();
        StairAreaControl result = new StairAreaControl(panel, control);
        panel.setControl(result);
        return result;
    }

    private static JStairAreaInformationPanel generateView() {
        return new JStairAreaInformationPanel(new StairAreaViewModel() {
        });
    }

    public void setModel(StairArea model) {
        getView().setModel(new StairAreaViewModelImpl(model, control.getProject()));
        this.model = model;
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
