package com.mtcoding.ex10;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class URLConnectionReader {
    public static void main(String[] args) {
        try {
            // 1. ip와 port를 통해 소켓을 만들고 스트림 연결
            URL url = new URL("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EB%82%A0%EC%94%A8&ackey=6bcutvt2");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            // 2. 데이터를 받아올 리드 버퍼 생성
            Scanner sc = new Scanner(con.getInputStream());
            String html = "";
            String line = "";
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                html += line;
                html += "\n";
            }
            System.out.println(html);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
