package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.ZControl;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class InaccessibleAreaControl extends AbstractInformationPanelControl<JDefaultInformationPanel, Void> {

    public InaccessibleAreaControl(ZControl control) {
        super(new JDefaultInformationPanel(), control);
    }
}
