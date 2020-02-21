package com.emzi0767.r6stratroulette.models.json;

import com.google.gson.annotations.SerializedName;

public class RouletteJsonRecruitSets {
    @SerializedName("attacker")
    private RouletteJsonRecruitLoadout attacker;

    @SerializedName("defender")
    private RouletteJsonRecruitLoadout defender;

    public RouletteJsonRecruitLoadout getAttacker() {
        return this.attacker;
    }

    public RouletteJsonRecruitLoadout getDefender() {
        return this.defender;
    }
}
