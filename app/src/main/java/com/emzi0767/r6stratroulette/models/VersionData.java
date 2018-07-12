// This file is a part of R6: Strat Roulette project.
//
// Copyright 2018 Emzi0767
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.emzi0767.r6stratroulette.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public final class VersionData implements Parcelable {
    @SerializedName("number")
    private int number;

    @SerializedName("timestamp")
    private Date timestamp;

    public static final Parcelable.Creator<VersionData> CREATOR = new Creator<VersionData>() {
        @Override
        public VersionData createFromParcel(Parcel in) {
            return new VersionData(in);
        }

        @Override
        public VersionData[] newArray(int i) {
            return new VersionData[i];
        }
    };

    public VersionData() {
        this.number = 0;
        this.timestamp = new Date();
    }

    protected VersionData(Parcel in) {
        this.number = in.readInt();
        this.timestamp = new Date(in.readLong());
    }

    public int getNumber() {
        return this.number;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.number);
        parcel.writeLong(this.timestamp.getTime());
    }
}
