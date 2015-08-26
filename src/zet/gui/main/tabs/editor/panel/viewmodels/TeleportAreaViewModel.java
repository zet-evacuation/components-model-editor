package zet.gui.main.tabs.editor.panel.viewmodels;

import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.TeleportArea;
import java.util.Collections;
import java.util.List;

/**
 * @author Jan-Philipp Kappmeier
 */
public interface TeleportAreaViewModel {
    
    default List<EvacuationArea> getEvacuationAreas() {
        return Collections.emptyList();
    }

    default List<TeleportArea> getTeleportAreas() {
        return Collections.emptyList();

    }

    default public String getName() {
        return "TeleportArea";
    }

    default EvacuationArea getExitArea() {
        return new EvacuationArea(null, 1, "Exit");
    }

    default TeleportArea getTargetArea() {
        return new TeleportArea(null);
    }
}
