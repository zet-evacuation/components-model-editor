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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.zet.components.model.editor.floor;

import de.zet_evakuierung.model.PlanPoint;
import de.zet_evakuierung.model.PlanPolygon;
import de.zet_evakuierung.model.Room;
import org.zet.components.model.editor.CoordinateTools;
import java.awt.Component;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import org.zet.components.model.editor.polygon.JPolygon;
import org.zet.components.model.editor.ZetObjectTypes;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class FloorClickCreateRectangleHandler extends FloorClickCreationHandler {
    private boolean creationActive = false;
    private boolean creationComplete = false;
            
    public FloorClickCreateRectangleHandler(FloorControl control) {
        super(control);
    }

    @Override
    public void mouseDown(Point p, List<JPolygon> elements) {
        super.mouseDown(isRasterizedPaintMode() ? getNextRasterPoint(p) : p, elements);
        creationActive = true;
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
        System.out.println("Click occured. Create rectangle.");
        //@//setMouseSelecting( false ); // not necessary any more, each selection mode has own instance

        creationActive = false;
        
        PlanPoint p1 = new PlanPoint(CoordinateTools.translateToModel(isRasterizedPaintMode() ? getNextRasterPoint(getLastClick()) : getLastClick()));
        PlanPoint p2 = new PlanPoint(CoordinateTools.translateToModel(isRasterizedPaintMode() ? getNextRasterPoint(p) : p));
        if (p1.getXInt()== p2.getXInt()|| p1.getYInt()== p2.getYInt()) {
            //ZETLoader.sendError( ZETLocalization2.loc.getString( "gui.error.RectangleCreationZeroArea" ) );
            System.out.println("Returning with no creation, zero sized area!");
            return;
        }

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

        LinkedList<PlanPoint> points = new LinkedList<>();
        points.add(new PlanPoint(p1.x, p1.y));
        points.add(new PlanPoint(p1.x, p2.y));
        points.add(new PlanPoint(p2.x, p2.y));
        points.add(new PlanPoint(p2.x, p1.y));
        points.add(new PlanPoint(p1.x, p1.y));
        if (getFloorControl().addPoints(points)) {
            creationComplete = true;
            //polygonFinishedHandler();
            //setFloorMode( FloorMode.RectangleStart );
            //lastClick = null;
        } else {
            throw new AssertionError("Creating of the polygon failed.");
        }
    }

    @Override
    public void mouseMove(Point p) {
        super.mouseMove(p);
        if (mouseDown) {
            //@// no selection possible in create modus
            //@//getEditStatus().setMouseSelecting( true );
            //@//getEditStatus().setPointerPosition( getEditStatus().isRasterizedPaintMode() ? getEditStatus().getNextRasterPoint( p ) : p );
        } else {
            //@// this is the only remaining case
            super.mouseMove(p);
        }
    }

    boolean isCreationActive() {
        return creationActive;
    }

    @Override
    public boolean isCreationComplete() {
        return creationComplete;
    }
    
    


}
