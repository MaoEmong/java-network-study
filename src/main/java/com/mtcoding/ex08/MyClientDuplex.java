package com.mtcoding.ex08;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyClientDuplex {

    public static void mmain(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 20000);

            Scanner keyboardSc = new Scanner(System.in);
            Scanner socketSc = new Scanner(socket.getInputStream());
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            // 1) 수신 스레드: 서버 -> 클라이언트
            Thread receiver = new Thread(() -> {
                try {
                    while (socketSc.hasNextLine()) {
                        String recv = socketSc.nextLine();
                        System.out.println("[client][recv] " + recv);
                    }
                } catch (Exception e) {
                    System.out.println("[client] receiver 종료: " + e.getMessage());
                }
            });

            // 2) 송신 스레드: 클라이언트 -> 서버 (키보드)
            Thread sender = new Thread(() -> {
                try {
                    while (true) {
                        String msg = keyboardSc.nextLine(); // 블로킹
                        pw.println(msg); // autoFlush=true

                        // 종료 명령 예시
                        if ("quit".equalsIgnoreCase(msg)) {
                            try {
                                socket.close();
                            } catch (Exception ignored) {
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("[client] sender 종료: " + e.getMessage());
                }
            });

            receiver.start();
            sender.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // 소켓 설정
            Socket socket = new Socket("localhost", 20000);
            System.out.println("서버 연결 성공");

            // 입출력 스트림
            // 읽기 버퍼
            Scanner socketSc = new Scanner(socket.getInputStream());
            // 쓰기 버퍼
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            // 키보드 연결
            Scanner keyboardSc = new Scanner(System.in);

            // 송수신 스레드
            // 수신 스레드
            Thread receiver = new Thread(() -> {
                try {
                    // 넘어올 데이터 받기 대기
                    while (true) {
                        String line = socketSc.nextLine();
                        System.out.println("[Server] " + line);

                        // quit 넘어오면 연결 종료
                        if ("quit".equalsIgnoreCase(line)) {
                            System.out.println("receiver 스트림 연결 종료");
                            socket.close();
                        }
                    }

                } catch (Exception e) {
                    System.out.println("receiver 에러 연결 종료");
                    e.printStackTrace();
                }
            });

            // 송신 스레드
            Thread sender = new Thread(() -> {
                try {
                    // 키보드 입력 대기
                    while (true) {
                        String line = keyboardSc.nextLine();
                        pw.println(line);

                        // quit 넘어오면 연결 종료
                        if ("quit".equalsIgnoreCase(line)) {
                            System.out.println("sender 스트림 연결 종료");
                            socket.close();
                        }
                    }

                } catch (Exception e) {
                    System.out.println("receiver 에러 연결 종료");
                    e.printStackTrace();
                }
            });

            // 스레드 시작
            receiver.start();
            sender.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}