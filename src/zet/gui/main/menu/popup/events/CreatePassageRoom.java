package zet.gui.main.menu.popup.events;

import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.ZControl;
import zet.gui.main.tabs.editor.panel.EdgeChangeEvent;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class CreatePassageRoom extends EdgeChangeEvent {
    private final PlanEdge edge;
    
    public CreatePassageRoom(Object source, PlanEdge edge) {
        super(source, EdgeChange.CreatePassageRoom);
        this.edge = edge;
    }

    public PlanEdge getEdge() {
        return edge;
    }
    
    @Override
    public void perform(ZControl control) {
    }
}
