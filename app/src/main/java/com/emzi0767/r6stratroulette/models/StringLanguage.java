package com.emzi0767.r6stratroulette.models;

public enum StringLanguage {
    UNKNOWN(null),
    ENGLISH("en");

    private final String languageCode;

    StringLanguage(String code) {
        this.languageCode = code;
    }

    public String getLanguageCode() {
        return this.languageCode;
    }
}
