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

package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.ZControl;
import java.awt.event.ActionEvent;

/**
 * This pop-up listener is responsible for handling menu events
 *
 * @author Timon Kelter, Jan-Philipp Kappmeier
 */
public class PointListener implements ChangeListener<PointChangeEvent> {

    /** The control class for changing the z data structure. */
    ZControl control;

    public PointListener(ZControl projectControl) {
        this.control = projectControl;
    }

    /**
     * This method contains the event code that is executed when certain action commands (defined at the menu creation)
     * are invoked.
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void changed(PointChangeEvent c) {
        if (c.getChangeType() != PointChangeEvent.PointChange.Delete) {
            throw new AssertionError("Unsupported change type: " + c.getChangeType());
        }
        c.perform(control);
    }
}
