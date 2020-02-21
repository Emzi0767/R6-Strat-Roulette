package com.emzi0767.r6stratroulette.models.runtime;

import android.os.Parcel;
import android.os.Parcelable;
import com.emzi0767.r6stratroulette.models.StringLanguage;
import com.emzi0767.r6stratroulette.models.json.RouletteJsonRecruitLoadout;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RouletteRuntimeRecruitLoadout implements Parcelable {
    private List<String> primaryWeapons;
    private List<String> secondaryWeapons;
    private List<String> primaryGadgets;
    private List<String> secondaryGadgets;

    public static final Creator<RouletteRuntimeRecruitLoadout> CREATOR = new Creator<RouletteRuntimeRecruitLoadout>() {
        @Override
        public RouletteRuntimeRecruitLoadout createFromParcel(Parcel in) {
            return new RouletteRuntimeRecruitLoadout(in);
        }

        @Override
        public RouletteRuntimeRecruitLoadout[] newArray(int size) {
            return new RouletteRuntimeRecruitLoadout[size];
        }
    };

    private RouletteRuntimeRecruitLoadout(
            List<String> primaryWeapons,
            List<String> secondaryWeapons,
            List<String> primaryGadgets,
            List<String> secondaryGadgets) {
        this.primaryWeapons = primaryWeapons;
        this.secondaryWeapons = secondaryWeapons;
        this.primaryGadgets = primaryGadgets;
        this.secondaryGadgets = secondaryGadgets;
    }

    protected RouletteRuntimeRecruitLoadout(Parcel in) {
        this.primaryWeapons = in.createStringArrayList();
        this.secondaryWeapons = in.createStringArrayList();
        this.primaryGadgets = in.createStringArrayList();
        this.secondaryGadgets = in.createStringArrayList();
    }

    public List<String> getPrimaryWeapons() {
        return this.primaryWeapons;
    }

    public List<String> getSecondaryWeapons() {
        return this.secondaryWeapons;
    }

    public List<String> getPrimaryGadgets() {
        return this.primaryGadgets;
    }

    public List<String> getSecondaryGadgets() {
        return this.secondaryGadgets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.primaryWeapons);
        dest.writeStringList(this.secondaryWeapons);
        dest.writeStringList(this.primaryGadgets);
        dest.writeStringList(this.secondaryGadgets);
    }

    static RouletteRuntimeRecruitLoadout fromJson(
            RouletteJsonRecruitLoadout jsonRecruitLoadout,
            Map<String, Map<String, String>> gadgetDefinitions,
            StringLanguage lang) {
        String[] gadgets1 = jsonRecruitLoadout.getPrimaryGadgets();
        String[] localizedGadgets1 = new String[gadgets1.length];
        for (int i = 0; i < localizedGadgets1.length; i++)
            localizedGadgets1[i] = gadgetDefinitions.get(gadgets1[i]).get(lang.getLanguageCode());

        String[] gadgets2 = jsonRecruitLoadout.getSecondaryGadgets();
        String[] localizedGadgets2 = new String[gadgets2.length];
        for (int i = 0; i < localizedGadgets2.length; i++)
            localizedGadgets2[i] = gadgetDefinitions.get(gadgets2[i]).get(lang.getLanguageCode());

        return new RouletteRuntimeRecruitLoadout(
                Arrays.asList(jsonRecruitLoadout.getPrimaryWeapons()),
                Arrays.asList(jsonRecruitLoadout.getSecondaryWeapons()),
                Arrays.asList(localizedGadgets1),
                Arrays.asList(localizedGadgets2)
        );
    }
}
