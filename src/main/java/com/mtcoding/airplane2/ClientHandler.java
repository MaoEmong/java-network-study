package com.mtcoding.airplane2;

import com.google.gson.Gson;
import com.mtcoding.MyKeys;
import com.mtcoding.MyTestChatServer.MyChatServer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private Scanner reader;
    private PrintWriter sender;
    private String portsList;

    public ClientHandler(Socket socket, String portsList) throws IOException {
        System.out.println("Client Construct");
        this.socket = socket;
        reader = new Scanner(socket.getInputStream());
        sender = new PrintWriter(socket.getOutputStream(),true);
        this.portsList = portsList;
    }

    @Override
    public void run() {
        try {
            System.out.println("Client Start Thread");
            String dep = ""; // 출발지
            String depKey = "";
            String arr = ""; // 목적지
            String arrKey = "";
            String time = ""; // yyyymmdd

            sender.println(portsList+"\n>출발지를 입력해주세요 : ");
            dep = reader.nextLine();
            System.out.println("Get DepData");
            depKey = AirApp.getPorts().get(dep);
            sender.println(">목적지를 입력해주세요 : ");
            arr = reader.nextLine();
            System.out.println("Get ArrData");
            arrKey = AirApp.getPorts().get(arr);
            sender.println(">출발시간을 입력해주세요(yyyymmdd) : ");
            time = reader.nextLine();
            System.out.println("Get TimeData");
            sender.println(">데이터 조회중 잠시만 기다려주세요");
            System.out.println("Data Searching..."+dep+"/"+arr+"/"+time);

            String site = "https://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList?serviceKey=" + MyKeys.kDataKey + "&pageNo=1&numOfRows=10&_type=json&depAirportId=${depKey}&arrAirportId=${arrKey}&depPlandTime=${time}".replace("${depKey}", depKey).replace("${arrKey}", arrKey).replace("${time}", time);
            var url = new URL(site);
            var con = (HttpURLConnection) url.openConnection();
            var sc = new Scanner(con.getInputStream());
            String json = "";
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                json += line;
            }
            AirInfo airInfo = new Gson().fromJson(json, AirInfo.class);
            System.out.println("Data Searching Success");
            for (var data : airInfo.getResponse().getBody().getItems().getItem()) {
                sender.println(data);
            }
            sender.println("END");
            System.out.println("Send Data");
            System.out.println("Client UnConnected");

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
