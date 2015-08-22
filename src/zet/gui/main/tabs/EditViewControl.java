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

import de.zet_evakuierung.model.Floor;
import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.PlanPolygon;
import de.zet_evakuierung.model.ZControl;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import zet.gui.main.tabs.base.JPolygon;
import zet.gui.main.tabs.editor.EditMode;
import zet.gui.main.tabs.editor.ZetObjectTypes;
import zet.gui.main.tabs.editor.control.FloorControl;
import zet.gui.main.tabs.editor.control.FloorViewModel;
import zet.gui.main.tabs.editor.floor.SelectionEvent;
import zet.gui.main.tabs.editor.floor.SelectionListener;
import zet.gui.main.tabs.editor.panel.AbstractInformationPanelControl;
import zet.gui.main.tabs.editor.panel.AssignmentAreaControl;
import zet.gui.main.tabs.editor.panel.ChangeEvent;
import zet.gui.main.tabs.editor.panel.DefaultPanelControl;
import zet.gui.main.tabs.editor.panel.DelayAreaControl;
import zet.gui.main.tabs.editor.panel.EdgeControl;
import zet.gui.main.tabs.editor.panel.EvacuationAreaControl;
import zet.gui.main.tabs.editor.panel.FloorPanelControl;
import zet.gui.main.tabs.editor.panel.InaccessibleAreaControl;
import zet.gui.main.tabs.editor.panel.InformationPanelControl;
import zet.gui.main.tabs.editor.panel.JInformationPanel;
import zet.gui.main.tabs.editor.panel.RoomInformationPanelControl;
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
    private JEditView view;
    private List<FloorViewModel> floorViewModels;
    private EnumMap<JEditView.Panels,InformationPanelControl> controlMap;
    //private EnumMap<JEditView.Panels,Component> componentMap;
    private final ZControl control;
    
    public EditViewControl(ZControl control, List<Floor> floors) {
        this.floors = validatedFloorList(floors);
        this.control = control;
        currentFloor = floors.get(1);
        view = createView();
        floorControl = new FloorControl(control, view.getFloor());
        floorControl.setEditMode(EditMode.Selection);
    }
    
    private JEditView createView() {
        JEditView editView = new JEditView(generateViewModel());
        
        // Add selection listener to the edit view
        editView.getLeftPanel().getMainComponent().addSelectionListener(floorSelectionListener);

        // Add all the panels to the map
        controlMap = new EnumMap<>(JEditView.Panels.class);
        registerControlAndView(new FloorPanelControl(control), JEditView.Panels.Floor, editView);
        registerControlAndView(new RoomInformationPanelControl(control), JEditView.Panels.Room, editView);
        registerControlAndView(new AssignmentAreaControl(control), JEditView.Panels.AssignmentArea, editView);
        registerControlAndView(new DelayAreaControl(control), JEditView.Panels.DelayArea, editView);
        registerControlAndView(new EvacuationAreaControl(), JEditView.Panels.EvacuationArea, editView);
        registerControlAndView(new InaccessibleAreaControl(), JEditView.Panels.InaccessibleArea, editView);
        registerControlAndView(new StairAreaControl(control), JEditView.Panels.StairArea, editView);
        registerControlAndView(new TeleportAreaControl(control), JEditView.Panels.TeleportArea, editView);
        registerControlAndView(new EdgeControl(), JEditView.Panels.Edge, editView);
        registerControlAndView(new DefaultPanelControl(), JEditView.Panels.Default, editView);

        return editView;
    }
    
    private <E extends JInformationPanel<K,M>, M, K extends ChangeEvent> void registerControlAndView(
            AbstractInformationPanelControl<E, M, K> a, JEditView.Panels key, JEditView editView) {
        controlMap.put(key, a);
        a.getView().addChangeListener(a);
        editView.registerPanel(a.getView(), key.toString());
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
    
    // Panel handling
	/**
	 * Sets the panel visible in the east panel.
	 * @param panel the panel
	 */
        private void showPolygonPanel(JEditView.Panels panel, PlanPolygon<?> p) {
            controlMap.get(panel).setModel(p);
            getView().showPanel(panel.toString());
        }
        
        private void showFloorPanel() {
            //JEditView.Panels.Floor.getPanel().update( currentFloor);
            FloorPanelControl floorPaneControl = (FloorPanelControl)controlMap.get(JEditView.Panels.Floor);
            floorPaneControl.setModel(currentFloor);
            getView().showPanel(JEditView.Panels.Floor.toString());
        }
        
        private void showEdgePanel(PlanEdge edge) {
            //JEditView.Panels.Edge.getPanel().update(edge);
            EdgeControl edgePaneControl = (EdgeControl)controlMap.get(JEditView.Panels.Edge);
            edgePaneControl.setModel(edge);
            getView().showPanel(JEditView.Panels.Edge.toString());
        }


    private final SelectionListener floorSelectionListener = new SelectionListener() {

        @Override
        public void selectionChanged(SelectionEvent event) {
            System.out.println("Selection changed!");
            JPolygon p = event.getSelectedPolygon();
            showPolygonPanel(JEditView.Panels.panelForPolygon(p.getPlanPolygon()), p.getPlanPolygon());
        }

        @Override
        public void selectionCleared(SelectionEvent event) {
            // When the selection is cleared, we go back to floor panel
            showFloorPanel();
        }

        @Override
        public void selectionEdge(SelectionEvent event) {
            System.out.println("Edge selected");
            showEdgePanel(event.getEdge());
        }

    };


}
