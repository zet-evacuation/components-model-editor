package zet.gui.main.menu.popup;

import de.zet_evakuierung.model.ZControl;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class Popups {

    public static enum Types {
        Edge, Point, Polygon
    }
    
    private final Map popupMap = new EnumMap(Types.class);
    
    public boolean isPopupEnabled() {
        return true;
    }

    public Popups(ZControl control) {
        popupMap.put(Types.Edge, new EdgePopup());
        popupMap.put(Types.Point, new PointPopup());
        popupMap.put(Types.Polygon, new PolygonPopup(control));
    }

    public EdgePopup getEdgePopup() {
        return (EdgePopup)popupMap.get(Types.Edge);
    }

    public PointPopup getPointPopup() {
        return (PointPopup)popupMap.get(Types.Point);
    }

    public PolygonPopup getPolygonPopup() {
        return (PolygonPopup)popupMap.get(Types.Polygon);
    }
}
