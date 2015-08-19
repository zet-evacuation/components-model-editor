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

package zet.gui.main.tabs.editor.control;

import de.zet_evakuierung.model.Floor;
import de.zet_evakuierung.model.Named;
import de.zet_evakuierung.model.Room;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;

/**
 * View model gaining access to necessary information to display a floor.
 * @author Jan-Philipp Kappmeier
 */
public class FloorViewModel implements Iterable<Room>, Named {

    final Floor floor;

    public FloorViewModel(Floor floor) {
        this.floor = floor;
    }
    
    /**
     * Returns a list of all rooms that lie on the floor.
     * @return a list of all rooms that lie on the floor
     */
    public List<Room> getRooms() {
        return floor.getRooms();
    }

    /**
     * The name of the floor.
     * @return 
     */
    public String getName() {
        return floor.getName();
    }

    /**
     * The number of rooms that lie on the floor.
     * @return 
     */
    public int roomCount() {
        return floor.roomCount();
    }
    
    public Rectangle getLocation() {
        return floor.getLocation();
    }

    @Override
    public Iterator<Room> iterator() {
        return getRooms().iterator();
    }
    
}
