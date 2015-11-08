/* zet evacuation tool copyright (c) 2007-15 zet evacuation team
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

package org.zet.components.model.editor.floor;

import de.zet_evakuierung.model.Area;
import de.zet_evakuierung.model.Barrier;
import de.zet_evakuierung.model.PlanPoint;
import de.zet_evakuierung.model.PlanPolygon;
import de.zet_evakuierung.model.Room;
import java.awt.Component;
import java.awt.Point;
import java.util.List;
import org.zet.components.model.editor.CoordinateTools;
import org.zet.components.model.editor.polygon.JPolygon;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class FloorClickCreatePointwiseHandler extends FloorClickCreationHandler {

    private boolean creationStarted = false;
    private boolean creationComplete = false;
    private PlanPoint last;

    public FloorClickCreatePointwiseHandler(FloorControl control) {
        super(control);
        setLastClick(null);
    }
    
    @Override
    public void mouseDown(Point p, List<JPolygon> elements) {
        creationComplete = false;
    }

    /**
     * If the mouse is moved up, a click has happened and either a new {@link PlanPolygon} is created or points are
     * added.
     *
     * @param p
     * @param components
     */
    @Override
    public void mouseUp(Point p, List<Component> components) {
        super.mouseUp(p, components);

        if (!creationStarted) { // start crating a new polygon by the first click.
            // Get the model points which we clicked on
            ZetObjectTypes type = getZetObjectType();
            if (type.isArea()) {
                Room parent = getRoomUnderMouse(p, components);
                if (parent == null) {
                    return;
                }
                getFloorControl().createNewPolygon(type.getObjectClass(), parent);
            } else {
                getFloorControl().createNewRoom();
            }

            PlanPoint p2 = new PlanPoint(CoordinateTools.translateToModel(isRasterizedPaintMode() ? getNextRasterPoint(p) : p));
            setLastClick(isRasterizedPaintMode() ? getNextRasterPoint(p) : p);
            getFloorControl().addPoint(p2);
            last = p2;
            creationStarted = true;
            //@//getEditStatus().setPopupEnabled(false);
        } else {
            // adding a new point
            PlanPoint p2 = new PlanPoint(CoordinateTools.translateToModel(isRasterizedPaintMode() ? getNextRasterPoint(p) : p));

            ZetObjectTypes type = getZetObjectType();
            if (type.isArea() && !getRoomsUnderMouse(p, components).contains(((Area) getFloorControl().latestPolygon()).getAssociatedRoom())) {
                return; // return, if the necessary polygon was not clicked on
            }
            // check if the new point will close the polygon or the area will be zero
            if (getFloorControl().latestPolygon().willClose(last, p2) && getFloorControl().latestPolygon().area() == 0 && !(getFloorControl().latestPolygon() instanceof Barrier)) {
                //@//ZETLoader.sendError( ZETLocalization2.loc.getString( "gui.error.RectangleCreationZeroArea" ) );
                return;
            }

            setLastClick(isRasterizedPaintMode() ? getNextRasterPoint(p) : p);
            if (getFloorControl().addPoint(p2)) {
                // closed a polygon
                last = null;
                creationStarted = false;
                System.out.println("Closed a polygon");
                creationComplete = true;
                setLastClick(null);
                //@//getEditStatus().setPopupEnabled(true);
            } else {
                last = p2;
            }
        }
    }

    @Override
    public void rightClick() {
        if (creationStarted) {
            System.out.println("Closing a polygon");
            creationComplete = true;
            getFloorControl().closePolygon();
            last = null;
            creationStarted = false;
            setLastClick(null);
            //@//getEditStatus().setPopupEnabled(true);
        }
    }

    protected boolean isCreationStarted() {
        return creationStarted;
    }

    @Override
    public boolean isCreationComplete() {
        return creationComplete;
    }
}
