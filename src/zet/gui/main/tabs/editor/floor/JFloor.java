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

package zet.gui.main.tabs.editor.floor;

import de.zet_evakuierung.components.model.editor.DefaultGraphicsStyle;
import de.zet_evakuierung.components.model.editor.GraphicsStyle;
import de.zet_evakuierung.model.Area;
import de.zet_evakuierung.model.Barrier;
import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.PlanPolygon;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.ZModelAreaEvent;
import de.zet_evakuierung.model.ZModelRoomEvent;
import event.EventListener;
import gui.editor.CoordinateTools;
import gui.editor.planimage.PlanImage;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Supplier;
import javax.swing.SwingUtilities;
import zet.gui.main.menu.popup.Popups;
import zet.gui.main.tabs.base.AbstractFloor;
import zet.gui.main.tabs.base.JPolygon;
import zet.gui.main.tabs.editor.control.FloorViewModel;

/**
 * Graphical representation of a Floor from the BuildingPlan.
 *
 * @author Jan-Philipp Kappmeier
 * @author Timon Kelter
 */
@SuppressWarnings("serial")
public class JFloor extends AbstractFloor implements EventListener<ZModelRoomEvent> {

    /** The background image. */
    private PlanImage planImage;
    private Popups popups = new Popups();

    public final GraphicsStyle graphicsStyle = new DefaultGraphicsStyle();
    
    //@// This probably should move?
    HashMap<Room, JPolygon> roomToPolygonMapping = new HashMap<>();

    private CustomFloorDrawer customDrawer = (Graphics2D g2) -> {
    };
    
    /** Stores information about selected polygons. */
    public Selection selection = new Selection();    
    
    /**
     * Creates a new instance of {@code JFloor} with an empty floor.
     *
     * @param floorModel
     */
    public JFloor(FloorViewModel floorModel) {
        super(floorModel);
        System.err.println("A floor is created!");
        setLayout(null);
        planImage = new PlanImage();
        setBackground(graphicsStyle.getEditorBackgroundColor());

        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        enableEvents(AWTEvent.KEY_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_WHEEL_EVENT_MASK);
        initialize();
    }

    /**
     * Initializes the floor. Objects for all the polygons and rooms on the
     * floor are created.
     */
    private void initialize() {
        FloorViewModel floorModel = getFloorModel();
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Displaying floor " + floorModel );

        System.out.println("Currently, floor "+ floorModel.getName() + " contains " + this.getComponents().length + " rooms.");
        System.out.println( "Clearing" );
        removeAll();
        roomToPolygonMapping.clear();
        System.out.println("Currently, floor "+ floorModel.getName() + " contains " + this.getComponents().length + " rooms.");

        updateOffsets(floorModel);

        System.out.println("Size of the mapping:" + roomToPolygonMapping.size());
        for (Room r : floorModel.getRooms()) {
            System.out.println("Adding polygon for room " + r.getName());
            JPolygon roomPolygon = new JPolygon(this, graphicsStyle.getWallColor());
            add(roomPolygon);
            roomPolygon.displayPolygon(r.getPolygon());
            roomToPolygonMapping.put(r, roomPolygon);
        }
        System.out.println("Size of the mapping:" + roomToPolygonMapping.size());

        planImage.setOffsetX(-floorMin_x);
        planImage.setOffsetY(-floorMin_y);

	//@//ZETLoader.sendMessage( ZETLocalization2.loc.getString( "gui.message.FloorSuccessfullyLoaded" ) );
        revalidate();
        repaint();
    }
    
    @Override
    public void setFloorModel( FloorViewModel floorModel ) {
        super.setFloorModel(floorModel);
        initialize();
    }

    @Override
    public void handleEvent(ZModelRoomEvent e) {
        if (e instanceof ZModelAreaEvent) {
            ZModelAreaEvent e2 = (ZModelAreaEvent) e;
            Room room = e2.getAffectedRoom();
            // cannot create a new room here!
            JPolygon poly = roomToPolygonMapping.get(room);
            assert poly != null;
            poly.displayPolygon(room.getPolygon());
            JPolygon jp = findPolygonToArea(this, e2.getAffectedArea().getPolygon());
            System.out.println("Editing set to " + jp);
            //@//editStatus.setCurrentEditing(jp);
            postActionListener.setCreated(jp);
        } else {
            for (Room room : e.getAffectedRooms()) {
                JPolygon poly = roomToPolygonMapping.get(room);
                if (poly != null) {
                    poly.displayPolygon(room.getPolygon());
                } else { // we have a new room
                    JPolygon roomPolygon = new JPolygon(this, graphicsStyle.getWallColor());
                    add(roomPolygon);
                    roomPolygon.displayPolygon(room.getPolygon());
                    roomToPolygonMapping.put(room, roomPolygon);
                    //@//editStatus.setCurrentEditing(roomPolygon);
                    postActionListener.setCreated(roomPolygon);
                }
            }
        }
    }

