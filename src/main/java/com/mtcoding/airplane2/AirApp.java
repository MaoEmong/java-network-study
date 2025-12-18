package com.mtcoding.airplane2;

import com.google.gson.Gson;
import com.mtcoding.MyKeys;
import lombok.Getter;

import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class AirApp {
    // 키 - 무안 / 값 - NAARKJB
    @Getter
    static Map<String, String> ports = new HashMap<>();

    public static void main(String[] args) {
        try {
            System.out.println("Start Program...");
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
            System.out.println("Get Port Data");

            String portsList = "";

            for (var n : ports.keySet()) {
                portsList += (n + "/");
            }
            System.out.println("Server Construct");
            // 서버 활성화
            ServerSocket ss = new ServerSocket(20000);
            
            // 클라이언트 연결
            while(true)
            {
                // 연결 대기
                Socket socket = ss.accept();
                System.out.println("Client Connect");
                ClientHandler client = new ClientHandler(socket,portsList);
                new Thread(client).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
