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
import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.PlanPoint;
import de.zet_evakuierung.model.PlanPolygon;
import de.zet_evakuierung.model.Room;
import org.zet.components.model.editor.CoordinateTools;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.swing.SwingUtilities;
import org.zet.components.model.editor.polygon.JPolygon;

/**
 * Handles actions that occur if mouse interaction with a {@link JFloor} occurs in selection mode.
 *
 * @author Jan-Philipp Kappmeier
 */
public class FloorClickSelectionHandler extends AbstractFloorClickHandler {

    private boolean potentialDragStart = false;
    private boolean dragStarted = false;

    private boolean mouseSelecting = false;

    public FloorClickSelectionHandler(FloorControl control) {
        super(control);
    }

    @Override
    public void mouseDown(Point p, List<JPolygon> elements) {
        super.mouseDown(p, elements);

        // if one of the clicked polygons was already selected, just set potential dragStart and return
        for (JPolygon poly : elements) {
            if (poly.isSelected()) {
                potentialDragStart = true;
                System.out.println("Potential drag start for polygon");
                return;
            }
        }
        selectPolygon(p, elements);
    }

    @Override
    public void doubleClick(Point p, List<JPolygon> elements) {
        selectPolygon(p, elements);
    }

    private void selectPolygon(Point p, List<JPolygon> elements) {
        // Get the new selection
        ListIterator<JPolygon> itPoly = elements.listIterator();
        JPolygon toSelect = null;

        // If none of the polygons that we clicked on is selected,
        // then just select the top-level one. If a JPolygon is selected
        // then switch the selection over to the next JPolygon in
        // the given order of polygons
        while (itPoly.hasNext()) {
            toSelect = itPoly.next();

            if (toSelect.isSelected()) {
                toSelect = itPoly.hasNext() ? itPoly.next() : elements.get(0);
                break;
            }
        }

        // We have clicked on an object, now find out if it was a polygon, edge or point and select it
        if (toSelect != null) {
            System.out.println("Probably start dragging");
            potentialDragStart = true;
            // We are gonna select the polygon "toSelect"
            Object clickedOn = null;
            JPolygon sel = toSelect;
            clickedOn = sel.findClickTargetAt(SwingUtilities.convertPoint(getFloorControl().getView(), p, sel));
            System.out.println("clickedOn: " + clickedOn);

            if (clickedOn != null) {
                if (clickedOn instanceof PlanPoint) {
                    PlanPoint dp = (PlanPoint) clickedOn;
                } else if (clickedOn instanceof PlanEdge) {
                    PlanEdge edge = (PlanEdge) clickedOn;
                    getFloorControl().getView().clearSelection();
                    getFloorControl().getView().selectEdge(toSelect, edge);
                } else if (clickedOn instanceof PlanPolygon) {
                    getFloorControl().getView().selectPolygon(toSelect);
                } else {
                    getFloorControl().getView().clearSelection();
                }
            }
        } else {
            getFloorControl().getView().clearSelection();
        }
    }

    @Override
    public void mouseUp(Point p, List<Component> components) {
        super.mouseUp(p, components);

        if (isMouseSelecting()) {
            handleSelection(components);
        } else if (dragStarted) {
            handleDrag(p, components);
        }
        potentialDragStart = false;
        dragStarted = false;
    }

    /**
     * Selects polygons in an area after mouse is released.
     */
    private void handleSelection(List<Component> components) {
        // We have created a selection rectangle. select all the stuff in there
        // No negative width/height allowed here, so work around it with Math
        Rectangle selectionArea = new Rectangle(Math.min(getLastClick().x, getCurrentPosition().x),
                Math.min(getLastClick().y, getCurrentPosition().y),
                Math.abs(getCurrentPosition().x - getLastClick().x),
                Math.abs(getCurrentPosition().y - getLastClick().y));
        List<JPolygon> toAdd = findComponentsInSelectionArea(selectionArea, components);
        getFloorControl().getView().clearSelection();
        getFloorControl().getView().addPolygon(toAdd);
        setMouseSelecting(false);
    }

    private List<JPolygon> findComponentsInSelectionArea(Rectangle selectionArea, List<Component> components) {
        List<JPolygon> foundComponents = new LinkedList<>();
        for (Component room : components) {
            // Search for contained rooms
            Rectangle room_bounds = ((JPolygon) room).getBounds();
            if (selectionArea.contains(room_bounds)) {
                foundComponents.add((JPolygon) room);
            } else if (selectionArea.intersects(room_bounds)) {
                // If the room as a whole is not contained, then at least some areas within
                // it may be inside the selection area.
                // Note that we do not explicitly select the areas when the full room is
                // selected, because areas always follow the movement of the room automatically.
                for (Component area : ((JPolygon) room).getComponents()) {
                    Rectangle area_bounds = ((JPolygon) area).getBounds();
                    area_bounds.translate(room.getX(), room.getY());
                    if (selectionArea.contains(area_bounds)) {
                        foundComponents.add((JPolygon) area);
                    }
                }
            }
        }
        return foundComponents;
    }

