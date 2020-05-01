
package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.PlanPoint;
import de.zet_evakuierung.model.RoomEdge;
import de.zet_evakuierung.model.ZControl;
import de.zet_evakuierung.template.Door;
import de.zet_evakuierung.template.ExitDoor;

/**
 * A control class for evacuation areas.
 * @author Jan-Philipp Kappmeier
 */
public class EdgeControl extends AbstractControl<PlanEdge, EdgeViewModel> {
    private PlanEdge model;

    public EdgeControl(ZControl control) {
        super(control);
    }

    public void setExitName(String text) {
        if (viewModel.getEdgeType() == EdgeViewModel.EdgeType.EXIT) {
            EvacuationArea ea = viewModel.getAssociatedExit();
            ea.setName(text);
        }
    }

    public void insertPoint(PlanPoint clickPoint) {
        // Compute a point that is ON the edge (the click is not neccessarily)
        PlanPoint pointOnEdge = model.getPointOnEdge(clickPoint);
        zcontrol.insertPoint(model, this.getModelPoint(pointOnEdge));
    }

    public void makePassable() {
        if (viewModel.getEdgeType() == EdgeViewModel.EdgeType.WALL) {
            boolean success = zcontrol.makePassable((RoomEdge) model);
            if (!success) {
                //EventServer.getInstance().dispatchEvent(new MessageEvent(this, MessageEvent.MessageType.Error, "Erzeugen Sie zuerst 2 übereinanderliegende Raumbegrenzungen!"));
            }
        } else {
            //EventServer.getInstance().dispatchEvent(new MessageEvent(this, MessageEvent.MessageType.Error, "Nur Raumbegrenzungen können passierbar gemacht werden!"));
        }
    }

    public void makeExit() {
        if (viewModel.getEdgeType() == EdgeViewModel.EdgeType.WALL) {
            zcontrol.getProject().getBuildingPlan().getDefaultFloor().addEvacuationRoom((RoomEdge) model);
        } else {
            //EventServer.getInstance().dispatchEvent(new MessageEvent(this, MessageEvent.MessageType.Error, "Nur Raumbegrenzungen können zu Evakuierungsausgängen gemacht werden!"));
        }
    }

    public void revertPassage() {
        if (viewModel.getEdgeType().isPassable()) {
            zcontrol.disconnectAtEdge((RoomEdge) model);
        }
    }

    public void createEvacuationRoom(RoomEdge connectTo) {
        if (connectTo == model) {
            return;
        }
        if ( viewModel.getEdgeType() == EdgeViewModel.EdgeType.WALL && !connectTo.isPassable() ) {
            zcontrol.connectRooms((RoomEdge)model, connectTo);
        }
    }

    public void createFloorPassage(RoomEdge connectTo) {
        if (connectTo == model) {
            return;
        }
        if (viewModel.getEdgeType() == EdgeViewModel.EdgeType.WALL && !connectTo.isPassable()) {
            System.out.println("Try to connect " + model + " with " + connectTo);
            zcontrol.connectToWithTeleportEdge((RoomEdge)model, connectTo);
        } else {
            //EventServer.getInstance().dispatchEvent(new MessageEvent(this, MessageEvent.MessageType.Error, "Nur Raumbegrenzungen können zu Stockwerksdurchgängen gemacht werden!"
        }
    }

    public void createTemplateExit(PlanPoint clickPoint, ExitDoor exit) {
        if (viewModel.getEdgeType() == EdgeViewModel.EdgeType.WALL) {
            zcontrol.createExitDoor((RoomEdge) model, getModelPoint(clickPoint), exit.getSize());
        }
    }

    public void createTemplatePassage(PlanPoint clickPoint, Door door) {
        if (viewModel.getEdgeType() == EdgeViewModel.EdgeType.WALL) {
            zcontrol.createDoor((RoomEdge) model, getModelPoint(clickPoint), door.getSize());
        }
    }
    
    private PlanPoint getModelPoint(PlanPoint clickPoint) {
        PlanPoint newPoint = model.getPointOnEdge(clickPoint);
        final double rasterSnap = 400;
        boolean rasterizedPaintMode = true;
        if (rasterizedPaintMode) {
            newPoint.x = (int) Math.round((double) newPoint.x / rasterSnap) * (int) rasterSnap;
            newPoint.y = (int) Math.round((double) newPoint.y / rasterSnap) * (int) rasterSnap;
        }
        return newPoint;
    }
}
