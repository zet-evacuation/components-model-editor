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
package org.zet.components.model.editor.panel;

import de.zet_evakuierung.model.EvacuationArea;
import info.clearthought.layout.TableLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.zet.components.model.viewmodel.EdgeControl;
import org.zet.components.model.viewmodel.EdgeViewModel;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class JEdgeInformationPanel extends JInformationPanel<EdgeControl, EdgeViewModel> {

    private JLabel lblEdgeType;
    private JLabel lblEdgeLength;
    private JLabel lblEdgeExitName;
    private JTextField txtEdgeExitName;

    public JEdgeInformationPanel(EdgeViewModel model, EdgeControl control) {
        super(new double[]{TableLayout.FILL},
                new double[]{
                    TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.FILL
                }, control);
        init();
    }

    private void init() {
        loc.setPrefix("Editview.Panel.");
        int row = 0;

        lblEdgeType = new JLabel("");
        this.add(lblEdgeType, "0, " + row++);
        row++;

        lblEdgeLength = new JLabel("");
        this.add(lblEdgeLength, "0, " + row++);
        row++;

        lblEdgeExitName = new JLabel("");
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
                    control.setExitName(txtEdgeExitName.getText());
                }
            }
        });
        this.add(txtEdgeExitName, "0, " + row++);
        row++;
        loc.clearPrefix();
    }

    @Override
    public void update() {
        txtEdgeExitName.setEnabled(false);

        updateTypeText();
        updateLengthText();
    }
    
    private void updateTypeText() {
        lblEdgeExitName.setText("");
        switch (getModel().getEdgeType()) {
            case EXIT:
                lblEdgeType.setText(loc.getString("EditView.Panel.Edge.Type.Exit"));
                txtEdgeExitName.setEnabled(true);
                EvacuationArea ea = getModel().getAssociatedExit();
                lblEdgeExitName.setText(loc.getString("EditView.Panel.Edge.ExitName"));
                txtEdgeExitName.setText(ea.getName());
                break;
            case FLOOR_PASSAGE:
                lblEdgeType.setText(loc.getString("EditView.Panel.Edge.Type.FloorPassage"));
                break;
            case PASSAGE:
                lblEdgeType.setText(loc.getString("EditView.Panel.Edge.Type.Passage"));
                break;
            case WALL:
                lblEdgeType.setText(loc.getString("EditView.Panel.Edge.Type.Wall"));
                break;
            case AREA_BOUNDARY:
                // easy peasy, this is an area boundry
                lblEdgeType.setText(loc.getString("EditView.Panel.Edge.Type.AreaBoundary"));
                break;
            default:
                lblEdgeType.setText(getModel().getEdgeType().toString());
                Logger.getGlobal().severe(String.format(loc.getString("Editview.Panel.Edge.TypeInvalid"),
                        getModel().getEdgeType()));
        }
    }
    
    private void updateLengthText() {
        String formattedLength = nfFloat.format((getModel().getLength()) * 0.001);
        String orientationText = getOrientationText();
        lblEdgeLength.setText(String.format("%s: %sm", orientationText, formattedLength));
    }

    @Override
    public void localize() {
        if( getModel() != null ) {
            updateTypeText();
            updateLengthText();
        }
    }

    private String getOrientationText() {
        switch (getModel().getOrientation()) {
            case Horizontal:
                return loc.getString("EditView.Panel.Edge.Orientation.Horizontal");
            case Vertical:
                return loc.getString("EditView.Panel.Edge.Orientation.Vertical");
            case Oblique:
                return loc.getString("EditView.Panel.Edge.Orientation.Oblique");
            default:
                return getModel().getOrientation().toString();
        }
    }

}
