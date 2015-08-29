package org.zet.components.model.editor.floor.popup;

import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class Popups {

    public static enum PopupType {
        EDGE, POINT, POLYGON
    }
    
    private final Map<PopupType,Object> popupMap = new EnumMap<>(PopupType.class);

    public Popups() {
        popupMap.put(PopupType.EDGE, new EdgePopup());
        popupMap.put(PopupType.POINT, new PointPopup());
        popupMap.put(PopupType.POLYGON, new PolygonPopup());
    }

    public EdgePopup getEdgePopup() {
        return (EdgePopup)popupMap.get(PopupType.EDGE);
    }

    public PointPopup getPointPopup() {
        return (PointPopup)popupMap.get(PopupType.POINT);
    }

    public PolygonPopup getPolygonPopup() {
        return (PolygonPopup)popupMap.get(PopupType.POLYGON);
    }
}
