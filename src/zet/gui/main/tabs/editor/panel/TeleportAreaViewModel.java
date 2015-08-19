/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.Floor;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.TeleportArea;
import de.zet_evakuierung.model.ZControl;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TeleportAreaViewModel {
    private final TeleportArea teleportArea;
    private final ZControl zcontrol;
    
    TeleportAreaViewModel(TeleportArea a, ZControl zcontrol) {
        this.teleportArea = a;
        this.zcontrol = zcontrol;
    }

    List<EvacuationArea> getEvacuationAreas() {
        return zcontrol.getProject().getBuildingPlan().getEvacuationAreas();
    }
    List<TeleportArea> getTeleportAreas() {
        LinkedList<TeleportArea> teleportAreas = new LinkedList<>();
        for (Floor f : zcontrol.getProject().getBuildingPlan() ) {
            for (Room r : f) {
                teleportAreas.addAll(r.getTeleportAreas());
            }
        }
        return teleportAreas;
    }
}
