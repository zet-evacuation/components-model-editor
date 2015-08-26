
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.Floor;
import de.zet_evakuierung.model.ZControl;
import java.awt.Rectangle;
import zet.gui.main.tabs.editor.control.FloorViewModel;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class FloorPanelControl extends AbstractInformationPanelControl<JFloorInformationPanel, FloorViewModel> {

    private Floor model;

    public FloorPanelControl(ZControl control) {
        super(new JFloorInformationPanel(), control);
        getView().setControl(this);
    }

    public void setModel(Floor model) {
        getView().setModel(new FloorViewModel(model));
        this.model = model;
    }

    void rename(String floorName) {
        boolean success = control.renameFloor(model, floorName);
        if (!success) {
            System.out.println("Renaming failed, floor with that name already exists");
        } else {
            System.out.println("Renaiming successful");
        }
    }

    void moveDown() {
        System.out.println("Moving down floor.");
        control.moveFloorDown(model);
    }

    void moveUp() {
        System.out.println("Moving up floor");
        control.moveFloorUp(model);
    }

    void rasterize() {
        System.out.println("Rassterize");
        control.getProject().getBuildingPlan().rasterize();
    }

    void setDimension(Rectangle floorSize) {
        System.out.println("Change dimension");
        control.setFloorSize(model, floorSize);
    }

}
