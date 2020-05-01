package org.zet.components.model.viewmodel;

import java.util.Objects;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
class AbstractViewModel<E> {
    protected final E model;

    public AbstractViewModel(E model) {
        this.model = Objects.requireNonNull(model);
    }
    
}
