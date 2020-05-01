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

package org.zet.components.model.editor.editview;

import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.DelayArea;
import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.Floor;
import de.zet_evakuierung.model.PlanPolygon;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.StairArea;
import de.zet_evakuierung.model.TeleportArea;
import de.zet_evakuierung.model.ZControl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.zet.components.model.editor.floor.EditMode;
import org.zet.components.model.editor.editview.JEditView.Panels;
import org.zet.components.model.editor.floor.ZetObjectTypes;
import org.zet.components.model.editor.polygon.JPolygon;
import org.zet.components.model.editor.floor.FloorControl;
import org.zet.components.model.editor.floor.FloorViewModel;
import org.zet.components.model.editor.floor.SelectionEvent;
import org.zet.components.model.editor.floor.SelectionListener;
import org.zet.components.model.editor.panel.AssignmentAreaInformationPanelControl;
import org.zet.components.model.editor.panel.DefaultAreaInformationPanelControl;
import org.zet.components.model.editor.panel.DelayAreaInformationPanelControl;
import org.zet.components.model.editor.panel.EdgeInformationPanelControl;
import org.zet.components.model.editor.panel.EvacuationAreaInformationPanelControl;
import org.zet.components.model.editor.panel.FloorInformationPanelControl;
import org.zet.components.model.editor.panel.JInformationPanel;
import org.zet.components.model.editor.panel.JRoomInformationPanel;
import org.zet.components.model.editor.panel.RoomInformationPanelControl;
import org.zet.components.model.editor.panel.StairAreaInformationPanelControl;
import org.zet.components.model.editor.panel.TeleportAreaInformationPanelControl;
import org.zet.components.model.viewmodel.AbstractControl;
import org.zet.components.model.viewmodel.AbstractInformationControl;
import org.zet.components.model.viewmodel.PointControl;
import org.zet.components.model.viewmodel.RoomControl;
import org.zet.components.model.viewmodel.RoomViewModel;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class EditViewControl {
    private Floor currentFloor;
    private final FloorControl currentFloorControl;
    private final JEditView view;
    private LinkedHashMap<Floor,FloorViewModel> floorMap;
    private final ZControl control;
    private final SelectionListener floorSelectionListener = new FloorSelectionListener();
    private final ActionListener floorActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            FloorViewModel selected = getView().getCurrentFloor();
            Floor f = selected.getFloor();
            setCurrentFloor(f);
        }
    };

    private FloorInformationPanelControl fc;
    private RoomInformationPanelControl rc;
    private AssignmentAreaInformationPanelControl aac;
    private DelayAreaInformationPanelControl dac;
    private EvacuationAreaInformationPanelControl eac;
    private StairAreaInformationPanelControl sac;
    private TeleportAreaInformationPanelControl tac;
    private EdgeInformationPanelControl ec;
    
    public EditViewControl(ZControl control, List<Floor> floors) {
        this.control = control;
        validatedFloorList(floors);
        currentFloor = floors.get(1);
        view = createView();
        currentFloorControl = new FloorControl(control, view.getFloor());
        registerControls();
        registerPopups();
        currentFloorControl.setEditMode(EditMode.SELECTION);
    }
    
    private JEditView createView() {
        JEditView editView = new JEditView(generateViewModel(currentFloor));
        
        // Add selection listener to the edit view
        editView.getLeftPanel().getMainComponent().addSelectionListener(floorSelectionListener);
        editView.addFloorActionListener(floorActionListener);
        return editView;
    }
    private void registerControls() {
        // Add all the panels to the map
        RoomInformationPanelControl rip = RoomInformationPanelControl.create(control);
        AbstractInformationControl<JRoomInformationPanel, RoomControl, Room, RoomViewModel> d = rip;
        fc = registerControl(FloorInformationPanelControl.create(control, currentFloorControl), JEditView.Panels.Floor);
        rc = registerControl(RoomInformationPanelControl.create(control), JEditView.Panels.Room);
        aac = registerControl(AssignmentAreaInformationPanelControl.create(control), JEditView.Panels.AssignmentArea);
        dac = registerControl(DelayAreaInformationPanelControl.create(control), JEditView.Panels.DelayArea);
        eac = registerControl(EvacuationAreaInformationPanelControl.create(control), JEditView.Panels.EvacuationArea);
        registerControl(DefaultAreaInformationPanelControl.create(control), JEditView.Panels.InaccessibleArea);
        sac = registerControl(StairAreaInformationPanelControl.create(control), JEditView.Panels.StairArea);
        tac = registerControl(TeleportAreaInformationPanelControl.create(control), JEditView.Panels.TeleportArea);
        ec = registerControl(EdgeInformationPanelControl.create(control), JEditView.Panels.Edge);
        registerControl(DefaultAreaInformationPanelControl.create(control), JEditView.Panels.Default);
    }
    
    private void registerPopups() {
        // Set up listener for popup menus and initialize popups
        view.getFloor().getPopups().getEdgePopup().setEdgeControl(ec.getControl());
        PointControl pc = new PointControl(control);
        view.getFloor().getPopups().getPointPopup().setPointControl(pc);
        view.getFloor().getPopups().getPolygonPopup().setPolygonControl(rc.getControl());
        view.getFloor().getPopups().getPolygonPopup().setAssignment(control.getProject().getCurrentAssignment());
    }
    
    private <E extends AbstractInformationControl<J, C, ?, ?>, J extends JInformationPanel<C, ?>, C extends AbstractControl<?, ?>> E registerControl(
            E panelControl, JEditView.Panels identifier) {
        view.registerPanel(panelControl.getView(), identifier.toString());
        return panelControl;
    }
    
    private void validatedFloorList(List<Floor> floors) {
        if( Objects.requireNonNull(floors).isEmpty()) {
            throw new IllegalArgumentException("Empty floor list!");
        }
        floorMap = new LinkedHashMap<>(floors.size());
        for( Floor floor : floors ) {
            floorMap.put(floor, new FloorViewModel(floor, control.getProject().getBuildingPlan()));
        }
    }
    
    private EditViewModel generateViewModel(Floor floor) {
        List<FloorViewModel> floorModelList = floorMap.values().stream().collect(Collectors.toList());
        return new EditViewModel(control.getProject().getBuildingPlan(), floorModelList.indexOf(floorMap.get(floor)));
    }
    
    public JEditView getView() {
        return view;
    }

    public void setEditMode(EditMode editMode) {
        currentFloorControl.setEditMode(editMode);
    }
    
    public void setZetObjectType(ZetObjectTypes type) {
        currentFloorControl.setZetObjectType(type);
    }
    
    public void set() {
        currentFloorControl.setEditMode(EditMode.SELECTION);
    }

    public void setControlledProject(List<Floor> floors) {
        validatedFloorList(floors);
        setCurrentFloor(floors.get(1));
    }
    
    public void setCurrentFloor(Floor floor) {
        if( !floorMap.containsKey(floor)) {
            throw new IllegalArgumentException( "Floor '" + floor + "' is not in the floor list!" );
        }
        currentFloor = floor;
        fc.setModel(currentFloor);
        currentFloorControl.setFloor(floor);
        getView().setEditViewModel(generateViewModel(floor));
    }
    
    public void moveCurrentFloorUp() {
        currentFloorControl.moveUp();
    }
    
    public void moveCurrentFloorDown() {
        currentFloorControl.moveDown();
    }
    
    public void createNewFloor() {
        Floor newFloor = control.createNewFloor();
        validatedFloorList(control.getProject().getBuildingPlan().getFloors());
        setCurrentFloor(newFloor);
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
