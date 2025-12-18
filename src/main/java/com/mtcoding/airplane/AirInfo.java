package com.mtcoding.airplane;

import com.mtcoding.ex10.Hello;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
public class AirInfo {
    private AirInfo.Response response;

    @Setter
    @Getter
    public static class Response {
        private AirInfo.Response.Header header;
        private AirInfo.Response.Body body;

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
            private AirInfo.Response.Body.Items items;
            private Integer pageNo;
            private Integer numOfRows;
            private Integer totalCount;

            @Setter
            @Getter
            public static class Items {
                private List<AirInfo.Response.Body.Items.Item> item;

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
                        System.out.println("항공사 : "+airlineNm);
                        System.out.println("출발지 : "+depAirportNm);
                        System.out.println("도착지 : "+arrAirportNm);
                        System.out.println("출발시간 : "+ changePlandTime(depPlandTime));
                        System.out.println("도착시간 : "+changePlandTime(arrPlandTime));
                        if(economyCharge != null && economyCharge != 0)
                        System.out.println("이코노미 가격 : "+economyCharge+" 원");
                        if(prestigeCharge != null && prestigeCharge != 0)
                        System.out.println("프레스티지 가격 : "+prestigeCharge+" 원");
                        if(vihicleId != null)
                        System.out.println("여객편ID : "+vihicleId);
                        return "\n";
                    }

                    private String changePlandTime(long time)
                    {
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
