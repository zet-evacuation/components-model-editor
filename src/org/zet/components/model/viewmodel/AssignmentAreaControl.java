package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.AssignmentType;
import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.editor.panel.JAssignmentAreaInformationPanel;

/**
 * A control class for evacuation areas.
 * @author Jan-Philipp Kappmeier
 */
public class AssignmentAreaControl extends AbstractInformationControl<JAssignmentAreaInformationPanel, AssignmentAreaViewModel> {
    private AssignmentArea model;

    private AssignmentAreaControl(JAssignmentAreaInformationPanel view, ZControl control) {
        super(view, control);
    }
    
    public static AssignmentAreaControl create(ZControl control) {
        JAssignmentAreaInformationPanel panel = generateView();
        AssignmentAreaControl result = new AssignmentAreaControl(panel, control);
        panel.setControl(result);
        return result;
    }

    private static JAssignmentAreaInformationPanel generateView() {
        return new JAssignmentAreaInformationPanel(new AssignmentAreaViewModel() {
        });
    }
    
    public void setModel(AssignmentArea model) {
        AssignmentAreaViewModel vm = new AssignmentAreaViewModelImpl(model, control.getProject());
        setModel(vm);
        getView().setModel(vm);
        this.model = model;
    }

    public void setAssignmentType(AssignmentType assignmentType) {
        model.setAssignmentType(assignmentType);
        System.err.println("We should change the assignment type!");
   }

    public void setStandardEvacuees() {
        int standardEvacuees = Math.min(viewModel.getAssignmentType().getDefaultEvacuees(), viewModel.getMaxEvacuees());
        System.out.println("Setting standard number of evacuees of " + standardEvacuees);
        model.setEvacuees(standardEvacuees);
    }
    
    /**
     * Sets the amount of evacuues if possible. To large amounts are set to maximum value and negative to 0.
     * @param evacuees 
     */
    public void setEvacuees(int evacuees) {
        final int actualEvacuees = Math.max( 0, Math.min(viewModel.getMaxEvacuees(), evacuees));
        System.out.println("Changing evacuees to " + actualEvacuees);
        model.setEvacuees(actualEvacuees);
    }

    public void setPreferredExit(EvacuationArea evacuationArea) {
        if (evacuationArea != null) {
            System.out.println("Set preferred exit to " + evacuationArea);
            model.setExitArea(evacuationArea);
        }
    }
}
