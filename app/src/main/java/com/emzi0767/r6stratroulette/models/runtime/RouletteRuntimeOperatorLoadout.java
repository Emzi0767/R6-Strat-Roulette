package com.emzi0767.r6stratroulette.models.runtime;

import android.os.Parcel;
import android.os.Parcelable;
import com.emzi0767.r6stratroulette.models.StringLanguage;
import com.emzi0767.r6stratroulette.models.json.RouletteJsonOperatorLoadout;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RouletteRuntimeOperatorLoadout implements Parcelable {
    private List<String> primaryWeapons;
    private List<String> secondaryWeapons;
    private List<String> gadgets;

    public static final Creator<RouletteRuntimeOperatorLoadout> CREATOR = new Creator<RouletteRuntimeOperatorLoadout>() {
        @Override
        public RouletteRuntimeOperatorLoadout createFromParcel(Parcel in) {
            return new RouletteRuntimeOperatorLoadout(in);
        }

        @Override
        public RouletteRuntimeOperatorLoadout[] newArray(int size) {
            return new RouletteRuntimeOperatorLoadout[size];
        }
    };

    private RouletteRuntimeOperatorLoadout(
            List<String> primaryWeapons,
            List<String> secondaryWeapons,
            List<String> gadgets) {
        this.primaryWeapons = primaryWeapons;
        this.secondaryWeapons = secondaryWeapons;
        this.gadgets = gadgets;
    }

    protected RouletteRuntimeOperatorLoadout(Parcel in) {
        this.primaryWeapons = in.createStringArrayList();
        this.secondaryWeapons = in.createStringArrayList();
        this.gadgets = in.createStringArrayList();
    }

    public List<String> getPrimaryWeapons() {
        return this.primaryWeapons;
    }

    public List<String> getSecondaryWeapons() {
        return this.secondaryWeapons;
    }

    public List<String> getGadgets() {
        return this.gadgets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.primaryWeapons);
        dest.writeStringList(this.secondaryWeapons);
        dest.writeStringList(this.gadgets);
    }

    static RouletteRuntimeOperatorLoadout fromJson(
            RouletteJsonOperatorLoadout jsonOperatorLoadout,
            Map<String, Map<String, String>> gadgetDefinitions,
            StringLanguage lang) {
        String[] gadgets = jsonOperatorLoadout.getGadgets();
        String[] localizedGadgets = new String[gadgets.length];
        for (int i = 0; i < localizedGadgets.length; i++)
            localizedGadgets[i] = gadgetDefinitions.get(gadgets[i]).get(lang.getLanguageCode());

        return new RouletteRuntimeOperatorLoadout(
                Arrays.asList(jsonOperatorLoadout.getPrimaryWeapons()),
                Arrays.asList(jsonOperatorLoadout.getSecondaryWeapons()),
                Arrays.asList(localizedGadgets)
        );
    }
}
