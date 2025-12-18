package com.mtcoding.airplane2;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class AirInfo {
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

                @Setter
                @Getter
                public static class Item {
                    private String airlineNm;
                    private String arrAirportNm;
                    private Long arrPlandTime;
                    private String depAirportNm;
                    private Long depPlandTime;
                    private Integer economyCharge;
                    private Integer prestigeCharge;
                    private String vihicleId;

                    @Override
                    public String toString() {
                        String result = "";
                        result += "항공사 : " + airlineNm + "\n";
                        result += "출발지 : " + depAirportNm + "\n";
                        result += "도착지 : " + arrAirportNm + "\n";
                        result += "출발시간 : " + changePlandTime(depPlandTime) + "\n";
                        result += "도착시간 : " + changePlandTime(arrPlandTime) + "\n";
                        if (economyCharge != null && economyCharge != 0)
                            result += "이코노미 가격 : " + economyCharge + " 원" + "\n";
                        if (prestigeCharge != null && prestigeCharge != 0)
                            result += "프레스티지 가격 : " + prestigeCharge + " 원" + "\n";
                        if (vihicleId != null)
                            result += "여객편ID : " + vihicleId + "\n";
                        return result;
                    }

                    private String changePlandTime(long time) {
                        String temp = "분";
                        temp += time % 10;
                        time /= 10;
                        temp += time % 10;
                        time /= 10;
                        temp += "시";
                        temp += time % 10;
                        time /= 10;
                        temp += time % 10;
                        time /= 10;
                        temp += "일";
                        temp += time % 10;
                        time /= 10;
                        temp += time % 10;
                        time /= 10;
                        temp += "월";
                        temp += time % 10;
                        time /= 10;
                        temp += time % 10;
                        time /= 10;
                        temp += "년";
                        temp += time % 10;
                        time /= 10;
                        temp += time % 10;
                        time /= 10;
                        temp += time % 10;
                        time /= 10;
                        temp += time % 10;

                        StringBuffer buf = new StringBuffer(temp);
                        temp = buf.reverse().toString();
                        return temp;
                    }
                }
            }
        }
    }
}
