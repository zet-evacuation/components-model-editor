/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

import java.util.EventListener;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <E>
 */
public interface ChangeListener<E extends ChangeEvent> extends EventListener {

    public void changed(E c);
    
}
