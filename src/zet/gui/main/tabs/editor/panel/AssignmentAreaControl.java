/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.AssignmentType;
import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.ZControl;

/**
 * A control class for evacuation areas.
 * @author Jan-Philipp Kappmeier
 */
public class AssignmentAreaControl extends AbstractInformationPanelControl<JAssignmentAreaInformationPanel, AssignmentArea, AssignmentAreaChangeEvent> {

    public AssignmentAreaControl(ZControl zcontrol) {
        super(new JAssignmentAreaInformationPanel(new AssignmentAreaViewModelFactory(zcontrol)));
    }

    @Override
    public void changed(AssignmentAreaChangeEvent c) {
        switch( c.getChangeType() ) {
            case StandardEvacuees:
                int standardEvacuees = Math.min(model.getAssignmentType().getDefaultEvacuees(), model.getMaxEvacuees());
                System.out.println("Setting standard number of evacuees of " + standardEvacuees);
                model.setEvacuees(standardEvacuees);
                break;
            case Evacuees:
                final int evacuees = Math.min(model.getMaxEvacuees(), getView().getNumberOfPersons());
                System.out.println("Changing evacuees to " + evacuees);
                model.setEvacuees(evacuees);
                break;
            case AssignmentType:
                AssignmentType newAssignmentType = getView().getAssignmentType();
                model.setAssignmentType(newAssignmentType);
                System.err.println("We should change the assignment type!");
                break;
            case PreferredExit:
                EvacuationArea preferredExit = getView().getPreferredExit();
                if( preferredExit != null ) {
                    System.out.println("Set preferred exit to " + preferredExit);
                    model.setExitArea(preferredExit);
                }
                break;
            default:
                throw new AssertionError(String.format("Event of type %s not implemented", c.getChangeType()));
        }
    }
}
