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

package zet.gui.main.tabs.editor.floor;

import org.zetool.common.util.SelectedElements;
import de.zet_evakuierung.model.PlanEdge;
import zet.gui.main.tabs.base.JPolygon;


/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class SelectedFloorElements extends SelectedElements<JPolygon> {
	PlanEdge selectedEdge;
	JPolygon selectedElementPolygon;

	void selectEdge( JPolygon toSelect, PlanEdge edge ) {
		clear();
		selectedEdge = edge;
		selectedElementPolygon = toSelect;
		toSelect.setSelectedEdge( edge );
		super.setChanged();
		super.notifyObservers( toSelect );
	}

	public PlanEdge getSelectedEdge() {
		return selectedEdge;
	}

	@Override
	public void select( JPolygon toSelect ) {
		clearEdgeInternal();
		super.select( toSelect );
	}

	@Override
	public void clear() {
		clearEdgeInternal();
		super.clear();
	}

	private void clearEdgeInternal() {
		if( selectedElementPolygon != null )
			selectedElementPolygon.setSelectedEdge( null );
		selectedElementPolygon = null;
		selectedEdge = null;

	}

  public JPolygon getSelectedElementPolygon() {
    return selectedElementPolygon;
  }
}
