
package org.zet.components.model.editor.panel;

import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.viewmodel.AbstractInformationControl;
import org.zet.components.model.viewmodel.DefaultPanelControl;

/**
 * Updates the displayed view model in the displayed panel and ensures that the corresponding model in the control
 * is synchrounous.
 * @author Jan-Philipp Kappmeier
 */
public class DefaultAreaInformationPanelControl extends AbstractInformationControl<JDefaultInformationPanel,
        DefaultPanelControl, Void, Void> {

    private DefaultAreaInformationPanelControl(JDefaultInformationPanel view, DefaultPanelControl control) {
        super(view, control);
    }
    
    public static DefaultAreaInformationPanelControl create(ZControl zcontrol) {
        DefaultPanelControl result = new DefaultPanelControl(zcontrol);
        JDefaultInformationPanel panel = generateView(result);
        return new DefaultAreaInformationPanelControl(panel, result);
    }

    private static JDefaultInformationPanel generateView(DefaultPanelControl control) {
        return new JDefaultInformationPanel(control);
    }

    @Override
    protected Void getViewModel(Void m) {
        return null;
    }
}
