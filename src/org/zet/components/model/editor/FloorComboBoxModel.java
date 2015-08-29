/* zet evacuation tool copyright (c) 2007-14 zet evacuation team
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

import javax.swing.DefaultComboBoxModel;
import org.zet.components.model.editor.floor.FloorViewModel;

/**
 * An immutable combo mox model displaying floors.
 * @author Jan-Philipp Kappmeier
 */
public class FloorComboBoxModel extends DefaultComboBoxModel<FloorViewModel> {
    public FloorComboBoxModel(EditViewModel model) {
        super(model.getFloors().toArray(new FloorViewModel[model.getFloorCount()]));
        setSelectedItem(model.getCurrentFloor());
    }
}
