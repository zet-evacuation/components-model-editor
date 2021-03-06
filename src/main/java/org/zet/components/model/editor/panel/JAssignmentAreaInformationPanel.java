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
package org.zet.components.model.editor.panel;

import de.zet_evakuierung.model.AssignmentType;
import de.zet_evakuierung.model.EvacuationArea;
import info.clearthought.layout.TableLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.zetool.components.framework.Button;
import org.zet.components.model.editor.selectors.NamedComboBoxRenderer;
import static org.zet.components.model.editor.panel.JInformationPanel.nfInteger;
import org.zet.components.model.viewmodel.AssignmentAreaControl;
import org.zet.components.model.viewmodel.AssignmentAreaViewModel;

/**
 * Displays information about an assignment area and allows to edit it. The changes are submitted to the viewmodel
 * which makes sure that the changes are applied to the model.
 * @author Jan-Philipp Kappmeier
 */
public class JAssignmentAreaInformationPanel extends JInformationPanel<AssignmentAreaControl, AssignmentAreaViewModel> {

    private JLabel lblAssignmentType;
    private JLabel lblAssignmentEvacueeNumber;
    private JButton btnAssignmentSetDefaultEvacuees;
    private JLabel lblAreaSizeDesc;
    private JLabel lblMaxPersonsDesc;
    private JLabel lblMaxPersons;
    private JLabel lblMaxPersonsWarning;
    private JLabel lblPreferredExit;
    private JTextField txtNumberOfPersons;
    private JComboBox<EvacuationArea> cbxPreferredExit;
    private JComboBox<AssignmentType> cbxAssignmentType;
    private JLabel lblAreaSize;

    public JAssignmentAreaInformationPanel(AssignmentAreaViewModel model, AssignmentAreaControl control) {
        super(new double[]{TableLayout.FILL},
                new double[]{ //Rows
                    TableLayout.PREFERRED, TableLayout.PREFERRED, 20, // Assignment type
                    TableLayout.PREFERRED, TableLayout.PREFERRED, 20, // Number of evacuees
                    TableLayout.PREFERRED, 20, // Button for default number of evacuees
                    TableLayout.PREFERRED, TableLayout.PREFERRED, 20, // Preferred exit
                    TableLayout.PREFERRED, TableLayout.PREFERRED, 10, // Area
                    TableLayout.PREFERRED, TableLayout.PREFERRED, 20, // Max number of persons for area
                    TableLayout.PREFERRED, // Warning
                    TableLayout.FILL // Fill the rest of space
                }, control);
        init();
    }

    private void init() {
        loc.setPrefix("Editview.Panel.");
        int row = 0;

        lblAssignmentType = new JLabel(loc.getString("Assignment.Type"));
        this.add(lblAssignmentType, "0, " + row++);
        cbxAssignmentType = new JComboBox<>();
        cbxAssignmentType.setRenderer(new NamedComboBoxRenderer<>());
        this.add(cbxAssignmentType, "0, " + row++);
        row++;

        // Number of Evacuees
        lblAssignmentEvacueeNumber = new JLabel(loc.getString("Assignment.Persons"));
        this.add(lblAssignmentEvacueeNumber, "0, " + row++);
        txtNumberOfPersons = new JTextField("10");
        txtNumberOfPersons.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //@//JZetWindow.setEditing( true );
            }

