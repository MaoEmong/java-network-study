package com.mtcoding.Bus;

import com.mtcoding.ex10.Hello;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
public class CityData {
    private CityData.Response response;

    @Setter
    @Getter
    public static class Response {
        private CityData.Response.Header header;
        private CityData.Response.Body body;

        @Setter
        @Getter
        public static class Header {
            private String resultCode;
            private String resultMsg;
        }

        @Setter
        @Getter
        public static class Body {
            private CityData.Response.Body.Items items;

            @Setter
            @Getter
            public static class Items {
                private List<CityData.Response.Body.Items.Item> item;

                @ToString
                @Setter
                @Getter
                public static class Item {
                    private Integer citycode;   // 도시코드
                    private String cityname;    // 도시이름
                }
            }
        }
    }
}
