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

import de.zet_evakuierung.model.Room;
import info.clearthought.layout.TableLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * A panel displaying information about a room.
 * @author Jan-Philipp Kappmeier
 */
public class JRoomInformationPanel extends JInformationPanel<RoomChangeEvent, Room> {

    private JLabel lblRoomName;
    private JTextField txtRoomName;
    private JLabel lblRoomSize;
    private JLabel lblRoomSizeDesc;
    private JButton deleteRoom;
    private JButton moveRoom;

    public JRoomInformationPanel() {
        super(new double[]{TableLayout.FILL},
                new double[]{TableLayout.PREFERRED, TableLayout.PREFERRED, 20,
                    TableLayout.PREFERRED, TableLayout.PREFERRED,
                    10,
                    TableLayout.PREFERRED,
                    10,
                    TableLayout.PREFERRED,
                    TableLayout.FILL,});
        init();
    }

    private void init() {
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
                    fireChangeEvent(new RoomChangeEvent(this, RoomChangeEvent.RoomChange.Name));
                    //boolean success = projectControl.renameRoom(current, txtRoomName.getText());
                    //if (!success) {
                    //    System.out.println("Floor with that name already exists");
                    //}
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

        deleteRoom = new JButton("Raum Löschen");
        deleteRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //projectControl.deletePolygon(current);
                fireChangeEvent(new RoomChangeEvent(this, RoomChangeEvent.RoomChange.Delete));
            }
        });
        this.add(deleteRoom, "0, " + ++row);
        row++;

        moveRoom = new JButton("Epsilon-Verschieben");
        moveRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //projectControl.refineRoomCoordinates(current.getPolygon(), gs.getRasterSizeSnap());
                fireChangeEvent(new RoomChangeEvent(this, RoomChangeEvent.RoomChange.RefineCoordinates));
            }
        });
        this.add(moveRoom, "0, " + ++row);
        row++;
    }

    @Override
    public void update() {
        System.out.println(current);
        //if( 1 == 1 )
        //    throw new IllegalStateException("This is not yet implemented!");
        txtRoomName.setText(current.getName());
        double areaRoom = Math.round(current.getPolygon().areaMeter() * 100) / 100.0;
        lblRoomSize.setText(nfFloat.format(areaRoom) + " m²");
    }

    @Override
    public void localize() {
        loc.setPrefix("gui.EditPanel.");
        lblRoomName.setText(loc.getString("Room.Name"));
        lblRoomSizeDesc.setText(loc.getString("Room.Area"));
        loc.clearPrefix();
    }
}