    public Popups getPopups() {
        return popups;
    }
    
    private static class DefaultClickHandler implements FloorClickHandler {
        private Point p = new Point();

        @Override
        public void mouseDown(Point p, List<JPolygon> elements) {
        }

        @Override
        public void mouseUp(Point p, List<Component> components) {
        }

        @Override
        public void doubleClick(Point p, List<JPolygon> elements) {
        }

        @Override
        public void mouseMove(Point p) {
            this.p = p;
        }

        @Override
        public void rightClick() {
        }

        @Override
        public Point getCurrentPosition() {
            return p;
        }
    }
    
    private MouseListener editMouseListener = null;
    private FloorClickHandler listener = new DefaultClickHandler();
    
    private PostActionHandler postActionListener = new DefaultPostActionHandler();
    
    public FloorClickHandler getJFloorEditListener() {
        return listener;
    }
    
    public void setJFloorEditListener(FloorClickHandler editListener) {
        removeMouseListener(editMouseListener);
        editMouseListener = new FloorEditListenerAdapter(editListener);
        this.listener = editListener;
        addMouseListener(editMouseListener);
    }
    
    public void setPostActionListener(PostActionHandler postActionHandler) {
        this.postActionListener = postActionHandler;
    }
    
    private class FloorEditListenerAdapter extends MouseAdapter {
        private final FloorClickHandler mouseHandler;

