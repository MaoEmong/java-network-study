package com.mtcoding.ex02;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// 쓰기
public class File01 {
    public static void main(String[] args) {
        try {
            FileWriter fw = new FileWriter("socket.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("ABC");
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
