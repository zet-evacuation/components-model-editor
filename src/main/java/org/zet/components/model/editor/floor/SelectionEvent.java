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

import de.zet_evakuierung.model.PlanEdge;
import java.util.EventObject;
import org.zet.components.model.editor.polygon.JPolygon;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class SelectionEvent extends EventObject {
    private final JPolygon selectedPolygon;
    private final PlanEdge edge;
    public SelectionEvent(Object source) {
        super(source);
        selectedPolygon = null;
        edge = null;
    }

    public SelectionEvent(Object source, JPolygon polygon) {
        super(source);
        this.selectedPolygon = polygon;
        edge = null;
    }

    public SelectionEvent(Object source, JPolygon polygon, PlanEdge edge) {
        super(source);
        this.selectedPolygon = polygon;
        this.edge = edge;
    }

    public JPolygon getSelectedPolygon() {
        return selectedPolygon;
    }

    public PlanEdge getEdge() {
        return edge;
    }
}
