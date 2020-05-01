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
package org.zet.components.model.editor.panel;

import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.viewmodel.AbstractInformationControl;
import org.zet.components.model.viewmodel.RoomControl;
import org.zet.components.model.viewmodel.RoomViewModel;
import org.zet.components.model.viewmodel.RoomViewModelImpl;

/**
 * Updates the displayed view model in the displayed panel and ensures that the corresponding model in the control
 * is synchrounous.
 * @author Jan-Philipp Kappmeier
 */
public class RoomInformationPanelControl extends AbstractInformationControl<JRoomInformationPanel,
        RoomControl, Room, RoomViewModel> {
    private final ZControl zcontrol;

    private RoomInformationPanelControl(JRoomInformationPanel view, RoomControl control, ZControl zcontrol) {
        super(view, control);
        this.zcontrol = zcontrol;
    }
    
    public static RoomInformationPanelControl create(ZControl zcontrol) {
        RoomControl result = new RoomControl(zcontrol);
        JRoomInformationPanel panel = generateView(result);
        return new RoomInformationPanelControl(panel, result, zcontrol);
    }

    private static JRoomInformationPanel generateView(RoomControl control) {
        return new JRoomInformationPanel(new RoomViewModel() {
        }, control);
    }

    @Override
    protected RoomViewModel getViewModel(Room m) {
        return new RoomViewModelImpl(m, zcontrol.getProject());
    }
}
