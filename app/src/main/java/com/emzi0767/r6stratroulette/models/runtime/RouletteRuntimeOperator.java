package com.emzi0767.r6stratroulette.models.runtime;

import android.os.Parcel;
import android.os.Parcelable;
import com.emzi0767.r6stratroulette.models.StringLanguage;
import com.emzi0767.r6stratroulette.models.json.RouletteJsonOperator;

import java.util.Map;

public class RouletteRuntimeOperator implements Parcelable {
    private String name;
    private String icon;
    private RouletteRuntimeCTU ctu;
    private RouletteRuntimeOperatorLoadout loadout;

    public static final Creator<RouletteRuntimeOperator> CREATOR = new Creator<RouletteRuntimeOperator>() {
        @Override
        public RouletteRuntimeOperator createFromParcel(Parcel in) {
            return new RouletteRuntimeOperator(in);
        }

        @Override
        public RouletteRuntimeOperator[] newArray(int size) {
            return new RouletteRuntimeOperator[size];
        }
    };

    private RouletteRuntimeOperator(
            String name,
            String icon,
            RouletteRuntimeCTU ctu,
            RouletteRuntimeOperatorLoadout loadout) {
        this.name = name;
        this.icon = icon;
        this.ctu = ctu;
        this.loadout = loadout;
    }

    protected RouletteRuntimeOperator(Parcel in) {
        this.name = in.readString();
        this.icon = in.readString();
        this.ctu = in.readParcelable(RouletteRuntimeCTU.class.getClassLoader());
        this.loadout = in.readParcelable(RouletteRuntimeOperatorLoadout.class.getClassLoader());
    }

    public String getName() {
        return this.name;
    }

    public String getIcon() {
        return this.icon;
    }

    public RouletteRuntimeCTU getCTU() {
        return this.ctu;
    }

    public RouletteRuntimeOperatorLoadout getLoadout() {
        return this.loadout;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.icon);
        dest.writeParcelable(this.ctu, flags);
        dest.writeParcelable(this.loadout, flags);
    }

    static RouletteRuntimeOperator fromJson(
            RouletteJsonOperator jsonOperator,
            Map<String, RouletteRuntimeCTU> ctus,
            Map<String, Map<String, String>> gadgetDefinitions,
            StringLanguage lang) {
        return new RouletteRuntimeOperator(
                jsonOperator.getName(),
                jsonOperator.getIcon(),
                ctus.get(jsonOperator.getCTU()),
                RouletteRuntimeOperatorLoadout.fromJson(jsonOperator.getLoadout(), gadgetDefinitions, lang)
        );
    }
}
