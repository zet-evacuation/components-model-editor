/* zet evacuation tool copyright (c) 2007-14 zet evacuation team
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
package zet.gui.main.tabs;

import org.zetool.common.localization.Localization;
import de.zet_evakuierung.model.FloorInterface;
import de.zet_evakuierung.model.Floor;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.ZControl;
import de.zet_evakuierung.model.ZModelRoomEvent;
import event.EventListener;
import gui.editor.Areas;
import info.clearthought.layout.TableLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.zetool.common.localization.Localized;
import org.zetool.components.JRuler;
import zet.gui.components.editor.EditorLocalization;
import zet.gui.components.model.FloorComboBoxModel;
import zet.gui.components.model.NamedComboBox;
import zet.gui.components.model.NamedComboBoxRenderer;
import zet.gui.components.model.RoomComboBoxModel;
import zet.gui.main.tabs.base.AbstractSplitPropertyWindow;
import zet.gui.main.tabs.base.JFloorScrollPane;
import zet.gui.main.tabs.editor.control.FloorViewModel;
import zet.gui.main.tabs.editor.floor.JFloor;

/**
 * <p>
 * One of the main components of the ZET software. This is a component consisting of two parts which are divided by a
 * split, which can be removed to resize the left and right part.</p>
 * <p>
 * The left part contains an {@link JFloor}. This component displays a floor and all the objects on it. These are rooms
 * and areas. For more details about this elements see the documentation of the z-data structure.</p>
 * <p>
 * The right part contains a panel with properties for the currently select element on the floor on the left side. For
 * each type of elements, a different panel with the appropriate properties is visible. These properties include names
 * and several information that can be stored in the z-data structure.</p>
 *
 * @see ds.z.Floor
 * @see zet.gui.components.tabs.editor.JFloor
 * @author Jan-Philipp Kappmeier
 */
@SuppressWarnings("serial")
public class JEditView extends AbstractSplitPropertyWindow<JFloorScrollPane<JFloor>> implements Observer, EventListener<ZModelRoomEvent> {
    private boolean hideDefaultFloor = true; //@// probably to some properties object or so...
    private EditViewModel viewModel;

    public void setZoomFactor(double zoomFactor) {
        getLeftPanel().setZoomFactor(zoomFactor);
        getFloor().redisplay();
        //updateFloorView();
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new IllegalStateException("This method is not implemented and should probably be deleted!");
    }

    @Override
    public void handleEvent(ZModelRoomEvent event) {
        System.out.println("Sending forward an event.");
        getFloor().handleEvent(event);
    }

    /**
     * Sets a new edit view model (with new / updated floors).
     *
     * @param editViewModel the edit view model containing the data displayed in the view
     */
    void setEditViewModel(EditViewModel editViewModel) {
        this.viewModel = editViewModel;
        floorSelector.setModel(new FloorComboBoxModel(viewModel));
        roomSelector.setModel(new RoomComboBoxModel(viewModel.getCurrentFloor()));
    }

    /**
     * An enumeration of all possible panels visible in the edit view on the right part.
     *
     */
    public enum Panels {

        /** Message code indicating that the delay area panel should be displayed. */
        InaccessibleArea,
        /** Message code indicating that the delay area panel should be displayed. */
        DelayArea,
        /** Message code indicating that the assignment area panel should be displayed. */
        AssignmentArea,
        /** Message code indicating that the room panel should be displayed. */
        Room,
        /** Message code indicating that the default panel should be displayed. */
        Default,
        /** Message code indicating that the floor panel should be displayed. */
        Floor,
        /** Message code indicating that the evacuation area panel should be displayed. */
        EvacuationArea,
        /** Message code indicating that the stair area panel should be displayed. */
        StairArea,
        /** Message code indicating that the stair area panel should be displayed. */
        TeleportArea,
        /** A single edge is select. No area or polygon. */
        Edge;
    }

