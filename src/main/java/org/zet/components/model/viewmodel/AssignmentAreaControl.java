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

import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.AssignmentType;
import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.ZControl;

/**
 * A control class for evacuation areas.
 * @author Jan-Philipp Kappmeier
 */
public class AssignmentAreaControl extends AbstractControl<AssignmentArea, AssignmentAreaViewModel> {

    public AssignmentAreaControl(ZControl control) {
        super(control);
    }

    public void setAssignmentType(AssignmentType assignmentType) {
        model.setAssignmentType(assignmentType);
        System.err.println("We should change the assignment type!");
   }

    public void setStandardEvacuees() {
        int standardEvacuees = Math.min(viewModel.getAssignmentType().getDefaultEvacuees(), viewModel.getMaxEvacuees());
        System.out.println("Setting standard number of evacuees of " + standardEvacuees);
        model.setEvacuees(standardEvacuees);
    }
    
    /**
     * Sets the amount of evacuues if possible. To large amounts are set to maximum value and negative to 0.
     * @param evacuees 
     */
    public void setEvacuees(int evacuees) {
        final int actualEvacuees = Math.max( 0, Math.min(viewModel.getMaxEvacuees(), evacuees));
        System.out.println("Changing evacuees to " + actualEvacuees);
        model.setEvacuees(actualEvacuees);
    }

    public void setPreferredExit(EvacuationArea evacuationArea) {
        if (evacuationArea != null) {
            System.out.println("Set preferred exit to " + evacuationArea);
            model.setExitArea(evacuationArea);
        }
    }
}
