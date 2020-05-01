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

package org.zet.components.model.editor.selectors;

import de.zet_evakuierung.model.Named;
import java.awt.Component;
import javax.swing.JList;

/**
 * Displays the name of a named object.
 * @author Jan-Philipp Kappmeier
 * @param <U>
 */
public class NamedComboBoxRenderer<U extends Named> extends ComboBoxRenderer<U> {

    @Override
    public Component getListCellRendererComponent(JList<? extends U> list, U value,
            int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value != null) {
            setText(value.getName());
        } else {
            setText("");
        }
        return this;
    }
}