    /** The localization class. */
    private final Localization loc = EditorLocalization.LOC;
    /** The currently active panel type */
    private static Panels eastPanelType;
    /** The control object for the loaded project. */
    private ZControl projectControl;
    /** The currently visible {@link de.zet_evakuierung.model.Floor} */
    private Floor currentFloor;
    /** The floor-selector combo box. */
    private NamedComboBox<FloorViewModel> floorSelector;
    /** The room-selector combo box. */
    private NamedComboBox<Room> roomSelector;
    /** A label that shows a string explaining the floor selection combo box. */
    private JLabel lblFloorSelector;
    /** A label that contains the number of the currently visible floor. */
    private JLabel lblFloorNumber;
    /** A label that shows a string explaining the room selection combo box. */
    private JLabel lblRoomSelector;
    // Components for the east bar
    /** The CardLayout object of the east subpanel. */
    private CardLayout eastSubBarCardLayout;
    /** The panel representing the variable part of the east bar. */
    private JPanel eastSubBar;

    private boolean disableUpdate = false;

    public JEditView(EditViewModel viewModel) {
        super(new JFloorScrollPane<>(new JFloor(Objects.requireNonNull(viewModel).getCurrentFloor())));
        this.viewModel = viewModel;
        addComponents();
    }

    public void registerPanel(Component component, String identifier) {
        eastSubBar.add(component, identifier);
    }

    public void showPanel(String identifier) {
        eastSubBarCardLayout.show(eastSubBar, identifier);
    }

    private AdjustmentListener adlPlanImage = new AdjustmentListener() {
        @Override
        public void adjustmentValueChanged(AdjustmentEvent e) {
            //getLeftPanel().getMainComponent().getPlanImage().setVisibleRect( getLeftPanel().getMainComponent().getVisibleRect() );
            //getLeftPanel().getMainComponent().getPlanImage().update();
        }
    };

    /**
     * Returns the index of the panel currently visible in the east panel.
     *
     * @return the index of the panel currently visible in the east panel
     */
    public Panels getEastPanelType() {
        return eastPanelType;
    }

