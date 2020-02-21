package com.emzi0767.r6stratroulette.models.json;

import com.google.gson.annotations.SerializedName;

public class RouletteJsonOperatorLoadout {
    @SerializedName("primary")
    private String[] primaryWeapons;

    @SerializedName("secondary")
    private String[] secondaryWeapons;

    @SerializedName("gadgets")
    private String[] gadgets;

    public String[] getPrimaryWeapons() {
        return this.primaryWeapons;
    }

    public String[] getSecondaryWeapons() {
        return this.secondaryWeapons;
    }

    public String[] getGadgets() {
        return this.gadgets;
    }
}
