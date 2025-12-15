package com.mtcoding.ex01;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Buf04 {
    public static void main(String[] args) {
        // 1. 바이트 스트림 연결
        OutputStream out = System.out;  // 모니터 연결

        // 2. 숫자를 문자로 변환해주는 것을 설정
        OutputStreamWriter ow = new OutputStreamWriter(out);

        // 3. 직접 배열을 다는게 아니라, 가변 배열을 달아줌
        BufferedWriter bw = new BufferedWriter(ow);

        try {
            bw.write("ABC"); //
            bw.flush(); // 버퍼 비우기 (꽉 찼을때 자동으로 비움)
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
