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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.zet.components.model.editor.panel;

import info.clearthought.layout.TableLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.zet.components.model.viewmodel.EvacuationAreaControl;
import org.zet.components.model.viewmodel.EvacuationAreaViewModel;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
@SuppressWarnings("serial")
public class JEvacuationAreaInformationPanel extends JInformationPanel<EvacuationAreaControl, EvacuationAreaViewModel> {

    private JLabel lblEvacuationAreaName;
    private JTextField txtEvacuationAreaName;
    private JLabel lblEvacuationAttractivity;
    private JTextField txtEvacuationAttractivity;

    public JEvacuationAreaInformationPanel(EvacuationAreaControl control) {
        super(new double[]{TableLayout.FILL},
                new double[]{TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, 20, TableLayout.FILL
                }, control);
        init();
    }

    private void init() {
        loc.setPrefix("Editview.Panel.");
        int row = 0;
        lblEvacuationAreaName = new JLabel(loc.getString("Evacuation.Name"));
        this.add(lblEvacuationAreaName, "0, " + row++);
        txtEvacuationAreaName = new JTextField();
        txtEvacuationAreaName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //@//JZetWindow.setEditing( true );
            }

            @Override
            public void focusLost(FocusEvent e) {
                //@//JZetWindow.setEditing( false );
            }
        });
        txtEvacuationAreaName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        control.setName(txtEvacuationAreaName.getText().trim());
                    }
                }
            }
        });
        this.add(txtEvacuationAreaName, "0, " + row++);
        row++;

        // Attractivity
        lblEvacuationAttractivity = new JLabel(loc.getString("Evacuation.Attractivity"));
        this.add(lblEvacuationAttractivity, "0, " + row++);

        txtEvacuationAttractivity = new JTextField(" ");
        txtEvacuationAttractivity.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //@//JZetWindow.setEditing( true );
            }

            @Override
            public void focusLost(FocusEvent e) {
                //@//JZetWindow.setEditing( false );
            }
        });
        txtEvacuationAttractivity.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        int attractivity = nfInteger.parse(txtEvacuationAttractivity.getText()).intValue();
                        control.setAttractivity(attractivity);
                    } catch (ParseException ex) {
                        Logger.getGlobal().severe(
                                String.format(loc.getString("Editview.Panel.Evacuation.AttractivityInvalid"),
                                txtEvacuationAttractivity.getText()));
                    }
                }
            }
        });
        this.add(txtEvacuationAttractivity, "0, " + row++);
        row++;
        loc.clearPrefix();
    }

    @Override
    public void update() {
        txtEvacuationAreaName.setText(getModel().getName());
        txtEvacuationAttractivity.setText(nfInteger.format(getModel().getAttractivity()));
    }

    @Override
    public void localize() {
        loc.setPrefix("Editview.Panel.");
        lblEvacuationAreaName.setText(loc.getString("Evacuation.Name"));
        lblEvacuationAttractivity.setText(loc.getString("Evacuation.Attractivity"));
        loc.clearPrefix();
    }

    /**
     * Prohibits serialization.
     */
    private synchronized void writeObject(java.io.ObjectOutputStream s) throws IOException {
        throw new UnsupportedOperationException("Serialization not supported");
    }
}
