package org.jenkinsci.plugins.anel;

import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;

import hudson.model.Describable;
import hudson.model.TaskListener;
import hudson.model.Descriptor;
import hudson.model.Run;
import hudson.model.listeners.RunListener;

/**
 * Notifies the power-outlet controller about the current Jenkins status.
 *
 * @author Ulli Hafner
 */
@Extension
public class JenkinsStatusListener extends RunListener<Run<?, ?>> implements Describable<JenkinsStatusListener> {
    @Override
    public void onCompleted(final Run<?, ?> r, final TaskListener listener) {
        new StatusMapper().update();
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public Descriptor<JenkinsStatusListener> getDescriptor() {
        return Jenkins.getInstance().getDescriptorOrDie(getClass());
    }

    /**
     * Descriptor for {@link JenkinsStatusListener}.
     *
     * @author Ulli Hafner
     */
    @Extension
    public static class DescriptorImpl extends Descriptor<JenkinsStatusListener> {
        private String ip;
        private int port;
        private String password;
        private String user;

        @Override
        public String getDisplayName() {
            return StringUtils.EMPTY;
        }

        /**
         * Creates a new instance of {@link JenkinsStatusListener.DescriptorImpl}.
         */
        public DescriptorImpl() {
            super();

            load();
        }

        @Override
        public boolean configure(final StaplerRequest req, final JSONObject json) throws FormException {
            req.bindJSON(this, json);
            save();
            return true;
        }

        /**
         * Returns the ip address of the power-outlet.
         *
         * @return the ip address
         */
        public String getIp() {
            return ip;
        }

        /**
         * Returns the port the power-outlet listens to.
         *
         * @return the port
         */
        public int getPort() {
            return port;
        }

        /**
         * Returns the password to access the power-outlet.
         *
         * @return the password
         */
        public String getPassword() {
            return password;
        }

        /**
         * Returns the user to access the power-outlet.
         *
         * @return the user
         */
        public String getUser() {
            return user;
        }

        /**
         * Sets the ip address to the specified value.
         *
         * @param ip the value to set
         */
        public void setIp(final String ip) {
            this.ip = ip;
        }

        /**
         * Sets the port to the specified value.
         *
         * @param port the value to set
         */
        public void setPort(final int port) {
            this.port = port;
        }

        /**
         * Sets the password to the specified value.
         *
         * @param password the value to set
         */
        public void setPassword(final String password) {
            this.password = password;
        }

        /**
         * Sets the user to the specified value.
         *
         * @param user the value to set
         */
        public void setUser(final String user) {
            this.user = user;
        }
    }
}
