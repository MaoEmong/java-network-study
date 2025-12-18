package com.mtcoding.ex00;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

class ClientThread implements Runnable{
    Socket socket;
    PrintWriter sender;
    Scanner receiver;
    String name;

    public ClientThread(Socket socket, PrintWriter sender, Scanner receiver) {
        this.socket = socket;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public void run() {
        // 최초 입력은 닉네임으로 설정
        name = receiver.nextLine();

        // 새로운 스레드 대기중
        while(true){
//            System.out.println("[server] 새로운 메시지 수신 대기중----------------");
            String msg = receiver.nextLine();
            for (ClientThread t : ChatServer.boxes){
                t.sender.println(name + " : " + msg);
//                System.out.println("[server] 새로운 메시지 전체 브로드캐스팅----------------");
            }
        }

    }
}

public class ChatServer {

    static List<ClientThread> boxes = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        try {
            System.out.println("Server Start");
            ServerSocket ss = new ServerSocket(10000);
            while(true){
                System.out.println("Connecting Wait...");
                Socket socket = ss.accept(); // main 스레드 대기
                System.out.println("[server] 클라이언트연결됨--------");
                PrintWriter sender = new PrintWriter(socket.getOutputStream(), true);
                Scanner receiver = new Scanner(socket.getInputStream());
                ClientThread t1 = new ClientThread(socket, sender, receiver);
                boxes.add(t1);
                new Thread(t1).start();
                System.out.println("[server] 클라이언트 Socket, Buffer 2개, Thread 생성되서 Box 담김-------");
            }

        } catch (Exception e) {
            System.out.println("서버 오류 : "+e.getMessage());
        }

    }
}
