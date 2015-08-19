package zet.gui.main.tabs.editor.panel;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TeleportAreaChangeEvent extends AbstractChangeEvent<TeleportAreaChangeEvent.TeleportAreaChange> {

    public enum TeleportAreaChange {
        Rename, TargetArea, TargetExit
    }
    
    public TeleportAreaChangeEvent(Object source, TeleportAreaChange change) {
        super(source, change);
    }
}
