package com.emzi0767.r6stratroulette.models;

import com.google.gson.annotations.SerializedName;

public final class OperatorData {
    @SerializedName("name")
    private String name;

    @SerializedName("icon")
    private String icon;

    @SerializedName("ctu")
    private String ctu;

    public OperatorData() {
        this.name = null;
        this.icon = null;
        this.ctu = null;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getCtu() {
        return ctu;
    }
}
