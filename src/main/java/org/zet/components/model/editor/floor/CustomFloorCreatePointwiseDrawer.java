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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Objects;
import org.zet.components.model.editor.polygon.JPolygon;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class CustomFloorCreatePointwiseDrawer implements CustomFloorDrawer {
    private FloorClickCreatePointwiseHandler handler;
    private final JFloor floor;

    public CustomFloorCreatePointwiseDrawer(FloorClickCreatePointwiseHandler handler, JFloor floor) {
        this.handler = Objects.requireNonNull(handler);
        this.floor = Objects.requireNonNull(floor);
    }

    //@// This is temporary
    private JPolygon jPolygon;

    @Override
    public void drawCustom(Graphics2D g2) {
        if (jPolygon == null) {
            jPolygon = new JPolygon(Color.red) {
            };
        }
        drawHelpLine(g2);
    }

    private void drawHelpLine(Graphics2D g2) {
        // If in PolygonCreationMode, draw help-line
        if (handler.isCreationStarted() && handler.getLastClick() != null) {
            Point p1 = handler.getLastClick();
            Point p2 = handler.getCurrentPosition();
            Color t = CustomFloorDrawer.colorForType(handler.getZetObjectType(), floor);
            g2.setPaint(t);
            g2.setStroke(jPolygon.stroke_thick);
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
    
}
