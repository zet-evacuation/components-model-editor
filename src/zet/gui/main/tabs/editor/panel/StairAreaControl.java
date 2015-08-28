package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.StairArea;
import de.zet_evakuierung.model.StairPreset;
import de.zet_evakuierung.model.ZControl;
import zet.gui.main.tabs.editor.panel.viewmodels.StairAreaViewModel;
import zet.gui.main.tabs.editor.panel.viewmodels.StairAreaViewModelImpl;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class StairAreaControl extends AbstractInformationPanelControl<JStairAreaInformationPanel, StairAreaViewModel> {

    private StairArea model;

    public StairAreaControl(ZControl control) {
        super(generateView(), control);
        getView().setControl(this);
    }

    private static JStairAreaInformationPanel generateView() {
        return new JStairAreaInformationPanel(new StairAreaViewModel() {
        });
    }

    public void setModel(StairArea model) {
        getView().setModel(new StairAreaViewModelImpl(model, control.getProject()));
        this.model = model;
    }

    void setFactorUp(double factorUp) {
        model.setSpeedFactorUp(Math.max(0, Math.min(1, factorUp)));
    }

    void setFactorDown(double factorDown) {
        model.setSpeedFactorDown(Math.max(0, Math.min(1, factorDown)));
    }

    void setPreset(StairPreset preset) {
        model.setSpeedFactorDown(preset.getSpeedFactorDown());
        model.setSpeedFactorUp(preset.getSpeedFactorUp());
    }
}
