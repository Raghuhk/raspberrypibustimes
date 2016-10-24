import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import com.pi4j.system.NetworkInfo;

public class TestNetwork {
	public static void main(String[] args) throws Exception {

		 System.out.println("Hostname          :  " + NetworkInfo.getHostname());
	        for (String ipAddress : NetworkInfo.getIPAddresses())
	            System.out.println("IP Addresses      :  " + ipAddress);
	        for (String fqdn : NetworkInfo.getFQDNs())
	            System.out.println("FQDN              :  " + fqdn);
	        for (String nameserver : NetworkInfo.getNameservers())
	            System.out.println("Nameserver        :  " + nameserver);


		}

}
