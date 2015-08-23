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

package zet.gui.main.menu.popup;

import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.AssignmentType;
import de.zet_evakuierung.model.PlanPoint;
import de.zet_evakuierung.model.PlanPolygon;
import de.zet_evakuierung.model.ZControl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This popup listener is responsible for handling menu events
 *
 * @author Timon Kelter, Jan-Philipp Kappmeier
 */
public class PolygonPopupListener implements ActionListener {

    private AssignmentType myType;
    private PlanPolygon myPolygon;
    private ZControl projectControl;

    public PolygonPopupListener(AssignmentType myType, ZControl zcontrol, ZControl projectControl) {
        this.projectControl = zcontrol;
        this.myType = myType;
        this.projectControl = projectControl;
    }

    /**
     * This method should be called every time before the popup is shown.
     *
     * @param currentPolygon The PlanPolygon on which the popup is shown.
     */
    public void setPolygon(PlanPolygon currentPolygon) {
        myPolygon = currentPolygon;
    }

    public void actionPerformed(ActionEvent e) {
        List<PlanPoint> points = myPolygon.getPolygonPoints();
        ArrayList<PlanPoint> newPoints = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            newPoints.add(new PlanPoint(points.get(i).getXInt(), points.get(i).getYInt()));
        }
        //AssignmentArea newAA = new AssignmentArea( (Room)myPolygon, myType );
        AssignmentArea newAA = projectControl.createNewArea(myPolygon, myType, newPoints);// .newAssignmentArea();
        //newAA.replace( newPoints );

        //@//guiControl.showArea(Areas.Assignment);
        //@//guiControl.setSelectedPolygon(newAA);
    }
}
