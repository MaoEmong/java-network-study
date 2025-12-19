package com.mtcoding.Bus;

import com.google.gson.Gson;
import com.mtcoding.MyKeys;

import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BussApp {
    public static Map<String, Integer> citys = new HashMap<>();
    public static Scanner keyboard;

    public static void main(String[] args) {
        keyboard = new Scanner(System.in);
        String bussStopName = "";
        int selectCityCode = 0;
        try {
            System.out.println("도시 코드 로드중...");
            CityData city = new Gson().fromJson(getcityDataJson(), CityData.class);
            System.out.println();

            for (var data : city.getResponse().getBody().getItems().getItem()) {
                citys.put(data.getCityname(), data.getCitycode());
            }

            while (true) {
                System.out.println(">찾으려는 도시를 입력해주세요(시/군)");
                String cityName = keyboard.nextLine();
                boolean isFind = false;
                for (var v : citys.keySet()) {
                    if (v.contains(cityName)) {
                        isFind = true;
                        selectCityCode = citys.get(v);
                        break;
                    }
                }
                if (isFind) break;

                System.out.println("도시를 잘못 입력하셨습니다");
            }
            System.out.println();

            System.out.println(">정류장을 입력해주세요");
            bussStopName = keyboard.nextLine();

            List<CityStopData> cityStopDataList = new ArrayList<>();
            System.out.println("정류장 검색 중...");
            String cityStopJson = getBussStopJson(selectCityCode, bussStopName);
            BussStopData bussStopData = new Gson().fromJson(cityStopJson, BussStopData.class);
            System.out.println();

            ArrayList<CityStopData> bussStopDataList = new ArrayList<>();
            bussStopDataList.addAll(bussStopData.getResponse().getBody().getItems().getItem());

            System.out.println("====== 검색한 정류소 정보 리스트 ======");
            for (int i = 0; i < bussStopDataList.size(); i++) {
                System.out.println((i + 1) + "번 " + bussStopDataList.get(i).getNodenm());
            }
            System.out.println("===================================");
            System.out.println();
            System.out.println("확인할 정류장 번호를 입력하세요");
            int select = keyboard.nextInt();
            String curBusStopJson = getCurBusStopJson(selectCityCode, bussStopDataList.get(select - 1).getNodeid());
            CurrentBussStopData curBusStopData = new Gson().fromJson(curBusStopJson, CurrentBussStopData.class);

            var curBusStopitems = new ArrayList<CurrentBussStopData.Response.Body.Items.Item>();
            if(curBusStopData.getResponse().getBody().getItemsSafe() == null)
            {
                System.out.println("현재 도착 정보가 없습니다");
                return;
            }
            curBusStopitems.addAll(curBusStopData.getResponse().getBody().getItemsSafe().getItemSafe());
            curBusStopitems.sort((a, b) -> a.getArrtime() - b.getArrtime());
            System.out.println("====== 정류장 도착 정보 ======");
            for (int i = 0; i < curBusStopitems.size(); i++) {
                System.out.println((i + 1) + ". " + curBusStopitems.get(i).toString());
            }
            System.out.println("==========================");
            System.out.println();
            System.out.println("확인하고싶은 버스 노선 번호를 입력하세요");
            int selectRoute = keyboard.nextInt();

            String busRouteString = getBusRouteJson(selectCityCode, curBusStopitems.get(selectRoute - 1).getRouteid());
            BusRouteData busRouteData = new Gson().fromJson(busRouteString, BusRouteData.class);
            var busRouteItems = new ArrayList<BusRouteData.Response.Body.Items.Item>();
            busRouteItems.addAll(busRouteData.getResponse().getBody().getItemsSafe().getItemSafe());

            System.out.println("==========================");
            for(int i = 0 ; i < busRouteItems.size(); i++)
            {
                System.out.println((i + 1)+". "+ busRouteItems.get(i).getNodenm());
            }
            System.out.println("==========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getBussStopJson(int cityCode, String stopName) throws Exception {
        String json = "";
        String link = ("https://apis.data.go.kr/1613000/BusSttnInfoInqireService/getSttnNoList?serviceKey=" + MyKeys.kDataKey + "&pageNo=1&numOfRows=10&_type=json&cityCode=${cityCode}&nodeNm=${stopName}").replace("${cityCode}", cityCode + "").replace("${stopName}", URLEncoder.encode(stopName, StandardCharsets.UTF_8));
        URL url = new URL(link);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        Scanner sc = new Scanner(con.getInputStream());
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            json += line;
        }
        return json;
    }

    public static String getcityDataJson() throws Exception {
        String json = "";
        String link = "https://apis.data.go.kr/1613000/ArvlInfoInqireService/getCtyCodeList?serviceKey=" + MyKeys.kDataKey + "&_type=json";
        URL url = new URL(link);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        Scanner sc = new Scanner(con.getInputStream());
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            json += line;
        }
        return json;
    }

    public static String getCurBusStopJson(int cityCode, String nodeId) throws Exception {
        String json = "";
        String link = "https://apis.data.go.kr/1613000/ArvlInfoInqireService/getSttnAcctoArvlPrearngeInfoList?serviceKey=" + MyKeys.kDataKey + "&pageNo=1&numOfRows=10&_type=json&cityCode=${cityCode}&nodeId=${nodeId}".replace("${cityCode}", cityCode + "").replace("${nodeId}", nodeId);
        URL url = new URL(link);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        Scanner sc = new Scanner(con.getInputStream());
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            json += line;
        }
        return json;
    }

    public static String getBusRouteJson(int cityCode, String routeId) throws Exception {
        String json = "";
        String link = "https://apis.data.go.kr/1613000/BusRouteInfoInqireService/getRouteAcctoThrghSttnList?serviceKey="+MyKeys.kDataKey+"&pageNo=1&numOfRows=10&_type=json&cityCode=${cityCode}&routeId=${routeId}".replace("${cityCode}", cityCode + "").replace("${routeId}", routeId);
        URL url = new URL(link);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        Scanner sc = new Scanner(con.getInputStream());
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            json += line;
        }
        return json;
    }
}
