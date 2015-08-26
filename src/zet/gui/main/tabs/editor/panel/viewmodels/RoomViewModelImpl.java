package zet.gui.main.tabs.editor.panel.viewmodels;

import de.zet_evakuierung.model.Project;
import de.zet_evakuierung.model.Room;

/**
 * The assignment area view model makes available all information necessary to display an assignment area.
 * @author Jan-Philipp Kappmeier
 */
public class RoomViewModelImpl extends AbstractViewModel<Room> implements RoomViewModel {
    private final Project project;
    
    public RoomViewModelImpl(Room r, Project p) {
        super(r);
        this.project = p;
    }

    @Override
    public String getName() {
        return model.getName();
    }

    @Override
    public double areaMeter() {
        return Math.round(model.getPolygon().areaMeter() * 100) / 100.0;
    }
    
}
