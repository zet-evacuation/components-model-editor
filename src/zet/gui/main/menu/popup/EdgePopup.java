package zet.gui.main.menu.popup;

import org.zetool.common.util.Functions;
import org.zetool.common.localization.Localization;
import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.PlanPoint;
import de.zet_evakuierung.model.RoomEdge;
import de.zet_evakuierung.model.TeleportEdge;
import de.zet_evakuierung.model.ZControl;
import de.zet_evakuierung.template.Door;
import de.zet_evakuierung.template.ExitDoor;
import de.zet_evakuierung.template.Templates;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.function.Consumer;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.zetool.components.framework.Menu;
import zet.gui.components.editor.EditorLocalization;
import zet.gui.main.tabs.editor.panel.EdgeControl;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class EdgePopup extends JPopupMenu {
    boolean rasterizedPaintMode = true;

    /** The localization class. */
    private final static Localization loc = EditorLocalization.LOC;
    ZControl zcontrol;
    JMenu mCreateDoors;
    JMenu mCreateExitDoors;
    private PlanPoint mousePosition;
    private PlanEdge currentEdge;
    private EdgeControl edgeControl;
    private TwoEdgeAction passageRoomCreator = new TwoEdgeAction(Functions::sinkConsumer);
    private TwoEdgeAction floorPassageCreator = new TwoEdgeAction(Functions::sinkConsumer);
    
    public EdgePopup() {
        super();
        recreate();
    }
    
    public void loadDoorTemplates(Templates<Door> doors) {
        int i = 0;
        for (Door d : doors) {
            Menu.addMenuItem(mCreateDoors, d.getName() + " (" + d.getSize() + ")", (ActionEvent e) -> {
                Door door = doors.getDoor(Integer.parseInt(e.getActionCommand()));
                edgeControl.setModel(currentEdge);
                edgeControl.createTemplatePassage(mousePosition, door);
            },
            String.valueOf(i++));
        }
    }

    public void loadExitTemplates(Templates<ExitDoor> exitDoors) {
        int i = 0;
        for (ExitDoor d : exitDoors) {
            Menu.addMenuItem(mCreateExitDoors, d.getName() + " (" + d.getSize() + ")", (ActionEvent e) -> {
                ExitDoor exit = exitDoors.getDoor(Integer.parseInt(e.getActionCommand()));
                edgeControl.setModel(currentEdge);
                edgeControl.createTemplateExit(mousePosition, exit);
            },
            String.valueOf(i++));
        }
    }

    /**
     * This method is called internally to recreate an up-to-date JPopupMenu for the JEdge objects. It also recreates
     * the EdgePopupListeners.
     */
    public void recreate() {
        removeAll();

        loc.setPrefix("gui.editor.JEditorPanel.");
        Menu.addMenuItem(this, loc.getString("popupInsertNewPoint"), e -> {
            edgeControl.setModel(currentEdge);
            edgeControl.insertPoint(mousePosition);
        });
        Menu.addMenuItem(this, loc.getString("popupCreatePassage"), e -> {
            edgeControl.setModel(currentEdge);
            edgeControl.makePassable();
        });
        Menu.addMenuItem(this, loc.getString("popupCreatePassageRoom"), e -> {
            edgeControl.setModel(currentEdge);
            passageRoomCreator.addEdge(currentEdge);
        });
        Menu.addMenuItem(this, loc.getString("popupCreateFloorPassage"), e -> {
            edgeControl.setModel(currentEdge);
            floorPassageCreator.addEdge(currentEdge);
        });
        Menu.addMenuItem(this, loc.getString("popupCreateEvacuationPassage"), e -> {
            edgeControl.setModel(currentEdge);
            edgeControl.makeExit();
        });
        Menu.addMenuItem(this, loc.getString("popupShowPassageTarget"), e -> 
            System.err.println("Showing targets of passages is not yet implemented!")
        );
        Menu.addMenuItem(this, loc.getString("popupRevertPassage"), e -> {
            edgeControl.setModel(currentEdge);
            edgeControl.revertPassage();
        });
        mCreateDoors = Menu.addMenu(this, "Tür erzeugen");
        mCreateExitDoors = Menu.addMenu(this, "Ausgang erzeugen");
        
        loc.setPrefix("");
    }
   
    public void setEdgeControl(EdgeControl edgeControl) {
        this.edgeControl = edgeControl;
        passageRoomCreator = new TwoEdgeAction(edgeControl::createEvacuationRoom);
        floorPassageCreator = new TwoEdgeAction(edgeControl::createFloorPassage);
    }
    
    /**
     * This method should be called every time before the JEdge popup menu is shown.
     *
     * @param currentEdge The Edge that is displayed by the JEdge on which the PopupMenu shall be shown.
     * @param mousePosition the position at which the popup menu is shown with coordinates that must be relative to the
     * whole Floor
     */
    public void setPopupEdge(PlanEdge currentEdge, PlanPoint mousePosition) {
        
        boolean passable = (currentEdge instanceof RoomEdge) && ((RoomEdge) currentEdge).isPassable();
        // passage-Creation
        ((JMenuItem) this.getComponent(1)).setVisible(!passable);
        // passage-room creation
        ((JMenuItem) this.getComponent(2)).setVisible(!passable);
        // Teleportation-Creation
        ((JMenuItem) this.getComponent(3)).setVisible(!passable);
        // EvacuationEdge-Creation
        ((JMenuItem) this.getComponent(4)).setVisible(!passable);
        // Show Partner edge
        ((JMenuItem) this.getComponent(5)).setVisible(currentEdge instanceof TeleportEdge);
        // revert passage
        ((JMenuItem) this.getComponent(6)).setVisible(passable);
        // create door 
        ((JMenuItem) this.getComponent(7)).setVisible(!passable && mCreateDoors.getItemCount() > 0);
        // create exit door 
        ((JMenuItem) this.getComponent(8)).setVisible(!passable && mCreateExitDoors.getItemCount() > 0);

        this.currentEdge = currentEdge;
        this.mousePosition = mousePosition;
    }

    /** Prohibits serialization. */
    private synchronized void writeObject(java.io.ObjectOutputStream s) throws IOException {
        throw new UnsupportedOperationException("Serialization not supported");
    }
    
    /** Prohibits serialization. */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Serialization not supported");
    }
    
    private static class TwoEdgeAction {
        /** The action performed on two edges. */
        private final Consumer<RoomEdge> action;
        /** Buffer for the first of the two edges. */
        private RoomEdge firstEdge = null;

        public TwoEdgeAction(Consumer<RoomEdge> twoEdgeAction) {
            this.action = twoEdgeAction;
        }
        
        public void addEdge(PlanEdge e) {
            if (!(e instanceof RoomEdge) ) {
                return;
            }
            if (firstEdge == null) {
                firstEdge = (RoomEdge)e;
            } else {
                action.accept(firstEdge);
                firstEdge = null;
            }
        }
    }
}