    private void handleDrag(Point p, List<Component> components) {
        if (getFloorControl().getView().getSelectedPolygons().size() > 0) {
            System.out.println("Stopping dragging of polygon");
            // we try to drag some polygons
            // Drag whole selection (>= 1 polygon)
            dragFinished(p, components);
        } else if (getFloorControl().getView().getSelectedEdge() != null) {
            System.out.println("Stop dragging a polygon");
            dragFinished(p, getFloorControl().getView().getSelectedEdge());
            getFloorControl().getView().getEdgePolygon().setSelectedEdge(null);
            //getEditStatus().getEdgePolygon().setDragOffset( dragOffset );
            getFloorControl().getView().getEdgePolygon().setDragged(false);
        }
    }

    @Override
    public void mouseMove(Point p) {
        if (mouseDown) {
            if (potentialDragStart) {
                dragStarted = true;
                dragOngoing(p);
            } else {
                setMouseSelecting(true);
                setPointerPosition(p);
            }
        } else {
            super.mouseMove(p);
        }
    }

    /**
     * Returns the drag offset for the current mouse position. Takes into account rasterization
     *
     * @param currentMouse
     * @return
     */
    private Point dragOffset(Point currentMouse) {
        if (isRasterizedPaintMode()) {
            Point start = getNextRasterPoint(getLastClick());
            Point end = getNextRasterPoint(currentMouse);
            return new Point(end.x - start.x, end.y - start.y);
        } else {
            return new Point(currentMouse.x - getLastClick().x, currentMouse.y - getLastClick().y);
        }
    }

    private void dragOngoing(Point currentMouse) {
        Point dragOffset = dragOffset(currentMouse);

        if (getFloorControl().getView().getSelectedPolygons().size() > 0) {
            // we try to drag some polygons
            for (JPolygon sel : getFloorControl().getView().getSelectedPolygons()) {
                sel.setDragged(true);
                sel.setDragOffset(dragOffset);
            }
        } else if (getFloorControl().getView().getSelectedEdge() != null) {
            System.out.println("Dragging an edge");
            getFloorControl().getView().getEdgePolygon().setSelectedEdge(getFloorControl().getView().getSelectedEdge());
            getFloorControl().getView().getEdgePolygon().setDragOffset(dragOffset);
            getFloorControl().getView().getEdgePolygon().setDragged(true);
        }
    }

    private void dragFinished(Point currentMouse, PlanEdge edge) {
        Point dragStart = CoordinateTools.translateToModel(isRasterizedPaintMode() ? getNextRasterPoint(getLastClick()) : getLastClick());
        Point dragEnd = CoordinateTools.translateToModel(isRasterizedPaintMode() ? getNextRasterPoint(currentMouse) : currentMouse);
        Point translated = new Point(dragEnd.x - dragStart.x, dragEnd.y - dragStart.y);

        List<PlanPoint> draggedPlanPoints = new LinkedList<>();
        draggedPlanPoints.add(edge.getSource());
        draggedPlanPoints.add(edge.getTarget());

        getFloorControl().movePoints(draggedPlanPoints, translated.x, translated.y);
    }

    private void dragFinished(Point currentMouse, List<Component> components) {
        Point dragStart = CoordinateTools.translateToModel(isRasterizedPaintMode() ? getNextRasterPoint(getLastClick()) : getLastClick());
        Point dragEnd = CoordinateTools.translateToModel(isRasterizedPaintMode() ? getNextRasterPoint(currentMouse) : currentMouse);
        Point translated = new Point(dragEnd.x - dragStart.x, dragEnd.y - dragStart.y);

        // Check, if all selected polygons are areas:
        List<Area> areas = new LinkedList<>();
        for (JPolygon sel : getFloorControl().getView().getSelectedPolygons()) {
            if (sel.getPlanPolygon() instanceof Area) {
                areas.add((Area) sel.getPlanPolygon());
            } else {
                areas = null;
                break;
            }
        }
        if (areas != null) {
            Room r = getRoomUnderMouse(currentMouse, components);
            if (r != null) {
                getFloorControl().moveAreas(areas, translated.x, translated.y, r);
                for (JPolygon sel : getFloorControl().getView().getSelectedPolygons()) {
                    sel.setDragged(false);
                }
                return;
            }
        }

        List<PlanPoint> draggedPlanPoints = new LinkedList<>();
        for (JPolygon sel : getFloorControl().getView().getSelectedPolygons()) {
            draggedPlanPoints.addAll(((PlanPolygon) sel.getPlanPolygon()).getPlanPoints());
        }

        getFloorControl().movePoints(draggedPlanPoints, translated.x, translated.y);

        for (JPolygon sel : getFloorControl().getView().getSelectedPolygons()) {
            sel.setDragged(false);
        }
    }

    protected void setMouseSelecting(boolean b) {
        mouseSelecting = b;
    }

    @Override
    public boolean isMouseSelecting() {
        return mouseSelecting;
    }
}
