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

public final class StrategyData {
    @SerializedName("name")
    private String name;

    @SerializedName("desc")
    private String description;

    @SerializedName("side")
    private List<String> sides;

    @SerializedName("mode")
    private List<String> gameModes;

    public StrategyData() {
        this.name = null;
        this.description = null;
        this.sides = null;
        this.gameModes = null;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getSides() {
        return sides;
    }

    public List<String> getGameModes() {
        return gameModes;
    }
}
