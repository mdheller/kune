package org.ourproject.kune.workspace.client.licensewizard.pages;

import org.ourproject.kune.platf.client.View;

import com.calclab.suco.client.events.Listener0;

public interface LicenseWizardFrdFormView extends View {

    String getSelectedLicense();

    void onChange(Listener0 slot);

    void reset();

    void setFlags(boolean isCopyleft, boolean isAppropiateForCulturalWorks, boolean isNonComercial);
}