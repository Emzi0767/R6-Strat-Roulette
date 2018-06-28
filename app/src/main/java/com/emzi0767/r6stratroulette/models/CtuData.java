package com.emzi0767.r6stratroulette.models;

import com.google.gson.annotations.SerializedName;

public final class CtuData {
    @SerializedName("name")
    private String name;

    @SerializedName("abbr")
    private String abbreviation;

    @SerializedName("icon")
    private String icon;

    public CtuData() {
        this.name = null;
        this.abbreviation = null;
        this.icon = null;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getIcon() {
        return icon;
    }
}
