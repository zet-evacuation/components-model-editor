/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.PlanEdge;

/**
 * A control class for evacuation areas.
 * @author Jan-Philipp Kappmeier
 */
public class EdgeControl extends AbstractInformationPanelControl<JEdgeInformationPanel, PlanEdge, EdgeChangeEvent> {
    public EdgeControl() {
        super(new JEdgeInformationPanel());
    }

    @Override
    public void changed(EdgeChangeEvent c) {
        
    }
}
