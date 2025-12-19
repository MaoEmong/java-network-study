package com.mtcoding.Bus;

import com.google.gson.Gson;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
public class BusRouteData {
    private BusRouteData.Response response;

    @Setter
    @Getter
    public static class Response {
        private BusRouteData.Response.Header header;
        private BusRouteData.Response.Body body;

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

                public List<BusRouteData.Response.Body.Items.Item> getItemSafe() {

                    List<BusRouteData.Response.Body.Items.Item> result = new ArrayList<>();
                    if (item == null) return result;

                    Gson gson = new Gson();
                    try {
                        List list = (List) item;
                        for (int i = 0; i < list.size(); i++) {
                            Object one = list.get(i);
                            BusRouteData.Response.Body.Items.Item it = gson.fromJson(gson.toJson(one), BusRouteData.Response.Body.Items.Item.class);
                            result.add(it);
                        }
                        return result;
                    } catch (Exception e) {
                    }

                    try {
                        BusRouteData.Response.Body.Items.Item it = gson.fromJson(gson.toJson(item), BusRouteData.Response.Body.Items.Item.class);
                        result.add(it);
                        return result;
                    } catch (Exception e) {
                    }

                    return result;
                }

                @ToString
                @Setter
                @Getter
                public static class Item {
                    private Double gpslati;
                    private Double gpslong;
                    private String nodeid;
                    private String nodenm;
                    private String nodeno;
                    private Integer nodeord;
                    private String routeid;
                    private Integer updowncd;
                }

            }
        }
    }
}
