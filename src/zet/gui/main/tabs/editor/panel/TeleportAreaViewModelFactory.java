/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.TeleportArea;
import de.zet_evakuierung.model.ZControl;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TeleportAreaViewModelFactory {
    private final ZControl zcontrol;

    TeleportAreaViewModelFactory(ZControl zcontrol) {
        this.zcontrol = zcontrol;
    }
    
    public TeleportAreaViewModel getViewModel(TeleportArea a) {
        return new TeleportAreaViewModel(a, zcontrol);
    }
}
