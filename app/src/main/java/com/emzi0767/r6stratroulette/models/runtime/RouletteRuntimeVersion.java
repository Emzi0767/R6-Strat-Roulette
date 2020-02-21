package com.emzi0767.r6stratroulette.models.runtime;

import android.os.Parcel;
import android.os.Parcelable;
import com.emzi0767.r6stratroulette.models.json.RouletteJsonVersion;

import java.util.Date;

public class RouletteRuntimeVersion implements Parcelable {
    private int numeric;
    private Date timestamp;

    public static final Creator<RouletteRuntimeVersion> CREATOR = new Creator<RouletteRuntimeVersion>() {
        @Override
        public RouletteRuntimeVersion createFromParcel(Parcel in) {
            return new RouletteRuntimeVersion(in);
        }

        @Override
        public RouletteRuntimeVersion[] newArray(int size) {
            return new RouletteRuntimeVersion[size];
        }
    };

    private RouletteRuntimeVersion(int numeric, Date timestamp) {
        this.numeric = numeric;
        this.timestamp = timestamp;
    }

    protected RouletteRuntimeVersion(Parcel in) {
        this.numeric = in.readInt();
        this.timestamp = new Date(in.readLong());
    }

    public int getNumeric() {
        return this.numeric;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.numeric);
        dest.writeLong(this.timestamp.getTime());
    }

    static RouletteRuntimeVersion fromJson(RouletteJsonVersion jsonVersion) {
        return new RouletteRuntimeVersion(jsonVersion.getNumeric(), jsonVersion.getTimestamp());
    }
}
