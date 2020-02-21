package com.emzi0767.r6stratroulette.models.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class RouletteJsonRoot {
    @SerializedName("version")
    private RouletteJsonVersion version;

    @SerializedName("ctus")
    private Map<String, RouletteJsonCTU> ctus;

    @SerializedName("gadgets")
    private Map<String, Map<String, String>> gadgets;

    @SerializedName("operators")
    private RouletteJsonOperatorSets operatorSets;

    @SerializedName("recruits")
    private RouletteJsonRecruitSets recruits;

    @SerializedName("strats")
    private List<RouletteJsonStrategy> strategies;

    public RouletteJsonVersion getVersion() {
        return this.version;
    }

    public Map<String, RouletteJsonCTU> getCTUs() {
        return this.ctus;
    }

    public Map<String, Map<String, String>> getGadgets() {
        return this.gadgets;
    }

    public RouletteJsonOperatorSets getOperatorSets() {
        return this.operatorSets;
    }

    public RouletteJsonRecruitSets getRecruits() {
        return this.recruits;
    }

    public List<RouletteJsonStrategy> getStrategies() {
        return this.strategies;
    }
}
