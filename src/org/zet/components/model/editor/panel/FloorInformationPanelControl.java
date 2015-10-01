
package org.zet.components.model.editor.panel;

import de.zet_evakuierung.model.Floor;
import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.editor.floor.FloorControl;
import org.zet.components.model.editor.floor.FloorViewModel;
import org.zet.components.model.viewmodel.AbstractInformationControl;

/**
 * Updates the displayed view model in the displayed panel and ensures that the corresponding model in the control
 * is synchrounous.
 * @author Jan-Philipp Kappmeier
 */
public class FloorInformationPanelControl extends AbstractInformationControl<JFloorInformationPanel,
        FloorControl, Floor, FloorViewModel> {
    private final ZControl zcontrol;

    private FloorInformationPanelControl(JFloorInformationPanel view, FloorControl control, ZControl zcontrol) {
        super(view, control);
        this.zcontrol = zcontrol;
    }
    
    public static FloorInformationPanelControl create(ZControl zcontrol, FloorControl fc) {
        //FloorControl result = new FloorControl(zcontrol);
        JFloorInformationPanel panel = generateView(fc);
        return new FloorInformationPanelControl(panel, fc, zcontrol);
    }

    private static JFloorInformationPanel generateView(FloorControl control) {
        return new JFloorInformationPanel(control);
    }

    @Override
    protected FloorViewModel getViewModel(Floor m) {
        return new FloorViewModel(m, zcontrol.getProject().getBuildingPlan());
    }
}
