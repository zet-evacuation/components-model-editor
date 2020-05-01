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

import de.zet_evakuierung.model.Floor;
import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.editor.floor.FloorControl;
import org.zet.components.model.editor.floor.FloorViewModel;
import org.zet.components.model.viewmodel.AbstractInformationControl;

/**
 * Updates the displayed view model in the displayed panel and ensures that the corresponding model in the control
 * is synchrounous.
 * @author Jan-Philipp Kappmeier
 */
public class FloorInformationPanelControl extends AbstractInformationControl<JFloorInformationPanel,
        FloorControl, Floor, FloorViewModel> {
    private final ZControl zcontrol;

    private FloorInformationPanelControl(JFloorInformationPanel view, FloorControl control, ZControl zcontrol) {
        super(view, control);
        this.zcontrol = zcontrol;
    }
    
    public static FloorInformationPanelControl create(ZControl zcontrol, FloorControl fc) {
        //FloorControl result = new FloorControl(zcontrol);
        JFloorInformationPanel panel = generateView(fc);
        return new FloorInformationPanelControl(panel, fc, zcontrol);
    }

    private static JFloorInformationPanel generateView(FloorControl control) {
        return new JFloorInformationPanel(control);
    }

    @Override
    protected FloorViewModel getViewModel(Floor m) {
        return new FloorViewModel(m, zcontrol.getProject().getBuildingPlan());
    }
}
