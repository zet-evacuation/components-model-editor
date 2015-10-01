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

import org.zetool.components.framework.Displayable;
import info.clearthought.layout.TableLayout;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Objects;
import javax.swing.JPanel;
import org.zet.components.model.editor.localization.EditorLocalization;
import org.zetool.common.localization.Localization;
import org.zetool.common.localization.LocalizationManager;
import org.zetool.common.localization.Localized;

/**
 *
 * @param <M> The model displayed.
 * @param <C> The control class receiving commands.
 * @author Jan-Philipp Kappmeier
 */
@SuppressWarnings("serial")
public abstract class JInformationPanel<C, M> extends JPanel implements Localized, Displayable<M> {

    /** The localization class. */
    protected Localization loc;
    protected static NumberFormat nfFloat = LocalizationManager.getManager().getFloatConverter();
    protected static NumberFormat nfInteger = LocalizationManager.getManager().getIntegerConverter();
    private M model;
    /** The control object for the object displayed. */
    protected final C control;

    public JInformationPanel(C control) {
        this(new double[]{TableLayout.FILL}, control);
    }

    protected JInformationPanel(double[] rows, C control) {
        this(columns(), rows, control);
        
    }

    protected JInformationPanel(double[] columns, double[] rows, C control) {
        super(new TableLayout(columns, rows));
        loc = EditorLocalization.LOC;
        this.control = Objects.requireNonNull(control);
    }

    private static double[] columns() {
        return new double[]{10, TableLayout.FILL, 10};
    }

    @Override
    public void setModel(M model) {
        this.model = Objects.requireNonNull(model);
        update();
    }
    
    protected M getModel() {
        return model;
    }

    public abstract void update();

    @Override
    public void localize() {
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
