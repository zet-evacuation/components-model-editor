
package org.zet.components.model.editor.panel;

import de.zet_evakuierung.model.TeleportArea;
import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.viewmodel.AbstractInformationControl;
import org.zet.components.model.viewmodel.TeleportAreaControl;
import org.zet.components.model.viewmodel.TeleportAreaViewModel;
import org.zet.components.model.viewmodel.TeleportAreaViewModelImpl;

/**
 * Updates the displayed view model in the displayed panel and ensures that the corresponding model in the control
 * is synchrounous.
 * @author Jan-Philipp Kappmeier
 */
public class TeleportAreaInformationPanelControl extends AbstractInformationControl<JTeleportAreaInformationPanel,
        TeleportAreaControl, TeleportArea, TeleportAreaViewModel> {
    private final ZControl zcontrol;

    private TeleportAreaInformationPanelControl(JTeleportAreaInformationPanel view, TeleportAreaControl control, ZControl zcontrol) {
        super(view, control);
        this.zcontrol = zcontrol;
    }
    
    public static TeleportAreaInformationPanelControl create(ZControl zcontrol) {
        TeleportAreaControl result = new TeleportAreaControl(zcontrol);
        JTeleportAreaInformationPanel panel = generateView(result);
        return new TeleportAreaInformationPanelControl(panel, result, zcontrol);
    }

    private static JTeleportAreaInformationPanel generateView(TeleportAreaControl control) {
        return new JTeleportAreaInformationPanel(new TeleportAreaViewModel() {
        }, control);
    }

    @Override
    protected TeleportAreaViewModel getViewModel(TeleportArea m) {
        return new TeleportAreaViewModelImpl(m, zcontrol.getProject());
    }
}
