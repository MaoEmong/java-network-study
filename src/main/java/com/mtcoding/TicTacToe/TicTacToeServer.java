package com.mtcoding.TicTacToe;


import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * TicTacToeServer.java
 * ------------------------------------------------------------
 * 역할 분리
 * 1) TicTacToeServer  : 접속/세션관리/브로드캐스트/흐름
 * 2) ClientSession    : 클라이언트 1명 소켓 송수신
 * 3) TicTacToeGame    : 보드/턴/승패 판정 (네트워크와 분리)
 */
public class TicTacToeServer {
    // ============================================================
    // [Config]
    // ============================================================
    private static final int PORT = 20000;

    // ============================================================
    // [Server State]
    // ============================================================
    private final List<ClientSession> sessions = new CopyOnWriteArrayList<>();
    private final TicTacToeGame game = new TicTacToeGame();

    public static void main(String[] args) {
        new TicTacToeServer().run();
    }

    // ============================================================
    // [Server Main Loop]
    // ============================================================
    private void run() {
        try (ServerSocket ss = new ServerSocket(PORT)) {
            System.out.println("[server] listening on " + PORT);

            while (true) {
                Socket socket = ss.accept();
                System.out.println("[server] connected: " + socket.getInetAddress());

                // 2명 제한(간단 구현)
                if (sessions.size() >= 2) {
                    try (PrintWriter temp = new PrintWriter(socket.getOutputStream(), true)) {
                        temp.println("INFO server full (only 2 players)");
                    } catch (Exception ignored) {}
                    try { socket.close(); } catch (Exception ignored) {}
                    continue;
                }

                // ------------------------------------------------------------
                // 1) 접속 순서대로 PlayerNo/Mark 배정
                //    Player1 = X, Player2 = O
                // ------------------------------------------------------------
                int playerNo = (sessions.size() == 0) ? 1 : 2;
                char mark = (playerNo == 1) ? 'X' : 'O';

                ClientSession session = new ClientSession(socket, mark, this);
                sessions.add(session);

                // ------------------------------------------------------------
                // 2) 세션 스레드 먼저 시작 (안전)
                // ------------------------------------------------------------
                new Thread(session).start();

                // ------------------------------------------------------------
                // 3) 초기 안내 (PlayerNo/Mark)
                // ------------------------------------------------------------
                session.send("PLAYER " + playerNo);
                session.send("ASSIGN " + mark);

                // ------------------------------------------------------------
                // 4) 대기 / 시작 처리
                // ------------------------------------------------------------
                if (sessions.size() < 2) {
                    session.send("WAIT");
                    broadcast("INFO player joined: " + mark + " (waiting...)");
                } else {
                    // 2명 다 모이면 새 게임 시작
                    game.reset(); // ✅ 중요: 재접속/재시작 테스트 편하게
                    broadcast("INFO player joined: " + mark);
                    broadcast("START");
                    broadcast("STATE " + game.getStateString());
                    broadcast("TURN " + game.getCurrentTurn());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ============================================================
    // [Called by Session]
    // ============================================================
    void onHello(ClientSession from, String name) {
        from.setName(name);
        System.out.println("Player "+name+" Connect.");
        from.send("INFO hello " + name);
    }

    void onPut(ClientSession from, int r, int c) {
        // 상대 기다리기
        if (sessions.size() < 2) {
            from.send("ERR wait for opponent");
            return;
        }

        TicTacToeGame.PutResult result = game.tryPut(from.getMark(), r, c);

        if (!result.ok) {
            from.send("ERR " + result.message);
            return;
        }

        // 상태 방송
        broadcast("STATE " + game.getStateString());

        // 게임 종료 체크
        if (result.winner != TicTacToeGame.EMPTY) {
            broadcast("WIN " + result.winner);
            return;
        }
        if (result.draw) {
            broadcast("DRAW");
            return;
        }

        // 턴 방송
        broadcast("TURN " + game.getCurrentTurn());
    }

    void onDisconnect(ClientSession session) {
        sessions.remove(session);
        broadcast("INFO player left: " + session.getMark());

        // 간단 처리: 누가 나가면 게임 중단
        game.stop();
        broadcast("INFO game stopped");
    }

    void onRestart(ClientSession from) {
        if (sessions.size() < 2) {
            from.send("ERR wait for opponent");
            return;
        }

        game.reset(); // 이미 넣어둔 reset() 사용

        broadcast("INFO restart");
        broadcast("START");
        broadcast("STATE " + game.getStateString());
        broadcast("TURN " + game.getCurrentTurn());
    }

    // ============================================================
    // [Broadcast]
    // ============================================================
    private void broadcast(String msg) {
        for (ClientSession s : sessions) s.send(msg);
    }
}
