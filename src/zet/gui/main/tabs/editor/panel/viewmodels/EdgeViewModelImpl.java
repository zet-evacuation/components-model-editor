package zet.gui.main.tabs.editor.panel.viewmodels;

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
public class EdgeViewModelImpl extends AbstractViewModel<PlanEdge> implements EdgeViewModel {

    private final Project project;

    public EdgeViewModelImpl(PlanEdge r, Project p) {
        super(r);
        this.project = p;
    }

    @Override
    public EdgeType getEdgeType() {
        if (model instanceof RoomEdge) {
            if (model instanceof TeleportEdge) {
                TeleportEdge te = (TeleportEdge) model;
                if (((Room) te.getLinkTarget().getAssociatedPolygon()).getAssociatedFloor() instanceof DefaultEvacuationFloor) {
                    return EdgeType.Exit;
                } else {
                    return EdgeType.FloorPassage;
                }
            } else {
                if (((RoomEdge) model).isPassable()) {
                    return EdgeType.Passage;
                } else {
                    return EdgeType.Wall;
                }
            }
        } else {
            return EdgeType.AreaBoundary;
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
        if (getEdgeType() != EdgeType.Exit) {
            throw new IllegalStateException("Not an edge leading to an exit room.");
        }
        TeleportEdge te = (TeleportEdge) model;
        Room r = (Room) te.getLinkTarget().getAssociatedPolygon();
        EvacuationArea ea = r.getEvacuationAreas().get(0);
        return ea;
    }

}
