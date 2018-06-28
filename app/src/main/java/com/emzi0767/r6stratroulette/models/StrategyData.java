package com.emzi0767.r6stratroulette.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class StrategyData {
    @SerializedName("name")
    private String name;

    @SerializedName("desc")
    private String description;

    @SerializedName("side")
    private List<String> sides;

    @SerializedName("mode")
    private List<String> gameModes;

    public StrategyData() {
        this.name = null;
        this.description = null;
        this.sides = null;
        this.gameModes = null;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getSides() {
        return sides;
    }

    public List<String> getGameModes() {
        return gameModes;
    }
}
