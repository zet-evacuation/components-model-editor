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
public class MakePassableEvent extends EdgeChangeEvent {
    private final PlanEdge edge;

    public MakePassableEvent(Object source, PlanEdge edge) {
        super(source, EdgeChange.CreatePassage);
        this.edge = edge;
    }

    @Override
    public void perform(ZControl control) {
        if (edge instanceof RoomEdge) {
            Room room = ((RoomEdge) edge).getRoom();
            RoomEdge partner = null;

            for (Room r : room.getAssociatedFloor().getRooms()) {
                if (r != room) {
                    try {
                        PlanPolygon<RoomEdge> p = (PlanPolygon<RoomEdge>) r.getPolygon();
                        partner = p.getEdge((RoomEdge) edge);
                        //partner = r.getPolygon().getEdge( (RoomEdge)myEdge );
                        break; // Break when successful
                    } catch (IllegalArgumentException ex) {
                    }
                }
            }
            if (partner != null) {
                ((RoomEdge) edge).setLinkTarget(partner);
                partner.setLinkTarget((RoomEdge) edge);
            } else {
                //EventServer.getInstance().dispatchEvent(new MessageEvent(this, MessageEvent.MessageType.Error, "Erzeugen Sie zuerst 2 übereinanderliegende Raumbegrenzungen!"));
            }
        } else {
            //EventServer.getInstance().dispatchEvent(new MessageEvent(this, MessageEvent.MessageType.Error, "Nur Raumbegrenzungen können passierbar gemacht werden!"));
        }
    }
}