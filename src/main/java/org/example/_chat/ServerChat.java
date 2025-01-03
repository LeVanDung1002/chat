package org.example._chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class ServerChat {

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(100);

            while (true) {
                var socket = server.accept();
                CompletableFuture.runAsync(() -> handleMessage(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void handleMessage(Socket socket) {
        try {
            var bufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var scanner = new Scanner(System.in);
            var printWriter = new PrintWriter(socket.getOutputStream());

            while (true) {
                System.out.println("Client: "+bufferReader.readLine());
                var msgSend = scanner.nextLine();
                printWriter.println(msgSend);
                printWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
