package org.jenkinsci.plugins.anel;

import hudson.Extension;

import hudson.model.listeners.ItemListener;

/**
 * Initializes the power-outlet controller.
 *
 * @author Ulli Hafner
 */
@Extension
public class PowerOutletInitializer extends ItemListener {
    /** {@inheritDoc} */
    @Override
    public void onLoaded() {
        new StatusMapper().update();
    }
}
