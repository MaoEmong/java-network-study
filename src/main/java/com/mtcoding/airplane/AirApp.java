package com.mtcoding.airplane;

import com.google.gson.Gson;
import com.mtcoding.MyKeys;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AirApp {
    // 키 - 무안 / 값 - NAARKJB
    static Map<String, String> ports = new HashMap<>();

    public static void main(String[] args) {
        try {
            // 1. 공항 정보 내려받기 - HTTPURLConnection으로
            String site = "https://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getArprtList?serviceKey=" + MyKeys.kDataKey + "&_type=json";
            URL url = new URL(site);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Scanner sc = new Scanner(con.getInputStream());
            String json = "";
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                json += line;
            }
            // 2. PortInfo로 오브젝트화
            Gson gson = new Gson();
            PortInfo portInfo = gson.fromJson(json, PortInfo.class);
            // 3. ports에 airpotId에 있는 값, airpotNm에 있는 값 옮기기
            for (var data : portInfo.getResponse().getBody().getItems().getItem()) {
                ports.put(data.getAirportNm(), data.getAirportId());
            }

            Scanner keyboard = new Scanner(System.in);
            String dep = ""; // 출발지
            String depKey = "";
            String arr = ""; // 목적지
            String arrKey = "";
            String time = ""; // yyyymmdd
            // 4. Scanner로 출발지 입력받기
            for (var n : ports.keySet()) {
                System.out.print(n + "/");
            }
            System.out.println();
            System.out.println(">출발지를 입력해주세요 : ");
            dep = keyboard.nextLine();
            depKey = ports.get(dep);
            // 5. Scanner로 목적지 받기
            System.out.println(">목적지를 입력해주세요 : ");
            arr = keyboard.nextLine();
            arrKey = ports.get(arr);
            // 6. Scanner로 출발날짜 받기
            System.out.println(">출발시간을 입력해주세요(yyyymmdd) : ");
            time = keyboard.nextLine();

            System.out.println();

            // 7. 동적인 URL 만들기
            String site2 = "https://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList?serviceKey=" + MyKeys.kDataKey + "&pageNo=1&numOfRows=10&_type=json&depAirportId=${depKey}&arrAirportId=${arrKey}&depPlandTime=${time}".replace("${depKey}", depKey).replace("${arrKey}", arrKey).replace("${time}", time);
            // 8. 항공 스케줄 받아오기 - HTTPURLConnection 호출
            var url2 = new URL(site2);
            var con2 = (HttpURLConnection) url2.openConnection();
            var sc2 = new Scanner(con2.getInputStream());
            json = "";
            while (sc2.hasNextLine()) {
                String line = sc2.nextLine();
                json += line;
            }
            // 9. json 파싱 -> arrInfo
            AirInfo airInfo = gson.fromJson(json, AirInfo.class);
            // 10. 항공스케줄 전체 출력
            for (var data : airInfo.getResponse().getBody().getItems().getItem()) {
                System.out.println(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
