package com.mtcoding.airplane2;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
public class PortInfo {
    private Response response;

    @Setter
    @Getter
    public static class Response {
        private Header header;
        private Body body;

        @Setter
        @Getter
        public static class Header {
            private String resultCode;
            private String resultMsg;
        }

        @Setter
        @Getter
        public static class Body {
            private String dataType;
            private Items items;
            private Integer pageNo;
            private Integer numOfRows;
            private Integer totalCount;

            @Setter
            @Getter
            public static class Items {
                private List<Item> item;

                @ToString
                @Setter
                @Getter
                public static class Item {
                    private String airportId;
                    private String airportNm;
                }
            }
        }
    }
}
