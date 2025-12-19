package com.mtcoding.Bus;

import com.google.gson.Gson;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
public class CurrentBussStopData {
    private CurrentBussStopData.Response response;

    @Setter
    @Getter
    public static class Response {
        private CurrentBussStopData.Response.Header header;
        private CurrentBussStopData.Response.Body body;

        @Setter
        @Getter
        public static class Header {
            private String resultCode;
            private String resultMsg;
        }

        @Setter
        @Getter
        public static class Body {
            private Object items;

            public Items getItemsSafe() {
                if (items == null) return null;
                try {
                    Gson gson = new Gson();
                    return gson.fromJson(gson.toJson(items), Items.class);
                } catch (Exception e) {
                    return null;
                }
            }

            @Setter
            @Getter
            public static class Items {
                Object item;

                public List<Item> getItemSafe() {

                    List<Item> result = new ArrayList<>();
                    if (item == null) return result;

                    Gson gson = new Gson();

                    // 1️⃣ 여러 개라고 가정 (List)
                    try {
                        List list = (List) item;
                        for (int i = 0; i < list.size(); i++) {
                            Object one = list.get(i);
                            Item it = gson.fromJson(gson.toJson(one), Item.class);
                            it.setRouteno(it.getRouteno().replace(".0",""));
                            result.add(it);
                        }
                        return result;
                    } catch (Exception e) {
                        // List 아님 → 다음으로
                    }

                    // 2️⃣ 한 개라고 가정 (Map / 객체)
                    try {
                        Item it = gson.fromJson(gson.toJson(item), Item.class);
                        it.setRouteno(it.getRouteno().replace(".0",""));
                        result.add(it);
                        return result;
                    } catch (Exception e) {
                        // 객체도 아님 → 없는 데이터
                    }

                    return result;
                }

                @ToString
                @Setter
                @Getter
                public static class Item {
                    private Integer arrprevstationcnt;  // 도착예정버스 남은 정류장 수
                    private Integer arrtime;    // 도착예상 시간
                    private String nodeid;  // 정류소id
                    private String nodenm;  // 정류소명
                    private String routeid; // 정류소id
                    private String routeno; // 노선번호
                    private String routetp; // 노선유형
                    private String vehicletp;   // 도착예정버스차량유형

                    @Override
                    public String toString() {
                        String result = "";
                        result += routeno+" 번\n"+
                                "도착까지 " + arrprevstationcnt+"정거장 남음\n"+
                                "도착까지 "+(arrtime / 60)+"분 남음\n";

                        return result;
                    }
                }

            }
        }
    }
}
