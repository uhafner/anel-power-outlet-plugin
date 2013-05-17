package org.jenkinsci.plugins.anel;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Sends a UDP package to the power-outlet controller in order to define the active outlets.
 *
 * @author Ulli Hafner
 * @see <a href="http://www.anel-elektronik.de/english/Produkte/NET-PwrCtrl_HOME/net-pwrctrl_home.html">http://www.anel-elektronik.de/english/Produkte/NET-PwrCtrl_HOME/net-pwrctrl_home.html</a>
 */
public class PowerOutletSender {
    private final String ip;
    private final int port;
    private final String user;
    private final String password;

    /**
     * Creates a new instance of {@link PowerOutletSender}.
     *
     * @param ip
     *            IP address of the power-outlet
     * @param port
     *            port the power-outlet listens to
     * @param user
     *            user name to access the power-outlet
     * @param password
     *            password to access the power-outlet
     */
    public PowerOutletSender(final String ip, final int port, final String user, final String password) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    /**
     * Sends the specified binary value to the power-outlet controller. Bit 0 is used for power-outlet 1, bit 1 for
     * power-outlet 2 and bit 2 for power-outlet 3.
     *
     * @param number
     *            the number indicating which outlets should be enabled or disabled
     */
    public void send(final int number) {
        DatagramSocket socket = null;
        try {
            InetAddress host = InetAddress.getByName(ip);
            socket = new DatagramSocket();
            byte[] data = ("Sw" + number + user + password).getBytes("US-ASCII");
            DatagramPacket packet = new DatagramPacket(data, data.length, host, port);
            socket.send(packet);
        }
        catch (IOException e) {
            // ignore
        }
        finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
