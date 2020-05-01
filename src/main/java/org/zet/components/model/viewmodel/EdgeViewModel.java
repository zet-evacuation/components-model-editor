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
package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.EvacuationArea;

/**
 * @author Jan-Philipp Kappmeier
 */
public interface EdgeViewModel {

    public enum EdgeType {
        EXIT(true), FLOOR_PASSAGE(true), PASSAGE(true), WALL(false), AREA_BOUNDARY(false);
        private final boolean passable;

        private EdgeType(boolean passable) {
            this.passable = passable;
        }
        
        public boolean isPassable() {
            return passable;
        }
    }
    
    public enum EdgeOrientation {
        Horizontal, Vertical, Oblique;
    }

    default public EdgeType getEdgeType() {
        return EdgeType.WALL;
    }

    default public double getLength() {
        return 0;
    }

    default public EdgeOrientation getOrientation() {
        return EdgeOrientation.Oblique;
    }
    
    default public EvacuationArea getAssociatedExit() {
        return new EvacuationArea(null, 1, "DummyExit");
    }

}
