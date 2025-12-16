package com.mtcoding.MyTestChatServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MyChatServer
 * ------------------------------------------------------------
 * - TCP 기반 채팅 서버의 중심 클래스다.
 * - 클라이언트 접속을 수락하고(Client accept),
 *   각 클라이언트마다 ClientHandler를 생성하여 스레드로 실행한다.
 * - 전체 클라이언트 목록을 관리하며,
 *   메시지를 모든 클라이언트에게 브로드캐스트하는 역할을 한다.
 */
public class MyChatServer {

    /** 서버가 리스닝할 포트 번호 */
    private final int port;

    /**
     * 현재 접속 중인 클라이언트 목록
     * ------------------------------------------------------------
     * - 여러 스레드(ClientHandler)가 동시에 접근하므로
     *   Thread-safe 한 ConcurrentHashMap 기반 Set을 사용한다.
     */
    private final Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();

    /** 클라이언트에게 순차적으로 발급할 ID 값 */
    private int nextClientId = 1;

    /**
     * 서버 생성자
     * ------------------------------------------------------------
     * @param port 서버가 사용할 포트 번호
     */
    public MyChatServer(int port) {
        this.port = port;
    }

    /**
     * 클라이언트 ID 생성 메서드
     * ------------------------------------------------------------
     * - 여러 클라이언트가 동시에 접속할 수 있으므로
     *   synchronized로 동기화하여 ID 중복을 방지한다.
     *
     * @return 새로 발급된 클라이언트 ID
     */
    public synchronized int generateClientId() {
        return nextClientId++;
    }

    /**
     * 서버 시작 메서드
     * ------------------------------------------------------------
     * 1) 지정된 포트로 ServerSocket을 생성한다.
     * 2) accept()로 클라이언트 접속을 대기한다. (블로킹)
     * 3) 클라이언트 접속 시 ClientHandler를 생성한다.
     * 4) ClientHandler를 별도의 스레드로 실행한다.
     */
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("[Server] Listening on port " + port);

        // 서버는 종료되지 않고 계속 클라이언트 접속을 받는다
        while (true) {
            // 1️⃣ 클라이언트 접속 대기 (블로킹)
            Socket socket = serverSocket.accept();

            // 2️⃣ 클라이언트 ID 발급
            int clientId = generateClientId();

            // 3️⃣ 클라이언트 전담 핸들러 생성
            ClientHandler handler = new ClientHandler(socket, this, clientId);

            // 4️⃣ 서버가 클라이언트 목록에 추가
            clients.add(handler);

            // 5️⃣ 클라이언트 전담 스레드 실행
            new Thread(handler).start();
        }
    }

    /**
     * 메시지 브로드캐스트
     * ------------------------------------------------------------
     * - 서버에 접속한 모든 클라이언트에게 동일한 메시지를 전송한다.
     *
     * @param msg 전송할 메시지
     */
    public void broadcast(String msg) {
        for (ClientHandler c : clients) {
            c.send(msg);
        }
    }

    /**
     * 클라이언트 제거
     * ------------------------------------------------------------
     * - 클라이언트 연결 종료 시 호출된다.
     * - 서버의 클라이언트 목록에서 해당 ClientHandler를 제거한다.
     *
     * @param handler 제거할 클라이언트 핸들러
     */
    public void remove(ClientHandler handler) {
        clients.remove(handler);
    }
}
