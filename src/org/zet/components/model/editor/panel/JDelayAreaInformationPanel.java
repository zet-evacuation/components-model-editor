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

import de.zet_evakuierung.model.DelayArea;
import info.clearthought.layout.TableLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import org.zetool.components.framework.Button;
import org.zet.components.model.editor.selectors.ComboBoxRenderer;
import org.zet.components.model.viewmodel.DelayAreaControl;
import org.zet.components.model.viewmodel.DelayAreaViewModel;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class JDelayAreaInformationPanel extends JInformationPanel<DelayAreaControl, DelayAreaViewModel> {

    private JButton btnDelaySetDefault;
    private JLabel lblDelayFactor;
    private JLabel lblDelayType;
    private JComboBox<DelayArea.DelayType> cbxDelayType;
    private JTextField txtDelayFactor;

    public JDelayAreaInformationPanel(DelayAreaViewModel model, DelayAreaControl control) {
        super(new double[]{TableLayout.FILL},
                new double[]{TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, 20, TableLayout.FILL
                }, control);
        init();
    }

    private void init() {
        loc.setPrefix("Editview.Panel.");
        int row = 0;

        // Delay-Selector
        lblDelayType = new JLabel(loc.getString("Delay.Type") + ":");
        this.add(lblDelayType, "0, " + row++);
        cbxDelayType = new JComboBox<>(new DefaultComboBoxModel<>(DelayArea.DelayType.values()));
        cbxDelayType.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                control.setType((DelayArea.DelayType)e.getItem());
            }
        });
        cbxDelayType.setRenderer(new ComboBoxRenderer<DelayArea.DelayType>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<? extends DelayArea.DelayType> list, DelayArea.DelayType value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    setText(value.description);
                }
                return this;
            }

            /**
             * Prohibits serialization.
             */
            private synchronized void writeObject(java.io.ObjectOutputStream s) throws IOException {
                throw new UnsupportedOperationException("Serialization not supported");
            }
        });
        this.add(cbxDelayType, "0, " + row++);
        row++;

        // Delay-Factor
        lblDelayFactor = new JLabel(loc.getString("Delay.Factor"));
        this.add(lblDelayFactor, "0, " + row++);
        txtDelayFactor = new JTextField(" ");
        txtDelayFactor.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //@//JZetWindow.setEditing( true );
            }

            @Override
            public void focusLost(FocusEvent e) {
                //@//JZetWindow.setEditing( false );
            }
        });
        txtDelayFactor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        double speedFactor = nfFloat.parse(txtDelayFactor.getText()).doubleValue();
                        control.setSpeedFactor(speedFactor);
                    } catch (ParseException ex) {
                        Logger.getGlobal().info(String.format(loc.getString("Editview.Panel.Delay.SpeedFactorInvalid"),
                                txtDelayFactor.getText()));
                    }
                }
            }
        });
        this.add(txtDelayFactor, "0, " + row++);
        row++;

        btnDelaySetDefault = Button.newButton(loc.getString("Delay.TypeDefault"),
                loc.getString("Delay.TypeDefault.ToolTip"));
        btnDelaySetDefault.setToolTipText(loc.getString("Delay.TypeDefault.ToolTip"));
        btnDelaySetDefault.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.setDefaultType();
            }
        });
        this.add(btnDelaySetDefault, "0, " + row++);
        loc.clearPrefix();
    }

    @Override
    public void update() {
        txtDelayFactor.setText(nfFloat.format(getModel().getSpeedFactor()));
        cbxDelayType.setSelectedItem(getModel().getDelayType());
    }

    @Override
    public void localize() {
        loc.setPrefix("Editview.Panel.");
        lblDelayType.setText(loc.getString("Delay.Type") + ":");
        lblDelayFactor.setText(loc.getString("Delay.Factor"));
        btnDelaySetDefault.setText(loc.getString("Delay.TypeDefault"));
        btnDelaySetDefault.setToolTipText(loc.getString("Delay.TypeDefault.ToolTip"));
        loc.clearPrefix();
    }
}
