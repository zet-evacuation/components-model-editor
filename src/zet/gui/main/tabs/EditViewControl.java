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

package zet.gui.main.tabs;

import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.DelayArea;
import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.Floor;
import de.zet_evakuierung.model.PlanPolygon;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.StairArea;
import de.zet_evakuierung.model.TeleportArea;
import de.zet_evakuierung.model.ZControl;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import zet.gui.main.tabs.JEditView.Panels;
import zet.gui.main.tabs.base.JPolygon;
import zet.gui.main.tabs.editor.EditMode;
import zet.gui.main.tabs.editor.ZetObjectTypes;
import zet.gui.main.tabs.editor.control.FloorControl;
import zet.gui.main.tabs.editor.control.FloorViewModel;
import zet.gui.main.tabs.editor.floor.SelectionEvent;
import zet.gui.main.tabs.editor.floor.SelectionListener;
import zet.gui.main.tabs.editor.panel.AbstractInformationPanelControl;
import zet.gui.main.tabs.editor.panel.AssignmentAreaControl;
import zet.gui.main.tabs.editor.panel.DefaultPanelControl;
import zet.gui.main.tabs.editor.panel.DelayAreaControl;
import zet.gui.main.tabs.editor.panel.EdgeControl;
import zet.gui.main.tabs.editor.panel.EvacuationAreaControl;
import zet.gui.main.tabs.editor.panel.FloorPanelControl;
import zet.gui.main.tabs.editor.panel.InaccessibleAreaControl;
import zet.gui.main.tabs.editor.panel.JInformationPanel;
import zet.gui.main.tabs.editor.panel.PointControl;
import zet.gui.main.tabs.editor.panel.RoomInformationControl;
import zet.gui.main.tabs.editor.panel.StairAreaControl;
import zet.gui.main.tabs.editor.panel.TeleportAreaControl;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class EditViewControl {
    private Floor currentFloor;
    private List<Floor> floors;
    private final FloorControl floorControl;
    private final JEditView view;
    private List<FloorViewModel> floorViewModels;
    private final ZControl control;
    private final SelectionListener floorSelectionListener = new FloorSelectionListener();

    private FloorPanelControl fc;
    private RoomInformationControl rc;
    private AssignmentAreaControl aac;
    private DelayAreaControl dac;
    private EvacuationAreaControl eac;
    private InaccessibleAreaControl iac;
    private StairAreaControl sac;
    private TeleportAreaControl tac;
    private EdgeControl ec;
    
    public EditViewControl(ZControl control, List<Floor> floors) {
        this.floors = validatedFloorList(floors);
        this.control = control;
        currentFloor = floors.get(1);
        view = createView();
        registerControls();
        registerPopups();
        floorControl = new FloorControl(control, view.getFloor());
        floorControl.setEditMode(EditMode.Selection);
    }
    
    private JEditView createView() {
        JEditView editView = new JEditView(generateViewModel());
        
        // Add selection listener to the edit view
        editView.getLeftPanel().getMainComponent().addSelectionListener(floorSelectionListener);
        return editView;
    }
    private void registerControls() {
        // Add all the panels to the map
        fc = registerControl(new FloorPanelControl(control), JEditView.Panels.Floor);
        rc = registerControl(new RoomInformationControl(control), JEditView.Panels.Room);
        aac = registerControl(new AssignmentAreaControl(control), JEditView.Panels.AssignmentArea);
        dac = registerControl(new DelayAreaControl(control), JEditView.Panels.DelayArea);
        eac = registerControl(new EvacuationAreaControl(control), JEditView.Panels.EvacuationArea);
        iac = registerControl(new InaccessibleAreaControl(control), JEditView.Panels.InaccessibleArea);
        sac = registerControl(new StairAreaControl(control), JEditView.Panels.StairArea);
        tac = registerControl(new TeleportAreaControl(control), JEditView.Panels.TeleportArea);
        ec = registerControl(new EdgeControl(control), JEditView.Panels.Edge);
        registerControl(new DefaultPanelControl(control), JEditView.Panels.Default);
    }
    
    private void registerPopups() {
        // Set up listener for popup menus and initialize popups
        view.getFloor().getPopups().getEdgePopup().setEdgeControl(ec);
        PointControl pc = new PointControl(control);
        view.getFloor().getPopups().getPointPopup().setPointControl(pc);
        view.getFloor().getPopups().getPolygonPopup().setPolygonControl(rc);
        view.getFloor().getPopups().getPolygonPopup().setAssignment(control.getProject().getCurrentAssignment());
    }
    
    private <E extends AbstractInformationPanelControl<V, ?>, V extends JInformationPanel<?,?>> E registerControl(
            E control, JEditView.Panels identifier) {
        view.registerPanel(control.getView(), identifier.toString());
        return control;
    }
    
    private List<Floor> validatedFloorList(List<Floor> floors) {
        if( Objects.requireNonNull(floors).isEmpty()) {
            throw new IllegalArgumentException("Empty floor list!");
        }
        floorViewModels = new LinkedList<>();
        for( Floor floor : floors ) {
            floorViewModels.add(new FloorViewModel(floor));
        }
        return floors;
        
    }
    
    private EditViewModel generateViewModel() {
        return new EditViewModel(floorViewModels, 1);
    }
    
    public void initView() {
    }
    
    public JEditView getView() {
        return view;
    }

    public void setEditMode(EditMode editMode) {
        floorControl.setEditMode(editMode);
    }
    
    public void setZetObjectType(ZetObjectTypes type) {
        floorControl.setZetObjectType(type);
    }
    
    public void set() {
        floorControl.setEditMode(EditMode.Selection);
    }

    public void setControlledProject(List<Floor> floors) {
        this.floors = validatedFloorList(floors);
        setCurrentFloor(floors.get(1));
    }
    
    public void setCurrentFloor(Floor floor) {
        if( !floors.contains(floor)) {
            throw new IllegalArgumentException( "Floor '" + floor + "' is not in the floor list!" );
        }
        currentFloor = floor;
        floorControl.setFloor(floor);
        getView().setEditViewModel(generateViewModel());
        System.out.println("Stored new floor in model" );
    }
        
    private class FloorSelectionListener implements SelectionListener {

        @Override
        public void selectionChanged(SelectionEvent event) {
            JPolygon displayPolygon = event.getSelectedPolygon();
            Panels panel = updateModel(displayPolygon.getPlanPolygon());
            getView().showPanel(panel.toString());
        }

        private Panels updateModel(PlanPolygon<?> p) {
            if (p instanceof Room) {
                rc.setModel((Room) p);
                return Panels.Room;
            } else if (p instanceof DelayArea) {
                dac.setModel((DelayArea) p);
                return Panels.DelayArea;
            } else if (p instanceof EvacuationArea) {
                eac.setModel((EvacuationArea) p);
                return Panels.EvacuationArea;
            } else if (p instanceof AssignmentArea) {
                aac.setModel((AssignmentArea) p);
                return Panels.AssignmentArea;
            } else if (p instanceof StairArea) {
                sac.setModel((StairArea) p);
                return Panels.StairArea;
            } else if (p instanceof TeleportArea) {
                tac.setModel((TeleportArea) p);
                return Panels.TeleportArea;
            } else {
                return Panels.Default;
            }
        }
        
        @Override
        public void selectionCleared(SelectionEvent event) {
            fc.setModel(currentFloor);
            getView().showPanel(JEditView.Panels.Floor.toString());
        }

        @Override
        public void selectionEdge(SelectionEvent event) {
            ec.setModel(event.getEdge());
            getView().showPanel(JEditView.Panels.Edge.toString());
        }

    };
}
