package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.AssignmentType;
import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.ZControl;
import zet.gui.main.tabs.editor.panel.viewmodels.AssignmentAreaViewModelImpl;
import zet.gui.main.tabs.editor.panel.viewmodels.AssignmentAreaViewModel;

/**
 * A control class for evacuation areas.
 * @author Jan-Philipp Kappmeier
 */
public class AssignmentAreaControl extends AbstractInformationPanelControl<JAssignmentAreaInformationPanel, AssignmentAreaViewModel> {
    private AssignmentArea model;

    public AssignmentAreaControl(ZControl control) {
        super(generateView(), control);
        getView().setControl(this);
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

    void setAssignmentType(AssignmentType assignmentType) {
        model.setAssignmentType(assignmentType);
        System.err.println("We should change the assignment type!");
   }

    void setStandardEvacuees() {
        int standardEvacuees = Math.min(viewModel.getAssignmentType().getDefaultEvacuees(), viewModel.getMaxEvacuees());
        System.out.println("Setting standard number of evacuees of " + standardEvacuees);
        model.setEvacuees(standardEvacuees);
    }
    
    void setEvacuees(int evacuees) {
        final int actualEvacuees = Math.min(viewModel.getMaxEvacuees(), evacuees);
        System.out.println("Changing evacuees to " + actualEvacuees);
        model.setEvacuees(actualEvacuees);
    }

    void setPreferredExit(EvacuationArea evacuationArea) {
        if (evacuationArea != null) {
            System.out.println("Set preferred exit to " + evacuationArea);
            model.setExitArea(evacuationArea);
        }
    }
}
