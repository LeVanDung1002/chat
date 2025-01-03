package _room_chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class Client {
    public void connectServer() {
        try {
            var sc = new Scanner(System.in);
            var socket = new Socket("localhost", 100);
            var clientListener = new ClientListener(socket);
            CompletableFuture.runAsync(clientListener::receiveMessage);

            var outputStream = socket.getOutputStream();
            while (true) {
                var msg = sc.nextLine();
                outputStream.write(msg.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        var client = new Client();
        client.connectServer();
    }
}