
package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.TeleportArea;
import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.editor.panel.JTeleportAreaInformationPanel;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class TeleportAreaControl extends AbstractInformationControl<JTeleportAreaInformationPanel, TeleportAreaViewModel> {

    private TeleportArea model;

    public TeleportAreaControl(JTeleportAreaInformationPanel view, ZControl control) {
        super(view, control);
    }

    public static TeleportAreaControl create(ZControl control) {
        JTeleportAreaInformationPanel panel = generateView();
        TeleportAreaControl result = new TeleportAreaControl(panel, control);
        panel.setControl(result);
        return result;
    }

    private static JTeleportAreaInformationPanel generateView() {
        return new JTeleportAreaInformationPanel(new TeleportAreaViewModel() {
        });
    }

    public void setModel(TeleportArea model) {
        super.setModel(new TeleportAreaViewModelImpl(model, control.getProject()));
        this.model = model;
    }

    public void rename(String name) {
        model.setName(name);
        System.out.println("Renaming to " + name);
    }

    public void setTargetExit(EvacuationArea targetExit) {
        System.out.println("Setting target exit to " + targetExit);
        model.setExitArea(targetExit);
    }

    public void setTargetArea(TeleportArea targetArea) {
        System.out.println("Setting target area to " + targetArea);
        model.setTargetArea(targetArea);
    }
}
