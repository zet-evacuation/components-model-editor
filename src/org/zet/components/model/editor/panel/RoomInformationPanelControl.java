
package org.zet.components.model.editor.panel;

import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.viewmodel.AbstractInformationControl;
import org.zet.components.model.viewmodel.RoomControl;
import org.zet.components.model.viewmodel.RoomViewModel;
import org.zet.components.model.viewmodel.RoomViewModelImpl;

/**
 * Updates the displayed view model in the displayed panel and ensures that the corresponding model in the control
 * is synchrounous.
 * @author Jan-Philipp Kappmeier
 */
public class RoomInformationPanelControl extends AbstractInformationControl<JRoomInformationPanel,
        RoomControl, Room, RoomViewModel> {
    private final ZControl zcontrol;

    private RoomInformationPanelControl(JRoomInformationPanel view, RoomControl control, ZControl zcontrol) {
        super(view, control);
        this.zcontrol = zcontrol;
    }
    
    public static RoomInformationPanelControl create(ZControl zcontrol) {
        RoomControl result = new RoomControl(zcontrol);
        JRoomInformationPanel panel = generateView(result);
        return new RoomInformationPanelControl(panel, result, zcontrol);
    }

    private static JRoomInformationPanel generateView(RoomControl control) {
        return new JRoomInformationPanel(new RoomViewModel() {
        }, control);
    }

    @Override
    protected RoomViewModel getViewModel(Room m) {
        return new RoomViewModelImpl(m, zcontrol.getProject());
    }
}
