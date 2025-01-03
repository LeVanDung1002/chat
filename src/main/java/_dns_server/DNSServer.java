package _dns_server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class DNSServer {
    private final Integer PORT = 100;
    private Map<String, String> dnsMap;
    public DNSServer() {
        this.dnsMap = getDNSServer();
    }
    public void startServer() {
        try {
            var sever = new DatagramSocket(PORT);
            while (true) {
                var bytes = new byte[1024];
                var packet = new DatagramPacket(bytes, 0, bytes.length);
                sever.receive(packet);

                var web = new String(packet.getData(), 0, packet.getLength());
                var response = "Not found";
                if (this.dnsMap.containsKey(web)) {
                    response = this.dnsMap.get(web);
                }

                System.out.println(response);
                var clientAddress = InetAddress.getByName(packet.getAddress().getHostAddress());
                var clientPort = packet.getPort();
                var byteRes = response.getBytes();
                var responsePacket = new DatagramPacket(byteRes, 0, byteRes.length, clientAddress, clientPort);
                sever.send(responsePacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getDNSServer() {
        var map = new HashMap<String, String>();
        map.put("google.com", "127.35.6.9");
        map.put("youtube.com", "127.45.32.1");
        map.put("facebook.com", "200.20.20.1");
        return map;
    }

    public static void main(String[] args) {
        var dns = new DNSServer();
        dns.startServer();
    }
}
