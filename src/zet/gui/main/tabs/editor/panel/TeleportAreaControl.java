
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.TeleportArea;
import de.zet_evakuierung.model.ZControl;
import zet.gui.main.tabs.editor.panel.viewmodels.TeleportAreaViewModel;
import zet.gui.main.tabs.editor.panel.viewmodels.TeleportAreaViewModelImpl;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class TeleportAreaControl extends AbstractInformationPanelControl<JTeleportAreaInformationPanel, TeleportAreaViewModel> {

    private TeleportArea model;

    public TeleportAreaControl(ZControl control) {
        super(generateView(), control);
        getView().setControl(this);
    }

    private static JTeleportAreaInformationPanel generateView() {
        return new JTeleportAreaInformationPanel(new TeleportAreaViewModel() {
        });
    }

    public void setModel(TeleportArea model) {
        super.setModel(new TeleportAreaViewModelImpl(model, control.getProject()));
        this.model = model;
    }

    void rename(String name) {
        model.setName(name);
        System.out.println("Renaming to " + name);
    }

    void setTargetExit(EvacuationArea targetExit) {
        System.out.println("Setting target exit to " + targetExit);
        model.setExitArea(targetExit);
    }

    void setTargetArea(TeleportArea targetArea) {
        System.out.println("Setting target area to " + targetArea);
        model.setTargetArea(targetArea);
    }
}
