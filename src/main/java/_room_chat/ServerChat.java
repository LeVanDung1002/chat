package _room_chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ServerChat {
    private static final Integer PORT = 100;
    private final List<ClientHandler> clientHandlers;
    private boolean isActive;
    public ServerChat() {
        this.clientHandlers = new ArrayList<>();
    }
    public void startServer() {
        this.isActive = true;
        try {
            var server = new ServerSocket(PORT);
            System.out.println("Server running...");
            while (this.isActive) {
                var socket = server.accept();
                System.out.println("New client connected: " + socket.getInetAddress().getHostAddress());
                var newClientHandler = new ClientHandler(System.currentTimeMillis(), socket, this);
                this.clientHandlers.add(newClientHandler);
                CompletableFuture.runAsync(newClientHandler::handleInput);
            }
        } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public void broatCast(String msg, Long id) {
        for (var clientHandler : clientHandlers) {
            if (!Objects.equals(clientHandler.getId(), id)) {
                clientHandler.sendMessage(msg, id);
            }
        }
    }
    public void finishServer() {
        this.isActive = false;
    }
    public static void main(String[] args) {
        var server = new ServerChat();
        server.startServer();
    }
}