            @Override
            public void focusLost(FocusEvent e) {
                //@//JZetWindow.setEditing( false );
            }
        });

        txtNumberOfPersons.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        int typedPersons = nfInteger.parse(txtNumberOfPersons.getText()).intValue();
                        control.setEvacuees(typedPersons);
                    } catch (ParseException ex) {
                        Logger.getGlobal().info(String.format(loc.getString("Editview.Panel.Assignment.PersonsInvalid"),
                                txtNumberOfPersons.getText()));
                    }
                }
            }
        });
        this.add(txtNumberOfPersons, "0, " + row++);
        row++;
        btnAssignmentSetDefaultEvacuees = Button.newButton(loc.getString("Assignment.SetDefaultEvacuees"),
                loc.getString("Assignment.SetDefaultEvacuees.ToolTip"));
        btnAssignmentSetDefaultEvacuees.setToolTipText(loc.getString("Assignment.SetDefaultEvacuees.ToolTip"));
        btnAssignmentSetDefaultEvacuees.addActionListener( e -> control.setStandardEvacuees() );
        this.add(btnAssignmentSetDefaultEvacuees, "0, " + row++);
        row++;

        // Preferred-Exit-Selector
        lblPreferredExit = new JLabel(loc.getString("Assignment.PreferredExit"));
        this.add(lblPreferredExit, "0, " + row++);
        cbxPreferredExit = new JComboBox<>();
        cbxPreferredExit.setRenderer(new NamedComboBoxRenderer<>());
        this.add(cbxPreferredExit, "0, " + row++);
        row++;

        lblAreaSizeDesc = new JLabel(loc.getString("Assignment.Area"));
        lblAreaSize = new JLabel("");
        this.add(lblAreaSizeDesc, "0, " + row++);
        this.add(lblAreaSize, "0, " + row++);
        row++;

        lblMaxPersonsDesc = new JLabel(loc.getString("Assignment.MaxPersons"));
        lblMaxPersons = new JLabel("");
        this.add(lblMaxPersonsDesc, "0, " + row++);
        this.add(lblMaxPersons, "0, " + row++);
        row++;

        lblMaxPersonsWarning = new JLabel(loc.getString("Assignment.AreaWarning"));
        this.add(lblMaxPersonsWarning, "0, " + row++);
        row++;
        loc.clearPrefix();
    }

    /**
     * The listener firing change events if a new assignment type is selected.
     */
    private final ItemListener assignmentTypeChangeListener = (ItemEvent event) -> {
        if (event.getStateChange() == ItemEvent.SELECTED) {
            control.setAssignmentType((AssignmentType)cbxAssignmentType.getSelectedItem());
        }
    };

    /**
     * The listener firing change events if a new preferred exit is selected.
     */
    private final ItemListener preferredExitChangedListener = (ItemEvent event) -> {
        if (event.getStateChange() == ItemEvent.SELECTED) {
            control.setPreferredExit((EvacuationArea)cbxPreferredExit.getSelectedItem());
        }
    };
    
    @Override
    public void update() {
        // Update the assignment type listener,
        // do not send unnecessary update events
        JTeleportAreaInformationPanel.resetComboBoxWithoutNotification(cbxAssignmentType, assignmentTypeChangeListener,
                getModel().getAssignmentTypes(), getModel().getAssignmentType());

        txtNumberOfPersons.setText(nfInteger.format(getModel().getEvacuees()));
        double area = Math.round(getModel().areaMeter() * 100) / 100.0;
        lblAreaSize.setText(nfFloat.format(area) + " m²");
        if (getModel().isRastered()) {
            lblMaxPersons.setText(nfInteger.format(getModel().getMaxEvacuees()));
            lblMaxPersonsWarning.setText("");

        } else {
            double persons = Math.round((area / (0.4 * 0.4)) * 100) / 100.0;
            lblMaxPersons.setText(nfFloat.format(persons));
            lblMaxPersonsWarning.setText(loc.getString("Editview.Panel.Assignment.AreaWarning"));
        }
        
        // handle preferred exits
        JTeleportAreaInformationPanel.resetComboBoxWithoutNotification(cbxPreferredExit, preferredExitChangedListener,
                getModel().getEvacuationAreas(), getModel().getExitArea());
    }

    @Override
    public void localize() {
        loc.setPrefix("Editview.Panel.");
        lblAssignmentType.setText(loc.getString("Assignment.Type"));
        lblAssignmentEvacueeNumber.setText(loc.getString("Assignment.Persons"));
        btnAssignmentSetDefaultEvacuees.setText(loc.getString("Assignment.SetDefaultEvacuees"));
        btnAssignmentSetDefaultEvacuees.setToolTipText(loc.getString("Assignment.SetDefaultEvacuees.ToolTip"));
        lblPreferredExit.setText(loc.getString("Assignment.PreferredExit"));
        lblMaxPersonsDesc.setText(loc.getString("Assignment.MaxPersons"));
        lblMaxPersonsWarning.setText(loc.getString("Assignment.AreaWarning"));
        lblAreaSizeDesc.setText(loc.getString("Assignment.Area"));
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
