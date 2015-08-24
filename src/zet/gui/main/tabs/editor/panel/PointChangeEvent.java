package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.PlanPoint;
import de.zet_evakuierung.model.PlanPolygon;
import de.zet_evakuierung.model.ZControl;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class PointChangeEvent extends AbstractChangeEvent<PointChangeEvent.PointChange> {
    private final PlanPoint currentPoint;
    private final PlanEdge currentEdge;

    public enum PointChange {
        Delete
    }

    public PointChangeEvent(Object source, PlanEdge edge, PlanPoint point) {
        super(source, PointChange.Delete);
        this.currentPoint = point;
        this.currentEdge = edge;
    }
    
    public void perform(ZControl control) {
        PlanPolygon poly = currentEdge.getAssociatedPolygon();
        control.deletePoint(poly, currentPoint);
    }
}
