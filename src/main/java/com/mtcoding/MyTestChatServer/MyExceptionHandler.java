package com.mtcoding.MyTestChatServer;

import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;

/**
 * MyExceptionHandler
 * ------------------------------------------------------------
 * - 서버 실행 중 발생하는 예외를 한 곳에서 처리하기 위한 클래스다.
 * - JVM의 기본 예외 출력 방식(printStackTrace)에 의존하지 않고,
 *   개발자가 의도한 메시지 형식으로 예외를 처리한다.
 * - main() 또는 서버 시작 지점에서 발생한 예외를 위임받아 처리한다.
 */
public class MyExceptionHandler {

    /**
     * 예외 처리 진입점
     * ------------------------------------------------------------
     * - 발생한 예외 타입에 따라 메시지를 분기 처리한다.
     * - 서버 실행 단계에서 발생할 수 있는 대표적인 예외들을 구분한다.
     *
     * @param e 발생한 예외 객체
     */
    public static void handle(Exception e) {

        // 서버 포트가 이미 사용 중인 경우
        // 예: 동일한 서버를 두 번 실행했을 때
        if (e instanceof BindException) {
            System.out.println("[Error] 포트가 이미 사용 중이다. (20000)");

            // 서버 또는 외부 네트워크 연결 실패
        } else if (e instanceof ConnectException) {
            System.out.println("[Error] 연결 실패");

            // 입출력(IO) 관련 문제
            // 소켓, 스트림 생성/종료 과정에서 주로 발생
        } else if (e instanceof IOException) {
            System.out.println("[Error] IO 문제 발생: " + e.getMessage());

            // 그 외 예상하지 못한 예외
        } else {
            System.out.println("[Error] 알 수 없는 예외: " + e.getClass().getSimpleName());
            System.out.println("[Error] 메시지: " + e.getMessage());
        }
    }
}
