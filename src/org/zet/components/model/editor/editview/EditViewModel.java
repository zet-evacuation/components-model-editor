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

package org.zet.components.model.editor.editview;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.zet.components.model.editor.floor.FloorViewModel;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class EditViewModel {
    private final FloorViewModel floorViewModel;
    List<FloorViewModel> floors;

    public EditViewModel(List<FloorViewModel> floors, int currentFloorIndex) {
        floorViewModel = floors.get(currentFloorIndex);
        this.floors = new LinkedList<>(floors);
    }
    
    public List<FloorViewModel> getFloors() {
        return Collections.unmodifiableList(floors);
    }
    
    public FloorViewModel getCurrentFloor() {
        return floorViewModel;
    }

    public int getFloorCount() {
        return floors.size();
    }
}
