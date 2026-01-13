import java.io.*;
import java.net.*;
import java.util.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ChatServer {
    private static final int PORT = 5555;
    private static Set<PrintWriter> clientWriters = Collections.synchronizedSet(new HashSet<>());
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        System.out.println("Chat Server listening on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket);
                handler.start();  // Start thread for each client
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    // Broadcast to all clients with timestamp
    public static void broadcast(String message) {
        String timestamped = "[" + LocalTime.now().format(timeFormatter) + "] " + message;
        synchronized (clientWriters) {
            for (PrintWriter writer : clientWriters) {
                writer.println(timestamped);
            }
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                // Ask for username until valid
                out.println("Enter your username:");
                do {
                    username = in.readLine();
                } while (username == null || username.trim().isEmpty());

                System.out.println(username + " joined from " + socket.getRemoteSocketAddress());
                out.println("Welcome to chat!");
                ChatServer.broadcast(username + " has joined the chat!");

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(username + ": " + message);
                    ChatServer.broadcast("[" + username + "]: " + message);
                }
            } catch (IOException e) {
                System.err.println("Connection error with " + username + ": " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException ignored) {}
                synchronized (clientWriters) {
                    clientWriters.remove(out);
                }
                if (username != null) {
                    System.out.println(username + " disconnected.");
                    ChatServer.broadcast("[Server] " + username + " has left the chat.");
                }
            }
        }
    }
}
