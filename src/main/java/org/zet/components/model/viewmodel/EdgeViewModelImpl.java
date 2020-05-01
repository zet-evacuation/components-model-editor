/* zet evacuation tool copyright (c) 2007-20 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
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
public class EdgeViewModelImpl extends AbstractViewModel<PlanEdge> implements EdgeViewModel {
    public EdgeViewModelImpl(PlanEdge r, Project p) {
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
