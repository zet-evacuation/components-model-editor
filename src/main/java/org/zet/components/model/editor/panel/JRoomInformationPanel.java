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

import info.clearthought.layout.TableLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.zet.components.model.viewmodel.RoomControl;
import org.zet.components.model.viewmodel.RoomViewModel;

/**
 * A panel displaying information about a room.
 * @author Jan-Philipp Kappmeier
 */
public class JRoomInformationPanel extends JInformationPanel<RoomControl, RoomViewModel> {

    private JLabel lblRoomName;
    private JTextField txtRoomName;
    private JLabel lblRoomSize;
    private JLabel lblRoomSizeDesc;
    private JButton deleteRoom;
    private JButton moveRoom;

    public JRoomInformationPanel(RoomViewModel model, RoomControl control) {
        super(new double[]{TableLayout.FILL},
                new double[]{TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, TableLayout.PREFERRED,
                    10,
                    TableLayout.PREFERRED,
                    10,
                    TableLayout.PREFERRED,
                    TableLayout.FILL,}, control);
        init();
    }

    private void init() {
        loc.setPrefix("Editview.Panel.");
        int row = 0;

        lblRoomName = new JLabel(loc.getString("Room.Name"));
        this.add(lblRoomName, "0, " + row++);
        txtRoomName = new JTextField();
        txtRoomName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //@//JZetWindow.setEditing( true );
            }

            @Override
            public void focusLost(FocusEvent e) {
                //@//JZetWindow.setEditing( false );
            }
        });
        txtRoomName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    control.setName(txtRoomName.getText());
                }
            }
        });
        this.add(txtRoomName, "0, " + row++);
        row++;

        // add size information
        lblRoomSizeDesc = new JLabel(loc.getString("Room.Area"));
        lblRoomSize = new JLabel("");
        this.add(lblRoomSizeDesc, "0, " + row++);
        this.add(lblRoomSize, "0, " + row++);

        deleteRoom = new JButton(loc.getString("Room.Delete"));
        deleteRoom.addActionListener(e ->  control.delete());
        this.add(deleteRoom, "0, " + ++row);
        row++;

        moveRoom = new JButton(loc.getString("Room.MoveEpsilon"));
        moveRoom.addActionListener(e -> control.refineCoordinates());
        this.add(moveRoom, "0, " + ++row);
        row++;
        loc.clearPrefix();
    }

    @Override
    public void update() {
        txtRoomName.setText(getModel().getName());
        double areaRoom = Math.round(getModel().areaMeter() * 100) / 100.0;
        lblRoomSize.setText(nfFloat.format(areaRoom) + " m²");
    }

    @Override
    public void localize() {
        loc.setPrefix("Editview.Panel.");
        lblRoomName.setText(loc.getString("Room.Name"));
        lblRoomSizeDesc.setText(loc.getString("Room.Area"));
        deleteRoom.setText(loc.getString("Room.Delete"));
        moveRoom.setText(loc.getString("Room.MoveEpsilon"));
        loc.clearPrefix();
    }
}
