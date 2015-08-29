/* zet evacuation tool copyright (c) 2007-15 zet evacuation team
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.zet.components.model.editor.floor.base;

import de.zet_evakuierung.model.Floor;
import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;
import javax.swing.JPanel;
import org.zet.components.model.editor.CoordinateTools;
import org.zet.components.model.editor.floor.FloorViewModel;

/**
 * A panel displaying a {@link Floor}.
 * @author Jan-Philipp Kappmeier
 */
@SuppressWarnings("serial")
public class AbstractFloor extends JPanel {
    // Variables for floor viewing
    protected Point floorMin = new Point(), floorMax = new Point();
    protected int xOffset, yOffset;
    private GraphicsStyle graphicsStyle;
    private final Rectangle defaultArea = new Rectangle(0, 0, 8000, 6000);
    private FloorViewModel floorModel;

    public AbstractFloor(FloorViewModel floorModel) {
        this(floorModel, new GraphicsStyle() {
        });
    }

    public AbstractFloor(FloorViewModel floorModel, GraphicsStyle graphicsStyle) {
        super();
        this.floorModel = Objects.requireNonNull( floorModel, "Floor may not be null." );
        this.graphicsStyle = graphicsStyle;
        enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
        reloadValues();
        floorMin = new Point(defaultArea.x, defaultArea.y);
        floorMax = new Point(defaultArea.x + defaultArea.width, defaultArea.y + defaultArea.height);
    }

    /**
     * Updates the floor offset values. The size is, if necessary, enlarged by
     * the value given by getBorderWidth().
     *
     * @param floor The Floor that should be displayed
     */
    public void updateOffsets(FloorViewModel floor) {
        Rectangle floorPosition = floor.getLocation();
        
        floorMax = new Point(Math.max(defaultArea.x + defaultArea.width, floorPosition.x + floorPosition.width),
                Math.max(defaultArea.y + defaultArea.height, floorPosition.y + floorPosition.height));
        floorMin = new Point(Math.min(defaultArea.x, floorPosition.x), Math.min(defaultArea.y, floorPosition.x));
        
        System.out.println("Floor sized to " + floorMax.x + " " + floorMax.y + " " + floorMin.x + " " + floorMin.y);

        int borderWidth = graphicsStyle.getBorderWidth();
        CoordinateTools.setOffsets(floorMin.x - borderWidth, floorMin.y - borderWidth);
        setPreferredSize(new Dimension(
                CoordinateTools.translateToScreen(floorMax.x - floorMin.x + 2 * borderWidth),
                CoordinateTools.translateToScreen(floorMax.y - floorMin.y + 2 * borderWidth)));
    }
    
    public Point getMinCoordinate() {
        Point offset = CoordinateTools.getOffsets();
        return new Point(-offset.x, -offset.y);
    }
    
    public Point getMaxCoordinate() {
        Point min = getMinCoordinate();
        return new Point(min.x + (floorMax.x - floorMax.x) + graphicsStyle.getBorderWidth(),
                min.y + (floorMax.y - floorMin.y) + graphicsStyle.getBorderWidth());
    }


    /**
     * Clears the background of the floor and draws the raster.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Clear background - do this only once - the polygons and edges won't do it.
        g2.setPaint(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Draws a raster on the floor. The raster can be drawn as lines or points.
     * Additionally, it is possible to draw a smaller helping grid as points.
     *
     * @param g2
     */
    public void drawRaster(Graphics2D g2) {
        final Point p = CoordinateTools.translateToModel(0, 0);
        int bigRaster = graphicsStyle.getRasterSize();
        final int startx = CoordinateTools.translateToScreen((int) (Math.floor((double) p.x / bigRaster)) * bigRaster - p.x);
        final int starty = CoordinateTools.translateToScreen((int) (Math.floor((double) p.y / bigRaster)) * bigRaster - p.y);

        if ( graphicsStyle.getRasterPaintType() == RasterPaintStyle.LINES) {
            g2.setPaint(graphicsStyle.getEditorRasterColor());
            final int rasterWidth = CoordinateTools.translateToScreen(bigRaster);
            for (int i = rasterWidth; i < getHeight(); i += rasterWidth) {
                g2.drawLine(0, i, getWidth(), i);
            }
            for (int i = rasterWidth; i < getWidth(); i += rasterWidth) {
                g2.drawLine(i, 0, i, getHeight());
            }
        } else if (graphicsStyle.getRasterPaintType() == RasterPaintStyle.POINTS) {
            g2.setPaint(graphicsStyle.getEditorRasterColor());
            final int rasterWidth = CoordinateTools.translateToScreen(bigRaster);
            for (int i = starty; i < getHeight(); i += rasterWidth) {
                for (int j = startx; j < getWidth(); j += rasterWidth) {
                    g2.fillRect(j - 1, i - 1, 3, 3);
                }
            }
            g2.drawRect(1, 2, 3, 4);
        }
        // draw smaller one
        int smallRaster = graphicsStyle.getRasterSizeSmall();
        if (graphicsStyle.getRasterPaintType() != RasterPaintStyle.NOTHING && bigRaster % smallRaster == 0 && smallRaster < bigRaster) {
            g2.setPaint(graphicsStyle.getEditorRasterColor());
            final int rasterWidth = CoordinateTools.translateToScreen(smallRaster);
            for (int i = starty; i < getHeight(); i += rasterWidth) {
                for (int j = startx; j < getWidth(); j += rasterWidth) {
                    g2.drawRect(j, i, 0, 0);
                }
            }
            g2.drawRect(1, 2, 3, 4);
        }
    }

    /**
     * Mouse Motion Event Handler
     *
     * @param e
     */
    @Override
    protected void processMouseMotionEvent(MouseEvent e) {
        final Point real = CoordinateTools.translateToModel(new Point(e.getX(), e.getY()));
        //JZetWindow.sendMouse( real );
    }

    /**
     * Reloads the minimum x and y positions, the border width and the grind
     * sizes and updates the values. Call this, if some of them have been
     * changed.
     */
    final public void reloadValues() {
        //@//min_x = PropertyContainer.getInstance().getAsInt( "editor.options.view.size.minx" );
        //@//min_y = PropertyContainer.getInstance().getAsInt( "editor.options.view.size.miny" );
        //@//max_x = PropertyContainer.getInstance().getAsInt( "editor.options.view.size.maxx" );
        //@//max_y = PropertyContainer.getInstance().getAsInt( "editor.options.view.size.maxy" );
        //@//borderWidth = PropertyContainer.getInstance().getAsInt( "editor.options.view.size.border" );

    //@//rasterSnap = ZETProperties.getRasterSizeSnap();
        //@//bigRaster = ZETProperties.getRasterSize();
        //@//smallRaster = ZETProperties.getRasterSizeSmall();
    }

    /**
     * Returns the model for the floor view.
     * @return the model for the floor view
     */
    public FloorViewModel getFloorModel() {
        return floorModel;
    }

    /**
     * Sets a new floor model. Can be overridden and made public by implementations of the abstract flow, if they
     * support changing the model.
     * @param floorModel 
     */
    protected void setFloorModel( FloorViewModel floorModel ) {
        this.floorModel = floorModel;
    }

    /** Prohibits serialization. */
    private synchronized void writeObject(java.io.ObjectOutputStream s) throws IOException {
        throw new UnsupportedOperationException("Serialization not supported");
    }

    /** Prohibits serialization. */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Serialization not supported");
    }
}
