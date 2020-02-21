package com.emzi0767.r6stratroulette.models.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RouletteJsonOperatorSets {
    @SerializedName("attackers")
    private List<RouletteJsonOperator> attackers;

    @SerializedName("defenders")
    private List<RouletteJsonOperator> defenders;

    public List<RouletteJsonOperator> getAttackers() {
        return this.attackers;
    }

    public List<RouletteJsonOperator> getDefenders() {
        return this.defenders;
    }
}
