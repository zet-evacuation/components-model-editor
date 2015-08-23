package zet.gui.main.menu.popup.events;

import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.RoomEdge;
import de.zet_evakuierung.model.ZControl;
import event.EventServer;
import event.MessageEvent;
import zet.gui.main.tabs.editor.panel.EdgeChangeEvent;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class MakeTeleportEvent extends EdgeChangeEvent {
    private final PlanEdge edge;
    
    public MakeTeleportEvent(Object source, PlanEdge edge) {
        super(source, EdgeChange.CreateFloorPassage);
        this.edge = edge;
    }

    public PlanEdge getEdge() {
        return edge;
    }
    
    
    
    @Override
    public void perform(ZControl control) {
    }
}
