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
package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.DelayArea;
import de.zet_evakuierung.model.ZControl;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class DelayAreaControl extends AbstractControl<DelayArea, DelayAreaViewModel> {

    public DelayAreaControl(ZControl zcontrol) {
        super(zcontrol);
    }

    public void setType(DelayArea.DelayType type) {
        zcontrol.setDelayType(model, type);
    }

    public void setSpeedFactor(double speedFactor) {
        zcontrol.setDelaySpeedFactor(model, Math.max( 0, Math.min( 1, speedFactor)) );
    }

    public void setDefaultType() {
        double defaultSpeed = model.getDelayType().defaultSpeedFactor;
        zcontrol.setDelaySpeedFactor(model, defaultSpeed);
    }
}
