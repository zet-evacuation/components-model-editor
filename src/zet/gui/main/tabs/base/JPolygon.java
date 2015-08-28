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

package zet.gui.main.tabs.base;

import de.zet_evakuierung.components.model.editor.DefaultGraphicsStyle;
import de.zet_evakuierung.components.model.editor.GraphicsStyle;
import static org.zetool.common.util.Helper.in;
import org.zetool.common.util.Selectable;
import de.zet_evakuierung.model.Area;
import de.zet_evakuierung.model.Barrier;
import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.PlanPoint;
import de.zet_evakuierung.model.PlanPolygon;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.RoomEdge;
import de.zet_evakuierung.model.StairArea;
import de.zet_evakuierung.model.TeleportEdge;
import gui.editor.Areas;
import gui.editor.CoordinateTools;
import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.swing.SwingUtilities;
import zet.gui.main.tabs.editor.floor.JFloor;

/**
 * Graphical representation of a {@link de.zet_evakuierung.model.PlanPolygon}.
 * This class has the special feature of forwarding mouse events to it's parent
 * component before dealing with them itself. This is different to the java
 * standard behavior, where only the topmost component that has been clicked on
 * is notified of the event.
 *
 * @author Jan-Philipp Kappmeier
 * @author Timon Kelter
 */
@SuppressWarnings("serial")
public class JPolygon extends AbstractPolygon<JFloor> implements Selectable {

    protected static Point lastPosition = new Point();
    protected static boolean selectedUsed = false;
    private final GraphicsStyle graphicsStyle = new DefaultGraphicsStyle();
    /**
     * The radius of the nodes on screen. This should be less than or equal to
     * EDGE_WIDTH_ADDITION + 1
     */
    private final int NODE_PAINT_RADIUS = graphicsStyle.getPointSize();
    /**
     * The radius around the nodes of the edge in which a click on the edge is
     * also counted as a click on the node. This should be less than or equal to
     * EDGE_WIDTH_ADDITION + 1
     */
    private final int NODE_SELECTION_RADIUS = (int) (NODE_PAINT_RADIUS * 1.5);
    private final static float dash1[] = {10.0f};

