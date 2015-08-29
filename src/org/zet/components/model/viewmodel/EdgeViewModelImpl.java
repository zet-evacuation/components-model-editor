package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.DefaultEvacuationFloor;
import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.Project;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.RoomEdge;
import de.zet_evakuierung.model.TeleportEdge;

/**
 * The assignment area view model makes available all information necessary to display an assignment area.
 *
 * @author Jan-Philipp Kappmeier
 */
class EdgeViewModelImpl extends AbstractViewModel<PlanEdge> implements EdgeViewModel {
    EdgeViewModelImpl(PlanEdge r, Project p) {
        super(r);
    }

    @Override
    public EdgeType getEdgeType() {
        if (model instanceof RoomEdge) {
            if (model instanceof TeleportEdge) {
                Room target = (Room)((TeleportEdge)model).getLinkTarget().getAssociatedPolygon();
                return target.getAssociatedFloor() instanceof DefaultEvacuationFloor ? EdgeType.EXIT
                        : EdgeType.FLOOR_PASSAGE;
            } else {
                return ((RoomEdge) model).isPassable() ? EdgeType.PASSAGE : EdgeType.WALL;
            }
        } else {
            return EdgeType.AREA_BOUNDARY;
        }
    }

    @Override
    public double getLength() {
        return model.length();
    }

    @Override
    public EdgeOrientation getOrientation() {
        if( model.isHorizontal()) {
            return EdgeOrientation.Horizontal;
        } else if (model.isVertical()) {
            return EdgeOrientation.Vertical;
        } else {
            return EdgeOrientation.Oblique;
        }
    }

    
    @Override
    public EvacuationArea getAssociatedExit() {
        if (getEdgeType() != EdgeType.EXIT) {
            throw new IllegalStateException("Not an edge leading to an exit room.");
        }
        TeleportEdge te = (TeleportEdge) model;
        Room r = (Room) te.getLinkTarget().getAssociatedPolygon();
        EvacuationArea ea = r.getEvacuationAreas().get(0);
        return ea;
    }

}
