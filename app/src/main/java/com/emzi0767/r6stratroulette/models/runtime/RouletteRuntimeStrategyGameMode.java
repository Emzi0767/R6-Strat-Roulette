package com.emzi0767.r6stratroulette.models.runtime;

import java.util.EnumSet;

public enum RouletteRuntimeStrategyGameMode {
    UNKNOWN(0),
    SECURE_AREA(1),
    BOMB(2),
    HOSTAGE(4);

    private final int value;
    RouletteRuntimeStrategyGameMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static EnumSet<RouletteRuntimeStrategyGameMode> fromNameArray(String[] arr) {
        EnumSet<RouletteRuntimeStrategyGameMode> val = EnumSet.noneOf(RouletteRuntimeStrategyGameMode.class);
        for (String s : arr)
            val.add(fromName(s));

        return val;
    }

    public static EnumSet<RouletteRuntimeStrategyGameMode> fromValue(int value) {
        EnumSet<RouletteRuntimeStrategyGameMode> val = EnumSet.noneOf(RouletteRuntimeStrategyGameMode.class);

        for (RouletteRuntimeStrategyGameMode sgm : EnumSet.allOf(RouletteRuntimeStrategyGameMode.class))
            if ((value & sgm.getValue()) == sgm.getValue())
                val.add(sgm);

        if (val.isEmpty())
            val.add(UNKNOWN);

        return val;
    }

    public static RouletteRuntimeStrategyGameMode fromValueSingle(int value) {
        if (SECURE_AREA.getValue() == value)
            return SECURE_AREA;
        else if (BOMB.getValue() == value)
            return BOMB;
        else if (HOSTAGE.getValue() == value)
            return HOSTAGE;

        return UNKNOWN;
    }

    public static RouletteRuntimeStrategyGameMode fromName(String s) {
        switch (s.toLowerCase()) {
            case "area":
                return SECURE_AREA;

            case "bomb":
                return BOMB;

            case "hostage":
                return HOSTAGE;
        }

        return UNKNOWN;
    }
}
