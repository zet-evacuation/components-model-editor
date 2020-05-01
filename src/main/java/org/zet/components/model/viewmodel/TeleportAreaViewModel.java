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
import de.zet_evakuierung.model.TeleportArea;
import java.util.Collections;
import java.util.List;

/**
 * @author Jan-Philipp Kappmeier
 */
public interface TeleportAreaViewModel {
    
    default List<EvacuationArea> getEvacuationAreas() {
        return Collections.emptyList();
    }

    default List<TeleportArea> getTeleportAreas() {
        return Collections.emptyList();

    }

    default public String getName() {
        return "TeleportArea";
    }

    default EvacuationArea getExitArea() {
        return new EvacuationArea(null, 1, "Exit");
    }

    default TeleportArea getTargetArea() {
        return new TeleportArea(null);
    }
}
