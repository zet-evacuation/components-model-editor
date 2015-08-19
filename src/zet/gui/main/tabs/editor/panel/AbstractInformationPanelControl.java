/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <E>
 * @param <M>
 * @param <K>
 */
public abstract class AbstractInformationPanelControl<E extends JInformationPanel<K,M>, M, K extends ChangeEvent> implements InformationPanelControl<E, M>, ChangeListener<K> {
    private final E view;
    protected M model;

    public AbstractInformationPanelControl(E view) {
        this.view = view;
    }
    
    @Override
    public E getView() {
        return view;
    }

    @Override
    public void setModel(M m) {
        getView().update(m);
        this.model = m;
    }
    
    
}
