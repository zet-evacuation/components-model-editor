/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.TeleportArea;
import de.zet_evakuierung.model.ZControl;

/**
 * A control class for evacuation areas.
 * @author Jan-Philipp Kappmeier
 */
public class TeleportAreaControl extends AbstractInformationPanelControl<JTeleportAreaInformationPanel, TeleportArea, TeleportAreaChangeEvent> {

    public TeleportAreaControl(ZControl zcontrol) {
        super(new JTeleportAreaInformationPanel(new TeleportAreaViewModelFactory(zcontrol)));
    }

    @Override
    public void changed(TeleportAreaChangeEvent c) {
        switch(c.getChangeType()) {
            case Rename:
                String name = getView().getTeleportAreaName();
                model.setName(name);
                System.out.println("Renaming to " );
                break;
            case TargetArea:
                System.out.println("Setting target area to " );
                TeleportArea targetArea = getView().getTargetArea();
                model.setTargetArea(targetArea);
                break;
            case TargetExit:
                System.out.println("Setting target exit to " );
                EvacuationArea targetExit = getView().getTargetExit();
                model.setExitArea(targetExit);
                break;
            default:
                throw new AssertionError("Teleport event " + c.getChangeType() + " not supported." );
        }
    }
}
