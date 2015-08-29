
package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.ZControl;
import java.util.Objects;
import org.zet.components.model.editor.panel.Displayable;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <V> the view
 * @param <M> the viewmodel
 */
public abstract class AbstractInformationControl<V extends Displayable<M>,M> {

    private final V view;
    protected M viewModel;
    protected final ZControl control;

    public AbstractInformationControl(V view, ZControl control) {
        this.view = view;
        this.control = Objects.requireNonNull(control);
    }
    
    public V getView() {
        return view;
    }

    protected void setModel(M m) {
        getView().setModel(m);
        this.viewModel = m;
    }
}
