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

import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.AssignmentType;
import de.zet_evakuierung.model.PlanPoint;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.ZControl;
import java.util.ArrayList;
import java.util.List;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class RoomControl extends AbstractControl<Room, RoomViewModel> {


    public RoomControl(ZControl control) {
        super(control);
    }

    public void setName(String name) {
        System.err.println("Name");
        zcontrol.setRoomName(model, name);
    }

    public void delete() {
        System.err.println("Attempt to delete " + model);
        zcontrol.deletePolygon(model);
    }

    public void refineCoordinates() {
        System.err.println("RefineCoordinates");
        zcontrol.refineRoomCoordinates(model.getPolygon(), 400);
    }

    /**
     * Fills the complete room with an assignment area of the given type.
     * @param assignmentType the type of the new assignment area
     * @return the newly created assignment area
     */
    public AssignmentArea fillWithAssignmentArea(AssignmentType assignmentType) {
        List<PlanPoint> points = model.getPolygon().getPolygonPoints();
        ArrayList<PlanPoint> newPoints = new ArrayList<>();

        points.stream().forEach((point) -> {
            newPoints.add(new PlanPoint(point.getXInt(), point.getYInt()));
        });
        return zcontrol.createNewArea(model, assignmentType, newPoints);
    }
}
