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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package org.zet.components.model.editor.floor;

import java.awt.Component;
import java.awt.Point;
import java.util.List;
import org.zet.components.model.editor.polygon.JPolygon;

/**
 * A default floor click hander. The handler enables context popup menus for floor components by default.
 *
 * @author Jan-Philipp Kappmeier
 */

public interface FloorClickHandler {

    default void mouseDown(Point p, List<JPolygon> elements) {
    }

    default void mouseUp(Point p, List<Component> components) {
    }

    default void doubleClick(Point p, List<JPolygon> elements) {
    }
    
    default void mouseMove(Point p) {
    }
    
    default void rightClick() {
    }
    
    public Point getCurrentPosition();
}
