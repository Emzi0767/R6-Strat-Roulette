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

import java.util.List;

public final class RecruitsData implements Parcelable {
    @SerializedName("attackers")
    private List<RecruitData> attackers;

    @SerializedName("defenders")
    private List<RecruitData> defenders;

    public static final Creator<RecruitsData> CREATOR = new Creator<RecruitsData>() {
        @Override
        public RecruitsData createFromParcel(Parcel in) {
            return new RecruitsData(in);
        }

        @Override
        public RecruitsData[] newArray(int size) {
            return new RecruitsData[size];
        }
    };

    public RecruitsData() {
        this.attackers = null;
        this.defenders = null;
    }

    protected RecruitsData(Parcel in) {
        this.attackers = in.createTypedArrayList(RecruitData.CREATOR);
        this.defenders = in.createTypedArrayList(RecruitData.CREATOR);
    }

    public List<RecruitData> getAttackers() {
        return attackers;
    }

    public List<RecruitData> getDefenders() {
        return defenders;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.attackers);
        dest.writeTypedList(this.defenders);
    }
}
