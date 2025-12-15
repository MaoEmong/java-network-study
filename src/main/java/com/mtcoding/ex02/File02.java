package com.mtcoding.ex02;

import java.io.*;
import java.util.Arrays;

// 쓰기
public class File02 {
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("socket.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            System.out.println(line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
