package zet.gui.main.menu.popup.events;

import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.PlanPolygon;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.RoomEdge;
import de.zet_evakuierung.model.ZControl;
import zet.gui.main.tabs.editor.panel.EdgeChangeEvent;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class RevertPassage extends EdgeChangeEvent {
    private final PlanEdge edge;

    public RevertPassage(Object source, PlanEdge edge) {
        super(source, EdgeChange.CreatePassage);
        this.edge = edge;
    }

    @Override
    public void perform(ZControl control) {
        if (edge instanceof RoomEdge) {
            control.disconnectAtEdge((RoomEdge) edge);
        }
    }
}