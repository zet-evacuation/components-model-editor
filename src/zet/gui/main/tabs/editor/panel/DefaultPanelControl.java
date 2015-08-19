/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

/**
 * A control class for evacuation areas.
 * @author Jan-Philipp Kappmeier
 */
public class DefaultPanelControl extends AbstractInformationPanelControl<JDefaultInformationPanel, Void, ChangeEvent> {
    public DefaultPanelControl() {
        super(new JDefaultInformationPanel());
    }

    @Override
    public void changed(ChangeEvent c) {
    }
}
