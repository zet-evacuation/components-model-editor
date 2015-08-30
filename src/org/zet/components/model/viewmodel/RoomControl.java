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
public class RoomControl extends AbstractControl<Room, RoomViewModel> {

    private Room model;

    public RoomControl(ZControl control) {
        super(control);
    }

    public void setName(String name) {
        System.err.println("Name");
        zcontrol.setRoomName(model, name);
    }

    public void delete() {
        System.err.println("Attempt to delete " + model);
        zcontrol.deletePolygon(model);
    }

    public void refineCoordinates() {
        System.err.println("RefineCoordinates");
        zcontrol.refineRoomCoordinates(model.getPolygon(), 400);
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
        return zcontrol.createNewArea(model, assignmentType, newPoints);
    }
}
