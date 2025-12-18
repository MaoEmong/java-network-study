package com.mtcoding.ex10;

import com.google.gson.Gson;
import com.mtcoding.MyKeys;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class TempApp {
    public static void main(String[] args) {
        try {
            // 1. ip와 port를 통해 소켓을 만들고 스트림 연결
            URL url = new URL("https://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList?serviceKey="+MyKeys.kDataKey+"&pageNo=1&numOfRows=10&_type=json&depAirportId=NAARKPK&arrAirportId=NAARKPC&depPlandTime=20251219&airlineId=JJA");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            // 2. 데이터를 받아올 리드 버퍼 생성
            Scanner sc = new Scanner(con.getInputStream());
            String json = "";
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                json += line;
                json += "\n";
            }
//            Hello h = new Gson().fromJson(json, Hello.class);
            
            Gson gson = new Gson();
            // 1. String형 데이터인 json을 Hello Object로 변경
            Hello hello = gson.fromJson(json, Hello.class);

            for(var data : hello.getResponse().getBody().getItems().getItem())
            {
                System.out.println(data);
            }

            // 2. Hello Object를 String형인 json으로 변경
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
