package com.emzi0767.r6stratroulette.models.json;

import com.google.gson.annotations.SerializedName;

public class RouletteJsonOperator {
    @SerializedName("name")
    private String name;

    @SerializedName("icon")
    private String icon;

    @SerializedName("ctu")
    private String ctu;

    @SerializedName("loadout")
    private RouletteJsonOperatorLoadout loadout;

    public String getName() {
        return this.name;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getCTU() {
        return this.ctu;
    }

    public RouletteJsonOperatorLoadout getLoadout() {
        return this.loadout;
    }
}
