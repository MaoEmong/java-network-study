package com.mtcoding.MyTestChatServer;

/**
 * MyChatServerApp
 * ------------------------------------------------------------
 * - 채팅 서버 실행을 담당하는 애플리케이션 진입점(main 클래스)이다.
 * - 실제 서버 로직(MyChatServer)과 예외 처리(MyExceptionHandler)를 분리하여
 *   main 메서드는 "실행 역할"만 담당하도록 설계했다.
 */
public class MyChatServerApp {

    /**
     * 프로그램 진입점
     * ------------------------------------------------------------
     * 1) MyChatServer를 생성한다.
     * 2) 서버를 시작한다.
     * 3) 실행 중 발생하는 예외는 MyExceptionHandler에게 위임한다.
     */
    public static void main(String[] args) {
        try {
            // 1️⃣ 서버 생성 (포트 지정)
            MyChatServer server = new MyChatServer(20000);

            // 2️⃣ 서버 시작
            // start() 내부에서 accept() 루프가 돌며 서버는 계속 실행된다.
            server.start();

        } catch (Exception e) {
            // 3️⃣ 예외 처리 위임
            // JVM 기본 예외 출력이 아닌, 내가 정의한 방식으로 처리한다.
            MyExceptionHandler.handle(e);
        }
    }
}
