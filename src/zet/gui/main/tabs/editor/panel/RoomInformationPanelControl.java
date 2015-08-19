/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.ZControl;


/**
 * A control class for evacuation areas.
 * @author Jan-Philipp Kappmeier
 */
public class RoomInformationPanelControl extends AbstractInformationPanelControl<JRoomInformationPanel, Room, RoomChangeEvent> {
    private final ZControl control;
    
    public RoomInformationPanelControl(ZControl zcontrol) {
        super(new JRoomInformationPanel());
        this.control = zcontrol;
    }

    @Override
    public void changed(RoomChangeEvent c) {
        switch( c.getChangeType() ) {
            case Delete:
                System.err.println("Attempt to delete " + model);
                control.deletePolygon(model);
                break;
            case Name:
                System.err.println("Name");
                break;
            case RefineCoordinates:
                System.err.println("RefineCoordinates");
                control.refineRoomCoordinates(model.getPolygon(), 400);
                break;
        }
    }
}
