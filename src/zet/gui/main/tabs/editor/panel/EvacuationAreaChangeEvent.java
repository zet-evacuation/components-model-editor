package zet.gui.main.tabs.editor.panel;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class EvacuationAreaChangeEvent extends AbstractChangeEvent<EvacuationAreaChangeEvent.EvacuationAreaChange> {

    public enum EvacuationAreaChange {
        Name, Attractivity
    }
    
    public EvacuationAreaChangeEvent(Object source, EvacuationAreaChange change) {
        super(source, change);
    }
}
