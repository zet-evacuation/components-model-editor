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

import zet.gui.components.editor.EditorLocalization;
import org.zetool.common.localization.Localization;
import org.zetool.common.localization.LocalizationManager;
import org.zetool.common.localization.Localized;
import de.zet_evakuierung.model.ZControl;
//@//import gui.GUIControl;
import info.clearthought.layout.TableLayout;
import java.text.NumberFormat;
import java.util.Objects;
import javax.swing.JPanel;

/**
 *
 * @param <E>
 * @param <C>
 * @param <U> The displayed object
 * @author Jan-Philipp Kappmeier
 */
@SuppressWarnings("serial")
public abstract class JInformationPanel<E extends ChangeEvent, U> extends JPanel implements Localized {

    /**
     * The localization class.
     */
    protected Localization loc;
    protected static NumberFormat nfFloat = LocalizationManager.getManager().getFloatConverter();
    protected static NumberFormat nfInteger = LocalizationManager.getManager().getIntegerConverter();
    /**
     * The control object for the loaded project.
     */
    protected ZControl projectControl;
    protected U current;

    public JInformationPanel() {
        this(new double[]{TableLayout.FILL});
    }

    protected JInformationPanel(double[] rows) {
        this(columns(), rows);
    }

    protected JInformationPanel(double[] columns, double[] rows) {
        super(new TableLayout(columns, rows));
        loc = EditorLocalization.LOC;
    }

    private static double[] columns() {
        return new double[]{10, TableLayout.FILL, 10};
    }
	//@//public void setControl( ZControl control ) { //@//, GUIControl guiControl ) {
    //@//	this.projectControl = control;
    //@//	//this.guiControl = guiControl;
    //@//}

    public void update(U current) {
        this.current = Objects.requireNonNull(current);
        update();
    }

    public abstract void update();

    @Override
    public void localize() {

    }

    public void addChangeListener(ChangeListener<E> l) {
        listenerList.add(ChangeListener.class, l);
    }

    public void removeChangeListener(ChangeListener<E> l) {
        listenerList.remove(ChangeListener.class, l);
    }

    protected void fireChangeEvent(E c) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class) {
                if (c == null) {
                    throw new IllegalArgumentException("ChangeEvent null!");
                }
                ((ChangeListener<E>) listeners[i + 1]).changed(c);
            }
        }
    }
}