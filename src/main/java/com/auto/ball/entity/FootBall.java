package com.auto.ball.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Builder
public class FootBall implements Serializable {
    private Integer teamIndex;
    private String hostName;
    private String guestName;
    @Builder.Default
    private String zeroScore="0.0";

    private String price;
    private String bf;
    private String pl;
}
