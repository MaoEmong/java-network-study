package com.mtcoding.ex10;

import java.util.ArrayList;

public class Hello {
    private Response response;

    class Response {
        private Header header;
        private Body body;

        class Header {
            private String resultCode;
            private String resultMsg;
        }

        class Body {
            private String dataType;
            private Items items;
            private Integer pageNo;
            private Integer numOfRows;
            private Integer totalCount;

            class Items {
                private ArrayList<Item> item = new ArrayList<>();

                class Item {
                    private String baseData;
                    private String baseTime;
                    private String category;
                    private Integer nx;
                    private Integer ny;
                    private String obsrValue;
                }
            }
        }
    }
}
