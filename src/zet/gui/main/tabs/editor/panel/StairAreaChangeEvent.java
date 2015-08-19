package zet.gui.main.tabs.editor.panel;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class StairAreaChangeEvent extends AbstractChangeEvent<StairAreaChangeEvent.StairAreaChange> {

    public enum StairAreaChange {
        UpFactor, DownFactor, Preset
    }
    
    public StairAreaChangeEvent(Object source, StairAreaChange change) {
        super(source, change);
    }
}
