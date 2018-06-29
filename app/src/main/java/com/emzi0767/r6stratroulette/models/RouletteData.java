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
import java.util.Map;

public final class RouletteData {
    @SerializedName("version")
    private VersionData version;

    @SerializedName("ctus")
    private Map<String, CtuData> ctus;

    @SerializedName("operators")
    private OperatorsData operators;

    @SerializedName("recruits")
    private RecruitsData recruits;

    @SerializedName("strats")
    private List<StrategyData> strategies;

    public RouletteData() {
        this.version = null;
        this.ctus = null;
        this.operators = null;
        this.recruits = null;
        this.strategies = null;
    }

    public VersionData getVersion() {
        return version;
    }

    public Map<String, CtuData> getCtus() {
        return ctus;
    }

    public OperatorsData getOperators() {
        return operators;
    }

    public RecruitsData getRecruits() {
        return recruits;
    }

    public List<StrategyData> getStrategies() {
        return strategies;
    }
}
