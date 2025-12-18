package com.mtcoding.ex00;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 10000);
            System.out.println("[client] 서버와 소켓이 연결되었음 -----------------");
            Scanner keyboard = new Scanner(System.in);
            Scanner receiver = new Scanner(socket.getInputStream());
            PrintWriter sender = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("채팅 닉네임 입력 : ");
            String name = keyboard.nextLine();
            sender.println(name);

            // 1. 송신 스레드
            new Thread(() -> {
                while(true){
//                    System.out.println("[client] 쓰기 스레드 키보드 입력 대기중--------");
                    String msg = keyboard.nextLine();
                    sender.println(msg);
                }
            }).start();

            // 2. 읽기 스레드
            new Thread(() -> {
                while(true){
//                    System.out.println("[client] 읽기 스레드 다른 사람 메시지 수신 대기중--------");
                    String msg = receiver.nextLine();
                    System.out.println(msg);
                }

            }).start();

        } catch (Exception e) {
            System.out.println("클라이언트 오류 : "+e.getMessage());
        }
    }
}
