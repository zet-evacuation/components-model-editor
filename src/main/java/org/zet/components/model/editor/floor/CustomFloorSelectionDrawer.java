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

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Objects;
import org.zet.components.model.editor.polygon.JPolygon;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class CustomFloorSelectionDrawer implements CustomFloorDrawer {
    private FloorClickSelectionHandler handler;
    private final JFloor floor;

    public CustomFloorSelectionDrawer(FloorClickSelectionHandler handler, JFloor floor) {
        this.handler = Objects.requireNonNull(handler);
        this.floor = Objects.requireNonNull(floor);
    }

    @Override
    public void drawCustom(Graphics2D g2) {
        drawSelectionRectangle(g2);
        drawSelection(g2);

    }

    /** The standard stroke. */
    private final static BasicStroke stroke_standard = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
    /** The stroke used for painting the selection rectangle. */
    private final static BasicStroke selection_stroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 3.0f, new float[]{4.0f, 2.0f}, 0.0f);
    private void drawSelectionRectangle(Graphics2D g2) {
        if (handler.isMouseSelecting()) {
            g2.setPaint(floor.graphicsStyle.getDragNodeColor());
            g2.setStroke(selection_stroke);
            // No negative width/height allowed here, so work around it with Math
            drawRect(g2, handler.getLastClick(), handler.getCurrentPosition());
            g2.setStroke(stroke_standard);
        }
    }
    
    public static void drawRect(Graphics2D g2, Point corner1, Point corner2 ) {
        g2.drawRect(Math.min(corner1.x, corner2.x),
                Math.min(corner1.y, corner2.y),
                Math.abs(corner2.x - corner1.x),
                Math.abs(corner2.y - corner1.y));
    }

    private void drawSelection(Graphics2D g2) {
        drawSelectedPolygons(g2);
        drawSelectedEdge(g2);
    }

    private void drawSelectedPolygons(Graphics2D g2) {
        for (JPolygon poly : floor.getSelectedPolygons()) {
            if (poly.isDragged()) {
                if (poly.getParent() == floor) {
                    poly.paint(g2, poly.getLocation(), true);
                } else {
                    poly.paint(g2, new Point(poly.getLocation().x + poly.getParent().getLocation().x,
                            poly.getLocation().y + poly.getParent().getLocation().y), true);
                }
            }
        }
    }

    private void drawSelectedEdge(Graphics2D g2) {
        if (floor.getEdgePolygon() != null) {
            System.out.println("Polygon with moving edge");
            JPolygon poly = floor.getEdgePolygon();
            if (poly.getParent() == floor) {
                poly.paintEdge(g2, poly.getLocation());
            } else {
                poly.paintEdge(g2, new Point(poly.getLocation().x + poly.getParent().getLocation().x, poly.getLocation().y + poly.getParent().getLocation().y));
            }
        }
    }
}
