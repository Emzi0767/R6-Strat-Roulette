package com.emzi0767.r6stratroulette.models.json;

import com.google.gson.annotations.SerializedName;

public class RouletteJsonCTU {
    @SerializedName("name")
    private String name;

    @SerializedName("abbr")
    private String abbreviation;

    public String getName() {
        return this.name;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }
}
