package com.mtcoding.ex06;

import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServer4 {
    public static void main(String[] args) {
        try {
            // 소켓 연결 확인
            ServerSocket ss = new ServerSocket(20000);
            Socket socket = ss.accept();

            // 연결된 소켓에 읽기 버퍼 연결
//            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner sc = new Scanner(socket.getInputStream());

            // 쓰기용 버퍼
            PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);

            while (true) {
                String line = sc.nextLine(); // 엔터키 까지 읽음
                Gson gson = new Gson();
                Person p = gson.fromJson(line,Person.class);

                System.out.println(p.getNo());
                System.out.println(p.getName());
                System.out.println(p.getAge());
                System.out.println(p.getHobby().get(0));
                System.out.println(p.getHobby().get(1));

                pw.println("OK");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
