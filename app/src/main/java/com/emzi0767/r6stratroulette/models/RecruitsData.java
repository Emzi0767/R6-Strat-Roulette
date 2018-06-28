package com.emzi0767.r6stratroulette.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class RecruitsData {
    @SerializedName("attackers")
    private List<RecruitData> attackers;

    @SerializedName("defenders")
    private List<RecruitData> defenders;

    public RecruitsData() {
        this.attackers = null;
        this.defenders = null;
    }

    public List<RecruitData> getAttackers() {
        return attackers;
    }

    public List<RecruitData> getDefenders() {
        return defenders;
    }
}
