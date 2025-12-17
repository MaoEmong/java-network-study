package com.mtcoding.ex09;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

class ClientThread implements Runnable {
    Socket socket;
    PrintWriter sender;
    Scanner receiver;

    public ClientThread(Socket socket, PrintWriter sender, Scanner receiver) {
        this.socket = socket;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public void run() {
        // 새로운 스레드 대기
        while (true) {
            String line = receiver.nextLine();

            for (var c : ChatServer.clientInfo) {
                c.sender.println(line);
            }
        }
    }
}

public class ChatServer {
    static List<ClientThread> clientInfo = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        try {
            // 1. 초기화
            ServerSocket ss = new ServerSocket(10000);
            while (true) {
                Socket socket = ss.accept();    // main 스레드 대기
                PrintWriter sender = new PrintWriter(socket.getOutputStream(),true);
                Scanner receiver = new Scanner(socket.getInputStream());
                ClientThread client = new ClientThread(socket, sender, receiver);
                clientInfo.add(client);

                new Thread(client).start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
