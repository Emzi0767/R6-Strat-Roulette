package com.emzi0767.r6stratroulette.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class OperatorsData {
    @SerializedName("attackers")
    private List<OperatorData> attackers;

    @SerializedName("defenders")
    private List<OperatorData> defenders;

    public OperatorsData() {
        this.attackers = null;
        this.defenders = null;
    }

    public List<OperatorData> getAttackers() {
        return attackers;
    }

    public List<OperatorData> getDefenders() {
        return defenders;
    }
}
