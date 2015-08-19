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

import de.zet_evakuierung.model.FloorInterface;
import de.zet_evakuierung.model.Room;
import info.clearthought.layout.TableLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.zetool.components.framework.Button;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class JFloorInformationPanel extends JInformationPanel<FloorChangeEvent, FloorInterface> {
    private JLabel lblFloorName;
    private JButton btnFloorUp;
    private JButton btnFloorDown;
    private JTextField txtFloorName;
    private JLabel lblFloorxOffset;
    private JLabel lblFlooryOffset;
    private JLabel lblFloorWidth;
    private JLabel lblFloorHeight;
    private JTextField txtFloorxOffset;
    private JTextField txtFlooryOffset;
    private JTextField txtFloorWidth;
    private JTextField txtFloorHeight;
    private JLabel lblFloorSize;
    private JLabel lblFloorSizeDesc;
            private String floorName;
    private Rectangle floorSize;

    public JFloorInformationPanel() {
        super(new double[]{
            TableLayout.FILL, 10, TableLayout.FILL
        },
        new double[]{
            TableLayout.PREFERRED,
            TableLayout.PREFERRED,
            20,
            TableLayout.PREFERRED,
            TableLayout.PREFERRED,
            20,
            TableLayout.PREFERRED,
            TableLayout.PREFERRED,
            TableLayout.PREFERRED,
            TableLayout.PREFERRED,
            TableLayout.PREFERRED,
            TableLayout.PREFERRED,
            20,
            TableLayout.PREFERRED,
            TableLayout.FILL
        });
        init();
    }

    private void init() {
        lblFloorName = new JLabel(loc.getString("Floor.Name"));
        this.add(lblFloorName, "0,0,2,0");
        txtFloorName = new JTextField();
        txtFloorName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //@//JZetWindow.setEditing( true );
            }

            @Override
            public void focusLost(FocusEvent e) {
                //@//JZetWindow.setEditing( false );
            }
        });
        txtFloorName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    floorName = txtFloorName.getText();
                    fireChangeEvent(new FloorChangeEvent(this, FloorChangeEvent.FloorChange.Rename));
                }
            }
        });
        this.add(txtFloorName, "0,1,2,1");

        btnFloorUp = Button.newButton(loc.getString("Floor.Up"), aclFloor, "up", loc.getString("Floor.Up.ToolTip"));
        this.add(btnFloorUp, "0,3,0,3");
        btnFloorDown = Button.newButton(loc.getString("Floor.Down"), aclFloor, "down", loc.getString("Floor.Down.ToolTip"));
        this.add(btnFloorDown, "2,3");

        // Additional Infos:
        lblFloorxOffset = new JLabel(loc.getString("Floor.xOffset"));
        lblFlooryOffset = new JLabel(loc.getString("Floor.yOffset"));
        lblFloorWidth = new JLabel(loc.getString("Floor.Width"));
        lblFloorHeight = new JLabel(loc.getString("Floor.Height"));
        txtFloorxOffset = new JTextField();
        txtFlooryOffset = new JTextField();
        txtFloorWidth = new JTextField();
        txtFloorHeight = new JTextField();
        this.add(lblFloorxOffset, "0,5");
        this.add(lblFlooryOffset, "0,6");
        this.add(lblFloorWidth, "0,7");
        this.add(lblFloorHeight, "0,8");
        this.add(txtFloorxOffset, "2,5");
        this.add(txtFlooryOffset, "2,6");
        this.add(txtFloorWidth, "2,7");
        this.add(txtFloorHeight, "2,8");

        txtFloorxOffset.addActionListener(aclFloorSize);
        txtFlooryOffset.addActionListener(aclFloorSize);
        txtFloorWidth.addActionListener(aclFloorSize);
        txtFloorHeight.addActionListener(aclFloorSize);

        lblFloorSizeDesc = new JLabel(loc.getString("Floor.Area"));
        lblFloorSize = new JLabel("");
        this.add(lblFloorSizeDesc, "0,10,2,10");
        this.add(lblFloorSize, "0,11,2,11");

        JButton cmdRasterizeBuilding = Button.newButton("Rastern!", aclFloor, "rasterize", loc.getString("Floor.Rasterize.ToolTip"));

        this.add(cmdRasterizeBuilding, "0,13,2,13");
        cmdRasterizeBuilding.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    ActionListener aclFloor = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "down":
                    fireChangeEvent(new FloorChangeEvent(this, FloorChangeEvent.FloorChange.MoveDown));
                    break;
                case "up":
                    fireChangeEvent(new FloorChangeEvent(this, FloorChangeEvent.FloorChange.MoveUp));
                    break;
                case "rasterize":
                    fireChangeEvent(new FloorChangeEvent(this, FloorChangeEvent.FloorChange.Rasterize));
                    break;
                default:
                    //@//ZETLoader.sendError( loc.getString( "gui.UnknownCommand" ) + " '" + e.getActionCommand() + "'. " + loc.getString( "gui.ContactDeveloper" ) );
                    System.err.println("Unrecognized action: " + e.getActionCommand());
                    break;
            }
        }
    };
    ActionListener aclFloorSize = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                final int xOffset = nfInteger.parse(txtFloorxOffset.getText()).intValue();
                final int yOffset = nfInteger.parse(txtFlooryOffset.getText()).intValue();
                final int width = nfInteger.parse(txtFloorWidth.getText()).intValue();
                final int height = nfInteger.parse(txtFloorHeight.getText()).intValue();
                floorSize = new Rectangle(xOffset, yOffset, width, height);
                fireChangeEvent(new FloorChangeEvent(this, FloorChangeEvent.FloorChange.Dimension));
            } catch (ParseException ex) {
                //@//ZETLoader.sendError( "Parsing nicht möglich." ); // TODO loc
                return;
            }
            //@//ZETLoader.sendMessage( "Floor-Size geändert." ); // TODO loc
        }
    };
    
    String getFloorName() {
        return floorName;
    }

    Rectangle getFloorSize() {
        return floorSize;
    }

    @Override
    public void update() {
        System.out.println("Current: " + current);
        txtFloorName.setText(this.current.getName());
        txtFloorxOffset.setText(Integer.toString(current.getLocation().x));
        txtFlooryOffset.setText(Integer.toString(current.getLocation().y));
        txtFloorWidth.setText(Integer.toString(current.getLocation().width));
        txtFloorHeight.setText(Integer.toString(current.getLocation().height));
        double areaFloor = 0;
        for (Room r : current) {
            areaFloor += r.getPolygon().areaMeter();
        }

        lblFloorSize.setText(nfFloat.format(areaFloor) + " m²");
    }

    @Override
    public void localize() {
        // Floor properties
        loc.setPrefix("gui.EditPanel.");
        lblFloorName.setText(loc.getString("Floor.Name"));
        btnFloorUp.setText(loc.getString("Floor.Up"));
        btnFloorUp.setToolTipText(loc.getString("Floor.Up.ToolTip"));
        btnFloorDown.setText(loc.getString("Floor.Down"));
        btnFloorDown.setToolTipText(loc.getString("Floor.Down.ToolTip"));
        lblFloorxOffset.setText(loc.getString("Floor.xOffset"));
        lblFlooryOffset.setText(loc.getString("Floor.yOffset"));
        lblFloorWidth.setText(loc.getString("Floor.Width"));
        lblFloorHeight.setText(loc.getString("Floor.Height"));
        lblFloorSizeDesc.setText(loc.getString("Floor.Area"));
        loc.clearPrefix();
    }
}
