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

package zet.gui.components.model;

import java.util.List;
import java.util.Objects;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <U>
 */
public class ListModel<U> extends AbstractListModel<U> implements ComboBoxModel<U> {

    /** The list of available floors. */
    List<U> floors;
    /** The current floor. */
    U current;

    /**
     * The elements and the currently selected element. The curren element can be {@code null}.
     * @param elements the elements in the combo box
     * @param current the currently selected
     */
    public ListModel(List<U> elements, U current) {
        this.floors = Objects.requireNonNull(elements);
        this.current = current;
    }

    @Override
    public int getSize() {
        return floors.size();
    }

    @Override
    public U getElementAt(int index) {
        return floors.get(index);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        current = (U) anItem;
    }

    @Override
    public U getSelectedItem() {
        return current;
    }
}
