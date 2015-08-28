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

import org.zetool.common.localization.LocalizationManager;
import de.zet_evakuierung.model.StairPreset;
import de.zet_evakuierung.model.ZLocalization;
import info.clearthought.layout.TableLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import static zet.gui.main.tabs.editor.panel.JInformationPanel.nfFloat;
import zet.gui.main.tabs.editor.panel.viewmodels.StairAreaViewModel;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class JStairAreaInformationPanel extends JInformationPanel<StairAreaControl, StairAreaViewModel> {

    private JTextField txtStairFactorUp;
    private JTextField txtStairFactorDown;

    private JLabel lblStairFactorUp;
    private JLabel lblStairFactorDown;
    /** A label for the stair speed preset. */
    private JLabel lblStairPreset;
    /** A selection box for the stair speed presets. */
    private JComboBox<StairPreset> cbxStairPresets;
    /** A label describing the current preset. */
    private JLabel lblStairPresetDescription;

    public JStairAreaInformationPanel(StairAreaViewModel model) {
        super(new double[]{TableLayout.FILL},
                new double[]{
                    TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.FILL
                }
        );
        init();
    }

    private void init() {
        int row = 0;

        // DelayFactor for going upwards
        lblStairFactorUp = new JLabel(loc.getString("Stair.FactorUp") + ":");
        this.add(lblStairFactorUp, "0, " + row++);
        txtStairFactorUp = new JTextField(" ");
        txtStairFactorUp.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //@//JZetWindow.setEditing( true );
            }

            @Override
            public void focusLost(FocusEvent e) {
                //@//JZetWindow.setEditing( false );
            }
        });
        txtStairFactorUp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        double factorUp = nfFloat.parse(txtStairFactorUp.getText()).doubleValue();
                        control.setFactorUp(factorUp);
                    } catch (ParseException ex) {
                        //@//ZETLoader.sendError( loc.getString( "gui.error.NonParsableFloatString" ) );
                    }
                }
            }
        });
        this.add(txtStairFactorUp, "0, " + row++);
        row++;

        // DelayFactor for going downwards
        lblStairFactorDown = new JLabel(loc.getString("Stair.FactorDown") + ":");
        this.add(lblStairFactorDown, "0, " + row++);
        txtStairFactorDown = new JTextField(" ");
        txtStairFactorDown.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //@//JZetWindow.setEditing( true );
            }

            @Override
            public void focusLost(FocusEvent e) {
                //@//JZetWindow.setEditing( false );
            }
        });
        txtStairFactorDown.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        double factorDown = nfFloat.parse(txtStairFactorDown.getText()).doubleValue();
                        control.setFactorDown(factorDown);
                    } catch (ParseException ex) {
                        //@//ZETLoader.sendError( loc.getString( "gui.error.NonParsableFloatString" ) );
                    }
                }
            }
        });
        this.add(txtStairFactorDown, "0, " + row++);
        row++;

        // Add combo box with presets
        lblStairPreset = new JLabel(loc.getString("Stair.Preset") + ":");
        cbxStairPresets = new JComboBox<>();

        cbxStairPresets.setRenderer(new ListCellRenderer<StairPreset>() {
            protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

            @Override
            public Component getListCellRendererComponent(JList<? extends StairPreset> list, StairPreset value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel presetLabel = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                presetLabel.setText(ZLocalization.loc.getString(presetLabel.getText()));
                return presetLabel;
            }

        });

        cbxStairPresets.addItem(StairPreset.Indoor);
        cbxStairPresets.addItem(StairPreset.Outdoor);
        this.add(lblStairPreset, "0, " + row++);
        this.add(cbxStairPresets, "0, " + row++);
        cbxStairPresets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                StairPreset preset = (StairPreset) cbxStairPresets.getSelectedItem();
                NumberFormat nf = LocalizationManager.getManager().getFloatConverter();
                txtStairFactorUp.setText(nf.format(preset.getSpeedFactorUp()));
                txtStairFactorDown.setText(nf.format(preset.getSpeedFactorDown()));
                control.setPreset(preset);
            }
        });
        lblStairPresetDescription = new JLabel(ZLocalization.loc.getString(((StairPreset) cbxStairPresets.getSelectedItem()).getText()));
        this.add(lblStairPresetDescription, "0, " + row++);
        row++;

    }

    @Override
    public void update() {
        txtStairFactorUp.setText(nfFloat.format(getModel().getSpeedFactorUp()));
        txtStairFactorDown.setText(nfFloat.format(getModel().getSpeedFactorDown()));
    }

    @Override
    public void localize() {
        loc.setPrefix("gui.EditPanel.");
        lblStairFactorUp.setText(loc.getString("Stair.FactorUp") + ":");
        lblStairFactorDown.setText(loc.getString("Stair.FactorDown") + ":");
        lblStairPreset.setText(loc.getString("Stair.Preset") + ":");
        lblStairPresetDescription.setText(ZLocalization.loc.getString(((StairPreset) cbxStairPresets.getSelectedItem()).getText()));
        loc.clearPrefix();
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
