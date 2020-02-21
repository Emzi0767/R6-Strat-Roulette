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

package com.emzi0767.r6stratroulette.data;

public final class RandomizedRecruit {
    private String primaryWeapon;
    private String secondaryWeapon;
    private String primaryGadget;
    private String secondaryGadget;

    public RandomizedRecruit() {

    }

    public RandomizedRecruit(
            String primaryWeapon,
            String secondaryWeapon,
            String primaryGadget,
            String secondaryGadget) {
        this.primaryWeapon = primaryWeapon;
        this.secondaryWeapon = secondaryWeapon;
        this.primaryGadget = primaryGadget;
        this.secondaryGadget = secondaryGadget;
    }

    public String getPrimaryWeapon() {
        return primaryWeapon;
    }

    public void setPrimaryWeapon(String primaryWeapon) {
        this.primaryWeapon = primaryWeapon;
    }

    public String getSecondaryWeapon() {
        return secondaryWeapon;
    }

    public void setSecondaryWeapon(String secondaryWeapon) {
        this.secondaryWeapon = secondaryWeapon;
    }

    public String getPrimaryGadget() {
        return primaryGadget;
    }

    public void setPrimaryGadget(String primaryGadget) {
        this.primaryGadget = primaryGadget;
    }

    public String getSecondaryGadget() {
        return secondaryGadget;
    }

    public void setSecondaryGadget(String secondaryGadget) {
        this.secondaryGadget = secondaryGadget;
    }
}
