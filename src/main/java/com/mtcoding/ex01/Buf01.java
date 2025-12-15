package com.mtcoding.ex01;

import java.io.IOException;
import java.io.InputStream;

public class Buf01 {
    public static void main(String[] args) {
        // 1. 키보드와 컴퓨터가 Byte 스트림이 연결됨
        InputStream in = System.in;
        
        // 2. 바이트 읽기
        try {
            int n = in.read();  // 키보드로 부터 입력 대기 상태 \n <-(엔터)
            System.out.println(n);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}