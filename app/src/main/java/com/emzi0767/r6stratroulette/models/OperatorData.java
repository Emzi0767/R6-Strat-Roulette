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

public final class OperatorData implements Parcelable {
    @SerializedName("name")
    private String name;

    @SerializedName("icon")
    private String icon;

    @SerializedName("ctu")
    private String ctu;

    @SerializedName("loadout")
    private LoadoutData loadout;

    public static final Creator<OperatorData> CREATOR = new Creator<OperatorData>() {
        @Override
        public OperatorData createFromParcel(Parcel in) {
            return new OperatorData(in);
        }

        @Override
        public OperatorData[] newArray(int size) {
            return new OperatorData[size];
        }
    };

    public OperatorData() {
        this.name = null;
        this.icon = null;
        this.ctu = null;
        this.loadout = null;
    }

    protected OperatorData(Parcel in) {
        this.name = in.readString();
        this.icon = in.readString();
        this.ctu = in.readString();
        this.loadout = in.readParcelable(LoadoutData.class.getClassLoader());
    }

    public String getName() {
        return this.name;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getCtu() {
        return this.ctu;
    }

    public LoadoutData getLoadout() {
        return this.loadout;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.icon);
        dest.writeString(this.ctu);
        dest.writeParcelable(this.loadout, flags);
    }
}
