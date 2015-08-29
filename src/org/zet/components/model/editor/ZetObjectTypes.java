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

package org.zet.components.model.editor;

import org.zet.components.model.editor.floor.base.DefaultGraphicsStyle;
import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.Barrier;
import de.zet_evakuierung.model.DelayArea;
import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.InaccessibleArea;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.SaveArea;
import de.zet_evakuierung.model.StairArea;
import de.zet_evakuierung.model.TeleportArea;
import java.awt.Color;
import java.util.Objects;


/**
 *
 * @author Jan-Philipp Kappmeier
 */
public enum ZetObjectTypes {
    // todo: remove duplication with graphics style and here
    Room( Room.class, new DefaultGraphicsStyle().getWallColor() ),
    Barrier( Barrier.class, new DefaultGraphicsStyle().getWallColor() ),
    Inaccessible( InaccessibleArea.class, new DefaultGraphicsStyle().getColorForArea(Areas.Inaccessible) ),
    Assignment( AssignmentArea.class, new DefaultGraphicsStyle().getColorForArea(Areas.Assignment) ),
    Delay( DelayArea.class, new DefaultGraphicsStyle().getColorForArea(Areas.Delay) ),
    Stair( StairArea.class, new DefaultGraphicsStyle().getColorForArea(Areas.Stair) ),
    Save( SaveArea.class, new DefaultGraphicsStyle().getColorForArea(Areas.Save) ),
    Evacuation( EvacuationArea.class, new DefaultGraphicsStyle().getColorForArea(Areas.Evacuation) ),
    Teleport( TeleportArea.class, new DefaultGraphicsStyle().getColorForArea(Areas.Teleportation) );

    private Class<?>objectClass;
    private Color editColor;

    private ZetObjectTypes( Class<?> c, Color color ) {
        this.objectClass = Objects.requireNonNull( c );
        this.editColor = Objects.requireNonNull( color );
    }

    public Color getEditorColor() {
        return editColor;
    }

    public boolean isArea() {
        return this == Room ? false : true;
    }

    public Class<?> getObjectClass() {
        return objectClass;
    }


}
