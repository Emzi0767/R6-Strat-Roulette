package com.emzi0767.r6stratroulette.models.runtime;

import java.util.EnumSet;

public enum RouletteRuntimeStrategySide {
    UNKNOWN(0),
    ATTACKERS(1),
    DEFENDERS(2);

    private final int value;

    RouletteRuntimeStrategySide(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static EnumSet<RouletteRuntimeStrategySide> fromNameArray(String[] arr) {
        EnumSet<RouletteRuntimeStrategySide> val = EnumSet.noneOf(RouletteRuntimeStrategySide.class);
        for (String s : arr)
            val.add(fromName(s));

        return val;
    }

    public static EnumSet<RouletteRuntimeStrategySide> fromValue(int value) {
        EnumSet<RouletteRuntimeStrategySide> val = EnumSet.noneOf(RouletteRuntimeStrategySide.class);

        for (RouletteRuntimeStrategySide ss : EnumSet.allOf(RouletteRuntimeStrategySide.class))
            if ((value & ss.getValue()) == ss.getValue())
                val.add(ss);

        if (val.isEmpty())
            val.add(UNKNOWN);

        return val;
    }

    public static RouletteRuntimeStrategySide fromValueSingle(int value) {
        if (ATTACKERS.getValue() == value)
            return ATTACKERS;
        else if (DEFENDERS.getValue() == value)
            return DEFENDERS;

        return UNKNOWN;
    }

    public static RouletteRuntimeStrategySide fromName(String s) {
        switch (s.toLowerCase()) {
            case "atk":
                return ATTACKERS;

            case "def":
                return DEFENDERS;
        }

        return UNKNOWN;
    }
}
