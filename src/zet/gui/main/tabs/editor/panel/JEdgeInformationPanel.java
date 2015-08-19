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
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.DefaultEvacuationFloor;
import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.RoomEdge;
import de.zet_evakuierung.model.TeleportEdge;
import info.clearthought.layout.TableLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
//import zet.gui.main.JZetWindow;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class JEdgeInformationPanel extends JInformationPanel<EdgeChangeEvent, PlanEdge> {

    private JLabel lblEdgeType;
    private JLabel lblEdgeLength;
    private JLabel lblEdgeExitName;
    private JTextField txtEdgeExitName;

    public JEdgeInformationPanel() {
        super(new double[]{TableLayout.FILL},
                new double[]{
                    TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.FILL
                });
        init();
    }

    private void init() {
        int row = 0;

        lblEdgeType = new JLabel("Edge type");
        this.add(lblEdgeType, "0, " + row++);
        row++;

        lblEdgeLength = new JLabel("Länge:");
        this.add(lblEdgeLength, "0, " + row++);
        row++;

        lblEdgeExitName = new JLabel(loc.getString("Evacuation.Name"));
        this.add(lblEdgeExitName, "0, " + row++);
        txtEdgeExitName = new JTextField();
        txtEdgeExitName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //@//JZetWindow.setEditing( true );
            }

            @Override
            public void focusLost(FocusEvent e) {
                //@//JZetWindow.setEditing( false );
            }
        });
        txtEdgeExitName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Find the attached exit and set the name
//					if( getLeftPanel().getMainComponent().getSelectedEdge() instanceof TeleportEdge ) {
//						TeleportEdge te = (TeleportEdge)getLeftPanel().getMainComponent().getSelectedEdge();
//						if( ((Room)te.getLinkTarget().getAssociatedPolygon()).getAssociatedFloor() instanceof DefaultEvacuationFloor ) {
//							// we have an evacuation exit
//							Room r = (Room)te.getLinkTarget().getAssociatedPolygon();
//							EvacuationArea ea = r.getEvacuationAreas().get( 0 );
//							ea.setName( txtEdgeExitName.getText() );
//						}
//					}
                }
                //((EvacuationArea)getLeftPanel().getMainComponent().getSelectedPolygons().get( 0 ).getPlanPolygon()).setName( txtEdgeExitName.getText() );
            }
        });
        this.add(txtEdgeExitName, "0, " + row++);
        row++;
    }

    @Override
    public void update() {
        txtEdgeExitName.setEnabled(false);
        lblEdgeExitName.setText("");
        if (current instanceof RoomEdge) {
            if (current instanceof TeleportEdge) {
                TeleportEdge te = (TeleportEdge) current;
                if (((Room) te.getLinkTarget().getAssociatedPolygon()).getAssociatedFloor() instanceof DefaultEvacuationFloor) {
                    lblEdgeType.setText("Ausgang");
                    txtEdgeExitName.setEnabled(true);
                    // we have an evacuation exit
                    Room r = (Room) te.getLinkTarget().getAssociatedPolygon();
                    EvacuationArea ea = r.getEvacuationAreas().get(0);
                    lblEdgeExitName.setText("Ausgang");
                    txtEdgeExitName.setText(ea.getName());
                } else {
                    lblEdgeType.setText("Stockwerkübergang");
                }
            } else {
                if (((RoomEdge) current).isPassable()) {
                    lblEdgeType.setText("Durchgang");
                } else {
                    lblEdgeType.setText("Wand");
                }
            }
        } else {
            // easy peasy, this is an area boundry
            lblEdgeType.setText("Area-Begrenzung");
        }
        String formattedLength = nfFloat.format((current.length()) * 0.001);
        String orientationText = getOrientationText();
        lblEdgeLength.setText( String.format("%s: %sm", orientationText, formattedLength));
    }

    @Override
    public void localize() {
        loc.setPrefix("gui.EditPanel.");

        loc.clearPrefix();
    }

    private String getOrientationText() {
        if( current.isHorizontal()) {
            return "Breite";
        } else if (current.isVertical()) {
            return "Höhe";
        } else {
            return "Länge";
        }
    }

}
