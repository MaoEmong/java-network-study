package com.mtcoding.TicTacToe;

import java.io.*;
import java.net.Socket;

// ============================================================
// 2) ClientSession : 소켓 1명 처리(송수신), 프로토콜 파싱
// ============================================================
public class ClientSession implements Runnable{
    private final Socket socket;
    private final BufferedReader br;
    private final PrintWriter pw;

    private final char mark;
    private String name = "anon";
    private final TicTacToeServer server;

    ClientSession(Socket socket, char mark, TicTacToeServer server) throws IOException {
        this.socket = socket;
        this.mark = mark;
        this.server = server;

        this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    }

    char getMark() { return mark; }
    void setName(String name) { this.name = name; }
    String getName() { return name; }

    void send(String msg) { pw.println(msg); }

    @Override
    public void run() {
        try {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // HELLO name
                if (line.startsWith("HELLO ")) {
                    server.onHello(this, line.substring(6).trim());
                    continue;
                }

                // PUT r c
                if (line.startsWith("PUT ")) {
                    String[] sp = line.split("\\s+");
                    if (sp.length != 3) {
                        send("ERR PUT format: PUT r c");
                        continue;
                    }
                    int r = Integer.parseInt(sp[1]);
                    int c = Integer.parseInt(sp[2]);
                    server.onPut(this, r, c);
                    continue;
                }

                // RESTART
                if (line.equalsIgnoreCase("RESTART")) {
                    System.out.println("Call ReStart");
                    server.onRestart(this);
                    continue;
                }

                // QUIT
                if (line.equalsIgnoreCase("QUIT"))
                {
                    System.out.println("Call Quit");
                    break;
                }

                send("ERR unknown command");
            }
        } catch (Exception e) {
            System.out.println("[server] session error(" + mark + "): " + e.getMessage());
        } finally {
            close();
            server.onDisconnect(this);
        }
    }

    private void close() {
        try { socket.close(); } catch (Exception ignored) {}
    }
}
