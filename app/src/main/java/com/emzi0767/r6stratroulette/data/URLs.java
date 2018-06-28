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