    private final int lineWidth = graphicsStyle.getLineWidth();
    /**
     * The width of the edge when selected, in pixels. The inequality 1 + 2 *
     * EDGE_WIDTH_ADDITION >= EDGE_PAINT_WIDTH must hold.
     */
    private final float EDGE_PAINT_WIDTH = 1.5f * lineWidth;
    /**
     * The amount of space (in pixels) that is added on each side of all edges'
     * bounding boxes, to enable them to paint themselves thicker when they are
     * marked as selected.
     */
    private final int EDGE_WIDTH_ADDITION = (int) Math.floor(Math.max(EDGE_PAINT_WIDTH, 2 * NODE_PAINT_RADIUS) / 2) + 1;
    private final BasicStroke stroke_standard = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
    private final BasicStroke stroke_dashed_slim = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
    private final BasicStroke stroke_dashed_thick = new BasicStroke(EDGE_PAINT_WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
    public final BasicStroke stroke_thick = new BasicStroke(EDGE_PAINT_WIDTH);
    static final Point noOffset = new Point(0, 0);
    private Color transparentForeground;

    private boolean dragged = false;
    private PlanEdge selectedEdge;
    private Point selectedPoint;
    private Point dragOffset;

    /**
     * A list of Edge Data that stores the Edge information in the order that
     * the edges are iterated through by the PlanPolygon iterator.
     */
    private LinkedList<EdgeData> edgeData = new LinkedList<>();
    
    /**
     * A helper variable that is used to prevent an event from being handled a
     * second time. This is made possible through the event pass-back code from
     * {@link JFloor#processMouseEvent(MouseEvent)}
     */
    private MouseEvent lastMouseEventToPassToFloor = null;
    private boolean selected = false;
    private final Color selectedColor = Color.red;

    //############## EDGE RELATED FIELDS ###################
    private static class EdgeData {

        /** The ds.z.Edges that eauch graphical edge representation is connected to. */
        private final PlanEdge edge;
        /** The location of PlanPoint1 of any edge in the <u>coordinate space of the JPolygon.</u> */
        private final Point node1;
        /** The location of PlanPoint2 of any edge in the <u>coordinate space of the JPolygon.</u> */
        private final Point node2;
        /** Indicates the direction in which each edge is drawn. See the internal comments in the
         * {@link #paintComponent(Graphics)} method for further details. */
        private final boolean startDrawingAtNode1;
        /** The selectionPolygons of the edges. */
        private final Polygon selectionPolygon;

        public EdgeData(PlanEdge edge, Point node1, Point node2, boolean startDrawingAtNode1, Polygon selectionPolygon) {
            this.edge = edge;
            this.node1 = node1;
            this.node2 = node2;
            this.startDrawingAtNode1 = startDrawingAtNode1;
            this.selectionPolygon = selectionPolygon;
        }
    }

    /**
     * Creates a new instance of {@code JPolygon}.
     *
     * @param myFloor The {@link JFloor} on which this polygon is displayed
     * @param foreground the border color of the polygon
     */
    public JPolygon(JFloor myFloor, Color foreground) {
        super(foreground);
        this.parentFloor = Objects.requireNonNull(myFloor);

        setOpaque(false);

        // Create a transparent color with which we will fill out the polygon
        // in case it represents an area
        transparentForeground = new Color(getForeground().getColorSpace(), getForeground().getColorComponents(null), 0.1f);

        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }

    
    public void setSelectedEdge(PlanEdge edge) {
        this.selectedEdge = edge;
    }

    public void setSelectedPoint(PlanPoint point) {
        this.selectedPoint = point;
    }

    public final void setDragged(boolean b) {
        dragged = b;
    }

    public final boolean isDragged() {
        return dragged;
    }

    public void setDragOffset(Point dragOffset) {
        this.dragOffset = dragOffset;
    }

    public Point getDragOffset() {
        return dragOffset;
    }

    /**
     * This method returns the ds.z object at the given point. In contrast to
     * findComponentAt it not just checks whether the point is inside the
     * bounding box of the Component, but also whether the point is inside the
     * shape of the {@code Component} (only works for edges / {@link JPolygon}).
     *
     * @param p
     * @return The topmost plan component that was clicked on, e.g. a PlanPoint,
     * a Edge/RoomEdgeA or a PlanPolygon. If no such component can be found null
     * is returned.
     */
    public Object findClickTargetAt(Point p) {
        // Check sub-objects
        for (Component c : getComponents()) {
            Point transPoint = SwingUtilities.convertPoint(this, p, c);

            if (c.contains(transPoint)) //if( c instanceof JPolygon ) {
            {
                if (c instanceof JPolygon) {
                    if (((JPolygon) c).getDrawingPolygon().contains(transPoint)) {
                        return ((JPolygon) c).findClickTargetAt(transPoint);
                    }
                } else {
                    return null;
                }
            }
        }
        // Check edges
        for (EdgeData ed : edgeData) {
            if (ed.selectionPolygon.contains(p)) {
                PlanPoint pointHit = clickHitsPlanPoint(ed, p);
                return pointHit != null ? pointHit : ed.edge;
            }
        }
        // Check if click is in own drawing area (most general option)
        return (drawingPolygon.contains(p)) ? this.myPolygon : null;
    }

    /**
     * Only call this after the component has been added to a container.
     * Otherwise operations like setBounds, which are called herein, will fail.
     *
     * @param p the polygon
     */
    @Override
    public void displayPolygon(PlanPolygon p) {
        if (myPolygon != null) {
            removeAll();
            edgeData.clear();
        }
        myPolygon = p;
        if( p == null ) {
            return;
        }

        // Contains absolute bounds
        Rectangle areabounds = CoordinateTools.translateToScreen(p.bounds());
        // Translate start point only for non-area JPolygons - see below
        areabounds.width += 2 * EDGE_WIDTH_ADDITION;
        areabounds.height += 2 * EDGE_WIDTH_ADDITION;

        // Contains room-relative bounds
        Rectangle bounds = new Rectangle(areabounds);
        if (myPolygon instanceof Area) {
            Rectangle translatedBounds = CoordinateTools.translateToScreen(((Area) myPolygon).getAssociatedRoom().getPolygon().bounds());
            bounds.x -= translatedBounds.x;
            bounds.y -= translatedBounds.y;
        } else {
            bounds.x -= EDGE_WIDTH_ADDITION;
            bounds.y -= EDGE_WIDTH_ADDITION;
        }
        setBounds(bounds);

        // This already copies the polygon
        drawingPolygon = CoordinateTools.translateToScreen(getAWTPolygon());
        // We only want to paint the space within the area polygon. The
        // drawingpolygon already contains the appropriate size to do that,
        // because it comes from the model and has no idea of EDGE_WIDTH_ADDITION
        // But we have to shift the polygon (1) into the coordinate space of the
        // area and (2) by EDGE_WIDTH_ADDITION because we want to paint inside
        // the painted edges, not only inside the JEdge Objects.
        drawingPolygon.translate(-areabounds.x + EDGE_WIDTH_ADDITION, -areabounds.y + EDGE_WIDTH_ADDITION);

        // Display subobjects
        // TODO: Provide better implementation - Do not recreate everything each time
        if (Room.class.isInstance(myPolygon)) {
            Room room = Room.class.cast(myPolygon);

            EnumSet<Areas> areaVisibility = EnumSet.allOf( Areas.class ); //@//GUIOptionManager.getAreaVisibility();

            EnumMap<Areas, Supplier<List<? extends Area>>> areaAccessors = new EnumMap<>(Areas.class);
            areaAccessors.put(Areas.Assignment, room::getAssignmentAreas);
            areaAccessors.put(Areas.Delay, room::getDelayAreas);
            areaAccessors.put(Areas.Evacuation, room::getEvacuationAreas);
            areaAccessors.put(Areas.Save, room::getSaveAreas);
            areaAccessors.put(Areas.Stair, room::getStairAreas);
            areaAccessors.put(Areas.Inaccessible, room::getInaccessibleAreas);
            areaAccessors.put(Areas.Teleportation, room::getTeleportAreas);

            areaVisibility.stream().forEach((areaType) -> {
                for (Area a : areaAccessors.get(areaType).get()) {
                    JPolygon areaPolygon = new JPolygon(parentFloor, graphicsStyle.getColorForArea(areaType));
                    add(areaPolygon);
                    areaPolygon.displayPolygon(a.getPolygon());
                }
            });

            if (areaVisibility.contains(Areas.Inaccessible)) {
                for (Area a : room.getBarriers()) {
                    JPolygon barrierPoly = new JPolygon(parentFloor, graphicsStyle.getWallColor());
                    add(barrierPoly);
                    barrierPoly.displayPolygon(a.getPolygon());
                }
            }
        }

        // Display own edges - This must come after the areas have been created,
        // otherwise the room edges will dominate the area edges.
        for (PlanEdge e : myPolygon.getEdges()) {
            // Add the new EdgeData to our list of EdgeData
            edgeData.add(getEdgeData(e, areabounds));
        }

        // Don't repaint here - Always repaint the whole Floor. This is
        // necessary because otherwise the background will not be cleaned
    }
    
    private EdgeData getEdgeData(PlanEdge e, Rectangle areabounds) {
        Point p1 = CoordinateTools.translateToScreen(e.getSource());
        Point p2 = CoordinateTools.translateToScreen(e.getTarget());

        // Compute coordinates of the PlanPoints within! the JEdge's coordinate space
        Point node1 = new Point(
                (int) p1.getX() - areabounds.x + EDGE_WIDTH_ADDITION,
                (int) p1.getY() - areabounds.y + EDGE_WIDTH_ADDITION);
        Point node2 = new Point(
                (int) p2.getX() - areabounds.x + EDGE_WIDTH_ADDITION,
                (int) p2.getY() - areabounds.y + EDGE_WIDTH_ADDITION);

        // Create the selection shape
        Point pLeft = (node1.x <= node2.x) ? node1 : node2;
        Point pRight = (node1.x <= node2.x) ? node2 : node1;
        Point pTop = (node1.y <= node2.y) ? node1 : node2;

        Polygon selectionPolygon = new Polygon();
        if (pLeft == pTop) {
            // Edge from left top to right bottom
            selectionPolygon.addPoint(pLeft.x - EDGE_WIDTH_ADDITION, pLeft.y - EDGE_WIDTH_ADDITION);
            selectionPolygon.addPoint(pLeft.x + EDGE_WIDTH_ADDITION, pLeft.y - EDGE_WIDTH_ADDITION);
            selectionPolygon.addPoint(pRight.x + EDGE_WIDTH_ADDITION, pRight.y - EDGE_WIDTH_ADDITION);
            selectionPolygon.addPoint(pRight.x + EDGE_WIDTH_ADDITION, pRight.y + EDGE_WIDTH_ADDITION);
            selectionPolygon.addPoint(pRight.x - EDGE_WIDTH_ADDITION, pRight.y + EDGE_WIDTH_ADDITION);
            selectionPolygon.addPoint(pLeft.x - EDGE_WIDTH_ADDITION, pLeft.y + EDGE_WIDTH_ADDITION);
        } else {
            // Edge from left bottom to right top
            selectionPolygon.addPoint(pLeft.x - EDGE_WIDTH_ADDITION, pLeft.y - EDGE_WIDTH_ADDITION);
            selectionPolygon.addPoint(pRight.x - EDGE_WIDTH_ADDITION, pRight.y - EDGE_WIDTH_ADDITION);
            selectionPolygon.addPoint(pRight.x + EDGE_WIDTH_ADDITION, pRight.y - EDGE_WIDTH_ADDITION);
            selectionPolygon.addPoint(pRight.x + EDGE_WIDTH_ADDITION, pRight.y + EDGE_WIDTH_ADDITION);
            selectionPolygon.addPoint(pLeft.x + EDGE_WIDTH_ADDITION, pLeft.y + EDGE_WIDTH_ADDITION);
            selectionPolygon.addPoint(pLeft.x - EDGE_WIDTH_ADDITION, pLeft.y + EDGE_WIDTH_ADDITION);
        }

        // Always start at the leftmost or, if that is not applicable, at
        // the topmost node. Either the topmost or the leftmost node must
        // exist, because both nodes may not have the same coodinates.
        boolean startDrawingAtNode1;
        if (node1.x != node2.x) {
            startDrawingAtNode1 = node1.x < node2.x;
        } else {
            startDrawingAtNode1 = node1.y < node2.y;
        }
        return new EdgeData(e, node1, node2, startDrawingAtNode1, selectionPolygon);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

//        // Guard - May be able to prevent some bizarre exception to occur
//        // (when the number of edgeDatas is not equal to the edge count due
//        // to some unknown reason. This exception is hard to reproduce)
//        if (myPolygon == null || myPolygon.getNumberOfEdges() != edgeData.size()) {
//            SwingUtilities.invokeLater(() -> {
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException ex) {
//                }
//                repaint();
//            });
//            return;
//        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        paint(g2, false);
    }

    public void paint(Graphics2D g2, boolean drag) {
        paint(g2, noOffset, drag);
    }

    /**
     * <p>
     * Draws the polygon on a given graphics context. The exact position of the
     * polygon on the graphics context is controlled using an offset variable.
     * The offset has to be zero, if the {@code JPolygon} is drawn in its own
     * graphics context, because the size of its own context is only the size of
     * the polygon itself.</p>
     * <p>
     * If the polygon is dragged, the original and the dragged copy may be
     * drawn. This is indicated by a variable. If it is dragged and the original
     * copy is drawn, the polygon will be faded according to a static fade
     * parameter. The dragged copy is drawn normally. The drag offset is added
     * to the submitted offset, if the dragged copy is drawn. To be visible, the
     * dragged component has to be drawn in a containing components graphics
     * context. </p>
     *
     * @param g2 the graphics context
     * @param offset the offset by which all points are moved
     * @param draggedCopy decides, whether the original or a dragged copy is
     * drawn
     */
    public void paint(Graphics2D g2, Point offset, boolean draggedCopy) {
        final Point totalOffset = draggedCopy ? new Point(offset.x + dragOffset.x, offset.y + dragOffset.y) : offset;
        // Option flags for Stairs
        boolean lowerPart = false;
        boolean upperPart = false;

        // ### Paint the Edges ###
        Iterator<EdgeData> itEdgeData = edgeData.iterator();
        for (PlanEdge myEdge : in(myPolygon.edgeIterator())) {
            assert itEdgeData.hasNext();
            EdgeData ed = itEdgeData.next();

            // Set various paint options
            Color edgeColor = (myEdge instanceof TeleportEdge) ? graphicsStyle.getColorForArea(Areas.Teleportation) : getForeground();
            if (!isDragged() || draggedCopy) {
                g2.setPaint(edgeColor);
            } else {
                Color baseColor = myEdge instanceof TeleportEdge ? graphicsStyle.getTeleportEdgeColor() : graphicsStyle.getHighlightColor();
                g2.setPaint(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), (int) (0.3 * baseColor.getAlpha())));
            }
            if (myEdge instanceof RoomEdge && ((RoomEdge) myEdge).isPassable()) {
                // Paint dashed line to indicate passability
                if ((selected || myEdge.equals(selectedEdge)) && !draggedCopy) {
                    g2.setStroke(stroke_dashed_thick);
                } else {
                    g2.setStroke(stroke_dashed_slim);
                }
            } else if ((selected || myEdge.equals(selectedEdge)) && !draggedCopy) {
                g2.setPaint(selectedColor);
                g2.setStroke(stroke_thick);
            } else {
                g2.setStroke(stroke_standard);
            }

            // Drawing coordinates for nodes are node1 / node 2
            if (!isDragged() || draggedCopy) {
                g2.fillRect(ed.node1.x + totalOffset.x - NODE_PAINT_RADIUS, ed.node1.y + totalOffset.y - NODE_PAINT_RADIUS, 2 * NODE_PAINT_RADIUS, 2 * NODE_PAINT_RADIUS);
                g2.fillRect(ed.node2.x + totalOffset.x - NODE_PAINT_RADIUS, ed.node2.y + totalOffset.y - NODE_PAINT_RADIUS, 2 * NODE_PAINT_RADIUS, 2 * NODE_PAINT_RADIUS);
            }

            // Consider the case, that there is a passable edge whose target
            // has node1 and node2 in the reversed order (this is absolutely
            // legal since we are using undirected edges). In this case we have
            // to make sure that the edge is always drawn in the same direction
            // because otherwise the line segments of the dashed lines will
            // overlap and form a solid line. Therefore we introduced the
            // field startAtNode1
            if (ed.startDrawingAtNode1) {
                g2.drawLine(ed.node1.x + totalOffset.x, ed.node1.y + totalOffset.y, ed.node2.x + totalOffset.x, ed.node2.y + totalOffset.y);
            } else {
                g2.drawLine(ed.node2.x + totalOffset.x, ed.node2.y + totalOffset.y, ed.node1.x + totalOffset.x, ed.node1.y + totalOffset.y);
            }

            // Set stair options
            if (myPolygon instanceof StairArea) {
                StairArea sme = (StairArea) myPolygon;
                if (myEdge.getSource() == sme.getLowerLevelStart()) {
                    lowerPart = true;
                }
                if (myEdge.getSource() == sme.getLowerLevelEnd()) {
                    lowerPart = false;
                }
                if (myEdge.getSource() == sme.getUpperLevelStart()) {
                    upperPart = true;
                }
                if (myEdge.getSource() == sme.getUpperLevelEnd()) {
                    upperPart = false;
                }

                String toDraw = null;
                if (lowerPart) {
                    toDraw = "L";
                } else if (upperPart) {
                    toDraw = "U";
                }

                if (toDraw != null) {
                    FontMetrics metrics = g2.getFontMetrics();
                    Rectangle nameBounds = metrics.getStringBounds(toDraw, g2).getBounds();
                    Point edgeCenter = new Point((ed.node1.x + ed.node2.x) / 2, (ed.node1.y + ed.node2.y) / 2);
                    Paint oldPaint = g2.getPaint();
                    g2.setPaint(graphicsStyle.getWallColor());
                    g2.drawString(toDraw, edgeCenter.x - nameBounds.width / 2, edgeCenter.y + nameBounds.height / 2);
                    g2.setPaint(oldPaint);
                }
            }
        }

