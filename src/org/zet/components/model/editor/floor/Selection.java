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

package org.zet.components.model.editor.floor;

import de.zet_evakuierung.model.PlanEdge;
import java.util.Collections;
import java.util.List;
import org.zet.components.model.editor.floor.base.JPolygon;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class Selection {

    //@// this class probably is the same as selectedfloorelements, combine?
    final SelectedFloorElements selection = new SelectedFloorElements();
    JPolygon currentEditing;

    /**
     * Efficiently De-selects all select polygons on the screen
     */
    void clearSelection() {
        selection.clear();
        currentEditing = null;
    }

    void selectPolygon(JPolygon toSelect) {
        selection.select(toSelect);
        currentEditing = selection.getSelected();
    }

    List<JPolygon> getSelectedPolygons() {
        return selection.getSelectedList();
    }

    /**
     * Selectes a single edge in a given polygon.
     *
     * @param toSelect
     * @param edge
     */
    void selectEdge(JPolygon toSelect, PlanEdge edge) {
        selection.selectEdge(toSelect, edge);
    }

    List<PlanEdge> getSelectedEdge() {
        return Collections.singletonList(selection.getSelectedEdge());
    }
    
    void addPolygon(List<JPolygon> toAdd) {
        selection.add(toAdd);
    }

    /**
     * Returns the polygon belonging to the selected edge.
     * @return the polygon belonging to the selected edge
     */
    JPolygon getEdgePolygon() {
        return selection.getSelectedElementPolygon();
    }

    boolean isClear() {
        return selection.getSelectedEdge() == null && selection.getSelected() == null;
    }
}
