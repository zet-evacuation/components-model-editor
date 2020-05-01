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

import de.zet_evakuierung.model.AreaType;
import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.Barrier;
import de.zet_evakuierung.model.DelayArea;
import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.InaccessibleArea;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.SaveArea;
import de.zet_evakuierung.model.StairArea;
import de.zet_evakuierung.model.TeleportArea;
import java.util.Objects;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public enum ZetObjectTypes {
    // todo: remove duplication with graphics style and here
    Room( Room.class, null ),
    Barrier( Barrier.class, AreaType.Inaccessible ),
    Inaccessible( InaccessibleArea.class, AreaType.Inaccessible),
    Assignment( AssignmentArea.class, AreaType.Assignment),
    Delay( DelayArea.class, AreaType.Delay),
    Stair( StairArea.class, AreaType.Stair),
    Save( SaveArea.class, AreaType.Save),
    Evacuation( EvacuationArea.class, AreaType.Evacuation),
    Teleport( TeleportArea.class, AreaType.Teleport);

    private final Class<?>objectClass;
    public final AreaType area;

    private ZetObjectTypes( Class<?> c, AreaType area ) {
        this.objectClass = Objects.requireNonNull( c );
        this.area = area;
    }

    public boolean isArea() {
        return this != Room;
    }

    public Class<?> getObjectClass() {
        return objectClass;
    }
}
