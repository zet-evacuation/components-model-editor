package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.Floor;
import de.zet_evakuierung.model.ZControl;
import java.awt.Rectangle;
import org.zet.components.model.editor.panel.JFloorInformationPanel;
import org.zet.components.model.editor.floor.FloorViewModel;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class FloorPanelControl extends AbstractInformationControl<JFloorInformationPanel, FloorViewModel> {

    private Floor model;

    private FloorPanelControl(JFloorInformationPanel view, ZControl control) {
        super(view, control);
    }

    public static FloorPanelControl create(ZControl control) {
        JFloorInformationPanel panel = generateView();
        FloorPanelControl result = new FloorPanelControl(panel, control);
        panel.setControl(result);
        return result;
    }

    private static JFloorInformationPanel generateView() {
        return new JFloorInformationPanel();
    }

    public void setModel(Floor model) {
        getView().setModel(new FloorViewModel(model));
        this.model = model;
    }

    public void rename(String floorName) {
        boolean success = control.renameFloor(model, floorName);
        if (!success) {
            System.out.println("Renaming failed, floor with that name already exists");
        } else {
            System.out.println("Renaiming successful");
        }
    }

    public void moveDown() {
        System.out.println("Moving down floor.");
        control.moveFloorDown(model);
    }

    public void moveUp() {
        System.out.println("Moving up floor");
        control.moveFloorUp(model);
    }

    public void rasterize() {
        System.out.println("Rassterize");
        control.getProject().getBuildingPlan().rasterize();
    }

    public void setDimension(Rectangle floorSize) {
        System.out.println("Change dimension");
        control.setFloorSize(model, floorSize);
    }

}
