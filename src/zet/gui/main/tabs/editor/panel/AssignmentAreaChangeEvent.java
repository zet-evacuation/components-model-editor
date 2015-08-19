package zet.gui.main.tabs.editor.panel;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class AssignmentAreaChangeEvent extends AbstractChangeEvent<AssignmentAreaChangeEvent.AssignmentAreaChange> {

    public enum AssignmentAreaChange {
        AssignmentType, Evacuees, StandardEvacuees, PreferredExit
    }
    
    public AssignmentAreaChangeEvent(Object source, AssignmentAreaChange change) {
        super(source, change);
    }
}