    /**
     * Returns the panel that is displayed in the right/eastern part of the edit view. This panel contains several cards
     * in a card layout that can be switched and contain elements for the properties of different objects of z format.
     *
     * @return the panel containing the different components
     */
    @Override
    protected JPanel createEastBar() {
        double size[][] = // Columns
                {{10, TableLayout.FILL, 10},
                //Rows
                {10, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.FILL
                }
                };
        JPanel eastPanel = new JPanel(new TableLayout(size));

        floorSelector = new NamedComboBox<>(new FloorComboBoxModel(viewModel));
        floorSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ACTION-LISTENER CALLED!!!!");
                if (floorSelector.getSelectedItem() == null) {
                    return;
                }

                roomSelector.setModel(new RoomComboBoxModel(floorSelector.getSelectedItem()));
                roomSelector.setSelectedItem(null);

                final int add = JEditView.this.hideDefaultFloor ? 1 : 0;
                lblFloorNumber.setText(String.format(loc.getStringWithoutPrefix("gui.EditPanel.Default.OnFloor"), floorSelector.getSelectedIndex() + add));

                //@//getLeftPanel().getTopRuler().setWidth( currentFloor.getWidth() );
                //@//getLeftPanel().getLeftRuler().setHeight( currentFloor.getHeight() );
                //@//getLeftPanel().getTopRuler().offset = de.zet_evakuierung.util.ConversionTools.roundScale3( currentFloor.getxOffset() / 1000.0 - 0.8 );
                //@//getLeftPanel().getLeftRuler().offset = de.zet_evakuierung.util.ConversionTools.roundScale3( currentFloor.getyOffset() / 1000.0 - 0.8 );
                //@//System.out.println( "Set offset to " + currentFloor.getxOffset() );
                //@//getLeftPanel().getTopRuler().setOffset( currentFloor.getxOffset() );
                //@//getLeftPanel().getLeftRuler().setOffset( currentFloor.getyOffset() );
                // Title of the window
                //@//guiControl.setZETWindowTitle( getAdditionalTitleBarText() );
                getLeftPanel().getMainComponent().setFloorModel(floorSelector.getSelectedItem());

                // Finally change the floor
                //changeFloor( currentFloor );
            }
        });

        roomSelector = new NamedComboBox<>(new RoomComboBoxModel(viewModel.getCurrentFloor()));
        roomSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                getFloor().showPolygon((Room) roomSelector.getSelectedItem());
            }
        });
        roomSelector.setRenderer(new NamedComboBoxRenderer<>());

        int row = 1;

        loc.setPrefix("gui.EditPanel.");
        lblFloorSelector = new JLabel(loc.getString("Default.Floors") + ":");
        eastPanel.add(lblFloorSelector, "1, " + row++);
        eastPanel.add(floorSelector, "1, " + row++);
        lblFloorNumber = new JLabel("1");
        eastPanel.add(lblFloorNumber, "1, " + row++);
        row++;
        lblRoomSelector = new JLabel(loc.getString("Default.Rooms") + ":");
        eastPanel.add(lblRoomSelector, "1, " + row++);
        eastPanel.add(roomSelector, "1, " + row++);
        row++;

        eastSubBarCardLayout = new CardLayout();
        eastSubBar = new JPanel(eastSubBarCardLayout);
        eastPanel.add(eastSubBar, "1, " + row++);

        loc.clearPrefix();
        return eastPanel;
    }

    @Override
    public void localize() {
        // Title of the window
        // Localization of child components
        getLeftPanel().localize();

        loc.setPrefix("gui.EditPanel.");
        // Localization of own components
        lblFloorSelector.setText(loc.getString("Default.Floors") + ":");
        lblFloorNumber.setText(String.format(loc.getStringWithoutPrefix("gui.EditPanel.Default.OnFloor"),
                floorSelector.getSelectedIndex() + (JEditView.this.hideDefaultFloor ? 1 : 0)));
        lblRoomSelector.setText(loc.getString("Default.Rooms") + ":");

        loc.clearPrefix();

        for (Component c : eastSubBar.getComponents()) {
            if (c instanceof Localized) {
                ((Localized) c).localize();
            }
        }
    }

    /**
     * Returns the current title bar text of the window, so it can be set if the editor panel is displayed inside an
     * window.
     *
     * @return the text
     */
    @Override
    protected String getAdditionalTitleBarText() {
        return projectControl.getProject().getProjectFile() != null ? "[" + currentFloor.getName() + "]" : "[" + currentFloor.getName() + "]";
    }

    /**
     * Changes the select displayed floor. This includes changing the GUI so that it displays the new floor.
     *
     * @param floor the new floor that is shown
     */
    public void changeFloor(FloorInterface floor) {
        floorSelector.setSelectedItem(floor);
    }

    public void displayProject() {
        if (1 == 1) {
            throw new IllegalStateException("should not be called");
        }
        displayProject(projectControl);
    }

    /**
     * Sets the z {@link de.zet_evakuierung.model.Project} that is displayed in the edit view.
     *
     * @param projectControl
     */
    final public void displayProject(ZControl projectControl) {
        if (1 == 1) {
            throw new IllegalStateException("should not be called");
        }
        this.projectControl = projectControl;

        updateFloorList();

        int floorId = 0;
        if (!JEditView.this.hideDefaultFloor) {
            if (projectControl.getProject().getBuildingPlan().getFloors().size() >= 2) {
                floorId = 1;
            }
        }

        // We create a new JFloor object, deregister the old one, and push the new one to the controller
        setFloor(floorId);

        // Setup the popup menus for the new project
        if (projectControl != null) {
            //@//guiControl.getPolygonPopup().recreate( projectControl.getProject().getCurrentAssignment() );
            //@//guiControl.getEdgePopup().recreate();
            //@//guiControl.getPointPopup().recreate();
        }

        // Give control over the project to the panels
        //@//for( Panels p : Panels.values() )
        //@//p.getPanel().setControl( projectControl);//@//, guiControl );
    }

    /**
     * Returns the GUI component of the floor.
     *
     * @return the GUI component of the floor
     */
    final public JFloor getFloor() {
        return getLeftPanel().getMainComponent();
    }

    /**
     * Returns the z format floor that is currently visible.
     *
     * @return the z format floor that is currently visible
     */
    public Floor getCurrentFloor() {
        return currentFloor;
    }

    /**
     * Sets the select floor to a specified id
     *
     * @param id the floor id
     */
    public void setFloor(int id) {
        floorSelector.setSelectedIndex(id);
    }

    /**
     * Returns the the z format floor that is currently visible. If the evacuation floor is hidden, the first floor is
     * returned with index 0.
     *
     * @return the z format floor that is currently visible
     */
    public int getFloorID() {
        return floorSelector.getSelectedIndex();
    }

    public ZControl getProjectControl() {
        return projectControl;
    }

    /**
     * Updates the GUI if a new project has been loaded. Loads new combo boxes, list boxes etc.
     */
    public void update() {
    }

    public void updateFloorView() {
        if (this.disableUpdate) {
            return;
        }
        //@//currentFloor = floorSelector.getSelectedItem();
//        getLeftPanel().getMainComponent().displayFloor( currentFloor );
        updateRoomList();
    }

    /**
     * Displays the floor with name {@code floorName}
     */
    public final void updateFloorList() {
        //@//floorSelector.clear();
        //@//floorSelector.displayFloors( projectControl.getProject().getBuildingPlan(), JEditView.this.hideDefaultFloor );
    }

    /**
     * Resets the room list on the current floor.
     */
    public void updateRoomList() {
        //@//roomSelector.clear();
        //@//roomSelector.displayRoomsForCurrentFloor();
    }

    public void setFloorNameFocus() {
        //txtFloorName.requestFocusInWindow();
        //txtFloorName.setSelectionStart( 0 );
        //txtFloorName.setSelectionEnd( txtFloorName.getText().length() );
    }

    public void setRoomNameFocus() {
        //txtRoomName.requestFocusInWindow();
        //txtRoomName.setSelectionStart( 0 );
        //txtRoomName.setSelectionEnd( txtRoomName.getText().length() );
    }

    /**
     *
     * @param mode
     */
    public void changeAreaView(ArrayList<Areas> mode) {
        // EnumSet.copyOf will not work for empty lists
        //@//GUIOptionManager.getAreaVisibility();
        //@//GUIOptionManager.setAreaVisibility( mode.isEmpty() ? EnumSet.noneOf( Areas.class ) : EnumSet.copyOf( mode ) );
        updateFloorView();
    }

    public void stateChanged() {
        final JRuler topRuler = getLeftPanel().getTopRuler();
        final JRuler leftRuler = getLeftPanel().getLeftRuler();
        Floor floor = getCurrentFloor();
        topRuler.setWidth(floor.getWidth());
        leftRuler.setHeight(floor.getHeight());
        //@//topRuler.offset = de.zet_evakuierung.util.ConversionTools.roundScale3( floor.getxOffset() / 1000.0 - 0.8 );
        //@//leftRuler.offset = de.zet_evakuierung.util.ConversionTools.roundScale3( floor.getyOffset() / 1000.0 - 0.8 );
        topRuler.setOffset(floor.getxOffset());
        leftRuler.setOffset(floor.getyOffset());
        topRuler.repaint();
        leftRuler.repaint();
    }

    /**
     * Prohibits serialization.
     */
    private synchronized void writeObject(java.io.ObjectOutputStream s) throws IOException {
        throw new UnsupportedOperationException("Serialization not supported");
    }
}
