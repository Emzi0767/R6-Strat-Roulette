package com.emzi0767.r6stratroulette.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadoutData implements Parcelable {
    @SerializedName("primary")
    private List<String> primaryWeapons;

    @SerializedName("secondary")
    private List<String> secondaryWeapons;

    @SerializedName("gadgets")
    private List<String> gadgets;

    public static final Creator<LoadoutData> CREATOR = new Creator<LoadoutData>() {
        @Override
        public LoadoutData createFromParcel(Parcel in) {
            return new LoadoutData(in);
        }

        @Override
        public LoadoutData[] newArray(int size) {
            return new LoadoutData[size];
        }
    };

    public LoadoutData() {
        this.primaryWeapons = null;
        this.secondaryWeapons = null;
        this.gadgets = null;
    }

    protected LoadoutData(Parcel in) {
        this.primaryWeapons = in.createStringArrayList();
        this.secondaryWeapons = in.createStringArrayList();
        this.gadgets = in.createStringArrayList();
    }

    public List<String> getPrimaryWeapons() {
        return this.primaryWeapons;
    }

    public List<String> getSecondaryWeapons() {
        return this.secondaryWeapons;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<String> getGadgets() {
        return this.gadgets;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.primaryWeapons);
        dest.writeStringList(this.secondaryWeapons);
        dest.writeStringList(this.gadgets);
    }
}
