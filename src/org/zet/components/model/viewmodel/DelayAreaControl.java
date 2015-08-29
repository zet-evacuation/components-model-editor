
package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.DelayArea;
import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.editor.panel.JDelayAreaInformationPanel;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class DelayAreaControl extends AbstractInformationControl<JDelayAreaInformationPanel, DelayAreaViewModel> {

    private DelayArea model;

    private DelayAreaControl(JDelayAreaInformationPanel view, ZControl zcontrol) {
        super(view, zcontrol);
    }

    public static DelayAreaControl create(ZControl control) {
        JDelayAreaInformationPanel panel = generateView();
        DelayAreaControl result = new DelayAreaControl(panel, control);
        panel.setControl(result);
        return result;
    }

    private static JDelayAreaInformationPanel generateView() {
        return new JDelayAreaInformationPanel(new DelayAreaViewModel() {
        });
    }

    public void setModel(DelayArea model) {
        DelayAreaViewModel vm = new DelayAreaViewModelImpl(model);
        setModel(vm);
        getView().setModel(vm);
        this.model = model;
    }

    public void setType(DelayArea.DelayType type) {
        control.setDelayType(model, type);
    }

    public void setSpeedFactor(double speedFactor) {
        control.setDelaySpeedFactor(model, Math.max( 0, Math.min( 1, speedFactor)) );
    }

    public void setDefaultType() {
        double defaultSpeed = model.getDelayType().defaultSpeedFactor;
        control.setDelaySpeedFactor(model, defaultSpeed);
    }
}
