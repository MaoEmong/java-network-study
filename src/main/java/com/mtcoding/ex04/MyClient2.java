package com.mtcoding.ex04;

import java.io.*;
import java.net.Socket;

public class MyClient2 {
    public static void main(String[] args) {
        try {
            // localhost = 로컬백주소(자기자신주소) == "127.0.0.1"
            // 소켓 생성(로컬 연결)
            Socket socket = new Socket("192.168.0.69", 20000);

            // 입력
            InputStream keyboard = System.in;
            InputStreamReader keyReader = new InputStreamReader(keyboard);
            BufferedReader keyBuf = new BufferedReader(keyReader);
            // 출력
            OutputStream out = socket.getOutputStream();
            OutputStreamWriter ow = new OutputStreamWriter(out);
            BufferedWriter bw = new BufferedWriter(ow);

            while (true) {
                String keyboardData = keyBuf.readLine();
                bw.write(keyboardData);
                bw.write("\n");
                bw.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
