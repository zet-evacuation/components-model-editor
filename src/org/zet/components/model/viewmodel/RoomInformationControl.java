package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.AssignmentType;
import de.zet_evakuierung.model.PlanPoint;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.ZControl;
import java.util.ArrayList;
import java.util.List;
import org.zet.components.model.editor.panel.JRoomInformationPanel;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class RoomInformationControl extends AbstractInformationControl<JRoomInformationPanel, RoomViewModel> {

    private Room model;

    private RoomInformationControl(JRoomInformationPanel view, ZControl control) {
        super(view, control);
    }

    public static RoomInformationControl create(ZControl control) {
        JRoomInformationPanel panel = generateView();
        RoomInformationControl result = new RoomInformationControl(panel, control);
        panel.setControl(result);
        return result;
    }

    private static JRoomInformationPanel generateView() {
        return new JRoomInformationPanel(new RoomViewModel() {
        });
    }

    public void setModel(Room model) {
        getView().setModel(new RoomViewModelImpl(model, control.getProject()));
        this.model = model;
    }

    public void setName(String name) {
        System.err.println("Name");
        control.setRoomName(model, name);
    }

    public void delete() {
        System.err.println("Attempt to delete " + model);
        control.deletePolygon(model);
    }

    public void refineCoordinates() {
        System.err.println("RefineCoordinates");
        control.refineRoomCoordinates(model.getPolygon(), 400);
    }

    /**
     * Fills the complete room with an assignment area of the given type.
     * @param assignmentType the type of the new assignment area
     * @return the newly created assignment area
     */
    public AssignmentArea fillWithAssignmentArea(AssignmentType assignmentType) {
        List<PlanPoint> points = model.getPolygon().getPolygonPoints();
        ArrayList<PlanPoint> newPoints = new ArrayList<>();

        points.stream().forEach((point) -> {
            newPoints.add(new PlanPoint(point.getXInt(), point.getYInt()));
        });
        return control.createNewArea(model, assignmentType, newPoints);
    }
}
