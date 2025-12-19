package com.mtcoding.Bus;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
public class BussStopData {
    private BussStopData.Response response;

    @Setter
    @Getter
    public static class Response {
        private BussStopData.Response.Header header;
        private BussStopData.Response.Body body;

        @Setter
        @Getter
        public static class Header {
            private String resultCode;
            private String resultMsg;
        }

        @Setter
        @Getter
        public static class Body {
            private BussStopData.Response.Body.Items items;

            @Setter
            @Getter
            public static class Items {
                private List<CityStopData> item;

            }
        }
    }
}

