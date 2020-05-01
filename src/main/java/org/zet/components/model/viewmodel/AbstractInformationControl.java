
package org.zet.components.model.viewmodel;

import org.zetool.components.framework.Displayable;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <C> the control class for the model
 * @param <P> the panel displaying the viewmodel
 * @param <V> the viewmodel
 * @param <M> the model
 * 
 */
public abstract class AbstractInformationControl<P extends Displayable<V>, C extends AbstractControl<M, V>, M, V> {
    private final P view;
    private final C control;

    public AbstractInformationControl(P view, C control) {
        this.view = view;
        this.control = control;
    }
    
    public P getView() {
        return view;
    }

    public void setModel(M m) {
        V viewModel = getViewModel(m);
        getView().setModel(viewModel);
        control.setModel(m);
        control.setViewModel(viewModel);
    }
    
    protected abstract V getViewModel(M m);

    public C getControl() {
        return control;
    }
}
