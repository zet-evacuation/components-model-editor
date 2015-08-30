
package org.zet.components.model.editor.panel;

import de.zet_evakuierung.model.StairArea;
import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.viewmodel.AbstractInformationControl;
import org.zet.components.model.viewmodel.StairAreaControl;
import org.zet.components.model.viewmodel.StairAreaViewModel;
import org.zet.components.model.viewmodel.StairAreaViewModelImpl;

/**
 * Updates the displayed view model in the displayed panel and ensures that the corresponding model in the control
 * is synchrounous.
 * @author Jan-Philipp Kappmeier
 */
public class StairAreaInformationPanelControl extends AbstractInformationControl<JStairAreaInformationPanel,
        StairAreaControl, StairArea, StairAreaViewModel> {
    private final ZControl zcontrol;

    private StairAreaInformationPanelControl(JStairAreaInformationPanel view, StairAreaControl control, ZControl zcontrol) {
        super(view, control);
        this.zcontrol = zcontrol;
    }
    
    public static StairAreaInformationPanelControl create(ZControl zcontrol) {
        StairAreaControl result = new StairAreaControl(zcontrol);
        JStairAreaInformationPanel panel = generateView(result);
        return new StairAreaInformationPanelControl(panel, result, zcontrol);
    }

    private static JStairAreaInformationPanel generateView(StairAreaControl control) {
        return new JStairAreaInformationPanel(new StairAreaViewModel() {
        }, control);
    }

    @Override
    protected StairAreaViewModel getViewModel(StairArea m) {
        return new StairAreaViewModelImpl(m, zcontrol.getProject());
    }
}
