
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.StairArea;
import de.zet_evakuierung.model.StairPreset;
import de.zet_evakuierung.model.ZControl;
import zet.gui.main.tabs.editor.panel.viewmodels.StairAreaViewModel;
import zet.gui.main.tabs.editor.panel.viewmodels.StairAreaViewModelImpl;

/**
 * A control class for evacuation areas.
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
                System.out.println("Setting up factor to " + factorUp);
                model.setSpeedFactorUp(factorUp);
    }

    void setFactorDown(double factorDown) {
                System.out.println("Setting down factor to " + factorDown);
                model.setSpeedFactorDown(factorDown);
    }

    void setPreset(StairPreset preset) {
                System.out.println("Loading stair preset " + preset);
                model.setSpeedFactorDown(preset.getSpeedFactorDown());
                model.setSpeedFactorUp(preset.getSpeedFactorUp());
    }
}
