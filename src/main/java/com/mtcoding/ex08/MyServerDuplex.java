package com.mtcoding.ex08;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServerDuplex {

    public static void mmain(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(20000);
            System.out.println("[server] 대기중...");

            Socket socket = ss.accept();
            System.out.println("[server] 연결됨: " + socket.getInetAddress());

            // 읽기버퍼
            Scanner socketSc = new Scanner(socket.getInputStream());
            // 쓰기버퍼
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            Scanner keyboardSc = new Scanner(System.in);

            // 1) 수신 스레드: 클라이언트 -> 서버
            Thread receiver = new Thread(() -> {
                try {
                    while (socketSc.hasNextLine()) {
                        String line = socketSc.nextLine();
                        System.out.println("[server][recv] " + line);

                        // 자동 응답도 가능
                        // pw.println("ok: " + line);

                        if ("quit".equalsIgnoreCase(line)) {
                            try {
                                socket.close();
                            } catch (Exception ignored) {
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("[server] receiver 종료: " + e.getMessage());
                }
            });

            // 2) 송신 스레드: 서버 -> 클라이언트 (서버 키보드 입력)
            Thread sender = new Thread(() -> {
                try {
                    while (true) {
                        String msg = keyboardSc.nextLine();
                        pw.println(msg);

                        if ("quit".equalsIgnoreCase(msg)) {
                            try {
                                socket.close();
                            } catch (Exception ignored) {
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("[server] sender 종료: " + e.getMessage());
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
            // 서버 소켓 생성
            ServerSocket ss = new ServerSocket(20000);
            System.out.println("클라이언트 연결 대기...");
            // 소켓 생성 및 연결대기
            Socket socket = ss.accept();
            System.out.println("클라이언트 연결 성공");

            // 입출력 스트림 생성
            // 읽기버퍼
            Scanner socketSc = new Scanner(socket.getInputStream());
            // 쓰기버퍼
            PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
            // 키보드 입력
            Scanner keyboardSc = new Scanner(System.in);

            // 수신 스레드(클라이언트 -> 서버)
            Thread receiver = new Thread(() -> {
                try {
                    // 클라이언트에서 보낸 데이터 받기 대기
                    while (true) {
                        String line = socketSc.nextLine();
                        // 입력받은 데이터 출력
                        System.out.println("[Client] " + line);

                        if ("quit".equalsIgnoreCase(line)) {
                            System.out.println("[receiver] 연결 스트림 종료");
                            socket.close();
                            ;
                            break;
                        }
                    }

                } catch (Exception e) {
                    System.out.println("[receiver] 에러 연결 스트림 종료");
                    e.printStackTrace();
                }
            });

            // 송신 스레드(서버 -> 클라이언트)
            Thread sender = new Thread(() -> {
                try {
                    // 입력 대기
                    while (true) {
                        String line = keyboardSc.nextLine();
                        pw.println(line);

                        if ("quit".equalsIgnoreCase(line)) {
                            System.out.println("[sender] 연결 스트림 종료");
                            socket.close();
                            ;
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("[sender] 에러 연결 스트림 종료");
                    e.printStackTrace();
                }
            });

            // 스레드 실행
            receiver.start();
            sender.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}