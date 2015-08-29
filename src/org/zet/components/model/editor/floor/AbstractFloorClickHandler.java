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
import de.zet_evakuierung.model.Room;
import java.awt.Component;
import java.awt.Point;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.zet.components.model.editor.CoordinateTools;
import org.zet.components.model.editor.polygon.JPolygon;
import org.zet.components.model.editor.ZetObjectTypes;


/**
 *
 * @author Jan-Philipp Kappmeier
 */
public abstract class AbstractFloorClickHandler implements FloorClickHandler {
    //@//Methods regarding mouse coordinates translation, probably belongs to own class
    int rasterSnap = 400;
    private boolean rasterizedPaintMode = true;
    
    //@//variables that are accessed by the floow view, probably belongs to own class
    /**
     * This field stored where the new PlanPoint would be inserted in raster
     * paint mode if the user clicked into the {@link JFloor}.
     */
    private Point highlightedPosition = new Point(0,0);
    

    ZetObjectTypes zetObjectType = ZetObjectTypes.Room;
    
    protected List<JPolygon> elementsUnderMouse;
    private final FloorControl floorControl;
    private Point lastClick;

    boolean mouseDown = false;

    protected AbstractFloorClickHandler(FloorControl control) {
        //@//this.editStatus = Objects.requireNonNull(editStatus);
        this.floorControl = Objects.requireNonNull(control);
        elementsUnderMouse = new LinkedList<>();
        //editStatus.setPopupEnabled(true);
        
    }

    @Override
    public void mouseDown(Point p, List<JPolygon> elements) {
        setLastClick(p);
        this.elementsUnderMouse = Collections.unmodifiableList(elements);
        mouseDown = true;
    }

    @Override
    public void mouseUp(Point p, List<Component> components) {
        if (isRasterizedPaintMode()) {
            setPointerPosition(getNextRasterPoint(p));
        } else {
            setPointerPosition(p);
        }
        mouseDown = false;
    }

    @Override
    public void doubleClick(Point p, List<JPolygon> elements) {
    }

    @Override
    public void mouseMove(Point p) {
        if (isRasterizedPaintMode()) {
            setPointerPosition(getNextRasterPoint(p));
        } else {
            setPointerPosition(p);
        }
    }
    
    void setPointerPosition(Point point) {
        Point real = CoordinateTools.translateToModel(point);
        highlightedPosition = point;
        //@//JZetWindow.sendMouse( real );
    }

    boolean isRasterizedPaintMode() {
        return rasterizedPaintMode;
    }
    
    public boolean isMouseSelecting() {
        return false;
    }

    /**
     * Returns a raster point that is closest to the given point.
     *
     * @param p the point
     * @return a raster point that is closest to the given point
     */
    public Point getNextRasterPoint(Point p) {
        int rasterWidth = CoordinateTools.translateToScreen(rasterSnap);
        return new Point((int) Math.round(p.getX() / (double) rasterWidth) * rasterWidth, (int) Math.round(p.getY() / (double) rasterWidth) * rasterWidth);
    }

    @Override
    public Point getCurrentPosition() {
        return highlightedPosition;
    }
    
    public Point getLastClick() {
        return lastClick;
    }

    protected void setLastClick(Point p) {
        this.lastClick = p;
    }

    //@// Only interesting for creation, thus probably move down to abstract class
    public ZetObjectTypes getZetObjectType() {
        return zetObjectType;
    }

    public void setZetObjectType(ZetObjectTypes zetObjectType) {
        this.zetObjectType = Objects.requireNonNull(zetObjectType);
    }

    protected FloorControl getFloorControl() {
        return floorControl;
    }

    protected Room getRoomUnderMouse(Point currentMouse, List<Component> components) {
        for (Component c : components) {
            if (c instanceof JPolygon) {
                JPolygon poly = (JPolygon) c;
                if (poly.getPlanPolygon() instanceof Room) {
                    Room r = (Room) poly.getPlanPolygon();
                    Point mousePosition = CoordinateTools.translateToModel(currentMouse);
                    if (r.getPolygon().contains(new PlanPoint(mousePosition))) {
                        return r;
                    }
                }
            }
        }
        return null;
    }

    protected List<Room> getRoomsUnderMouse(Point currentMouse, List<Component> components) {
        List<Room> ret = new LinkedList<>();
        for (Component c : components) {
            if (c instanceof JPolygon) {
                JPolygon poly = (JPolygon) c;
                if (poly.getPlanPolygon() instanceof Room) {
                    Room r = (Room) poly.getPlanPolygon();
                    Point mousePosition = CoordinateTools.translateToModel(currentMouse);
                    if (r.getPolygon().contains(new PlanPoint(mousePosition))) {
                        ret.add(r);
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public void rightClick() {
    }
}
