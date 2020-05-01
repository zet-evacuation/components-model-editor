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

import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.viewmodel.AbstractInformationControl;
import org.zet.components.model.viewmodel.EvacuationAreaControl;
import org.zet.components.model.viewmodel.EvacuationAreaViewModel;
import org.zet.components.model.viewmodel.EvacuationAreaViewModelImpl;

/**
 * Updates the displayed view model in the displayed panel and ensures that the corresponding model in the control
 * is synchrounous.
 * @author Jan-Philipp Kappmeier
 */
public class EvacuationAreaInformationPanelControl extends AbstractInformationControl<JEvacuationAreaInformationPanel,
        EvacuationAreaControl, EvacuationArea, EvacuationAreaViewModel> {
    private final ZControl zcontrol;

    private EvacuationAreaInformationPanelControl(JEvacuationAreaInformationPanel view, EvacuationAreaControl control, ZControl zcontrol) {
        super(view, control);
        this.zcontrol = zcontrol;
    }
    
    public static EvacuationAreaInformationPanelControl create(ZControl zcontrol) {
        EvacuationAreaControl result = new EvacuationAreaControl(zcontrol);
        JEvacuationAreaInformationPanel panel = generateView(result);
        return new EvacuationAreaInformationPanelControl(panel, result, zcontrol);
    }

    private static JEvacuationAreaInformationPanel generateView(EvacuationAreaControl control) {
        return new JEvacuationAreaInformationPanel(control);
    }

    @Override
    protected EvacuationAreaViewModel getViewModel(EvacuationArea m) {
        return new EvacuationAreaViewModelImpl(m, zcontrol.getProject());
    }
}
