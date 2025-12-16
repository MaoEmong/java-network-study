package com.mtcoding.MyTestChatServer;

import java.io.*;
import java.net.Socket;

/**
 * ClientHandler
 * ------------------------------------------------------------
 * - 서버에 접속한 "클라이언트 1명"을 전담하는 클래스다.
 * - 하나의 Socket과 1:1로 대응되며, Runnable로 실행된다.
 * - 클라이언트의 메시지를 수신하고, 서버에게 전달하는 역할을 한다.
 */
public class ClientHandler implements Runnable {

    /** 클라이언트와 연결된 소켓 */
    private final Socket socket;

    /** 서버 참조 (브로드캐스트, 클라이언트 제거 요청용) */
    private final MyChatServer server;

    /** 서버에서 발급한 고유 클라이언트 ID */
    private final int clientId;
    private String nickName = "";

    /** 클라이언트로부터 문자열을 읽기 위한 입력 스트림 */
    private BufferedReader br;

    /** 클라이언트에게 문자열을 보내기 위한 출력 스트림 */
    private BufferedWriter bw;

    /**
     * ClientHandler 생성자
     * ------------------------------------------------------------
     * - 클라이언트 소켓과 서버 참조, 클라이언트 ID를 전달받는다.
     * - 소켓의 InputStream / OutputStream을 문자열 기반 스트림으로 감싼다.
     *
     * @param socket   클라이언트와 연결된 소켓
     * @param server   서버 객체 (브로드캐스트 및 관리용)
     * @param clientId 서버에서 발급한 클라이언트 고유 ID
     */
    public ClientHandler(Socket socket, MyChatServer server, int clientId) throws IOException {
        this.socket = socket;
        this.server = server;
        this.clientId = clientId;
        this.nickName = "Client-" + clientId;

        // 문자열 단위 입출력을 위해 스트림 래핑
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    /**
     * 클라이언트 전용 스레드의 실행 로직
     * ------------------------------------------------------------
     * 1) 접속한 클라이언트에게 자신의 ID를 알려준다.
     * 2) 전체 클라이언트에게 "입장" 메시지를 브로드캐스트한다.
     * 3) 클라이언트가 보내는 메시지를 계속 수신한다.
     * 4) 수신한 메시지를 서버를 통해 모든 클라이언트에게 전달한다.
     * 5) 연결 종료 시 정리(clean-up)를 수행한다.
     */
    @Override
    public void run() {
        try {
            // 기본 닉네임 설정
            nickName = "Client-" + clientId;

            // 클라이언트에게 ID 전달
            send("[SYSTEM] YOUR_ID=" + clientId);

            // 입장 알림
            server.broadcast("[Server] " + nickName + " joined.");

            String line;
            while ((line = br.readLine()) != null) {

                // ✅ 1) 보이지 않는 문자 제거 + trim
                String sanitized = line
                        .replace("\uFEFF", "")  // BOM 제거
                        .replace("\u200B", "")  // Zero Width Space 제거
                        .trim();

                // ✅ 2) 닉네임 명령 처리
                if (sanitized.startsWith("[NICK]")) {
                    String newNick = sanitized.substring(6).trim();
                    if (!newNick.isEmpty()) {
                        nickName = newNick;
                        send("[SYSTEM] NICK_OK=" + nickName);
                    }
                    continue; // ⭐ 절대 브로드캐스트 금지
                }

                // ✅ 3) 일반 채팅
                server.broadcast("[" + nickName + "] " + sanitized);
            }

        } catch (IOException e) {
            System.out.println("[" + nickName + "] Disconnected");
        } finally {
            server.remove(this);
            try { socket.close(); } catch (IOException ignored) {}
            server.broadcast("[Server] " + nickName + " left.");
        }
    }

    /**
     * 클라이언트에게 메시지 전송
     * ------------------------------------------------------------
     * - 서버가 클라이언트에게 데이터를 보낼 때 사용한다.
     * - 문자열 뒤에 줄바꿈을 붙여 readLine() 기반 통신을 유지한다.
     *
     * @param msg 전송할 메시지
     */
    public void send(String msg) {
        try {
            bw.write(msg);
            bw.write("\n");   // readLine() 종료를 위한 줄바꿈
            bw.flush();
        } catch (IOException e) {
            // 전송 실패 = 클라이언트 연결 문제로 판단
            server.remove(this);
        }
    }
}