        public FloorEditListenerAdapter(FloorClickHandler mouseHandler) {
            this.mouseHandler = mouseHandler;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            // Convert coordinates to encompassing floor's coordinates and send
            // to the floor edit listener.
            if (e.getButton() == MouseEvent.BUTTON1 && e.getID() == MouseEvent.MOUSE_CLICKED) {
                if (e.getClickCount() == 2) {
                    List<JPolygon> clickedPolygons = findAllPolygonsAt(JFloor.this, e.getPoint());
                    mouseHandler.doubleClick(e.getPoint(), clickedPolygons);
                }
            } else if (e.getButton() == MouseEvent.BUTTON3 && e.getID() == MouseEvent.MOUSE_CLICKED) {
                mouseHandler.rightClick();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            super.mouseReleased(e);
            if (e.getButton() == MouseEvent.BUTTON1 && e.getID() == MouseEvent.MOUSE_PRESSED) {
                List<JPolygon> clickedPolygons = findAllPolygonsAt(JFloor.this, e.getPoint());
                mouseHandler.mouseDown(e.getPoint(), clickedPolygons);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mousePressed(e);
            if (e.getButton() == MouseEvent.BUTTON1 && e.getID() == MouseEvent.MOUSE_RELEASED) {
                mouseHandler.mouseUp(e.getPoint(), Arrays.asList(getComponents()));
                repaint();
            }
        }
    };    

    private JPolygon findPolygonToArea(JFloor base, PlanPolygon poly) {
        for (Component c : base.getComponents()) {
            if (c instanceof JPolygon) {
                JPolygon jp = (JPolygon) c;
                JPolygon ret = findPolygonToArea(jp, poly);
                if (ret != null) {
                    return ret;
                }
            }
        }
        throw new AssertionError("Created area not found!");
    }

    private JPolygon findPolygonToArea(JPolygon base, PlanPolygon poly) {
        if (base.getPlanPolygon() == poly) {
            return base; // if we are assigned to the plan poly, return
        }
        for (Component c : base.getComponents()) { // otherwise search in the sub-components
            if (c instanceof JPolygon) {
                JPolygon jp = (JPolygon) c;
                JPolygon ret = findPolygonToArea(jp, poly);
                if (ret != null) {
                    return ret;
                }
            }
        }
        return null; // not contained, return null
    }

    /**
     * Recreates the visual representations for the objects on the floor. Should
     * be called if the properties of the representation (e. g. color, zoom
     * factor, etc.) have been changed. This method recreates all the objects,
     * thus it is quite costly and should be called reasonable.
     */
    public void redisplay() {
        System.out.println("REDISPLAY");
        JPolygon wasEditing = postActionListener.getCreated();
        PlanPolygon<?> editPoly = null;
        if (wasEditing != null) {
            editPoly = wasEditing.getPlanPolygon();
        }

        for (Entry<Room, JPolygon> e : roomToPolygonMapping.entrySet()) {
            Room r = e.getKey();
            JPolygon poly = e.getValue();
            poly.displayPolygon(r.getPolygon());
        }
        getPlanImage().update();

        if (editPoly != null) { // keep track of the editing-polygon. they are lost due to the current implementation of JPolygon. this is slow!
            JPolygon jp = findPolygonToArea(this, editPoly);
            //editStatus.setCurrentEditing(jp); // do not call redisplay in this case, as long as JPolygon.display is not reimplemented
            postActionListener.setCreated(jp);
        }
    }

    /**
     * <p>
     * Paints the panel in the graphics object. It is possible to pass any
     * graphics object, but it is particularly used for painting this panel.
     * This can be used to save as bitmap or JPEG.</p>
     * <p>
     * It draws points and a raster and a background image, if setLocation.</p>
     *
     * @param g The graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.drawRaster(g2);

        // Draw background image
        planImage.paintComponent(g2);

        drawMouse(g2);
        
        customDrawer.drawCustom(g2);
    }
    
    /**
     * Draws a small rectangle at the mouse position.
     * @param g2 the graphics context
     */
    private void drawMouse(Graphics2D g2) {
        // draw a highlighted node at the mouse pointer
        Point mousePosition = listener.getCurrentPosition();
        int size = graphicsStyle.getPointSize();
        //if (!listener.isMouseSelecting()) {
            g2.drawRect(mousePosition.x - size, mousePosition.y - size, 2 * size, 2 * size);
        //}
    }

    public void setCustomDrawer(CustomFloorDrawer customDrawer) {
        this.customDrawer = customDrawer;
    }

    /**
     * Mouse Motion Event Handler
     */
    @Override
    protected void processMouseMotionEvent(MouseEvent e) {
        //@//editStatus.getCurrentHandler().mouseMove(e.getPoint());
        listener.mouseMove(e.getPoint());
        repaint();
    }

    /**
     * This method returns <b>all</b> JPolygons which are contained in "c" and
     * contain the specified point. In contrast to that, the API method
     * findComponentAt only returns <b>1</b> component which contains the given
     * point.
     *
     * @param c The container in which the search should be performed
     * @param p The point, which the sought-after components must contain. The
     * coordinates must be relative to container c
     * @return A list of JPolygons which contain point p. This list will be
     * empty in the case that such JPolygons do not exist.
     */
    private static List<JPolygon> findAllPolygonsAt(Container c, Point p) {
        // For performance reasons we use only one single list for all recursive calls
        LinkedList<JPolygon> result = new LinkedList<>();
        findAllPolygonsAtImpl(c, p, result);
        return result;
    }

    /**
     * Used for zooming in and out with the mouse wheel.
     *
     * @param e the mouse event
     */
    @Override
    protected void processMouseWheelEvent(MouseWheelEvent e) {
        if (!e.isControlDown()) // move up/down
                    ; //@//guiControl.scrollVertical(e.getWheelRotation() );
        else {
            // zoom in and out
            double oldZoom = CoordinateTools.getZoomFactor();
            if (e.getScrollType() == MouseWheelEvent.WHEEL_BLOCK_SCROLL) {
                oldZoom = e.getWheelRotation() < 0 ? Math.min(oldZoom * 2, 0.25d) : Math.max(oldZoom * 2, 0.01d);
            } else {
                double offset = (e.getScrollAmount() * Math.abs(e.getWheelRotation())) / 100.0d;
                offset /= 4; // Make offset smaller, otherwise it's too fast
                oldZoom = e.getWheelRotation() < 0 ? Math.min(oldZoom + offset, 0.25d) : Math.max(oldZoom - offset, 0.01d);
            }
            //@//guiControl.setZoomFactor( oldZoom );
        }
    }

    /**
     * This is an internal helper method. Never call it. Call
     * findAllComponentsAt (Container c, Point p) instead.
     */
    private static void findAllPolygonsAtImpl(Container c, Point p, List<JPolygon> polygonList) {
        for (Component comp : c.getComponents()) {
            Point relative_point = SwingUtilities.convertPoint(c, p, comp);
            if (comp instanceof JPolygon) {
                JPolygon poly = (JPolygon) comp;

                // Test if the Polygon has the point (Barriers pass a slightly less strict test)
                if (poly.getPlanPolygon() instanceof Barrier) {
                    if (poly.getBounds().contains(p)) {
                        polygonList.add(poly);
                    }
                } else if (poly.getDrawingPolygon().contains(relative_point)) {
                    polygonList.add(poly);
                }

		// Recursively search the JPolygon. We can restrict ourselves to
                // searching recursively in all JPolygons, because Polygons can
                // only be placed upon the Floor (then we find them in the first
                // recursion level) or upon Rooms (then we find them in recursion
                // level 2). So, we don't have to check every instance of
                // java.awt.Container for new JPolygons. This also is a benefit
                // for the performance.
				// Second note: It is important that we search all JPolygons no
                // matter whether they contain the Point or not, because Rooms
                // may not contain the point but they may contain Areas which lie
                // outside of the room (though this is not accepted when you want
                // to transform to CA or Graph) and must be checked
                findAllPolygonsAtImpl(poly, relative_point, polygonList);
            }
        }
    }

    public void displayFloor() {
//		displayFloor( myFloor );
    }

    public PlanImage getPlanImage() {
        return planImage;
    }

    /**
     * Show the given polygon to the user by scrolling until it is visible and
     * setting it as the selected polygon (clears the previous selection). If
     * the given polygon is not shown on this JFloor nothing will happen.
     *
     * @param p the polygon that is shown
     */
    public void showPolygon(Room p) {
		//if( p instanceof Area )
        //	p = ((Area)p).getAssociatedRoom();
        JPolygon jp = roomToPolygonMapping.get(p);//getJPolygon( p );

        if (jp != null) {
            scrollRectToVisible(jp.getBounds());
            //@//setSelectedPolygon( jp );
            //@//editStatus.selectPolygon(jp);
        }
    }

    public void showPolygon(Area a) {
        showPolygon(a.getAssociatedRoom());
    }

    void addPolygon(List<JPolygon> toAdd) {
        selection.addPolygon(toAdd);
        System.err.println("Add multiple polygons");
        if (!toAdd.isEmpty()) {
            fireSelectionChangedEvent(toAdd.get(0));
        }
    }

    void clearSelection() {
        boolean wasClear = selection.isClear();
        selection.clearSelection();
        System.err.println("Selection cleared");
        if (!wasClear) {
            fireSelectionClearedEvent();
        }
    }

    void selectPolygon(JPolygon toSelect) {
        selection.selectPolygon(toSelect);
        System.err.println("Polygon selected");
        fireSelectionChangedEvent(toSelect);
    }

    void selectEdge(JPolygon toSelect, PlanEdge edge) {
        selection.selectEdge(toSelect, edge);
        System.err.println("Edge selected");
        fireSelectionEdgeEvent(toSelect, edge);
    }

    public List<JPolygon> getSelectedPolygons() {
        return selection.getSelectedPolygons();
    }

    public PlanEdge getSelectedEdge() {
        return selection.getSelectedEdge().isEmpty() ? null : selection.getSelectedEdge().get(0);
    }

    public JPolygon getEdgePolygon() {
        return selection.getEdgePolygon();
    }
    
    public void addSelectionListener(SelectionListener l) {
        listenerList.add(SelectionListener.class, l);
    }

    public void removeSelectionListener(SelectionListener l) {
        listenerList.remove(SelectionListener.class, l);
    }

    private void fireSelectionChangedEvent(JPolygon polygon) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        SelectionEvent selectionEvent = null;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == SelectionListener.class) {
                if (selectionEvent == null) {
                    selectionEvent = new SelectionEvent(this, polygon);
                }
                ((SelectionListener) listeners[i + 1]).selectionChanged(selectionEvent);
            }
        }
    }
    private void fireSelectionEdgeEvent(JPolygon polygon, PlanEdge edge) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        SelectionEvent selectionEvent = null;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == SelectionListener.class) {
                if (selectionEvent == null) {
                    selectionEvent = new SelectionEvent(this, polygon, edge);
                }
                ((SelectionListener) listeners[i + 1]).selectionEdge(selectionEvent);
            }
        }
    }
    private void fireSelectionClearedEvent() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        SelectionEvent selectionEvent = null;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == SelectionListener.class) {
                if (selectionEvent == null) {
                    selectionEvent = new SelectionEvent(this);
                }
                ((SelectionListener) listeners[i + 1]).selectionCleared(selectionEvent);
            }
        }
    }
}
