package com.emzi0767.r6stratroulette.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public final class RouletteData {
    @SerializedName("version")
    private VersionData version;

    @SerializedName("ctus")
    private Map<String, CtuData> ctus;

    @SerializedName("operators")
    private OperatorsData operators;

    @SerializedName("recruits")
    private RecruitsData recruits;

    @SerializedName("strats")
    private List<StrategyData> strategies;

    public RouletteData() {
        this.version = null;
        this.ctus = null;
        this.operators = null;
        this.recruits = null;
        this.strategies = null;
    }

    public VersionData getVersion() {
        return version;
    }

    public Map<String, CtuData> getCtus() {
        return ctus;
    }

    public OperatorsData getOperators() {
        return operators;
    }

    public RecruitsData getRecruits() {
        return recruits;
    }

    public List<StrategyData> getStrategies() {
        return strategies;
    }
}
