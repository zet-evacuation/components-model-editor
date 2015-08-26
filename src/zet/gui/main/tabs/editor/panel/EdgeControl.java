
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.RoomEdge;
import de.zet_evakuierung.model.ZControl;
import zet.gui.main.menu.popup.events.CreatePassageRoom;
import zet.gui.main.menu.popup.events.MakeTeleportEvent;
import zet.gui.main.tabs.editor.panel.viewmodels.EdgeViewModel;
import zet.gui.main.tabs.editor.panel.viewmodels.EdgeViewModelImpl;

/**
 * A control class for evacuation areas.
 * @author Jan-Philipp Kappmeier
 */
public class EdgeControl extends AbstractInformationPanelControl<JEdgeInformationPanel, EdgeViewModel> implements ChangeListener<EdgeChangeEvent> {
    private PlanEdge model;

    public EdgeControl(ZControl control) {
        super(generateView(), control);
        getView().setControl(this);
    }

    private static JEdgeInformationPanel generateView() {
        return new JEdgeInformationPanel(new EdgeViewModel() {
        });
    }
    
    public void setModel(PlanEdge model) {
        getView().setModel(new EdgeViewModelImpl(model, control.getProject()));
        this.model = model;
    }

    @Override
    public void changed(EdgeChangeEvent c) {
        System.err.println("Source: " + c.getSource());
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

    void setExitName(String text) {
//					if( getLeftPanel().getMainComponent().getSelectedEdge() instanceof TeleportEdge ) {
//						TeleportEdge te = (TeleportEdge)getLeftPanel().getMainComponent().getSelectedEdge();
//						if( ((Room)te.getLinkTarget().getAssociatedPolygon()).getAssociatedFloor() instanceof DefaultEvacuationFloor ) {
//							// we have an evacuation exit
//							Room r = (Room)te.getLinkTarget().getAssociatedPolygon();
//							EvacuationArea ea = r.getEvacuationAreas().get( 0 );
//							ea.setName( txtEdgeExitName.getText() );
//						}
//					}
    }

}
