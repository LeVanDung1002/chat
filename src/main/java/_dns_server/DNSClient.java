package _dns_server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class DNSClient {
    public void connectServer() {
        try {
            var socket = new DatagramSocket();

            while (true) {
                var sc = new Scanner(System.in);
                System.out.println("Enter url : ");
                var web = sc.nextLine();

                var serverAddress = InetAddress.getByName("localhost");
                var serverPort = 100;
                var byteSend = web.getBytes();
                var packetToSend = new DatagramPacket(byteSend, 0, byteSend.length, serverAddress, serverPort);
                socket.send(packetToSend);

                var bytes = new byte[1024];
                var receivePacket = new DatagramPacket(bytes, 0, bytes.length);
                socket.receive(receivePacket);

                System.out.println("IP: "+new String(receivePacket.getData(), 0, receivePacket.getLength()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        var client = new DNSClient();
        client.connectServer();
    }
}
