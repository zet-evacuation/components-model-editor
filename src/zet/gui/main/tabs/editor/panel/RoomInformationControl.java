package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.ZControl;
import zet.gui.main.tabs.editor.panel.viewmodels.RoomViewModel;
import zet.gui.main.tabs.editor.panel.viewmodels.RoomViewModelImpl;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class RoomInformationControl extends AbstractInformationPanelControl<JRoomInformationPanel, RoomViewModel> {

    private Room model;

    public RoomInformationControl(ZControl control) {
        super(generateView(), control);
        getView().setControl(this);
    }

    private static JRoomInformationPanel generateView() {
        return new JRoomInformationPanel(new RoomViewModel() {
        });
    }

    public void setModel(Room model) {
        getView().setModel(new RoomViewModelImpl(model, control.getProject()));
        this.model = model;
    }

    void setName(String name) {
        System.err.println("Name");
        control.setRoomName(model, name);
    }

    void delete() {
        System.err.println("Attempt to delete " + model);
        control.deletePolygon(model);
    }

    void refineCoordinates() {
        System.err.println("RefineCoordinates");
        control.refineRoomCoordinates(model.getPolygon(), 400);
    }
}
