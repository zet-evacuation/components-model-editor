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

import de.zet_evakuierung.model.Project;
import de.zet_evakuierung.model.Room;

/**
 * The assignment area view model makes available all information necessary to display an assignment area.
 * @author Jan-Philipp Kappmeier
 */
public class RoomViewModelImpl extends AbstractViewModel<Room> implements RoomViewModel {
    public RoomViewModelImpl(Room r, Project p) {
        super(r);
    }

    @Override
    public String getName() {
        return model.getName();
    }

    @Override
    public double areaMeter() {
        return Math.round(model.getPolygon().areaMeter() * 100) / 100.0;
    }
    
}
