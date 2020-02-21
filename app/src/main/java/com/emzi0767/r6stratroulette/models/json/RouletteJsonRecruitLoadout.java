package com.emzi0767.r6stratroulette.models.json;

import com.google.gson.annotations.SerializedName;

public class RouletteJsonRecruitLoadout {
    @SerializedName("primary")
    private String[] primaryWeapons;

    @SerializedName("secondary")
    private String[] secondaryWeapons;

    @SerializedName("gadget1")
    private String[] primaryGadgets;

    @SerializedName("gadget2")
    private String[] secondaryGadgets;

    public String[] getPrimaryWeapons() {
        return this.primaryWeapons;
    }

    public String[] getSecondaryWeapons() {
        return this.secondaryWeapons;
    }

    public String[] getPrimaryGadgets() {
        return this.primaryGadgets;
    }

    public String[] getSecondaryGadgets() {
        return this.secondaryGadgets;
    }
}
