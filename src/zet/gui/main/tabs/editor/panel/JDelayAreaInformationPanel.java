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

import de.zet_evakuierung.model.DelayArea;
import info.clearthought.layout.TableLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import org.zetool.components.framework.Button;
import zet.gui.components.model.ComboBoxRenderer;
import zet.gui.main.tabs.editor.panel.viewmodels.DelayAreaViewModel;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
//public class JDelayAreaInformationPanel extends JInformationPanel<DelayAreaChangeEvent, DelayArea> {
public class JDelayAreaInformationPanel extends JInformationPanel<DelayAreaControl, DelayAreaViewModel> {

    private JButton btnDelaySetDefault;
    private JLabel lblDelayFactor;
    private JLabel lblDelayType;
    private JComboBox<DelayArea.DelayType> cbxDelayType;
    private JTextField txtDelayFactor;
    //private double speedFactor;
    //private DelayArea.DelayType type;

    public JDelayAreaInformationPanel() {
        super(new double[]{TableLayout.FILL},
                new double[]{TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, 20, TableLayout.FILL
                });
        init();
    }

    private void init() {
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
                        //fireChangeEvent(new DelayAreaChangeEvent(this, DelayAreaChangeEvent.DelayAreaChange.Factor));
                    } catch (ParseException ex) {
                        //ZETLoader.sendError(loc.getString("gui.error.NonParsableFloatString"));
                        System.err.println("String not valid: " + txtDelayFactor.getText());
                    }
                }
            }
        });
        this.add(txtDelayFactor, "0, " + row++);
        row++;

        btnDelaySetDefault = Button.newButton(loc.getString("Delay.TypeDefault"), loc.getString("Delay.TypeDefault.ToolTip"));
        btnDelaySetDefault.setToolTipText(loc.getString("Delay.TypeDefault.ToolTip"));
        btnDelaySetDefault.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.setDefaultType();
                //fireChangeEvent(new DelayAreaChangeEvent(this, DelayAreaChangeEvent.DelayAreaChange.DefaultType));
            }
        });
        this.add(btnDelaySetDefault, "0, " + row++);
    }

//    double getSpeedFactor() {
//        return speedFactor;
//    }

//    DelayArea.DelayType getDelayType() {
//        return type;
//    }

    @Override
    public void update() {
        txtDelayFactor.setText(nfFloat.format(getModel().getSpeedFactor()));
        cbxDelayType.setSelectedItem(getModel().getDelayType());
    }

    @Override
    public void localize() {
        loc.setPrefix("gui.EditPanel.");
        lblDelayType.setText(loc.getString("Delay.Type") + ":");
        lblDelayFactor.setText(loc.getString("Delay.Factor"));
        btnDelaySetDefault.setText(loc.getString("Delay.TypeDefault"));
        btnDelaySetDefault.setToolTipText(loc.getString("Delay.TypeDefault.ToolTip"));
        loc.clearPrefix();
    }
}
