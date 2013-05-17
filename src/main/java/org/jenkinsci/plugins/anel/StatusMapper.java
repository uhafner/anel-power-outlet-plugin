package org.jenkinsci.plugins.anel;

import java.util.List;

import jenkins.model.Jenkins;

import org.jenkinsci.plugins.anel.JenkinsStatusListener.DescriptorImpl;

import hudson.model.BallColor;
import hudson.model.AbstractProject;

/**
 * Updates the power-outlet controller using the current build result of all active Jenkins jobs.
 *
 * @author Ulli Hafner
 */
public class StatusMapper {
    /**
     * Updates the power-outlet controller with the current status of the Jenkins instance.
     */
    @SuppressWarnings("rawtypes")
    public void update() {
        int powerSwitchValue = 0;
        List<AbstractProject> projects = Jenkins.getInstance().getAllItems(AbstractProject.class);
        for (AbstractProject project : projects) {
            if (!project.isDisabled()) {
                if (BallColor.BLUE == project.getIconColor()) {
                    powerSwitchValue += 1;
                }
                else if (BallColor.YELLOW == project.getIconColor()) {
                    powerSwitchValue += 2;
                }
                else if (BallColor.RED == project.getIconColor()) {
                    powerSwitchValue += 4;
                }
            }
        }
        JenkinsStatusListener.DescriptorImpl descriptor = (DescriptorImpl)Jenkins.getInstance().getDescriptorOrDie(JenkinsStatusListener.class);
        PowerOutletSender sender = new PowerOutletSender(descriptor.getIp(), descriptor.getPort(), descriptor.getUser(), descriptor.getPassword());
        sender.send(powerSwitchValue);
    }
}

