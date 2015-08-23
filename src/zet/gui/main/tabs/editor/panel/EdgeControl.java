/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.RoomEdge;
import de.zet_evakuierung.model.ZControl;
import zet.gui.main.menu.popup.events.CreatePassageRoom;
import zet.gui.main.menu.popup.events.MakeTeleportEvent;

/**
 * A control class for evacuation areas.
 * @author Jan-Philipp Kappmeier
 */
public class EdgeControl extends AbstractInformationPanelControl<JEdgeInformationPanel, PlanEdge, EdgeChangeEvent> {
    private final ZControl control;
    public EdgeControl(ZControl control) {
        super(new JEdgeInformationPanel());
        this.control = control;
    }

    @Override
    public void changed(EdgeChangeEvent c) {
        switch(c.getChangeType()) {
            case CreateFloorPassage:
                perform((MakeTeleportEvent)c);
                break;
            case CreatePassageRoom:
                perform((CreatePassageRoom)c);
                break;
            default:
            c.perform(control);
        }
    }
    
    private boolean creationStarted = false;
    private RoomEdge lastEdge = null;
    private void perform(MakeTeleportEvent e) {
        if (e.getEdge() instanceof RoomEdge) {
            RoomEdge edge = (RoomEdge)e.getEdge();
            if (creationStarted) {
                System.out.println("Try to connect " + lastEdge + " with " + edge);
                control.connectToWithTeleportEdge(lastEdge, edge);
                lastEdge = null;
                creationStarted = false;
            } else {
                //EventServer.getInstance().dispatchEvent(new MessageEvent(this, MessageEvent.MessageType.Status,
                //        "Wählen Sie jetzt die Gegenseite aus (Rechtsklick+Menu)!"));
                lastEdge = edge;
                creationStarted = true;
            }
        } else {
            //EventServer.getInstance().dispatchEvent(new MessageEvent(this, MessageEvent.MessageType.Error, "Nur Raumbegrenzungen können zu Stockwerksdurchgängen gemacht werden!"));
        }
    }
    private boolean passageCreationStarted = false;
    private RoomEdge passageLastEdge = null;

    private void perform(CreatePassageRoom e) {
        if (e.getEdge() instanceof RoomEdge) {
            RoomEdge myEdge = (RoomEdge) e.getEdge();
            if (passageCreationStarted) {
                control.connectRooms((RoomEdge) passageLastEdge, myEdge);
                passageLastEdge = null;
                passageCreationStarted = false;
            } else {
                passageLastEdge = myEdge;
                passageCreationStarted = true;
            }

        }
        //if (null == EditModeOld.TeleportEdgeCreation) {
        //    EventServer.getInstance().dispatchEvent(new MessageEvent(this, MessageEvent.MessageType.Status, "Erzeugen sie erst die Teleport-Kante!"));
        //    return;
        //}
        //if (null != EditModeOld.PassableRoomCreation) {
        //    EventServer.getInstance().dispatchEvent(new MessageEvent(this, MessageEvent.MessageType.Status, "Wählen Sie jetzt die Gegenseite aus (Rechtsklick+Menu)!"));
        //} else {
        //@//GUIOptionManager.setEditMode(GUIOptionManager.getPreviousEditMode());
    }

}
