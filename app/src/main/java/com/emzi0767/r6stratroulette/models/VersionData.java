package com.emzi0767.r6stratroulette.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public final class VersionData {
    @SerializedName("number")
    private int number;

    @SerializedName("timestamp")
    private Date timestamp;

    public VersionData() {
        this.number = 0;
        this.timestamp = new Date();
    }

    public int getNumber() {
        return this.number;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }
}
