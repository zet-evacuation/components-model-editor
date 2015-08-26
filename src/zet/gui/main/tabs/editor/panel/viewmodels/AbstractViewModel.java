/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel.viewmodels;

import java.util.Objects;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class AbstractViewModel<E> {
    protected final E model;

    public AbstractViewModel(E model) {
        this.model = Objects.requireNonNull(model);
    }
    
}
