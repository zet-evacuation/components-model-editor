
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.DelayArea;
import de.zet_evakuierung.model.ZControl;
import zet.gui.main.tabs.editor.panel.viewmodels.DelayAreaViewModel;
import zet.gui.main.tabs.editor.panel.viewmodels.DelayAreaViewModelImpl;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class DelayAreaControl extends AbstractInformationPanelControl<JDelayAreaInformationPanel, DelayAreaViewModel> {

    private DelayArea model;

    public DelayAreaControl(ZControl zcontrol) {
        super(new JDelayAreaInformationPanel(), zcontrol);
    }

    public void setModel(DelayArea model) {
        getView().setModel(new DelayAreaViewModelImpl(model));
        this.model = model;
    }

    void setType(DelayArea.DelayType type) {
        control.setDelayType(model, type);
    }

    void setSpeedFactor(double speedFactor) {
        control.setDelaySpeedFactor(model, speedFactor);
    }

    void setDefaultType() {
        double defaultSpeed = model.getDelayType().defaultSpeedFactor;
        control.setDelaySpeedFactor(model, defaultSpeed);
    }
}
