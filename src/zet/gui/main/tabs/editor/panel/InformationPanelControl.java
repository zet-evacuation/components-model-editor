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
 */
public interface InformationPanelControl<E extends JInformationPanel<?,M>, M> {
    E getView();
    public default void setUpListener() {};
    
    default void setModel(M m) {
        getView().update(m);
    }
}
