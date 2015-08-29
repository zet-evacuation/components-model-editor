package zet.gui.main.menu.popup;

import de.zet_evakuierung.model.Area;
import org.zetool.common.localization.Localization;
import de.zet_evakuierung.model.Assignment;
import de.zet_evakuierung.model.PlanPolygon;
import de.zet_evakuierung.model.Room;
import java.io.IOException;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import org.zetool.components.framework.Menu;
import zet.gui.components.editor.EditorLocalization;
import zet.gui.main.tabs.editor.panel.RoomInformationControl;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
@SuppressWarnings("serial")
public class PolygonPopup extends JPopupMenu {

    /** The localization class. */
    private final Localization loc = EditorLocalization.LOC;
    private RoomInformationControl polygonControl;
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
                loc.getString("gui.editor.JEditorPanel.popupDefaultAssignmentArea") + " in room " + r.getName());
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
    
    public void setPolygonControl(RoomInformationControl pc) {
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