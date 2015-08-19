/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.FloorInterface;
import de.zet_evakuierung.model.ZControl;
import java.awt.Rectangle;

/**
 * A control class for evacuation areas.
 * @author Jan-Philipp Kappmeier
 */
public class FloorPanelControl extends AbstractInformationPanelControl<JFloorInformationPanel, FloorInterface, FloorChangeEvent> {
    private final ZControl control;
    public FloorPanelControl(ZControl control) {
        super(new JFloorInformationPanel());
        this.control = control;
    }

    @Override
    public void changed(FloorChangeEvent c) {
        switch( c.getChangeType() ) {
            case Rename:
                System.out.println("Renaming floor to " + getView().getFloorName());
                boolean success = control.renameFloor(model, getView().getFloorName());
                if (!success) {
                    System.out.println("Renaming failed, floor with that name already exists");
                } else {
                    System.out.println("Renaiming successful");
                }
                break;
            case MoveDown:
                System.out.println("Moving down floor.");
                control.moveFloorDown(model);
                break;
            case MoveUp:
                System.out.println("Moving up floor");
                control.moveFloorUp(model);
                break;
            case Dimension:
                System.out.println("Change dimension");
                Rectangle floorSize = getView().getFloorSize();
                control.setFloorSize(model, floorSize);
                break;
            case Rasterize:
                System.out.println("Rassterize");
                control.getProject().getBuildingPlan().rasterize();
                break;
            default:
                throw new AssertionError("Unknown event type: " + c.getChangeType());
        }
    }

    public void moveFloorUp(FloorInterface floor) {
        
        //final int oldIndex = editview.getFloorID();
        //control.moveFloorUp(editview.getFloorID() + (ZETProperties.isDefaultFloorHidden() ? 1 : 0));
        //editview.setFloor(oldIndex + 1);
        control.moveFloorUp(floor);
    }

//    public void moveFloorDown(FloorInterface floor) {
//        final int oldIndex = editview.getFloorID();
//        control.moveFloorDown(editview.getFloorID() + (ZETProperties.isDefaultFloorHidden() ? 1 : 0));
//        editview.setFloor(oldIndex - 1);
//
//    }

}
