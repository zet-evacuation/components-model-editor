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

import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.TeleportArea;
import info.clearthought.layout.TableLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import zet.gui.components.model.NamedComboBoxRenderer;
import zet.gui.main.tabs.editor.panel.viewmodels.TeleportAreaViewModel;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
@SuppressWarnings("serial")
public class JTeleportAreaInformationPanel extends JInformationPanel<TeleportAreaControl, TeleportAreaViewModel> {

    /** Describes the teleportation area name field. */
    private JLabel lblTeleportAreaName;
    /** The name filed for a teleportation area. */
    private JTextField txtTeleportAreaName;

    /** Describes the target area combo box. */
    private JLabel lblTargetArea;
    /** A combo box selecting the target area of a target area. */
    private JComboBox<TeleportArea> cbxTargetArea;

    /** A combo box selecting the possible exits for a teleportation area. */
    private JComboBox<EvacuationArea> cbxTargetExit;
    /** Describes the target exit combo box. */
    private JLabel lblTargetExit;

    public JTeleportAreaInformationPanel(TeleportAreaViewModel viewModel) {
        super(new double[]{TableLayout.FILL},
                new double[]{TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, 20, TableLayout.FILL
                });
        init();
    }

    private void init() {
        int row = 0;

        // The area name
        lblTeleportAreaName = new JLabel(loc.getString("Teleportation.Name"));
        this.add(lblTeleportAreaName, "0, " + row++);
        txtTeleportAreaName = new JTextField();
        txtTeleportAreaName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //@//JZetWindow.setEditing( true );
            }

            @Override
            public void focusLost(FocusEvent e) {
                //@//JZetWindow.setEditing( false );
            }
        });
        txtTeleportAreaName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    control.rename(txtTeleportAreaName.getText());
                }
            }
        });
        this.add(txtTeleportAreaName, "0, " + row++);
        row++;

        // Target-Aea-Selector
        lblTargetArea = new JLabel(loc.getString("Teleportation.TargetArea"));
        this.add(lblTargetArea, "0, " + row++);
        cbxTargetArea = new JComboBox<>();
        cbxTargetArea.setRenderer(new NamedComboBoxRenderer<>());
        this.add(cbxTargetArea, "0, " + row++);
        row++;

        // Target-Exit-Selector
        lblTargetExit = new JLabel(loc.getString("Teleportation.TargetExit"));
        this.add(lblTargetExit, "0, " + row++);
        cbxTargetExit = new JComboBox<>();
        cbxTargetExit.setRenderer(new NamedComboBoxRenderer<>());
        this.add(cbxTargetExit, "0, " + row++);
        row++;
    }

    ItemListener targetExitChangedListener = (event) -> {
        EvacuationArea targetExit = (EvacuationArea) cbxTargetExit.getSelectedItem();
        control.setTargetExit(targetExit);
    };
    ItemListener targetAreaChangedListener = (event) -> {
        TeleportArea targetArea = (TeleportArea) cbxTargetArea.getSelectedItem();
        control.setTargetArea(targetArea);
    };

    @Override
    public void update() {
        txtTeleportAreaName.setText(getModel().getName());

        // handle preferred exits
        resetComboBoxWithoutNotification(cbxTargetExit, targetExitChangedListener, getModel().getEvacuationAreas(), getModel().getExitArea());
        resetComboBoxWithoutNotification(cbxTargetArea, targetAreaChangedListener, getModel().getTeleportAreas(), getModel().getTargetArea());
    }

    /**
     * Removes all items from a combo box and sets new ones. Selects a given item and removes a specific item listener
     * during the replacement.
     *
     * @param <E> the combo box element type
     * @param comboBox the combo box
     * @param itemListener the listener to be removed
     * @param boxItems the iterable elements
     * @param selectedItem the element to select, can be {@code null}
     */
    public static <E> void resetComboBoxWithoutNotification(JComboBox<E> comboBox, ItemListener itemListener,
            Iterable<E> boxItems, E selectedItem) {
        comboBox.removeItemListener(itemListener);
        comboBox.removeAllItems();
        boxItems.forEach((item) -> {
            comboBox.addItem(item);
        });
        if (selectedItem == null) {
            comboBox.setSelectedIndex(-1);
        } else {
            comboBox.setSelectedItem(selectedItem);
        }
        comboBox.addItemListener(itemListener);
    }

    @Override
    public void localize() {
        loc.setPrefix("gui.EditPanel.");
        lblTeleportAreaName.setText(loc.getString("Teleportation.Name"));
        lblTargetArea.setText(loc.getString("Teleportation.TargetArea"));
        lblTargetExit.setText(loc.getString("Teleportation.TargetExit"));
        loc.clearPrefix();
    }

    /**
     * Prohibits serialization.
     */
    private synchronized void writeObject(java.io.ObjectOutputStream s) throws IOException {
        throw new UnsupportedOperationException("Serialization not supported");
    }

    /**
     * Prohibits serialization.
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Serialization not supported");
    }
}
