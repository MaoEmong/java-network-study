package com.mtcoding.ex06;

import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

// 리퀘스트, 리스펀스
public class MyClient4 {
    public static void main(String[] args) {
        try {
            // localhost = 로컬백주소(자기자신주소) == "127.0.0.1"
            // 소켓 생성(로컬 연결)
            Socket socket = new Socket("127.0.0.1", 20000);

            // 소켓에 출력 버퍼 연결
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            // 연결된 소켓에 읽기 버퍼 연결
            Scanner socketSc = new Scanner(socket.getInputStream());

            Person person = new Person(1, "홍길동", 20, Arrays.asList("축구", "농구"));
            Gson gson = new Gson();
            String json = "";
            json = gson.toJson(person);
            pw.println(json); // ln이 \n을 자동으로 넣어주고 autoFlush가 된다
            String recv = socketSc.nextLine();
            System.out.println("서버로 부터 받은 메시지 : " + recv);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
