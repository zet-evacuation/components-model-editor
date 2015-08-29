package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.editor.panel.JDefaultInformationPanel;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class InaccessibleAreaControl extends AbstractInformationControl<JDefaultInformationPanel, Void> {

    public InaccessibleAreaControl(ZControl control) {
        super(new JDefaultInformationPanel(), control);
    }
}
