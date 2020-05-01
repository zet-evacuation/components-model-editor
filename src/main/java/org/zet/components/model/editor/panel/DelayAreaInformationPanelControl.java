
package org.zet.components.model.editor.panel;

import de.zet_evakuierung.model.DelayArea;
import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.viewmodel.AbstractInformationControl;
import org.zet.components.model.viewmodel.DelayAreaControl;
import org.zet.components.model.viewmodel.DelayAreaViewModel;
import org.zet.components.model.viewmodel.DelayAreaViewModelImpl;

/**
 * Updates the displayed view model in the displayed panel and ensures that the corresponding model in the control
 * is synchrounous.
 * @author Jan-Philipp Kappmeier
 */
public class DelayAreaInformationPanelControl extends AbstractInformationControl<JDelayAreaInformationPanel,
        DelayAreaControl, DelayArea, DelayAreaViewModel> {
    private final ZControl zcontrol;

    private DelayAreaInformationPanelControl(JDelayAreaInformationPanel view, DelayAreaControl control, ZControl zcontrol) {
        super(view, control);
        this.zcontrol = zcontrol;
    }
    
    public static DelayAreaInformationPanelControl create(ZControl zcontrol) {
        DelayAreaControl result = new DelayAreaControl(zcontrol);
        JDelayAreaInformationPanel panel = generateView(result);
        return new DelayAreaInformationPanelControl(panel, result, zcontrol);
    }

    private static JDelayAreaInformationPanel generateView(DelayAreaControl control) {
        return new JDelayAreaInformationPanel(new DelayAreaViewModel() {
        }, control);
    }

    @Override
    protected DelayAreaViewModel getViewModel(DelayArea m) {
        return new DelayAreaViewModelImpl(m);
    }
}
