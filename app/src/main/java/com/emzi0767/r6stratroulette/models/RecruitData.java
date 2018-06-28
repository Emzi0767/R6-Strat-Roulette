package com.emzi0767.r6stratroulette.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class RecruitData {
    @SerializedName("ctu")
    private String ctu;

    @SerializedName("weapon1")
    private List<String> primaryWeapons;

    @SerializedName("weapon2")
    private List<String> secondaryWeapons;

    @SerializedName("gadget1")
    private List<String> primaryGadgets;

    @SerializedName("gadget2")
    private List<String> secondaryGadgets;

    public RecruitData() {
        this.ctu = null;
        this.primaryWeapons = null;
        this.secondaryWeapons = null;
        this.primaryGadgets = null;
        this.secondaryGadgets = null;
    }

    public String getCtu() {
        return ctu;
    }

    public List<String> getPrimaryWeapons() {
        return primaryWeapons;
    }

    public List<String> getSecondaryWeapons() {
        return secondaryWeapons;
    }

    public List<String> getPrimaryGadgets() {
        return primaryGadgets;
    }

    public List<String> getSecondaryGadgets() {
        return secondaryGadgets;
    }
}
