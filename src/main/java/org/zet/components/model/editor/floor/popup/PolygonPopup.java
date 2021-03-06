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
package org.zet.components.model.editor.floor.popup;

import de.zet_evakuierung.model.Area;
import de.zet_evakuierung.model.Assignment;
import de.zet_evakuierung.model.PlanPolygon;
import de.zet_evakuierung.model.Room;
import java.io.IOException;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import org.zetool.components.framework.Menu;
import org.zet.components.model.editor.localization.EditorLocalization;
import org.zet.components.model.viewmodel.RoomControl;
import org.zetool.common.localization.Localization;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
@SuppressWarnings("serial")
public class PolygonPopup extends JPopupMenu {

    /** The localization class. */
    private static final Localization LOC = EditorLocalization.LOC;
    private RoomControl polygonControl;
    private PlanPolygon<?> currentPolygon;
    private Assignment assignment;

    public PolygonPopup() {
        super();
    }
    
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public void recreate() {
        removeAll();

        Room r = getClickRoom();
        JMenu createAssignmentAreaMenu = Menu.addMenu(this,
                LOC.getString("gui.editor.JEditorPanel.popupDefaultAssignmentArea") + " in room " + r.getName());
        if (assignment != null) {
            System.out.println(assignment.toString());
            assignment.getAssignmentTypes().stream().forEach((t) -> 
                Menu.addMenuItem(createAssignmentAreaMenu, t.getName(), e -> {
                    polygonControl.setModel(r);
                    polygonControl.fillWithAssignmentArea(t);
                })
            );
            createAssignmentAreaMenu.setEnabled(true);
        } else {
            createAssignmentAreaMenu.setEnabled(false);
        }
    }

    private Room getClickRoom() {
        if( currentPolygon instanceof Room ) {
            return (Room)currentPolygon;
        } else {
            Area a = (Area)currentPolygon;
            return a.getAssociatedRoom();
        }
    }
    
    public void setPolygonControl(RoomControl pc) {
        this.polygonControl = pc;
    }

    /**
     * This method should be called every time before the JPolygon pop-up menu is shown.
     *
     * @param currentPolygon The PlanPolygon that is displayed by the JPolygon on which the PopupMenu shall be shown.
     */
    public void setPopupPolygon(PlanPolygon<?> currentPolygon) {
        this.currentPolygon = currentPolygon;
        recreate();
    }

    /** Prohibits serialization. */
    private synchronized void writeObject(java.io.ObjectOutputStream s) throws IOException {
        throw new UnsupportedOperationException("Serialization not supported");
    }
    
    /** Prohibits serialization. */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Serialization not supported");
    }
}