        // ### Paint Polygon-specific stuff like the room name or the area filling ###
        if (Room.class.isInstance(myPolygon) && (!isDragged() || !draggedCopy)) {
            // Paint the name of the room
            Font originalFont = g2.getFont();
            g2.setFont(graphicsStyle.getLabelFont());
            drawName(Room.class.cast(myPolygon).getName(), g2, graphicsStyle.getWallColor());
            g2.setFont(originalFont);
        }

        // If the polygons are of the type area, fill them. This won't work for
        // barriers as they only represent lines.
        if ((myPolygon instanceof Area) && !(myPolygon instanceof Barrier) && (!isDragged() || !draggedCopy)) {
            // Paint the background with the area color
            g2.setPaint(transparentForeground);
            if (drawingPolygon.npoints > 0) {
                g2.fillPolygon(drawingPolygon);
            }
        }

        // Redraw the polygon if it is selected. This will give better
        // if many polygons are visible at the same time.
        if (isSelected() && !(myPolygon instanceof Barrier)
                && (!isDragged() || !draggedCopy)) {
            g2.setPaint(transparentForeground);
            g2.fillPolygon(drawingPolygon);
        }
    }

    // Temporary method that only draws a dragged copy of a selected edge!
    public void paintEdge(Graphics2D g2, Point offset) {
        if (!isDragged()) {
            return;
        }

        final Point totalOffset = new Point(offset.x + dragOffset.x, offset.y + dragOffset.y);
        Iterator<EdgeData> itEdgeData = edgeData.iterator();
        assert itEdgeData.hasNext();
        EdgeData ed = itEdgeData.next();
        while (ed.edge != selectedEdge) {
            ed = itEdgeData.next();
        }
        paintEdge(g2, ed, totalOffset);
    }
    
    private void paintEdge(Graphics2D g2, EdgeData ed, Point totalOffset) {
        PlanEdge myEdge = ed.edge;

        // Set various paint options
        if (!isDragged()) {
            Color edgeColor = myEdge instanceof TeleportEdge ? graphicsStyle.getColorForArea(Areas.Teleportation) : getForeground();
            g2.setPaint(edgeColor);
        } else {
            Color baseColor = myEdge instanceof TeleportEdge ? graphicsStyle.getTeleportEdgeColor() : graphicsStyle.getHighlightColor();
            g2.setPaint(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), (int) (0.3 * baseColor.getAlpha())));
        }
        if (myEdge instanceof RoomEdge && ((RoomEdge) myEdge).isPassable()) {
            // Paint dashed line to indicate passability
            g2.setStroke(stroke_dashed_thick);
        } else {
            g2.setPaint(selectedColor);
            g2.setStroke(stroke_thick);
        }
        // Drawing coordinates for nodes are node1 / node 2
        g2.fillRect(ed.node1.x + totalOffset.x - NODE_PAINT_RADIUS, ed.node1.y + totalOffset.y - NODE_PAINT_RADIUS, 2 * NODE_PAINT_RADIUS, 2 * NODE_PAINT_RADIUS);
        g2.fillRect(ed.node2.x + totalOffset.x - NODE_PAINT_RADIUS, ed.node2.y + totalOffset.y - NODE_PAINT_RADIUS, 2 * NODE_PAINT_RADIUS, 2 * NODE_PAINT_RADIUS);

        // Consider the case, that there is a passable edge whose target
        // has node1 and node2 in the reversed order (this is absolutely
        // legal since we are using undirected edges). In this case we have
        // to make sure that the edge is always drawn in the same direction
        // because otherwise the line segments of the dashed lines will
        // overlap and form a solid line. Therefore we introduced the
        // field startAtNode1
        if (ed.startDrawingAtNode1) {
            g2.drawLine(ed.node1.x + totalOffset.x, ed.node1.y + totalOffset.y, ed.node2.x + totalOffset.x, ed.node2.y + totalOffset.y);
        } else {
            g2.drawLine(ed.node2.x + totalOffset.x, ed.node2.y + totalOffset.y, ed.node1.x + totalOffset.x, ed.node1.y + totalOffset.y);
        }
    }

    /**
     * Indicates whether the {@code JPolygon} is selected or not.
     *
     * @return true if the polygon is selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Determines if the {@code JPolygon} is selected. Other polygons that my be
     * selected are not touched, thus it is possible to select more than one.
     *
     * @param selected the selection state, true if it is selected
     */
    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
        this.selectedEdge = null;
        this.selectedPoint = null;
        repaint();
    }

    /**
     * MouseEvents occur on this component are forwarded to the parent
     * component, if the click does not trigger the PopupMenu of the component.
     *
     * @param e the {@code MouseEvent}
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        // Check the events in the following order (small objects before big objects):
        // 1. Check whether a Point Popup is triggered
        // 2. Check whether an Edge Popup is triggered
        // 3. Check whether a Polygon Popup is triggered
        // 4. Else forward the event to parent object

        // Do not use e.isPopupTrigger() here - Won't work under linux
        if (e.getID() == MouseEvent.MOUSE_RELEASED && e.getButton() == MouseEvent.BUTTON3) {
            if (!lastPosition.equals(e.getLocationOnScreen())) {
                lastPosition = e.getLocationOnScreen();
                selectedUsed = false;
            }

            EdgeData hitEdge = null;
            // First check whether at least one edge was hit by the click
            for (EdgeData ed : edgeData) {
                if (ed.selectionPolygon.contains(e.getPoint())) {
                    hitEdge = ed;
                    break;
                }
            }

            if (hitEdge != null) {
                PlanPoint hitPoint = clickHitsPlanPoint(hitEdge, e.getPoint());
                if (hitPoint == null) {
                    // Show edge popup
                    if (!selectedUsed) {
                        PlanPoint newPoint = new PlanPoint(CoordinateTools.translateToModel(convertPointToFloorCoordinates((Component) e.getSource(), e.getPoint())));
                        parentFloor.getPopups().getEdgePopup().setPopupEdge(hitEdge.edge, newPoint);
                        parentFloor.getPopups().getEdgePopup().show(this, e.getX(), e.getY());
                    }
                    selectedUsed = isSelected();
                } else {
                    // Show point popup
                    if (!selectedUsed) {
                        parentFloor.getPopups().getPointPopup().setPopupPoint(hitEdge.edge, hitPoint);
                        parentFloor.getPopups().getPointPopup().show(this, e.getX(), e.getY());
                    }
                    selectedUsed = isSelected();
                }
            } else if (Room.class.isInstance(myPolygon) && drawingPolygon.contains(e.getPoint())) {
                if (!selectedUsed) {
                    parentFloor.getPopups().getPolygonPopup().setPopupPolygon(myPolygon);
                    parentFloor.getPopups().getPolygonPopup().show(this, e.getX(), e.getY());
                }
                selectedUsed = isSelected();
            }
        }

        if (!e.equals(lastMouseEventToPassToFloor) && getParent() != null) {
            // Keep the polygon as the source and translate the coordinates
            // Do not use SwingUtilities.convertMouseEvent here! - Timon
            Point translated = SwingUtilities.convertPoint(this, e.getPoint(), getParent());
            getParent().dispatchEvent(new MouseEvent((Component) e.getSource(), e.getID(),
                    e.getWhen(), e.getModifiers(), translated.x, translated.y, e.getClickCount(),
                    e.isPopupTrigger()));

            lastMouseEventToPassToFloor = e;
        }
    }

    /**
     * This is a helper method for other GUI objects who need to transform
     * points that are given in their own coordinate space into the coordinate
     * space of the Floor.
     * @param source The Component in whose coordinate space the Point "toConvert"
     * is specified. It must be an object which is located directly or indirectly
     * upon the JEditorPanel's JFloor object.
     * @param toConvert The point to convert
     * @return The same point as "toConvert", but relative to the surrounding
     * JFloor object.
     */
    public Point convertPointToFloorCoordinates( Component source, Point toConvert ) {
        return SwingUtilities.convertPoint(source, toConvert, parentFloor );
    }

    /**
     * MouseEvents occurring on this component are also forwarded to the parent
     * component.
     */
    @Override
    protected void processMouseMotionEvent(MouseEvent e) {
        if (getParent() != null) {
            // Keep the polygon as the source and translate the coordinates
            // Do not use SwingUtilities.convertMouseEvent here! - Timon
            Point translated = SwingUtilities.convertPoint(this, e.getPoint(), getParent());
            getParent().dispatchEvent(new MouseEvent((Component) e.getSource(), e.getID(),
                    e.getWhen(), e.getModifiers(), translated.x, translated.y, e.getClickCount(),
                    e.isPopupTrigger()));
        }
    }

    /**
     * Determines whether the given {@code MouseEvent} will lead to a popup menu
     * when dispatched to this JPolygon.
     *
     * @param e the mouse event that occurred
     * @return {@code true} if the popup is displayed, {@code false} otherwise
     */
    public boolean isPopupTrigger(MouseEvent e) {
        // Do not use e.isPopupTrigger() here - Won't work under linux
        if (e.getID() == MouseEvent.MOUSE_RELEASED && e.getButton() == MouseEvent.BUTTON3) {
            EdgeData hitEdge = null;
            // First check whether at least one edge was hit by the click
            for (EdgeData ed : edgeData) {
                if (ed.selectionPolygon.contains(e.getPoint())) {
                    hitEdge = ed;
                    break;
                }
            }

            return (hitEdge != null) || (Room.class.isInstance(myPolygon) && drawingPolygon.contains(e.getPoint()));
        } else {
            return false;
        }
    }

    /**
     * This is a helper method, to determine whether the user clicked on a
     * planPoint (the nodes of an edge) and not only into the middle of an edge.
     *
     * @param ed The edge on which the user clicked
     * @param click The click coordinates <u>in the coordinate space of the
     * JPolygon</u>
     * @return The {@link de.zet_evakuierung.model.PlanPoint} that the user
     * clicked on, if such a point exists, {@code null} if no point was hit.
     */
    private PlanPoint clickHitsPlanPoint(EdgeData ed, Point click) {
        if (ed.node1.distance(click) <= NODE_SELECTION_RADIUS) {
            return ed.edge.getSource();
        } else if (ed.node2.distance(click) <= NODE_SELECTION_RADIUS) {
            return ed.edge.getTarget();
        } else {
            return null;
        }
    }

    /** Prohibits serialization. */
    private synchronized void writeObject(java.io.ObjectOutputStream s) throws IOException {
        throw new UnsupportedOperationException("Serialization not supported");
    }
    
    /** Prohibits serialization. */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Serialization not supported");
    }
}
