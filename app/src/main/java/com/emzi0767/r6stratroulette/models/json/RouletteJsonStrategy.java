package com.emzi0767.r6stratroulette.models.json;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class RouletteJsonStrategy {
    @SerializedName("name")
    private Map<String, String> localizedNames;

    @SerializedName("desc")
    private Map<String, String> localizedDescriptions;

    @SerializedName("side")
    private String[] sides;

    @SerializedName("mode")
    private String[] modes;

    public Map<String, String> getLocalizedNames() {
        return this.localizedNames;
    }

    public Map<String, String> getLocalizedDescriptions() {
        return this.localizedDescriptions;
    }

    public String[] getSides() {
        return this.sides;
    }

    public String[] getModes() {
        return this.modes;
    }
}
