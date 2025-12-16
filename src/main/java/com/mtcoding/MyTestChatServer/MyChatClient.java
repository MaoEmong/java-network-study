package com.mtcoding.MyTestChatServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * MyChatClient
 * ------------------------------------------------------------
 * - 서버 동작을 테스트하기 위한 콘솔 기반 채팅 클라이언트다.
 * - Unity 클라이언트를 붙이기 전에,
 *   서버의 수신 / 브로드캐스트 로직이 정상 동작하는지 확인하는 용도로 사용한다.
 * - 표준 입력(System.in)을 통해 메시지를 입력받아 서버로 전송한다.
 */
public class MyChatClient {

    /**
     * 프로그램 진입점
     * ------------------------------------------------------------
     * 1) 서버(localhost:20000)에 소켓으로 연결한다.
     * 2) 콘솔에서 입력한 문자열을 서버로 전송한다.
     * 3) "/quit" 입력 시 클라이언트를 종료한다.
     */
    public static void main(String[] args) throws Exception {

        // 1️⃣ 서버에 소켓 연결 (로컬 테스트용)
        Socket socket = new Socket("localhost", 20000);

        // 2️⃣ 서버로 문자열을 전송하기 위한 출력 스트림
        BufferedWriter bw =
                new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        // 3️⃣ 콘솔 입력을 받기 위한 입력 스트림
        BufferedReader console =
                new BufferedReader(new InputStreamReader(System.in));

        System.out.println("[Client] Type message and press Enter:");

        // 4️⃣ 콘솔 입력을 계속 받아 서버로 전송
        while (true) {
            // 콘솔에서 한 줄 입력 대기 (엔터 입력 전까지 블로킹)
            String msg = console.readLine();

            // 입력 스트림 종료
            if (msg == null) break;

            // 종료 명령어
            if (msg.equalsIgnoreCase("/quit")) break;

            // 5️⃣ 서버로 메시지 전송
            // 서버가 readLine()으로 읽기 때문에 줄바꿈을 포함한다.
            bw.write(msg);
            bw.write("\n");
            bw.flush();
        }

        // 6️⃣ 소켓 종료 및 자원 정리
        socket.close();
    }
}
