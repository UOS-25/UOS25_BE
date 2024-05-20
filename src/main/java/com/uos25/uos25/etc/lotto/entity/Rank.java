package com.uos25.uos25.etc.lotto.entity;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Rank {
    ONE(6),
    TWO(5),
    THREE(4),
    FOUR(3),
    FIVE(2),
    FAIL(1);

    private final int check;

    Rank(int check) {
        this.check = check;
    }

    public static Rank from(int check) {
        return Arrays.stream(Rank.values()).filter(rank -> rank.getCheck() == check).findAny().orElse(FAIL);
    }
}
