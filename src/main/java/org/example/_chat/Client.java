package org.example._chat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            var socket = new Socket("localhost", 100);
            handleMessage(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleMessage(Socket socket) {
        try {
            var scanner = new Scanner(System.in);
            var printWriter = new PrintWriter(socket.getOutputStream());
            var bufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                var msg = scanner.nextLine();
                printWriter.println(msg);
                printWriter.flush();
                System.out.println("Server: "+bufferReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
