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

import java.awt.Component;
import java.awt.Point;
import java.util.List;
import org.zet.components.model.editor.polygon.JPolygon;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class PostActionHandler implements FloorClickHandler {

    public boolean isActive() {
        return false;
    }
    JPolygon created;
    JFloor floor;

    public void activate() {
        
    }
    
    public void setCreated(JPolygon created) {
        this.created = created;
    }
    
    protected JPolygon getCreated() {
        return created;
    }

    public void setFloor(JFloor floor) {
        this.floor = floor;
    }
    
    public JFloor getFloor() {
        return floor;
    }

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
        
    }

    @Override
    public void rightClick() {
        
    }

    @Override
    public Point getCurrentPosition() {
        throw new UnsupportedOperationException("Not supported.");
    }
    
}
