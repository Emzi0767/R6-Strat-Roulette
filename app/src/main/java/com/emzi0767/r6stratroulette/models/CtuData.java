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

public final class CtuData implements Parcelable {
    @SerializedName("name")
    private String name;

    @SerializedName("abbr")
    private String abbreviation;

    @SerializedName("icon")
    private String icon;

    public static final Creator<CtuData> CREATOR = new Creator<CtuData>() {
        @Override
        public CtuData createFromParcel(Parcel in) {
            return new CtuData(in);
        }

        @Override
        public CtuData[] newArray(int size) {
            return new CtuData[size];
        }
    };

    public CtuData() {
        this.name = null;
        this.abbreviation = null;
        this.icon = null;
    }

    protected CtuData(Parcel in) {
        this.name = in.readString();
        this.abbreviation = in.readString();
        this.icon = in.readString();
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.abbreviation);
        dest.writeString(this.icon);
    }
}
