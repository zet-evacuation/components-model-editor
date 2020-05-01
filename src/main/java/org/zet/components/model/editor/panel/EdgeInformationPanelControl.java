
package org.zet.components.model.editor.panel;

import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.viewmodel.AbstractInformationControl;
import org.zet.components.model.viewmodel.EdgeControl;
import org.zet.components.model.viewmodel.EdgeViewModel;
import org.zet.components.model.viewmodel.EdgeViewModelImpl;

/**
 * Updates the displayed view model in the displayed panel and ensures that the corresponding model in the control
 * is synchrounous.
 * @author Jan-Philipp Kappmeier
 */
public class EdgeInformationPanelControl extends AbstractInformationControl<JEdgeInformationPanel,
        EdgeControl, PlanEdge, EdgeViewModel> {
    private final ZControl zcontrol;

    private EdgeInformationPanelControl(JEdgeInformationPanel view, EdgeControl control, ZControl zcontrol) {
        super(view, control);
        this.zcontrol = zcontrol;
    }
    
    public static EdgeInformationPanelControl create(ZControl zcontrol) {
        EdgeControl result = new EdgeControl(zcontrol);
        JEdgeInformationPanel panel = generateView(result);
        return new EdgeInformationPanelControl(panel, result, zcontrol);
    }

    private static JEdgeInformationPanel generateView(EdgeControl control) {
        return new JEdgeInformationPanel(new EdgeViewModel() {
        }, control);
    }

    @Override
    protected EdgeViewModel getViewModel(PlanEdge m) {
        return new EdgeViewModelImpl(m, zcontrol.getProject());
    }
}
