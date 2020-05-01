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

import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.viewmodel.AbstractInformationControl;
import org.zet.components.model.viewmodel.DefaultPanelControl;

/**
 * Updates the displayed view model in the displayed panel and ensures that the corresponding model in the control
 * is synchrounous.
 * @author Jan-Philipp Kappmeier
 */
public class DefaultAreaInformationPanelControl extends AbstractInformationControl<JDefaultInformationPanel,
        DefaultPanelControl, Void, Void> {

    private DefaultAreaInformationPanelControl(JDefaultInformationPanel view, DefaultPanelControl control) {
        super(view, control);
    }
    
    public static DefaultAreaInformationPanelControl create(ZControl zcontrol) {
        DefaultPanelControl result = new DefaultPanelControl(zcontrol);
        JDefaultInformationPanel panel = generateView(result);
        return new DefaultAreaInformationPanelControl(panel, result);
    }

    private static JDefaultInformationPanel generateView(DefaultPanelControl control) {
        return new JDefaultInformationPanel(control);
    }

    @Override
    protected Void getViewModel(Void m) {
        return null;
    }
}
