package zet.gui.main.menu.popup.events;

import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.RoomEdge;
import de.zet_evakuierung.model.ZControl;
import zet.gui.main.tabs.editor.panel.EdgeChangeEvent;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class MakeExitEvent extends EdgeChangeEvent {
    private final PlanEdge edge;

    public MakeExitEvent(Object source, PlanEdge edge) {
        super(source, EdgeChange.CreatePassage);
        this.edge = edge;
    }

    @Override
    public void perform(ZControl control) {
        if (edge instanceof RoomEdge) {
            control.getProject().getBuildingPlan().getDefaultFloor().addEvacuationRoom((RoomEdge) edge);
        } else {
            //EventServer.getInstance().dispatchEvent(new MessageEvent(this, MessageEvent.MessageType.Error, "Nur Raumbegrenzungen können zu Evakuierungsausgängen gemacht werden!"));
        }
    }
}