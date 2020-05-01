package org.zet.components.model.editor.floor.popup;

import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.PlanPoint;
import java.io.IOException;
import java.util.Objects;
import javax.swing.JPopupMenu;
import org.zetool.components.framework.Menu;
import org.zet.components.model.editor.localization.EditorLocalization;
import org.zet.components.model.viewmodel.PointControl;
import org.zetool.common.localization.Localization;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class PointPopup extends JPopupMenu {

    /** The localization class. */
    private static final Localization LOC = EditorLocalization.LOC;
    private PlanEdge currentEdge;
    private PlanPoint currentPoint;
    private PointControl pointControl;

    public PointPopup() {
        super();
    }

    /**
     * This method is called internally to recreate an up-to-date JPopupMenu for the JEdge (Point) objects. It also
     * recreates the JEdgePopupListeners.
     */
    public void recreate() {
        removeAll();
        Menu.addMenuItem(this, LOC.getString("gui.editor.JEditorPanel.popupDeletePoint"), e
                -> pointControl.delete(currentEdge, currentPoint));
    }


    public void setPointControl(PointControl pc) {
        this.pointControl = pc;
    }

    /**
     * This method should be called every time before the JEdge point pop-up menu is shown.
     *
     * @param currentEdge The Edge on which the PointPopupMenu shall be shown.
     * @param currentPoint The PlanPoint on which the PointPopupMenu shall be shown.
     */
    public void setPopupPoint(PlanEdge currentEdge, PlanPoint currentPoint) {
        recreate();
        this.currentEdge = Objects.requireNonNull(currentEdge);
        this.currentPoint = Objects.requireNonNull(currentPoint);
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
