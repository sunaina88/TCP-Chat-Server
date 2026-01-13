# 💬 TCP Multi-Client Chat Server

> Real-time chat. Multiple clients. Pure TCP sockets.

This Java project implements a **multi-client chat application** using TCP sockets.  
A central server accepts multiple clients, manages usernames, broadcasts messages, and handles clean disconnects — while each client runs **two threads** for simultaneous sending and receiving.

---

## 🚀 Features

- Multi-client chat over TCP  
- Thread-per-client server architecture  
- Username authentication on connect  
- Timestamped message broadcasting  
- Graceful handling of client disconnects  
- Concurrent sender & receiver threads on the client side  
- Uses standard Java networking (no external libraries)

---

## 🛠️ Project Structure

```bash
tcp-chat-server/
├── ChatServer.java # Multi-threaded TCP chat server
├── ChatClient.java # Client with sender & receiver threads
└── README.md # You're here right now :)
```
---

## 🏁 How to Run

Compile both Java files.
```bash
javac *.java
```

### 1. Start the Server

```bash
java ChatServer
```
You should see:

```bash
Chat Server listening on port 5555
```

### 2. Start a Client

Open a new terminal:

```bash
java ChatClient
```

You will see:

```bash
Enter your username:
```

Type a username and press Enter:

```bash
Welcome to chat!
Connected! Start chatting (type 'EXIT' to exit).
```

### 3. Start more clients

Open additional terminals and run:

```bash
java ChatClient
```

All clients will receive each other's messages.

## 📸 Example Interaction

Client 1 (alice):
```bash
Enter your username:
alice
Connected! Start chatting (type 'EXIT' to exit).
[12:21] alice has joined the chat!
hi everyone!
```

Client 2 (bob):

```bash
Enter your username:
bob
Connected! Start chatting (type 'EXIT' to exit).
[12:21] alice has joined the chat!
[12:22] [alice]: hi everyone!
hello alice!
```
---

## 🧠 What This Project Demonstrates

- TCP socket fundamentals (ServerSocket, Socket)
- Network I/O using BufferedReader and PrintWriter
- Thread-per-client concurrency model
- Designing real-time communication systems
- Managing shared state safely (synchronizedSet)
- Clean shutdown and error-handling logic

---

## 🙋‍♀️ Author

**sunaina ☀**  (she/her)

GitHub: [@sunaina88](https://github.com/sunaina88)

---

Feel free to ⭐ the repo if you found it useful or fun!
