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

import java.net.URL;

public final class URLs {
    public static final String URL_BASE = "https://static.emzi0767.com/r6strats/";

    public static URL getAssetUrl(String assetName) {
        try {
            URL url = new URL(URL_BASE + assetName);
            return url;
        } catch (Exception ex) {
            return null;
        }
    }
}
