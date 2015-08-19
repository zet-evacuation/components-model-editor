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
public class InaccessibleAreaControl extends AbstractInformationPanelControl<JDefaultInformationPanel, Void, ChangeEvent> {
    public InaccessibleAreaControl() {
        super(new JDefaultInformationPanel());
    }

    @Override
    public void changed(ChangeEvent c) {
    }
}
