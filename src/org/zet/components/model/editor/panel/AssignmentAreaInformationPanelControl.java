
package org.zet.components.model.editor.panel;

import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.viewmodel.AbstractInformationControl;
import org.zet.components.model.viewmodel.AssignmentAreaControl;
import org.zet.components.model.viewmodel.AssignmentAreaViewModel;
import org.zet.components.model.viewmodel.AssignmentAreaViewModelImpl;

/**
 * Updates the displayed view model in the displayed panel and ensures that the corresponding model in the control
 * is synchrounous.
 * @author Jan-Philipp Kappmeier
 */
public class AssignmentAreaInformationPanelControl extends AbstractInformationControl<JAssignmentAreaInformationPanel,
        AssignmentAreaControl, AssignmentArea, AssignmentAreaViewModel> {
    private final ZControl zcontrol;

    private AssignmentAreaInformationPanelControl(JAssignmentAreaInformationPanel view, AssignmentAreaControl control, ZControl zcontrol) {
        super(view, control);
        this.zcontrol = zcontrol;
    }
    
    public static AssignmentAreaInformationPanelControl create(ZControl zcontrol) {
        AssignmentAreaControl result = new AssignmentAreaControl(zcontrol);
        JAssignmentAreaInformationPanel panel = generateView(result);
        return new AssignmentAreaInformationPanelControl(panel, result, zcontrol);
    }

    private static JAssignmentAreaInformationPanel generateView(AssignmentAreaControl control) {
        return new JAssignmentAreaInformationPanel(new AssignmentAreaViewModel() {
        }, control);
    }

    @Override
    protected AssignmentAreaViewModel getViewModel(AssignmentArea m) {
        return new AssignmentAreaViewModelImpl(m, zcontrol.getProject());
    }
}
