package org.zet.components.model.editor.style;

import de.zet_evakuierung.model.AreaType;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.util.EnumMap;
import javax.swing.JLabel;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public interface GraphicsStyle {

    /**
     * Returns the paint used for drawing help lines in floors.
     *
     * @return the default help line color {@link Color#LIGHT_GRAY}
     */
    default Paint getEditorRasterColor() {
        return Color.LIGHT_GRAY;
    }

    /**
     * Returns the raster size for the helping raster on the background of any
     * floor.
     *
     * @return the raster size for the helping raster on the background of any
     * floor
     */
    default int getRasterSize() {
        return 400;
    }

    /**
     * Returns the size of an smaller helping raster visible on the background
     * of any floor. This raster is only visible, if its size is a divisor of
     * the larger one and strictly smaller.
     *
     * @return the size of an smaller helping raster
     */
    default int getRasterSizeSmall() {
        return 400;
    }

    /**
     * Returns the paint style for the grid.
     * @return the paint style for the grid
     */
    default RasterPaintStyle getRasterPaintType() {
        return RasterPaintStyle.POINTS;
    }
    

    /**
     * Returns the size of the raster (used to snap the cursor) of the editor.
     * This does not influence the rasterization to a cellular automaton.
     *
     * @return the current raster size (as stored in the property container)
     */
    default int getRasterSizeSnap() {
        return 400;
    }

    default int getBorderWidth() {
        return 800;
    }

    /**
     * The size of a point in the editor.
     *
     * @return the point size
     */
    default int getPointSize() {
        return 5;
    }

    default int getLineWidth() {
        return 3;
    }
    
    default Color getWallColor() {
        return Color.BLACK;
    }
    
    default Color getColorForArea(AreaType areaType) {
        EnumMap<AreaType, Color> colorForArea = new EnumMap<>(AreaType.class);
        colorForArea.put(AreaType.Assignment, Color.decode("#0066FF"));
        colorForArea.put(AreaType.Delay, Color.RED);
        colorForArea.put(AreaType.Evacuation, Color.GREEN);
        colorForArea.put(AreaType.Inaccessible, Color.BLACK);
        colorForArea.put(AreaType.Save, Color.YELLOW);
        colorForArea.put(AreaType.Stair, Color.ORANGE);
        colorForArea.put(AreaType.Teleport, Color.BLUE);
        return colorForArea.get(areaType);
    }

    default Font getLabelFont() {
        return new JLabel().getFont();
    }
    
    default Color getEditorBackgroundColor() {
        return Color.WHITE;
    }
    
    default Color getDragNodeColor() {
        return Color.GRAY;
    }

    default Color getTeleportEdgeColor() {
        return Color.blue;
    }

    default public Color getHighlightColor() {
        return Color.black;
    }
}
