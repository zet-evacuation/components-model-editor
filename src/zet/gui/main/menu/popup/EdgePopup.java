package zet.gui.main.menu.popup;

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
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.zetool.components.framework.Menu;
import zet.gui.components.editor.EditorLocalization;
import zet.gui.main.menu.popup.events.CreatePassageRoom;
import zet.gui.main.menu.popup.events.CreateTemplateExitEvent;
import zet.gui.main.menu.popup.events.CreateTemplatePassageEvent;
import zet.gui.main.menu.popup.events.InsertPointEvent;
import zet.gui.main.menu.popup.events.MakeExitEvent;
import zet.gui.main.menu.popup.events.MakePassableEvent;
import zet.gui.main.menu.popup.events.MakeTeleportEvent;
import zet.gui.main.menu.popup.events.RevertPassage;
import zet.gui.main.tabs.editor.panel.ChangeListener;
import zet.gui.main.tabs.editor.panel.EdgeChangeEvent;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class EdgePopup extends JPopupMenu {
    boolean rasterizedPaintMode = true;

    /** The localization class. */
    private final Localization loc = EditorLocalization.LOC;
    ZControl zcontrol;
    JMenu mCreateDoors;
    JMenu mCreateExitDoors;
    private PlanPoint mousePosition;
    private PlanEdge currentEdge;
    private Templates<Door> doors;
    private Templates<ExitDoor> exits;
    

    public EdgePopup() {
        super();
        recreate();
    }
    
    public void loadDoorTemplates(Templates<Door> doors) {
        this.doors = doors;
        System.err.println("Door templates disabled!");
        int i = 0;
        for (Door d : doors) {
            Menu.addMenuItem(mCreateDoors, d.getName() + " (" + d.getSize() + ")", (ActionEvent e) -> {
                Door door = doors.getDoor(Integer.parseInt(e.getActionCommand()));
                fireChangeEvent(new CreateTemplatePassageEvent(this, currentEdge, mousePosition, door));
            },
            String.valueOf(i++));
        }
    }
    
    public void loadExitTemplates(Templates<ExitDoor> exitDoors ) {
        this.exits = exitDoors;
        System.err.println("Exit templates disabled!");
        int i = 0;
        for (ExitDoor d : exitDoors) {
            Menu.addMenuItem(mCreateExitDoors, d.getName() + " (" + d.getSize() + ")", (ActionEvent e) -> {
                ExitDoor exit = exits.getDoor(Integer.parseInt(e.getActionCommand()));
                fireChangeEvent(new CreateTemplateExitEvent(this, currentEdge, mousePosition, exit));
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
        Menu.addMenuItem(this, loc.getString("popupInsertNewPoint"), ActionEvent
                -> fireChangeEvent(new InsertPointEvent(this, currentEdge, mousePosition)));
        Menu.addMenuItem(this, loc.getString("popupCreatePassage"), ActionEvent
                -> fireChangeEvent(new MakePassableEvent(this, currentEdge)));
        Menu.addMenuItem(this, loc.getString("popupCreatePassageRoom"), ActionEvent
                -> fireChangeEvent(new CreatePassageRoom(this, currentEdge)));
        Menu.addMenuItem(this, loc.getString("popupCreateFloorPassage"), ActionEvent
                -> fireChangeEvent(new MakeTeleportEvent(this, currentEdge)));
        Menu.addMenuItem(this, loc.getString("popupCreateEvacuationPassage"), ActionEvent
                -> fireChangeEvent(new MakeExitEvent(this, currentEdge)));
        Menu.addMenuItem(this, loc.getString("popupShowPassageTarget"), ActionEvent
                -> System.err.println("Showing targets of passages is not yet implemented!"));
        Menu.addMenuItem(this, loc.getString("popupRevertPassage"), ActionEvent
                -> fireChangeEvent(new RevertPassage(this, currentEdge)));
        mCreateDoors = Menu.addMenu(this, "Tür erzeugen");
        mCreateExitDoors = Menu.addMenu(this, "Ausgang erzeugen");
        
        loc.setPrefix("");
    }
    
    private final ActionListener popupClickListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            fireChangeEvent(new InsertPointEvent(this, currentEdge, mousePosition));
        }
    };
    

    public void addChangeListener(ChangeListener<EdgeChangeEvent> l) {
        listenerList.add(ChangeListener.class, l);
    }

    public void removeChangeListener(ChangeListener<EdgeChangeEvent> l) {
        listenerList.remove(ChangeListener.class, l);
    }

    protected void fireChangeEvent(EdgeChangeEvent c) {
        // Guaranteed to return a non-null array
        System.out.println("We are in firechangeevent");
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class) {
                if (c == null) {
                    throw new IllegalArgumentException("ChangeEvent null!");
                }
                ((ChangeListener<EdgeChangeEvent>) listeners[i + 1]).changed(c);
            }
        }
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
        ((JMenuItem) this.getComponent(7)).setVisible(!passable && mCreateExitDoors.getItemCount() > 0);

        
        this.currentEdge = currentEdge;
        System.out.println("New point will be at " + mousePosition);
        this.mousePosition = mousePosition;
    }

}
