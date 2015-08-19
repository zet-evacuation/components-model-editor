/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.ZControl;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class AssignmentAreaViewModelFactory {
    private final ZControl zcontrol;

    AssignmentAreaViewModelFactory(ZControl zcontrol) {
        this.zcontrol = zcontrol;
    }
    
    public AssignmentAreaViewModel getViewModel(AssignmentArea a) {
        return new AssignmentAreaViewModel(a, zcontrol);
    }
}
