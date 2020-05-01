/* zet evacuation tool copyright (c) 2007-20 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
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
