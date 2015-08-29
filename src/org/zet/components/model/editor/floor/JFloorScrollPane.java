/* zet evacuation tool copyright (c) 2007-14 zet evacuation team
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
package org.zet.components.model.editor.floor;

import org.zet.components.model.editor.floor.AbstractFloor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.zet.components.model.editor.CoordinateTools;
import org.zet.components.model.editor.localization.EditorLocalization;
import org.zetool.common.localization.Localized;
import org.zetool.components.JCorner;
import org.zetool.components.JRuler;
import org.zetool.components.JZoomableRuler;

/**
 * A scroll pane containing a floor.
 * @param <T>
 * @author Jan-Philipp Kappmeier
 */
public class JFloorScrollPane<T extends AbstractFloor> extends JScrollPane implements Localized {
    /** The floor that is drawn. */
    private T floorPanel;
    /** The upper ruler. */
    private JZoomableRuler topRuler;
    /** The left ruler. */
    private JZoomableRuler leftRuler;
    /** A button used to change the units of the rulers. */
    private JButton unitButton;
    /** The localization string for the unit button. */
    private String locString = "gui.EditPanel.Unit.Meter";
    private static final EnumMap<JRuler.RulerDisplayUnit, RulerStyle> styles = new EnumMap<>(JRuler.RulerDisplayUnit.class);

    static {
        styles.put(JRuler.RulerDisplayUnit.CENTIMETER, new RulerStyle(JRuler.RulerDisplayUnit.DECIMETER, 4, 1, "gui.EditPanel.Unit.Decimeter"));
        styles.put(JRuler.RulerDisplayUnit.DECIMETER, new RulerStyle(JRuler.RulerDisplayUnit.METER, 2, 1, "gui.EditPanel.Unit.Meter"));
        styles.put(JRuler.RulerDisplayUnit.METER, new RulerStyle(JRuler.RulerDisplayUnit.INCH, 10, 5, "gui.EditPanel.Unit.Inch"));
        styles.put(JRuler.RulerDisplayUnit.INCH, new RulerStyle(JRuler.RulerDisplayUnit.FOOT, 5, 1, "gui.EditPanel.Unit.Foot"));
        styles.put(JRuler.RulerDisplayUnit.FOOT, new RulerStyle(JRuler.RulerDisplayUnit.YARD, 2, 1, "gui.EditPanel.Unit.Yard"));
        styles.put(JRuler.RulerDisplayUnit.YARD, new RulerStyle(JRuler.RulerDisplayUnit.CENTIMETER, 40, 10, "gui.EditPanel.Unit.Centimeter"));
    }
    
    public JFloorScrollPane( T floorPanel ) {
        super( floorPanel );
        this.floorPanel = floorPanel;
        topRuler = new JZoomableRuler( JRuler.RulerOrientation.HORIZONTAL, JRuler.RulerDisplayUnit.METER );
        topRuler.setBigScaleStep( 2 );
        topRuler.setSmallScaleStep( 1 );
        topRuler.setZoomFactor( 0.1 );
        topRuler.setPreferredWidth( 2400 );
        leftRuler = new JZoomableRuler( JRuler.RulerOrientation.VERTICAL, JRuler.RulerDisplayUnit.METER );
        leftRuler.setBigScaleStep( 2 );
        leftRuler.setSmallScaleStep( 1 );
        leftRuler.setZoomFactor( 0.1 );
        leftRuler.setPreferredHeight( 600 );
        setColumnHeaderView( topRuler );
        setRowHeaderView( leftRuler );

        JPanel buttonCorner = new JPanel();
        buttonCorner.setBackground( Color.white );
        unitButton = new JButton( "m" );
        unitButton.setFont( new Font( "SansSerif", Font.PLAIN, 11 ) );
        unitButton.setMargin( new Insets( 2, 2, 2, 2 ) );
        unitButton.setActionCommand( "unit" );
        
       
        unitButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if( "unit".equals( e.getActionCommand() ) ) {
                    RulerStyle style = styles.get(topRuler.getDisplayUnit());
                    topRuler.setDisplayUnit( style.selectedUnit );
                    topRuler.setBigScaleStep( style.bigStep );
                    topRuler.setSmallScaleStep( style.smallStep );
                    leftRuler.setDisplayUnit( style.selectedUnit );
                    leftRuler.setBigScaleStep( style.bigStep );
                    leftRuler.setSmallScaleStep( style.smallStep );
                    topRuler.repaint();
                    leftRuler.repaint();
                    unitButton.setText( style.selectedUnit.toString() );
                    unitButton.setToolTipText( EditorLocalization.LOC.getStringWithoutPrefix( locString ) );
                }
            }
        });
        buttonCorner.add( unitButton );
        
        System.out.println(floorPanel.xOffset);
        Point p = floorPanel.getMinCoordinate();
        Point p2 = floorPanel.getMaxCoordinate();
        System.out.println("Min: " + p);
        System.out.println("Max: " + p2);
        
        
        unitButton.setToolTipText( EditorLocalization.LOC.getStringWithoutPrefix( locString ) );

        setCorner( JScrollPane.UPPER_LEFT_CORNER, buttonCorner );
        setCorner( JScrollPane.UPPER_RIGHT_CORNER, new JCorner() );
        setCorner( JScrollPane.LOWER_RIGHT_CORNER, new JCorner() );
        setCorner( JScrollPane.LOWER_RIGHT_CORNER, new JCorner() );
    }

    public final void setMainComponent( T floorPanel) {
        setViewportView( floorPanel );
        this.floorPanel = floorPanel;
    }

    public final T getMainComponent() {
        return floorPanel;
    }

    public JZoomableRuler getLeftRuler() {
        return leftRuler;
    }

    public JZoomableRuler getTopRuler() {
        return topRuler;
    }

    @Override
    public void localize() {
        unitButton.setToolTipText( EditorLocalization.LOC.getStringWithoutPrefix( locString ) );
    }

    /**
     * @param zoomFactor A double in the range [0;1]
     */
    public void setZoomFactor( double zoomFactor ) {
        CoordinateTools.setZoomFactor( zoomFactor );
        topRuler.setZoomFactor( zoomFactor );
        topRuler.repaint();
        leftRuler.setZoomFactor( zoomFactor );
        leftRuler.repaint();
    }

    private static class RulerStyle {

        private final int smallStep;
        private final int bigStep;
        private final String locString;
        private final JRuler.RulerDisplayUnit selectedUnit;

        public RulerStyle(JRuler.RulerDisplayUnit selectedUnit, int smallStep, int bigStep, String locString) {
            this.smallStep = smallStep;
            this.bigStep = bigStep;
            this.locString = locString;
            this.selectedUnit = selectedUnit;
        }

    };
}
