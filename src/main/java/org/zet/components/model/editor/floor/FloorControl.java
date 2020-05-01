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

package org.zet.components.model.editor.floor;

import de.zet_evakuierung.model.Area;
import de.zet_evakuierung.model.Floor;
import de.zet_evakuierung.model.PlanPoint;
import de.zet_evakuierung.model.PlanPolygon;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.ZControl;
import de.zet_evakuierung.model.exception.AssignmentException;
import java.awt.Rectangle;
import java.util.List;
import java.util.Objects;
import org.zet.components.model.viewmodel.AbstractControl;

/**
 * Control class (in the MVC model) for a floor in the zet model. The class is coupled to a floor model instance but
 * stateless and can thus be used for multiple views.
 *
 * @author Jan-Philipp Kappmeier
 */
public class FloorControl extends AbstractControl<Floor, FloorViewModel> {
    private Floor floor;
    private final JFloor view;
    private FloorClickHandler originalHandler;
    private ZetObjectTypes zetObjectType = ZetObjectTypes.Room;

    /**
     * The read only floor interface.
     * @param zcontrol
     * @param floor
     */
    public FloorControl(ZControl zcontrol, Floor floor) {
        super(zcontrol);
        this.floor = Objects.requireNonNull(floor);
        this.view = new JFloor(generateViewModel());
    }
    
    public FloorControl(ZControl zcontrol, JFloor view) {
        super(zcontrol);
        this.view = view;
        this.floor = view.getFloorModel().floor;
    }
    
    public void setFloor( Floor floor) {
        this.floor = floor;
        view.setFloorModel(generateViewModel());
    }
    
    private FloorViewModel generateViewModel() {
        return new FloorViewModel(floor, zcontrol.getProject().getBuildingPlan());
    }
    
    public void initView() {
        originalHandler = new FloorClickSelectionHandler(this);
        view.setJFloorEditListener(originalHandler);
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

    public PlanPolygon<?> latestPolygon() {
        return zcontrol.latestPolygon();
    }

    public PlanPolygon<?> closePolygon() {
        return zcontrol.closePolygon();
    }
    
    public void setEditMode( EditMode editMode ) {
        switch( editMode ) {
            case SELECTION:
                // Selection listener
                final FloorClickSelectionHandler selectionHandler = new FloorClickSelectionHandler(this);
                view.setJFloorEditListener(selectionHandler);
                view.setCustomDrawer(new CustomFloorSelectionDrawer(selectionHandler, view));
                originalHandler = selectionHandler;
                break;
            case CREATE_POINTWISE:
                // Creation pointwise (normal)
                final FloorClickCreatePointwiseHandler pointwiseHandler = new FloorClickCreatePointwiseHandler(this);
                view.setJFloorEditListener(pointwiseHandler);
                view.setCustomDrawer(new CustomFloorCreatePointwiseDrawer(pointwiseHandler, view));
                originalHandler = pointwiseHandler;
                break;
            case CREATE_RECTANGLE:
                final FloorClickCreateRectangleHandler rectangleHandler = new FloorClickCreateRectangleHandler(this);
                view.setJFloorEditListener(rectangleHandler);
                view.setCustomDrawer(new CustomFloorCreateRectangleDrawer(rectangleHandler, view));
                originalHandler = rectangleHandler;
                break;
            default:
                throw new AssertionError("Edit mode handling for floors not implemented: " + editMode);
        }
        setZetObjectTypeInternal();
    }
    
    public void setZetObjectType(ZetObjectTypes type) {
        this.zetObjectType = type;
        setZetObjectTypeInternal();
    }
    
    private void setZetObjectTypeInternal() {
        if (!(originalHandler instanceof FloorClickCreationHandler)) {
            return;
        }
        FloorClickCreationHandler handler = (FloorClickCreationHandler)originalHandler;
        PostActionHandler stairHandler;
        FloorClickHandler adaptedHandler;
        if (zetObjectType == ZetObjectTypes.Stair) {
            stairHandler = new PostActionStairHandler();
            adaptedHandler = new FloorClickCreationPostActionAdapter((FloorClickCreationHandler)originalHandler, stairHandler);
        } else {
            stairHandler = new DefaultPostActionHandler();
            adaptedHandler = originalHandler;
        }
        view.setJFloorEditListener(adaptedHandler);
        view.setPostActionListener(stairHandler);
        stairHandler.setFloor(view);
        handler.setZetObjectType(zetObjectType);
    }
    public void rename(String floorName) {
        boolean success = zcontrol.renameFloor(floor, floorName);
        if (!success) {
            System.out.println("Renaming failed, floor with that name already exists");
        } else {
            System.out.println("Renaiming successful");
        }
    }

    public void moveDown() {
        System.out.println("Moving down floor.");
        zcontrol.moveFloorDown(floor);
    }

    public void moveUp() {
        System.out.println("Moving up floor");
        zcontrol.moveFloorUp(floor);
    }

    public void rasterize() {
        System.out.println("Rassterize");
        zcontrol.getProject().getBuildingPlan().rasterize();
    }

    public void setDimension(Rectangle floorSize) {
        System.out.println("Change dimension");
        zcontrol.setFloorSize(floor, floorSize);
    }
}
