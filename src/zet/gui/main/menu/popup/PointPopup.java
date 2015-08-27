package zet.gui.main.menu.popup;

import org.zetool.common.localization.Localization;
import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.PlanPoint;
import java.io.IOException;
import java.util.Objects;
import javax.swing.JPopupMenu;
import org.zetool.components.framework.Menu;
import zet.gui.components.editor.EditorLocalization;
import zet.gui.main.tabs.editor.panel.ChangeListener;
import zet.gui.main.tabs.editor.panel.PointChangeEvent;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class PointPopup extends JPopupMenu {

    /** The localization class. */
    private Localization loc = EditorLocalization.LOC;
    private PlanEdge currentEdge;
    private PlanPoint currentPoint;

    public PointPopup() {
        super();
        recreate();
    }

    /**
     * This method is called internally to recreate an up-to-date JPopupMenu for the JEdge (Point) objects. It also
     * recreates the JEdgePopupListeners.
     */
    public void recreate() {
        removeAll();
        Menu.addMenuItem(this, loc.getString("gui.editor.JEditorPanel.popupDeletePoint"), e
                -> fireChangeEvent(new PointChangeEvent(e.getSource(), currentEdge, currentPoint)));
    }
    
    public void addChangeListener(ChangeListener<PointChangeEvent> l) {
        listenerList.add(ChangeListener.class, Objects.requireNonNull(l));
    }

    public void removeChangeListener(ChangeListener<PointChangeEvent> l) {
        listenerList.remove(ChangeListener.class, Objects.requireNonNull(l));
    }

    protected void fireChangeEvent(PointChangeEvent c) {
        // Guaranteed to return a non-null array
        Objects.requireNonNull(c, "ChangeEvent null!");
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class) {
                ((ChangeListener<PointChangeEvent>) listeners[i + 1]).changed(c);
            }
        }
    }

    /**
     * This method should be called every time before the JEdge point pop-up menu is shown.
     *
     * @param currentEdge The Edge on which the PointPopupMenu shall be shown.
     * @param currentPoint The PlanPoint on which the PointPopupMenu shall be shown.
     */
    public void setPopupPoint(PlanEdge currentEdge, PlanPoint currentPoint) {
        this.currentEdge = Objects.requireNonNull(currentEdge);
        this.currentPoint = Objects.requireNonNull(currentPoint);
    }

    /**
     * Prohibits serialization.
     */
    private synchronized void writeObject(java.io.ObjectOutputStream s) throws IOException {
        throw new UnsupportedOperationException("Serialization not supported");
    }
}
