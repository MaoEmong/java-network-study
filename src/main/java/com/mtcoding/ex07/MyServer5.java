package com.mtcoding.ex07;

import com.google.gson.Gson;
import com.mtcoding.ex06.Person;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MyServer5 {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(20000);
            Socket socket = ss.accept();

            // 읽기 버퍼
            Scanner sc = new Scanner(socket.getInputStream());

            // 쓰기 버퍼
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            while(true){
                String line = sc.nextLine(); // 엔터키까지 읽기
                System.out.println("line : "+line);

                // 4. HTML 바디
                Random r = new Random();
                int num = r.nextInt(45)+1;


                String body = """
                    <html>
                      <head>
                        <meta charset="UTF-8">
                        <title>My HTTP Server</title>
                      </head>
                      <body>
                        <h1>안녕하세요 👋 ${num} </h1>
                        <p>자바 소켓으로 만든 HTTP 서버입니다.</p>
                      </body>
                    </html>
                    """.replace("${num}", num+"");

                // 5. HTTP 응답 헤더
                StringBuilder sb = new StringBuilder();

                // \r\n -> 엔터
                sb.append("HTTP/1.1 200 OK\r\n");
                sb.append("Content-Type: text/html; charset=UTF-8\r\n");
                sb.append("Content-Length: ");
                sb.append(body.getBytes("UTF-8").length);
                sb.append("\r\n");
                sb.append("\r\n");
                sb.append(body);

                String response = sb.toString();
                pw.println(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}