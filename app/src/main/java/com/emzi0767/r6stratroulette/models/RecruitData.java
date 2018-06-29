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

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class RecruitData {
    @SerializedName("ctu")
    private String ctu;

    @SerializedName("weapon1")
    private List<String> primaryWeapons;

    @SerializedName("weapon2")
    private List<String> secondaryWeapons;

    @SerializedName("gadget1")
    private List<String> primaryGadgets;

    @SerializedName("gadget2")
    private List<String> secondaryGadgets;

    public RecruitData() {
        this.ctu = null;
        this.primaryWeapons = null;
        this.secondaryWeapons = null;
        this.primaryGadgets = null;
        this.secondaryGadgets = null;
    }

    public String getCtu() {
        return ctu;
    }

    public List<String> getPrimaryWeapons() {
        return primaryWeapons;
    }

    public List<String> getSecondaryWeapons() {
        return secondaryWeapons;
    }

    public List<String> getPrimaryGadgets() {
        return primaryGadgets;
    }

    public List<String> getSecondaryGadgets() {
        return secondaryGadgets;
    }
}
