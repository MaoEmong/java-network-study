package com.mtcoding.ex09;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.0.99", 10000);
            Scanner keyboard = new Scanner(System.in);
            Scanner receiver = new Scanner(socket.getInputStream());
            PrintWriter sender = new PrintWriter(socket.getOutputStream(), true);

            // 읽기 스레드
            new Thread(() -> {
                while (true) {
                    String line = receiver.nextLine();
                    System.out.println(line);
                }
            }).start();
            // 송신 스레드
            new Thread(() -> {
                while (true) {
                    String line = keyboard.nextLine();
                    sender.println(line);
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
