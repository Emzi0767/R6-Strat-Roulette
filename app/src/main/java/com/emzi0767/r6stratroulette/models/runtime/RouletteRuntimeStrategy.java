package com.emzi0767.r6stratroulette.models.runtime;

import android.os.Parcel;
import android.os.Parcelable;
import com.emzi0767.r6stratroulette.models.StringLanguage;
import com.emzi0767.r6stratroulette.models.json.RouletteJsonStrategy;

import java.util.EnumSet;

public class RouletteRuntimeStrategy implements Parcelable {
    private String name;
    private String description;
    private EnumSet<RouletteRuntimeStrategySide> sides;
    private EnumSet<RouletteRuntimeStrategyGameMode> gameModes;

    public static final Creator<RouletteRuntimeStrategy> CREATOR = new Creator<RouletteRuntimeStrategy>() {
        @Override
        public RouletteRuntimeStrategy createFromParcel(Parcel in) {
            return new RouletteRuntimeStrategy(in);
        }

        @Override
        public RouletteRuntimeStrategy[] newArray(int size) {
            return new RouletteRuntimeStrategy[size];
        }
    };

    private RouletteRuntimeStrategy(
            String name,
            String description,
            EnumSet<RouletteRuntimeStrategySide> sides,
            EnumSet<RouletteRuntimeStrategyGameMode> gameModes) {
        this.name = name;
        this.description = description;
        this.sides = sides;
        this.gameModes = gameModes;
    }

    protected RouletteRuntimeStrategy(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.sides = RouletteRuntimeStrategySide.fromValue(in.readInt());
        this.gameModes = RouletteRuntimeStrategyGameMode.fromValue(in.readInt());
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public EnumSet<RouletteRuntimeStrategySide> getSides() {
        return this.sides;
    }

    public EnumSet<RouletteRuntimeStrategyGameMode> getGameModes() {
        return this.gameModes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.description);

        int val = 0;
        for (RouletteRuntimeStrategySide ss : this.sides)
            val |= ss.getValue();
        parcel.writeInt(val);

        val = 0;
        for (RouletteRuntimeStrategyGameMode sgm : this.gameModes)
            val |= sgm.getValue();
        parcel.writeInt(val);
    }

    static RouletteRuntimeStrategy fromJson(RouletteJsonStrategy jsonStrategy, StringLanguage lang) {
        return new RouletteRuntimeStrategy(
                jsonStrategy.getLocalizedNames().get(lang.getLanguageCode()),
                jsonStrategy.getLocalizedDescriptions().get(lang.getLanguageCode()),
                RouletteRuntimeStrategySide.fromNameArray(jsonStrategy.getSides()),
                RouletteRuntimeStrategyGameMode.fromNameArray(jsonStrategy.getModes())
        );
    }
}
