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

package zet.gui.main.tabs.editor.control;

import de.zet_evakuierung.model.Area;
import de.zet_evakuierung.model.Floor;
import de.zet_evakuierung.model.PlanPoint;
import de.zet_evakuierung.model.PlanPolygon;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.ZControl;
import de.zet_evakuierung.model.exception.AssignmentException;
import java.util.List;
import java.util.Objects;
import zet.gui.main.tabs.editor.EditMode;
import zet.gui.main.tabs.editor.floor.CustomFloorSelectionDrawer;
import zet.gui.main.tabs.editor.floor.FloorClickCreatePointwiseHandler;
import zet.gui.main.tabs.editor.floor.FloorClickHandler;
import zet.gui.main.tabs.editor.floor.FloorClickSelectionHandler;
import zet.gui.main.tabs.editor.floor.JFloor;

/**
 * Control class (in the MVC model) for a floor in the zet model. The class is coupled to a floor model instance but
 * stateless and can thus be used for multiple view.
 *
 * @author Jan-Philipp Kappmeier
 */
public class FloorControl {
    private final ZControl zcontrol;
    private Floor floor;
    private final JFloor view;
    /** The read only floor interface. */

    /**
     * The read only floor interface.
     * @param zcontrol
     * @param floor
     */
    public FloorControl(ZControl zcontrol, Floor floor) {
        this.zcontrol = Objects.requireNonNull(zcontrol);
        this.floor = Objects.requireNonNull(floor);
        this.view = new JFloor(generateViewModel());
    }
    
    public FloorControl(ZControl zcontrol, JFloor view) {
        this.zcontrol = Objects.requireNonNull(zcontrol);
        this.view = view;
        this.floor = view.getFloorModel().floor;
    }
    
    public void setFloor( Floor floor) {
        this.floor = floor;
        view.setFloorModel(generateViewModel());
    }
    
    private FloorViewModel generateViewModel() {
        return new FloorViewModel(floor);
    }
    
    public void initView() {
        FloorClickHandler handler = new FloorClickSelectionHandler(this);
        view.setJFloorEditListener(handler);
    }
    
    public JFloor getView() {
        return view;
    }

    public void movePoints(List<? extends PlanPoint> points, int x, int y) {
        zcontrol.movePoints(points, x, y);
    }

    public void moveAreas(List<Area> areas, int x, int y, Room target) {
        zcontrol.moveAreas(areas, x, y, target);
    }

    public boolean addPoint(PlanPoint point) {
        return zcontrol.addPoint(point);
    }

    public boolean addPoints(List<PlanPoint> points) {
        return zcontrol.addPoints(points);
    }

    public void createNewRoom() throws AssignmentException, IllegalArgumentException {
        zcontrol.createNewPolygon(Room.class, floor);
    }

    public void createNewPolygon(Class<?> polygonClass, Room parent) throws AssignmentException, IllegalArgumentException {
        if (!isRoomInFloor(parent)) {
            throw new IllegalStateException("Room " + parent + " is not on the controlled floor " + floor);
        }
        zcontrol.createNewPolygon(polygonClass, parent);
    }
    
    private boolean isRoomInFloor(Room room) {
        for (Room r : floor) {
            if (room.equals(r)) {
                return true;
            }
        }
        return false;
    }

    public PlanPolygon latestPolygon() {
        return zcontrol.latestPolygon();
    }

    public PlanPolygon closePolygon() {
        return zcontrol.closePolygon();
    }
    
    public void setEditMode( EditMode editMode ) {
        //PostActionHandler stairHandler = new PostActionStairHandler();
        //PostActionHandler stairHandler = new DefaultPostActionHandler();
        //stairHandler.setFloor(jfloor);
        //FloorClickHandler handler = new FloorClickCreationPostActionAdapter(editListener, stairHandler);
        //jfloor.setPostActionListener(stairHandler);
        //editListener.setZetObjectType(ZetObjectTypes.Evacuation);
        FloorClickSelectionHandler handler;
        switch( editMode ) {
            case Selection:
                // Selection listener
                handler = new FloorClickSelectionHandler(this);
                view.setJFloorEditListener(handler);
                view.setCustomDrawer(new CustomFloorSelectionDrawer(handler, view));
                break;
            case CreationPointWise:
                // Creation pointwise (normal)
                view.setJFloorEditListener(new FloorClickCreatePointwiseHandler(this));
                break;
            case CreationRectangle:
                view.setJFloorEditListener(new FloorClickCreatePointwiseHandler(this));
                break;
            default:
                throw new AssertionError("Edit mode handling for floors not implemented: " + editMode);
        }
    }
}
