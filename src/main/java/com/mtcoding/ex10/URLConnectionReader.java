package com.mtcoding.ex10;

import com.mtcoding.MyKeys;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class URLConnectionReader {
    public static void main(String[] args) {
        try {
            // 1. ip와 port를 통해 소켓을 만들고 스트림 연결
            URL url = new URL("https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey="+ MyKeys.kDataKey +"&pageNo=1&numOfRows=1000&dataType=JSON&base_date=20251218&base_time=1200&nx=98&ny=75#");
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
