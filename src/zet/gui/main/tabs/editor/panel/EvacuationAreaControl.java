/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.EvacuationArea;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class EvacuationAreaControl extends AbstractInformationPanelControl<JEvacuationAreaInformationPanel, EvacuationArea, EvacuationAreaChangeEvent> {

    public EvacuationAreaControl() {
        super(new JEvacuationAreaInformationPanel());
    }

    @Override
    public void changed(EvacuationAreaChangeEvent c) {
        switch (c.getChangeType()) {
            case Attractivity:
                int attractivity = getView().getAttractivity();
                System.err.println("TODO: change attractivity of evacuation area to " + attractivity);
                EvacuationArea ea = model;
                // control.setAttractivity(attractivity)
                break;
            case Name:
                String name = getView().getEvacuationName();
                System.err.println("Setting evacuation area name to '" + name + "'.");
                model.setName(name);
//                ((EvacuationArea) getLeftPanel().getMainComponent().getSelectedPolygons().get(0).getPlanPolygon()).setName(txtEvacuationAreaName.getText());

                break;
            default:
                throw new IllegalArgumentException("Unsupported evacuation area change event: " + c);
        }

    }
}
