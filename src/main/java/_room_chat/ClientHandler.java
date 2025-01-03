package _room_chat;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler {
    private ServerChat serverChat;
    private InputStream is;
    private OutputStream os;
    private Long id;
    public ClientHandler(Long id, Socket socket, ServerChat serverChat) {
        this.id = id;
        this.serverChat = serverChat;
        try {
            this.is = socket.getInputStream();
            this.os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleInput() {
        try {
            var bytes = new byte[1024];
            var byteRead = 0;
            while ((byteRead = this.is.read(bytes)) != -1) {
                serverChat.broatCast(new String(bytes, 0, byteRead), this.id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(String msg, Long id) {
        try {
            this.os.write(handleMsg(msg, id).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String handleMsg(String msg, Long id) {
        return String.format("%d: %s", id, msg);
    }

    public ServerChat getServerChat() {
        return serverChat;
    }

    public void setServerChat(ServerChat serverChat) {
        this.serverChat = serverChat;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public OutputStream getOs() {
        return os;
    }

    public void setOs(OutputStream os) {
        this.os = os;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
