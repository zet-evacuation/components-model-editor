package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.ZControl;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class EvacuationAreaControl extends AbstractControl<EvacuationArea, EvacuationAreaViewModel> {
    private EvacuationArea model;

    public EvacuationAreaControl(ZControl control) {
        super(control);
    }

    public void setName(String name) {
        model.setName(name);
        //((EvacuationArea) getLeftPanel().getMainComponent().getSelectedPolygons().get(0).getPlanPolygon()).setName(txtEvacuationAreaName.getText());
    }

    public void setAttractivity(int attractivity) {
        EvacuationArea ea = model;
        model.setAttractivity(attractivity);
    }
}
