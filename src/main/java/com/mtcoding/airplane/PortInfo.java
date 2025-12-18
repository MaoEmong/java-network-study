package com.mtcoding.airplane;

import com.mtcoding.ex10.Hello;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
public class PortInfo {
    private PortInfo.Response response;

    @Setter
    @Getter
    public static class Response {
        private PortInfo.Response.Header header;
        private PortInfo.Response.Body body;

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
            private PortInfo.Response.Body.Items items;
            private Integer pageNo;
            private Integer numOfRows;
            private Integer totalCount;

            @Setter
            @Getter
            public static class Items {
                private List<PortInfo.Response.Body.Items.Item> item;

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
