package com.mtcoding.Bus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CityStopData {
    private Double gpslati; // 정류소 y좌표
    private Double gpslong; // 정류소 x좌표
    private String nodeid;  // 정류소 id
    private String nodenm;  // 정류소 명
    private Integer nodeno; // 정류소 번호
}
