/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.ZControl;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <M> the model
 */
public class AbstractControl<M, V>  {

    protected ZControl zcontrol;
    protected M model;
    protected V viewModel;

    public AbstractControl(ZControl zcontrol) {
        this.zcontrol = zcontrol;
    }
    
    public void setModel(M m) {
        this.model = m;
    }
    
    public void setViewModel(V v) {
        this.viewModel = v;
    }
    
}
