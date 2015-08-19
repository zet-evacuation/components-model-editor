/* zet evacuation tool copyright (c) 2007-15 zet evacuation team
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

package zet.gui.main.tabs.editor.floor;

import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.StairArea;
import de.zet_evakuierung.model.exception.StairAreaBoundaryException;
import java.awt.Component;
import java.awt.Point;
import java.util.List;
import javax.swing.SwingUtilities;
import zet.gui.main.tabs.base.JPolygon;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class PostActionStairHandler extends PostActionHandler {

    private StairStates stairState = StairStates.Done;

    private enum StairStates {

        Lower,
        Upper,
        Done;
    }

    @Override
    public boolean isActive() {
        return stairState != StairStates.Done;
    }

    @Override
    public void activate() {
        System.out.println("Waiting for Lower and Upper side" );
        stairState = StairStates.Lower;
    }

    @Override
    public void setCreated(JPolygon created) {
        if (isActive()) {
            throw new IllegalStateException("Cannot set another new polygon when old is not finished!");
        }
        super.setCreated(created);
    }

    @Override
    public void mouseUp(Point p, List<Component> components) {
        if (stairState == StairStates.Lower || stairState == StairStates.Upper) {
            System.out.println("Trying to add a lower side");
            JPolygon stair = getCreated();
            if (stair == null) {
                throw new IllegalStateException("STAIR is null");
            }
            if (!(stair.getPlanPolygon() instanceof StairArea)) {
                throw new AssertionError("No stair, but instead it is of type " + stair.getPlanPolygon().getClass());
            }

            Object obj = stair.findClickTargetAt(SwingUtilities.convertPoint(getFloor(), p, stair));
            if (obj instanceof PlanEdge) {
                PlanEdge e = (PlanEdge) obj;
                StairArea sa = (StairArea) stair.getPlanPolygon();
                if (stairState == StairStates.Lower) {
                    sa.setLowerLevel(e.getSource(), e.getTarget());
                    stairState = StairStates.Upper;
                    System.out.println("Lower level set.");
                } else if (stairState == StairStates.Upper) {
                    try {
                        sa.setUpperLevel(e.getSource(), e.getTarget());
                        System.out.println("Upper level set.");
                        stairState = StairStates.Done;
                    } catch (StairAreaBoundaryException ex) {
                        // ignore exception and mouse click. do nothing and wait for correct one.
                        // TODO send an info to the user interface
                        System.err.println("Adjacent segments cannot be lower and upper!");
                    }
                } else {
                    throw new AssertionError("In creation mode, illegally to define upper or lower edges!");
                }
            }
        }
    }
}
