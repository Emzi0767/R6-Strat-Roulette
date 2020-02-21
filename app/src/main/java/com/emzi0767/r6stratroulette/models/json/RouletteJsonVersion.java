package com.emzi0767.r6stratroulette.models.json;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RouletteJsonVersion {
    @SerializedName("number")
    private int numeric;

    @SerializedName("timestamp")
    private Date timestamp;

    public int getNumeric() {
        return this.numeric;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }
}
