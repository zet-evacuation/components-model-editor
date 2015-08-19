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

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;
import zet.gui.main.tabs.base.JPolygon;
import static zet.gui.main.tabs.editor.floor.CustomFloorSelectionDrawer.drawRect;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class CustomFloorCreateRectangleDrawer implements CustomFloorDrawer {
    private FloorClickCreateRectangleHandler handler;
    private final JFloor floor;

    public CustomFloorCreateRectangleDrawer(FloorClickCreateRectangleHandler handler, JFloor floor) {
        this.handler = Objects.requireNonNull(handler);
        this.floor = Objects.requireNonNull(floor);
    }

    //@// This is temporary
    private JPolygon jPolygon;

    @Override
    public void drawCustom(Graphics2D g2) {
        if (jPolygon == null) {
            jPolygon = new JPolygon(floor, Color.red) {
            };
        }
        drawHelpRectangle(g2);
    }

    private void drawHelpRectangle(Graphics2D g2) {
        if (handler.isCreationActive()) {
            Color t = handler.getZetObjectType().getEditorColor();
            g2.setPaint(t);
            g2.setStroke(jPolygon.stroke_thick);
            drawRect(g2, handler.getLastClick(), handler.getCurrentPosition());
        }
    }
}
