/* zet evacuation tool copyright (c) 2007-20 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
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
