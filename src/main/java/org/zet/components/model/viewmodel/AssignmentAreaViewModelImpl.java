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
import de.zet_evakuierung.model.Project;
import java.util.List;

/**
 * The assignment area view model makes available all information necessary to display an assignment area.
 * @author Jan-Philipp Kappmeier
 */
public class AssignmentAreaViewModelImpl extends AbstractViewModel<AssignmentArea> implements AssignmentAreaViewModel {
    private final Project project;
    
    public AssignmentAreaViewModelImpl(AssignmentArea a, Project p) {
        super(a);
        this.project = p;
    }

    @Override
    public AssignmentType getAssignmentType() {
        return model.getAssignmentType();
    }

    @Override
    public int getMaxEvacuees() {
        return model.getMaxEvacuees();
    }

    @Override
    public double areaMeter() {
        return model.areaMeter();
    }

    @Override
    public int getEvacuees() {
        return model.getEvacuees();
    }

    @Override
    public EvacuationArea getExitArea() {
        return model.getExitArea();
    }

     @Override
    public List<AssignmentType> getAssignmentTypes() {
        return project.getCurrentAssignment().getAssignmentTypes();
    }

    @Override
    public boolean isRastered() {
        return project.getBuildingPlan().isRastered();
    }

    @Override
    public List<EvacuationArea> getEvacuationAreas() {
        return project.getBuildingPlan().getEvacuationAreas();
    }
}
