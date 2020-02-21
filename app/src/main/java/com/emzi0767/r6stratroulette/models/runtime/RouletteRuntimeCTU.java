package com.emzi0767.r6stratroulette.models.runtime;

import android.os.Parcel;
import android.os.Parcelable;
import com.emzi0767.r6stratroulette.models.json.RouletteJsonCTU;

public class RouletteRuntimeCTU implements Parcelable {
    private String id;
    private String name;
    private String abbreviation;

    public static final Creator<RouletteRuntimeCTU> CREATOR = new Creator<RouletteRuntimeCTU>() {
        @Override
        public RouletteRuntimeCTU createFromParcel(Parcel in) {
            return new RouletteRuntimeCTU(in);
        }

        @Override
        public RouletteRuntimeCTU[] newArray(int size) {
            return new RouletteRuntimeCTU[size];
        }
    };

    private RouletteRuntimeCTU(String id, String name, String abbreviation) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
    }

    protected RouletteRuntimeCTU(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.abbreviation = in.readString();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.abbreviation);
    }

    static RouletteRuntimeCTU fromJson(RouletteJsonCTU jsonCTU, String id) {
        return new RouletteRuntimeCTU(id, jsonCTU.getName(), jsonCTU.getAbbreviation());
    }
}
